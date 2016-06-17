package com.gzp1124.gutils.three_ui_widget.have_header_viewpager;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.gzp1124.gutils.R;

import java.util.LinkedHashMap;

/**
 * 带有头布局的ViewPager
 *
 *
 * 基本使用：
        GHaveHeaderViewpager ghvUtil = new G.....;
         LinkedHashMap<String,HVFragment.FullContentView> map = new LinkedHashMap<>();
         map.put("第一个",new HVMyScrollFragment.FullScrollview(){

        @Override
        public View fullSCrollview() {
        TextView textView = new TextView(Activity.this);
        textView.setText("拉开纠纷的");
        return textView;
        }
        });
         map.put("来一个6空得listview", new HVMyListFragment.FullListView() {
        @Override
        public void fullListView(ListView listView) {
        listView.setAdapter(new ArrayAdapter<String >(Activity.this,android.R.layout.simple_list_item_1,
        android.R.id.text1,getResources().getStringArray(R.array.list)));
        }
        });
         ghvUtil.setTabfragments(map);

         ghvUtil.setmyAddHeaderView(View.inflate(Activity.this,R.layout.hh,null));

         ghvUtil.setTabBGColor("#33ff0000");

         ghvUtil.setTabHeaderBackground(getResources().getDrawable(R.drawable.ab));

         setContentView(ghvUtil.getRootView());
 *
 *
 *
 * author：高志鹏 on 16/6/2 13:37
 * email:imbagaozp@163.com
 */
public class GHaveHeaderViewpagerUtil {
    //tab的高度
    private int mTabHeight;
    //header的高度
    private int mHeaderHeight;
    private FragmentActivity mActivity;
    private SparseArrayCompat<HVScrollableListener> mScrollableListenerArrays;
    private HVViewPagerHeaderHelper.MyScrollable myScrollable;

    private LinkedHashMap<String,? extends HVFragment.FullContentView> tabfragments;
    //header layout
    private View mHeaderLayoutView;
    //tab layout
    private HVSlidingTabLayout slidingTabLayout;

    public GHaveHeaderViewpagerUtil(FragmentActivity activity) {
        mActivity = activity;
        mTabHeight = activity.getResources().getDimensionPixelSize(R.dimen.hv_tabs_height);
        mHeaderHeight = activity.getResources().getDimensionPixelSize(R.dimen.hv_viewpager_header_height);
        mScrollableListenerArrays = new SparseArrayCompat<>();
    }


    String [] tabNames;

    public View getRootView() {
        String res = checkData();
        if (!TextUtils.isEmpty(res)){
            Toast.makeText(mActivity,res,Toast.LENGTH_SHORT).show();
            return null;
        }

        int i = 0;
        tabNames = new String[tabfragments.size()];
        for (String s :
                tabfragments.keySet()) {
            tabNames[i++] = s;
        }

        View view = View.inflate(mActivity, R.layout.have_header_viewpager_show_main,null);
        HVTouchCallbackLayout touchCallbackLayout = (HVTouchCallbackLayout) view.findViewById(R.id.layout);

        mHeaderLayoutView = view.findViewById(R.id.header);
        if (mHeaderLayoutViewDrawable != null) mHeaderLayoutView.setBackgroundDrawable(mHeaderLayoutViewDrawable);
        RelativeLayout header_content = (RelativeLayout) view.findViewById(R.id.header_content);
        if (myAddHeaderView != null){
            header_content.addView(myAddHeaderView);
        }else {
            TextView textView = new TextView(mActivity);
            textView.setText("没有设置headerview布局");
            header_content.addView(textView);
        }

        slidingTabLayout = (HVSlidingTabLayout) view.findViewById(R.id.tabs);
        if (!TextUtils.isEmpty(TabLayoutColor)) slidingTabLayout.setBackgroundColor(Color.parseColor(TabLayoutColor));

        ViewPager mViewPager = (ViewPager) view.findViewById(R.id.viewpager);

        HVViewPagerHeaderHelper helper = new HVViewPagerHeaderHelper(mActivity, mScrollableListenerArrays,mViewPager, mHeaderLayoutView,mTabHeight,mHeaderHeight,touchCallbackLayout);

        myScrollable = helper.getMyScrollable();
        mViewPager.setAdapter(new ViewPagerAdapter(mActivity.getSupportFragmentManager()));
        slidingTabLayout.setViewPager(mViewPager,mActivity);
        return view;
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
    private View myAddHeaderView;
    private String TabLayoutColor;
    private String TabTextColor;
    private Drawable mHeaderLayoutViewDrawable;
    /**
     * 设置tab和Fragment对应
     * @param tabfragments  键为tab名称， 值为 FullListView 或 FullScrollView 的实现类
     */
    public void setTabfragments(LinkedHashMap<String,? extends HVFragment.FullContentView> tabfragments){
        this.tabfragments = tabfragments;
    }

    public void setmyAddHeaderView(View headerLayoutView){
        myAddHeaderView = headerLayoutView;
    }

    public void setTabBGColor(String color){
        TabLayoutColor = color;
    }

    public void setTabTextColor(String color){
        TabTextColor = color;
    }

    public void setTabHeaderBackground(Drawable drawable){
        mHeaderLayoutViewDrawable = drawable;
    }

    //============检查各部分数据==========
    public String checkData(){
        String res = "";
        if (tabfragments == null || tabfragments.size() == 0){
            res = "GUtil 中的tabfragment没有设置";
        }
        return res;
    }
}
