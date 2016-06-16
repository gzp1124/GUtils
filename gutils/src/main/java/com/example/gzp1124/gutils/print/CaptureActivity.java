/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.gzp1124.gutils.print;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.gzp1124.gutils.R;
import com.example.gzp1124.gutils.print.camera.CameraManager;
import com.example.gzp1124.gutils.print.decode.DecodeThread;
import com.example.gzp1124.gutils.print.decode.ViewfinderView;
import com.example.gzp1124.gutils.print.print_util.BeepManager;
import com.example.gzp1124.gutils.print.print_util.CaptureActivityHandler;
import com.example.gzp1124.gutils.print.print_util.InactivityTimer;
import com.google.zxing.Result;

import java.io.IOException;
import java.lang.reflect.Field;


/**
 * This activity opens the camera and does the actual scanning on a background
 * thread. It draws a viewfinder to help the user place the barcode correctly,
 * shows feedback as the image processing is happening, and then overlays the
 * results when a scan is successful.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 * @author Sean Owen
 */
public final class CaptureActivity extends ActionBarActivity implements
        SurfaceHolder.Callback, View.OnClickListener {

    private static final String TAG = CaptureActivity.class.getSimpleName();

    private CameraManager cameraManager;
    private CaptureActivityHandler handler;
    private InactivityTimer inactivityTimer;
    private BeepManager beepManager;

    private SurfaceView scanPreview = null;
    private RelativeLayout scanContainer;
    private ViewfinderView scanCropView;
    private ImageView scanLine;
    private ImageView mFlash;

    private Rect mCropRect = null;

    private boolean isHasSurface = false;
    //竖屏标识
    public static boolean isVertical = false;
    //使用前置摄像头
    public static boolean isFrontCamera = true;

    public Handler getHandler() {
        return handler;
    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        isFrontCamera = getIntent().getBooleanExtra("isFront",true);

        int ori = this.getResources().getConfiguration().orientation;
        if(ori == Configuration.ORIENTATION_LANDSCAPE) {
            //横屏
            isVertical = false;
        } else if (ori == Configuration.ORIENTATION_PORTRAIT) {
            //竖屏
            isVertical = true;
        }

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        if (isVertical) {
            setContentView(R.layout.print_activity_vartical_qr_scan);
        }else {
            setContentView(R.layout.print_activity_landscape_qr_scan);
        }

        scanPreview = (SurfaceView) findViewById(R.id.capture_preview);
        scanContainer = (RelativeLayout) findViewById(R.id.capture_container);
        scanCropView = (ViewfinderView) findViewById(R.id.capture_crop_view);
        scanCropView.setInit(cameraManager,-50,this);
        scanLine = (ImageView) findViewById(R.id.capture_scan_line);
        mFlash = (ImageView) findViewById(R.id.capture_flash);
        mFlash.setOnClickListener(this);

        findViewById(R.id.switch_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFrontBack(!isFrontCamera);
            }
        });

        inactivityTimer = new InactivityTimer(this);
        beepManager = new BeepManager(this);

        TranslateAnimation animation = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.9f);
        animation.setDuration(4500);
        animation.setRepeatCount(-1);
        animation.setRepeatMode(Animation.RESTART);
        scanLine.startAnimation(animation);
    }

    @SuppressLint("NewApi")
    protected boolean hasActionBar() {

        if (Build.VERSION.SDK_INT >= 11) {
            getSupportActionBar().hide();
            return true;
        } else {
            return false;
        }

    }

    /**
     * 切换前置后置摄像头
     * @param isFront
     */
    protected void switchFrontBack(boolean isFront){
        isFrontCamera = isFront;

        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        inactivityTimer.onPause();
        beepManager.close();
        cameraManager.closeDriver();
        if (!isHasSurface) {
            scanPreview.getHolder().removeCallback(this);
        }

        inactivityTimer.shutdown();

        cameraManager = new CameraManager(getApplication());
        if (isFrontCamera) {
            //前置
            cameraManager.setManualCameraId(cameraManager.getDefaultCameraId());
        }else {
            //后置
            cameraManager.setManualCameraId(-1);
        }

        handler = null;

        if (isHasSurface) {
            // The activity was paused but not stopped, so the surface still
            // exists. Therefore
            // surfaceCreated() won't be called, so init the camera here.
            initCamera(scanPreview.getHolder());
        } else {
            // Install the callback and wait for surfaceCreated() to init the
            // camera.
            scanPreview.getHolder().addCallback(this);
        }

        inactivityTimer.onResume();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onResume() {
        super.onResume();

        // CameraManager must be initialized here, not in onCreate(). This is
        // necessary because we don't
        // want to open the camera driver and measure the screen size if we're
        // going to show the help on
        // first launch. That led to bugs where the scanning rectangle was the
        // wrong size and partially
        // off screen.
        cameraManager = new CameraManager(getApplication());
        if (isFrontCamera) {
            //前置
            cameraManager.setManualCameraId(cameraManager.getDefaultCameraId());
        }else {
            //后置
            cameraManager.setManualCameraId(-1);
        }

        handler = null;

        if (isHasSurface) {
            // The activity was paused but not stopped, so the surface still
            // exists. Therefore
            // surfaceCreated() won't be called, so init the camera here.
            initCamera(scanPreview.getHolder());
        } else {
            // Install the callback and wait for surfaceCreated() to init the
            // camera.
            scanPreview.getHolder().addCallback(this);
        }

        inactivityTimer.onResume();
    }

    @Override
    protected void onPause() {
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        inactivityTimer.onPause();
        beepManager.close();
        cameraManager.closeDriver();
        if (!isHasSurface) {
            scanPreview.getHolder().removeCallback(this);
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (holder == null) {
            Log.e(TAG,
                    "*** WARNING *** surfaceCreated() gave us a null surface!");
        }
        if (!isHasSurface) {
            isHasSurface = true;
            initCamera(holder);

        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isHasSurface = false;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    /**
     * A valid barcode has been found, so give an indication of success and show
     * the results.
     *
     * @param rawResult The contents of the barcode.
     * @param bundle    The extras
     */
    public void handleDecode(final Result rawResult, Bundle bundle) {
        inactivityTimer.onActivity();
        beepManager.playBeepSoundAndVibrate();

        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                resultHandlerText(rawResult.getText());
            }
        }, 800);
    }

    private void handleText(String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.app_name));
        builder.setMessage(text);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }

        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });
        builder.show();
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        int degree = 0;
        //横竖屏切换时调整显示角度
        if (isVertical){
            degree = 90;
        }else {
            degree = 0;
        }

        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        }
        if (cameraManager.isOpen()) {
            Log.w(TAG,
                    "initCamera() while already open -- late SurfaceView callback?");
            return;
        }
        try {
            cameraManager.openDriver(surfaceHolder,degree);
            // Creating the handler starts the preview, which can also throw a
            // RuntimeException.
            if (handler == null) {
                handler = new CaptureActivityHandler(this, cameraManager,
                        DecodeThread.ALL_MODE);
            }

            initCrop();
        } catch (IOException ioe) {
            Log.w(TAG, ioe);
            displayFrameworkBugMessageAndExit();
        } catch (RuntimeException e) {
            Log.w(TAG, "Unexpected error initializing camera", e);
            displayFrameworkBugMessageAndExit();
        }
    }

    private void displayFrameworkBugMessageAndExit() {
        // camera error
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.app_name));
        builder.setMessage("相机打开出错，请稍后重试");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }

        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });
        builder.show();
    }

    public void restartPreviewAfterDelay(long delayMS) {
        if (handler != null) {
            handler.sendEmptyMessageDelayed(R.id.restart_preview, delayMS);
        }
    }

    public Rect getCropRect() {
        return mCropRect;
    }

    /**
     * 初始化截取的矩形区域
     */
    private void initCrop() {
        int cameraWidth = isVertical?cameraManager.getCameraResolution().y:cameraManager.getCameraResolution().x;
        int cameraHeight = isVertical?cameraManager.getCameraResolution().x:cameraManager.getCameraResolution().y;

        /** 获取布局中扫描框的位置信息 */
        int[] location = new int[2];
        scanCropView.getLocationInWindow(location);

        int cropLeft = location[0];
        int cropTop = location[1] - getStatusBarHeight();

        int cropWidth = scanCropView.getWidth();
        int cropHeight = scanCropView.getHeight();

        /** 获取布局容器的宽高 */
        int containerWidth = scanContainer.getWidth();
        int containerHeight = scanContainer.getHeight();

        /** 计算最终截取的矩形的左上角顶点x坐标 */
        int x = cropLeft * cameraWidth / containerWidth;
        /** 计算最终截取的矩形的左上角顶点y坐标 */
        int y = cropTop * cameraHeight / containerHeight;

        /** 计算最终截取的矩形的宽度 */
        int width = cropWidth * cameraWidth / containerWidth;
        /** 计算最终截取的矩形的高度 */
        int height = cropHeight * cameraHeight / containerHeight;

        /** 生成最终的截取的矩形 */
        mCropRect = new Rect(x, y, width + x, height + y);
    }

    private int getStatusBarHeight() {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.capture_flash) {
            light();

        } else {
        }
    }

    private boolean flag;

    protected void light() {
        if (flag == true) {
            flag = false;
            // 开闪光灯
            cameraManager.openLight();
            mFlash.setBackgroundResource(R.drawable.flash_open);
        } else {
            flag = true;
            // 关闪光灯
            cameraManager.offLight();
            mFlash.setBackgroundResource(R.drawable.flash_default);
        }
    }

    public interface ReceiveResultText{
        void receiveText(String resultText);
    }
    private static ReceiveResultText receiveResultText;

    /**
     * 设置接收扫描结果的回调
     * @param res
     */
    public static void setReceiveResultText(ReceiveResultText res){
        receiveResultText = res;
    }

    private void resultHandlerText(String text){
        if (receiveResultText != null){
            //有回调就用回调返回
            receiveResultText.receiveText(text);
        }else {
            //没回调就用result方式放回到原activity中
            Intent intent = new Intent();
            intent.putExtra("result", text);
            setResult(1001, intent);
        }
        finish();
    }
}