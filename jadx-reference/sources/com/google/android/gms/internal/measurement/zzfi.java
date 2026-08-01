package com.google.android.gms.internal.measurement;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzfi implements Runnable {
    private final /* synthetic */ int zzajg;
    private final /* synthetic */ String zzajh;
    private final /* synthetic */ Object zzaji;
    private final /* synthetic */ Object zzajj;
    private final /* synthetic */ Object zzajk;
    private final /* synthetic */ zzfh zzajl;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzfi(zzfh zzfhVar, int i, String str, Object obj, Object obj2, Object obj3) {
        this.zzajl = zzfhVar;
        this.zzajg = i;
        this.zzajh = str;
        this.zzaji = obj;
        this.zzajj = obj2;
        this.zzajk = obj3;
    }

    @Override // java.lang.Runnable
    public final void run() {
        char c;
        long j;
        char c2;
        long j2;
        zzfh zzfhVar;
        char c3;
        zzfs zzgg = this.zzajl.zzacw.zzgg();
        if (!zzgg.isInitialized()) {
            this.zzajl.zza(6, "Persisted config not initialized. Not logging error/warn");
            return;
        }
        c = this.zzajl.zzaiv;
        if (c == 0) {
            if (this.zzajl.zzgh().zzds()) {
                zzfhVar = this.zzajl;
                zzfhVar.zzgi();
                c3 = 'C';
            } else {
                zzfhVar = this.zzajl;
                zzfhVar.zzgi();
                c3 = 'c';
            }
            zzfhVar.zzaiv = c3;
        }
        j = this.zzajl.zzadu;
        if (j < 0) {
            zzfh.zza(this.zzajl, 12451L);
        }
        char charAt = "01VDIWEA?".charAt(this.zzajg);
        c2 = this.zzajl.zzaiv;
        j2 = this.zzajl.zzadu;
        String zza = zzfh.zza(true, this.zzajh, this.zzaji, this.zzajj, this.zzajk);
        StringBuilder sb = new StringBuilder(String.valueOf(zza).length() + 24);
        sb.append("2");
        sb.append(charAt);
        sb.append(c2);
        sb.append(j2);
        sb.append(":");
        sb.append(zza);
        String sb2 = sb.toString();
        if (sb2.length() > 1024) {
            sb2 = this.zzajh.substring(0, 1024);
        }
        zzgg.zzakc.zzc(sb2, 1L);
    }
}
