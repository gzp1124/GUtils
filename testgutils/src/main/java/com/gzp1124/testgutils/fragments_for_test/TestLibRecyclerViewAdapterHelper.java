package com.gzp1124.testgutils.fragments_for_test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.gzp1124.gutils.base.BaseFragment;
import com.gzp1124.lib_ui.tab_layout.TabLayoutUpFragmentVP1;
import com.gzp1124.testgutils.R;
import com.gzp1124.testgutils.test_recyclerview.Fragment1;
import com.gzp1124.testgutils.test_recyclerview.Fragment2;
import com.gzp1124.testgutils.test_recyclerview.Fragment3;
import com.gzp1124.testgutils.test_recyclerview.Fragment4;
import com.gzp1124.testgutils.test_recyclerview_adapter_helper.TRFragment1;
import com.gzp1124.testgutils.test_recyclerview_adapter_helper.TRFragment2;
import com.gzp1124.testgutils.test_recyclerview_adapter_helper.TRFragment3;
import com.gzp1124.testgutils.test_recyclerview_adapter_helper.TRFragment4;

/**
 * author：高志鹏 on 16/7/15 17:24
 * email:imbagaozp@163.com
 */
public class TestLibRecyclerViewAdapterHelper extends BaseFragment {
    Fragment[] fragments = new Fragment[]{new TRFragment1(),new TRFragment2(),new TRFragment3(),new TRFragment4()};
    String [] titles = {"加载动画","refresh 添加头","Section 样式","空布局"};
    @Override
    protected int getLayoutId() {
        return R.layout.test_lib_recyclerview_adapter_helper;
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
