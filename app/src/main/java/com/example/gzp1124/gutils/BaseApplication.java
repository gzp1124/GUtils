package com.example.gzp1124.gutils;

import android.app.Application;
import android.content.Context;

/**
 * 作者：gzp on 2016/5/16 15:46
 * 邮箱：imbagaozp@163.com
 */
public class BaseApplication extends Application {
    public static Context gContext;
    //TODO 崩溃收集
    @Override
    public void onCreate() {
        super.onCreate();
        gContext = this;
    }
}
