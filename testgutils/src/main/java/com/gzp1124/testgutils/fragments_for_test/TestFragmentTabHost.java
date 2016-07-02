package com.gzp1124.testgutils.fragments_for_test;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.gzp1124.testgutils.R;
import com.gzp1124.gutils.BaseFragment;
import com.gzp1124.lib_ui.fragment_tab_host.MainFragmentTabHostFragment;
import com.gzp1124.testgutils.linshi.Frag1;
import com.gzp1124.testgutils.linshi.Frag2;
import com.gzp1124.testgutils.linshi.Frag3;

/**
 * author：高志鹏 on 16/6/30 15:40
 * email:imbagaozp@163.com
 */
public class TestFragmentTabHost extends BaseFragment {

    //图标集
    private int mImages[] = {
            R.drawable.tab_icon_explore,
            R.drawable.tab_icon_me,
            R.drawable.tab_icon_new
    };

    //标题
    String [] ll = {"首页","新闻","个人"};

    Class<Fragment> [] fs = new Class[]{Frag1.class,Frag2.class,Frag3.class};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainFragmentTabHostFragment.setAllDatas(mImages,ll,fs);
        MainFragmentTabHostFragment.setBGColor("#fafafa");
        MainFragmentTabHostFragment.setTabTextColor("#00ff00","#ff0000");
//        MainFragmentTabHostFragment.setHaveTabText(false);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.test_fragment_tab_host;
    }
}
