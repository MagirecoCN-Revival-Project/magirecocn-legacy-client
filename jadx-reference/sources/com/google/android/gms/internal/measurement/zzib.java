package com.google.android.gms.internal.measurement;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzib implements Runnable {
    private final /* synthetic */ zzhl zzaog;
    private final /* synthetic */ long zzaon;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzib(zzhl zzhlVar, long j) {
        this.zzaog = zzhlVar;
        this.zzaon = j;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzaog.zzgg().zzakr.set(this.zzaon);
        this.zzaog.zzgf().zziy().zzg("Minimum session duration set", Long.valueOf(this.zzaon));
    }
}
