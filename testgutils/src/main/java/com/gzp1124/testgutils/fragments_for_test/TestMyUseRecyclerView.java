package com.gzp1124.testgutils.fragments_for_test;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gzp1124.gutils.base.BaseFragment;
import com.gzp1124.testgutils.R;
import com.gzp1124.testgutils.test_recyclerview.LayoutAdapter;
import com.gzp1124.testgutils.test_recyclerview_adapter_helper.adapter.QuickAdapter;
import com.gzp1124.testgutils.test_recyclerview_adapter_helper.animation.CustomAnimation;
import com.gzp1124.testgutils.test_recyclerview_adapter_helper.bean.Status;

import org.lucasr.twowayview.core.ItemClickSupport;
import org.lucasr.twowayview.widget.DividerItemDecoration;
import org.lucasr.twowayview.widget.TwoWayView;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_SETTLING;

/**
 * 结合lib_recyclerview  和  lib_recyclerview_adapter_helper
 *  的实际使用案例
 * author：高志鹏 on 16/7/16 12:37
 * email:imbagaozp@163.com
 */
public class TestMyUseRecyclerView extends Fragment {
    private static final String ARG_LAYOUT_ID = "layout_id";

    private TwoWayView mRecyclerView;
    private TextView mPositionText;
    private TextView mCountText;
    private TextView mStateText;
    private Toast mToast;

    private int mLayoutId = LayoutAdapter.LIST;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment3,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Activity activity = getActivity();

        mToast = Toast.makeText(activity, "", Toast.LENGTH_SHORT);
        mToast.setGravity(Gravity.CENTER, 0, 0);

        mRecyclerView = (TwoWayView) view.findViewById(R.id.list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLongClickable(true);

        mPositionText = (TextView) view.getRootView().findViewById(R.id.position);
        mCountText = (TextView) view.getRootView().findViewById(R.id.count);

        mStateText = (TextView) view.getRootView().findViewById(R.id.state);
        updateState(SCROLL_STATE_IDLE);

        final ItemClickSupport itemClick = ItemClickSupport.addTo(mRecyclerView);

        itemClick.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View child, int position, long id) {
                mToast.setText("Item clicked: " + position);
                mToast.show();
            }
        });

        itemClick.setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(RecyclerView parent, View child, int position, long id) {
                mToast.setText("Item long pressed: " + position);
                mToast.show();
                return true;
            }
        });

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {
                updateState(scrollState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                mPositionText.setText("First: " + mRecyclerView.getFirstVisiblePosition());
                mCountText.setText("Count: " + mRecyclerView.getChildCount());
            }
        });

        final Drawable divider = getResources().getDrawable(R.drawable.divider);
        //添加分割线
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(divider));

//        useOldAdapter();
        useNewAdapter(view);
    }

    private void useNewAdapter(View view) {
        initAdapter();
        initMenu(view);
    }

    private void useOldAdapter() {
        mRecyclerView.setAdapter(new LayoutAdapter(getActivity(), mRecyclerView, mLayoutId));
    }

    private void updateState(int scrollState) {
        String stateName = "Undefined";
        switch(scrollState) {
            case SCROLL_STATE_IDLE:
                stateName = "停止";
                break;

            case SCROLL_STATE_DRAGGING:
                stateName = "拖动";
                break;

            case SCROLL_STATE_SETTLING:
                stateName = "滑动中";
                break;
        }

        mStateText.setText(stateName);
    }

    //=====================adapter设置

    private QuickAdapter mQuickAdapter;
    private void initAdapter() {
        mQuickAdapter = new QuickAdapter();
        mQuickAdapter.openLoadAnimation();
        //原先这里的点击事件删除，使用lib_recyclerview中的点击事件，见75行
        mRecyclerView.setAdapter(mQuickAdapter);
    }

    private void initMenu(View view) {

        //"AlphaIn", "ScaleIn", "SlideInBottom", "SlideInLeft", "SlideInRight", "Custom"
        //切换动画表示，下面共有6种动画
        int position = 2;
        switch (position) {
            case 0:
                mQuickAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
                break;
            case 1:
                mQuickAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
                break;
            case 2:
                mQuickAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
                break;
            case 3:
                mQuickAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
                break;
            case 4:
                mQuickAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);
                break;
            case 5:
                mQuickAdapter.openLoadAnimation(new CustomAnimation());
                break;
            default:
                break;
        }
        mRecyclerView.setAdapter(mQuickAdapter);

        //是否一直展示动画效果
        int alwayshow = 1;
        switch (alwayshow) {
            case 0:
                mQuickAdapter.isFirstOnly(true);
                break;
            case 1:
                mQuickAdapter.isFirstOnly(false);
                break;
            default:
                break;
        }

    }

}
