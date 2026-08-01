package com.google.android.gms.internal.measurement;

import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzhq implements Runnable {
    private final /* synthetic */ AtomicReference zzaof;
    private final /* synthetic */ zzhl zzaog;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzhq(zzhl zzhlVar, AtomicReference atomicReference) {
        this.zzaog = zzhlVar;
        this.zzaof = atomicReference;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzaog.zzfy().zza(this.zzaof);
    }
}
