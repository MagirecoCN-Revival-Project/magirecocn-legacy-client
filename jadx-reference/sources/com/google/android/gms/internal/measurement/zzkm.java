package com.google.android.gms.internal.measurement;

import java.io.IOException;

/* loaded from: classes.dex */
public final class zzkm extends zzaca<zzkm> {
    public Long zzatb = null;
    public String zzadm = null;
    private Integer zzatc = null;
    public zzkn[] zzatd = zzkn.zzls();
    public zzkl[] zzate = zzkl.zzlr();
    public zzkf[] zzatf = zzkf.zzln();

    public zzkm() {
        this.zzbxg = null;
        this.zzbxr = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzkm)) {
            return false;
        }
        zzkm zzkmVar = (zzkm) obj;
        Long l = this.zzatb;
        if (l == null) {
            if (zzkmVar.zzatb != null) {
                return false;
            }
        } else if (!l.equals(zzkmVar.zzatb)) {
            return false;
        }
        String str = this.zzadm;
        if (str == null) {
            if (zzkmVar.zzadm != null) {
                return false;
            }
        } else if (!str.equals(zzkmVar.zzadm)) {
            return false;
        }
        Integer num = this.zzatc;
        if (num == null) {
            if (zzkmVar.zzatc != null) {
                return false;
            }
        } else if (!num.equals(zzkmVar.zzatc)) {
            return false;
        }
        if (zzace.equals(this.zzatd, zzkmVar.zzatd) && zzace.equals(this.zzate, zzkmVar.zzate) && zzace.equals(this.zzatf, zzkmVar.zzatf)) {
            return (this.zzbxg == null || this.zzbxg.isEmpty()) ? zzkmVar.zzbxg == null || zzkmVar.zzbxg.isEmpty() : this.zzbxg.equals(zzkmVar.zzbxg);
        }
        return false;
    }

    public final int hashCode() {
        int hashCode = (getClass().getName().hashCode() + 527) * 31;
        Long l = this.zzatb;
        int i = 0;
        int hashCode2 = (hashCode + (l == null ? 0 : l.hashCode())) * 31;
        String str = this.zzadm;
        int hashCode3 = (hashCode2 + (str == null ? 0 : str.hashCode())) * 31;
        Integer num = this.zzatc;
        int hashCode4 = (((((((hashCode3 + (num == null ? 0 : num.hashCode())) * 31) + zzace.hashCode(this.zzatd)) * 31) + zzace.hashCode(this.zzate)) * 31) + zzace.hashCode(this.zzatf)) * 31;
        if (this.zzbxg != null && !this.zzbxg.isEmpty()) {
            i = this.zzbxg.hashCode();
        }
        return hashCode4 + i;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.internal.measurement.zzaca, com.google.android.gms.internal.measurement.zzacg
    public final int zza() {
        int zza = super.zza();
        Long l = this.zzatb;
        if (l != null) {
            zza += zzaby.zzc(1, l.longValue());
        }
        String str = this.zzadm;
        if (str != null) {
            zza += zzaby.zzc(2, str);
        }
        Integer num = this.zzatc;
        if (num != null) {
            zza += zzaby.zzf(3, num.intValue());
        }
        zzkn[] zzknVarArr = this.zzatd;
        int i = 0;
        if (zzknVarArr != null && zzknVarArr.length > 0) {
            int i2 = 0;
            while (true) {
                zzkn[] zzknVarArr2 = this.zzatd;
                if (i2 >= zzknVarArr2.length) {
                    break;
                }
                zzkn zzknVar = zzknVarArr2[i2];
                if (zzknVar != null) {
                    zza += zzaby.zzb(4, zzknVar);
                }
                i2++;
            }
        }
        zzkl[] zzklVarArr = this.zzate;
        if (zzklVarArr != null && zzklVarArr.length > 0) {
            int i3 = 0;
            while (true) {
                zzkl[] zzklVarArr2 = this.zzate;
                if (i3 >= zzklVarArr2.length) {
                    break;
                }
                zzkl zzklVar = zzklVarArr2[i3];
                if (zzklVar != null) {
                    zza += zzaby.zzb(5, zzklVar);
                }
                i3++;
            }
        }
        zzkf[] zzkfVarArr = this.zzatf;
        if (zzkfVarArr != null && zzkfVarArr.length > 0) {
            while (true) {
                zzkf[] zzkfVarArr2 = this.zzatf;
                if (i >= zzkfVarArr2.length) {
                    break;
                }
                zzkf zzkfVar = zzkfVarArr2[i];
                if (zzkfVar != null) {
                    zza += zzaby.zzb(6, zzkfVar);
                }
                i++;
            }
        }
        return zza;
    }

    @Override // com.google.android.gms.internal.measurement.zzaca, com.google.android.gms.internal.measurement.zzacg
    public final void zza(zzaby zzabyVar) throws IOException {
        Long l = this.zzatb;
        if (l != null) {
            zzabyVar.zzb(1, l.longValue());
        }
        String str = this.zzadm;
        if (str != null) {
            zzabyVar.zzb(2, str);
        }
        Integer num = this.zzatc;
        if (num != null) {
            zzabyVar.zze(3, num.intValue());
        }
        zzkn[] zzknVarArr = this.zzatd;
        int i = 0;
        if (zzknVarArr != null && zzknVarArr.length > 0) {
            int i2 = 0;
            while (true) {
                zzkn[] zzknVarArr2 = this.zzatd;
                if (i2 >= zzknVarArr2.length) {
                    break;
                }
                zzkn zzknVar = zzknVarArr2[i2];
                if (zzknVar != null) {
                    zzabyVar.zza(4, zzknVar);
                }
                i2++;
            }
        }
        zzkl[] zzklVarArr = this.zzate;
        if (zzklVarArr != null && zzklVarArr.length > 0) {
            int i3 = 0;
            while (true) {
                zzkl[] zzklVarArr2 = this.zzate;
                if (i3 >= zzklVarArr2.length) {
                    break;
                }
                zzkl zzklVar = zzklVarArr2[i3];
                if (zzklVar != null) {
                    zzabyVar.zza(5, zzklVar);
                }
                i3++;
            }
        }
        zzkf[] zzkfVarArr = this.zzatf;
        if (zzkfVarArr != null && zzkfVarArr.length > 0) {
            while (true) {
                zzkf[] zzkfVarArr2 = this.zzatf;
                if (i >= zzkfVarArr2.length) {
                    break;
                }
                zzkf zzkfVar = zzkfVarArr2[i];
                if (zzkfVar != null) {
                    zzabyVar.zza(6, zzkfVar);
                }
                i++;
            }
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
                this.zzatb = Long.valueOf(zzabxVar.zzvi());
            } else if (zzvf == 18) {
                this.zzadm = zzabxVar.readString();
            } else if (zzvf == 24) {
                this.zzatc = Integer.valueOf(zzabxVar.zzvh());
            } else if (zzvf == 34) {
                int zzb = zzacj.zzb(zzabxVar, 34);
                zzkn[] zzknVarArr = this.zzatd;
                int length = zzknVarArr == null ? 0 : zzknVarArr.length;
                int i = zzb + length;
                zzkn[] zzknVarArr2 = new zzkn[i];
                if (length != 0) {
                    System.arraycopy(zzknVarArr, 0, zzknVarArr2, 0, length);
                }
                while (length < i - 1) {
                    zzknVarArr2[length] = new zzkn();
                    zzabxVar.zza(zzknVarArr2[length]);
                    zzabxVar.zzvf();
                    length++;
                }
                zzknVarArr2[length] = new zzkn();
                zzabxVar.zza(zzknVarArr2[length]);
                this.zzatd = zzknVarArr2;
            } else if (zzvf == 42) {
                int zzb2 = zzacj.zzb(zzabxVar, 42);
                zzkl[] zzklVarArr = this.zzate;
                int length2 = zzklVarArr == null ? 0 : zzklVarArr.length;
                int i2 = zzb2 + length2;
                zzkl[] zzklVarArr2 = new zzkl[i2];
                if (length2 != 0) {
                    System.arraycopy(zzklVarArr, 0, zzklVarArr2, 0, length2);
                }
                while (length2 < i2 - 1) {
                    zzklVarArr2[length2] = new zzkl();
                    zzabxVar.zza(zzklVarArr2[length2]);
                    zzabxVar.zzvf();
                    length2++;
                }
                zzklVarArr2[length2] = new zzkl();
                zzabxVar.zza(zzklVarArr2[length2]);
                this.zzate = zzklVarArr2;
            } else if (zzvf == 50) {
                int zzb3 = zzacj.zzb(zzabxVar, 50);
                zzkf[] zzkfVarArr = this.zzatf;
                int length3 = zzkfVarArr == null ? 0 : zzkfVarArr.length;
                int i3 = zzb3 + length3;
                zzkf[] zzkfVarArr2 = new zzkf[i3];
                if (length3 != 0) {
                    System.arraycopy(zzkfVarArr, 0, zzkfVarArr2, 0, length3);
                }
                while (length3 < i3 - 1) {
                    zzkfVarArr2[length3] = new zzkf();
                    zzabxVar.zza(zzkfVarArr2[length3]);
                    zzabxVar.zzvf();
                    length3++;
                }
                zzkfVarArr2[length3] = new zzkf();
                zzabxVar.zza(zzkfVarArr2[length3]);
                this.zzatf = zzkfVarArr2;
            } else if (!super.zza(zzabxVar, zzvf)) {
                return this;
            }
        }
    }
}
