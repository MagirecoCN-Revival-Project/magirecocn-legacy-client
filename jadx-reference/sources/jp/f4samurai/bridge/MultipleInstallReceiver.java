package jp.f4samurai.bridge;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.adjust.sdk.AdjustReferrerReceiver;

/* loaded from: classes.dex */
public class MultipleInstallReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        new AdjustReferrerReceiver().onReceive(context, intent);
    }
}
