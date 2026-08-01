package jp.f4samurai.bridge;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;
import jp.f4samurai.madomagi.BuildConfig;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class CheatHandler {
    private Activity mActivity;
    private boolean mIsUnauthorizedUser;

    public CheatHandler(Context context) {
        this.mIsUnauthorizedUser = true;
        this.mActivity = (Activity) context;
        if (TextUtils.equals(BuildConfig.FLAVOR, BuildConfig.FLAVOR) && TextUtils.equals("release", "release")) {
            if (!fromGooglePlay()) {
                showDialog("GooglePlayStore以外からインストールしたアプリはご利用できません。", new DialogInterface.OnClickListener() { // from class: jp.f4samurai.bridge.CheatHandler.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                        CheatHandler.this.closeApplication();
                    }
                });
                return;
            } else {
                this.mIsUnauthorizedUser = false;
                return;
            }
        }
        this.mIsUnauthorizedUser = false;
    }

    public boolean isUnauthorizedUser() {
        return this.mIsUnauthorizedUser;
    }

    private boolean fromGooglePlay() {
        String installerPackageName = this.mActivity.getPackageManager().getInstallerPackageName(this.mActivity.getPackageName());
        if (installerPackageName != null) {
            return TextUtils.equals("com.android.vending", installerPackageName);
        }
        return false;
    }

    private boolean hasIntegrity(String str) {
        try {
            return new JSONObject(new String(Base64.decode(str.split("\\.")[1], 0))).getBoolean("basicIntegrity");
        } catch (JSONException e) {
            e.printStackTrace();
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void closeApplication() {
        if (Build.VERSION.SDK_INT >= 21) {
            this.mActivity.finishAndRemoveTask();
            System.exit(0);
        } else {
            this.mActivity.finish();
            System.exit(0);
        }
    }

    private void showDialog(String str, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mActivity);
        builder.setMessage(str);
        builder.setPositiveButton("OK", onClickListener);
        builder.setCancelable(false);
        builder.create().show();
    }
}
