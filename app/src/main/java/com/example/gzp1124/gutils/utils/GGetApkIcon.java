package com.example.gzp1124.gutils.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * 抽取apk程序的应用图标
 * 不在android中使用，使用pe
 *
 * 使用方式：GetApkIcon.getApkIcon("C:\\Users\\ThinkPad\\Desktop\\aixin_2.0.4_pgyer.apk", "C:\\Users\\ThinkPad\\Desktop\\node", "C:\\Users\\ThinkPad\\Desktop\\node");
 */
public class GGetApkIcon {

    /**
     * 抽取apk程序的应用图标
     * @param apkPath  apk的路径名
     * @param aaptPath aapt工具的全路径名
     * @param outDir   图标的输出路径
     */
    public static void getApkIcon(String apkPath, String aaptPath, String outDir) {
        String iconName = getIconName(apkPath, aaptPath);
        File iconOutDir = new File(outDir);
        if (!iconOutDir.exists()) {
            iconOutDir.mkdirs();
        }
        getIcon(apkPath, iconName, outDir + iconName);
    }

    /**
     * 获取图标的名字
     */
    private static String getIconName(String apkPath, String aaptPath) {
        String iconName = "";
        try {
            /**
             * Runtime类封装了运行时的环境。每个Java应用程序都有一个Runtime 类实例，使应用程序能够与其运行的环境相连接。
             * 一般不能实例化一个Runtime对象，应用程序也不能创建自己的Runtime类实例，但可以通过getRuntime方法获取当前Runtime运行时对象的引用。
             */
            Runtime rt = Runtime.getRuntime();
            String order = aaptPath + "aapt.exe" + " d badging \"" + apkPath + "\"";
            Process proc = rt.exec(order);
            InputStream inputStream = proc.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains("application:")) {//application: label='UC浏览器' icon='res/drawable-hdpi/icon.png'
                    iconName = line.substring(line.lastIndexOf("/") + 1, line.lastIndexOf("'")).trim().toLowerCase();
                    break;
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return iconName;
    }

    /**
     * 抽取图标
     */
    private static void getIcon(String apkPath, String iconName, String outPath) {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        ZipInputStream zis = null;
        File apkFile = new File(apkPath);
        try {
            fis = new FileInputStream(apkFile);
            zis = new ZipInputStream(fis);
            ZipEntry zipEntry = null;
            while ((zipEntry = zis.getNextEntry()) != null) {
                String name = zipEntry.getName().toLowerCase();
                if ((name.endsWith("/" + iconName) && name.contains("drawable") && name.contains("res")) ||
                        (name.endsWith("/" + iconName) && name.contains("raw") && name.contains("res"))) {
                    fos = new FileOutputStream(new File(outPath));
                    byte[] buffer = new byte[1024];
                    int n = 0;
                    while ((n = zis.read(buffer, 0, buffer.length)) != -1) {
                        fos.write(buffer, 0, n);
                    }
                    break;
                }
            }
            fos = null;
            zis.closeEntry();
            zipEntry = null;
            System.out.println("图标抽取成功" + outPath);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (zis != null) {
                    zis.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
