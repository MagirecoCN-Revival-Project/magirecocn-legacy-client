package com.google.android.gms.internal.measurement;

import com.google.android.gms.internal.measurement.zzzs;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzaaf implements zzaay {
    private static final zzaam zzbto = new zzaag();
    private final zzaam zzbtn;

    public zzaaf() {
        this(new zzaah(zzzr.zztu(), zzub()));
    }

    private zzaaf(zzaam zzaamVar) {
        this.zzbtn = (zzaam) zzzt.zza(zzaamVar, "messageInfoFactory");
    }

    private static boolean zza(zzaal zzaalVar) {
        return zzaalVar.zzuf() == zzzs.zzb.zzbsu;
    }

    private static zzaam zzub() {
        try {
            return (zzaam) Class.forName("com.google.protobuf.DescriptorMessageInfoFactory").getDeclaredMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
        } catch (Exception unused) {
            return zzbto;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzaay
    public final <T> zzaax<T> zzg(Class<T> cls) {
        zzaaz.zzh(cls);
        zzaal zze = this.zzbtn.zze(cls);
        if (zze.zzug()) {
            return zzzs.class.isAssignableFrom(cls) ? zzaar.zza(zzaaz.zzup(), zzzn.zztp(), zze.zzuh()) : zzaar.zza(zzaaz.zzun(), zzzn.zztq(), zze.zzuh());
        }
        if (!zzzs.class.isAssignableFrom(cls)) {
            boolean zza = zza(zze);
            zzaas zzuj = zzaau.zzuj();
            zzaab zztz = zzaab.zztz();
            return zza ? zzaaq.zza(cls, zze, zzuj, zztz, zzaaz.zzun(), zzzn.zztq(), zzaak.zzuc()) : zzaaq.zza(cls, zze, zzuj, zztz, zzaaz.zzuo(), null, zzaak.zzuc());
        }
        boolean zza2 = zza(zze);
        zzaas zzuk = zzaau.zzuk();
        zzaab zzua = zzaab.zzua();
        zzabl<?, ?> zzup = zzaaz.zzup();
        return zza2 ? zzaaq.zza(cls, zze, zzuk, zzua, zzup, zzzn.zztp(), zzaak.zzud()) : zzaaq.zza(cls, zze, zzuk, zzua, zzup, null, zzaak.zzud());
    }
}
