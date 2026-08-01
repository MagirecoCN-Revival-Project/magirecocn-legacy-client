package com.google.android.gms.internal.gtm;

/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
public final class zztv extends zzuz<zztv, zztu> implements zzwl {
    private static final zztv zza;
    private int zze;
    private String zzf = "";
    private String zzg = "";

    static {
        zztv zztvVar = new zztv();
        zza = zztvVar;
        zzuz.zzak(zztv.class, zztvVar);
    }

    private zztv() {
    }

    @Override // com.google.android.gms.internal.gtm.zzuz
    public final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return new zzwv(zza, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ဈ\u0000\u0002ဈ\u0001", new Object[]{"zze", "zzf", "zzg"});
        }
        if (i2 == 3) {
            return new zztv();
        }
        if (i2 == 4) {
            return new zztu(null);
        }
        if (i2 != 5) {
            return null;
        }
        return zza;
    }
}
