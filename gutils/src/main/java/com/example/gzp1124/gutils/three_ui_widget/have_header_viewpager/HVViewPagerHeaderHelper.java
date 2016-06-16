package com.example.gzp1124.gutils.three_ui_widget.have_header_viewpager;

import android.content.Context;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

public class HVViewPagerHeaderHelper {

    private VelocityTracker mVelocityTracker;

    private boolean mIsBeingMove;

    private float mInitialMotionY;
    private float mInitialMotionX;
    private float mLastMotionY;
    private boolean mHandlingTouchEventFromDown;

    private boolean mIsHeaderExpand = true;

    private int mTouchSlop;

    private int mMinimumFlingVelocity;
    private int mMaximumFlingVelocity;

    private int mHeaderHeight;


    SparseArrayCompat<HVScrollableListener> mScrollableListenerArrays;
    ViewPager mViewPager;
    View mHeaderLayoutView;

    int mTabHeight; //tab的高度
    int mGHeaderHeight;     //header的高度
    private static final long  DEFAULT_DURATION = 300L;
    private static final float DEFAULT_DAMPING  = 1.5f;
    Interpolator mInterpolator;
    HVTouchCallbackLayout touchCallbackLayout;
    HVViewPagerHeaderHelper mVPHHelper;

    /**
     * 带有头的viewpager必须
     * @param context
     * @param mScrollableListenerArrays
     * @param mViewPager viewpager
     * @param mHeaderLayoutView headerview
     * @param mTabHeight  tab的高度
     * @param mGHeaderHeight header的高度
     * @param touchCallbackLayout 触摸回调
     */
    public HVViewPagerHeaderHelper(@NonNull Context context, @NonNull SparseArrayCompat<HVScrollableListener> mScrollableListenerArrays
            , @NonNull ViewPager mViewPager, @NonNull View mHeaderLayoutView, @NonNull int mTabHeight, @NonNull int mGHeaderHeight,
                                   @NonNull HVTouchCallbackLayout touchCallbackLayout) {
        mVPHHelper = this;
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        mTouchSlop = viewConfiguration.getScaledTouchSlop();
        mMinimumFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        mMaximumFlingVelocity = viewConfiguration.getScaledMaximumFlingVelocity();

        this.mScrollableListenerArrays = mScrollableListenerArrays;
        this.mViewPager = mViewPager;
        this.mHeaderLayoutView = mHeaderLayoutView;
        this.mGHeaderHeight = mGHeaderHeight;
        this.mInterpolator = new DecelerateInterpolator();
        this.touchCallbackLayout = touchCallbackLayout;
        this.mTabHeight = mTabHeight;

        init();
    }

    private void init() {
        initMyScrollable(mScrollableListenerArrays);
        if (null != touchCallbackLayout){
            //设置TouchEventListener才能上滑动隐藏头部
            touchCallbackLayout.setTouchEventListener(new mTouchCallbackListener());
        }
        ViewCompat.setTranslationY(mViewPager, mGHeaderHeight);
    }


    public boolean onLayoutInterceptTouchEvent(MotionEvent event, int headerHeight) {
        mHeaderHeight = headerHeight;

        final float x = event.getX(), y = event.getY();
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:

                boolean isViewBeingDragged = isViewBeingDragged(event);

                if (isViewBeingDragged && !mIsHeaderExpand || mIsHeaderExpand) {

                    if (mIsHeaderExpand && y < headerHeight) {
                        return mIsBeingMove;
                    }

                    mInitialMotionX = x;
                    mInitialMotionY = y;
                }

                break;
            case MotionEvent.ACTION_MOVE:
                if (mInitialMotionY > 0f && !mIsBeingMove) {
                    final float yDiff = y - mInitialMotionY;
                    final float xDiff = x - mInitialMotionX;
                    if ((!mIsHeaderExpand && yDiff > mTouchSlop)  // header fold , pull
                            || (mIsHeaderExpand && yDiff < 0))// header expand, push
                    {
                        if (Math.abs(yDiff) > Math.abs(xDiff)) {
                            mIsBeingMove = true;
                            onMoveStarted(y);
                        }
                    }
                }

                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (mIsBeingMove) {
                    onMoveEnded(false, 0f);
                }
                resetTouch();
                break;
        }

        return mIsBeingMove;
    }

    public boolean onLayoutTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mHandlingTouchEventFromDown = true;
        }

        if (mHandlingTouchEventFromDown) {
            if (mIsBeingMove) {
                mLastMotionY = event.getY();
            } else {
                onLayoutInterceptTouchEvent(event, mHeaderHeight);
                return true;
            }
        }

        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);

        int action = event.getAction();
        final int count = event.getPointerCount();

        switch (action) {
            case MotionEvent.ACTION_MOVE:
                final float y = event.getY();
                if (mIsBeingMove && y != mLastMotionY) {
                    final float yDx = mLastMotionY == -1 ? 0 : y - mLastMotionY;
                    onMove(y, yDx);
                    mLastMotionY = y;
                }
                break;

            case MotionEvent.ACTION_POINTER_UP:
                // Check the dot product of current velocities.
                // If the pointer that left was opposing another velocity vector, clear.
                mVelocityTracker.computeCurrentVelocity(1000, mMaximumFlingVelocity);
                final int upIndex = event.getActionIndex();
                final int id1 = event.getPointerId(upIndex);
                final float x1 = mVelocityTracker.getXVelocity(id1);
                final float y1 = mVelocityTracker.getYVelocity(id1);
                for (int i = 0; i < count; i++) {
                    if (i == upIndex) continue;
                    final int id2 = event.getPointerId(i);
                    final float vx = x1 * mVelocityTracker.getXVelocity(id2);
                    final float vy = y1 * mVelocityTracker.getYVelocity(id2);

                    final float dot = vx + vy;
                    if (dot < 0) {
                        mVelocityTracker.clear();
                        break;
                    }
                }

                break;

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (mIsBeingMove) {

                    boolean isFling = false;
                    float velocityY = 0;

                    if (action == MotionEvent.ACTION_UP) {
                        final VelocityTracker velocityTracker = mVelocityTracker;
                        final int pointerId = event.getPointerId(0);
                        velocityTracker.computeCurrentVelocity(1000, mMaximumFlingVelocity);
                        velocityY = velocityTracker.getYVelocity(pointerId);
                        if ((Math.abs(velocityY) > mMinimumFlingVelocity)) {
                            isFling = true;
                        }
                    }

                    onMoveEnded(isFling, velocityY);
                }
                resetTouch();

                break;
        }

        return true;
    }

    private void resetTouch() {
        mIsBeingMove = false;
        mHandlingTouchEventFromDown = false;
        mInitialMotionY = mLastMotionY = -1f;
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    public void setHeaderExpand(boolean isHeaderExpand) {
        this.mIsHeaderExpand = isHeaderExpand;
    }

    public float getInitialMotionY() {
        return mInitialMotionY;
    }

    public float getLastMotionY() {
        return mLastMotionY;
    }



    long downtime=-1;
    int countPushEnd=0,countPullEnd=0;
    private boolean isViewBeingDragged(MotionEvent event) {
        HVFragment helper = (HVFragment) mScrollableListenerArrays.valueAt(mViewPager.getCurrentItem());
        if (helper != null){
            return helper.isViewBeingDragged(event);
        }else {
            return false;
        }
    }

    private void onMoveStarted(float y) {}

    private void onMove(float y, float yDx) {
        float headerTranslationY = ViewCompat.getTranslationY(mHeaderLayoutView) + yDx;
        if (headerTranslationY >= 0) { // pull end
            headerExpand(0L);

            //Log.d("kaede", "pull end");
            if(countPullEnd>=1){
                if (countPullEnd==1){
                    downtime= SystemClock.uptimeMillis();
                    simulateTouchEvent(mViewPager,downtime, SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 250, y+mGHeaderHeight);
                }
                simulateTouchEvent(mViewPager,downtime, SystemClock.uptimeMillis(), MotionEvent.ACTION_MOVE, 250, y+mGHeaderHeight);
            }
            countPullEnd++;

        } else if (headerTranslationY <= -mGHeaderHeight) { // push end
            headerFold(0L);

            //Log.d("kaede", "push end");
            //Log.d("kaede", "kaede onMove y="+y+",yDx="+yDx+",headerTranslationY="+headerTranslationY);
            if(countPushEnd>=1){
                if (countPushEnd==1){
                    downtime= SystemClock.uptimeMillis();
                    simulateTouchEvent(mViewPager,downtime, SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 250, y+mGHeaderHeight);
                }
                simulateTouchEvent(mViewPager,downtime, SystemClock.uptimeMillis(), MotionEvent.ACTION_MOVE, 250, y+mGHeaderHeight);
            }
            countPushEnd++;

        } else {

            //Log.d("kaede", "ing");
        	/*if(!isHasDispatchDown3){
        	simulateTouchEvent(mViewPager,SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_CANCEL, 250, y+mGHeaderHeight);
        	isHasDispatchDown3=true;
        	}*/

            ViewCompat.animate(mHeaderLayoutView)
                    .translationY(headerTranslationY)
                    .setDuration(0)
                    .start();
            ViewCompat.animate(mViewPager)
                    .translationY(headerTranslationY + mGHeaderHeight)
                    .setDuration(0)
                    .start();
        }
    }

    private void onMoveEnded(boolean isFling, float flingVelocityY) {

        //Log.d("kaede", "move end");
        countPushEnd = countPullEnd=0;

        float headerY = ViewCompat.getTranslationY(mHeaderLayoutView); // 0到负数
        if (headerY == 0 || headerY == -mGHeaderHeight) {
            return;
        }

        if (getInitialMotionY() - getLastMotionY()
                < -mTouchSlop) {  // pull > mTouchSlop = expand
            headerExpand(headerMoveDuration(true, headerY, isFling, flingVelocityY));
        } else if (getInitialMotionY()
                - getLastMotionY()
                > mTouchSlop) { // push > mTouchSlop = fold
            headerFold(headerMoveDuration(false, headerY, isFling, flingVelocityY));
        } else {
            if (headerY > -mGHeaderHeight / 2f) {  // headerY > header/2 = expand
                headerExpand(headerMoveDuration(true, headerY, isFling, flingVelocityY));
            } else { // headerY < header/2= fold
                headerFold(headerMoveDuration(false, headerY, isFling, flingVelocityY));
            }
        }
    }

    //--------头部动作区，需要的mHeaderLayoutView ，mViewPager  mInterpolator mHeaderHeight---------------
    //头部移动
    private long headerMoveDuration(boolean isExpand, float currentHeaderY, boolean isFling,
                                    float velocityY) {

        long defaultDuration = DEFAULT_DURATION;

        if (isFling) {

            float distance = isExpand ? Math.abs(mGHeaderHeight) - Math.abs(currentHeaderY)
                    : Math.abs(currentHeaderY);
            velocityY = Math.abs(velocityY) / 1000;

            defaultDuration = (long) (distance / velocityY * DEFAULT_DAMPING);

            defaultDuration =
                    defaultDuration > DEFAULT_DURATION ? DEFAULT_DURATION : defaultDuration;
        }

        return defaultDuration;
    }
    //头部折叠
    private void headerFold(long duration) {
        ViewCompat.animate(mHeaderLayoutView)
                .translationY(-mGHeaderHeight)
                .setDuration(duration)
                .setInterpolator(mInterpolator)
                .start();

        ViewCompat.animate(mViewPager).translationY(0).
                setDuration(duration).setInterpolator(mInterpolator).start();

        setHeaderExpand(false);
    }

    //头部扩大
    private void headerExpand(long duration) {
        ViewCompat.animate(mHeaderLayoutView)
                .translationY(0)
                .setDuration(duration)
                .setInterpolator(mInterpolator)
                .start();

        ViewCompat.animate(mViewPager)
                .translationY(mGHeaderHeight)
                .setDuration(duration)
                .setInterpolator(mInterpolator)
                .start();
        setHeaderExpand(true);
    }

    //模拟触摸事件
    private void simulateTouchEvent(View dispatcher, long downTime, long eventTime, int action, float x, float y) {
        MotionEvent motionEvent = MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), action, x, y, 0);
        try {
            dispatcher.dispatchTouchEvent(motionEvent);
        } catch (Throwable e) {
        } finally {
            motionEvent.recycle();
        }
    }

    /**
     * @see HVTouchCallbackLayout.TouchEventListener 的回调类
     */
    private class mTouchCallbackListener implements HVTouchCallbackLayout.TouchEventListener{
        @Override
        public boolean onLayoutInterceptTouchEvent(MotionEvent event) {
            return mVPHHelper.onLayoutInterceptTouchEvent(event,
                    mTabHeight + mGHeaderHeight);
        }

        @Override
        public boolean onLayoutTouchEvent(MotionEvent event) {
            return mVPHHelper.onLayoutTouchEvent(event);
        }
    }

    //----------------
    private MyScrollable myScrollable;
    private void initMyScrollable(SparseArrayCompat<HVScrollableListener> mScrollableListenerArrays){
        if (null == myScrollable) {
            myScrollable = new MyScrollable(mScrollableListenerArrays);
        }
    }

    /**
     * 获取ScrollableFragmentListener代理类
     * @return
     */
    public MyScrollable getMyScrollable(){
        return myScrollable;
    }
    public class MyScrollable implements HVScrollableFragmentListener {
        private SparseArrayCompat<HVScrollableListener> mScrollableListenerArrays;
        public MyScrollable(SparseArrayCompat<HVScrollableListener> mScrollableListenerArrays) {
            this.mScrollableListenerArrays = mScrollableListenerArrays;
        }

        @Override
        public void onFragmentAttached(HVScrollableListener listener, int position) {
            mScrollableListenerArrays.put(position, listener);
        }

        @Override
        public void onFragmentDetached(HVScrollableListener listener, int position) {
            mScrollableListenerArrays.remove(position);
        }
    }
}
