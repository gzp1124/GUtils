package com.gzp1124.testgutils.fragments_for_test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gzp1124.testgutils.R;
import com.gzp1124.gutils.BaseFragment;
import com.gzp1124.lib_ui.have_header_viewpager.GHaveHeaderViewpagerFragment;
import com.gzp1124.lib_ui.have_header_viewpager.HVFragment;
import com.gzp1124.lib_ui.have_header_viewpager.HVMyListFragment;
import com.gzp1124.lib_ui.have_header_viewpager.HVMyScrollFragment;

import java.util.LinkedHashMap;

/**
 * author：高志鹏 on 16/6/2 18:07
 * email:imbagaozp@163.com
 */
public class HaveHeaderViewPagerFragment extends BaseFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                listView.setAdapter(new ArrayAdapter<String >(getActivity(),android.R.layout.simple_list_item_1,
                        android.R.id.text1,getResources().getStringArray(R.array.list)));
            }
        });
        map.put("资料1", new HVMyListFragment.FullListView() {
            @Override
            public void fullListView(ListView listView) {
//                listView.setAdapter(new ArrayAdapter<String >(getActivity(),android.R.layout.simple_list_item_1,
//                        android.R.id.text1,getResources().getStringArray(R.array.list)));
            }
        });
        GHaveHeaderViewpagerFragment.setTabfragments(map);

//        GHaveHeaderViewpagerFragment.setmyAddHeaderView(View.inflate(getActivity(),R.layout.hh,null));

        GHaveHeaderViewpagerFragment.setTabBGColor("#33ff0000");

        GHaveHeaderViewpagerFragment.setTabHeaderBackground(getResources().getDrawable(R.drawable.test));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.test_have_header_viewpager_fragment,null);
    }
}
