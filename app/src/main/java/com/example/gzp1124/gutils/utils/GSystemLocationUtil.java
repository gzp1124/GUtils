package com.example.gzp1124.gutils.utils;

import android.Manifest;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.widget.ImageView;

import com.example.gzp1124.gutils.BaseApplication;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * 使用系统的地位服务
 * 作者：gzp on 2016/5/16 15:34
 * 邮箱：imbagaozp@163.com
 *
 *    权限
 <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
 <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
 */
public class GSystemLocationUtil {

    private static Location gLocation;
    private static boolean permissionDenied = false;

    /** 获取当前位置 */
    private static boolean getLastLocation() {
        if (gLocation != null){return true;}
        LocationManager locationManager = (LocationManager) BaseApplication.gContext.getSystemService(Context.LOCATION_SERVICE);
        if (GDeviceUtil.checkPermissionStatus(Manifest.permission.ACCESS_FINE_LOCATION)
                && GDeviceUtil.checkPermissionStatus(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            gLocation = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
            return true;
        }else{
            return false;
        }
    }

    /** 获取经度 */
    public static double getCurrentLongitude(){
        getLastLocation();
        return gLocation.getLongitude();
    }
    /** 获取纬度 */
    public static double getCurrentLatitude(){
        getLastLocation();
        return gLocation.getLatitude();
    }
    /** 获取地址 */
    public static String getAddress(){
        getLastLocation();
        Geocoder geocoder=new Geocoder(BaseApplication.gContext, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(gLocation.getLatitude(), gLocation.getLongitude(), 1);
            for (Address add:addresses){
                GLog.log("ggg"+add.toString() + "\r\n");
            }
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }



}
