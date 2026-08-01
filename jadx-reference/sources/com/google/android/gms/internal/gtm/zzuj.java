package com.google.android.gms.internal.gtm;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
public class zzuj {
    static final zzuj zza = new zzuj(true);
    private static volatile boolean zzb;
    private static volatile zzuj zzc;
    private static volatile zzuj zzd;
    private final Map<zzui, zzux<?, ?>> zze;

    zzuj() {
        this.zze = new HashMap();
    }

    public static zzuj zza() {
        zzuj zzujVar = zzc;
        if (zzujVar == null) {
            synchronized (zzuj.class) {
                zzujVar = zzc;
                if (zzujVar == null) {
                    zzujVar = zza;
                    zzc = zzujVar;
                }
            }
        }
        return zzujVar;
    }

    public <ContainingType extends zzwk> zzux<ContainingType, ?> zzc(ContainingType containingtype, int i) {
        return (zzux) this.zze.get(new zzui(containingtype, i));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzuj(boolean z) {
        this.zze = Collections.emptyMap();
    }

    public static zzuj zzb() {
        zzuj zzujVar = zzd;
        if (zzujVar != null) {
            return zzujVar;
        }
        synchronized (zzuj.class) {
            zzuj zzujVar2 = zzd;
            if (zzujVar2 != null) {
                return zzujVar2;
            }
            zzuj zzb2 = zzur.zzb(zzuj.class);
            zzd = zzb2;
            return zzb2;
        }
    }
}
