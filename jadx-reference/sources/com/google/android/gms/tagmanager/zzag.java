package com.google.android.gms.tagmanager;

import com.google.android.gms.common.api.Status;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* loaded from: classes.dex */
public final class zzag implements zzdg<com.google.android.gms.internal.gtm.zzai> {
    final /* synthetic */ zzal zza;

    /* JADX DEBUG: Marked for inline */
    /* JADX DEBUG: Method not inlined, still used in: [com.google.android.gms.tagmanager.zzal.zzs(boolean):void] */
    /* JADX INFO: Access modifiers changed from: package-private */
    public /* synthetic */ zzag(zzal zzalVar, zzaf zzafVar) {
        this.zza = zzalVar;
    }

    @Override // com.google.android.gms.tagmanager.zzdg
    public final void zza(int i) {
        zzam zzamVar;
        zzaa zzaaVar;
        zzaa zzaaVar2;
        zzam zzamVar2;
        if (i == 4) {
            zzamVar2 = this.zza.zzi;
            zzamVar2.zzc();
        }
        synchronized (this.zza) {
            if (!this.zza.isReady()) {
                zzaaVar = this.zza.zzl;
                if (zzaaVar != null) {
                    zzal zzalVar = this.zza;
                    zzaaVar2 = zzalVar.zzl;
                    zzalVar.setResult(zzaaVar2);
                } else {
                    zzal zzalVar2 = this.zza;
                    zzalVar2.setResult(zzalVar2.createFailedResult(Status.RESULT_TIMEOUT));
                }
            }
        }
        zzamVar = this.zza.zzi;
        this.zza.zzr(zzamVar.zzb());
    }
}
