package com.google.android.gms.internal.measurement;

/* loaded from: classes.dex */
final class zzaak {
    private static final zzaai zzbtq = zzue();
    private static final zzaai zzbtr = new zzaaj();

    /* JADX INFO: Access modifiers changed from: package-private */
    public static zzaai zzuc() {
        return zzbtq;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static zzaai zzud() {
        return zzbtr;
    }

    private static zzaai zzue() {
        try {
            return (zzaai) Class.forName("com.google.protobuf.MapFieldSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception unused) {
            return null;
        }
    }
}
