package jp.f4samurai.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import jp.f4samurai.AppActivity;

/* loaded from: classes.dex */
public class RuntimePermissionUtils {
    public static final int REQUEST_CAMERA_PERMISSION = 1;
    public static final int REQUEST_MANAGE_STORAGE_PERMISSION = 3;
    public static final int REQUEST_POST_NOTIFICATIONS = 4;
    public static final int REQUEST_STORAGE_PERMISSION = 2;

    /* loaded from: classes.dex */
    public interface Callback {
        void onDenied();

        void onGranted();
    }

    public static void requestPermission(AppActivity appActivity, String str, int i, Callback callback) {
        ActivityCompat.requestPermissions(appActivity, new String[]{str}, i);
        appActivity.setPermissionCallback(callback);
    }

    public static boolean hasSelfPermissions(Context context, String str) {
        return Build.VERSION.SDK_INT < 23 || context.checkSelfPermission(str) == 0;
    }

    public static boolean checkGrantResults(int i, int... iArr) {
        return iArr.length == 1 && iArr[0] == 0;
    }

    public static boolean shouldShowRequestPermissionRationale(Activity activity, String str) {
        if (Build.VERSION.SDK_INT >= 23) {
            return activity.shouldShowRequestPermissionRationale(str);
        }
        return true;
    }

    public static void showAlertDialog(FragmentManager fragmentManager, String str) {
        RuntimePermissionAlertDialogFragment.newInstance(str).show(fragmentManager, RuntimePermissionAlertDialogFragment.TAG);
    }

    /* loaded from: classes.dex */
    public static class RuntimePermissionAlertDialogFragment extends DialogFragment {
        private static final String ARG_PERMISSION_NAME = "permissionName";
        public static final String TAG = "RuntimePermissionApplicationSettingsDialogFragment";

        public static RuntimePermissionAlertDialogFragment newInstance(String str) {
            RuntimePermissionAlertDialogFragment runtimePermissionAlertDialogFragment = new RuntimePermissionAlertDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putString(ARG_PERMISSION_NAME, str);
            runtimePermissionAlertDialogFragment.setArguments(bundle);
            return runtimePermissionAlertDialogFragment;
        }

        @Override // android.app.DialogFragment
        public Dialog onCreateDialog(Bundle bundle) {
            String string = getArguments().getString(ARG_PERMISSION_NAME);
            return new AlertDialog.Builder(getActivity()).setMessage(string + "の権限がないので、アプリ情報の「許可」から設定してください").setPositiveButton("アプリ情報", new DialogInterface.OnClickListener() { // from class: jp.f4samurai.utils.RuntimePermissionUtils.RuntimePermissionAlertDialogFragment.2
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i) {
                    RuntimePermissionAlertDialogFragment.this.dismiss();
                    Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS", Uri.parse("package:" + RuntimePermissionAlertDialogFragment.this.getActivity().getPackageName()));
                    intent.addFlags(268435456);
                    RuntimePermissionAlertDialogFragment.this.getActivity().startActivity(intent);
                }
            }).setNegativeButton("キャンセル", new DialogInterface.OnClickListener() { // from class: jp.f4samurai.utils.RuntimePermissionUtils.RuntimePermissionAlertDialogFragment.1
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i) {
                    RuntimePermissionAlertDialogFragment.this.dismiss();
                }
            }).create();
        }
    }
}
