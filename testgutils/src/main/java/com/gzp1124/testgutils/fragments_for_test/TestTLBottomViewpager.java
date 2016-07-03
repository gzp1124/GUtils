package com.gzp1124.testgutils.fragments_for_test;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.gzp1124.gutils.base.BaseFragment;
import com.gzp1124.lib_ui.tab_layout.TabLayoutBottomFragmentWithViewPager;
import com.gzp1124.testgutils.R;
import com.gzp1124.testgutils.linshi.Frag1;
import com.gzp1124.testgutils.linshi.Frag2;
import com.gzp1124.testgutils.linshi.Frag3;

/**
 * author：高志鹏 on 16/7/3 08:56
 * email:imbagaozp@163.com
 */
public class TestTLBottomViewpager extends BaseFragment {
    String [] ll = {"首页","新闻","个人"};
    Fragment[] fragments = {new Frag1(),new Frag2(),new Frag3()};
    private int[] mIconUnselectIds = {
            R.mipmap.tab_home_unselect, R.mipmap.tab_speech_unselect,
            R.mipmap.tab_contact_unselect, R.mipmap.tab_more_unselect};
    private int[] mIconSelectIds = {
            R.mipmap.tab_home_select, R.mipmap.tab_speech_select,
            R.mipmap.tab_contact_select, R.mipmap.tab_more_select};

    @Override
    protected void initViews() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.test_tl_bottom_viewpager;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TabLayoutBottomFragmentWithViewPager.setAllDatas(ll,fragments,mIconSelectIds,mIconUnselectIds);
    }


}
