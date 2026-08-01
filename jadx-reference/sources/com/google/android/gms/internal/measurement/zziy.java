package com.google.android.gms.internal.measurement;

/* loaded from: classes.dex */
final class zziy implements Runnable {
    private final /* synthetic */ zzez zzapv;
    private final /* synthetic */ zzix zzapw;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zziy(zzix zzixVar, zzez zzezVar) {
        this.zzapw = zzixVar;
        this.zzapv = zzezVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        synchronized (this.zzapw) {
            zzix.zza(this.zzapw, false);
            if (!this.zzapw.zzapn.isConnected()) {
                this.zzapw.zzapn.zzgf().zziz().log("Connected to service");
                this.zzapw.zzapn.zza(this.zzapv);
            }
        }
    }
}
