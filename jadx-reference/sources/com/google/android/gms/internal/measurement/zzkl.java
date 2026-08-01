package com.google.android.gms.internal.measurement;

import java.io.IOException;

/* loaded from: classes.dex */
public final class zzkl extends zzaca<zzkl> {
    private static volatile zzkl[] zzasx;
    public String name = null;
    public Boolean zzasy = null;
    public Boolean zzasz = null;
    public Integer zzata = null;

    public zzkl() {
        this.zzbxg = null;
        this.zzbxr = -1;
    }

    public static zzkl[] zzlr() {
        if (zzasx == null) {
            synchronized (zzace.zzbxq) {
                if (zzasx == null) {
                    zzasx = new zzkl[0];
                }
            }
        }
        return zzasx;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzkl)) {
            return false;
        }
        zzkl zzklVar = (zzkl) obj;
        String str = this.name;
        if (str == null) {
            if (zzklVar.name != null) {
                return false;
            }
        } else if (!str.equals(zzklVar.name)) {
            return false;
        }
        Boolean bool = this.zzasy;
        if (bool == null) {
            if (zzklVar.zzasy != null) {
                return false;
            }
        } else if (!bool.equals(zzklVar.zzasy)) {
            return false;
        }
        Boolean bool2 = this.zzasz;
        if (bool2 == null) {
            if (zzklVar.zzasz != null) {
                return false;
            }
        } else if (!bool2.equals(zzklVar.zzasz)) {
            return false;
        }
        Integer num = this.zzata;
        if (num == null) {
            if (zzklVar.zzata != null) {
                return false;
            }
        } else if (!num.equals(zzklVar.zzata)) {
            return false;
        }
        return (this.zzbxg == null || this.zzbxg.isEmpty()) ? zzklVar.zzbxg == null || zzklVar.zzbxg.isEmpty() : this.zzbxg.equals(zzklVar.zzbxg);
    }

    public final int hashCode() {
        int hashCode = (getClass().getName().hashCode() + 527) * 31;
        String str = this.name;
        int i = 0;
        int hashCode2 = (hashCode + (str == null ? 0 : str.hashCode())) * 31;
        Boolean bool = this.zzasy;
        int hashCode3 = (hashCode2 + (bool == null ? 0 : bool.hashCode())) * 31;
        Boolean bool2 = this.zzasz;
        int hashCode4 = (hashCode3 + (bool2 == null ? 0 : bool2.hashCode())) * 31;
        Integer num = this.zzata;
        int hashCode5 = (hashCode4 + (num == null ? 0 : num.hashCode())) * 31;
        if (this.zzbxg != null && !this.zzbxg.isEmpty()) {
            i = this.zzbxg.hashCode();
        }
        return hashCode5 + i;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.internal.measurement.zzaca, com.google.android.gms.internal.measurement.zzacg
    public final int zza() {
        int zza = super.zza();
        String str = this.name;
        if (str != null) {
            zza += zzaby.zzc(1, str);
        }
        Boolean bool = this.zzasy;
        if (bool != null) {
            bool.booleanValue();
            zza += zzaby.zzaq(2) + 1;
        }
        Boolean bool2 = this.zzasz;
        if (bool2 != null) {
            bool2.booleanValue();
            zza += zzaby.zzaq(3) + 1;
        }
        Integer num = this.zzata;
        return num != null ? zza + zzaby.zzf(4, num.intValue()) : zza;
    }

    @Override // com.google.android.gms.internal.measurement.zzaca, com.google.android.gms.internal.measurement.zzacg
    public final void zza(zzaby zzabyVar) throws IOException {
        String str = this.name;
        if (str != null) {
            zzabyVar.zzb(1, str);
        }
        Boolean bool = this.zzasy;
        if (bool != null) {
            zzabyVar.zza(2, bool.booleanValue());
        }
        Boolean bool2 = this.zzasz;
        if (bool2 != null) {
            zzabyVar.zza(3, bool2.booleanValue());
        }
        Integer num = this.zzata;
        if (num != null) {
            zzabyVar.zze(4, num.intValue());
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
                this.name = zzabxVar.readString();
            } else if (zzvf == 16) {
                this.zzasy = Boolean.valueOf(zzabxVar.zzvg());
            } else if (zzvf == 24) {
                this.zzasz = Boolean.valueOf(zzabxVar.zzvg());
            } else if (zzvf == 32) {
                this.zzata = Integer.valueOf(zzabxVar.zzvh());
            } else if (!super.zza(zzabxVar, zzvf)) {
                return this;
            }
        }
    }
}
