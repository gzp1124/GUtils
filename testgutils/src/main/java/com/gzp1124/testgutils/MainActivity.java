package com.gzp1124.testgutils;


import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.gzp1124.gutils.base.BaseApplication;
import com.gzp1124.gutils.utils.GDeviceUtil;
import com.gzp1124.gutils.utils.GToastUtil;
import com.gzp1124.gutils.utils.GWebUtil;
import com.gzp1124.gutils.widget.BrowserFragment;
import com.gzp1124.testgutils.fragments_for_test.HaveHeaderViewPagerFragment;
import com.gzp1124.testgutils.fragments_for_test.MapTestFragment;
import com.gzp1124.testgutils.fragments_for_test.SendMyBroadFragment;
import com.gzp1124.testgutils.fragments_for_test.SocialTestFragment;
import com.gzp1124.testgutils.fragments_for_test.TestBottomN2;
import com.gzp1124.testgutils.fragments_for_test.TestBeepFragment;
import com.gzp1124.testgutils.fragments_for_test.TestBottomNavigationFragment;
import com.gzp1124.testgutils.fragments_for_test.TestFragmentTabHost;
import com.gzp1124.testgutils.fragments_for_test.TestLibRecyclerViewAdapterHelper;
import com.gzp1124.testgutils.fragments_for_test.TestLibRecyclerViewFragment;
import com.gzp1124.testgutils.fragments_for_test.TestMyUseRecyclerView;
import com.gzp1124.testgutils.fragments_for_test.TestPrintFragment;
import com.gzp1124.testgutils.fragments_for_test.TestTLBottomFrameLayout;
import com.gzp1124.testgutils.fragments_for_test.TestTLBottomViewpager;
import com.gzp1124.testgutils.fragments_for_test.TestTLUpFragmentF;
import com.gzp1124.testgutils.fragments_for_test.TestTLUpFragmentVP2;
import com.gzp1124.testgutils.fragments_for_test.TestTabLayoutFragment;
import com.gzp1124.testgutils.fragments_for_test.TestTLUpFragmentVP1;
import com.gzp1124.testgutils.fragments_for_test.TestTabLayout_ALL;
import com.gzp1124.testgutils.fragments_for_test.TestTabStripFragment;
import com.gzp1124.testgutils.fragments_for_test.TimeTaskTestFragment;

import junit.framework.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class MainActivity extends ListActivity {
    private static Map<String,Fragment> fragmentMap = new LinkedHashMap<>();

    static {
        fragmentMap.put("社会化",new SocialTestFragment());
        fragmentMap.put("定时器",new TimeTaskTestFragment());
        fragmentMap.put("带有头布局的viewpager",new HaveHeaderViewPagerFragment());
        fragmentMap.put("发送自定义广播",new SendMyBroadFragment());
        fragmentMap.put("百度地图",new MapTestFragment());
        fragmentMap.put("前后摄像头扫描二维码/条形码",new TestPrintFragment());
        fragmentMap.put("测试震动",new TestBeepFragment());
        fragmentMap.put("测试配合viewpager的tab选项卡",new TestTabStripFragment());
        fragmentMap.put("测试抽取的fragmenttabhost的使用",new TestFragmentTabHost());
        fragmentMap.put("测试抽取的bottomnavigation",new TestBottomNavigationFragment());
        fragmentMap.put("测试多样式的bottomnavigation",new TestBottomN2());
        fragmentMap.put("tablayout合集，可实现顶部底部导航栏",new TestTabLayout_ALL());
        fragmentMap.put("tablayout,直接使用tablayout实现顶部导航",new TestTabLayoutFragment());
        fragmentMap.put("tablayout使用，顶部，关联ViewPager，横向铺满",new TestTLUpFragmentVP1());
        fragmentMap.put("tablayout使用，顶部，关联ViewPager，居中不铺满",new TestTLUpFragmentVP2());
        fragmentMap.put("tablayout使用，顶部，关联Fragment",new TestTLUpFragmentF());
        fragmentMap.put("tablayout使用：底部，关联ViewPager",new TestTLBottomViewpager());
        fragmentMap.put("tablayout使用：底部，关联FrameLayout",new TestTLBottomFrameLayout());
        fragmentMap.put("测试内置浏览器",new BrowserFragment());
        fragmentMap.put("测试lib_recyclerview",new TestLibRecyclerViewFragment());
        fragmentMap.put("测试lib_recyclerview_adapter_helper",new TestLibRecyclerViewAdapterHelper());
        fragmentMap.put("测试综合使用的recyclerview",new TestMyUseRecyclerView());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<String> arrs = new ArrayList<>();
        arrs.addAll(fragmentMap.keySet());
        setListAdapter(new ArrayAdapter<String >(this,android.R.layout.simple_list_item_1,android.R.id.text1,arrs));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        BaseApplication.showFragment = fragmentMap.get(((TextView)v).getText().toString().trim());
        Intent intent = new Intent(this,ShowActivity.class);
        startActivity(intent);
    }
}
