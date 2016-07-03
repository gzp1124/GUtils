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
import com.gzp1124.testgutils.ShowActivity;

import java.util.ArrayList;

/**
 * tablayout 总的展示
 *
 * author：高志鹏 on 16/7/2 12:45
 * email:imbagaozp@163.com
 */
public class TestTabLayout_ALL extends BaseFragment {
    Class<BaseFragment>[] classs = new Class[]{TestTabLayout1.class,TestTabLayout2.class,TestTabLayout3.class};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ListView listView = new ListView(getContext());
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("作为顶部导航各种效果");
        arrayList.add("作为底部导航各种效果");
        arrayList.add("作为顶部导航各种特殊效果");
        listView.setAdapter(new ArrayAdapter<String >(getContext(),android.R.layout.simple_expandable_list_item_1,arrayList));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    BaseApplication.showFragment = classs[position].newInstance();
                    startActivity(new Intent(getActivity(), ShowActivity.class));
                } catch (java.lang.InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
        return listView;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected int getLayoutId() {
        return 0;
    }
}
