package com.gzp1124.testgutils.fragments_for_test;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.gzp1124.gutils.base.BaseFragment;
import com.gzp1124.lib_ui.bottom_tab_view.BottomTabView;
import com.gzp1124.testgutils.R;
import com.gzp1124.testgutils.linshi.Frag1;
import com.gzp1124.testgutils.linshi.Frag2;
import com.gzp1124.testgutils.linshi.Frag3;
import com.gzp1124.testgutils.linshi.Frag4;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gzp1124 on 2017/5/18.
 * 测试bottom tabview
 */

public class TestBottomTabViewFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.bottom_tab_view_fragment;
    }

    protected void initViews(){}

    private FragmentPagerAdapter getAdapter(){
        return new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return getFragments().get(position);
            }
            @Override
            public int getCount() {
                return getFragments().size();
            }
        };
    }

    @Override
    protected void initViews(View view) {
        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        final BottomTabView bottomTabView = (BottomTabView) view.findViewById(R.id.bottomTabView);

        bottomTabView.setTabItemViews(getTabItemViews(),getCenterView());
        viewPager.setAdapter(getAdapter());

        bottomTabView.setOnTabItemSelectListener(new BottomTabView.OnTabItemSelectListener() {
            @Override
            public void onTabItemSelect(int position) {
                viewPager.setCurrentItem(position, true);
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override
            public void onPageSelected(int position) {
                bottomTabView.updatePosition(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }

    protected List<BottomTabView.TabItemView>  getTabItemViews(){
        List<BottomTabView.TabItemView> tabItemViews = new ArrayList<>();
        tabItemViews.add(new BottomTabView.TabItemView(getContext(), "标题1", R.color.colorPrimary,
                R.color.colorAccent, R.mipmap.ic_launcher, R.mipmap.ic_launcher));
        tabItemViews.add(new BottomTabView.TabItemView(getContext(), "标题2", R.color.colorPrimary,
                R.color.colorAccent, R.mipmap.ic_launcher, R.mipmap.ic_launcher));
        tabItemViews.add(new BottomTabView.TabItemView(getContext(), "标题3", R.color.colorPrimary,
                R.color.colorAccent, R.mipmap.ic_launcher, R.mipmap.ic_launcher));
        tabItemViews.add(new BottomTabView.TabItemView(getContext(), "标题4", R.color.colorPrimary,
                R.color.colorAccent, R.mipmap.ic_launcher, R.mipmap.ic_launcher));
        return tabItemViews;
    }

    protected List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new Frag1());
        fragments.add(new Frag2());
        fragments.add(new Frag3());
        fragments.add(new Frag4());
        return fragments;
    }

    //中间的大图标
    protected View getCenterView() {
        ImageView centerView = new ImageView(getContext());
        centerView.setImageResource(R.mipmap.ic_launcher);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(150, 150);
        layoutParams.leftMargin = 30;
        layoutParams.rightMargin = 30;
        layoutParams.bottomMargin = 0;
        centerView.setLayoutParams(layoutParams);
        centerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "centerView 点击了", Toast.LENGTH_SHORT).show();
            }
        });
        return centerView;
    }

}
