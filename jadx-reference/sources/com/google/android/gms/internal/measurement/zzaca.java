package com.google.android.gms.internal.measurement;

import com.google.android.gms.internal.measurement.zzaca;
import java.io.IOException;

/* loaded from: classes.dex */
public abstract class zzaca<M extends zzaca<M>> extends zzacg {
    protected zzacc zzbxg;

    @Override // com.google.android.gms.internal.measurement.zzacg
    public /* synthetic */ Object clone() throws CloneNotSupportedException {
        zzaca zzacaVar = (zzaca) super.clone();
        zzace.zza(this, zzacaVar);
        return zzacaVar;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.internal.measurement.zzacg
    public int zza() {
        if (this.zzbxg == null) {
            return 0;
        }
        int i = 0;
        for (int i2 = 0; i2 < this.zzbxg.size(); i2++) {
            i += this.zzbxg.zzau(i2).zza();
        }
        return i;
    }

    public final <T> T zza(zzacb<M, T> zzacbVar) {
        zzacd zzat;
        zzacc zzaccVar = this.zzbxg;
        if (zzaccVar == null || (zzat = zzaccVar.zzat(zzacbVar.tag >>> 3)) == null) {
            return null;
        }
        return (T) zzat.zzb(zzacbVar);
    }

    @Override // com.google.android.gms.internal.measurement.zzacg
    public void zza(zzaby zzabyVar) throws IOException {
        if (this.zzbxg == null) {
            return;
        }
        for (int i = 0; i < this.zzbxg.size(); i++) {
            this.zzbxg.zzau(i).zza(zzabyVar);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final boolean zza(zzabx zzabxVar, int i) throws IOException {
        int position = zzabxVar.getPosition();
        if (!zzabxVar.zzak(i)) {
            return false;
        }
        int i2 = i >>> 3;
        zzaci zzaciVar = new zzaci(i, zzabxVar.zzc(position, zzabxVar.getPosition() - position));
        zzacd zzacdVar = null;
        zzacc zzaccVar = this.zzbxg;
        if (zzaccVar == null) {
            this.zzbxg = new zzacc();
        } else {
            zzacdVar = zzaccVar.zzat(i2);
        }
        if (zzacdVar == null) {
            zzacdVar = new zzacd();
            this.zzbxg.zza(i2, zzacdVar);
        }
        zzacdVar.zza(zzaciVar);
        return true;
    }

    @Override // com.google.android.gms.internal.measurement.zzacg
    /* renamed from: zzvo */
    public final /* synthetic */ zzacg clone() throws CloneNotSupportedException {
        return (zzaca) clone();
    }
}
