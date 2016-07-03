package com.gzp1124.testgutils.fragments_for_test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.gzp1124.gutils.base.BaseFragment;
import com.gzp1124.lib_ui.tab_layout.TabLayoutUpFragmentF;
import com.gzp1124.lib_ui.tab_layout.TabLayoutUpFragmentVP2;
import com.gzp1124.testgutils.R;
import com.gzp1124.testgutils.linshi.Frag1;
import com.gzp1124.testgutils.linshi.Frag2;
import com.gzp1124.testgutils.linshi.Frag3;

/**
 * author：高志鹏 on 16/7/3 14:42
 * email:imbagaozp@163.com
 */
public class TestTLUpFragmentF extends BaseFragment {
    String [] ll = {"首页","新闻","个人"};
    Fragment[] fragments = {new Frag1(),new Frag2(),new Frag3()};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TabLayoutUpFragmentF.setAllDatas(ll,fragments);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.test_tl_up_fragment_f;
    }

    @Override
    protected void initViews() {

    }
}
