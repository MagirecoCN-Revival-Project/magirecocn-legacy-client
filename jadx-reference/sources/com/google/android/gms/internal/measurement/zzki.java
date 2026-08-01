package com.google.android.gms.internal.measurement;

import java.io.IOException;

/* loaded from: classes.dex */
public final class zzki extends zzaca<zzki> {
    public Integer zzasl = null;
    public Boolean zzasm = null;
    public String zzasn = null;
    public String zzaso = null;
    public String zzasp = null;

    public zzki() {
        this.zzbxg = null;
        this.zzbxr = -1;
    }

    /* JADX DEBUG: Method merged with bridge method: zzb(Lcom/google/android/gms/internal/measurement/zzabx;)Lcom/google/android/gms/internal/measurement/zzacg; */
    /* JADX INFO: Access modifiers changed from: private */
    @Override // com.google.android.gms.internal.measurement.zzacg
    /* renamed from: zzd, reason: merged with bridge method [inline-methods] */
    public final zzki zzb(zzabx zzabxVar) throws IOException {
        while (true) {
            int zzvf = zzabxVar.zzvf();
            if (zzvf == 0) {
                return this;
            }
            if (zzvf == 8) {
                int position = zzabxVar.getPosition();
                try {
                    int zzvh = zzabxVar.zzvh();
                    if (zzvh < 0 || zzvh > 4) {
                        StringBuilder sb = new StringBuilder(46);
                        sb.append(zzvh);
                        sb.append(" is not a valid enum ComparisonType");
                        throw new IllegalArgumentException(sb.toString());
                        break;
                    }
                    this.zzasl = Integer.valueOf(zzvh);
                } catch (IllegalArgumentException unused) {
                    zzabxVar.zzam(position);
                    zza(zzabxVar, zzvf);
                }
            } else if (zzvf == 16) {
                this.zzasm = Boolean.valueOf(zzabxVar.zzvg());
            } else if (zzvf == 26) {
                this.zzasn = zzabxVar.readString();
            } else if (zzvf == 34) {
                this.zzaso = zzabxVar.readString();
            } else if (zzvf == 42) {
                this.zzasp = zzabxVar.readString();
            } else if (!super.zza(zzabxVar, zzvf)) {
                return this;
            }
        }
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzki)) {
            return false;
        }
        zzki zzkiVar = (zzki) obj;
        Integer num = this.zzasl;
        if (num == null) {
            if (zzkiVar.zzasl != null) {
                return false;
            }
        } else if (!num.equals(zzkiVar.zzasl)) {
            return false;
        }
        Boolean bool = this.zzasm;
        if (bool == null) {
            if (zzkiVar.zzasm != null) {
                return false;
            }
        } else if (!bool.equals(zzkiVar.zzasm)) {
            return false;
        }
        String str = this.zzasn;
        if (str == null) {
            if (zzkiVar.zzasn != null) {
                return false;
            }
        } else if (!str.equals(zzkiVar.zzasn)) {
            return false;
        }
        String str2 = this.zzaso;
        if (str2 == null) {
            if (zzkiVar.zzaso != null) {
                return false;
            }
        } else if (!str2.equals(zzkiVar.zzaso)) {
            return false;
        }
        String str3 = this.zzasp;
        if (str3 == null) {
            if (zzkiVar.zzasp != null) {
                return false;
            }
        } else if (!str3.equals(zzkiVar.zzasp)) {
            return false;
        }
        return (this.zzbxg == null || this.zzbxg.isEmpty()) ? zzkiVar.zzbxg == null || zzkiVar.zzbxg.isEmpty() : this.zzbxg.equals(zzkiVar.zzbxg);
    }

    public final int hashCode() {
        int hashCode = (getClass().getName().hashCode() + 527) * 31;
        Integer num = this.zzasl;
        int i = 0;
        int intValue = (hashCode + (num == null ? 0 : num.intValue())) * 31;
        Boolean bool = this.zzasm;
        int hashCode2 = (intValue + (bool == null ? 0 : bool.hashCode())) * 31;
        String str = this.zzasn;
        int hashCode3 = (hashCode2 + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.zzaso;
        int hashCode4 = (hashCode3 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.zzasp;
        int hashCode5 = (hashCode4 + (str3 == null ? 0 : str3.hashCode())) * 31;
        if (this.zzbxg != null && !this.zzbxg.isEmpty()) {
            i = this.zzbxg.hashCode();
        }
        return hashCode5 + i;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.internal.measurement.zzaca, com.google.android.gms.internal.measurement.zzacg
    public final int zza() {
        int zza = super.zza();
        Integer num = this.zzasl;
        if (num != null) {
            zza += zzaby.zzf(1, num.intValue());
        }
        Boolean bool = this.zzasm;
        if (bool != null) {
            bool.booleanValue();
            zza += zzaby.zzaq(2) + 1;
        }
        String str = this.zzasn;
        if (str != null) {
            zza += zzaby.zzc(3, str);
        }
        String str2 = this.zzaso;
        if (str2 != null) {
            zza += zzaby.zzc(4, str2);
        }
        String str3 = this.zzasp;
        return str3 != null ? zza + zzaby.zzc(5, str3) : zza;
    }

    @Override // com.google.android.gms.internal.measurement.zzaca, com.google.android.gms.internal.measurement.zzacg
    public final void zza(zzaby zzabyVar) throws IOException {
        Integer num = this.zzasl;
        if (num != null) {
            zzabyVar.zze(1, num.intValue());
        }
        Boolean bool = this.zzasm;
        if (bool != null) {
            zzabyVar.zza(2, bool.booleanValue());
        }
        String str = this.zzasn;
        if (str != null) {
            zzabyVar.zzb(3, str);
        }
        String str2 = this.zzaso;
        if (str2 != null) {
            zzabyVar.zzb(4, str2);
        }
        String str3 = this.zzasp;
        if (str3 != null) {
            zzabyVar.zzb(5, str3);
        }
        super.zza(zzabyVar);
    }
}
