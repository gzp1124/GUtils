package com.gzp1124.lib_ui.tab_layout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gzp1124.lib_ui.R;
import com.gzp1124.lib_ui.tab_layout.listener.CustomTabEntity;

import java.util.ArrayList;

/**
 * author：高志鹏 on 16/7/6 11:12
 * email:imbagaozp@163.com
 */
public class TabLayoutBottomFragmentWithFragment extends Fragment {
    private ArrayList<Fragment> mFragments2 = new ArrayList<>();

    protected int getLayoutId() {
        return R.layout.tab_layout_bottom_fragment_fragment;
    }

    private static String[] mTitles;
    private static int[] mIconUnselectIds;
    private static int[] mIconSelectIds;
    private static Fragment[] mFragments;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    public static void setAllDatas(String[] titles,Fragment[] fragments,int[] iconSelectIds,int[] iconUnselectIds){
        mTitles = titles;
        mFragments = fragments;
        mIconSelectIds = iconSelectIds;
        mIconUnselectIds = iconUnselectIds;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_layout_bottom_fragment_fragment,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mFragments2.clear();
        for (Fragment fragment:mFragments){
            mFragments2.add(fragment);
        }

        mTabEntities.clear();
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }

        View mDecorView = view;

        CommonTabLayout mTabLayout_3 = (CommonTabLayout) mDecorView.findViewById(R.id.tl_3);

        mTabLayout_3.setTabData(mTabEntities, getActivity(), R.id.fl_change, mFragments2);

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

}
