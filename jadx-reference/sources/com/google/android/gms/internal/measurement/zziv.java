package com.google.android.gms.internal.measurement;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zziv implements Runnable {
    private final /* synthetic */ zzdz zzano;
    private final /* synthetic */ zzjz zzanv;
    private final /* synthetic */ zzij zzapn;
    private final /* synthetic */ boolean zzapq;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zziv(zzij zzijVar, boolean z, zzjz zzjzVar, zzdz zzdzVar) {
        this.zzapn = zzijVar;
        this.zzapq = z;
        this.zzanv = zzjzVar;
        this.zzano = zzdzVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzez zzezVar;
        zzezVar = this.zzapn.zzaph;
        if (zzezVar == null) {
            this.zzapn.zzgf().zzis().log("Discarding data. Failed to set user attribute");
        } else {
            this.zzapn.zza(zzezVar, this.zzapq ? null : this.zzanv, this.zzano);
            this.zzapn.zzcu();
        }
    }
}
