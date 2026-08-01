package com.google.android.gms.internal.measurement;

/* loaded from: classes.dex */
final class zzgd implements Runnable {
    private final /* synthetic */ zzgm zzalk;
    private final /* synthetic */ zzfh zzall;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzgd(zzgc zzgcVar, zzgm zzgmVar, zzfh zzfhVar) {
        this.zzalk = zzgmVar;
        this.zzall = zzfhVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (this.zzalk.zzjw() == null) {
            this.zzall.zzis().log("Install Referrer Reporter is null");
        } else {
            this.zzalk.zzjw().zzjo();
        }
    }
}
