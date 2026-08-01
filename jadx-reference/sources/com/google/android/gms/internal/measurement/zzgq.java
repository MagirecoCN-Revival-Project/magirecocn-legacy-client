package com.google.android.gms.internal.measurement;

/* loaded from: classes.dex */
final class zzgq implements Runnable {
    private final /* synthetic */ zzdz zzano;
    private final /* synthetic */ zzgo zzanp;
    private final /* synthetic */ zzee zzanq;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzgq(zzgo zzgoVar, zzee zzeeVar, zzdz zzdzVar) {
        this.zzanp = zzgoVar;
        this.zzanq = zzeeVar;
        this.zzano = zzdzVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzjs zzjsVar;
        zzjs zzjsVar2;
        zzjsVar = this.zzanp.zzajy;
        zzjsVar.zzlg();
        zzjsVar2 = this.zzanp.zzajy;
        zzjsVar2.zzc(this.zzanq, this.zzano);
    }
}
