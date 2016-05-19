package com.example.gzp1124.gutils.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.gzp1124.gutils.R;
import com.example.gzp1124.gutils.three_platform.social.GSocialUtil;
import com.example.gzp1124.gutils.utils.GTimeTaskUtil;
import com.example.gzp1124.gutils.utils.NotificationUtil;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * 微信规定的回调activity
 * 并在manifest文件里面加上exported属性，设置为true
 * author：高志鹏 on 16/5/19 10:00
 * email:imbagaozp@163.com
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI mWxApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        mWxApi = WXAPIFactory.createWXAPI(this, GSocialUtil.WX_APP_ID, false);
        mWxApi.registerApp(GSocialUtil.WX_APP_ID);
        mWxApi.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        //微信发送的请求将回调到onReq方法
        GTimeTaskUtil.setAlarmReceiverSuccess(new GTimeTaskUtil.GAlarmReceiverInterface() {
            @Override
            public void receiverSuccess(Intent intent) {
                NotificationUtil.simple("来自onReq");
            }
        });
        GTimeTaskUtil.startRequestAlarm(System.currentTimeMillis(),GTimeTaskUtil.ALARM_ACTION);
    }

    /*
     * WXEntryActivity 返回收到的错误码是-6 ：清除微信数据
     */
    @Override
    public void onResp(final BaseResp baseResp) {
        Log.i("gzp1124","错误号：" + baseResp.errCode + "；信息：" + baseResp.errStr);
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK://成功
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL://取消
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED://被拒绝
                break;
        }
        //发送到微信请求的响应结果将回调到onResp方法
        GTimeTaskUtil.setAlarmReceiverSuccess(new GTimeTaskUtil.GAlarmReceiverInterface() {
            @Override
            public void receiverSuccess(Intent intent) {
                NotificationUtil.simple("错误号：" + baseResp.errCode + "；信息：" + baseResp.errStr);
            }
        });
        GTimeTaskUtil.startRequestAlarm(System.currentTimeMillis(),GTimeTaskUtil.ALARM_ACTION);
        finish();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        mWxApi.handleIntent(intent, this);
    }
}
