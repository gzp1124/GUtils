package com.gzp1124.testgutils.fragments_for_test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.gzp1124.gutils.base.BaseFragment;
import com.gzp1124.gutils.utils.GBeepManager;

/**
 * author：高志鹏 on 16/6/17 10:31
 * email:imbagaozp@163.com
 */
public class TestBeepFragment extends BaseFragment {

    private GBeepManager beepManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        beepManager = new GBeepManager(getActivity());
        Button button = new Button(container.getContext());
        button.setText("点击震动");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beepManager.playBeepSoundAndVibrate();
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
    public void onDestroy() {
        super.onDestroy();
        beepManager.close();
    }
}
