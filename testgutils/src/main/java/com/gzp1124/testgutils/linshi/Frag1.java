package com.gzp1124.testgutils.linshi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.gzp1124.lib_ui.fragment_tab_host.FragmentTabHostTabFragment;
import com.gzp1124.log.GLog;

import java.util.ArrayList;

/**
 * author：高志鹏 on 16/6/30 16:36
 * email:imbagaozp@163.com
 */
public class Frag1 extends FragmentTabHostTabFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        GLog.i("frag1 oncreate view");
        return super.onCreateView(inflater,container,savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public View getLayoutView() {
        TextView textView = new TextView(getContext());
        textView.setText("11111");
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0;i<100;i++){
            strings.add("jfda--"+i);
        }
        ListView listView = new ListView(getContext());
        listView.setAdapter(new ArrayAdapter<String >(getActivity(),android.R.layout.simple_expandable_list_item_1,strings));
        return listView;
    }
}
