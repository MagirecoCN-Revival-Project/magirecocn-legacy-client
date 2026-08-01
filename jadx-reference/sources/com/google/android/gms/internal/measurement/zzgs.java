package com.google.android.gms.internal.measurement;

/* loaded from: classes.dex */
final class zzgs implements Runnable {
    private final /* synthetic */ zzgo zzanp;
    private final /* synthetic */ zzee zzanq;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzgs(zzgo zzgoVar, zzee zzeeVar) {
        this.zzanp = zzgoVar;
        this.zzanq = zzeeVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzjs zzjsVar;
        zzjs zzjsVar2;
        zzjsVar = this.zzanp.zzajy;
        zzjsVar.zzlg();
        zzjsVar2 = this.zzanp.zzajy;
        zzee zzeeVar = this.zzanq;
        zzdz zzca = zzjsVar2.zzca(zzeeVar.packageName);
        if (zzca != null) {
            zzjsVar2.zzc(zzeeVar, zzca);
        }
    }
}
