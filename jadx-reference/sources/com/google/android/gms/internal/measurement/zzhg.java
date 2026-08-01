package com.google.android.gms.internal.measurement;

/* loaded from: classes.dex */
final class zzhg implements Runnable {
    private final /* synthetic */ zzgo zzanp;
    private final /* synthetic */ String zzant;
    private final /* synthetic */ String zzanw;
    private final /* synthetic */ String zzanx;
    private final /* synthetic */ long zzany;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzhg(zzgo zzgoVar, String str, String str2, String str3, long j) {
        this.zzanp = zzgoVar;
        this.zzanw = str;
        this.zzant = str2;
        this.zzanx = str3;
        this.zzany = j;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzjs zzjsVar;
        zzjs zzjsVar2;
        String str = this.zzanw;
        if (str == null) {
            zzjsVar2 = this.zzanp.zzajy;
            zzjsVar2.zzlj().zzfz().zza(this.zzant, (zzif) null);
        } else {
            zzif zzifVar = new zzif(this.zzanx, str, this.zzany);
            zzjsVar = this.zzanp.zzajy;
            zzjsVar.zzlj().zzfz().zza(this.zzant, zzifVar);
        }
    }
}
