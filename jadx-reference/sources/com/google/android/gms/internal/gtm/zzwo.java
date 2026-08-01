package com.google.android.gms.internal.gtm;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
final class zzwo<T> implements zzwx<T> {
    private final zzwk zza;
    private final zzxo<?, ?> zzb;
    private final boolean zzc;
    private final zzuk<?> zzd;

    private zzwo(zzxo<?, ?> zzxoVar, zzuk<?> zzukVar, zzwk zzwkVar) {
        this.zzb = zzxoVar;
        this.zzc = zzukVar.zzi(zzwkVar);
        this.zzd = zzukVar;
        this.zza = zzwkVar;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <T> zzwo<T> zzc(zzxo<?, ?> zzxoVar, zzuk<?> zzukVar, zzwk zzwkVar) {
        return new zzwo<>(zzxoVar, zzukVar, zzwkVar);
    }

    @Override // com.google.android.gms.internal.gtm.zzwx
    public final int zza(T t) {
        zzxo<?, ?> zzxoVar = this.zzb;
        int zzb = zzxoVar.zzb(zzxoVar.zzd(t));
        return this.zzc ? zzb + this.zzd.zzb(t).zzb() : zzb;
    }

    @Override // com.google.android.gms.internal.gtm.zzwx
    public final int zzb(T t) {
        int hashCode = this.zzb.zzd(t).hashCode();
        return this.zzc ? (hashCode * 53) + this.zzd.zzb(t).zza.hashCode() : hashCode;
    }

    @Override // com.google.android.gms.internal.gtm.zzwx
    public final T zze() {
        return (T) this.zza.zzao().zzD();
    }

    @Override // com.google.android.gms.internal.gtm.zzwx
    public final void zzf(T t) {
        this.zzb.zzm(t);
        this.zzd.zzf(t);
    }

    @Override // com.google.android.gms.internal.gtm.zzwx
    public final void zzg(T t, T t2) {
        zzwz.zzF(this.zzb, t, t2);
        if (this.zzc) {
            zzwz.zzE(this.zzd, t, t2);
        }
    }

    @Override // com.google.android.gms.internal.gtm.zzwx
    public final void zzh(T t, zzww zzwwVar, zzuj zzujVar) throws IOException {
        boolean zzT;
        zzxo<?, ?> zzxoVar = this.zzb;
        zzuk<?> zzukVar = this.zzd;
        Object zzc = zzxoVar.zzc(t);
        zzuo<?> zzc2 = zzukVar.zzc(t);
        while (zzwwVar.zzc() != Integer.MAX_VALUE) {
            try {
                int zzd = zzwwVar.zzd();
                if (zzd != 11) {
                    if ((zzd & 7) == 2) {
                        Object zzd2 = zzukVar.zzd(zzujVar, this.zza, zzd >>> 3);
                        if (zzd2 != null) {
                            zzukVar.zzg(zzwwVar, zzd2, zzujVar, zzc2);
                        } else {
                            zzT = zzxoVar.zzp(zzc, zzwwVar);
                        }
                    } else {
                        zzT = zzwwVar.zzT();
                    }
                    if (!zzT) {
                        return;
                    }
                } else {
                    int i = 0;
                    Object obj = null;
                    zztd zztdVar = null;
                    while (zzwwVar.zzc() != Integer.MAX_VALUE) {
                        int zzd3 = zzwwVar.zzd();
                        if (zzd3 == 16) {
                            i = zzwwVar.zzj();
                            obj = zzukVar.zzd(zzujVar, this.zza, i);
                        } else if (zzd3 == 26) {
                            if (obj != null) {
                                zzukVar.zzg(zzwwVar, obj, zzujVar, zzc2);
                            } else {
                                zztdVar = zzwwVar.zzq();
                            }
                        } else if (!zzwwVar.zzT()) {
                            break;
                        }
                    }
                    if (zzwwVar.zzd() != 12) {
                        throw zzvk.zzb();
                    }
                    if (zztdVar != null) {
                        if (obj != null) {
                            zzukVar.zzh(zztdVar, obj, zzujVar, zzc2);
                        } else {
                            zzxoVar.zzk(zzc, i, zztdVar);
                        }
                    }
                }
            } finally {
                zzxoVar.zzn(t, zzc);
            }
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for r11v0, resolved type: T */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:22:0x00ba  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00bf A[EDGE_INSN: B:24:0x00bf->B:25:0x00bf BREAK  A[LOOP:1: B:10:0x0067->B:18:0x0067], SYNTHETIC] */
    @Override // com.google.android.gms.internal.gtm.zzwx
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void zzi(T t, byte[] bArr, int i, int i2, zzsl zzslVar) throws IOException {
        zzuz zzuzVar = (zzuz) t;
        zzxp zzxpVar = zzuzVar.zzc;
        if (zzxpVar == zzxp.zzc()) {
            zzxpVar = zzxp.zze();
            zzuzVar.zzc = zzxpVar;
        }
        zzuo<zzuw> zzU = ((zzuv) t).zzU();
        Object obj = null;
        while (i < i2) {
            int zzj = zzsm.zzj(bArr, i, zzslVar);
            int i3 = zzslVar.zza;
            if (i3 == 11) {
                int i4 = 0;
                zztd zztdVar = null;
                while (zzj < i2) {
                    zzj = zzsm.zzj(bArr, zzj, zzslVar);
                    int i5 = zzslVar.zza;
                    int i6 = i5 & 7;
                    int i7 = i5 >>> 3;
                    if (i7 != 2) {
                        if (i7 == 3) {
                            if (obj != null) {
                                zzux zzuxVar = (zzux) obj;
                                zzj = zzsm.zzd(zzwt.zza().zzb(zzuxVar.zzc.getClass()), bArr, zzj, i2, zzslVar);
                                zzU.zzi(zzuxVar.zzd, zzslVar.zzc);
                            } else if (i6 == 2) {
                                zzj = zzsm.zza(bArr, zzj, zzslVar);
                                zztdVar = (zztd) zzslVar.zzc;
                            }
                        }
                        if (i5 != 12) {
                            break;
                        } else {
                            zzj = zzsm.zzn(i5, bArr, zzj, i2, zzslVar);
                        }
                    } else if (i6 == 0) {
                        zzj = zzsm.zzj(bArr, zzj, zzslVar);
                        i4 = zzslVar.zza;
                        obj = this.zzd.zzd(zzslVar.zzd, this.zza, i4);
                    } else if (i5 != 12) {
                    }
                }
                if (zztdVar != null) {
                    zzxpVar.zzh((i4 << 3) | 2, zztdVar);
                }
                i = zzj;
            } else if ((i3 & 7) == 2) {
                Object zzd = this.zzd.zzd(zzslVar.zzd, this.zza, i3 >>> 3);
                if (zzd != null) {
                    zzux zzuxVar2 = (zzux) zzd;
                    i = zzsm.zzd(zzwt.zza().zzb(zzuxVar2.zzc.getClass()), bArr, zzj, i2, zzslVar);
                    zzU.zzi(zzuxVar2.zzd, zzslVar.zzc);
                } else {
                    i = zzsm.zzi(i3, bArr, zzj, i2, zzxpVar, zzslVar);
                }
                obj = zzd;
            } else {
                i = zzsm.zzn(i3, bArr, zzj, i2, zzslVar);
            }
        }
        if (i != i2) {
            throw zzvk.zzg();
        }
    }

    @Override // com.google.android.gms.internal.gtm.zzwx
    public final boolean zzj(T t, T t2) {
        if (!this.zzb.zzd(t).equals(this.zzb.zzd(t2))) {
            return false;
        }
        if (this.zzc) {
            return this.zzd.zzb(t).equals(this.zzd.zzb(t2));
        }
        return true;
    }

    @Override // com.google.android.gms.internal.gtm.zzwx
    public final boolean zzk(T t) {
        return this.zzd.zzb(t).zzk();
    }

    @Override // com.google.android.gms.internal.gtm.zzwx
    public final void zzn(T t, zztp zztpVar) throws IOException {
        Iterator<Map.Entry<?, Object>> zzf = this.zzd.zzb(t).zzf();
        while (zzf.hasNext()) {
            Map.Entry<?, Object> next = zzf.next();
            zzun zzunVar = (zzun) next.getKey();
            if (zzunVar.zze() == zzyf.MESSAGE) {
                zzunVar.zzg();
                zzunVar.zzf();
                if (next instanceof zzvn) {
                    zztpVar.zzw(zzunVar.zza(), ((zzvn) next).zza().zzb());
                } else {
                    zztpVar.zzw(zzunVar.zza(), next.getValue());
                }
            } else {
                throw new IllegalStateException("Found invalid MessageSet item.");
            }
        }
        zzxo<?, ?> zzxoVar = this.zzb;
        zzxoVar.zzr(zzxoVar.zzd(t), zztpVar);
    }
}
