package com.google.android.gms.internal.gtm;

import java.util.List;

/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
final class zzvw extends zzvy {
    private zzvw() {
        super(null);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public /* synthetic */ zzvw(zzvv zzvvVar) {
        super(null);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.android.gms.internal.gtm.zzvy
    public final <L> List<L> zza(Object obj, long j) {
        zzvh zzvhVar = (zzvh) zzxy.zzf(obj, j);
        if (zzvhVar.zzc()) {
            return zzvhVar;
        }
        int size = zzvhVar.size();
        zzvh zzd = zzvhVar.zzd(size == 0 ? 10 : size + size);
        zzxy.zzs(obj, j, zzd);
        return zzd;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.android.gms.internal.gtm.zzvy
    public final void zzb(Object obj, long j) {
        ((zzvh) zzxy.zzf(obj, j)).zzb();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.android.gms.internal.gtm.zzvy
    public final <E> void zzc(Object obj, Object obj2, long j) {
        zzvh zzvhVar = (zzvh) zzxy.zzf(obj, j);
        zzvh zzvhVar2 = (zzvh) zzxy.zzf(obj2, j);
        int size = zzvhVar.size();
        int size2 = zzvhVar2.size();
        if (size > 0 && size2 > 0) {
            if (!zzvhVar.zzc()) {
                zzvhVar = zzvhVar.zzd(size2 + size);
            }
            zzvhVar.addAll(zzvhVar2);
        }
        if (size > 0) {
            zzvhVar2 = zzvhVar;
        }
        zzxy.zzs(obj, j, zzvhVar2);
    }
}
