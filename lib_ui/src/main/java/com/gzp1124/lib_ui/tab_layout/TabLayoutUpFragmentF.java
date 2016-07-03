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

    private SegmentTabLayout mTabLayout;

    public static void setAllDatas(String[] titls,Fragment[] fragments){
        mTitles = titls;
        mFragments = fragments;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_layout_up_fragment_f,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SegmentTabLayout tabLayout = (SegmentTabLayout) view.findViewById(R.id.tab_layout);
        ArrayList<Fragment> fs = new ArrayList<>();
        for (Fragment f :
                mFragments) {
            fs.add(f);
        }
        tabLayout.setTabData(mTitles, getActivity(), R.id.content_fragment, fs);
    }
}
