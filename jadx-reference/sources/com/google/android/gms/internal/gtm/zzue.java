package com.google.android.gms.internal.gtm;

/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
public final class zzue extends zzuz<zzue, zzud> implements zzwl {
    private static final zzue zza;
    private int zze;
    private boolean zzg;
    private byte zzh = 2;
    private String zzf = "";

    static {
        zzue zzueVar = new zzue();
        zza = zzueVar;
        zzuz.zzak(zzue.class, zzueVar);
    }

    private zzue() {
    }

    @Override // com.google.android.gms.internal.gtm.zzuz
    public final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return Byte.valueOf(this.zzh);
        }
        if (i2 == 2) {
            return new zzwv(zza, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0002\u0001ᔈ\u0000\u0002ᔇ\u0001", new Object[]{"zze", "zzf", "zzg"});
        }
        if (i2 == 3) {
            return new zzue();
        }
        if (i2 == 4) {
            return new zzud(null);
        }
        if (i2 == 5) {
            return zza;
        }
        this.zzh = obj == null ? (byte) 0 : (byte) 1;
        return null;
    }
}
