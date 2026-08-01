package com.google.android.gms.internal.measurement;

/* loaded from: classes.dex */
final class zzjc implements Runnable {
    private final /* synthetic */ zzix zzapw;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzjc(zzix zzixVar) {
        this.zzapw = zzixVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzij.zza(this.zzapw.zzapn, (zzez) null);
        this.zzapw.zzapn.zzkp();
    }
}
