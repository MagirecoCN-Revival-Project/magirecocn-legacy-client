package com.google.android.gms.internal.gtm;

import java.util.List;

/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
public final class zzaa extends zzuz<zzaa, zzz> implements zzwl {
    private static final zzaa zza;
    private int zze;
    private zzq zzr;
    private float zzs;
    private boolean zzt;
    private int zzv;
    private byte zzw = 2;
    private zzvh<String> zzf = zzuz.zzag();
    private zzvh<String> zzg = zzuz.zzag();
    private zzvh<zzak> zzh = zzag();
    private zzvh<zzy> zzi = zzag();
    private zzvh<zzs> zzj = zzag();
    private zzvh<zzs> zzk = zzag();
    private zzvh<zzs> zzl = zzag();
    private zzvh<zzac> zzm = zzag();
    private String zzn = "";
    private String zzo = "";
    private String zzp = "0";
    private String zzq = "";
    private zzvh<String> zzu = zzuz.zzag();

    static {
        zzaa zzaaVar = new zzaa();
        zza = zzaaVar;
        zzuz.zzak(zzaa.class, zzaaVar);
    }

    private zzaa() {
    }

    public static zzz zzj() {
        return zza.zzY();
    }

    public static zzaa zzl() {
        return zza;
    }

    public static zzaa zzm(byte[] bArr, zzuj zzujVar) throws zzvk {
        return (zzaa) zzuz.zzad(zza, bArr, zzujVar);
    }

    public final int zza() {
        return this.zzj.size();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.internal.gtm.zzuz
    public final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return Byte.valueOf(this.zzw);
        }
        if (i2 == 2) {
            return zzaj(zza, "\u0001\u0011\u0000\u0001\u0001\u0013\u0011\u0000\t\u0005\u0001\u001a\u0002Л\u0003Л\u0004Л\u0005Л\u0006Л\u0007\u001b\tဈ\u0000\nဈ\u0001\fဈ\u0002\rဈ\u0003\u000eဉ\u0004\u000fခ\u0005\u0010\u001a\u0011င\u0007\u0012ဇ\u0006\u0013\u001a", new Object[]{"zze", "zzg", "zzh", zzak.class, "zzi", zzy.class, "zzj", zzs.class, "zzk", zzs.class, "zzl", zzs.class, "zzm", zzac.class, "zzn", "zzo", "zzp", "zzq", "zzr", "zzs", "zzu", "zzv", "zzt", "zzf"});
        }
        if (i2 == 3) {
            return new zzaa();
        }
        zzn zznVar = null;
        if (i2 == 4) {
            return new zzz(zznVar);
        }
        if (i2 == 5) {
            return zza;
        }
        this.zzw = obj == null ? (byte) 0 : (byte) 1;
        return null;
    }

    public final int zzc() {
        return this.zzl.size();
    }

    public final int zzd() {
        return this.zzv;
    }

    public final int zze() {
        return this.zzk.size();
    }

    public final int zzf() {
        return this.zzh.size();
    }

    public final zzs zzg(int i) {
        return this.zzj.get(i);
    }

    public final zzs zzh(int i) {
        return this.zzl.get(i);
    }

    public final zzs zzi(int i) {
        return this.zzk.get(i);
    }

    public final zzak zzn(int i) {
        return this.zzh.get(i);
    }

    public final String zzo() {
        return this.zzq;
    }

    public final List<String> zzp() {
        return this.zzg;
    }

    public final List<zzy> zzq() {
        return this.zzi;
    }

    public final List<zzac> zzr() {
        return this.zzm;
    }

    public final List<zzak> zzs() {
        return this.zzh;
    }
}
