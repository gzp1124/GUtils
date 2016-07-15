package com.gzp1124.testgutils.fragments_for_test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.gzp1124.gutils.base.BaseFragment;
import com.gzp1124.lib_ui.tab_layout.TabLayoutUpFragmentF;
import com.gzp1124.lib_ui.tab_layout.TabLayoutUpFragmentVP1;
import com.gzp1124.testgutils.R;
import com.gzp1124.testgutils.test_recyclerview.Fragment1;
import com.gzp1124.testgutils.test_recyclerview.Fragment2;
import com.gzp1124.testgutils.test_recyclerview.Fragment3;
import com.gzp1124.testgutils.test_recyclerview.Fragment4;

/**
 * 测试 lib_recyclerview
 * author：高志鹏 on 16/7/15 13:08
 * email:imbagaozp@163.com
 */
public class TestLibRecyclerViewFragment extends BaseFragment{
    //Fragment1  Fragment2  Fragment3  Fragment4类除了布局不同，其他都相同
    Fragment[] fragments = new Fragment[]{new Fragment1(),new Fragment2(),new Fragment3(),new Fragment4()};
    String [] titles = {"list","grid","staggered_grid","spannable_grid"};
    @Override
    protected int getLayoutId() {
        return R.layout.test_lib_recyclerview_fragment;
    }

    @Override
    protected void initViews() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TabLayoutUpFragmentVP1.setAllDatas(titles,fragments);
    }
}
