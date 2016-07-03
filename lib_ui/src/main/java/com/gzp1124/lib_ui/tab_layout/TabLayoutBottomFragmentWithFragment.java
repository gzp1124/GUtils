package com.gzp1124.lib_ui.tab_layout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gzp1124.lib_ui.R;
import com.gzp1124.lib_ui.tab_layout.listener.CustomTabEntity;
import com.gzp1124.lib_ui.tab_layout.listener.OnTabSelectListener;
import com.gzp1124.lib_ui.tab_layout.listener.TabEntity;

import java.util.ArrayList;

/**
 * 底部导航栏，关联顶部FrameLayout
 *
 * author：高志鹏 on 16/7/3 11:07
 * email:imbagaozp@163.com
 */
public class TabLayoutBottomFragmentWithFragment extends Fragment {
    private static String[] mTitles;
    private static int[] mIconUnselectIds;
    private static int[] mIconSelectIds;
    private static Fragment[] mFragments;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private CommonTabLayout mTabLayout;

    public static void setAllDatas(String[] titles,Fragment[] fragments,int[] iconSelectIds,int[] iconUnselectIds){
        mTitles = titles;
        mFragments = fragments;
        mIconSelectIds = iconSelectIds;
        mIconUnselectIds = iconUnselectIds;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_layout_bottom_fragment_fragment,null);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        CommonTabLayout tabLayout = (CommonTabLayout) view.findViewById(R.id.tab_layout);
        ArrayList<Fragment> fs = new ArrayList<Fragment>();
        for (Fragment f :
                mFragments) {
            fs.add(f);
        }
        tabLayout.setTabData(mTabEntities,getActivity(),R.id.content_fragment,fs);
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {

            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        tabLayout.setCurrentTab(2);
    }
}
