package com.google.android.gms.internal.measurement;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzii implements Runnable {
    private final /* synthetic */ zzig zzape;
    private final /* synthetic */ zzif zzapf;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzii(zzig zzigVar, zzif zzifVar) {
        this.zzape = zzigVar;
        this.zzapf = zzifVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzape.zza(this.zzapf);
        this.zzape.zzaov = null;
        this.zzape.zzfy().zzb((zzif) null);
    }
}
