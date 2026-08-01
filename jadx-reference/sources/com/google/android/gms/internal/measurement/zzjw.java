package com.google.android.gms.internal.measurement;

import java.util.concurrent.Callable;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzjw implements Callable<String> {
    private final /* synthetic */ zzdz zzano;
    private final /* synthetic */ zzjs zzarf;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzjw(zzjs zzjsVar, zzdz zzdzVar) {
        this.zzarf = zzjsVar;
        this.zzano = zzdzVar;
    }

    /* JADX DEBUG: Return type fixed from 'java.lang.Object' to match base method */
    @Override // java.util.concurrent.Callable
    public final /* synthetic */ String call() throws Exception {
        zzdy zzg = this.zzarf.zzgh().zzay(this.zzano.packageName) ? this.zzarf.zzg(this.zzano) : this.zzarf.zzje().zzbb(this.zzano.packageName);
        if (zzg != null) {
            return zzg.getAppInstanceId();
        }
        this.zzarf.zzgf().zziv().log("App info was null when attempting to get app instance id");
        return null;
    }
}
