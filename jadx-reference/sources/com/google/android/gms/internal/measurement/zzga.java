package com.google.android.gms.internal.measurement;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzga implements ServiceConnection {
    final /* synthetic */ zzfy zzalh;

    private zzga(zzfy zzfyVar) {
        this.zzalh = zzfyVar;
    }

    @Override // android.content.ServiceConnection
    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        if (iBinder == null) {
            zzfy.zza(this.zzalh).zzgf().zziv().log("Install Referrer connection returned with null binder");
            return;
        }
        try {
            this.zzalh.zzalf = zzs.zza(iBinder);
            if (this.zzalh.zzalf == null) {
                zzfy.zza(this.zzalh).zzgf().zziv().log("Install Referrer Service implementation was not found");
            } else {
                zzfy.zza(this.zzalh).zzgf().zzix().log("Install Referrer Service connected");
                zzfy.zza(this.zzalh).zzge().zzc(new zzgb(this));
            }
        } catch (Exception e) {
            zzfy.zza(this.zzalh).zzgf().zziv().zzg("Exception occurred while calling Install Referrer API", e);
        }
    }

    @Override // android.content.ServiceConnection
    public final void onServiceDisconnected(ComponentName componentName) {
        this.zzalh.zzalf = null;
        zzfy.zza(this.zzalh).zzgf().zzix().log("Install Referrer Service disconnected");
    }
}
