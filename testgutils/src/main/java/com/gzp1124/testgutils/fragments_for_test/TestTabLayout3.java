package com.gzp1124.testgutils.fragments_for_test;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.gzp1124.gutils.utils.GViewUtil;
import com.gzp1124.lib_ui.tab_layout.SegmentTabLayout;
import com.gzp1124.lib_ui.tab_layout.listener.OnTabSelectListener;
import com.gzp1124.lib_ui.tab_layout.widget.MsgView;
import com.gzp1124.testgutils.R;
import com.gzp1124.gutils.base.BaseFragment;

import java.util.ArrayList;

/**
 * tab layout 作为顶部导航，几种特殊效果
 * author：高志鹏 on 16/7/2 12:46
 * email:imbagaozp@163.com
 */
public class TestTabLayout3  extends BaseFragment {
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ArrayList<Fragment> mFragments2 = new ArrayList<>();

    private String[] mTitles = {"首页", "消息"};
    private String[] mTitles_2 = {"首页", "消息", "联系人"};
    private String[] mTitles_3 = {"首页", "消息", "联系人", "更多"};
    private View mDecorView;
    private SegmentTabLayout mTabLayout_3;
    @Override
    protected int getLayoutId() {
        return R.layout.test_tab_layout_3;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        for (String title : mTitles_3) {
            mFragments.add(SimpleCardFragment.getInstance("Switch ViewPager " + title));
        }

        for (String title : mTitles_2) {
            mFragments2.add(SimpleCardFragment.getInstance("Switch Fragment " + title));
        }
        
        mDecorView = gView;
        SegmentTabLayout tabLayout_1 = GViewUtil.find(mDecorView, R.id.tl_1);
        SegmentTabLayout tabLayout_2 = GViewUtil.find(mDecorView, R.id.tl_2);
        mTabLayout_3 = GViewUtil.find(mDecorView, R.id.tl_3);
        SegmentTabLayout tabLayout_4 = GViewUtil.find(mDecorView, R.id.tl_4);
        SegmentTabLayout tabLayout_5 = GViewUtil.find(mDecorView, R.id.tl_5);

        tabLayout_1.setTabData(mTitles);
        tabLayout_2.setTabData(mTitles_2);
        tl_3();
        tabLayout_4.setTabData(mTitles_2, getActivity(), R.id.fl_change, mFragments2);
        tabLayout_5.setTabData(mTitles_3);

        //显示未读红点
        tabLayout_1.showDot(2);
        tabLayout_2.showDot(2);
        mTabLayout_3.showDot(1);
        tabLayout_4.showDot(1);

        //设置未读消息红点
        mTabLayout_3.showDot(2);
        MsgView rtv_3_2 = mTabLayout_3.getMsgView(2);
        if (rtv_3_2 != null) {
            rtv_3_2.setBackgroundColor(Color.parseColor("#6D8FB0"));
        }
    }

    @Override
    protected void initViews() {

    }

    private void tl_3() {
        final ViewPager vp_3 = GViewUtil.find(mDecorView, R.id.vp_2);
        vp_3.setAdapter(new MyPagerAdapter(getActivity().getSupportFragmentManager()));

        mTabLayout_3.setTabData(mTitles_3);
        mTabLayout_3.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                vp_3.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });

        vp_3.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout_3.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vp_3.setCurrentItem(1);
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
            return mTitles_3[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
}
