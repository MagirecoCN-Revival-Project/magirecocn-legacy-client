package com.google.android.gms.internal.measurement;

/* loaded from: classes.dex */
final class zzaah implements zzaam {
    private zzaam[] zzbtp;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzaah(zzaam... zzaamVarArr) {
        this.zzbtp = zzaamVarArr;
    }

    @Override // com.google.android.gms.internal.measurement.zzaam
    public final boolean zzd(Class<?> cls) {
        for (zzaam zzaamVar : this.zzbtp) {
            if (zzaamVar.zzd(cls)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.google.android.gms.internal.measurement.zzaam
    public final zzaal zze(Class<?> cls) {
        for (zzaam zzaamVar : this.zzbtp) {
            if (zzaamVar.zzd(cls)) {
                return zzaamVar.zze(cls);
            }
        }
        String valueOf = String.valueOf(cls.getName());
        throw new UnsupportedOperationException(valueOf.length() != 0 ? "No factory is available for message type: ".concat(valueOf) : new String("No factory is available for message type: "));
    }
}
