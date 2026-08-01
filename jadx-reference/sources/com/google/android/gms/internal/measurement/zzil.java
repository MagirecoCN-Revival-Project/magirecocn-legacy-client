package com.google.android.gms.internal.measurement;

import android.os.RemoteException;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzil implements Runnable {
    private final /* synthetic */ zzdz zzano;
    private final /* synthetic */ zzij zzapn;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzil(zzij zzijVar, zzdz zzdzVar) {
        this.zzapn = zzijVar;
        this.zzano = zzdzVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzez zzezVar;
        zzezVar = this.zzapn.zzaph;
        if (zzezVar == null) {
            this.zzapn.zzgf().zzis().log("Failed to reset data on the service; null service");
            return;
        }
        try {
            zzezVar.zzd(this.zzano);
        } catch (RemoteException e) {
            this.zzapn.zzgf().zzis().zzg("Failed to reset data on the service", e);
        }
        this.zzapn.zzcu();
    }
}
