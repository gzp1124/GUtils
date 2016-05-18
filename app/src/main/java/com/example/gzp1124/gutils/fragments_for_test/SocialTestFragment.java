package com.example.gzp1124.gutils.fragments_for_test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gzp1124.gutils.BaseFragment;
import com.example.gzp1124.gutils.R;

/**
 * 社会化功能测试
 * author：高志鹏 on 16/5/18 13:35
 * email:imbagaozp@163.com
 */
public class SocialTestFragment extends BaseFragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.test_social_fragment,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.share).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.share:

                break;
        }
    }
}
