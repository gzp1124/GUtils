package com.gzp1124.gutils.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import com.gzp1124.gutils.base.BaseApplication;

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
    public static String DEFAULT_ACTION = "gzp1124.alarm.service";
    public static String CURRENT_ACTION = "";
    private static AlarmManager mAlarmMgr;
    private static Context mContext = BaseApplication.gContext;

    private static void init(String action){
        CURRENT_ACTION = action;
        if (mAlarmMgr == null) {
            mAlarmMgr = (AlarmManager) mContext.getSystemService(mContext.ALARM_SERVICE);
        }else{
            cancelRequestAlarm(action);
        }
    }

    /** 开启，每隔1秒执行一次 */
    public static void startRequestAlarm(String action) {
        init(action);
        // 1秒钟后执行一次getOperationIntent方法
        mAlarmMgr.setRepeating(AlarmManager.RTC_WAKEUP,
                0,1000,
                getOperationIntent(action));
    }

    /**
     * 开启，指定时间后执行一次，要用指定时间的时间戳，不是相对时间  System.currentTimeMillis() + 1000
     * @param startTime 执行时间，毫秒，如 System.currentTimeMillis() + 1000
     * @param action 事件
     */
    public static void startRequestAlarm(long startTime,String action){
        init(action);
        // 指定时间为相对于1970年的时间毫秒值
        mAlarmMgr.set(AlarmManager.RTC_WAKEUP,
                startTime,
                getOperationIntent(action));
    }

    /**
     * 开启，首次间隔时间后执行一次，每隔一段时间后执行一次
     * @param startTime 第一次执行间隔的时间，单位毫秒，如：1000
     * @param intervalTime 以后每次的间隔时间，毫秒
     * @param action 事件
     */
    public static void startRequestAlarm(long startTime,long intervalTime,String action){
        init(action);
        // 指定时间为相对于1970年的时间毫秒值
        mAlarmMgr.setRepeating(AlarmManager.RTC_WAKEUP,
                startTime ,intervalTime,
                getOperationIntent(action));
    }

    /** 关闭 */
    public static void cancelRequestAlarm(String action) {
        if (mAlarmMgr != null){
            mAlarmMgr.cancel(getOperationIntent(action));
        }
    }

    private static PendingIntent getOperationIntent(String action) {
        Intent intent = new Intent(mContext, AlarmReceiver.class);
        intent.setAction(action);
        if (mExtras != null){
            for (String key:mExtras.keySet()){
                if (TextUtils.isEmpty(key)||mExtras.get(key)==null){
                    Toast.makeText(BaseApplication.gContext,"不能传空",Toast.LENGTH_SHORT).show();
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





