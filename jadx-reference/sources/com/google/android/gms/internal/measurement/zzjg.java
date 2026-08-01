package com.google.android.gms.internal.measurement;

/* loaded from: classes.dex */
final class zzjg implements Runnable {
    private final /* synthetic */ Runnable zzabt;
    private final /* synthetic */ zzjs zzaqc;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzjg(zzjd zzjdVar, zzjs zzjsVar, Runnable runnable) {
        this.zzaqc = zzjsVar;
        this.zzabt = runnable;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzaqc.zzlg();
        this.zzaqc.zzg(this.zzabt);
        this.zzaqc.zzlb();
    }
}
