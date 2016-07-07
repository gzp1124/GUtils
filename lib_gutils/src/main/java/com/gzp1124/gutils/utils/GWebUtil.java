package com.gzp1124.gutils.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.zxing.common.StringUtils;
import com.gzp1124.gutils.widget.BrowserFragment;

import java.net.URLDecoder;

/**
 * 浏览器相关工具类
 *
 * author：高志鹏 on 16/7/7 14:09
 * email:imbagaozp@163.com
 */
public class GWebUtil {
    /**
     * 打开系统中的浏览器
     *
     * @param context
     * @param url
     */
    public static void openSysBrowser(Context context, String url) {
        try {
            Uri uri = Uri.parse(url);
            Intent it = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(it);
        } catch (Exception e) {
            e.printStackTrace();
            GToastUtil.showToast("无法浏览此网页");
        }
    }

    /**
     * 打开内置浏览器
     *
     * @param context
     * @param url
     */
    public static void openBrowser(Context context, String url) {
        try {
            Bundle bundle = new Bundle();
            bundle.putString(BrowserFragment.BROWSER_KEY, url);
            //TODO 使用Activity将BrowserFragment进行显示
            GToastUtil.showToast("使用Activity将BrowserFragment进行显示");
        } catch (Exception e) {
            e.printStackTrace();
            GToastUtil.showToast("无法浏览此网页");
        }
    }

}
