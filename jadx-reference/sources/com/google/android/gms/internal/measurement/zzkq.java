package com.google.android.gms.internal.measurement;

import java.io.IOException;

/* loaded from: classes.dex */
public final class zzkq extends zzaca<zzkq> {
    private static volatile zzkq[] zzatp;
    public String name = null;
    public String zzajo = null;
    public Long zzatq = null;
    private Float zzarn = null;
    public Double zzaro = null;

    public zzkq() {
        this.zzbxg = null;
        this.zzbxr = -1;
    }

    public static zzkq[] zzlv() {
        if (zzatp == null) {
            synchronized (zzace.zzbxq) {
                if (zzatp == null) {
                    zzatp = new zzkq[0];
                }
            }
        }
        return zzatp;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzkq)) {
            return false;
        }
        zzkq zzkqVar = (zzkq) obj;
        String str = this.name;
        if (str == null) {
            if (zzkqVar.name != null) {
                return false;
            }
        } else if (!str.equals(zzkqVar.name)) {
            return false;
        }
        String str2 = this.zzajo;
        if (str2 == null) {
            if (zzkqVar.zzajo != null) {
                return false;
            }
        } else if (!str2.equals(zzkqVar.zzajo)) {
            return false;
        }
        Long l = this.zzatq;
        if (l == null) {
            if (zzkqVar.zzatq != null) {
                return false;
            }
        } else if (!l.equals(zzkqVar.zzatq)) {
            return false;
        }
        Float f = this.zzarn;
        if (f == null) {
            if (zzkqVar.zzarn != null) {
                return false;
            }
        } else if (!f.equals(zzkqVar.zzarn)) {
            return false;
        }
        Double d = this.zzaro;
        if (d == null) {
            if (zzkqVar.zzaro != null) {
                return false;
            }
        } else if (!d.equals(zzkqVar.zzaro)) {
            return false;
        }
        return (this.zzbxg == null || this.zzbxg.isEmpty()) ? zzkqVar.zzbxg == null || zzkqVar.zzbxg.isEmpty() : this.zzbxg.equals(zzkqVar.zzbxg);
    }

    public final int hashCode() {
        int hashCode = (getClass().getName().hashCode() + 527) * 31;
        String str = this.name;
        int i = 0;
        int hashCode2 = (hashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.zzajo;
        int hashCode3 = (hashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        Long l = this.zzatq;
        int hashCode4 = (hashCode3 + (l == null ? 0 : l.hashCode())) * 31;
        Float f = this.zzarn;
        int hashCode5 = (hashCode4 + (f == null ? 0 : f.hashCode())) * 31;
        Double d = this.zzaro;
        int hashCode6 = (hashCode5 + (d == null ? 0 : d.hashCode())) * 31;
        if (this.zzbxg != null && !this.zzbxg.isEmpty()) {
            i = this.zzbxg.hashCode();
        }
        return hashCode6 + i;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.internal.measurement.zzaca, com.google.android.gms.internal.measurement.zzacg
    public final int zza() {
        int zza = super.zza();
        String str = this.name;
        if (str != null) {
            zza += zzaby.zzc(1, str);
        }
        String str2 = this.zzajo;
        if (str2 != null) {
            zza += zzaby.zzc(2, str2);
        }
        Long l = this.zzatq;
        if (l != null) {
            zza += zzaby.zzc(3, l.longValue());
        }
        Float f = this.zzarn;
        if (f != null) {
            f.floatValue();
            zza += zzaby.zzaq(4) + 4;
        }
        Double d = this.zzaro;
        if (d == null) {
            return zza;
        }
        d.doubleValue();
        return zza + zzaby.zzaq(5) + 8;
    }

    @Override // com.google.android.gms.internal.measurement.zzaca, com.google.android.gms.internal.measurement.zzacg
    public final void zza(zzaby zzabyVar) throws IOException {
        String str = this.name;
        if (str != null) {
            zzabyVar.zzb(1, str);
        }
        String str2 = this.zzajo;
        if (str2 != null) {
            zzabyVar.zzb(2, str2);
        }
        Long l = this.zzatq;
        if (l != null) {
            zzabyVar.zzb(3, l.longValue());
        }
        Float f = this.zzarn;
        if (f != null) {
            zzabyVar.zza(4, f.floatValue());
        }
        Double d = this.zzaro;
        if (d != null) {
            zzabyVar.zza(5, d.doubleValue());
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
            } else if (zzvf == 18) {
                this.zzajo = zzabxVar.readString();
            } else if (zzvf == 24) {
                this.zzatq = Long.valueOf(zzabxVar.zzvi());
            } else if (zzvf == 37) {
                this.zzarn = Float.valueOf(Float.intBitsToFloat(zzabxVar.zzvj()));
            } else if (zzvf == 41) {
                this.zzaro = Double.valueOf(Double.longBitsToDouble(zzabxVar.zzvk()));
            } else if (!super.zza(zzabxVar, zzvf)) {
                return this;
            }
        }
    }
}
