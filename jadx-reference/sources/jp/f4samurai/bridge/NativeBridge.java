package jp.f4samurai.bridge;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import com.adjust.sdk.Adjust;
import com.google.firebase.iid.FirebaseInstanceId;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import jp.f4samurai.AppActivity;
import jp.f4samurai.backtrace.BacktraceHandler;

/* loaded from: classes.dex */
public class NativeBridge {
    private static final String TAG = "NativeBridge";
    private static AppActivity sAppActivity;
    private static ClipboardManager sClipboardManager;

    public static native void onBackKeyReleased();

    private static native void setRewardData(String str);

    public static void startBacktrace() {
    }

    public NativeBridge() {
        sAppActivity = (AppActivity) AppActivity.getContext();
        sClipboardManager = (ClipboardManager) AppActivity.getContext().getSystemService("clipboard");
    }

    public static void _setRewardData(String str) {
        setRewardData(str);
    }

    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    public static String getBundleId() {
        return sAppActivity.getPackageName();
    }

    public static String getAppVersion() {
        try {
            return sAppActivity.getPackageManager().getPackageInfo(sAppActivity.getPackageName(), 128).versionName;
        } catch (PackageManager.NameNotFoundException unused) {
            return "1.0.0";
        }
    }

    public static String getOSVersion() {
        return Build.VERSION.RELEASE;
    }

    public static String getDeviceName() {
        return Build.MODEL;
    }

    public static void openUrl(String str) {
        sAppActivity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
    }

    public static String getExternalStorageDirectory() {
        return Environment.getExternalStorageDirectory().getPath() + "/";
    }

    public static String getLive2dViewerDirectory() {
        return Environment.getExternalStorageDirectory().toString() + File.separator + ("Android" + File.separator + "obb" + File.separator + sAppActivity.getPackageName() + File.separator + "Live2dViewer" + File.separator);
    }

    public static void setAdjustPushToken() {
        Adjust.setPushToken(FirebaseInstanceId.getInstance().getToken(), AppActivity.getContext());
    }

    /* renamed from: jp.f4samurai.bridge.NativeBridge$1, reason: invalid class name */
    /* loaded from: classes.dex */
    class AnonymousClass1 implements Runnable {
        final /* synthetic */ String val$userId;

        AnonymousClass1(String str) {
            this.val$userId = str;
        }

        @Override // java.lang.Runnable
        public void run() {
            BacktraceHandler.setUserId(this.val$userId);
        }
    }

    public static void setBacktraceUserId(String str) {
    }

    /* renamed from: jp.f4samurai.bridge.NativeBridge$2, reason: invalid class name */
    /* loaded from: classes.dex */
    class AnonymousClass2 implements Runnable {
        final /* synthetic */ String val$message;

        AnonymousClass2(String str) {
            this.val$message = str;
        }

        @Override // java.lang.Runnable
        public void run() {
            BacktraceHandler.setLog(this.val$message);
        }
    }

    public static void setBacktraceLog(String str) {
    }

    /* renamed from: jp.f4samurai.bridge.NativeBridge$3, reason: invalid class name */
    /* loaded from: classes.dex */
    class AnonymousClass3 implements Runnable {
        final /* synthetic */ String val$key;
        final /* synthetic */ String val$value;

        AnonymousClass3(String str, String str2) {
            this.val$key = str;
            this.val$value = str2;
        }

        @Override // java.lang.Runnable
        public void run() {
            BacktraceHandler.setLogData(this.val$key, this.val$value);
        }
    }

    public static void setBacktraceLogData(String str, String str2) {
    }

    public static void setClipboard(String str) {
        sClipboardManager.setPrimaryClip(ClipData.newPlainText("text", str));
    }

    private static String getClipboard() {
        ClipData primaryClip = sClipboardManager.getPrimaryClip();
        return primaryClip != null ? primaryClip.getItemAt(0).getText().toString() : "";
    }

    public static int getRemainStorage() {
        StatFs statFs = new StatFs(Environment.getDataDirectory().getAbsolutePath());
        return (int) ((statFs.getAvailableBlocksLong() * statFs.getBlockSizeLong()) / 1048576);
    }

    public static void preventScreenLock(final boolean z) {
        sAppActivity.runOnUiThread(new Runnable() { // from class: jp.f4samurai.bridge.NativeBridge.4
            @Override // java.lang.Runnable
            public void run() {
                if (z) {
                    NativeBridge.sAppActivity.getWindow().addFlags(128);
                } else {
                    NativeBridge.sAppActivity.getWindow().clearFlags(128);
                }
            }
        });
    }

    public static void _onBackKeyReleased() {
        sAppActivity.runOnGLThread(new Runnable() { // from class: jp.f4samurai.bridge.NativeBridge.5
            @Override // java.lang.Runnable
            public void run() {
                NativeBridge.onBackKeyReleased();
            }
        });
    }

    public static void onCloseApplication() {
        if (Build.VERSION.SDK_INT >= 21) {
            sAppActivity.finishAndRemoveTask();
            System.exit(0);
        } else {
            sAppActivity.finish();
            System.exit(0);
        }
    }

    public static boolean isUnauthorizedUser() {
        return false;
    }

    private static String fetchFileNamesFromDirectory(String str, String str2) {
        if (str2.isEmpty()) {
            return fetchFileNamesFromAppResourcesDirectory(str);
        }
        return fetchFileNamesFromDownloadDirectory(str, str2);
    }

    private static String fetchFileNamesFromAppResourcesDirectory(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("");
        AssetManager assets = sAppActivity.getResources().getAssets();
        if (assets != null) {
            try {
                for (String str2 : assets.list(str)) {
                    if (stringBuffer.toString() != "") {
                        stringBuffer.append(",");
                    }
                    stringBuffer.append(str2);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stringBuffer.toString();
    }

    private static String fetchFileNamesFromDownloadDirectory(String str, String str2) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("");
        File[] listFiles = new File(str2 + str).listFiles();
        if (listFiles != null) {
            for (int i = 0; i < listFiles.length; i++) {
                if (i != 0) {
                    stringBuffer.append(",");
                }
                stringBuffer.append(listFiles[i].getName());
            }
        }
        return stringBuffer.toString();
    }
}
