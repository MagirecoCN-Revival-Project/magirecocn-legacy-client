package com.google.android.gms.internal.gtm;

/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
public final class zzi extends zzuz<zzi, zzh> implements zzwl {
    private static final zzi zza;
    private int zze;
    private zzak zzg;
    private byte zzh = 2;
    private String zzf = "";

    static {
        zzi zziVar = new zzi();
        zza = zziVar;
        zzuz.zzak(zzi.class, zziVar);
    }

    private zzi() {
    }

    @Override // com.google.android.gms.internal.gtm.zzuz
    public final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return Byte.valueOf(this.zzh);
        }
        if (i2 == 2) {
            return zzaj(zza, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0001\u0001ဈ\u0000\u0002ᐉ\u0001", new Object[]{"zze", "zzf", "zzg"});
        }
        if (i2 == 3) {
            return new zzi();
        }
        if (i2 == 4) {
            return new zzh(null);
        }
        if (i2 == 5) {
            return zza;
        }
        this.zzh = obj == null ? (byte) 0 : (byte) 1;
        return null;
    }
}
