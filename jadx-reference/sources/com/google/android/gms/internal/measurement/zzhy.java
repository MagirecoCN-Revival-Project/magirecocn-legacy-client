package com.google.android.gms.internal.measurement;

import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzhy implements Runnable {
    private final /* synthetic */ AtomicReference zzaof;
    private final /* synthetic */ zzhl zzaog;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzhy(zzhl zzhlVar, AtomicReference atomicReference) {
        this.zzaog = zzhlVar;
        this.zzaof = atomicReference;
    }

    @Override // java.lang.Runnable
    public final void run() {
        synchronized (this.zzaof) {
            try {
                AtomicReference atomicReference = this.zzaof;
                zzeg zzgh = this.zzaog.zzgh();
                atomicReference.set(Integer.valueOf(zzgh.zzb(zzgh.zzfw().zzah(), zzey.zzahz)));
            } finally {
                this.zzaof.notify();
            }
        }
    }
}
