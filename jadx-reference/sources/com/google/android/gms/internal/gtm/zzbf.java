package com.google.android.gms.internal.gtm;

import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.internal.Preconditions;
import java.util.HashMap;
import java.util.UUID;

/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
public final class zzbf extends com.google.android.gms.analytics.zzj<zzbf> {
    private int zza;

    public zzbf() {
        UUID randomUUID = UUID.randomUUID();
        int leastSignificantBits = (int) (randomUUID.getLeastSignificantBits() & 2147483647L);
        if (leastSignificantBits == 0 && (leastSignificantBits = (int) (randomUUID.getMostSignificantBits() & 2147483647L)) == 0) {
            Log.e("GAv4", "UUID.randomUUID() returned 0.");
            leastSignificantBits = Integer.MAX_VALUE;
        }
        Preconditions.checkNotZero(leastSignificantBits);
        this.zza = leastSignificantBits;
    }

    public final String toString() {
        HashMap hashMap = new HashMap();
        hashMap.put("screenName", null);
        hashMap.put("interstitial", false);
        hashMap.put("automatic", false);
        hashMap.put("screenId", Integer.valueOf(this.zza));
        hashMap.put("referrerScreenId", 0);
        hashMap.put("referrerScreenName", null);
        hashMap.put("referrerUri", null);
        return zza(hashMap);
    }

    /* JADX DEBUG: Method arguments types fixed to match base method, original types: [com.google.android.gms.analytics.zzj] */
    @Override // com.google.android.gms.analytics.zzj
    public final /* bridge */ /* synthetic */ void zzc(zzbf zzbfVar) {
        zzbf zzbfVar2 = zzbfVar;
        TextUtils.isEmpty(null);
        int i = this.zza;
        if (i != 0) {
            zzbfVar2.zza = i;
        }
        TextUtils.isEmpty(null);
        if (TextUtils.isEmpty(null)) {
            return;
        }
        TextUtils.isEmpty(null);
    }

    public final int zzd() {
        return this.zza;
    }
}
