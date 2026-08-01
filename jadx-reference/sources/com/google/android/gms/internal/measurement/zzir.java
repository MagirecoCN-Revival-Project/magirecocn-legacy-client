package com.google.android.gms.internal.measurement;

import android.os.RemoteException;
import android.text.TextUtils;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzir implements Runnable {
    private final /* synthetic */ zzdz zzano;
    private final /* synthetic */ String zzant;
    private final /* synthetic */ zzew zzanu;
    private final /* synthetic */ zzij zzapn;
    private final /* synthetic */ boolean zzapp;
    private final /* synthetic */ boolean zzapq;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzir(zzij zzijVar, boolean z, boolean z2, zzew zzewVar, zzdz zzdzVar, String str) {
        this.zzapn = zzijVar;
        this.zzapp = z;
        this.zzapq = z2;
        this.zzanu = zzewVar;
        this.zzano = zzdzVar;
        this.zzant = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzez zzezVar;
        zzezVar = this.zzapn.zzaph;
        if (zzezVar == null) {
            this.zzapn.zzgf().zzis().log("Discarding data. Failed to send event to service");
            return;
        }
        if (this.zzapp) {
            this.zzapn.zza(zzezVar, this.zzapq ? null : this.zzanu, this.zzano);
        } else {
            try {
                if (TextUtils.isEmpty(this.zzant)) {
                    zzezVar.zza(this.zzanu, this.zzano);
                } else {
                    zzezVar.zza(this.zzanu, this.zzant, this.zzapn.zzgf().zzjb());
                }
            } catch (RemoteException e) {
                this.zzapn.zzgf().zzis().zzg("Failed to send event to the service", e);
            }
        }
        this.zzapn.zzcu();
    }
}
