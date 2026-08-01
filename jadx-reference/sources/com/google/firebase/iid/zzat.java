package com.google.firebase.iid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import javax.annotation.Nullable;

/* loaded from: classes.dex */
final class zzat extends BroadcastReceiver {

    @Nullable
    private zzas zzde;

    public zzat(zzas zzasVar) {
        this.zzde = zzasVar;
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        zzas zzasVar = this.zzde;
        if (zzasVar != null && zzasVar.zzah()) {
            if (FirebaseInstanceId.zzi()) {
                Log.d("FirebaseInstanceId", "Connectivity changed. Starting background sync.");
            }
            FirebaseInstanceId.zza(this.zzde, 0L);
            this.zzde.getContext().unregisterReceiver(this);
            this.zzde = null;
        }
    }

    public final void zzai() {
        if (FirebaseInstanceId.zzi()) {
            Log.d("FirebaseInstanceId", "Connectivity change received registered");
        }
        this.zzde.getContext().registerReceiver(this, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }
}
