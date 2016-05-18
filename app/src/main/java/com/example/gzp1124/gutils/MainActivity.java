package com.example.gzp1124.gutils;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.gzp1124.gutils.fragments_for_test.SocialTestFragment;
import com.example.gzp1124.gutils.fragments_for_test.TimeTaskTestFragment;
import com.example.gzp1124.gutils.utils.GSystemLocationUtil;
import com.example.gzp1124.gutils.utils.GToastUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.test1).setOnClickListener(this);
        findViewById(R.id.timetask).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;
        switch (v.getId()){
            case R.id.test1:
                fragment = new SocialTestFragment();
                break;// 15535801439
            case R.id.timetask:
                fragment = new TimeTaskTestFragment();
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
