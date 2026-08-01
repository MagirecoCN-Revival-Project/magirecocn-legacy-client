package com.google.android.gms.internal.gtm;

/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
public final class zzq extends zzuz<zzq, zzo> implements zzwl {
    private static final zzq zza;
    private int zze;
    private int zzf = 1;
    private int zzg;
    private int zzh;

    static {
        zzq zzqVar = new zzq();
        zza = zzqVar;
        zzuz.zzak(zzq.class, zzqVar);
    }

    private zzq() {
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.google.android.gms.internal.gtm.zzo.<init>():void, com.google.android.gms.internal.gtm.zzo.<init>(com.google.android.gms.internal.gtm.zzn):void] */
    public static /* synthetic */ zzq zza() {
        return zza;
    }

    @Override // com.google.android.gms.internal.gtm.zzuz
    public final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzaj(zza, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဌ\u0000\u0002င\u0001\u0003င\u0002", new Object[]{"zze", "zzf", zzp.zza, "zzg", "zzh"});
        }
        if (i2 == 3) {
            return new zzq();
        }
        if (i2 == 4) {
            return new zzo(null);
        }
        if (i2 != 5) {
            return null;
        }
        return zza;
    }
}
