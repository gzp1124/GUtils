package com.gzp1124.testgutils.fragments_for_test;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.gzp1124.gutils.base.BaseApplication;
import com.gzp1124.gutils.base.BaseFragment;
import com.gzp1124.lib_ui.bottomnavigation_2.BottomNavigationFragment2;
import com.gzp1124.testgutils.ShowActivity;

import java.util.ArrayList;

/**
 * author：高志鹏 on 16/7/2 10:03
 * email:imbagaozp@163.com
 */
public class TestBottomN2 extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ListView listview = new ListView(getContext());
        final ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("MODE_DEFAULT--BACKGROUND_STYLE_DEFAULT");
//        arrayList.add("MODE_DEFAULT--BACKGROUND_STYLE_STATIC");
//        arrayList.add("MODE_DEFAULT--BACKGROUND_STYLE_RIPPLE");
//
//        arrayList.add("MODE_FIXED--BACKGROUND_STYLE_DEFAULT");
//        arrayList.add("MODE_FIXED--BACKGROUND_STYLE_STATIC");
//        arrayList.add("MODE_FIXED--BACKGROUND_STYLE_RIPPLE");
//
//        arrayList.add("MODE_SHIFTING--BACKGROUND_STYLE_DEFAULT");
//        arrayList.add("MODE_SHIFTING--BACKGROUND_STYLE_STATIC");
        arrayList.add("MODE_SHIFTING--BACKGROUND_STYLE_RIPPLE");

        listview.setAdapter(new ArrayAdapter<String >(getContext(),android.R.layout.simple_expandable_list_item_1,arrayList));
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BaseApplication.showFragment = new TestBottomNavigationFrag2();
                String s = arrayList.get(position);
                String[] ss = s.split("-");
                BottomNavigationFragment2.Mode mode = null;
                BottomNavigationFragment2.BackgroundStyle backgroundStyle = null;
                for (int i = 0 ;i<ss.length;i++){
                    if ("MODE_DEFAULT".equals(ss[0])){
                        mode = BottomNavigationFragment2.Mode.MODE_DEFAULT;
                    }else if("MODE_FIXED".equals(ss[0])){
                        mode = BottomNavigationFragment2.Mode.MODE_FIXED;
                    }else {
                        mode = BottomNavigationFragment2.Mode.MODE_SHIFTING;
                    }
                    if ("BACKGROUND_STYLE_DEFAULT".equals(ss[1])){
                        backgroundStyle = BottomNavigationFragment2.BackgroundStyle.BACKGROUND_STYLE_DEFAULT;
                    }else if("BACKGROUND_STYLE_STATIC".equals(ss[1])){
                        backgroundStyle = BottomNavigationFragment2.BackgroundStyle.BACKGROUND_STYLE_STATIC;
                    }else {
                        backgroundStyle = BottomNavigationFragment2.BackgroundStyle.BACKGROUND_STYLE_RIPPLE;
                    }
                }
                TestBottomNavigationFrag2.setMB(mode,backgroundStyle);
                Intent intent = new Intent(getActivity(),ShowActivity.class);
                startActivity(intent);
            }
        });
        return listview;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected int getLayoutId() {
        return 0;
    }
}
