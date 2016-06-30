package com.gzp1124.testgutils.fragments_for_test;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gzp1124.testgutils.R;
import com.gzp1124.gutils.BaseFragment;
import com.gzp1124.gutils.utils.GNotificationUtil;
import com.gzp1124.gutils.utils.GTimeTaskUtil;

/**
 * author：高志鹏 on 16/5/19 10:14
 * email:imbagaozp@163.com
 */
public class SendMyBroadFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.test_send_mybroad,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GTimeTaskUtil.setAlarmReceiverSuccess(new GTimeTaskUtil.GAlarmReceiverInterface() {
            @Override
            public void receiverSuccess(Intent intent) {
                GNotificationUtil.simple();
            }
        });
        GTimeTaskUtil.startRequestAlarm(System.currentTimeMillis(),GTimeTaskUtil.DEFAULT_ACTION);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        GTimeTaskUtil.cancelRequestAlarm(GTimeTaskUtil.DEFAULT_ACTION);
    }
}
