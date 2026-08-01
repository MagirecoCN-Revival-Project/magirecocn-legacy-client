package com.google.android.gms.internal.gtm;

/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
public final class zzag extends zzuz<zzag, zzaf> implements zzwl {
    private static final zzag zza;
    private int zze;
    private zzak zzg;
    private zzw zzh;
    private byte zzi = 2;
    private String zzf = "";

    static {
        zzag zzagVar = new zzag();
        zza = zzagVar;
        zzuz.zzak(zzag.class, zzagVar);
    }

    private zzag() {
    }

    public final zzw zza() {
        zzw zzwVar = this.zzh;
        return zzwVar == null ? zzw.zzc() : zzwVar;
    }

    @Override // com.google.android.gms.internal.gtm.zzuz
    public final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return Byte.valueOf(this.zzi);
        }
        if (i2 == 2) {
            return zzaj(zza, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0002\u0001ဈ\u0000\u0002ᐉ\u0001\u0003ᐉ\u0002", new Object[]{"zze", "zzf", "zzg", "zzh"});
        }
        if (i2 == 3) {
            return new zzag();
        }
        if (i2 == 4) {
            return new zzaf(null);
        }
        if (i2 == 5) {
            return zza;
        }
        this.zzi = obj == null ? (byte) 0 : (byte) 1;
        return null;
    }

    public final String zzd() {
        return this.zzf;
    }

    public final boolean zze() {
        return (this.zze & 4) != 0;
    }

    public final boolean zzf() {
        return (this.zze & 1) != 0;
    }
}
