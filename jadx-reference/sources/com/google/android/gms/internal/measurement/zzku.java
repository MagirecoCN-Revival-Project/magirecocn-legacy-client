package com.google.android.gms.internal.measurement;

import java.io.IOException;

/* loaded from: classes.dex */
public final class zzku extends zzaca<zzku> {
    private static volatile zzku[] zzauy;
    public Long zzauz = null;
    public String name = null;
    public String zzajo = null;
    public Long zzatq = null;
    private Float zzarn = null;
    public Double zzaro = null;

    public zzku() {
        this.zzbxg = null;
        this.zzbxr = -1;
    }

    public static zzku[] zzlx() {
        if (zzauy == null) {
            synchronized (zzace.zzbxq) {
                if (zzauy == null) {
                    zzauy = new zzku[0];
                }
            }
        }
        return zzauy;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzku)) {
            return false;
        }
        zzku zzkuVar = (zzku) obj;
        Long l = this.zzauz;
        if (l == null) {
            if (zzkuVar.zzauz != null) {
                return false;
            }
        } else if (!l.equals(zzkuVar.zzauz)) {
            return false;
        }
        String str = this.name;
        if (str == null) {
            if (zzkuVar.name != null) {
                return false;
            }
        } else if (!str.equals(zzkuVar.name)) {
            return false;
        }
        String str2 = this.zzajo;
        if (str2 == null) {
            if (zzkuVar.zzajo != null) {
                return false;
            }
        } else if (!str2.equals(zzkuVar.zzajo)) {
            return false;
        }
        Long l2 = this.zzatq;
        if (l2 == null) {
            if (zzkuVar.zzatq != null) {
                return false;
            }
        } else if (!l2.equals(zzkuVar.zzatq)) {
            return false;
        }
        Float f = this.zzarn;
        if (f == null) {
            if (zzkuVar.zzarn != null) {
                return false;
            }
        } else if (!f.equals(zzkuVar.zzarn)) {
            return false;
        }
        Double d = this.zzaro;
        if (d == null) {
            if (zzkuVar.zzaro != null) {
                return false;
            }
        } else if (!d.equals(zzkuVar.zzaro)) {
            return false;
        }
        return (this.zzbxg == null || this.zzbxg.isEmpty()) ? zzkuVar.zzbxg == null || zzkuVar.zzbxg.isEmpty() : this.zzbxg.equals(zzkuVar.zzbxg);
    }

    public final int hashCode() {
        int hashCode = (getClass().getName().hashCode() + 527) * 31;
        Long l = this.zzauz;
        int i = 0;
        int hashCode2 = (hashCode + (l == null ? 0 : l.hashCode())) * 31;
        String str = this.name;
        int hashCode3 = (hashCode2 + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.zzajo;
        int hashCode4 = (hashCode3 + (str2 == null ? 0 : str2.hashCode())) * 31;
        Long l2 = this.zzatq;
        int hashCode5 = (hashCode4 + (l2 == null ? 0 : l2.hashCode())) * 31;
        Float f = this.zzarn;
        int hashCode6 = (hashCode5 + (f == null ? 0 : f.hashCode())) * 31;
        Double d = this.zzaro;
        int hashCode7 = (hashCode6 + (d == null ? 0 : d.hashCode())) * 31;
        if (this.zzbxg != null && !this.zzbxg.isEmpty()) {
            i = this.zzbxg.hashCode();
        }
        return hashCode7 + i;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.internal.measurement.zzaca, com.google.android.gms.internal.measurement.zzacg
    public final int zza() {
        int zza = super.zza();
        Long l = this.zzauz;
        if (l != null) {
            zza += zzaby.zzc(1, l.longValue());
        }
        String str = this.name;
        if (str != null) {
            zza += zzaby.zzc(2, str);
        }
        String str2 = this.zzajo;
        if (str2 != null) {
            zza += zzaby.zzc(3, str2);
        }
        Long l2 = this.zzatq;
        if (l2 != null) {
            zza += zzaby.zzc(4, l2.longValue());
        }
        Float f = this.zzarn;
        if (f != null) {
            f.floatValue();
            zza += zzaby.zzaq(5) + 4;
        }
        Double d = this.zzaro;
        if (d == null) {
            return zza;
        }
        d.doubleValue();
        return zza + zzaby.zzaq(6) + 8;
    }

    @Override // com.google.android.gms.internal.measurement.zzaca, com.google.android.gms.internal.measurement.zzacg
    public final void zza(zzaby zzabyVar) throws IOException {
        Long l = this.zzauz;
        if (l != null) {
            zzabyVar.zzb(1, l.longValue());
        }
        String str = this.name;
        if (str != null) {
            zzabyVar.zzb(2, str);
        }
        String str2 = this.zzajo;
        if (str2 != null) {
            zzabyVar.zzb(3, str2);
        }
        Long l2 = this.zzatq;
        if (l2 != null) {
            zzabyVar.zzb(4, l2.longValue());
        }
        Float f = this.zzarn;
        if (f != null) {
            zzabyVar.zza(5, f.floatValue());
        }
        Double d = this.zzaro;
        if (d != null) {
            zzabyVar.zza(6, d.doubleValue());
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
                this.zzauz = Long.valueOf(zzabxVar.zzvi());
            } else if (zzvf == 18) {
                this.name = zzabxVar.readString();
            } else if (zzvf == 26) {
                this.zzajo = zzabxVar.readString();
            } else if (zzvf == 32) {
                this.zzatq = Long.valueOf(zzabxVar.zzvi());
            } else if (zzvf == 45) {
                this.zzarn = Float.valueOf(Float.intBitsToFloat(zzabxVar.zzvj()));
            } else if (zzvf == 49) {
                this.zzaro = Double.valueOf(Double.longBitsToDouble(zzabxVar.zzvk()));
            } else if (!super.zza(zzabxVar, zzvf)) {
                return this;
            }
        }
    }
}
