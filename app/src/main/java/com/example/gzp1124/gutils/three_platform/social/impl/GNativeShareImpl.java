package com.example.gzp1124.gutils.three_platform.social.impl;

import com.example.gzp1124.gutils.three_platform.social.GLoginInterface;
import com.example.gzp1124.gutils.three_platform.social.GShareInterface;
import com.example.gzp1124.gutils.three_platform.social.weixin.WeiXinShare;

/**
 * 原生对接第三方的方式实现社会化
 * author：高志鹏 on 16/5/18 22:47
 * email:imbagaozp@163.com
 */
public class GNativeShareImpl implements GShareInterface {

    @Override
    public boolean regWX() {
        return WeiXinShare.regToWX();
    }

    @Override
    public void weixinShare(GShareInfo shareInfo,GShareCallBack callBack) {
        //调用weixin包下的真正的分享功能，进行分享，然后进行分享成功或失败的回调
//        callBack.shareError();
        WeiXinShare.shareHtml();
    }

    @Override
    public void qqShare(GShareInfo shareInfo,GShareCallBack callBack) {
        callBack.shareError();
    }
}
