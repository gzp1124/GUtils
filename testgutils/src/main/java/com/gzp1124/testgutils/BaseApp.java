package com.gzp1124.testgutils;

import com.example.gzp1124.testgutils.BuildConfig;
import com.gzp1124.gutils.BaseApplication;
import com.gzp1124.log.GLog;

/**
 * author：高志鹏 on 16/6/16 11:29
 * email:imbagaozp@163.com
 */
public class BaseApp extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();

        GLog.init(BuildConfig.DEBUG);
    }
}
