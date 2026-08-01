package com.google.android.gms.internal.gtm;

import java.io.IOException;

/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
abstract class zzxo<T, B> {
    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract int zza(T t);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract int zzb(T t);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract B zzc(Object obj);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract T zzd(Object obj);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract T zze(T t, T t2);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract B zzf();

    abstract T zzg(B b);

    abstract void zzh(B b, int i, int i2);

    abstract void zzi(B b, int i, long j);

    abstract void zzj(B b, int i, T t);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void zzk(B b, int i, zztd zztdVar);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void zzl(B b, int i, long j);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void zzm(Object obj);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void zzn(Object obj, B b);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void zzo(Object obj, T t);

    /* JADX INFO: Access modifiers changed from: package-private */
    public final boolean zzp(B b, zzww zzwwVar) throws IOException {
        int zzd = zzwwVar.zzd();
        int i = zzd >>> 3;
        int i2 = zzd & 7;
        if (i2 == 0) {
            zzl(b, i, zzwwVar.zzl());
            return true;
        }
        if (i2 == 1) {
            zzi(b, i, zzwwVar.zzk());
            return true;
        }
        if (i2 == 2) {
            zzk(b, i, zzwwVar.zzq());
            return true;
        }
        if (i2 != 3) {
            if (i2 == 4) {
                return false;
            }
            if (i2 == 5) {
                zzh(b, i, zzwwVar.zzf());
                return true;
            }
            throw zzvk.zza();
        }
        B zzf = zzf();
        int i3 = 4 | (i << 3);
        while (zzwwVar.zzc() != Integer.MAX_VALUE && zzp(zzf, zzwwVar)) {
        }
        if (i3 != zzwwVar.zzd()) {
            throw zzvk.zzb();
        }
        zzg(zzf);
        zzj(b, i, zzf);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract boolean zzq(zzww zzwwVar);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void zzr(T t, zztp zztpVar) throws IOException;

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void zzs(T t, zztp zztpVar) throws IOException;
}
