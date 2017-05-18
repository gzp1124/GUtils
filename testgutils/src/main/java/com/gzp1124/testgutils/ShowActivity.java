package com.gzp1124.testgutils;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.gzp1124.testgutils.R;
import com.gzp1124.gutils.three_platform.social.GSocialUtil;
import com.gzp1124.gutils.utils.GToastUtil;


/**
 * 显示各种fragment
 * author：高志鹏 on 16/5/18 16:16
 * email:imbagaozp@163.com
 */
public class ShowActivity extends FragmentActivity {

    private View bb;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity_show);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction tx = fm.beginTransaction();
        tx.add(R.id.content_fragment,BaseApp.showFragment ,"showFragment");
        tx.commit();
        bb = findViewById(R.id.testbtn);

//        GSocialUtil.getShareInstance().regWX();
    }

    public void anim(View view){
        GoodsAnimUtil.setOnEndAnimListener(new GoodsAnimUtil.OnEndAnimListener() {
            @Override
            public void onEndAnim() {

            }
        });
        GoodsAnimUtil.setAnim(this,view,bb);
    }


}
