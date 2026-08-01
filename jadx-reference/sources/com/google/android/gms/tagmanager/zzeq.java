package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.gtm.zzro;
import com.google.android.gms.internal.gtm.zzrw;
import java.util.Set;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* loaded from: classes.dex */
public final class zzeq implements zzer {
    /* JADX INFO: Access modifiers changed from: package-private */
    public zzeq(zzeu zzeuVar) {
    }

    @Override // com.google.android.gms.tagmanager.zzer
    public final void zza(zzrw zzrwVar, Set<zzro> set, Set<zzro> set2, zzdo zzdoVar) {
        set.addAll(zzrwVar.zzc());
        set2.addAll(zzrwVar.zzh());
        zzrwVar.zzc();
        zzrwVar.zzb();
        zzrwVar.zzh();
        zzrwVar.zzg();
    }
}
