package com.gzp1124.lib_ui.have_header_viewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

public class HVTouchCallbackLayout extends FrameLayout {

    public void setTouchEventListener(TouchEventListener touchEventListener) {
        mTouchEventListener = touchEventListener;
    }

    private TouchEventListener mTouchEventListener;

    public HVTouchCallbackLayout(Context context) {
        super(context);
    }

    public HVTouchCallbackLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HVTouchCallbackLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public interface TouchEventListener {
        public boolean onLayoutInterceptTouchEvent(MotionEvent ev);

        public boolean onLayoutTouchEvent(MotionEvent ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        if (mTouchEventListener != null) {
            return mTouchEventListener.onLayoutInterceptTouchEvent(ev);
        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (mTouchEventListener != null) {
            return mTouchEventListener.onLayoutTouchEvent(event);
        }

        return super.onTouchEvent(event);
    }
}
