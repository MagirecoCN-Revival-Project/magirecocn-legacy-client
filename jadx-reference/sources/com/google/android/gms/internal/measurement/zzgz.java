package com.google.android.gms.internal.measurement;

/* loaded from: classes.dex */
final class zzgz implements Runnable {
    private final /* synthetic */ zzdz zzano;
    private final /* synthetic */ zzgo zzanp;
    private final /* synthetic */ zzew zzanu;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzgz(zzgo zzgoVar, zzew zzewVar, zzdz zzdzVar) {
        this.zzanp = zzgoVar;
        this.zzanu = zzewVar;
        this.zzano = zzdzVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzjs zzjsVar;
        zzjs zzjsVar2;
        zzjsVar = this.zzanp.zzajy;
        zzjsVar.zzlg();
        zzjsVar2 = this.zzanp.zzajy;
        zzjsVar2.zzb(this.zzanu, this.zzano);
    }
}
