package com.gzp1124.gutils.utils;

import android.os.Environment;

import java.io.File;
import java.io.IOException;

/**
 * author：高志鹏 on 16/6/12 14:58
 * email:imbagaozp@163.com
 */
public class GFileUtil {

    public static File getSaveFile(String folderPath, String fileNmae) {
        File file = new File(getSavePath(folderPath) + File.separator + fileNmae);

        try {
            file.createNewFile();
        } catch (IOException var4) {
            var4.printStackTrace();
        }

        return file;
    }

    public static String getSavePath(String folderName) {
        return getSaveFolder(folderName).getAbsolutePath();
    }

    public static File getSaveFolder(String folderName) {
        File file = new File(getSDCardPath() + File.separator + folderName + File.separator);
        file.mkdirs();
        return file;
    }

    public static String getSDCardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }
}
