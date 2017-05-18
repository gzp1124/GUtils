package com.gzp1124.gutils.utils;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 需要root权限
 * author：高志鹏 on 16/9/2 13:09
 * email:imbagaozp@163.com
 */
public class GWifiManage {

    public List<MyWifiInfo> read() throws Exception {
        List<MyWifiInfo> wifiInfos=new ArrayList<MyWifiInfo>();

        Process process = null;
        DataOutputStream dataOutputStream = null;
        DataInputStream dataInputStream = null;
        StringBuffer wifiConf = new StringBuffer();
        try {
            process = Runtime.getRuntime().exec("su");
            dataOutputStream = new DataOutputStream(process.getOutputStream());
            dataInputStream = new DataInputStream(process.getInputStream());
            dataOutputStream
                    .writeBytes("cat /data/misc/wifi/*.conf\n");
            dataOutputStream.writeBytes("exit\n");
            dataOutputStream.flush();
            InputStreamReader inputStreamReader = new InputStreamReader(
                    dataInputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(
                    inputStreamReader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                wifiConf.append(line);
            }
            bufferedReader.close();
            inputStreamReader.close();
            process.waitFor();
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (dataOutputStream != null) {
                    dataOutputStream.close();
                }
                if (dataInputStream != null) {
                    dataInputStream.close();
                }
                process.destroy();
            } catch (Exception e) {
                throw e;
            }
        }


        Pattern network = Pattern.compile("network=\\{([^\\}]+)\\}", Pattern.DOTALL);
        Matcher networkMatcher = network.matcher(wifiConf.toString() );
        while (networkMatcher.find() ) {
            String networkBlock = networkMatcher.group();
            Pattern ssid = Pattern.compile("ssid=\"([^\"]+)\"");
            Matcher ssidMatcher = ssid.matcher(networkBlock);

            if (ssidMatcher.find() ) {
                MyWifiInfo wifiInfo=new MyWifiInfo();
                wifiInfo.Ssid=ssidMatcher.group(1);
                Pattern psk = Pattern.compile("psk=\"([^\"]+)\"");
                Matcher pskMatcher = psk.matcher(networkBlock);
                if (pskMatcher.find() ) {
                    wifiInfo.Password=pskMatcher.group(1);
                } else {
                    wifiInfo.Password="无密码";
                }
                wifiInfos.add(wifiInfo);
            }

        }

        return wifiInfos;
    }

    public class MyWifiInfo {
        public String Ssid="";
        public String Password="";
    }




    public String getInfo(Activity activity){
        WifiManager wifi = (WifiManager) activity.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        String maxText = info.getMacAddress();
        String ipText = intToIp(info.getIpAddress());
        String status = "";
        if (wifi.getWifiState() == WifiManager.WIFI_STATE_ENABLED)
        {
            status = "WIFI_STATE_ENABLED";
        }
        String ssid = info.getSSID();
        int networkID = info.getNetworkId();
        int speed = info.getLinkSpeed();
        return "mac：" + maxText + "\n\r"
                + "ip：" + ipText + "\n\r"
                + "wifi status :" + status + "\n\r"
                + "ssid :" + ssid + "\n\r"
                + "net work id :" + networkID + "\n\r"
                + "connection speed:" + speed + "\n\r"
                ;
    }
    private String intToIp(int ip) {
        return (ip & 0xFF) + "." + ((ip >> 8) & 0xFF) + "." + ((ip >> 16) & 0xFF) + "."
                + ((ip >> 24) & 0xFF);
    }

}