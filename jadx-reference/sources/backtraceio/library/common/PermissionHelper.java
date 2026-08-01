package backtraceio.library.common;

import android.content.Context;
import androidx.core.content.ContextCompat;

/* loaded from: classes.dex */
public class PermissionHelper {
    public static boolean isPermissionForBluetoothGranted(Context context) {
        return ContextCompat.checkSelfPermission(context, "android.permission.BLUETOOTH") == 0;
    }

    public static boolean isPermissionForInternetGranted(Context context) {
        return ContextCompat.checkSelfPermission(context, "android.permission.INTERNET") == 0;
    }

    public static boolean isPermissionForAccessWifiStateGranted(Context context) {
        return ContextCompat.checkSelfPermission(context, "android.permission.ACCESS_WIFI_STATE") == 0;
    }

    public static boolean isPermissionForReadExternalStorageGranted(Context context) {
        return ContextCompat.checkSelfPermission(context, "android.permission.READ_EXTERNAL_STORAGE") == 0;
    }
}
