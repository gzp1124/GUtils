package com.gzp1124.gutils.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.gzp1124.gutils.base.BaseApplication;

import java.util.Timer;
import java.util.TimerTask;

/**
 * author：高志鹏 on 16/7/2 13:01
 * email:imbagaozp@163.com
 */
public class GViewUtil {
    public static boolean PRE_HC;
    static {
        PRE_HC = Build.VERSION.SDK_INT < 11;
    }

    /**
     * 替代findviewById方法
     */
    public static <T extends View> T find(View view, int id) {
        return (T) view.findViewById(id);
    }

    /**
     * ViewHolder简洁写法,避免适配器中重复定义ViewHolder,减少代码量 用法:
     * if (convertView == null){
     * 	convertView = View.inflate(context, R.layout.ad_demo, null);
     * }
     * TextView tv_demo = ViewHolderUtils.get(convertView, R.id.tv_demo);
     * ImageView iv_demo = ViewHolderUtils.get(convertView, R.id.iv_demo);
     */
    public static <T extends View> T hold(View view, int id) {
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();

        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }

        View childView = viewHolder.get(id);

        if (childView == null) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }

    public static void hideAnimatedView(View view) {
        if (PRE_HC && view != null)
            view.setPadding(view.getWidth(), 0, 0, 0);
    }

    public static void hideSoftKeyboard(View view) {
        if (view == null)
            return;
        ((InputMethodManager) BaseApplication.gContext.getSystemService(
                Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                view.getWindowToken(), 0);
    }

    /**如果软键盘显示处理，一段时间后强制隐藏*/
    public static void hideSoftKey() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                InputMethodManager imm = (InputMethodManager) BaseApplication.gContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                boolean isOpen = imm.isActive();
                if (isOpen) {
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }, 150);
    }

    public static void showSoftKeyboard(Dialog dialog) {
        dialog.getWindow().setSoftInputMode(4);
    }

    public static void showSoftKeyboard(View view) {
        ((InputMethodManager) BaseApplication.gContext.getSystemService(
                Context.INPUT_METHOD_SERVICE)).showSoftInput(view,
                InputMethodManager.SHOW_FORCED);
    }

    public static DisplayMetrics getDisplayMetrics() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((WindowManager) BaseApplication.gContext.getSystemService(
                Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(
                displaymetrics);
        return displaymetrics;
    }

    public static float pxToDp(float px) {
        return px / (getDisplayMetrics().densityDpi / 160F);
    }

    public static float dpToPx(float dp) {
        return dp * (getDisplayMetrics().densityDpi / 160F);
    }


}
