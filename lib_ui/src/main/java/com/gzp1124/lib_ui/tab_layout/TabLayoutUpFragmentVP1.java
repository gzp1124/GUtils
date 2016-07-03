package com.gzp1124.lib_ui.tab_layout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gzp1124.lib_ui.R;

/**
 * 顶部简单样式
 * 结合viewpager使用
 * 供外部设置数据后可直接使用的tablayout
 *
 * 使用方式：直接在xml中设置<fragment>然后设置setAllDatas
 *
 * 修改tab样式，直接修改这里tab_layout_fragment的布局中代码
 *
 * author：高志鹏 on 16/7/2 21:04
 * email:imbagaozp@163.com
 */
public class TabLayoutUpFragmentVP1 extends Fragment{

    //tab 标题
    private static String [] mTitles;

    private static Fragment[] mFragments;

    private MyPagerAdapter mAdapter;

    public static void setAllDatas(String[] titls,Fragment[] fragments){
        mTitles = titls;
        mFragments = fragments;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_layout_up_fragment_vp_1,null);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View decorView = view;
        ViewPager vp = (ViewPager) view.findViewById(R.id.vp);
        mAdapter = new MyPagerAdapter(getChildFragmentManager());
        vp.setAdapter(mAdapter);

        SlidingTabLayout tabLayout = (SlidingTabLayout) view.findViewById(R.id.tab_layout);
        tabLayout.setViewPager(vp);

        vp.setCurrentItem(4);
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments[position];
        }
    }

}
