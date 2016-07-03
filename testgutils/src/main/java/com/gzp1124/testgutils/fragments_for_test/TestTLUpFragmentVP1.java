package com.gzp1124.testgutils.fragments_for_test;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.gzp1124.gutils.base.BaseFragment;
import com.gzp1124.lib_ui.tab_layout.TabLayoutUpFragmentVP1;
import com.gzp1124.testgutils.R;
import com.gzp1124.testgutils.linshi.Frag1;
import com.gzp1124.testgutils.linshi.Frag2;
import com.gzp1124.testgutils.linshi.Frag3;

/**
 * 顶部导航，简单样式，结合viewpager
 * 使用简单
 *
 * tab layout 的具体使用案例,使用lib_ui中封装好的 TestTabLayoutFragment2
 *
 * author：高志鹏 on 16/7/2 21:50
 * email:imbagaozp@163.com
 */
public class TestTLUpFragmentVP1 extends BaseFragment {
    String [] ll = {"首页","新闻","个人"};
    Fragment[] fragments = {new Frag1(),new Frag2(),new Frag3()};

    @Override
    protected void initViews() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.test_tl_up_fragment_vp_1;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TabLayoutUpFragmentVP1.setAllDatas(ll,fragments);
    }
}
