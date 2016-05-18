package com.example.gzp1124.gutils;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.PersistableBundle;

/**
 * 显示各种fragment
 * author：高志鹏 on 16/5/18 16:16
 * email:imbagaozp@163.com
 */
public class ShowActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction tx = fm.beginTransaction();
        tx.add(R.id.content_fragment,BaseApplication.showFragment ,"showFragment");
        tx.commit();
    }
}
