package com.google.android.gms.internal.measurement;

/* loaded from: classes.dex */
final class zzgp implements Runnable {
    private final /* synthetic */ zzdz zzano;
    private final /* synthetic */ zzgo zzanp;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzgp(zzgo zzgoVar, zzdz zzdzVar) {
        this.zzanp = zzgoVar;
        this.zzano = zzdzVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzjs zzjsVar;
        zzjs zzjsVar2;
        zzjsVar = this.zzanp.zzajy;
        zzjsVar.zzlg();
        zzjsVar2 = this.zzanp.zzajy;
        zzjsVar2.zze(this.zzano);
    }
}
