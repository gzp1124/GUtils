package com.gzp1124.lib_ui.bottomnavigation_2;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gzp1124.lib_ui.R;

/**
 * author：高志鹏 on 16/7/1 18:26
 * email:imbagaozp@163.com
 */
public class BottomNavigationFragment2 extends Fragment implements BottomNavigationBar.OnTabSelectedListener {

    private View rootView;
    //tab 图标集
    static int[] mImages = null;
    //tab 图标对应的文本提示
    static String[] mTabLables = null;
    //tab 每个选项卡对应的颜色
    static int[] mColors = null;//0xff4a5965  0xff096c54  0xff8a6a64  0xff553b36
    //tab 对应的选项卡Fragment
    static Class<Fragment> [] mFragments = null;
    //tab 当前的mode
    static Mode mMode;
    //tab 当前的backgroundStyle
    static BackgroundStyle mBackgroundStyle;
    private BottomNavigationBar bottomNavigationBar;

    private Fragment[] showFragments = null;
    private Fragment currentShowFragment = null;

    private int currentSelectIndex = -1;

    public static enum Mode{
        MODE_DEFAULT(0), MODE_FIXED(1), MODE_SHIFTING(2);
        private int value;

        Mode(int value) {
            this.value = value;
        }
    }

    public static enum BackgroundStyle{
        BACKGROUND_STYLE_DEFAULT(0), BACKGROUND_STYLE_STATIC(1), BACKGROUND_STYLE_RIPPLE(2);
        private int value;

        BackgroundStyle(int value) {
            this.value = value;
        }
    }

    /**
     * 设置数据，必须
     * @param images    标签卡图标
     * @param tabLables  每个标签卡的文本
     * @param color     选中时的背景颜色
     * @param fragments   每个标签卡对应的Fragment
     */
    public static void setAllDatas(int[] images,String[] tabLables,int [] color,Class<Fragment> [] fragments){
        mImages = images;
        mTabLables = tabLables;
        mFragments = fragments;
        mColors = color;
    }

    /**
     * 设置数据
     * @param images  图标
     * @param tabLables  文本
     * @param color  颜色
     * @param fragments  fragment
     * @param mode   模式
     * @param backgroundStyle  样式
     */
    public static void setAllDatas(int[] images,String[] tabLables,int [] color,Class<Fragment> [] fragments,Mode mode,BackgroundStyle backgroundStyle){
        setAllDatas(images,tabLables,color,fragments);
        mMode = mode;
        mBackgroundStyle = backgroundStyle;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.bottom_navigation2_fragment_2,null);
        initViews();
        setUpBottomNavigationBar();
        return rootView;
    }

    private void initViews() {
        bottomNavigationBar = (BottomNavigationBar) rootView.findViewById(R.id.bottomLayout);
    }

    int lastSelectedPosition = 0;

    //选中时是否隐藏
    boolean hideOnSelect = false;

    BadgeItem numberBadgeItem;

    private void setUpBottomNavigationBar() {
        if (mFragments == null){
            throw new RuntimeException("MainFragmentTabHostFragment : 请先设置数据");
        }
        showFragments = new Fragment[mFragments.length];

        bottomNavigationBar.clearAll();

        setUpCurrentShowFragment(0);

        numberBadgeItem = new BadgeItem()
                .setBorderWidth(4)
                .setBackgroundColor(Color.BLUE)
                .setText("111" + lastSelectedPosition)
                .setHideOnSelect(hideOnSelect);

        bottomNavigationBar.setMode(mMode.value);
        bottomNavigationBar.setBackgroundStyle(mBackgroundStyle.value);

        for (int i = 0;i<mFragments.length;i++){
            if (i==0){
                bottomNavigationBar
                        .addItem(new BottomNavigationItem(mImages[i], mTabLables[i])
                                .setActiveColor(mColors[i])
                                .setBadgeItem(numberBadgeItem));
            }else{
                bottomNavigationBar.addItem(
                        new BottomNavigationItem(mImages[i], mTabLables[i]).setActiveColor(mColors[i]));
            }
        }
        //执行
        bottomNavigationBar.initialise();

        bottomNavigationBar.setTabSelectedListener(this);
    }

    private void setUpCurrentShowFragment(int position){
        if (currentSelectIndex == position){
            //重复点击，可以在这里做刷新操作
            return;
        }else {
            currentSelectIndex = position;
        }
        Fragment fragment = null;
        try {
            if (showFragments[position] == null) {
                fragment = mFragments[position].newInstance();
                showFragments[position] = fragment;

                if (currentShowFragment == null) {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.container, fragment)
                            .commit();
                }else {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .hide(currentShowFragment)
                            .add(R.id.container, fragment)
                            .commit();
                }
                currentShowFragment = fragment;
            }else {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .hide(currentShowFragment)
                        .show(showFragments[position])
                        .commit();
                currentShowFragment = showFragments[position];
            }
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onTabSelected(int position) {
        setUpCurrentShowFragment(position);
    }

    @Override
    public void onTabUnselected(int position) {
    }

    @Override
    public void onTabReselected(int position) {
    }
}
