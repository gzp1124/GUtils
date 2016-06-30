package com.gzp1124.lib_ui;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

/**
 * 可独立使用的fragmentTabHost
 *
 * 使用方式：直接把该fragment放到xml布局中，通过setAllDatas设置需要的数据
 *
 * author：高志鹏 on 16/6/30 16:42
 * email:imbagaozp@163.com
 */
public class MainFragmentTabHostFragment extends Fragment{

    private View rootView;
    static FragmentTabHost mTabHost;
    //tab 图标集
    static int[] mImages = null;
    //tab 图标对应的文本提示
    static String[] mTabLables = null;
    //tab 对应的选项卡Fragment
    static Class<Fragment> [] mFragments = null;
    private static String bgColor = "#CECECE";
    private static String tabTextselectColor = "#ff0000";
    private static String tabTextNoSelectColor = "#000000";
    private TextView tabTextView;
    private static boolean isHaveTabText = true;

    /**
     * 设置选中
     * @param index
     */
    public static void setSelect(int index){
        if (index < mTabHost.getTabWidget().getChildCount()) {
            mTabHost.setCurrentTab(index);
        }
    }

    /**
     * 设置背景色
     */
    public static void setBGColor(String color){
        bgColor = color;
    }

    /**
     * 设置tab文本选中和未选中的颜色
     */
    public static void setTabTextColor(String selectColor,String noSelectColor){
        tabTextselectColor = selectColor;
        tabTextNoSelectColor = noSelectColor;
    }
    /**设置tab是否含有文字*/
    public static void setHaveTabText(boolean have){
        isHaveTabText = have;
    }

    /**
     * 设置数据，必须
     * @param images
     * @param tabLables
     * @param fragments
     */
    public static void setAllDatas(int[] images,String[] tabLables,Class<Fragment> [] fragments){
        mImages = images;
        mTabLables = tabLables;
        mFragments = fragments;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.main_fragment_tab_host,null);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        fullDataAndFragments();
    }

    private void initViews() {
        mTabHost = (FragmentTabHost) rootView.findViewById(android.R.id.tabhost);
        mTabHost.setup(getContext(), getActivity().getSupportFragmentManager(), android.R.id.tabcontent);
//        mTabHost.getTabWidget().setDividerDrawable(null); // 去掉分割线
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if (isHaveTabText) {
                    updateTabTextColor();
                }
            }
        });
    }

    private void updateTabTextColor(){
        for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {
            View view = mTabHost.getTabWidget().getChildAt(i);
            TextView tv = (TextView) mTabHost.getTabWidget().getChildAt(i).findViewById(R.id.tab_text);
            if (mTabHost.getCurrentTab() == i) {//选中
                tv.setTextColor(Color.parseColor(tabTextselectColor));
            }else {
                tv.setTextColor(Color.parseColor(tabTextNoSelectColor));
            }
        }
    }

    private void fullDataAndFragments() {
        if (mFragments == null){
            throw new RuntimeException("MainFragmentTabHostFragment : 请先设置数据");
        }
        for (int i = 0; i < mImages.length; i++) {
            // Tab按钮添加文字和图片
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mTabLables[i]).setIndicator(getImageView(i));
            // 添加Fragment
            mTabHost.addTab(tabSpec, mFragments[i], null);
            // 设置Tab按钮的背景
            mTabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor(bgColor));
        }
    }

    // 获得tab卡的view布局
    private View getImageView(int index) {
        @SuppressLint("InflateParams")
        View view = getActivity().getLayoutInflater().inflate(R.layout.main_fragment_tab_layout, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.tab_iv_image);
        imageView.setImageResource(mImages[index]);
        tabTextView = (TextView) view.findViewById(R.id.tab_text);
        tabTextView.setText(mTabLables[index]);
        if (!isHaveTabText){
            tabTextView.setVisibility(View.GONE);
        }
        return view;
    }
}
