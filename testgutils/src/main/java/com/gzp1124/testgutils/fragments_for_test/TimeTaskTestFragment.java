package com.gzp1124.testgutils.fragments_for_test;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gzp1124.gutils.BaseFragment;
import com.gzp1124.gutils.R;
import com.gzp1124.gutils.utils.GDateUtil;
import com.gzp1124.gutils.utils.GTimeTaskUtil;

import java.util.Date;

/**
 * 定时器测试
 * author：高志鹏 on 16/5/18 20:50
 * email:imbagaozp@163.com
 */
public class TimeTaskTestFragment extends BaseFragment implements View.OnClickListener {

    /*
    说明：
        开启定时器
        关闭定时器
        回调成功后显示当前时间到textview上

        基本使用：
        1. GTimeTaskUtil.setAlarmReceiverSuccess 定时器执行的动作
        2. GTimeTaskUtil.startRequestAlarm 开启定时器
        3. GTimeTaskUtil.cancelRequestAlarm 停止定时器

        注意：在页面关闭的时候要停止定时器，如果在后台执行定时器操作，另论

     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.test_time_task_fragment,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final TextView tv = (TextView) view.findViewById(R.id.show_text);

        //指定执行的任务
        GTimeTaskUtil.setAlarmReceiverSuccess(new GTimeTaskUtil.GAlarmReceiverInterface() {
            @Override
            public void receiverSuccess(Intent intent) {
                Date date = new Date();
                long time = date.getTime();
                tv.setText("当前时间"+time);
            }
        });

        view.findViewById(R.id.start).setOnClickListener(this);
        view.findViewById(R.id.end).setOnClickListener(this);
        view.findViewById(R.id.start1).setOnClickListener(this);
        view.findViewById(R.id.start2).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.start) {//这里测试的是3秒后开始，每隔1秒执行一次，可以通过calendar指定具体时间
            GTimeTaskUtil.startRequestAlarm(GTimeTaskUtil.DEFAULT_ACTION);

        } else if (i == R.id.end) {
            GTimeTaskUtil.cancelRequestAlarm(GTimeTaskUtil.DEFAULT_ACTION);

        } else if (i == R.id.start1) {
            GTimeTaskUtil.startRequestAlarm(1000, 2000, GTimeTaskUtil.DEFAULT_ACTION);

        } else if (i == R.id.start2) {//                GTimeTaskUtil.startRequestAlarm(System.currentTimeMillis()+2000,GTimeTaskUtil.DEFAULT_ACTION);

            GTimeTaskUtil.startRequestAlarm(GDateUtil.getSpecifiedTimestamp("2016-06-03 17:47:00", GDateUtil.TIME_FORMAT), GTimeTaskUtil.DEFAULT_ACTION);


        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        GTimeTaskUtil.cancelRequestAlarm(GTimeTaskUtil.DEFAULT_ACTION);
    }
}
