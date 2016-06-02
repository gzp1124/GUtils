package com.example.gzp1124.gutils.fragments_for_test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gzp1124.gutils.BaseFragment;
import com.example.gzp1124.gutils.MainActivity;
import com.example.gzp1124.gutils.R;
import com.example.gzp1124.gutils.three_ui_widget.have_header_viewpager.GHaveHeaderViewpagerUtil;
import com.example.gzp1124.gutils.three_ui_widget.have_header_viewpager.HVFragment;
import com.example.gzp1124.gutils.three_ui_widget.have_header_viewpager.HVMyListFragment;
import com.example.gzp1124.gutils.three_ui_widget.have_header_viewpager.HVMyScrollFragment;

import java.util.LinkedHashMap;

/**
 * author：高志鹏 on 16/6/2 18:07
 * email:imbagaozp@163.com
 */
public class HaveHeaderViewPagerFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        GHaveHeaderViewpagerUtil ghvUtil = new GHaveHeaderViewpagerUtil(getActivity());
        LinkedHashMap<String,HVFragment.FullContentView> map = new LinkedHashMap<>();
        map.put("货架",new HVMyScrollFragment.FullScrollview(){

            @Override
            public View fullSCrollview() {
                TextView textView = new TextView(getActivity());
                textView.setText("拉开纠纷的");
                return textView;
            }
        });
        map.put("资料", new HVMyListFragment.FullListView() {
            @Override
            public void fullListView(ListView listView) {
//                listView.setAdapter(new ArrayAdapter<String >(getActivity(),android.R.layout.simple_list_item_1,
//                        android.R.id.text1,getResources().getStringArray(R.array.list)));
            }
        });
        map.put("资料1", new HVMyListFragment.FullListView() {
            @Override
            public void fullListView(ListView listView) {
//                listView.setAdapter(new ArrayAdapter<String >(getActivity(),android.R.layout.simple_list_item_1,
//                        android.R.id.text1,getResources().getStringArray(R.array.list)));
            }
        });
        ghvUtil.setTabfragments(map);

//        ghvUtil.setmyAddHeaderView(View.inflate(getActivity(),R.layout.hh,null));

        ghvUtil.setTabBGColor("#33ff0000");

//        ghvUtil.setTabHeaderBackground(getResources().getDrawable(R.drawable.ab));

        return ghvUtil.getRootView();
    }
}
