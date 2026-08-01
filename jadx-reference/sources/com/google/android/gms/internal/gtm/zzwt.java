package com.google.android.gms.internal.gtm;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
public final class zzwt {
    private static final zzwt zza = new zzwt();
    private final ConcurrentMap<Class<?>, zzwx<?>> zzc = new ConcurrentHashMap();
    private final zzwy zzb = new zzwc();

    private zzwt() {
    }

    public static zzwt zza() {
        return zza;
    }

    public final <T> zzwx<T> zzb(Class<T> cls) {
        zzvi.zzf(cls, "messageType");
        zzwx<T> zzwxVar = (zzwx) this.zzc.get(cls);
        if (zzwxVar == null) {
            zzwxVar = this.zzb.zza(cls);
            zzvi.zzf(cls, "messageType");
            zzvi.zzf(zzwxVar, "schema");
            zzwx<T> zzwxVar2 = (zzwx) this.zzc.putIfAbsent(cls, zzwxVar);
            if (zzwxVar2 != null) {
                return zzwxVar2;
            }
        }
        return zzwxVar;
    }
}
