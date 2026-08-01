package com.google.android.gms.internal.measurement;

import android.os.Bundle;
import kotlinx.coroutines.DebugKt;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzih implements Runnable {
    private final /* synthetic */ boolean zzapb;
    private final /* synthetic */ zzif zzapc;
    private final /* synthetic */ zzif zzapd;
    private final /* synthetic */ zzig zzape;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzih(zzig zzigVar, boolean z, zzif zzifVar, zzif zzifVar2) {
        this.zzape = zzigVar;
        this.zzapb = z;
        this.zzapc = zzifVar;
        this.zzapd = zzifVar2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (this.zzapb && this.zzape.zzaov != null) {
            zzig zzigVar = this.zzape;
            zzigVar.zza(zzigVar.zzaov);
        }
        zzif zzifVar = this.zzapc;
        if ((zzifVar != null && zzifVar.zzaot == this.zzapd.zzaot && zzkc.zzs(this.zzapc.zzaos, this.zzapd.zzaos) && zzkc.zzs(this.zzapc.zzul, this.zzapd.zzul)) ? false : true) {
            Bundle bundle = new Bundle();
            zzig.zza(this.zzapd, bundle, true);
            zzif zzifVar2 = this.zzapc;
            if (zzifVar2 != null) {
                if (zzifVar2.zzul != null) {
                    bundle.putString("_pn", this.zzapc.zzul);
                }
                bundle.putString("_pc", this.zzapc.zzaos);
                bundle.putLong("_pi", this.zzapc.zzaot);
            }
            this.zzape.zzfv().zza(DebugKt.DEBUG_PROPERTY_VALUE_AUTO, "_vs", bundle);
        }
        this.zzape.zzaov = this.zzapd;
        this.zzape.zzfy().zzb(this.zzapd);
    }
}
