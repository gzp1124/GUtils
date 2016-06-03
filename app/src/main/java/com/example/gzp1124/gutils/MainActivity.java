package com.example.gzp1124.gutils;


import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gzp1124.gutils.fragments_for_test.HaveHeaderViewPagerFragment;
import com.example.gzp1124.gutils.fragments_for_test.SocialTestFragment;
import com.example.gzp1124.gutils.fragments_for_test.TimeTaskTestFragment;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class MainActivity extends ListActivity {
    private static Map<String,Fragment> fragmentMap = new LinkedHashMap<>();

    static {
        fragmentMap.put("社会化",new SocialTestFragment());
        fragmentMap.put("定时器",new TimeTaskTestFragment());
        fragmentMap.put("带有头布局的viewpager",new HaveHeaderViewPagerFragment());
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
