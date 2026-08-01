package com.google.android.gms.internal.gtm;

import java.util.List;

/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
public final class zzac extends zzuz<zzac, zzab> implements zzwl {
    private static final zzac zza;
    private zzve zze = zzaf();
    private zzve zzf = zzaf();
    private zzve zzg = zzaf();
    private zzve zzh = zzaf();
    private zzve zzi = zzaf();
    private zzve zzj = zzaf();
    private zzve zzk = zzaf();
    private zzve zzl = zzaf();
    private zzve zzm = zzaf();
    private zzve zzn = zzaf();

    static {
        zzac zzacVar = new zzac();
        zza = zzacVar;
        zzuz.zzak(zzac.class, zzacVar);
    }

    private zzac() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.internal.gtm.zzuz
    public final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzaj(zza, "\u0001\n\u0000\u0000\u0001\n\n\u0000\n\u0000\u0001\u0016\u0002\u0016\u0003\u0016\u0004\u0016\u0005\u0016\u0006\u0016\u0007\u0016\b\u0016\t\u0016\n\u0016", new Object[]{"zze", "zzf", "zzg", "zzh", "zzi", "zzj", "zzk", "zzl", "zzm", "zzn"});
        }
        if (i2 == 3) {
            return new zzac();
        }
        zzn zznVar = null;
        if (i2 == 4) {
            return new zzab(zznVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zza;
    }

    public final List<Integer> zzc() {
        return this.zzk;
    }

    public final List<Integer> zzd() {
        return this.zzm;
    }

    public final List<Integer> zze() {
        return this.zzg;
    }

    public final List<Integer> zzf() {
        return this.zzi;
    }

    public final List<Integer> zzg() {
        return this.zzf;
    }

    public final List<Integer> zzh() {
        return this.zze;
    }

    public final List<Integer> zzi() {
        return this.zzl;
    }

    public final List<Integer> zzj() {
        return this.zzn;
    }

    public final List<Integer> zzk() {
        return this.zzh;
    }

    public final List<Integer> zzl() {
        return this.zzj;
    }
}
