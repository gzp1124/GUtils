package com.gzp1124.gutils.three_platform.social.impl;

import com.gzp1124.gutils.three_platform.social.GShareInterface;

/**
 * 友盟方式实现分享
 * author：高志鹏 on 16/5/18 22:47
 * email:imbagaozp@163.com
 */
public class UmengShareImpl implements GShareInterface {
    @Override
    public boolean regWX() {
        return false;
    }

    @Override
    public void weixinShare(GShareInfo shareInfo,GShareCallBack callBack) {
        callBack.shareSuccess();
    }

    @Override
    public void qqShare(GShareInfo shareInfo,GShareCallBack callBack) {
        callBack.shareSuccess();
    }

}
