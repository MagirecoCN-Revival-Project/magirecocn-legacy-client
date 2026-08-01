package com.google.android.gms.internal.measurement;

import java.io.IOException;

/* loaded from: classes.dex */
public final class zzkr extends zzaca<zzkr> {
    public zzks[] zzatr = zzks.zzlw();

    public zzkr() {
        this.zzbxg = null;
        this.zzbxr = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzkr)) {
            return false;
        }
        zzkr zzkrVar = (zzkr) obj;
        if (zzace.equals(this.zzatr, zzkrVar.zzatr)) {
            return (this.zzbxg == null || this.zzbxg.isEmpty()) ? zzkrVar.zzbxg == null || zzkrVar.zzbxg.isEmpty() : this.zzbxg.equals(zzkrVar.zzbxg);
        }
        return false;
    }

    public final int hashCode() {
        return ((((getClass().getName().hashCode() + 527) * 31) + zzace.hashCode(this.zzatr)) * 31) + ((this.zzbxg == null || this.zzbxg.isEmpty()) ? 0 : this.zzbxg.hashCode());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.internal.measurement.zzaca, com.google.android.gms.internal.measurement.zzacg
    public final int zza() {
        int zza = super.zza();
        zzks[] zzksVarArr = this.zzatr;
        if (zzksVarArr != null && zzksVarArr.length > 0) {
            int i = 0;
            while (true) {
                zzks[] zzksVarArr2 = this.zzatr;
                if (i >= zzksVarArr2.length) {
                    break;
                }
                zzks zzksVar = zzksVarArr2[i];
                if (zzksVar != null) {
                    zza += zzaby.zzb(1, zzksVar);
                }
                i++;
            }
        }
        return zza;
    }

    @Override // com.google.android.gms.internal.measurement.zzaca, com.google.android.gms.internal.measurement.zzacg
    public final void zza(zzaby zzabyVar) throws IOException {
        zzks[] zzksVarArr = this.zzatr;
        if (zzksVarArr != null && zzksVarArr.length > 0) {
            int i = 0;
            while (true) {
                zzks[] zzksVarArr2 = this.zzatr;
                if (i >= zzksVarArr2.length) {
                    break;
                }
                zzks zzksVar = zzksVarArr2[i];
                if (zzksVar != null) {
                    zzabyVar.zza(1, zzksVar);
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
            if (zzvf == 10) {
                int zzb = zzacj.zzb(zzabxVar, 10);
                zzks[] zzksVarArr = this.zzatr;
                int length = zzksVarArr == null ? 0 : zzksVarArr.length;
                int i = zzb + length;
                zzks[] zzksVarArr2 = new zzks[i];
                if (length != 0) {
                    System.arraycopy(zzksVarArr, 0, zzksVarArr2, 0, length);
                }
                while (length < i - 1) {
                    zzksVarArr2[length] = new zzks();
                    zzabxVar.zza(zzksVarArr2[length]);
                    zzabxVar.zzvf();
                    length++;
                }
                zzksVarArr2[length] = new zzks();
                zzabxVar.zza(zzksVarArr2[length]);
                this.zzatr = zzksVarArr2;
            } else if (!super.zza(zzabxVar, zzvf)) {
                return this;
            }
        }
    }
}
