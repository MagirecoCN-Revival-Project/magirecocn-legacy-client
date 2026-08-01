package com.google.android.gms.internal.measurement;

import java.io.IOException;

/* loaded from: classes.dex */
public final class zzkh extends zzaca<zzkh> {
    private static volatile zzkh[] zzasg;
    public zzkk zzash = null;
    public zzki zzasi = null;
    public Boolean zzasj = null;
    public String zzask = null;

    public zzkh() {
        this.zzbxg = null;
        this.zzbxr = -1;
    }

    public static zzkh[] zzlp() {
        if (zzasg == null) {
            synchronized (zzace.zzbxq) {
                if (zzasg == null) {
                    zzasg = new zzkh[0];
                }
            }
        }
        return zzasg;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzkh)) {
            return false;
        }
        zzkh zzkhVar = (zzkh) obj;
        zzkk zzkkVar = this.zzash;
        if (zzkkVar == null) {
            if (zzkhVar.zzash != null) {
                return false;
            }
        } else if (!zzkkVar.equals(zzkhVar.zzash)) {
            return false;
        }
        zzki zzkiVar = this.zzasi;
        if (zzkiVar == null) {
            if (zzkhVar.zzasi != null) {
                return false;
            }
        } else if (!zzkiVar.equals(zzkhVar.zzasi)) {
            return false;
        }
        Boolean bool = this.zzasj;
        if (bool == null) {
            if (zzkhVar.zzasj != null) {
                return false;
            }
        } else if (!bool.equals(zzkhVar.zzasj)) {
            return false;
        }
        String str = this.zzask;
        if (str == null) {
            if (zzkhVar.zzask != null) {
                return false;
            }
        } else if (!str.equals(zzkhVar.zzask)) {
            return false;
        }
        return (this.zzbxg == null || this.zzbxg.isEmpty()) ? zzkhVar.zzbxg == null || zzkhVar.zzbxg.isEmpty() : this.zzbxg.equals(zzkhVar.zzbxg);
    }

    public final int hashCode() {
        int hashCode = getClass().getName().hashCode() + 527;
        zzkk zzkkVar = this.zzash;
        int i = 0;
        int hashCode2 = (hashCode * 31) + (zzkkVar == null ? 0 : zzkkVar.hashCode());
        zzki zzkiVar = this.zzasi;
        int hashCode3 = ((hashCode2 * 31) + (zzkiVar == null ? 0 : zzkiVar.hashCode())) * 31;
        Boolean bool = this.zzasj;
        int hashCode4 = (hashCode3 + (bool == null ? 0 : bool.hashCode())) * 31;
        String str = this.zzask;
        int hashCode5 = (hashCode4 + (str == null ? 0 : str.hashCode())) * 31;
        if (this.zzbxg != null && !this.zzbxg.isEmpty()) {
            i = this.zzbxg.hashCode();
        }
        return hashCode5 + i;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.internal.measurement.zzaca, com.google.android.gms.internal.measurement.zzacg
    public final int zza() {
        int zza = super.zza();
        zzkk zzkkVar = this.zzash;
        if (zzkkVar != null) {
            zza += zzaby.zzb(1, zzkkVar);
        }
        zzki zzkiVar = this.zzasi;
        if (zzkiVar != null) {
            zza += zzaby.zzb(2, zzkiVar);
        }
        Boolean bool = this.zzasj;
        if (bool != null) {
            bool.booleanValue();
            zza += zzaby.zzaq(3) + 1;
        }
        String str = this.zzask;
        return str != null ? zza + zzaby.zzc(4, str) : zza;
    }

    @Override // com.google.android.gms.internal.measurement.zzaca, com.google.android.gms.internal.measurement.zzacg
    public final void zza(zzaby zzabyVar) throws IOException {
        zzkk zzkkVar = this.zzash;
        if (zzkkVar != null) {
            zzabyVar.zza(1, zzkkVar);
        }
        zzki zzkiVar = this.zzasi;
        if (zzkiVar != null) {
            zzabyVar.zza(2, zzkiVar);
        }
        Boolean bool = this.zzasj;
        if (bool != null) {
            zzabyVar.zza(3, bool.booleanValue());
        }
        String str = this.zzask;
        if (str != null) {
            zzabyVar.zzb(4, str);
        }
        super.zza(zzabyVar);
    }

    @Override // com.google.android.gms.internal.measurement.zzacg
    public final /* synthetic */ zzacg zzb(zzabx zzabxVar) throws IOException {
        zzacg zzacgVar;
        while (true) {
            int zzvf = zzabxVar.zzvf();
            if (zzvf == 0) {
                return this;
            }
            if (zzvf == 10) {
                if (this.zzash == null) {
                    this.zzash = new zzkk();
                }
                zzacgVar = this.zzash;
            } else if (zzvf == 18) {
                if (this.zzasi == null) {
                    this.zzasi = new zzki();
                }
                zzacgVar = this.zzasi;
            } else if (zzvf == 24) {
                this.zzasj = Boolean.valueOf(zzabxVar.zzvg());
            } else if (zzvf == 34) {
                this.zzask = zzabxVar.readString();
            } else if (!super.zza(zzabxVar, zzvf)) {
                return this;
            }
            zzabxVar.zza(zzacgVar);
        }
    }
}
