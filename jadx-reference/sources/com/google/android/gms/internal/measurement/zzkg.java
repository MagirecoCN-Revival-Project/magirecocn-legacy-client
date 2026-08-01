package com.google.android.gms.internal.measurement;

import java.io.IOException;

/* loaded from: classes.dex */
public final class zzkg extends zzaca<zzkg> {
    private static volatile zzkg[] zzasa;
    public Integer zzasb = null;
    public String zzasc = null;
    public zzkh[] zzasd = zzkh.zzlp();
    private Boolean zzase = null;
    public zzki zzasf = null;

    public zzkg() {
        this.zzbxg = null;
        this.zzbxr = -1;
    }

    public static zzkg[] zzlo() {
        if (zzasa == null) {
            synchronized (zzace.zzbxq) {
                if (zzasa == null) {
                    zzasa = new zzkg[0];
                }
            }
        }
        return zzasa;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzkg)) {
            return false;
        }
        zzkg zzkgVar = (zzkg) obj;
        Integer num = this.zzasb;
        if (num == null) {
            if (zzkgVar.zzasb != null) {
                return false;
            }
        } else if (!num.equals(zzkgVar.zzasb)) {
            return false;
        }
        String str = this.zzasc;
        if (str == null) {
            if (zzkgVar.zzasc != null) {
                return false;
            }
        } else if (!str.equals(zzkgVar.zzasc)) {
            return false;
        }
        if (!zzace.equals(this.zzasd, zzkgVar.zzasd)) {
            return false;
        }
        Boolean bool = this.zzase;
        if (bool == null) {
            if (zzkgVar.zzase != null) {
                return false;
            }
        } else if (!bool.equals(zzkgVar.zzase)) {
            return false;
        }
        zzki zzkiVar = this.zzasf;
        if (zzkiVar == null) {
            if (zzkgVar.zzasf != null) {
                return false;
            }
        } else if (!zzkiVar.equals(zzkgVar.zzasf)) {
            return false;
        }
        return (this.zzbxg == null || this.zzbxg.isEmpty()) ? zzkgVar.zzbxg == null || zzkgVar.zzbxg.isEmpty() : this.zzbxg.equals(zzkgVar.zzbxg);
    }

    public final int hashCode() {
        int hashCode = (getClass().getName().hashCode() + 527) * 31;
        Integer num = this.zzasb;
        int i = 0;
        int hashCode2 = (hashCode + (num == null ? 0 : num.hashCode())) * 31;
        String str = this.zzasc;
        int hashCode3 = (((hashCode2 + (str == null ? 0 : str.hashCode())) * 31) + zzace.hashCode(this.zzasd)) * 31;
        Boolean bool = this.zzase;
        int hashCode4 = hashCode3 + (bool == null ? 0 : bool.hashCode());
        zzki zzkiVar = this.zzasf;
        int hashCode5 = ((hashCode4 * 31) + (zzkiVar == null ? 0 : zzkiVar.hashCode())) * 31;
        if (this.zzbxg != null && !this.zzbxg.isEmpty()) {
            i = this.zzbxg.hashCode();
        }
        return hashCode5 + i;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.internal.measurement.zzaca, com.google.android.gms.internal.measurement.zzacg
    public final int zza() {
        int zza = super.zza();
        Integer num = this.zzasb;
        if (num != null) {
            zza += zzaby.zzf(1, num.intValue());
        }
        String str = this.zzasc;
        if (str != null) {
            zza += zzaby.zzc(2, str);
        }
        zzkh[] zzkhVarArr = this.zzasd;
        if (zzkhVarArr != null && zzkhVarArr.length > 0) {
            int i = 0;
            while (true) {
                zzkh[] zzkhVarArr2 = this.zzasd;
                if (i >= zzkhVarArr2.length) {
                    break;
                }
                zzkh zzkhVar = zzkhVarArr2[i];
                if (zzkhVar != null) {
                    zza += zzaby.zzb(3, zzkhVar);
                }
                i++;
            }
        }
        Boolean bool = this.zzase;
        if (bool != null) {
            bool.booleanValue();
            zza += zzaby.zzaq(4) + 1;
        }
        zzki zzkiVar = this.zzasf;
        return zzkiVar != null ? zza + zzaby.zzb(5, zzkiVar) : zza;
    }

    @Override // com.google.android.gms.internal.measurement.zzaca, com.google.android.gms.internal.measurement.zzacg
    public final void zza(zzaby zzabyVar) throws IOException {
        Integer num = this.zzasb;
        if (num != null) {
            zzabyVar.zze(1, num.intValue());
        }
        String str = this.zzasc;
        if (str != null) {
            zzabyVar.zzb(2, str);
        }
        zzkh[] zzkhVarArr = this.zzasd;
        if (zzkhVarArr != null && zzkhVarArr.length > 0) {
            int i = 0;
            while (true) {
                zzkh[] zzkhVarArr2 = this.zzasd;
                if (i >= zzkhVarArr2.length) {
                    break;
                }
                zzkh zzkhVar = zzkhVarArr2[i];
                if (zzkhVar != null) {
                    zzabyVar.zza(3, zzkhVar);
                }
                i++;
            }
        }
        Boolean bool = this.zzase;
        if (bool != null) {
            zzabyVar.zza(4, bool.booleanValue());
        }
        zzki zzkiVar = this.zzasf;
        if (zzkiVar != null) {
            zzabyVar.zza(5, zzkiVar);
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
            if (zzvf == 8) {
                this.zzasb = Integer.valueOf(zzabxVar.zzvh());
            } else if (zzvf == 18) {
                this.zzasc = zzabxVar.readString();
            } else if (zzvf == 26) {
                int zzb = zzacj.zzb(zzabxVar, 26);
                zzkh[] zzkhVarArr = this.zzasd;
                int length = zzkhVarArr == null ? 0 : zzkhVarArr.length;
                int i = zzb + length;
                zzkh[] zzkhVarArr2 = new zzkh[i];
                if (length != 0) {
                    System.arraycopy(zzkhVarArr, 0, zzkhVarArr2, 0, length);
                }
                while (length < i - 1) {
                    zzkhVarArr2[length] = new zzkh();
                    zzabxVar.zza(zzkhVarArr2[length]);
                    zzabxVar.zzvf();
                    length++;
                }
                zzkhVarArr2[length] = new zzkh();
                zzabxVar.zza(zzkhVarArr2[length]);
                this.zzasd = zzkhVarArr2;
            } else if (zzvf == 32) {
                this.zzase = Boolean.valueOf(zzabxVar.zzvg());
            } else if (zzvf == 42) {
                if (this.zzasf == null) {
                    this.zzasf = new zzki();
                }
                zzabxVar.zza(this.zzasf);
            } else if (!super.zza(zzabxVar, zzvf)) {
                return this;
            }
        }
    }
}
