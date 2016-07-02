package com.gzp1124.testgutils.fragments_for_test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.gzp1124.gutils.BaseFragment;
import com.gzp1124.gutils.utils.GViewUtil;
import com.gzp1124.lib_ui.tab_layout.SlidingTabLayout;
import com.gzp1124.testgutils.R;

import java.util.ArrayList;

/**
 * tab layout 的具体使用案例
 * author：高志鹏 on 16/7/2 12:46
 * email:imbagaozp@163.com
 */
public class TestTabLayoutFragment1 extends BaseFragment {

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private final String[] mTitles = {
            "热门", "iOS", "Android"
            , "前端", "后端", "设计", "工具资源"
    };
    private MyPagerAdapter mAdapter;
    @Override
    protected int getLayoutId() {
        return R.layout.test_tab_layout_fragment_1;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        for (String title : mTitles) {
            mFragments.add(SimpleCardFragment.getInstance(title));
        }

        View decorView = view;
        ViewPager vp = GViewUtil.find(decorView, R.id.vp);
        mAdapter = new MyPagerAdapter(getChildFragmentManager());
        vp.setAdapter(mAdapter);

        SlidingTabLayout tabLayout = GViewUtil.find(decorView, R.id.tab_layout);
        tabLayout.setViewPager(vp);

        vp.setCurrentItem(4);
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
}
