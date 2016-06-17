package com.gzp1124.gutils.three_ui_widget.have_header_viewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.SparseArrayCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.gzp1124.gutils.R;

/**
 * author：高志鹏 on 16/6/1 16:41
 * email:imbagaozp@163.com
 */
public class HVMyScrollFragment extends HVFragment implements HVScrollableListener {
    private ScrollView scrollView;

    public HVMyScrollFragment(SparseArrayCompat<HVScrollableListener> mScrollableListenerArrays, HVViewPagerHeaderHelper.MyScrollable myScrollable, int po) {
        super(mScrollableListenerArrays, myScrollable, po);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.have_header_viewpager_fragment_scroll_view,null );
        scrollView = (ScrollView) view.findViewById(R.id.scrollview);
        View contentView = null;
        if (fullScrollview != null){
            contentView = fullScrollview.fullSCrollview();
        }
        if (contentView == null){
            TextView textView = new TextView(view.getContext());
            textView.setText("scrollview为设置内容，调用initContentView()进行设置");
            scrollView.addView(textView);
        }else {
            scrollView.addView(contentView);
        }
        return view;
    }

    private HVScrollViewDelegate mScrollViewDelegate = new HVScrollViewDelegate();

    @Override
    public boolean isViewBeingDragged(MotionEvent event) {
        return mScrollViewDelegate.isViewBeingDragged(event,scrollView);
    }

    /**
     * 提供外部进行scrollview内容自定义的方法
     */
    public interface FullScrollview extends FullContentView{
        View fullSCrollview();
    }
    private FullScrollview fullScrollview;
    public void setFullScrollview(FullScrollview fScrollview){
        fullScrollview = fScrollview;
    }
}
