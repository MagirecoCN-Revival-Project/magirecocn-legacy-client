package com.google.android.gms.internal.measurement;

/* loaded from: classes.dex */
final class zzaar<T> implements zzaax<T> {
    private final zzaan zzbtu;
    private final zzabl<?, ?> zzbtv;
    private final boolean zzbtw;
    private final zzzl<?> zzbtx;

    private zzaar(zzabl<?, ?> zzablVar, zzzl<?> zzzlVar, zzaan zzaanVar) {
        this.zzbtv = zzablVar;
        this.zzbtw = zzzlVar.zza(zzaanVar);
        this.zzbtx = zzzlVar;
        this.zzbtu = zzaanVar;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <T> zzaar<T> zza(zzabl<?, ?> zzablVar, zzzl<?> zzzlVar, zzaan zzaanVar) {
        return new zzaar<>(zzablVar, zzzlVar, zzaanVar);
    }

    @Override // com.google.android.gms.internal.measurement.zzaax
    public final boolean equals(T t, T t2) {
        if (!this.zzbtv.zzu(t).equals(this.zzbtv.zzu(t2))) {
            return false;
        }
        if (this.zzbtw) {
            return this.zzbtx.zzs(t).equals(this.zzbtx.zzs(t2));
        }
        return true;
    }

    @Override // com.google.android.gms.internal.measurement.zzaax
    public final int hashCode(T t) {
        int hashCode = this.zzbtv.zzu(t).hashCode();
        return this.zzbtw ? (hashCode * 53) + this.zzbtx.zzs(t).hashCode() : hashCode;
    }
}
