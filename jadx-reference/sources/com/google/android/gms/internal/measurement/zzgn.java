package com.google.android.gms.internal.measurement;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzgn implements Runnable {
    private final /* synthetic */ zzhk zzank;
    private final /* synthetic */ zzgm zzanl;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzgn(zzgm zzgmVar, zzhk zzhkVar) {
        this.zzanl = zzgmVar;
        this.zzank = zzhkVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzanl.zza(this.zzank);
        this.zzanl.start();
    }
}
