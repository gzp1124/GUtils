package com.gzp1124.lib_ui.have_header_viewpager;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gzp1124.lib_ui.R;

import java.util.LinkedHashMap;

/**
 * 带有头布局的ViewPager
 *
 * author：高志鹏 on 16/6/2 13:37
 * email:imbagaozp@163.com
 */
public class GHaveHeaderViewpagerFragment extends Fragment{
    //tab的高度
    private int mTabHeight;
    //header的高度
    private int mHeaderHeight;
    private SparseArrayCompat<HVScrollableListener> mScrollableListenerArrays;
    private HVViewPagerHeaderHelper.MyScrollable myScrollable;

    private static LinkedHashMap<String,? extends HVFragment.FullContentView> tabfragments;
    //header layout
    private View mHeaderLayoutView;
    //tab layout
    private HVSlidingTabLayout slidingTabLayout;
    private View rootView;

    String [] tabNames;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = View.inflate(getContext(), R.layout.have_header_viewpager_show_main,null);

        mTabHeight = getActivity().getResources().getDimensionPixelSize(R.dimen.hv_tabs_height);
        mHeaderHeight = getActivity().getResources().getDimensionPixelSize(R.dimen.hv_viewpager_header_height);
        mScrollableListenerArrays = new SparseArrayCompat<>();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String res = checkData();
        if (!TextUtils.isEmpty(res)){
            Toast.makeText(getContext(),res,Toast.LENGTH_SHORT).show();
            return ;
        }

        int i = 0;
        tabNames = new String[tabfragments.size()];
        for (String s :
                tabfragments.keySet()) {
            tabNames[i++] = s;
        }
        HVTouchCallbackLayout touchCallbackLayout = (HVTouchCallbackLayout) view.findViewById(R.id.layout);

        mHeaderLayoutView = view.findViewById(R.id.header);
        if (mHeaderLayoutViewDrawable != null) mHeaderLayoutView.setBackgroundDrawable(mHeaderLayoutViewDrawable);
        RelativeLayout header_content = (RelativeLayout) view.findViewById(R.id.header_content);
        if (myAddHeaderView != null){
            header_content.addView(myAddHeaderView);
        }else {
            TextView textView = new TextView(getContext());
            textView.setText("没有设置headerview布局");
            header_content.addView(textView);
        }

        slidingTabLayout = (HVSlidingTabLayout) view.findViewById(R.id.tabs);
        if (!TextUtils.isEmpty(TabLayoutColor)) slidingTabLayout.setBackgroundColor(Color.parseColor(TabLayoutColor));

        ViewPager mViewPager = (ViewPager) view.findViewById(R.id.viewpager);

        HVViewPagerHeaderHelper helper = new HVViewPagerHeaderHelper(getContext(), mScrollableListenerArrays,mViewPager, mHeaderLayoutView,mTabHeight,mHeaderHeight,touchCallbackLayout);

        myScrollable = helper.getMyScrollable();
        mViewPager.setAdapter(new ViewPagerAdapter(getActivity().getSupportFragmentManager()));
        slidingTabLayout.setViewPager(mViewPager,getActivity());
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            //TODO 每次都会new新对象，要进行优化
            HVFragment.FullContentView fullContentView = tabfragments.get(tabNames[position]);
            HVFragment fragment = null;
            if (fullContentView instanceof HVMyListFragment.FullListView){
                HVMyListFragment listFragment = new HVMyListFragment(mScrollableListenerArrays, myScrollable, position);
                listFragment.setFullListView((HVMyListFragment.FullListView) fullContentView);
                fragment = listFragment;
            }else if (fullContentView instanceof HVMyScrollFragment.FullScrollview){
                HVMyScrollFragment scrollFragment = new HVMyScrollFragment(mScrollableListenerArrays, myScrollable, position);
                scrollFragment.setFullScrollview((HVMyScrollFragment.FullScrollview) fullContentView);
                fragment = scrollFragment;
            }else{
                throw new RuntimeException("添加的FullContentView异常，目前只能添加FullListView,FullScrollview两种");
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return tabNames.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabNames[position];
        }
    }

    //======== 外部set Data ==========
    private static View myAddHeaderView;
    private static String TabLayoutColor;
    private static String TabTextColor;
    private static Drawable mHeaderLayoutViewDrawable;
    /**
     * 设置tab和Fragment对应
     */
    public static void setTabfragments(LinkedHashMap<String,? extends HVFragment.FullContentView> tabf){
        tabfragments = tabf;
    }

    public static void setmyAddHeaderView(View headerLayoutView){
        myAddHeaderView = headerLayoutView;
    }

    public static void setTabBGColor(String color){
        TabLayoutColor = color;
    }

    public static void setTabTextColor(String color){
        TabTextColor = color;
    }

    public static void setTabHeaderBackground(Drawable drawable){
        mHeaderLayoutViewDrawable = drawable;
    }

    //============检查各部分数据==========
    public String checkData(){
        String res = "";
        if (tabfragments == null || tabfragments.size() == 0){
            res = "GUtil 中的tabfragment没有设置";
            throw new RuntimeException("GUtil 中的tabfragment没有设置");
        }
        return res;
    }
}
