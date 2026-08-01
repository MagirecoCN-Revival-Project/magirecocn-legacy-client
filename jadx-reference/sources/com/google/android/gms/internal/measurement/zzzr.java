package com.google.android.gms.internal.measurement;

/* loaded from: classes.dex */
final class zzzr implements zzaam {
    private static final zzzr zzbsh = new zzzr();

    private zzzr() {
    }

    public static zzzr zztu() {
        return zzbsh;
    }

    @Override // com.google.android.gms.internal.measurement.zzaam
    public final boolean zzd(Class<?> cls) {
        return zzzs.class.isAssignableFrom(cls);
    }

    @Override // com.google.android.gms.internal.measurement.zzaam
    public final zzaal zze(Class<?> cls) {
        if (!zzzs.class.isAssignableFrom(cls)) {
            String valueOf = String.valueOf(cls.getName());
            throw new IllegalArgumentException(valueOf.length() != 0 ? "Unsupported message type: ".concat(valueOf) : new String("Unsupported message type: "));
        }
        try {
            return (zzaal) zzzs.zzf(cls.asSubclass(zzzs.class)).zza(3, (Object) null, (Object) null);
        } catch (Exception e) {
            String valueOf2 = String.valueOf(cls.getName());
            throw new RuntimeException(valueOf2.length() != 0 ? "Unable to get message info for ".concat(valueOf2) : new String("Unable to get message info for "), e);
        }
    }
}
