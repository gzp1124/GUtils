package com.gzp1124.testgutils.linshi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gzp1124.lib_ui.bottomnavigation.BottomNavigationFragment;
import com.gzp1124.lib_ui.fragment_tab_host.MainFragmentTabHostFragment;

/**
 * author：高志鹏 on 16/6/30 16:36
 * email:imbagaozp@163.com
 */
public class Frag2 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView = new TextView(getContext());
        textView.setText("2222222222");
        Button button = new Button(getContext());
        button.setText("dianji ");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                BottomNavigationFragment.setSelect(2);
            }
        });
        return button;
    }
}
