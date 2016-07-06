package com.gzp1124.lib_ui.tab_layout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gzp1124.lib_ui.R;

import java.util.ArrayList;

/**
 * 顶部导航，结合底部FrameLayout使用
 *
 * 简单实用
 *
 * author：高志鹏 on 16/7/3 14:57
 * email:imbagaozp@163.com
 */
public class TabLayoutUpFragmentF extends Fragment {
    //tab 标题
    private static String [] mTitles;

    private static Fragment[] mFragments;
    private static ArrayList<Fragment> mFragments2 = new ArrayList<>();

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
        return inflater.inflate(R.layout.tab_layout_up_fragment_f,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragments2.clear();
        for (Fragment f :
                mFragments) {
            mFragments2.add(f);
        }

        SegmentTabLayout tabLayout_4 = (SegmentTabLayout) view.findViewById(R.id.tl_4);

        tabLayout_4.setTabData(mTitles, getActivity(), R.id.fl_change, mFragments2);
    }
}
