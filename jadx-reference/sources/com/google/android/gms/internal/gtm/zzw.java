package com.google.android.gms.internal.gtm;

import java.util.List;

/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
public final class zzw extends zzuz<zzw, zzv> implements zzwl {
    private static final zzw zza;
    private byte zzh = 2;
    private zzvh<zzak> zze = zzag();
    private zzvh<zzak> zzf = zzag();
    private zzvh<zzu> zzg = zzag();

    static {
        zzw zzwVar = new zzw();
        zza = zzwVar;
        zzuz.zzak(zzw.class, zzwVar);
    }

    private zzw() {
    }

    public static zzw zzc() {
        return zza;
    }

    @Override // com.google.android.gms.internal.gtm.zzuz
    public final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return Byte.valueOf(this.zzh);
        }
        if (i2 == 2) {
            return zzaj(zza, "\u0001\u0003\u0000\u0000\u0001\u0003\u0003\u0000\u0003\u0002\u0001Л\u0002Л\u0003\u001b", new Object[]{"zze", zzak.class, "zzf", zzak.class, "zzg", zzu.class});
        }
        if (i2 == 3) {
            return new zzw();
        }
        if (i2 == 4) {
            return new zzv(null);
        }
        if (i2 == 5) {
            return zza;
        }
        this.zzh = obj == null ? (byte) 0 : (byte) 1;
        return null;
    }

    public final List<zzu> zzd() {
        return this.zzg;
    }

    public final List<zzak> zze() {
        return this.zzf;
    }

    public final List<zzak> zzf() {
        return this.zze;
    }
}
