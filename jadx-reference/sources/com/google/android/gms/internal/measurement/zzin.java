package com.google.android.gms.internal.measurement;

import android.os.RemoteException;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzin implements Runnable {
    private final /* synthetic */ zzdz zzano;
    private final /* synthetic */ zzij zzapn;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzin(zzij zzijVar, zzdz zzdzVar) {
        this.zzapn = zzijVar;
        this.zzano = zzdzVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzez zzezVar;
        zzezVar = this.zzapn.zzaph;
        if (zzezVar == null) {
            this.zzapn.zzgf().zzis().log("Discarding data. Failed to send app launch");
            return;
        }
        try {
            zzezVar.zza(this.zzano);
            this.zzapn.zza(zzezVar, null, this.zzano);
            this.zzapn.zzcu();
        } catch (RemoteException e) {
            this.zzapn.zzgf().zzis().zzg("Failed to send app launch to the service", e);
        }
    }
}
