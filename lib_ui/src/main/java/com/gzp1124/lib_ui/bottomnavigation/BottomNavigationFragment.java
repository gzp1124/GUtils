package com.gzp1124.lib_ui.bottomnavigation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gzp1124.lib_ui.R;

/**
 * author：高志鹏 on 16/7/1 16:19
 * email:imbagaozp@163.com
 */
public class BottomNavigationFragment extends Fragment{

    private View rootView;
    //tab 图标集
    static int[] mImages = null;
    //tab 图标对应的文本提示
    static String[] mTabLables = null;
    //tab 每个选项卡对应的颜色
    static int[] mColors = null;//0xff4a5965  0xff096c54  0xff8a6a64  0xff553b36
    //tab 对应的选项卡Fragment
    static Class<Fragment> [] mFragments = null;

    private Fragment[] showFragments = null;

    /**
     * 设置数据，必须
     * @param images  标签卡图标
     * @param tabLables  每个标签卡的文本
     * @param color  选中时的背景颜色
     * @param fragments   每个标签卡对应的Fragment
     */
    public static void setAllDatas(int[] images,String[] tabLables,int [] color,Class<Fragment> [] fragments){
        mImages = images;
        mTabLables = tabLables;
        mFragments = fragments;
        mColors = color;
    }
    /**设置选中*/
    public static void setSelect(int index){
        if (index<bottomLayout.getChildCount()){
            bottomLayout.setSelected(index);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.bottom_navigation_fragment,container,false);
        setUpBottomNavigationBar();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            Fragment fragment = mFragments[0].newInstance();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,
                    fragment).commitAllowingStateLoss();
            showFragments[0] = fragment;
            currentShowFragment = fragment;
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static BottomNavigationBar bottomLayout;
    private Fragment currentShowFragment = null;
    private int currentSelectIndex = -1;
    public void setUpBottomNavigationBar() {
        if (mFragments == null){
            throw new RuntimeException("MainFragmentTabHostFragment : 请先设置数据");
        }
        bottomLayout = (BottomNavigationBar) rootView.findViewById(R.id.bottomLayout);
        bottomLayout.setTabWidthSelectedScale(1.5f);
        bottomLayout.setTextDefaultVisible(false);
        for (int i = 0;i<mImages.length;i++){
            bottomLayout.addTab(mImages[i], mTabLables[i], mColors[i]);
        }
        showFragments = new Fragment[mFragments.length];
        bottomLayout.setOnTabListener(new BottomNavigationBar.TabListener() {
            @Override
            public void onSelected(BottomBarTab tab, int position) {
                if (currentSelectIndex == position){
                    //重复点击，可以在这里做刷新操作
                    return;
                }else {
                    currentSelectIndex = position;
                }
                Fragment fragment = null;
                try {
                    if (showFragments[position]==null) {
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
        });
    }
}
