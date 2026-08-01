package com.google.android.gms.internal.gtm;

/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
public final class zzk extends zzuz<zzk, zzj> implements zzwl {
    private static final zzk zza;
    private int zze;
    private zzak zzl;
    private byte zzm = 2;
    private zzvh<zzg> zzf = zzag();
    private zzvh<zzg> zzg = zzag();
    private zzvh<zzg> zzh = zzag();
    private zzvh<zzg> zzi = zzag();
    private zzvh<zzg> zzj = zzag();
    private zzvh<zzg> zzk = zzag();

    static {
        zzk zzkVar = new zzk();
        zza = zzkVar;
        zzuz.zzak(zzk.class, zzkVar);
    }

    private zzk() {
    }

    @Override // com.google.android.gms.internal.gtm.zzuz
    public final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return Byte.valueOf(this.zzm);
        }
        if (i2 == 2) {
            return zzaj(zza, "\u0001\u0007\u0000\u0001\u0001\u0007\u0007\u0000\u0006\u0007\u0001Л\u0002Л\u0003Л\u0004Л\u0005Л\u0006Л\u0007ᐉ\u0000", new Object[]{"zze", "zzf", zzg.class, "zzg", zzg.class, "zzh", zzg.class, "zzi", zzg.class, "zzj", zzg.class, "zzk", zzg.class, "zzl"});
        }
        if (i2 == 3) {
            return new zzk();
        }
        if (i2 == 4) {
            return new zzj(null);
        }
        if (i2 == 5) {
            return zza;
        }
        this.zzm = obj == null ? (byte) 0 : (byte) 1;
        return null;
    }
}
