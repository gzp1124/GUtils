package com.gzp1124.lib_ui.tab_layout;

import android.graphics.Color;
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
import com.gzp1124.lib_ui.tab_layout.listener.OnTabSelectListener;
import com.gzp1124.lib_ui.tab_layout.widget.MsgView;

/**
 * 顶部复杂样式
 * 结合viewpager使用
 * author：高志鹏 on 16/7/2 22:51
 * email:imbagaozp@163.com
 */
public class TabLayoutUpFragmentVP2 extends Fragment {
    //tab 标题
    private static String [] mTitles;

    private static Fragment[] mFragments;

    private MyPagerAdapter mAdapter;
    private SegmentTabLayout mTabLayout;

    public static void setAllDatas(String[] titls,Fragment[] fragments){
        mTitles = titls;
        mFragments = fragments;
    }

    private static SegmentTabLayout tab;
    /**
     *提供外部获取TabLayout的方法，方便fragment中进行tab操作
     */
    public static SegmentTabLayout getTabLayout(){
        return tab;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_layout_up_fragment_vp_2,null);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTabLayout = (SegmentTabLayout) view.findViewById(R.id.tab_layout);
        final ViewPager mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mViewPager.setAdapter(new MyPagerAdapter(getActivity().getSupportFragmentManager()));

        mTabLayout.setTabData(mTitles);
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setCurrentItem(1);

        setUnReadMsg();

        tab = mTabLayout;
    }

    private void setUnReadMsg() {
        //显示未读红点
        mTabLayout.showDot(1);
        //设置未读消息红点
        mTabLayout.showDot(2);
        MsgView rtv_3_2 = mTabLayout.getMsgView(2);
        if (rtv_3_2 != null) {
            rtv_3_2.setBackgroundColor(Color.parseColor("#6D8FB0"));
        }
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

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
        }
    }
}
