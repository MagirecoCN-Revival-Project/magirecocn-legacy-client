package com.google.android.gms.internal.measurement;

/* loaded from: classes.dex */
final class zzyx {
    private static final Class<?> zzbrf = zzff("libcore.io.Memory");
    private static final boolean zzbrg;

    static {
        zzbrg = zzff("org.robolectric.Robolectric") != null;
    }

    private static <T> Class<T> zzff(String str) {
        try {
            return (Class<T>) Class.forName(str);
        } catch (Throwable unused) {
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean zzte() {
        return (zzbrf == null || zzbrg) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Class<?> zztf() {
        return zzbrf;
    }
}
