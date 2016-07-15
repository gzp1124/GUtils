package com.gzp1124.testgutils.test_recyclerview_adapter_helper;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gzp1124.testgutils.R;
import com.gzp1124.testgutils.test_recyclerview_adapter_helper.adapter.QuickAdapter;
import com.gzp1124.testgutils.test_recyclerview_adapter_helper.data.DataServer;

/**
 * author：高志鹏 on 16/7/15 17:27
 * email:imbagaozp@163.com
 */
public class TRFragment2 extends Fragment implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView mRecyclerView;
    private QuickAdapter mQuickAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private static final int TOTAL_COUNTER = 18;

    private static final int PAGE_SIZE = 6;

    private int delayMillis = 1000;

    private int mCurrentCounter = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.pr_fragment2,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_list);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        initAdapter();
        addHeadView();
        mRecyclerView.setAdapter(mQuickAdapter);
    }


    private void addHeadView() {
        View headView = getActivity().getLayoutInflater().inflate(R.layout.pr_head_view, (ViewGroup) mRecyclerView.getParent(), false);
        ((TextView) headView.findViewById(R.id.tv)).setText("click use custom loading view");
        final View customLoading = getActivity().getLayoutInflater().inflate(R.layout.pr_custom_loading, (ViewGroup) mRecyclerView.getParent(), false);
        headView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQuickAdapter.setLoadingView(customLoading);
                mRecyclerView.setAdapter(mQuickAdapter);
                Toast.makeText(getContext(), "use ok!", Toast.LENGTH_LONG).show();
            }
        });
        mQuickAdapter.addHeaderView(headView);
        //mQuickAdapter.addFooterView(view);
    }

    @Override
    public void onLoadMoreRequested() {
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                if (mCurrentCounter >= TOTAL_COUNTER) {
                    mQuickAdapter.notifyDataChangedAfterLoadMore(false);
                    View view = getActivity().getLayoutInflater().inflate(R.layout.pr_not_loading, (ViewGroup) mRecyclerView.getParent(), false);
                    mQuickAdapter.addFooterView(view);
                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mQuickAdapter.notifyDataChangedAfterLoadMore(DataServer.getSampleData(PAGE_SIZE), true);
                            mCurrentCounter = mQuickAdapter.getData().size();
                        }
                    }, delayMillis);
                }
            }


        });
    }


    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mQuickAdapter.setNewData(DataServer.getSampleData(PAGE_SIZE));
                mQuickAdapter.openLoadMore(PAGE_SIZE, true);
                mCurrentCounter = PAGE_SIZE;
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, delayMillis);
    }

    private void initAdapter() {
        mQuickAdapter = new QuickAdapter(PAGE_SIZE);
        mQuickAdapter.openLoadAnimation();
        mRecyclerView.setAdapter(mQuickAdapter);
        mCurrentCounter = mQuickAdapter.getData().size();
        mQuickAdapter.setOnLoadMoreListener(this);
        mQuickAdapter.openLoadMore(PAGE_SIZE, true);//or call mQuickAdapter.setPageSize(PAGE_SIZE);  mQuickAdapter.openLoadMore(true);
        addHeadView();
        mQuickAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getContext(), Integer.toString(position), Toast.LENGTH_LONG).show();
            }
        });
    }
}