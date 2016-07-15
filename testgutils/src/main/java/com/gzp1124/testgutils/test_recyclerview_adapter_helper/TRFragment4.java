package com.gzp1124.testgutils.test_recyclerview_adapter_helper;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gzp1124.testgutils.R;
import com.gzp1124.testgutils.test_recyclerview_adapter_helper.adapter.QuickAdapter;

/**
 * author：高志鹏 on 16/7/15 17:27
 * email:imbagaozp@163.com
 */
public class TRFragment4 extends Fragment {
    private RecyclerView mRecyclerView;
    private QuickAdapter mQuickAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.pr_fragment4,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        initAdapter();
    }

    private void initAdapter() {
        mQuickAdapter = new QuickAdapter(0);
        View emptyView = getActivity().getLayoutInflater().inflate(R.layout.empty_view, (ViewGroup) mRecyclerView.getParent(), false);
        mQuickAdapter.setEmptyView(emptyView);
        View view = getActivity().getLayoutInflater().inflate(R.layout.head_view, (ViewGroup) mRecyclerView.getParent(), false);
        mRecyclerView.setAdapter(mQuickAdapter);
    }
}
