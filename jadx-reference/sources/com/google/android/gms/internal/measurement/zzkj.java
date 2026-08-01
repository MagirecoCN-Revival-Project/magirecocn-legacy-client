package com.google.android.gms.internal.measurement;

import java.io.IOException;

/* loaded from: classes.dex */
public final class zzkj extends zzaca<zzkj> {
    private static volatile zzkj[] zzasq;
    public Integer zzasb = null;
    public String zzasr = null;
    public zzkh zzass = null;

    public zzkj() {
        this.zzbxg = null;
        this.zzbxr = -1;
    }

    public static zzkj[] zzlq() {
        if (zzasq == null) {
            synchronized (zzace.zzbxq) {
                if (zzasq == null) {
                    zzasq = new zzkj[0];
                }
            }
        }
        return zzasq;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzkj)) {
            return false;
        }
        zzkj zzkjVar = (zzkj) obj;
        Integer num = this.zzasb;
        if (num == null) {
            if (zzkjVar.zzasb != null) {
                return false;
            }
        } else if (!num.equals(zzkjVar.zzasb)) {
            return false;
        }
        String str = this.zzasr;
        if (str == null) {
            if (zzkjVar.zzasr != null) {
                return false;
            }
        } else if (!str.equals(zzkjVar.zzasr)) {
            return false;
        }
        zzkh zzkhVar = this.zzass;
        if (zzkhVar == null) {
            if (zzkjVar.zzass != null) {
                return false;
            }
        } else if (!zzkhVar.equals(zzkjVar.zzass)) {
            return false;
        }
        return (this.zzbxg == null || this.zzbxg.isEmpty()) ? zzkjVar.zzbxg == null || zzkjVar.zzbxg.isEmpty() : this.zzbxg.equals(zzkjVar.zzbxg);
    }

    public final int hashCode() {
        int hashCode = (getClass().getName().hashCode() + 527) * 31;
        Integer num = this.zzasb;
        int i = 0;
        int hashCode2 = (hashCode + (num == null ? 0 : num.hashCode())) * 31;
        String str = this.zzasr;
        int hashCode3 = hashCode2 + (str == null ? 0 : str.hashCode());
        zzkh zzkhVar = this.zzass;
        int hashCode4 = ((hashCode3 * 31) + (zzkhVar == null ? 0 : zzkhVar.hashCode())) * 31;
        if (this.zzbxg != null && !this.zzbxg.isEmpty()) {
            i = this.zzbxg.hashCode();
        }
        return hashCode4 + i;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.internal.measurement.zzaca, com.google.android.gms.internal.measurement.zzacg
    public final int zza() {
        int zza = super.zza();
        Integer num = this.zzasb;
        if (num != null) {
            zza += zzaby.zzf(1, num.intValue());
        }
        String str = this.zzasr;
        if (str != null) {
            zza += zzaby.zzc(2, str);
        }
        zzkh zzkhVar = this.zzass;
        return zzkhVar != null ? zza + zzaby.zzb(3, zzkhVar) : zza;
    }

    @Override // com.google.android.gms.internal.measurement.zzaca, com.google.android.gms.internal.measurement.zzacg
    public final void zza(zzaby zzabyVar) throws IOException {
        Integer num = this.zzasb;
        if (num != null) {
            zzabyVar.zze(1, num.intValue());
        }
        String str = this.zzasr;
        if (str != null) {
            zzabyVar.zzb(2, str);
        }
        zzkh zzkhVar = this.zzass;
        if (zzkhVar != null) {
            zzabyVar.zza(3, zzkhVar);
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
                this.zzasr = zzabxVar.readString();
            } else if (zzvf == 26) {
                if (this.zzass == null) {
                    this.zzass = new zzkh();
                }
                zzabxVar.zza(this.zzass);
            } else if (!super.zza(zzabxVar, zzvf)) {
                return this;
            }
        }
    }
}
