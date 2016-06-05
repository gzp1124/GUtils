package com.example.gzp1124.gutils;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.gzp1124.gutils.three_platform.social.GSocialUtil;

/**
 * 显示各种fragment
 * author：高志鹏 on 16/5/18 16:16
 * email:imbagaozp@163.com
 */
public class ShowActivity extends FragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity_show);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction tx = fm.beginTransaction();
        tx.add(R.id.content_fragment,BaseApplication.showFragment ,"showFragment");
        tx.commit();

        GSocialUtil.getShareInstance().regWX();
    }
}
