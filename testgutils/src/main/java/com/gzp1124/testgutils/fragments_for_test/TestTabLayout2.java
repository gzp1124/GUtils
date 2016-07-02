package com.gzp1124.testgutils.fragments_for_test;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.gzp1124.testgutils.R;
import com.gzp1124.gutils.BaseFragment;
import com.gzp1124.gutils.utils.GViewUtil;
import com.gzp1124.lib_ui.tab_layout.CommonTabLayout;
import com.gzp1124.lib_ui.tab_layout.listener.CustomTabEntity;
import com.gzp1124.lib_ui.tab_layout.listener.OnTabSelectListener;
import com.gzp1124.lib_ui.tab_layout.utils.UnreadMsgUtils;
import com.gzp1124.lib_ui.tab_layout.widget.MsgView;

import java.util.ArrayList;
import java.util.Random;

/**
 * tab layout 作为底部导航，实现bottomnavigation的效果
 *
 * author：高志鹏 on 16/7/2 12:46
 * email:imbagaozp@163.com
 */
public class TestTabLayout2  extends BaseFragment {
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ArrayList<Fragment> mFragments2 = new ArrayList<>();

    private String[] mTitles = {"首页", "消息", "联系人", "更多"};
    private int[] mIconUnselectIds = {
            R.mipmap.tab_home_unselect, R.mipmap.tab_speech_unselect,
            R.mipmap.tab_contact_unselect, R.mipmap.tab_more_unselect};
    private int[] mIconSelectIds = {
            R.mipmap.tab_home_select, R.mipmap.tab_speech_select,
            R.mipmap.tab_contact_select, R.mipmap.tab_more_select};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.test_tab_layout_2;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        for (String title : mTitles) {
            mFragments.add(SimpleCardFragment.getInstance("Switch ViewPager " + title));
            mFragments2.add(SimpleCardFragment.getInstance("Switch Fragment " + title));
        }

        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        
        View mDecorView = gView;

        ViewPager mViewPager = GViewUtil.find(mDecorView, R.id.vp_2);
        mViewPager.setAdapter(new MyPagerAdapter(getActivity().getSupportFragmentManager()));


        /** with nothing */
//        final CommonTabLayout mTabLayout_1 = GViewUtil.find(mDecorView, R.id.tl_1);
        /** with ViewPager */
        final CommonTabLayout mTabLayout_2 = GViewUtil.find(mDecorView, R.id.tl_2);
        /** with Fragments */
        CommonTabLayout mTabLayout_3 = GViewUtil.find(mDecorView, R.id.tl_3);
        /** indicator固定宽度 */
        final CommonTabLayout mTabLayout_4 = GViewUtil.find(mDecorView, R.id.tl_4);
        /** indicator固定宽度 */
        final CommonTabLayout mTabLayout_5 = GViewUtil.find(mDecorView, R.id.tl_5);
        /** indicator矩形圆角 */
        final CommonTabLayout mTabLayout_6 = GViewUtil.find(mDecorView, R.id.tl_6);
        /** indicator三角形 */
        final CommonTabLayout mTabLayout_7 = GViewUtil.find(mDecorView, R.id.tl_7);
        /** indicator圆角色块 */
        final CommonTabLayout mTabLayout_8 = GViewUtil.find(mDecorView, R.id.tl_8);


//        mTabLayout_1.setTabData(mTabEntities);
        tl_2(mViewPager,mTabLayout_2);
        mTabLayout_3.setTabData(mTabEntities, getActivity(), R.id.fl_change, mFragments2);
        mTabLayout_4.setTabData(mTabEntities);
        mTabLayout_5.setTabData(mTabEntities);
        mTabLayout_6.setTabData(mTabEntities);
        mTabLayout_7.setTabData(mTabEntities);
        mTabLayout_8.setTabData(mTabEntities);

        mTabLayout_3.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
//                mTabLayout_1.setCurrentTab(position);
                mTabLayout_2.setCurrentTab(position);
                mTabLayout_4.setCurrentTab(position);
                mTabLayout_5.setCurrentTab(position);
                mTabLayout_6.setCurrentTab(position);
                mTabLayout_7.setCurrentTab(position);
                mTabLayout_8.setCurrentTab(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mTabLayout_8.setCurrentTab(2);
        mTabLayout_3.setCurrentTab(1);

        //显示未读红点
//        mTabLayout_1.showDot(2);
        mTabLayout_3.showDot(1);
        mTabLayout_4.showDot(1);

        //两位数
        mTabLayout_2.showMsg(0, 55);
        mTabLayout_2.setMsgMargin(0, -5, 5);

        //三位数
        mTabLayout_2.showMsg(1, 100);
        mTabLayout_2.setMsgMargin(1, -5, 5);

        //设置未读消息红点
        mTabLayout_2.showDot(2);
        MsgView rtv_2_2 = mTabLayout_2.getMsgView(2);
        if (rtv_2_2 != null) {
            UnreadMsgUtils.setSize(rtv_2_2, dp2px(7.5f));
        }

        //设置未读消息背景
        mTabLayout_2.showMsg(3, 5);
        mTabLayout_2.setMsgMargin(3, 0, 5);
        MsgView rtv_2_3 = mTabLayout_2.getMsgView(3);
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

    Random mRandom = new Random();

    private void tl_2(final ViewPager mViewPager, final CommonTabLayout mTabLayout_2) {
        mTabLayout_2.setTabData(mTabEntities);
        mTabLayout_2.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
                if (position == 0) {
                    mTabLayout_2.showMsg(0, mRandom.nextInt(100) + 1);
//                    UnreadMsgUtils.show(mTabLayout_2.getMsgView(0), mRandom.nextInt(100) + 1);
                }
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout_2.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.setCurrentItem(1);
    }


    /**
     * 设置图标的标题，选中和未选中时的状态
     */
    class TabEntity implements CustomTabEntity {
        public String title;
        public int selectedIcon;
        public int unSelectedIcon;

        public TabEntity(String title, int selectedIcon, int unSelectedIcon) {
            this.title = title;
            this.selectedIcon = selectedIcon;
            this.unSelectedIcon = unSelectedIcon;
        }

        @Override
        public String getTabTitle() {
            return title;
        }

        @Override
        public int getTabSelectedIcon() {
            return selectedIcon;
        }

        @Override
        public int getTabUnselectedIcon() {
            return unSelectedIcon;
        }
    }

    protected int dp2px(float dp) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
