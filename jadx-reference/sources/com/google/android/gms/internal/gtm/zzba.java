package com.google.android.gms.internal.gtm;

import android.text.TextUtils;
import java.util.HashMap;

/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
public final class zzba extends com.google.android.gms.analytics.zzj<zzba> {
    public int zza;
    public int zzb;
    private String zzc;

    public final String toString() {
        HashMap hashMap = new HashMap();
        hashMap.put("language", this.zzc);
        hashMap.put("screenColors", 0);
        hashMap.put("screenWidth", Integer.valueOf(this.zza));
        hashMap.put("screenHeight", Integer.valueOf(this.zzb));
        hashMap.put("viewportWidth", 0);
        hashMap.put("viewportHeight", 0);
        return zza(hashMap);
    }

    /* JADX DEBUG: Method arguments types fixed to match base method, original types: [com.google.android.gms.analytics.zzj] */
    @Override // com.google.android.gms.analytics.zzj
    public final /* bridge */ /* synthetic */ void zzc(zzba zzbaVar) {
        zzba zzbaVar2 = zzbaVar;
        int i = this.zza;
        if (i != 0) {
            zzbaVar2.zza = i;
        }
        int i2 = this.zzb;
        if (i2 != 0) {
            zzbaVar2.zzb = i2;
        }
        if (TextUtils.isEmpty(this.zzc)) {
            return;
        }
        zzbaVar2.zzc = this.zzc;
    }

    public final String zzd() {
        return this.zzc;
    }

    public final void zze(String str) {
        this.zzc = str;
    }
}
