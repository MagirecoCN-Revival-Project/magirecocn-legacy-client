package com.google.android.gms.internal.measurement;

/* loaded from: classes.dex */
final class zzaau {
    private static final zzaas zzbty = zzul();
    private static final zzaas zzbtz = new zzaat();

    /* JADX INFO: Access modifiers changed from: package-private */
    public static zzaas zzuj() {
        return zzbty;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static zzaas zzuk() {
        return zzbtz;
    }

    private static zzaas zzul() {
        try {
            return (zzaas) Class.forName("com.google.protobuf.NewInstanceSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception unused) {
            return null;
        }
    }
}
