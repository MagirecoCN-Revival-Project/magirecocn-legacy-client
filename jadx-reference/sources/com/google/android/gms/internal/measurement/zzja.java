package com.google.android.gms.internal.measurement;

/* loaded from: classes.dex */
final class zzja implements Runnable {
    private final /* synthetic */ zzix zzapw;
    private final /* synthetic */ zzez zzapx;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzja(zzix zzixVar, zzez zzezVar) {
        this.zzapw = zzixVar;
        this.zzapx = zzezVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        synchronized (this.zzapw) {
            zzix.zza(this.zzapw, false);
            if (!this.zzapw.zzapn.isConnected()) {
                this.zzapw.zzapn.zzgf().zziy().log("Connected to remote service");
                this.zzapw.zzapn.zza(this.zzapx);
            }
        }
    }
}
