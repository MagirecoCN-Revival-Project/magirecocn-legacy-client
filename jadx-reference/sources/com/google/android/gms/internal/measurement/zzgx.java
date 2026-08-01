package com.google.android.gms.internal.measurement;

import java.util.List;
import java.util.concurrent.Callable;

/* loaded from: classes.dex */
final class zzgx implements Callable<List<zzee>> {
    private final /* synthetic */ zzgo zzanp;
    private final /* synthetic */ String zzanr;
    private final /* synthetic */ String zzans;
    private final /* synthetic */ String zzant;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzgx(zzgo zzgoVar, String str, String str2, String str3) {
        this.zzanp = zzgoVar;
        this.zzant = str;
        this.zzanr = str2;
        this.zzans = str3;
    }

    /* JADX DEBUG: Return type fixed from 'java.lang.Object' to match base method */
    @Override // java.util.concurrent.Callable
    public final /* synthetic */ List<zzee> call() throws Exception {
        zzjs zzjsVar;
        zzjs zzjsVar2;
        zzjsVar = this.zzanp.zzajy;
        zzjsVar.zzlg();
        zzjsVar2 = this.zzanp.zzajy;
        return zzjsVar2.zzje().zzc(this.zzant, this.zzanr, this.zzans);
    }
}
