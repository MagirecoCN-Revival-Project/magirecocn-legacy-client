package jp.f4samurai.pnote;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import io.kamihama.totentanz.R;
import java.io.IOException;
import jp.f4samurai.AppActivity;
import jp.f4samurai.pnote.util.PnoteUtil;

/* loaded from: classes.dex */
public class PnoteHelper implements PnoteUtil.RegistDeviceCallback {
    private static AppActivity sAppActivity;
    private static String sTmpMid;
    private static Boolean isRegistDevice = false;
    private static final String TAG = "PnoteHelper";

    /* JADX INFO: Access modifiers changed from: private */
    public static native void callStartRegist();

    public static void _callStartRegist() {
        sAppActivity.runOnGLThread(new Runnable() { // from class: jp.f4samurai.pnote.PnoteHelper.1
            @Override // java.lang.Runnable
            public void run() {
                PnoteHelper.callStartRegist();
            }
        });
    }

    public PnoteHelper() {
        AppActivity appActivity = (AppActivity) AppActivity.getContext();
        sAppActivity = appActivity;
        PnoteUtil.initialize(appActivity);
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel notificationChannel = new NotificationChannel(sAppActivity.getResources().getString(R.string.fcm_channel_id), "イベント通知", 4);
            notificationChannel.setDescription("イベントの開催等、ゲーム内のお知らせを通知します。");
            notificationChannel.setLockscreenVisibility(1);
            notificationChannel.enableVibration(true);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(-16711936);
            notificationChannel.setShowBadge(true);
            ((NotificationManager) sAppActivity.getSystemService("notification")).createNotificationChannel(notificationChannel);
        }
    }

    public static void startRegist(final String str) {
        isRegistDevice = true;
        sAppActivity.runOnUiThread(new Runnable() { // from class: jp.f4samurai.pnote.PnoteHelper.2
            @Override // java.lang.Runnable
            public void run() {
                if (FirebaseApp.getApps(PnoteHelper.sAppActivity).isEmpty()) {
                    return;
                }
                PnoteUtil.registDevice(PnoteHelper.sAppActivity, FirebaseInstanceId.getInstance().getToken(), str);
            }
        });
    }

    public static void unregist() {
        isRegistDevice = false;
        sAppActivity.runOnUiThread(new Runnable() { // from class: jp.f4samurai.pnote.PnoteHelper.3
            @Override // java.lang.Runnable
            public void run() {
                try {
                    FirebaseInstanceId.getInstance().deleteInstanceId();
                } catch (IOException e) {
                    e.getMessage();
                }
                PnoteUtil.unregistDevice(PnoteHelper.sAppActivity);
            }
        });
    }

    public static void sendTags(String str, String str2) {
        PnoteUtil.registTags(sAppActivity, str2, str);
    }

    public static Boolean getIsRegistDevice() {
        return isRegistDevice;
    }

    @Override // jp.f4samurai.pnote.util.PnoteUtil.RegistDeviceCallback
    public void registDeviceCallback() {
        String str = sTmpMid;
        if (str != null) {
            PnoteUtil.sendMessageCounter(sAppActivity, str);
            sTmpMid = null;
        }
    }

    public void onNewIntent(Intent intent) {
        String string;
        Bundle extras = intent.getExtras();
        if (extras == null || (string = extras.getString("mid")) == null) {
            return;
        }
        sTmpMid = string;
        PnoteUtil.setRegistDeviceCallback(this);
        _callStartRegist();
    }
}
