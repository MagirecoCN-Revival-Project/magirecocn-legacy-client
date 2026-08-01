package com.google.android.gms.internal.measurement;

import java.io.IOException;

/* loaded from: classes.dex */
public final class zzko extends zzaca<zzko> {
    private static volatile zzko[] zzath;
    public Integer zzarx = null;
    public zzkt zzati = null;
    public zzkt zzatj = null;
    public Boolean zzatk = null;

    public zzko() {
        this.zzbxg = null;
        this.zzbxr = -1;
    }

    public static zzko[] zzlt() {
        if (zzath == null) {
            synchronized (zzace.zzbxq) {
                if (zzath == null) {
                    zzath = new zzko[0];
                }
            }
        }
        return zzath;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzko)) {
            return false;
        }
        zzko zzkoVar = (zzko) obj;
        Integer num = this.zzarx;
        if (num == null) {
            if (zzkoVar.zzarx != null) {
                return false;
            }
        } else if (!num.equals(zzkoVar.zzarx)) {
            return false;
        }
        zzkt zzktVar = this.zzati;
        if (zzktVar == null) {
            if (zzkoVar.zzati != null) {
                return false;
            }
        } else if (!zzktVar.equals(zzkoVar.zzati)) {
            return false;
        }
        zzkt zzktVar2 = this.zzatj;
        if (zzktVar2 == null) {
            if (zzkoVar.zzatj != null) {
                return false;
            }
        } else if (!zzktVar2.equals(zzkoVar.zzatj)) {
            return false;
        }
        Boolean bool = this.zzatk;
        if (bool == null) {
            if (zzkoVar.zzatk != null) {
                return false;
            }
        } else if (!bool.equals(zzkoVar.zzatk)) {
            return false;
        }
        return (this.zzbxg == null || this.zzbxg.isEmpty()) ? zzkoVar.zzbxg == null || zzkoVar.zzbxg.isEmpty() : this.zzbxg.equals(zzkoVar.zzbxg);
    }

    public final int hashCode() {
        int hashCode = (getClass().getName().hashCode() + 527) * 31;
        Integer num = this.zzarx;
        int i = 0;
        int hashCode2 = hashCode + (num == null ? 0 : num.hashCode());
        zzkt zzktVar = this.zzati;
        int hashCode3 = (hashCode2 * 31) + (zzktVar == null ? 0 : zzktVar.hashCode());
        zzkt zzktVar2 = this.zzatj;
        int hashCode4 = ((hashCode3 * 31) + (zzktVar2 == null ? 0 : zzktVar2.hashCode())) * 31;
        Boolean bool = this.zzatk;
        int hashCode5 = (hashCode4 + (bool == null ? 0 : bool.hashCode())) * 31;
        if (this.zzbxg != null && !this.zzbxg.isEmpty()) {
            i = this.zzbxg.hashCode();
        }
        return hashCode5 + i;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.internal.measurement.zzaca, com.google.android.gms.internal.measurement.zzacg
    public final int zza() {
        int zza = super.zza();
        Integer num = this.zzarx;
        if (num != null) {
            zza += zzaby.zzf(1, num.intValue());
        }
        zzkt zzktVar = this.zzati;
        if (zzktVar != null) {
            zza += zzaby.zzb(2, zzktVar);
        }
        zzkt zzktVar2 = this.zzatj;
        if (zzktVar2 != null) {
            zza += zzaby.zzb(3, zzktVar2);
        }
        Boolean bool = this.zzatk;
        if (bool == null) {
            return zza;
        }
        bool.booleanValue();
        return zza + zzaby.zzaq(4) + 1;
    }

    @Override // com.google.android.gms.internal.measurement.zzaca, com.google.android.gms.internal.measurement.zzacg
    public final void zza(zzaby zzabyVar) throws IOException {
        Integer num = this.zzarx;
        if (num != null) {
            zzabyVar.zze(1, num.intValue());
        }
        zzkt zzktVar = this.zzati;
        if (zzktVar != null) {
            zzabyVar.zza(2, zzktVar);
        }
        zzkt zzktVar2 = this.zzatj;
        if (zzktVar2 != null) {
            zzabyVar.zza(3, zzktVar2);
        }
        Boolean bool = this.zzatk;
        if (bool != null) {
            zzabyVar.zza(4, bool.booleanValue());
        }
        super.zza(zzabyVar);
    }

    @Override // com.google.android.gms.internal.measurement.zzacg
    public final /* synthetic */ zzacg zzb(zzabx zzabxVar) throws IOException {
        zzkt zzktVar;
        while (true) {
            int zzvf = zzabxVar.zzvf();
            if (zzvf == 0) {
                return this;
            }
            if (zzvf != 8) {
                if (zzvf == 18) {
                    if (this.zzati == null) {
                        this.zzati = new zzkt();
                    }
                    zzktVar = this.zzati;
                } else if (zzvf == 26) {
                    if (this.zzatj == null) {
                        this.zzatj = new zzkt();
                    }
                    zzktVar = this.zzatj;
                } else if (zzvf == 32) {
                    this.zzatk = Boolean.valueOf(zzabxVar.zzvg());
                } else if (!super.zza(zzabxVar, zzvf)) {
                    return this;
                }
                zzabxVar.zza(zzktVar);
            } else {
                this.zzarx = Integer.valueOf(zzabxVar.zzvh());
            }
        }
    }
}
