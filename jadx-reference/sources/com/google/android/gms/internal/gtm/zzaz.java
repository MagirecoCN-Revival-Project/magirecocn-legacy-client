package com.google.android.gms.internal.gtm;

import com.google.android.gms.common.internal.Preconditions;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
public final class zzaz extends com.google.android.gms.analytics.zzj<zzaz> {
    private final Map<String, Object> zza = new HashMap();

    public final String toString() {
        return zza(this.zza);
    }

    /* JADX DEBUG: Method arguments types fixed to match base method, original types: [com.google.android.gms.analytics.zzj] */
    @Override // com.google.android.gms.analytics.zzj
    public final /* bridge */ /* synthetic */ void zzc(zzaz zzazVar) {
        zzaz zzazVar2 = zzazVar;
        Preconditions.checkNotNull(zzazVar2);
        zzazVar2.zza.putAll(this.zza);
    }

    public final Map<String, Object> zzd() {
        return Collections.unmodifiableMap(this.zza);
    }

    public final void zze(String str, String str2) {
        Preconditions.checkNotEmpty(str);
        if (str != null && str.startsWith("&")) {
            str = str.substring(1);
        }
        Preconditions.checkNotEmpty(str, "Name can not be empty or \"&\"");
        this.zza.put(str, str2);
    }
}
