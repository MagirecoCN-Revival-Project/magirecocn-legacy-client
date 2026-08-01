package com.google.android.gms.internal.gtm;

import sun.misc.Unsafe;

/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
final class zzxv extends zzxx {
    /* JADX INFO: Access modifiers changed from: package-private */
    public zzxv(Unsafe unsafe) {
        super(unsafe);
    }

    @Override // com.google.android.gms.internal.gtm.zzxx
    public final double zza(Object obj, long j) {
        return Double.longBitsToDouble(zzk(obj, j));
    }

    @Override // com.google.android.gms.internal.gtm.zzxx
    public final float zzb(Object obj, long j) {
        return Float.intBitsToFloat(zzj(obj, j));
    }

    @Override // com.google.android.gms.internal.gtm.zzxx
    public final void zzc(Object obj, long j, boolean z) {
        if (zzxy.zzb) {
            zzxy.zzD(obj, j, r3 ? (byte) 1 : (byte) 0);
        } else {
            zzxy.zzE(obj, j, r3 ? (byte) 1 : (byte) 0);
        }
    }

    @Override // com.google.android.gms.internal.gtm.zzxx
    public final void zzd(Object obj, long j, byte b) {
        if (zzxy.zzb) {
            zzxy.zzD(obj, j, b);
        } else {
            zzxy.zzE(obj, j, b);
        }
    }

    @Override // com.google.android.gms.internal.gtm.zzxx
    public final void zze(Object obj, long j, double d) {
        zzo(obj, j, Double.doubleToLongBits(d));
    }

    @Override // com.google.android.gms.internal.gtm.zzxx
    public final void zzf(Object obj, long j, float f) {
        zzn(obj, j, Float.floatToIntBits(f));
    }

    @Override // com.google.android.gms.internal.gtm.zzxx
    public final boolean zzg(Object obj, long j) {
        if (zzxy.zzb) {
            return zzxy.zzt(obj, j);
        }
        return zzxy.zzu(obj, j);
    }
}
