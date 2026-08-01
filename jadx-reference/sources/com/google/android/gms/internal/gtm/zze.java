package com.google.android.gms.internal.gtm;

/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
public final class zze extends zzuz<zze, zzd> implements zzwl {
    public static final zzux<zzak, zze> zza;
    private static final zze zze;
    private int zzf;
    private zzm zzg;
    private zzg zzh;
    private byte zzi = 2;

    static {
        zze zzeVar = new zze();
        zze = zzeVar;
        zzuz.zzak(zze.class, zzeVar);
        zza = zzuz.zzaa(zzak.zzi(), zzeVar, zzeVar, null, 47497405, zzye.MESSAGE, zze.class);
    }

    private zze() {
    }

    @Override // com.google.android.gms.internal.gtm.zzuz
    public final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return Byte.valueOf(this.zzi);
        }
        if (i2 == 2) {
            return zzaj(zze, "\u0001\u0002\u0000\u0001\u0001\u0003\u0002\u0000\u0000\u0002\u0001ᐉ\u0000\u0003ᐉ\u0001", new Object[]{"zzf", "zzg", "zzh"});
        }
        if (i2 == 3) {
            return new zze();
        }
        if (i2 == 4) {
            return new zzd(null);
        }
        if (i2 == 5) {
            return zze;
        }
        this.zzi = obj == null ? (byte) 0 : (byte) 1;
        return null;
    }
}
