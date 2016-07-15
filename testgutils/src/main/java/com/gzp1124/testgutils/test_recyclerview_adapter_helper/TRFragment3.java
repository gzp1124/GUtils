package com.gzp1124.testgutils.test_recyclerview_adapter_helper;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gzp1124.testgutils.R;
import com.gzp1124.testgutils.test_recyclerview_adapter_helper.adapter.SectionAdapter;
import com.gzp1124.testgutils.test_recyclerview_adapter_helper.bean.MySection;
import com.gzp1124.testgutils.test_recyclerview_adapter_helper.data.DataServer;

import java.util.List;

/**
 * author：高志鹏 on 16/7/15 17:27
 * email:imbagaozp@163.com
 */
public class TRFragment3 extends Fragment implements BaseQuickAdapter.OnRecyclerViewItemClickListener {
    private RecyclerView mRecyclerView;
    private List<MySection> mData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.pr_fragment3,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mData = DataServer.getSampleData();
        SectionAdapter sectionAdapter = new SectionAdapter(R.layout.item_section_content, R.layout.def_section_head, mData);
        sectionAdapter.setOnRecyclerViewItemClickListener(this);
        sectionAdapter.setOnRecyclerViewItemChildClickListener(new BaseQuickAdapter.OnRecyclerViewItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(getContext(), "onItemChildClick", Toast.LENGTH_LONG).show();
            }
        });
        mRecyclerView.setAdapter(sectionAdapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        MySection mySection = mData.get(position);
        if (mySection.isHeader)
            Toast.makeText(getContext(), mySection.header, Toast.LENGTH_LONG).show();
        else
            Toast.makeText(getContext(), mySection.t.getName(), Toast.LENGTH_LONG).show();
    }
}
