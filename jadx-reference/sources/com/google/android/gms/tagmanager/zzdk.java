package com.google.android.gms.tagmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/* compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* loaded from: classes.dex */
final class zzdk extends BroadcastReceiver {
    static final String zza = "com.google.android.gms.tagmanager.zzdk";
    private final zzey zzb;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzdk(zzey zzeyVar) {
        this.zzb = zzeyVar;
    }

    public static void zza(Context context) {
        Intent intent = new Intent("com.google.analytics.RADIO_POWERED");
        intent.addCategory(context.getPackageName());
        intent.putExtra(zza, true);
        context.sendBroadcast(intent);
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if ("android.net.conn.CONNECTIVITY_CHANGE".equals(action)) {
            Bundle extras = intent.getExtras();
            Boolean bool = Boolean.FALSE;
            if (extras != null) {
                bool = Boolean.valueOf(intent.getExtras().getBoolean("noConnectivity"));
            }
            this.zzb.zzc(!bool.booleanValue());
            return;
        }
        if (!"com.google.analytics.RADIO_POWERED".equals(action) || intent.hasExtra(zza)) {
            return;
        }
        this.zzb.zzb();
    }
}
