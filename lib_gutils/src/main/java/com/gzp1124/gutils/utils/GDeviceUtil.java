package com.gzp1124.gutils.utils;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.gzp1124.gutils.base.BaseApplication;

/**
 * 和设备相关的工具类
 * 作者：gzp on 2016/5/16 15:45
 * 邮箱：imbagaozp@163.com
 *
 * 功能：
 * 1. 6.0动态权限检测，判断当前权限状态
 */
public class GDeviceUtil {

    /**
     * 检测权限状态
     * @param permission
     * @return 是否有该权限 true有，false没有
     */
    public static boolean checkPermissionStatus(@NonNull String permission){
        return  ActivityCompat.checkSelfPermission(BaseApplication.gContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

}
