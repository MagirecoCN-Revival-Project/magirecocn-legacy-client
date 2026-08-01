package com.google.android.gms.internal.gtm;

import java.util.List;

/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
public final class zzs extends zzuz<zzs, zzr> implements zzwl {
    private static final zzs zza;
    private int zze;
    private int zzg;
    private int zzh;
    private boolean zzi;
    private boolean zzj;
    private byte zzk = 2;
    private zzve zzf = zzaf();

    static {
        zzs zzsVar = new zzs();
        zza = zzsVar;
        zzuz.zzak(zzs.class, zzsVar);
    }

    private zzs() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.internal.gtm.zzuz
    public final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return Byte.valueOf(this.zzk);
        }
        if (i2 == 2) {
            return zzaj(zza, "\u0001\u0005\u0000\u0001\u0001\u0006\u0005\u0000\u0001\u0001\u0001ဇ\u0003\u0002ᔄ\u0000\u0003\u0016\u0004င\u0001\u0006ဇ\u0002", new Object[]{"zze", "zzj", "zzg", "zzf", "zzh", "zzi"});
        }
        if (i2 == 3) {
            return new zzs();
        }
        zzn zznVar = null;
        if (i2 == 4) {
            return new zzr(zznVar);
        }
        if (i2 == 5) {
            return zza;
        }
        this.zzk = obj == null ? (byte) 0 : (byte) 1;
        return null;
    }

    public final List<Integer> zzc() {
        return this.zzf;
    }
}
