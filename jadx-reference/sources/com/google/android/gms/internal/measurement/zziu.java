package com.google.android.gms.internal.measurement;

import android.os.RemoteException;
import android.text.TextUtils;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zziu implements Runnable {
    private final /* synthetic */ zzdz zzano;
    private final /* synthetic */ String zzanr;
    private final /* synthetic */ String zzans;
    private final /* synthetic */ String zzant;
    private final /* synthetic */ boolean zzaoj;
    private final /* synthetic */ zzij zzapn;
    private final /* synthetic */ AtomicReference zzapo;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zziu(zzij zzijVar, AtomicReference atomicReference, String str, String str2, String str3, boolean z, zzdz zzdzVar) {
        this.zzapn = zzijVar;
        this.zzapo = atomicReference;
        this.zzant = str;
        this.zzanr = str2;
        this.zzans = str3;
        this.zzaoj = z;
        this.zzano = zzdzVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        AtomicReference atomicReference;
        zzez zzezVar;
        AtomicReference atomicReference2;
        List<zzjz> zza;
        synchronized (this.zzapo) {
            try {
                try {
                    zzezVar = this.zzapn.zzaph;
                } catch (RemoteException e) {
                    this.zzapn.zzgf().zzis().zzd("Failed to get user properties", zzfh.zzbl(this.zzant), this.zzanr, e);
                    this.zzapo.set(Collections.emptyList());
                    atomicReference = this.zzapo;
                }
                if (zzezVar == null) {
                    this.zzapn.zzgf().zzis().zzd("Failed to get user properties", zzfh.zzbl(this.zzant), this.zzanr, this.zzans);
                    this.zzapo.set(Collections.emptyList());
                    return;
                }
                if (TextUtils.isEmpty(this.zzant)) {
                    atomicReference2 = this.zzapo;
                    zza = zzezVar.zza(this.zzanr, this.zzans, this.zzaoj, this.zzano);
                } else {
                    atomicReference2 = this.zzapo;
                    zza = zzezVar.zza(this.zzant, this.zzanr, this.zzans, this.zzaoj);
                }
                atomicReference2.set(zza);
                this.zzapn.zzcu();
                atomicReference = this.zzapo;
                atomicReference.notify();
            } finally {
                this.zzapo.notify();
            }
        }
    }
}
