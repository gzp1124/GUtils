package com.gzp1124.testgutils.linshi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.gzp1124.gutils.base.BaseFragment;
import com.gzp1124.log.GLog;
import com.gzp1124.testgutils.ShowActivity;

import java.util.ArrayList;

/**
 * author：高志鹏 on 16/6/30 16:36
 * email:imbagaozp@163.com
 */
public class Frag1 extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        GLog.i("frag1 oncreate view");
        return getLayoutView();
    }

    @Override
    protected void initViews() {

    }

    public int getLayoutId() {
        return 0;
    }

    public View getLayoutView() {
        TextView textView = new TextView(getContext());
        textView.setText("11111");
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0;i<100;i++){
            strings.add("jfda--"+i);
        }
        ListView listView = new ListView(getContext());
        listView.setAdapter(new ArrayAdapter<String >(getActivity(),android.R.layout.simple_expandable_list_item_1,strings));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((ShowActivity)getActivity()).anim(view);
            }
        });
        return listView;
    }

    @Override
    protected void requestData() {
        super.requestData();
        GLog.i("frag1 request data");
    }
}
