package com.google.android.gms.internal.measurement;

import android.os.RemoteException;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzio implements Runnable {
    private final /* synthetic */ zzif zzapf;
    private final /* synthetic */ zzij zzapn;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzio(zzij zzijVar, zzif zzifVar) {
        this.zzapn = zzijVar;
        this.zzapf = zzifVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzez zzezVar;
        long j;
        String str;
        String str2;
        String packageName;
        zzezVar = this.zzapn.zzaph;
        if (zzezVar == null) {
            this.zzapn.zzgf().zzis().log("Failed to send current screen to service");
            return;
        }
        try {
            zzif zzifVar = this.zzapf;
            if (zzifVar == null) {
                j = 0;
                str = null;
                str2 = null;
                packageName = this.zzapn.getContext().getPackageName();
            } else {
                j = zzifVar.zzaot;
                str = this.zzapf.zzul;
                str2 = this.zzapf.zzaos;
                packageName = this.zzapn.getContext().getPackageName();
            }
            zzezVar.zza(j, str, str2, packageName);
            this.zzapn.zzcu();
        } catch (RemoteException e) {
            this.zzapn.zzgf().zzis().zzg("Failed to send current screen to the service", e);
        }
    }
}
