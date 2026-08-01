package jp.f4samurai.notification;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import java.util.Calendar;
import jp.f4samurai.AppActivity;

/* loaded from: classes.dex */
public class NotificationCommandHelper {
    private static final String TAG = "NotificationCommandHelper";
    private static AppActivity sAppActivity = null;
    private static NotificationManager sNotificationManager = null;
    private static final long sWeekTime = 604800000;

    public NotificationCommandHelper() {
        sAppActivity = (AppActivity) AppActivity.getContext();
        if (Build.VERSION.SDK_INT >= 26) {
            sNotificationManager = (NotificationManager) AppActivity.getContext().getSystemService("notification");
            sNotificationManager.createNotificationChannel(createChannel("channel_once_alarm", "AP回復通知", "APの全回復を通知します。"));
        }
    }

    public static void setOnceAlarm(int i, int i2, String str) {
        Intent intent = new Intent(sAppActivity, (Class<?>) NotificationCommandReceiver.class);
        intent.putExtra("ALARM_TYPE", i);
        intent.putExtra("ALARM_MESSAGE", str);
        intent.putExtra("ALARM_CHANNEL_ID", "channel_once_alarm");
        ((AlarmManager) sAppActivity.getSystemService(NotificationCompat.CATEGORY_ALARM)).setExact(1, Calendar.getInstance().getTimeInMillis() + (i2 * 1000), PendingIntent.getBroadcast(sAppActivity, i, intent, 201326592));
    }

    public static void cancelAlarm(int i) {
        PendingIntent broadcast = PendingIntent.getBroadcast(sAppActivity, i, new Intent(sAppActivity, (Class<?>) NotificationCommandReceiver.class), 335544320);
        ((AlarmManager) sAppActivity.getSystemService(NotificationCompat.CATEGORY_ALARM)).cancel(broadcast);
        broadcast.cancel();
    }

    private NotificationChannel createChannel(String str, String str2, String str3) {
        NotificationChannel notificationChannel = new NotificationChannel(str, str2, 4);
        notificationChannel.setDescription(str3);
        notificationChannel.setLockscreenVisibility(1);
        notificationChannel.enableVibration(true);
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(-16711936);
        notificationChannel.setShowBadge(true);
        return notificationChannel;
    }
}
