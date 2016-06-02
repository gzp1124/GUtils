package com.example.gzp1124.gutils;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.gzp1124.gutils.fragments_for_test.HaveHeaderViewPagerFragment;
import com.example.gzp1124.gutils.fragments_for_test.SendMyBroadFragment;
import com.example.gzp1124.gutils.fragments_for_test.SocialTestFragment;
import com.example.gzp1124.gutils.fragments_for_test.TimeTaskTestFragment;
import com.example.gzp1124.gutils.utils.GSystemLocationUtil;
import com.example.gzp1124.gutils.utils.GTimeTaskUtil;
import com.example.gzp1124.gutils.utils.GToastUtil;
import com.example.gzp1124.gutils.utils.NotificationUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.test1).setOnClickListener(this);
        findViewById(R.id.timetask).setOnClickListener(this);
        findViewById(R.id.have_header_viewpager).setOnClickListener(this);
        useTask("create");
    }

    @Override
    protected void onResume() {
        super.onResume();
        useTask("resume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        useTask("restart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        useTask("start");
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;
        switch (v.getId()){
            case R.id.test1:
                fragment = new SocialTestFragment();
//                fragment = new SendMyBroadFragment();
                break;// 15535801439
            case R.id.timetask:
                fragment = new TimeTaskTestFragment();
                break;
            case R.id.have_header_viewpager:
                fragment = new HaveHeaderViewPagerFragment();
                break;
            default:
                break;
        }
        if (fragment == null){
            GToastUtil.getInstance().setText("fragment没有设置").show();
            return;
        }
        BaseApplication.showFragment = fragment;
        Intent intent = new Intent(this,ShowActivity.class);
        startActivity(intent);
    }
}
