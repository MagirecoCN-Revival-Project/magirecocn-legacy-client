package com.google.android.gms.internal.gtm;

import java.util.Iterator;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
final class zzwf {
    public static final int zza(int i, Object obj, Object obj2) {
        zzwe zzweVar = (zzwe) obj;
        if (zzweVar.isEmpty()) {
            return 0;
        }
        Iterator it = zzweVar.entrySet().iterator();
        if (!it.hasNext()) {
            return 0;
        }
        Map.Entry entry = (Map.Entry) it.next();
        entry.getKey();
        entry.getValue();
        throw null;
    }

    public static final boolean zzb(Object obj) {
        return !((zzwe) obj).zze();
    }

    public static final Object zzc(Object obj, Object obj2) {
        zzwe zzweVar = (zzwe) obj;
        zzwe zzweVar2 = (zzwe) obj2;
        if (!zzweVar2.isEmpty()) {
            if (!zzweVar.zze()) {
                zzweVar = zzweVar.zzb();
            }
            zzweVar.zzd(zzweVar2);
        }
        return zzweVar;
    }
}
