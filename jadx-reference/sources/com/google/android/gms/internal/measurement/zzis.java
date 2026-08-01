package com.google.android.gms.internal.measurement;

import android.os.RemoteException;
import android.text.TextUtils;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzis implements Runnable {
    private final /* synthetic */ zzdz zzano;
    private final /* synthetic */ zzij zzapn;
    private final /* synthetic */ boolean zzapp;
    private final /* synthetic */ boolean zzapq;
    private final /* synthetic */ zzee zzapr;
    private final /* synthetic */ zzee zzaps;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzis(zzij zzijVar, boolean z, boolean z2, zzee zzeeVar, zzdz zzdzVar, zzee zzeeVar2) {
        this.zzapn = zzijVar;
        this.zzapp = z;
        this.zzapq = z2;
        this.zzapr = zzeeVar;
        this.zzano = zzdzVar;
        this.zzaps = zzeeVar2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzez zzezVar;
        zzezVar = this.zzapn.zzaph;
        if (zzezVar == null) {
            this.zzapn.zzgf().zzis().log("Discarding data. Failed to send conditional user property to service");
            return;
        }
        if (this.zzapp) {
            this.zzapn.zza(zzezVar, this.zzapq ? null : this.zzapr, this.zzano);
        } else {
            try {
                if (TextUtils.isEmpty(this.zzaps.packageName)) {
                    zzezVar.zza(this.zzapr, this.zzano);
                } else {
                    zzezVar.zzb(this.zzapr);
                }
            } catch (RemoteException e) {
                this.zzapn.zzgf().zzis().zzg("Failed to send conditional user property to the service", e);
            }
        }
        this.zzapn.zzcu();
    }
}
