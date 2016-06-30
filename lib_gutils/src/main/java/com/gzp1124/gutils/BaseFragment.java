package com.gzp1124.gutils;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * author：高志鹏 on 16/5/18 10:35
 * email:imbagaozp@163.com
 */
public class BaseFragment extends Fragment {
    protected Activity gActivity;
    protected View gView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        gActivity = getActivity();
        if (getLayoutId() != 0) {
            gView = inflater.inflate(getLayoutId(), null);
        }
        return gView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    protected void initViews() {

    }

    protected int getLayoutId(){
        return 0;
    }

}
