package com.google.android.gms.internal.measurement;

import java.io.IOException;

/* loaded from: classes.dex */
public abstract class zzacg {
    protected volatile int zzbxr = -1;

    public static final <T extends zzacg> T zza(T t, byte[] bArr) throws zzacf {
        return (T) zzb(t, bArr, 0, bArr.length);
    }

    public static final void zza(zzacg zzacgVar, byte[] bArr, int i, int i2) {
        try {
            zzaby zzb = zzaby.zzb(bArr, 0, i2);
            zzacgVar.zza(zzb);
            zzb.zzvn();
        } catch (IOException e) {
            throw new RuntimeException("Serializing to a byte array threw an IOException (should never happen).", e);
        }
    }

    private static final <T extends zzacg> T zzb(T t, byte[] bArr, int i, int i2) throws zzacf {
        try {
            zzabx zza = zzabx.zza(bArr, 0, i2);
            t.zzb(zza);
            zza.zzaj(0);
            return t;
        } catch (zzacf e) {
            throw e;
        } catch (IOException e2) {
            throw new RuntimeException("Reading from a byte array threw an IOException (should never happen).", e2);
        }
    }

    public String toString() {
        return zzach.zzc(this);
    }

    protected int zza() {
        return 0;
    }

    public void zza(zzaby zzabyVar) throws IOException {
    }

    public abstract zzacg zzb(zzabx zzabxVar) throws IOException;

    /* JADX DEBUG: Method merged with bridge method: clone()Ljava/lang/Object; */
    @Override // 
    /* renamed from: zzvo, reason: merged with bridge method [inline-methods] */
    public zzacg clone() throws CloneNotSupportedException {
        return (zzacg) super.clone();
    }

    public final int zzvu() {
        if (this.zzbxr < 0) {
            zzvv();
        }
        return this.zzbxr;
    }

    public final int zzvv() {
        int zza = zza();
        this.zzbxr = zza;
        return zza;
    }
}
