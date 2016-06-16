package com.example.gzp1124.gutils.three_ui_widget.have_header_viewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.SparseArrayCompat;
import android.view.MotionEvent;
import android.widget.ListView;
import android.widget.ScrollView;

/**
 * author：高志鹏 on 16/6/2 13:40
 * email:imbagaozp@163.com
 */
public abstract  class HVFragment extends Fragment implements HVScrollableListener{
    protected SparseArrayCompat<HVScrollableListener> mScrollableListenerArrays;
    protected HVViewPagerHeaderHelper.MyScrollable myScrollable;
    protected int postion;

    protected ListView mListview;
    protected ScrollView scrollView;

    public HVFragment(SparseArrayCompat<HVScrollableListener> mScrollableListenerArrays, HVViewPagerHeaderHelper.MyScrollable myScrollable, int po) {
        this.mScrollableListenerArrays = mScrollableListenerArrays;
        this.myScrollable = myScrollable;
        postion = po;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (myScrollable != null)
            myScrollable.onFragmentAttached(this,postion);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (myScrollable != null)
            myScrollable.onFragmentDetached(this,postion);
    }

    @Override
    public abstract boolean isViewBeingDragged(MotionEvent event);

    /**
     * 该接口为标识接口，具体使用子类
     */
    public interface FullContentView{

    }
}
