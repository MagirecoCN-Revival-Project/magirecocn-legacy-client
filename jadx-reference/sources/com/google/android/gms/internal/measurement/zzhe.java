package com.google.android.gms.internal.measurement;

import java.util.List;
import java.util.concurrent.Callable;

/* loaded from: classes.dex */
final class zzhe implements Callable<List<zzkb>> {
    private final /* synthetic */ zzdz zzano;
    private final /* synthetic */ zzgo zzanp;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzhe(zzgo zzgoVar, zzdz zzdzVar) {
        this.zzanp = zzgoVar;
        this.zzano = zzdzVar;
    }

    /* JADX DEBUG: Return type fixed from 'java.lang.Object' to match base method */
    @Override // java.util.concurrent.Callable
    public final /* synthetic */ List<zzkb> call() throws Exception {
        zzjs zzjsVar;
        zzjs zzjsVar2;
        zzjsVar = this.zzanp.zzajy;
        zzjsVar.zzlg();
        zzjsVar2 = this.zzanp.zzajy;
        return zzjsVar2.zzje().zzba(this.zzano.packageName);
    }
}
