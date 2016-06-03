package com.example.gzp1124.gutils.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gzp1124.gutils.BaseApplication;
import com.example.gzp1124.gutils.R;

import java.lang.ref.WeakReference;


/**
 * 自定义的Toast显示工具类
 *
 * 权限：<uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
 *
 * 样式
 <style name="gtoast_anim_view">
 <item name="@android:windowEnterAnimation">@anim/gtoast_anim_in</item>
 <item name="@android:windowExitAnimation">@anim/gtoast_anim_out</item>
 </style>

 <style name="gtoast_top_anim_view">
 <item name="@android:windowEnterAnimation">@anim/gtoast_top_anim_in</item>
 <item name="@android:windowExitAnimation">@anim/gtoast_top_anim_out</item>
 </style>

 <style name="gtoast_bottom_anim_view">
 <item name="@android:windowEnterAnimation">@anim/gtoast_bottom_anim_in</item>
 <item name="@android:windowExitAnimation">@anim/gtoast_bottom_anim_out</item>
 </style>

 <style name="gtoast_center_anim_view">
 <item name="@android:windowEnterAnimation">@anim/gtoast_center_anim_in</item>
 <item name="@android:windowExitAnimation">@anim/gtoast_center_anim_out</item>
 </style>
 */
public class GToastUtil {

    //==============
    /**
     * 主流的自定义Toast显示方式
     * 默认显示调用方式：GToastUtil.getInstance(ZoomPicActivity.this).show();
     */
    private static GToastUtil gToastFloat;

    public static final int TOP = 101;
    public static final int CENTER = 102;
    public static final int BOTTOM = 103;

    public static final int LENGTH_ALWAYS = 0;
    public static final int LENGTH_SHORT = 2;
    public static final int LENGTH_LONG = 4;

    static WeakReference<Activity> mActivity;
    WindowManager.LayoutParams params;
    WindowManager mWM;
    LinearLayout mView;
    TextView mTv;
    Handler mHandler;
    int mDuration = LENGTH_SHORT;
    private final ImageView mIv;
    //初始化
    private GToastUtil(){
        //获取一个全局变量
        mHandler = new Handler();
        params = new WindowManager.LayoutParams();
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.format = PixelFormat.TRANSLUCENT;
        params.alpha = 80;

        if (Build.VERSION.SDK_INT > 19){
            params.type = WindowManager.LayoutParams.TYPE_TOAST;
        }else {
            params.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
        }

        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;

        //默认top
        params.gravity = Gravity.FILL_HORIZONTAL | Gravity.TOP;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.windowAnimations = R.style.gtoast_top_anim_view;

        mWM = (WindowManager) mActivity.get().getSystemService(Context.WINDOW_SERVICE);

        LayoutInflater inflate = (LayoutInflater)
                mActivity.get().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mView = new LinearLayout(mActivity.get());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mView.setLayoutParams(layoutParams);
        mView.setPadding(0,20,0,20);
        mView.setBackgroundColor(Color.parseColor("#ccff0000"));
        mView.setOrientation(LinearLayout.HORIZONTAL);
        mView.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        mIv = new ImageView(mActivity.get());
        mIv.setImageResource(R.mipmap.ic_launcher);
        mView.addView(mIv,params);

        mTv = new TextView(mActivity.get());
        mTv.setText("这是一条提示信息!");
        mView.addView(mTv,params);
    }
    //设置显示位置，默认Top (GtoastUtil.TOP)
    public void setGravity(int gravity){
        switch (gravity){
            case TOP:
                params.gravity = Gravity.FILL_HORIZONTAL | Gravity.TOP;
                params.width = WindowManager.LayoutParams.MATCH_PARENT;
                params.windowAnimations = R.style.gtoast_top_anim_view;
                break;
            case CENTER:
                params.gravity = Gravity.CENTER;
                params.width = WindowManager.LayoutParams.WRAP_CONTENT;
                params.windowAnimations = R.style.gtoast_center_anim_view;
                break;
            case BOTTOM:
                params.gravity = Gravity.FILL_HORIZONTAL | Gravity.BOTTOM;
                params.width = WindowManager.LayoutParams.MATCH_PARENT;
                params.windowAnimations = R.style.gtoast_bottom_anim_view;
                break;
        }
    }

    private Runnable hideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    //展示toast
    public void show(){
        if (mView.getParent() != null) {
            mWM.removeView(mView);
        }
        mWM.addView(mView, params);
        //判断duration，如果大于#LENGTH_ALWAYS 则设置消失时间
        if (mDuration > LENGTH_ALWAYS) {
            mHandler.removeCallbacks(hideRunnable);
            mHandler.postDelayed(hideRunnable, mDuration * 1000);
        }
    }

    public void hide(){
        if (mView != null) {
            if (mView.getParent() != null) {
                mWM.removeView(mView);
            }
        }
    }
    //设置提示文本
    public GToastUtil setText(String text){
        mTv.setText(text);
        return gToastFloat;
    }
    //设置图片
    public GToastUtil setImage(int imageId){
        mIv.setImageResource(imageId);
        return gToastFloat;
    }
    //设置图片
    public GToastUtil setImage(Bitmap bitmap){
        mIv.setImageBitmap(bitmap);
        return gToastFloat;
    }
    //设置显示时间，默认为SHORT，（GToastUtil.LENGTH_SHORT）
    public GToastUtil setDuration(int duration) {
        mDuration = duration;
        return gToastFloat;
    }
    //获取实例
    public static GToastUtil getInstance(Activity activity){
        mActivity = new WeakReference<Activity>(activity);
        if (gToastFloat == null){
            gToastFloat = new GToastUtil();
        }
        return gToastFloat;
    }

}
