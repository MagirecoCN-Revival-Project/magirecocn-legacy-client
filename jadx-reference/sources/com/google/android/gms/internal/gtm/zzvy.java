package com.google.android.gms.internal.gtm;

import java.util.List;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
public abstract class zzvy {
    private static final zzvy zza = new zzvu(null);
    private static final zzvy zzb = new zzvw(0 == true ? 1 : 0);

    /* JADX INFO: Access modifiers changed from: package-private */
    public /* synthetic */ zzvy(zzvx zzvxVar) {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static zzvy zzd() {
        return zza;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static zzvy zze() {
        return zzb;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract <L> List<L> zza(Object obj, long j);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void zzb(Object obj, long j);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract <L> void zzc(Object obj, Object obj2, long j);
}
