package jp.f4samurai.thinkingdata;

import cn.thinkingdata.analytics.TDAnalytics;
import jp.f4samurai.AppActivity;

/* loaded from: classes.dex */
public class ThinkingDataHelper {
    private static final String TAG = "ThinkingDataHelper";
    private static String TE_APP_KEY = "7ebb302663eb420ba26bd536889a04a2";
    private static String TE_URL = "https://te-data.magi-reco.com";
    private static AppActivity sAppActivity;

    /* JADX INFO: Access modifiers changed from: private */
    public static native void callLogin();

    public ThinkingDataHelper() {
        sAppActivity = (AppActivity) AppActivity.getContext();
    }

    public static void initialize() {
        sAppActivity.runOnGLThread(new Runnable() { // from class: jp.f4samurai.thinkingdata.ThinkingDataHelper.1
            @Override // java.lang.Runnable
            public void run() {
                TDAnalytics.init(ThinkingDataHelper.sAppActivity, ThinkingDataHelper.TE_APP_KEY, ThinkingDataHelper.TE_URL);
                TDAnalytics.enableAutoTrack(35);
            }
        });
    }

    public static void _login() {
        sAppActivity.runOnGLThread(new Runnable() { // from class: jp.f4samurai.thinkingdata.ThinkingDataHelper.2
            @Override // java.lang.Runnable
            public void run() {
                ThinkingDataHelper.callLogin();
            }
        });
    }

    public static void login(final String str) {
        sAppActivity.runOnGLThread(new Runnable() { // from class: jp.f4samurai.thinkingdata.ThinkingDataHelper.3
            @Override // java.lang.Runnable
            public void run() {
                if (str.isEmpty()) {
                    return;
                }
                TDAnalytics.login(str);
            }
        });
    }
}
