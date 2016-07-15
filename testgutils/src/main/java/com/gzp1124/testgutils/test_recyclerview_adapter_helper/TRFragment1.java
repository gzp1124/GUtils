package com.gzp1124.testgutils.test_recyclerview_adapter_helper;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gzp1124.testgutils.R;
import com.gzp1124.testgutils.test_recyclerview_adapter_helper.adapter.QuickAdapter;
import com.gzp1124.testgutils.test_recyclerview_adapter_helper.animation.CustomAnimation;
import com.gzp1124.testgutils.test_recyclerview_adapter_helper.bean.Status;

/**
 * author：高志鹏 on 16/7/15 17:27
 * email:imbagaozp@163.com
 */
public class TRFragment1 extends Fragment {
    private RecyclerView mRecyclerView;
    private QuickAdapter mQuickAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.pr_fragment1,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        initAdapter();
        initMenu(view);
    }

    private void initAdapter() {
        mQuickAdapter = new QuickAdapter();
        mQuickAdapter.openLoadAnimation();
        mQuickAdapter.setOnRecyclerViewItemChildClickListener(new BaseQuickAdapter.OnRecyclerViewItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                String content = null;
                Status status = (Status) adapter.getItem(position);
                switch (view.getId()) {
                    case R.id.tweetAvatar:
                        content = "img:" + status.getUserAvatar();
                        break;
                    case R.id.tweetName:
                        content = "name:" + status.getUserName();
                        break;
                }
                Toast.makeText(getContext(), content, Toast.LENGTH_LONG).show();
            }
        });
        mRecyclerView.setAdapter(mQuickAdapter);
    }

    private void initMenu(View view) {

        //"AlphaIn", "ScaleIn", "SlideInBottom", "SlideInLeft", "SlideInRight", "Custom"
        //切换动画表示，下面共有6种动画
        int position = 1;
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
