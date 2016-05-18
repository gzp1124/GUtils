package com.example.gzp1124.gutils;

import android.app.Application;
import android.app.Fragment;
import android.content.Context;
import android.widget.Toast;

/**
 * 作者：gzp on 2016/5/16 15:46
 * 邮箱：imbagaozp@163.com
 */
public class BaseApplication extends Application {
    public static Context gContext;
    public static Fragment showFragment;

    //TODO 崩溃收集

    @Override
    public void onCreate() {
        super.onCreate();
        gContext = this;
    }

    public static void showToast(String content){
        Toast.makeText(gContext,content,Toast.LENGTH_SHORT).show();
    }
}
