package com.google.android.gms.internal.measurement;

import android.os.RemoteException;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzim implements Runnable {
    private final /* synthetic */ zzdz zzano;
    private final /* synthetic */ zzij zzapn;
    private final /* synthetic */ AtomicReference zzapo;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzim(zzij zzijVar, AtomicReference atomicReference, zzdz zzdzVar) {
        this.zzapn = zzijVar;
        this.zzapo = atomicReference;
        this.zzano = zzdzVar;
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
                    this.zzapn.zzgf().zzis().zzg("Failed to get app instance id", e);
                    atomicReference = this.zzapo;
                }
                if (zzezVar == null) {
                    this.zzapn.zzgf().zzis().log("Failed to get app instance id");
                    return;
                }
                this.zzapo.set(zzezVar.zzc(this.zzano));
                String str = (String) this.zzapo.get();
                if (str != null) {
                    this.zzapn.zzfv().zzbq(str);
                    this.zzapn.zzgg().zzakk.zzbr(str);
                }
                this.zzapn.zzcu();
                atomicReference = this.zzapo;
                atomicReference.notify();
            } finally {
                this.zzapo.notify();
            }
        }
    }
}
