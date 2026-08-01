package com.google.android.gms.internal.measurement;

/* loaded from: classes.dex */
public class zzaaa {
    private static final zzzk zzbtg = zzzk.zztn();
    private zzyy zzbth;
    private volatile zzaan zzbti;
    private volatile zzyy zzbtj;

    private final zzaan zzb(zzaan zzaanVar) {
        if (this.zzbti == null) {
            synchronized (this) {
                if (this.zzbti == null) {
                    try {
                        this.zzbti = zzaanVar;
                        this.zzbtj = zzyy.zzbrh;
                    } catch (zzzv unused) {
                        this.zzbti = zzaanVar;
                        this.zzbtj = zzyy.zzbrh;
                    }
                }
            }
        }
        return this.zzbti;
    }

    private final zzyy zzty() {
        if (this.zzbtj != null) {
            return this.zzbtj;
        }
        synchronized (this) {
            if (this.zzbtj != null) {
                return this.zzbtj;
            }
            this.zzbtj = this.zzbti == null ? zzyy.zzbrh : this.zzbti.zzty();
            return this.zzbtj;
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzaaa)) {
            return false;
        }
        zzaaa zzaaaVar = (zzaaa) obj;
        zzaan zzaanVar = this.zzbti;
        zzaan zzaanVar2 = zzaaaVar.zzbti;
        return (zzaanVar == null && zzaanVar2 == null) ? zzty().equals(zzaaaVar.zzty()) : (zzaanVar == null || zzaanVar2 == null) ? zzaanVar != null ? zzaanVar.equals(zzaaaVar.zzb(zzaanVar.zzui())) : zzb(zzaanVar2.zzui()).equals(zzaanVar2) : zzaanVar.equals(zzaanVar2);
    }

    public int hashCode() {
        return 1;
    }

    public final zzaan zzc(zzaan zzaanVar) {
        zzaan zzaanVar2 = this.zzbti;
        this.zzbth = null;
        this.zzbtj = null;
        this.zzbti = zzaanVar;
        return zzaanVar2;
    }
}
