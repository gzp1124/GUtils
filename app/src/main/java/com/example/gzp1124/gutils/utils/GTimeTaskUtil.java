package com.example.gzp1124.gutils.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.gzp1124.gutils.BaseApplication;

import java.io.Serializable;
import java.util.Map;

/**
 * 定时任务工具
 * author：高志鹏 on 16/5/18 17:58
 * email:imbagaozp@163.com
 *
 * 调用方式：
 *  开启：startRequestAlarm
 *  关闭：cancelRequestAlarm
 *  设置定时器携带的数据：setExtras，以Map形式
 *  设置定时器到时间的回调：setAlarmReceiverSuccess
 */
public class GTimeTaskUtil {
    public static String ALARM_ACTION = "gzp1124.alarm.service";
    private static AlarmManager mAlarmMgr;
    private static Context mContext = BaseApplication.gContext;

    private static void init(){
        mAlarmMgr = (AlarmManager) mContext.getSystemService(mContext.ALARM_SERVICE);
    }

    /** 开启，3秒钟后执行一次，每隔1秒执行一次 */
    public static void startRequestAlarm(String action) {
        cancelRequestAlarm(action);
        // 3秒钟后执行一次getOperationIntent方法
        mAlarmMgr.setRepeating(AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + 3000,1000,
                getOperationIntent(action));
    }

    /** 开启，指定时间后执行一次  System.currentTimeMillis() + 1000  */
    public static void startRequestAlarm(long startTime,String action){
        cancelRequestAlarm(action);
        // 3秒钟后执行一次getOperationIntent方法
        mAlarmMgr.set(AlarmManager.RTC_WAKEUP,
                startTime,
                getOperationIntent(action));
    }

    /** 开启，指定时间后执行一次，每隔一段时间后执行一次 */
    public static void startRequestAlarm(long startTime,long intervalTime,String action){
        cancelRequestAlarm(action);
        // 3秒钟后执行一次getOperationIntent方法
        mAlarmMgr.setRepeating(AlarmManager.RTC_WAKEUP,
                startTime ,intervalTime,
                getOperationIntent(action));
    }

    /** 关闭 */
    public static void cancelRequestAlarm(String action) {
        if (mAlarmMgr == null){
            init();
        }
        mAlarmMgr.cancel(getOperationIntent(action));
    }

    private static PendingIntent getOperationIntent(String action) {
//        AlarmReceiver alarmReceiver = new AlarmReceiver();
//        IntentFilter filter = new IntentFilter();

//        filter.addAction(action);
        Intent intent = new Intent(mContext, AlarmReceiver.class);
        intent.setAction(action);
        if (mExtras != null){
            for (String key:mExtras.keySet()){
                if (TextUtils.isEmpty(key)||mExtras.get(key)==null){
                    GToastUtil.getInstance().setText("不能传空");
                    break;
                }
                intent.putExtra(key,mExtras.get(key));
            }
        }
        PendingIntent operation = PendingIntent.getBroadcast(mContext, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        return operation;
    }

    private static Map<String,Serializable> mExtras = null;

    /**
     * 添加定时器携带的参数，以键值对的形式传递
     * id,12
     * @param extras
     */
    public static void setExtras(Map<String,Serializable> extras){
        mExtras = extras;
    }

    /**
     * 接收广播
     */
    public static class AlarmReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if (mAlarmReceiverInterface!=null){
                mAlarmReceiverInterface.receiverSuccess(intent);
            }else{
                Toast.makeText(BaseApplication.gContext,"定时任务执行成功，但是没有回调接口来处理",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public interface GAlarmReceiverInterface {
        void receiverSuccess(Intent intent);
    }

    private static GAlarmReceiverInterface mAlarmReceiverInterface;

    /**
     * 设置定时器到时间时的回调
     * @param alarmReceiverInterface
     */
    public static void setAlarmReceiverSuccess(GAlarmReceiverInterface alarmReceiverInterface){
        mAlarmReceiverInterface = alarmReceiverInterface;
    }
}





