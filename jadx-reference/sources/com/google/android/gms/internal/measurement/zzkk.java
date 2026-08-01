package com.google.android.gms.internal.measurement;

import java.io.IOException;

/* loaded from: classes.dex */
public final class zzkk extends zzaca<zzkk> {
    public Integer zzast = null;
    public String zzasu = null;
    public Boolean zzasv = null;
    public String[] zzasw = zzacj.zzbya;

    public zzkk() {
        this.zzbxg = null;
        this.zzbxr = -1;
    }

    /* JADX DEBUG: Method merged with bridge method: zzb(Lcom/google/android/gms/internal/measurement/zzabx;)Lcom/google/android/gms/internal/measurement/zzacg; */
    /* JADX INFO: Access modifiers changed from: private */
    @Override // com.google.android.gms.internal.measurement.zzacg
    /* renamed from: zze, reason: merged with bridge method [inline-methods] */
    public final zzkk zzb(zzabx zzabxVar) throws IOException {
        while (true) {
            int zzvf = zzabxVar.zzvf();
            if (zzvf == 0) {
                return this;
            }
            if (zzvf == 8) {
                int position = zzabxVar.getPosition();
                try {
                    int zzvh = zzabxVar.zzvh();
                    if (zzvh < 0 || zzvh > 6) {
                        StringBuilder sb = new StringBuilder(41);
                        sb.append(zzvh);
                        sb.append(" is not a valid enum MatchType");
                        throw new IllegalArgumentException(sb.toString());
                        break;
                    }
                    this.zzast = Integer.valueOf(zzvh);
                } catch (IllegalArgumentException unused) {
                    zzabxVar.zzam(position);
                    zza(zzabxVar, zzvf);
                }
            } else if (zzvf == 18) {
                this.zzasu = zzabxVar.readString();
            } else if (zzvf == 24) {
                this.zzasv = Boolean.valueOf(zzabxVar.zzvg());
            } else if (zzvf == 34) {
                int zzb = zzacj.zzb(zzabxVar, 34);
                String[] strArr = this.zzasw;
                int length = strArr == null ? 0 : strArr.length;
                int i = zzb + length;
                String[] strArr2 = new String[i];
                if (length != 0) {
                    System.arraycopy(strArr, 0, strArr2, 0, length);
                }
                while (length < i - 1) {
                    strArr2[length] = zzabxVar.readString();
                    zzabxVar.zzvf();
                    length++;
                }
                strArr2[length] = zzabxVar.readString();
                this.zzasw = strArr2;
            } else if (!super.zza(zzabxVar, zzvf)) {
                return this;
            }
        }
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzkk)) {
            return false;
        }
        zzkk zzkkVar = (zzkk) obj;
        Integer num = this.zzast;
        if (num == null) {
            if (zzkkVar.zzast != null) {
                return false;
            }
        } else if (!num.equals(zzkkVar.zzast)) {
            return false;
        }
        String str = this.zzasu;
        if (str == null) {
            if (zzkkVar.zzasu != null) {
                return false;
            }
        } else if (!str.equals(zzkkVar.zzasu)) {
            return false;
        }
        Boolean bool = this.zzasv;
        if (bool == null) {
            if (zzkkVar.zzasv != null) {
                return false;
            }
        } else if (!bool.equals(zzkkVar.zzasv)) {
            return false;
        }
        if (zzace.equals(this.zzasw, zzkkVar.zzasw)) {
            return (this.zzbxg == null || this.zzbxg.isEmpty()) ? zzkkVar.zzbxg == null || zzkkVar.zzbxg.isEmpty() : this.zzbxg.equals(zzkkVar.zzbxg);
        }
        return false;
    }

    public final int hashCode() {
        int hashCode = (getClass().getName().hashCode() + 527) * 31;
        Integer num = this.zzast;
        int i = 0;
        int intValue = (hashCode + (num == null ? 0 : num.intValue())) * 31;
        String str = this.zzasu;
        int hashCode2 = (intValue + (str == null ? 0 : str.hashCode())) * 31;
        Boolean bool = this.zzasv;
        int hashCode3 = (((hashCode2 + (bool == null ? 0 : bool.hashCode())) * 31) + zzace.hashCode(this.zzasw)) * 31;
        if (this.zzbxg != null && !this.zzbxg.isEmpty()) {
            i = this.zzbxg.hashCode();
        }
        return hashCode3 + i;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.internal.measurement.zzaca, com.google.android.gms.internal.measurement.zzacg
    public final int zza() {
        int zza = super.zza();
        Integer num = this.zzast;
        if (num != null) {
            zza += zzaby.zzf(1, num.intValue());
        }
        String str = this.zzasu;
        if (str != null) {
            zza += zzaby.zzc(2, str);
        }
        Boolean bool = this.zzasv;
        if (bool != null) {
            bool.booleanValue();
            zza += zzaby.zzaq(3) + 1;
        }
        String[] strArr = this.zzasw;
        if (strArr == null || strArr.length <= 0) {
            return zza;
        }
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            String[] strArr2 = this.zzasw;
            if (i >= strArr2.length) {
                return zza + i2 + (i3 * 1);
            }
            String str2 = strArr2[i];
            if (str2 != null) {
                i3++;
                i2 += zzaby.zzfk(str2);
            }
            i++;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzaca, com.google.android.gms.internal.measurement.zzacg
    public final void zza(zzaby zzabyVar) throws IOException {
        Integer num = this.zzast;
        if (num != null) {
            zzabyVar.zze(1, num.intValue());
        }
        String str = this.zzasu;
        if (str != null) {
            zzabyVar.zzb(2, str);
        }
        Boolean bool = this.zzasv;
        if (bool != null) {
            zzabyVar.zza(3, bool.booleanValue());
        }
        String[] strArr = this.zzasw;
        if (strArr != null && strArr.length > 0) {
            int i = 0;
            while (true) {
                String[] strArr2 = this.zzasw;
                if (i >= strArr2.length) {
                    break;
                }
                String str2 = strArr2[i];
                if (str2 != null) {
                    zzabyVar.zzb(4, str2);
                }
                i++;
            }
        }
        super.zza(zzabyVar);
    }
}
