package com.example.gzp1124.gutils.three_platform.social;

/**
 * 三方分享接口类
 * Created by gzp1124 on 16/5/18.
 */
public interface GShareInterface {
    /**
     * 常用的分享数据：
     *  content
     *  title
     *  img
     *  link
     * 四部分
     */

    /**分享回调*/
    interface GShareCallBack{
        void shareSuccess();
        void shareError();
    }

    boolean regWX();

    void weixinShare(GShareInfo shareInfo,GShareCallBack callBack);

    void qqShare(GShareInfo shareInfo,GShareCallBack callBack);

    /**
     * 分享实体类，要分享的内容同实体传递
     * 微信目前支持的分享内容为：文字、图片、音乐、视频、网页
     */
    public class GShareInfo{
        private String content;
        private String title;
        private String img;
        private String link;

        /**
         * @param content 分享的内容
         * @param title 标题
         * @param img 分享的图片
         * @param link 分享链接
         */
        public GShareInfo(String content, String title, String img, String link) {
            this.content = content;
            this.title = title;
            this.img = img;
            this.link = link;
        }
    }
}
