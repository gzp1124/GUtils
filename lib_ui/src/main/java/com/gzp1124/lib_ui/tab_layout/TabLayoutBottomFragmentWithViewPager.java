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
import com.gzp1124.lib_ui.tab_layout.listener.CustomTabEntity;
import com.gzp1124.lib_ui.tab_layout.listener.OnTabSelectListener;
import com.gzp1124.lib_ui.tab_layout.listener.TabEntity;
import com.gzp1124.lib_ui.tab_layout.utils.UnreadMsgUtils;
import com.gzp1124.lib_ui.tab_layout.widget.MsgView;

import java.util.ArrayList;

/**
 * 底部导航栏，关联顶部ViewPager
 *
 * author：高志鹏 on 16/7/3 08:38
 * email:imbagaozp@163.com
 */
public class TabLayoutBottomFragmentWithViewPager extends Fragment {

    private static String[] mTitles;
    private static int[] mIconUnselectIds;
    private static int[] mIconSelectIds;
    private static Fragment[] mFragments;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private CommonTabLayout mTabLayout;
    private ViewPager mViewPager;

    public static void setAllDatas(String[] titles,Fragment[] fragments,int[] iconSelectIds,int[] iconUnselectIds){
        mTitles = titles;
        mFragments = fragments;
        mIconSelectIds = iconSelectIds;
        mIconUnselectIds = iconUnselectIds;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_layout_bottom_fragment_viewpager,null);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
//        mViewPager.setOffscreenPageLimit(0);
        mViewPager.setAdapter(new MyPagerAdapter(getActivity().getSupportFragmentManager()));
        mTabLayout = (CommonTabLayout) view.findViewById(R.id.tab_layout);

        mTabLayout.setTabData(mTabEntities);
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
                if (position == 0) {
//                    mTabLayout.showMsg(0, mRandom.nextInt(100) + 1);
//                    UnreadMsgUtils.show(mTabLayout.getMsgView(0), mRandom.nextInt(100) + 1);
                }
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

        mViewPager.setCurrentItem(0);
        
        setUnReadMsg();
    }

    /**
     * 设置未读消息
     */
    private void setUnReadMsg() {
        //两位数
        mTabLayout.showMsg(0, 55);
        mTabLayout.setMsgMargin(0, -5, 5);

        //三位数
        mTabLayout.showMsg(1, 100);
        mTabLayout.setMsgMargin(1, -5, 5);

        //设置未读消息红点
        mTabLayout.showDot(2);
        MsgView rtv_2_2 = mTabLayout.getMsgView(2);
        if (rtv_2_2 != null) {
            UnreadMsgUtils.setSize(rtv_2_2, dp2px(7.5f));
        }

        //设置未读消息背景
        mTabLayout.showMsg(3, 5);
        mTabLayout.setMsgMargin(3, 0, 5);
        MsgView rtv_2_3 = mTabLayout.getMsgView(3);
        if (rtv_2_3 != null) {
            rtv_2_3.setBackgroundColor(Color.parseColor("#6D8FB0"));
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

    protected int dp2px(float dp) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
