package com.google.android.gms.internal.measurement;

import java.io.IOException;

/* loaded from: classes.dex */
public final class zzkf extends zzaca<zzkf> {
    private static volatile zzkf[] zzarw;
    public Integer zzarx = null;
    public zzkj[] zzary = zzkj.zzlq();
    public zzkg[] zzarz = zzkg.zzlo();

    public zzkf() {
        this.zzbxg = null;
        this.zzbxr = -1;
    }

    public static zzkf[] zzln() {
        if (zzarw == null) {
            synchronized (zzace.zzbxq) {
                if (zzarw == null) {
                    zzarw = new zzkf[0];
                }
            }
        }
        return zzarw;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzkf)) {
            return false;
        }
        zzkf zzkfVar = (zzkf) obj;
        Integer num = this.zzarx;
        if (num == null) {
            if (zzkfVar.zzarx != null) {
                return false;
            }
        } else if (!num.equals(zzkfVar.zzarx)) {
            return false;
        }
        if (zzace.equals(this.zzary, zzkfVar.zzary) && zzace.equals(this.zzarz, zzkfVar.zzarz)) {
            return (this.zzbxg == null || this.zzbxg.isEmpty()) ? zzkfVar.zzbxg == null || zzkfVar.zzbxg.isEmpty() : this.zzbxg.equals(zzkfVar.zzbxg);
        }
        return false;
    }

    public final int hashCode() {
        int hashCode = (getClass().getName().hashCode() + 527) * 31;
        Integer num = this.zzarx;
        int i = 0;
        int hashCode2 = (((((hashCode + (num == null ? 0 : num.hashCode())) * 31) + zzace.hashCode(this.zzary)) * 31) + zzace.hashCode(this.zzarz)) * 31;
        if (this.zzbxg != null && !this.zzbxg.isEmpty()) {
            i = this.zzbxg.hashCode();
        }
        return hashCode2 + i;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.internal.measurement.zzaca, com.google.android.gms.internal.measurement.zzacg
    public final int zza() {
        int zza = super.zza();
        Integer num = this.zzarx;
        if (num != null) {
            zza += zzaby.zzf(1, num.intValue());
        }
        zzkj[] zzkjVarArr = this.zzary;
        int i = 0;
        if (zzkjVarArr != null && zzkjVarArr.length > 0) {
            int i2 = 0;
            while (true) {
                zzkj[] zzkjVarArr2 = this.zzary;
                if (i2 >= zzkjVarArr2.length) {
                    break;
                }
                zzkj zzkjVar = zzkjVarArr2[i2];
                if (zzkjVar != null) {
                    zza += zzaby.zzb(2, zzkjVar);
                }
                i2++;
            }
        }
        zzkg[] zzkgVarArr = this.zzarz;
        if (zzkgVarArr != null && zzkgVarArr.length > 0) {
            while (true) {
                zzkg[] zzkgVarArr2 = this.zzarz;
                if (i >= zzkgVarArr2.length) {
                    break;
                }
                zzkg zzkgVar = zzkgVarArr2[i];
                if (zzkgVar != null) {
                    zza += zzaby.zzb(3, zzkgVar);
                }
                i++;
            }
        }
        return zza;
    }

    @Override // com.google.android.gms.internal.measurement.zzaca, com.google.android.gms.internal.measurement.zzacg
    public final void zza(zzaby zzabyVar) throws IOException {
        Integer num = this.zzarx;
        if (num != null) {
            zzabyVar.zze(1, num.intValue());
        }
        zzkj[] zzkjVarArr = this.zzary;
        int i = 0;
        if (zzkjVarArr != null && zzkjVarArr.length > 0) {
            int i2 = 0;
            while (true) {
                zzkj[] zzkjVarArr2 = this.zzary;
                if (i2 >= zzkjVarArr2.length) {
                    break;
                }
                zzkj zzkjVar = zzkjVarArr2[i2];
                if (zzkjVar != null) {
                    zzabyVar.zza(2, zzkjVar);
                }
                i2++;
            }
        }
        zzkg[] zzkgVarArr = this.zzarz;
        if (zzkgVarArr != null && zzkgVarArr.length > 0) {
            while (true) {
                zzkg[] zzkgVarArr2 = this.zzarz;
                if (i >= zzkgVarArr2.length) {
                    break;
                }
                zzkg zzkgVar = zzkgVarArr2[i];
                if (zzkgVar != null) {
                    zzabyVar.zza(3, zzkgVar);
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
                this.zzarx = Integer.valueOf(zzabxVar.zzvh());
            } else if (zzvf == 18) {
                int zzb = zzacj.zzb(zzabxVar, 18);
                zzkj[] zzkjVarArr = this.zzary;
                int length = zzkjVarArr == null ? 0 : zzkjVarArr.length;
                int i = zzb + length;
                zzkj[] zzkjVarArr2 = new zzkj[i];
                if (length != 0) {
                    System.arraycopy(zzkjVarArr, 0, zzkjVarArr2, 0, length);
                }
                while (length < i - 1) {
                    zzkjVarArr2[length] = new zzkj();
                    zzabxVar.zza(zzkjVarArr2[length]);
                    zzabxVar.zzvf();
                    length++;
                }
                zzkjVarArr2[length] = new zzkj();
                zzabxVar.zza(zzkjVarArr2[length]);
                this.zzary = zzkjVarArr2;
            } else if (zzvf == 26) {
                int zzb2 = zzacj.zzb(zzabxVar, 26);
                zzkg[] zzkgVarArr = this.zzarz;
                int length2 = zzkgVarArr == null ? 0 : zzkgVarArr.length;
                int i2 = zzb2 + length2;
                zzkg[] zzkgVarArr2 = new zzkg[i2];
                if (length2 != 0) {
                    System.arraycopy(zzkgVarArr, 0, zzkgVarArr2, 0, length2);
                }
                while (length2 < i2 - 1) {
                    zzkgVarArr2[length2] = new zzkg();
                    zzabxVar.zza(zzkgVarArr2[length2]);
                    zzabxVar.zzvf();
                    length2++;
                }
                zzkgVarArr2[length2] = new zzkg();
                zzabxVar.zza(zzkgVarArr2[length2]);
                this.zzarz = zzkgVarArr2;
            } else if (!super.zza(zzabxVar, zzvf)) {
                return this;
            }
        }
    }
}
