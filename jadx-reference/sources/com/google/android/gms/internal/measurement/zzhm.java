package com.google.android.gms.internal.measurement;

import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzhm implements Runnable {
    private final /* synthetic */ AtomicReference zzaof;
    private final /* synthetic */ zzhl zzaog;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzhm(zzhl zzhlVar, AtomicReference atomicReference) {
        this.zzaog = zzhlVar;
        this.zzaof = atomicReference;
    }

    @Override // java.lang.Runnable
    public final void run() {
        synchronized (this.zzaof) {
            try {
                this.zzaof.set(Boolean.valueOf(this.zzaog.zzgh().zzhp()));
            } finally {
                this.zzaof.notify();
            }
        }
    }
}
