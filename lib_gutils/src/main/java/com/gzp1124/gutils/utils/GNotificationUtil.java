package com.gzp1124.gutils.utils;

import android.app.Notification;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.gzp1124.gutils.base.BaseApplication;
import com.gzp1124.gutils.R;

/**
 * author：高志鹏 on 16/5/19 10:19
 * email:imbagaozp@163.com
 */
public class GNotificationUtil {
    public static void simple(){
//        PendingIntent pi = PendingIntent.getActivity(this, 1000, intent,
//                PendingIntent.FLAG_CANCEL_CURRENT);
        String  contentTitle = "title";
        String contentText = "content";

        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                BaseApplication.gContext).setTicker(contentTitle).setContentTitle(contentTitle)
                .setContentText(contentText).setAutoCancel(true)
                /*.setContentIntent(pi)*/.setSmallIcon(R.mipmap.ic_launcher);

//        if (AppContext.get(AppConfig.KEY_NOTIFICATION_SOUND, true)) {
//            builder.setSound(Uri.parse("android.resource://"
//                    + AppContext.getInstance().getPackageName() + "/"
//                    + R.raw.notificationsound));
//        }
//        if (AppContext.get(AppConfig.KEY_NOTIFICATION_VIBRATION, true)) {
            long[] vibrate = { 0, 10, 20, 30 };
            builder.setVibrate(vibrate);
//        }

        Notification notification = builder.build();

        NotificationManagerCompat.from(BaseApplication.gContext).notify(
                R.string.message_come, notification);
    }

    public static void simple(String content){
//        PendingIntent pi = PendingIntent.getActivity(this, 1000, intent,
//                PendingIntent.FLAG_CANCEL_CURRENT);
        String  contentTitle = "title";
        String contentText = content;

        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                BaseApplication.gContext).setTicker(contentTitle).setContentTitle(contentTitle)
                .setContentText(contentText).setAutoCancel(true)
                /*.setContentIntent(pi)*/.setSmallIcon(R.mipmap.ic_launcher);

//        if (AppContext.get(AppConfig.KEY_NOTIFICATION_SOUND, true)) {
//            builder.setSound(Uri.parse("android.resource://"
//                    + AppContext.getInstance().getPackageName() + "/"
//                    + R.raw.notificationsound));
//        }
//        if (AppContext.get(AppConfig.KEY_NOTIFICATION_VIBRATION, true)) {
        long[] vibrate = { 0, 10, 20, 30 };
        builder.setVibrate(vibrate);
//        }

        Notification notification = builder.build();

        NotificationManagerCompat.from(BaseApplication.gContext).notify(
                R.string.message_come, notification);
    }
}
