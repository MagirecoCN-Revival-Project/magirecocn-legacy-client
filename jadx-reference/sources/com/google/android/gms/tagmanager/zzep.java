package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.gtm.zzro;
import com.google.android.gms.internal.gtm.zzrw;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* loaded from: classes.dex */
public final class zzep implements zzer {
    final /* synthetic */ Map zza;
    final /* synthetic */ Map zzb;
    final /* synthetic */ Map zzc;
    final /* synthetic */ Map zzd;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzep(zzeu zzeuVar, Map map, Map map2, Map map3, Map map4) {
        this.zza = map;
        this.zzb = map2;
        this.zzc = map3;
        this.zzd = map4;
    }

    @Override // com.google.android.gms.tagmanager.zzer
    public final void zza(zzrw zzrwVar, Set<zzro> set, Set<zzro> set2, zzdo zzdoVar) {
        List list = (List) this.zza.get(zzrwVar);
        if (list != null) {
            set.addAll(list);
        }
        List list2 = (List) this.zzc.get(zzrwVar);
        if (list2 != null) {
            set2.addAll(list2);
        }
    }
}
