package com.google.android.gms.internal.gtm;

/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
public final class zzy extends zzuz<zzy, zzx> implements zzwl {
    private static final zzy zza;
    private int zze;
    private int zzf;
    private int zzg;
    private byte zzh = 2;

    static {
        zzy zzyVar = new zzy();
        zza = zzyVar;
        zzuz.zzak(zzy.class, zzyVar);
    }

    private zzy() {
    }

    public final int zza() {
        return this.zzf;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.internal.gtm.zzuz
    public final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return Byte.valueOf(this.zzh);
        }
        if (i2 == 2) {
            return zzaj(zza, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0002\u0001ᔄ\u0000\u0002ᔄ\u0001", new Object[]{"zze", "zzf", "zzg"});
        }
        if (i2 == 3) {
            return new zzy();
        }
        zzn zznVar = null;
        if (i2 == 4) {
            return new zzx(zznVar);
        }
        if (i2 == 5) {
            return zza;
        }
        this.zzh = obj == null ? (byte) 0 : (byte) 1;
        return null;
    }

    public final int zzc() {
        return this.zzg;
    }
}
