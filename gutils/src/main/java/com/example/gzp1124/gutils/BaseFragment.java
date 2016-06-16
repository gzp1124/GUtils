package com.example.gzp1124.gutils;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * author：高志鹏 on 16/5/18 10:35
 * email:imbagaozp@163.com
 */
public class BaseFragment extends Fragment {
    protected Activity gActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gActivity = getActivity();
    }

}
