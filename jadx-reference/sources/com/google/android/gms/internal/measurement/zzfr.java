package com.google.android.gms.internal.measurement;

/* loaded from: classes.dex */
final class zzfr implements Runnable {
    private final /* synthetic */ boolean zzajz;
    private final /* synthetic */ zzfq zzaka;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzfr(zzfq zzfqVar, boolean z) {
        this.zzaka = zzfqVar;
        this.zzajz = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzjs zzjsVar;
        zzjsVar = this.zzaka.zzajy;
        zzjsVar.zzm(this.zzajz);
    }
}
