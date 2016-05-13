package com.example.gzp1124.gutils;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.gzp1124.gutils.utils.GToastUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GToastUtil.getInstance(this).show();
    }
}
