package com.google.android.gms.internal.measurement;

import java.io.IOException;

/* loaded from: classes.dex */
public final class zzkp extends zzaca<zzkp> {
    private static volatile zzkp[] zzatl;
    public zzkq[] zzatm = zzkq.zzlv();
    public String name = null;
    public Long zzatn = null;
    public Long zzato = null;
    public Integer count = null;

    public zzkp() {
        this.zzbxg = null;
        this.zzbxr = -1;
    }

    public static zzkp[] zzlu() {
        if (zzatl == null) {
            synchronized (zzace.zzbxq) {
                if (zzatl == null) {
                    zzatl = new zzkp[0];
                }
            }
        }
        return zzatl;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzkp)) {
            return false;
        }
        zzkp zzkpVar = (zzkp) obj;
        if (!zzace.equals(this.zzatm, zzkpVar.zzatm)) {
            return false;
        }
        String str = this.name;
        if (str == null) {
            if (zzkpVar.name != null) {
                return false;
            }
        } else if (!str.equals(zzkpVar.name)) {
            return false;
        }
        Long l = this.zzatn;
        if (l == null) {
            if (zzkpVar.zzatn != null) {
                return false;
            }
        } else if (!l.equals(zzkpVar.zzatn)) {
            return false;
        }
        Long l2 = this.zzato;
        if (l2 == null) {
            if (zzkpVar.zzato != null) {
                return false;
            }
        } else if (!l2.equals(zzkpVar.zzato)) {
            return false;
        }
        Integer num = this.count;
        if (num == null) {
            if (zzkpVar.count != null) {
                return false;
            }
        } else if (!num.equals(zzkpVar.count)) {
            return false;
        }
        return (this.zzbxg == null || this.zzbxg.isEmpty()) ? zzkpVar.zzbxg == null || zzkpVar.zzbxg.isEmpty() : this.zzbxg.equals(zzkpVar.zzbxg);
    }

    public final int hashCode() {
        int hashCode = (((getClass().getName().hashCode() + 527) * 31) + zzace.hashCode(this.zzatm)) * 31;
        String str = this.name;
        int i = 0;
        int hashCode2 = (hashCode + (str == null ? 0 : str.hashCode())) * 31;
        Long l = this.zzatn;
        int hashCode3 = (hashCode2 + (l == null ? 0 : l.hashCode())) * 31;
        Long l2 = this.zzato;
        int hashCode4 = (hashCode3 + (l2 == null ? 0 : l2.hashCode())) * 31;
        Integer num = this.count;
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
        zzkq[] zzkqVarArr = this.zzatm;
        if (zzkqVarArr != null && zzkqVarArr.length > 0) {
            int i = 0;
            while (true) {
                zzkq[] zzkqVarArr2 = this.zzatm;
                if (i >= zzkqVarArr2.length) {
                    break;
                }
                zzkq zzkqVar = zzkqVarArr2[i];
                if (zzkqVar != null) {
                    zza += zzaby.zzb(1, zzkqVar);
                }
                i++;
            }
        }
        String str = this.name;
        if (str != null) {
            zza += zzaby.zzc(2, str);
        }
        Long l = this.zzatn;
        if (l != null) {
            zza += zzaby.zzc(3, l.longValue());
        }
        Long l2 = this.zzato;
        if (l2 != null) {
            zza += zzaby.zzc(4, l2.longValue());
        }
        Integer num = this.count;
        return num != null ? zza + zzaby.zzf(5, num.intValue()) : zza;
    }

    @Override // com.google.android.gms.internal.measurement.zzaca, com.google.android.gms.internal.measurement.zzacg
    public final void zza(zzaby zzabyVar) throws IOException {
        zzkq[] zzkqVarArr = this.zzatm;
        if (zzkqVarArr != null && zzkqVarArr.length > 0) {
            int i = 0;
            while (true) {
                zzkq[] zzkqVarArr2 = this.zzatm;
                if (i >= zzkqVarArr2.length) {
                    break;
                }
                zzkq zzkqVar = zzkqVarArr2[i];
                if (zzkqVar != null) {
                    zzabyVar.zza(1, zzkqVar);
                }
                i++;
            }
        }
        String str = this.name;
        if (str != null) {
            zzabyVar.zzb(2, str);
        }
        Long l = this.zzatn;
        if (l != null) {
            zzabyVar.zzb(3, l.longValue());
        }
        Long l2 = this.zzato;
        if (l2 != null) {
            zzabyVar.zzb(4, l2.longValue());
        }
        Integer num = this.count;
        if (num != null) {
            zzabyVar.zze(5, num.intValue());
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
                int zzb = zzacj.zzb(zzabxVar, 10);
                zzkq[] zzkqVarArr = this.zzatm;
                int length = zzkqVarArr == null ? 0 : zzkqVarArr.length;
                int i = zzb + length;
                zzkq[] zzkqVarArr2 = new zzkq[i];
                if (length != 0) {
                    System.arraycopy(zzkqVarArr, 0, zzkqVarArr2, 0, length);
                }
                while (length < i - 1) {
                    zzkqVarArr2[length] = new zzkq();
                    zzabxVar.zza(zzkqVarArr2[length]);
                    zzabxVar.zzvf();
                    length++;
                }
                zzkqVarArr2[length] = new zzkq();
                zzabxVar.zza(zzkqVarArr2[length]);
                this.zzatm = zzkqVarArr2;
            } else if (zzvf == 18) {
                this.name = zzabxVar.readString();
            } else if (zzvf == 24) {
                this.zzatn = Long.valueOf(zzabxVar.zzvi());
            } else if (zzvf == 32) {
                this.zzato = Long.valueOf(zzabxVar.zzvi());
            } else if (zzvf == 40) {
                this.count = Integer.valueOf(zzabxVar.zzvh());
            } else if (!super.zza(zzabxVar, zzvf)) {
                return this;
            }
        }
    }
}
