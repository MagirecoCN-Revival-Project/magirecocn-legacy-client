package com.google.android.gms.internal.gtm;

/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
public final class zzu extends zzuz<zzu, zzt> implements zzwl {
    private static final zzu zza;
    private int zze;
    private long zzg;
    private boolean zzi;
    private long zzj;
    private String zzf = "";
    private long zzh = 2147483647L;

    static {
        zzu zzuVar = new zzu();
        zza = zzuVar;
        zzuz.zzak(zzu.class, zzuVar);
    }

    private zzu() {
    }

    public final long zza() {
        return this.zzj;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.internal.gtm.zzuz
    public final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzaj(zza, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0000\u0000\u0001ဈ\u0000\u0002ဂ\u0001\u0003ဂ\u0002\u0004ဇ\u0003\u0005ဂ\u0004", new Object[]{"zze", "zzf", "zzg", "zzh", "zzi", "zzj"});
        }
        if (i2 == 3) {
            return new zzu();
        }
        zzn zznVar = null;
        if (i2 == 4) {
            return new zzt(zznVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zza;
    }

    public final long zzc() {
        return this.zzh;
    }

    public final long zzd() {
        return this.zzg;
    }

    public final String zzf() {
        return this.zzf;
    }

    public final boolean zzg() {
        return this.zzi;
    }

    public final boolean zzh() {
        return (this.zze & 1) != 0;
    }
}
