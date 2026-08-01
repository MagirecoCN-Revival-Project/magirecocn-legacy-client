package com.google.android.gms.internal.measurement;

import java.io.IOException;

/* loaded from: classes.dex */
public final class zzkn extends zzaca<zzkn> {
    private static volatile zzkn[] zzatg;
    public String zzny = null;
    public String value = null;

    public zzkn() {
        this.zzbxg = null;
        this.zzbxr = -1;
    }

    public static zzkn[] zzls() {
        if (zzatg == null) {
            synchronized (zzace.zzbxq) {
                if (zzatg == null) {
                    zzatg = new zzkn[0];
                }
            }
        }
        return zzatg;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzkn)) {
            return false;
        }
        zzkn zzknVar = (zzkn) obj;
        String str = this.zzny;
        if (str == null) {
            if (zzknVar.zzny != null) {
                return false;
            }
        } else if (!str.equals(zzknVar.zzny)) {
            return false;
        }
        String str2 = this.value;
        if (str2 == null) {
            if (zzknVar.value != null) {
                return false;
            }
        } else if (!str2.equals(zzknVar.value)) {
            return false;
        }
        return (this.zzbxg == null || this.zzbxg.isEmpty()) ? zzknVar.zzbxg == null || zzknVar.zzbxg.isEmpty() : this.zzbxg.equals(zzknVar.zzbxg);
    }

    public final int hashCode() {
        int hashCode = (getClass().getName().hashCode() + 527) * 31;
        String str = this.zzny;
        int i = 0;
        int hashCode2 = (hashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.value;
        int hashCode3 = (hashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        if (this.zzbxg != null && !this.zzbxg.isEmpty()) {
            i = this.zzbxg.hashCode();
        }
        return hashCode3 + i;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.internal.measurement.zzaca, com.google.android.gms.internal.measurement.zzacg
    public final int zza() {
        int zza = super.zza();
        String str = this.zzny;
        if (str != null) {
            zza += zzaby.zzc(1, str);
        }
        String str2 = this.value;
        return str2 != null ? zza + zzaby.zzc(2, str2) : zza;
    }

    @Override // com.google.android.gms.internal.measurement.zzaca, com.google.android.gms.internal.measurement.zzacg
    public final void zza(zzaby zzabyVar) throws IOException {
        String str = this.zzny;
        if (str != null) {
            zzabyVar.zzb(1, str);
        }
        String str2 = this.value;
        if (str2 != null) {
            zzabyVar.zzb(2, str2);
        }
        super.zza(zzabyVar);
    }

    @Override // com.google.android.gms.internal.measurement.zzacg
    public final /* synthetic */ zzacg zzb(zzabx zzabxVar) throws IOException {
        while (true) {
            int zzvf = zzabxVar.zzvf();
            if (zzvf == 0) {
                return this;
            }
            if (zzvf == 10) {
                this.zzny = zzabxVar.readString();
            } else if (zzvf == 18) {
                this.value = zzabxVar.readString();
            } else if (!super.zza(zzabxVar, zzvf)) {
                return this;
            }
        }
    }
}
