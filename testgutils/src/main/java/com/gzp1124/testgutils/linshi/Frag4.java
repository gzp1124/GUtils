package com.gzp1124.testgutils.linshi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gzp1124.gutils.base.BaseFragment;

/**
 * author：高志鹏 on 16/6/30 16:36
 * email:imbagaozp@163.com
 */
public class Frag4 extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView = new TextView(getContext());
        textView.setText("44444444");
        return textView;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected int getLayoutId() {
        return 0;
    }
}
