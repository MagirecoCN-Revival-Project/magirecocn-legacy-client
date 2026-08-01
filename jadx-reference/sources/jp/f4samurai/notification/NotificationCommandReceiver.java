package jp.f4samurai.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import io.kamihama.totentanz.R;
import jp.f4samurai.AppActivity;

/* loaded from: classes.dex */
public class NotificationCommandReceiver extends BroadcastReceiver {
    private static final String TAG = "NotificationCommandReceiver";

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        Notification build;
        int intExtra = intent.getIntExtra("ALARM_TYPE", 0);
        String stringExtra = intent.getStringExtra("ALARM_MESSAGE");
        PendingIntent activity = PendingIntent.getActivity(context, intExtra, new Intent(context, (Class<?>) AppActivity.class), 201326592);
        if (Build.VERSION.SDK_INT >= 26) {
            build = new NotificationCompat.Builder(context, intent.getStringExtra("ALARM_CHANNEL_ID")).setSmallIcon(R.mipmap.ic_stat_notify).setContentTitle(context.getString(R.string.app_name)).setTicker(stringExtra).setContentText(stringExtra).setWhen(System.currentTimeMillis()).setContentIntent(activity).setAutoCancel(true).build();
        } else {
            build = new NotificationCompat.Builder(context).setSmallIcon(R.mipmap.ic_stat_notify).setContentTitle(context.getString(R.string.app_name)).setTicker(stringExtra).setContentText(stringExtra).setWhen(System.currentTimeMillis()).setContentIntent(activity).setAutoCancel(true).build();
        }
        NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
        notificationManager.cancel(intExtra);
        notificationManager.notify(intExtra, build);
    }
}
