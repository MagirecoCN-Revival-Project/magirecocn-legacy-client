package com.google.android.gms.internal.measurement;

import android.os.RemoteException;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zziw implements Runnable {
    private final /* synthetic */ zzdz zzano;
    private final /* synthetic */ boolean zzaoj;
    private final /* synthetic */ zzij zzapn;
    private final /* synthetic */ AtomicReference zzapo;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zziw(zzij zzijVar, AtomicReference atomicReference, zzdz zzdzVar, boolean z) {
        this.zzapn = zzijVar;
        this.zzapo = atomicReference;
        this.zzano = zzdzVar;
        this.zzaoj = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        AtomicReference atomicReference;
        zzez zzezVar;
        synchronized (this.zzapo) {
            try {
                try {
                    zzezVar = this.zzapn.zzaph;
                } catch (RemoteException e) {
                    this.zzapn.zzgf().zzis().zzg("Failed to get user properties", e);
                    atomicReference = this.zzapo;
                }
                if (zzezVar == null) {
                    this.zzapn.zzgf().zzis().log("Failed to get user properties");
                    return;
                }
                this.zzapo.set(zzezVar.zza(this.zzano, this.zzaoj));
                this.zzapn.zzcu();
                atomicReference = this.zzapo;
                atomicReference.notify();
            } finally {
                this.zzapo.notify();
            }
        }
    }
}
