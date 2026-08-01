package com.google.android.gms.internal.measurement;

import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzho implements Runnable {
    private final /* synthetic */ AtomicReference zzaof;
    private final /* synthetic */ zzhl zzaog;
    private final /* synthetic */ boolean zzaoj;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzho(zzhl zzhlVar, AtomicReference atomicReference, boolean z) {
        this.zzaog = zzhlVar;
        this.zzaof = atomicReference;
        this.zzaoj = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzaog.zzfy().zza(this.zzaof, this.zzaoj);
    }
}
