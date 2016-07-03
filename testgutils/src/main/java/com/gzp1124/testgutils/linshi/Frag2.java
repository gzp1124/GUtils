package com.gzp1124.testgutils.linshi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gzp1124.gutils.base.BaseFragment;
import com.gzp1124.lib_ui.bottomnavigation.BottomNavigationFragment;
import com.gzp1124.lib_ui.fragment_tab_host.MainFragmentTabHostFragment;
import com.gzp1124.lib_ui.tab_layout.CommonTabLayout;
import com.gzp1124.lib_ui.tab_layout.TabLayoutBottomFragmentWithViewPager;
import com.gzp1124.log.GLog;

/**
 * author：高志鹏 on 16/6/30 16:36
 * email:imbagaozp@163.com
 */
public class Frag2 extends BaseFragment {
    private CommonTabLayout commonTabLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        GLog.i("frag2 oncreate view");
        TextView textView = new TextView(getContext());
        textView.setText("2222222222");
        Button button = new Button(getContext());
        button.setText("dianji ");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                BottomNavigationFragment.setSelect(2);
                if (commonTabLayout != null){
                    commonTabLayout.setCurrentTab(2);
                    commonTabLayout.showMsg(2,11);
                }else {
                    GLog.i("为空");
                }
            }
        });
        return button;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void requestData() {
        super.requestData();
        GLog.i("frag2 request data");
    }

    @Override
    protected void onViewInvisible() {
        super.onViewInvisible();
        commonTabLayout = TabLayoutBottomFragmentWithViewPager.getTabLayout();
    }
}
