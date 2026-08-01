package com.google.android.gms.internal.measurement;

/* loaded from: classes.dex */
final class zzhc implements Runnable {
    private final /* synthetic */ zzdz zzano;
    private final /* synthetic */ zzgo zzanp;
    private final /* synthetic */ zzjz zzanv;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzhc(zzgo zzgoVar, zzjz zzjzVar, zzdz zzdzVar) {
        this.zzanp = zzgoVar;
        this.zzanv = zzjzVar;
        this.zzano = zzdzVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzjs zzjsVar;
        zzjs zzjsVar2;
        zzjsVar = this.zzanp.zzajy;
        zzjsVar.zzlg();
        zzjsVar2 = this.zzanp.zzajy;
        zzjsVar2.zzc(this.zzanv, this.zzano);
    }
}
