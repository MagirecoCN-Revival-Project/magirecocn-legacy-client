package com.google.android.gms.internal.measurement;

import java.util.List;
import java.util.concurrent.Callable;

/* loaded from: classes.dex */
final class zzgw implements Callable<List<zzee>> {
    private final /* synthetic */ zzdz zzano;
    private final /* synthetic */ zzgo zzanp;
    private final /* synthetic */ String zzanr;
    private final /* synthetic */ String zzans;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzgw(zzgo zzgoVar, zzdz zzdzVar, String str, String str2) {
        this.zzanp = zzgoVar;
        this.zzano = zzdzVar;
        this.zzanr = str;
        this.zzans = str2;
    }

    /* JADX DEBUG: Return type fixed from 'java.lang.Object' to match base method */
    @Override // java.util.concurrent.Callable
    public final /* synthetic */ List<zzee> call() throws Exception {
        zzjs zzjsVar;
        zzjs zzjsVar2;
        zzjsVar = this.zzanp.zzajy;
        zzjsVar.zzlg();
        zzjsVar2 = this.zzanp.zzajy;
        return zzjsVar2.zzje().zzc(this.zzano.packageName, this.zzanr, this.zzans);
    }
}
