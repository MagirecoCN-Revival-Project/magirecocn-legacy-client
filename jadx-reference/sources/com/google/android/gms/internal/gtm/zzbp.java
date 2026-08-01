package com.google.android.gms.internal.gtm;

import java.util.concurrent.Callable;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
public final class zzbp implements Callable<Void> {
    final /* synthetic */ zzbq zza;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzbp(zzbq zzbqVar) {
        this.zza = zzbqVar;
    }

    /* JADX DEBUG: Return type fixed from 'java.lang.Object' to match base method */
    @Override // java.util.concurrent.Callable
    public final /* bridge */ /* synthetic */ Void call() throws Exception {
        zzck zzckVar;
        zzckVar = this.zza.zza;
        zzckVar.zzac();
        return null;
    }
}
