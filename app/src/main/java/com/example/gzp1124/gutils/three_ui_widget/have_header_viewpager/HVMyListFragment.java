package com.example.gzp1124.gutils.three_ui_widget.have_header_viewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.SparseArrayCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.gzp1124.gutils.R;


/**
 * author：高志鹏 on 16/6/2 11:36
 * email:imbagaozp@163.com
 */
public class HVMyListFragment extends HVFragment  implements HVScrollableListener {
    private ListView mListview;

    public HVMyListFragment(SparseArrayCompat<HVScrollableListener> mScrollableListenerArrays, HVViewPagerHeaderHelper.MyScrollable myScrollable, int po) {
        super(mScrollableListenerArrays, myScrollable, po);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.have_header_viewpager_fragment_list_view,null);
        mListview = (ListView) view.findViewById(R.id.list_item);
        mListview.setEmptyView(view.findViewById(R.id.empty));
        if (fullListView != null){
            fullListView.fullListView(mListview);
        }

        return view;
    }

    private HVAbsListViewDelegate mListviewDelegate = new HVAbsListViewDelegate();
    @Override
    public boolean isViewBeingDragged(MotionEvent event) {
        return mListviewDelegate.isViewBeingDragged(event,mListview);
    }

    /**
     * 提供给外部进行listview内容自定义的方法
     */
    public interface FullListView extends FullContentView{
        void fullListView(ListView listView);
    }
    private FullListView fullListView;
    public void setFullListView(FullListView flistView){
        fullListView = flistView;
    }

}
