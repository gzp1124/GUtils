package com.example.gzp1124.gutils;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.gzp1124.gutils.three_platform.social.GSocialUtil;
import com.example.gzp1124.gutils.utils.GTimeTaskUtil;
import com.example.gzp1124.gutils.utils.NotificationUtil;

/**
 * 显示各种fragment
 * author：高志鹏 on 16/5/18 16:16
 * email:imbagaozp@163.com
 */
public class ShowActivity extends FragmentActivity {

    public static void useTask(final String show){
        GTimeTaskUtil.setAlarmReceiverSuccess(new GTimeTaskUtil.GAlarmReceiverInterface() {
            @Override
            public void receiverSuccess(Intent intent) {
                NotificationUtil.simple(show);
            }
        });
        GTimeTaskUtil.startRequestAlarm(System.currentTimeMillis(),GTimeTaskUtil.ALARM_ACTION);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction tx = fm.beginTransaction();
        tx.add(R.id.content_fragment,BaseApplication.showFragment ,"showFragment");
        tx.commit();

        GSocialUtil.getShareInstance().regWX();
        useTask("show create");
    }

    @Override
    protected void onResume() {
        super.onResume();
        useTask("show resume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        useTask("show restart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        useTask("show start");
    }

}
