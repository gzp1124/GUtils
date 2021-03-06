package com.gzp1124.gutils.three_platform.social;

import com.gzp1124.gutils.three_platform.social.impl.GNativeLoginImpl;
import com.gzp1124.gutils.three_platform.social.impl.GNativeShareImpl;

/**
 * 三方社会化的工具类
 * author：高志鹏 on 16/5/18 22:34
 * email:imbagaozp@163.com
 */
public class GSocialUtil {
    private static GShareInterface gShareInterface = null;
    private static GLoginInterface gLoginInterface = null;

    public static final String WX_APP_ID = "wx54708fe8e4a5cc5d";
    public static final String WX_APP_SECRET = "9a97182be7a0117deb02a4d7d99987ac";

    //三方分享
    public static GShareInterface getShareInstance(){
        if (gShareInterface == null){
            //使用友盟方式
//            gShareInterface = new UmengShareImpl();
            //直接对接第三方
            gShareInterface = new GNativeShareImpl();
        }
        return gShareInterface;
    }

    //三方登录
    public static GLoginInterface getLoginInstance(){
        if (gLoginInterface == null){
            gLoginInterface = new GNativeLoginImpl();
        }
        return  gLoginInterface;
    }
}
