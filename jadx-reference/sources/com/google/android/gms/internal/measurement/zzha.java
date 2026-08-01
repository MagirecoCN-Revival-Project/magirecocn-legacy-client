package com.google.android.gms.internal.measurement;

/* loaded from: classes.dex */
final class zzha implements Runnable {
    private final /* synthetic */ zzgo zzanp;
    private final /* synthetic */ String zzant;
    private final /* synthetic */ zzew zzanu;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzha(zzgo zzgoVar, zzew zzewVar, String str) {
        this.zzanp = zzgoVar;
        this.zzanu = zzewVar;
        this.zzant = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzjs zzjsVar;
        zzjs zzjsVar2;
        zzjsVar = this.zzanp.zzajy;
        zzjsVar.zzlg();
        zzjsVar2 = this.zzanp.zzajy;
        zzjsVar2.zzc(this.zzanu, this.zzant);
    }
}
