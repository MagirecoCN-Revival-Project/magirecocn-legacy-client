package com.google.android.gms.internal.measurement;

import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzhz implements Runnable {
    private final /* synthetic */ AtomicReference zzaof;
    private final /* synthetic */ zzhl zzaog;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzhz(zzhl zzhlVar, AtomicReference atomicReference) {
        this.zzaog = zzhlVar;
        this.zzaof = atomicReference;
    }

    @Override // java.lang.Runnable
    public final void run() {
        synchronized (this.zzaof) {
            try {
                AtomicReference atomicReference = this.zzaof;
                zzeg zzgh = this.zzaog.zzgh();
                atomicReference.set(Double.valueOf(zzgh.zzc(zzgh.zzfw().zzah(), zzey.zzaia)));
            } finally {
                this.zzaof.notify();
            }
        }
    }
}
