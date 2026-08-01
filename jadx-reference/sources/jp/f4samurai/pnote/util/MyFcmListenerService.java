package jp.f4samurai.pnote.util;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import io.kamihama.totentanz.R;
import java.util.Map;
import jp.f4samurai.AppActivity;

/* loaded from: classes.dex */
public class MyFcmListenerService extends FirebaseMessagingService {
    private static final String TAG = "Pnote";

    @Override // com.google.firebase.messaging.FirebaseMessagingService
    public void onDeletedMessages() {
    }

    @Override // com.google.firebase.messaging.FirebaseMessagingService
    public void onMessageSent(String str) {
    }

    @Override // com.google.firebase.messaging.FirebaseMessagingService
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Map<String, String> data = remoteMessage.getData();
        if (isSilentNotification(data)) {
            return;
        }
        String str = data.get("launch");
        String str2 = data.get("mid");
        String str3 = data.get("alert");
        int parseInt = data.get("badge") != null ? Integer.parseInt(data.get("badge")) : 0;
        data.get("sound");
        generateNotification(this, str3, parseInt, str, str2, data);
    }

    private static void generateNotification(Context context, String str, int i, String str2, String str3, Map<String, String> map) {
        NotificationCompat.Builder builder;
        boolean z;
        Intent intent = new Intent(context, (Class<?>) AppActivity.class);
        if (str2 != null) {
            intent.putExtra("launch", str2);
        }
        if (str3 != null) {
            intent.putExtra("mid", str3);
        }
        intent.setFlags(536870912);
        PendingIntent activity = PendingIntent.getActivity(context, 0, intent, 201326592);
        if (Build.VERSION.SDK_INT >= 26) {
            builder = new NotificationCompat.Builder(context, context.getResources().getString(R.string.fcm_channel_id));
        } else {
            builder = new NotificationCompat.Builder(context);
        }
        builder.setContentIntent(activity);
        builder.setSmallIcon(R.mipmap.ic_stat_notify);
        String str4 = map.get("title");
        if (str4 == null || str4.isEmpty()) {
            str4 = context.getString(R.string.app_name);
        }
        builder.setContentTitle(str4);
        builder.setContentText(str);
        builder.setWhen(System.currentTimeMillis());
        if (i >= 0) {
            builder.setNumber(i);
        }
        String str5 = map.get("subtext");
        if (str4 != null && !str4.isEmpty()) {
            builder.setSubText(str5);
        }
        String str6 = map.get("info");
        if (str6 != null && !str6.isEmpty()) {
            builder.setContentInfo(str6);
        }
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        String str7 = map.get("big-content-title");
        if (str7 == null || str7.isEmpty()) {
            z = false;
        } else {
            bigTextStyle.setBigContentTitle(str7);
            z = true;
        }
        String str8 = map.get("big-text");
        if (str8 != null && !str8.isEmpty()) {
            str = str8;
            z = true;
        }
        bigTextStyle.bigText(str);
        String str9 = map.get("summary-text");
        if (str9 != null && !str9.isEmpty()) {
            bigTextStyle.setSummaryText(str9);
            z = true;
        }
        if (z) {
            builder.setStyle(bigTextStyle);
        }
        builder.setAutoCancel(true);
        NotificationManagerCompat.from(context).notify(0, builder.build());
    }

    private static boolean isSilentNotification(Map<String, String> map) {
        return map.containsKey("content-available") || map.containsKey("adjust_purpose");
    }
}
