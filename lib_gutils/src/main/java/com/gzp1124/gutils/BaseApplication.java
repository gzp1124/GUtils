package com.gzp1124.gutils;

import android.app.Application;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.gzp1124.gutils.three_platform.social.GSocialUtil;

/**
 * 作者：gzp on 2016/5/16 15:46
 * 邮箱：imbagaozp@163.com
 */
public class BaseApplication extends Application {
    public static Context gContext;
    public static Fragment showFragment;

    @Override
    public void onCreate() {
        super.onCreate();
        gContext = this;

        boolean b = GSocialUtil.getShareInstance().regWX();
        showToast(b+"注册");

        initException();
    }

    private void initException() {
        Thread.setDefaultUncaughtExceptionHandler(AppException
                .getAppExceptionHandler(this));
    }

    public static void showToast(String content){
        Toast.makeText(gContext,content,Toast.LENGTH_SHORT).show();

    }
}
