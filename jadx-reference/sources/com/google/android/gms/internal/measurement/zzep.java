package com.google.android.gms.internal.measurement;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzep implements Runnable {
    private final /* synthetic */ zzhj zzafl;
    private final /* synthetic */ zzeo zzafm;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzep(zzeo zzeoVar, zzhj zzhjVar) {
        this.zzafm = zzeoVar;
        this.zzafl = zzhjVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzafl.zzgi();
        if (zzec.isMainThread()) {
            this.zzafl.zzge().zzc(this);
            return;
        }
        boolean zzef = this.zzafm.zzef();
        zzeo.zza(this.zzafm, 0L);
        if (zzef) {
            this.zzafm.run();
        }
    }
}
