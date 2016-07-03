package com.gzp1124.gutils.three_platform.social.weixin;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.gzp1124.gutils.base.BaseApplication;
import com.gzp1124.gutils.R;
import com.gzp1124.gutils.three_platform.social.GSocialUtil;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.io.ByteArrayOutputStream;

/**
 * 微信分享，对接官方文档实现
 * author：高志鹏 on 16/5/18 22:15
 * email:imbagaozp@163.com
 */
public class WeiXinShare {
    private static final String APP_ID = GSocialUtil.WX_APP_ID;
    private static final String APP_SECRET = GSocialUtil.WX_APP_SECRET;

    private static IWXAPI wxapi;

    //注册微信，
    public static boolean regToWX(){
        wxapi = WXAPIFactory.createWXAPI(BaseApplication.gContext,APP_ID,true);
         return wxapi.registerApp(APP_ID);
    }

    public static void shareText(){

        String sc = "这是测试分享内容";
        //初始化填写分享的文本内容
        WXTextObject textObject = new WXTextObject();
        textObject.text = sc;

        //用WXTextObject对象初始化一个对象
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObject;
        msg.description = sc;

        //构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        //transaction字段唯一标示一个请求
        req.transaction = sc;
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneTimeline;

        boolean b = wxapi.sendReq(req);
    }

    public static void shareHtml(){
//        WXWebpageObject webpageObject = new WXWebpageObject();
//        webpageObject.webpageUrl = "http://www.aixintrip.cn";
//
//        WXMediaMessage msg = new WXMediaMessage(webpageObject);
//        msg.title = "测试标题";
//        msg.description = "测试描述";
//        Bitmap thumb = BitmapFactory.decodeResource(BaseApplication.gContext.getResources(), R.mipmap.ic_launcher);
//        msg.thumbData = bmpToByteArray(thumb);
//
//        SendMessageToWX.Req req = new SendMessageToWX.Req();
//        req.transaction = buildTransaction("webpage");
//        req.message = msg;
//        req.scene = SendMessageToWX.Req.WXSceneTimeline;
//
//        wxapi.sendReq(req);
        shareWebPage(wxapi,"http://www.baidu.com",false,R.mipmap.ic_launcher,"标题11","描述11");
    }

    public static byte[] bpToByte(Bitmap bitmap){
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream);
        byte[] bytes = bStream.toByteArray();
        return bytes;
    }

    /**
     * 构建一个唯一标志
     * @author YOLANDA
     * @param type
     * @return
     */
    private static String buildTransaction(String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : (type + System.currentTimeMillis());
    }
    /**
     * 得到Bitmap的byte
     * @author YOLANDA
     * @param bmp
     * @return
     */
    private static byte[] bmpToByteArray(Bitmap bmp) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 分享一个网页
     * @author YOLANDA
     * @param wxApi
     * @param httpUrl
     * @param isToFriend
     * @param icon
     * @param title
     * @param description
     */
    public static void shareWebPage(IWXAPI wxApi, String httpUrl, boolean isToFriend, Bitmap icon, String title, String description){
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = httpUrl;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = description;
        msg.thumbData = bmpToByteArray(icon);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = isToFriend ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
        boolean b = wxApi.sendReq(req);
    }
    /**
     * 分享一个网页
     * @author YOLANDA
     * @param wxApi
     * @param httpUrl 要分享的连接
     * @param isToFriend 是否是分享到朋友圈
     * @param iconRes ICON
     * @param title 标题
     * @param description 描述
     */
    public static void shareWebPage(IWXAPI wxApi, String httpUrl, boolean isToFriend, int iconRes, String title, String description){
        Bitmap icon = BitmapFactory.decodeResource(BaseApplication.gContext.getResources(), iconRes);
        shareWebPage(wxApi, httpUrl, isToFriend, icon, title, description);
    }
    /**压缩图的大小**/
    private static final int THUMB_SIZE = 150;
    /**
     * 分享一个图片
     * @author YOLANDA
     * @param wxApi
     * @param shareBitmap 要分享的图片
     * @param isToFriend 是否是分享到朋友圈
     */
    public static void shareImg(IWXAPI wxApi, Bitmap shareBitmap, boolean isToFriend){
        WXImageObject imgObj = new WXImageObject(shareBitmap);

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;

        Bitmap thumbBmp = Bitmap.createScaledBitmap(shareBitmap, THUMB_SIZE, THUMB_SIZE, true);
        msg.thumbData = bmpToByteArray(thumbBmp);  // 设置缩略图

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        req.scene = isToFriend ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
        wxApi.sendReq(req);
    }
}
