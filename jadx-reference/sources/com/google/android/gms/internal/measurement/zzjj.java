package com.google.android.gms.internal.measurement;

import android.os.Bundle;
import kotlinx.coroutines.DebugKt;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzjj extends zzeo {
    private final /* synthetic */ zzji zzaqg;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zzjj(zzji zzjiVar, zzhj zzhjVar) {
        super(zzhjVar);
        this.zzaqg = zzjiVar;
    }

    @Override // com.google.android.gms.internal.measurement.zzeo
    public final void run() {
        zzji zzjiVar = this.zzaqg;
        zzjiVar.zzab();
        zzjiVar.zzgf().zziz().zzg("Session started, time", Long.valueOf(zzjiVar.zzbt().elapsedRealtime()));
        zzjiVar.zzgg().zzakt.set(false);
        zzjiVar.zzfv().zza(DebugKt.DEBUG_PROPERTY_VALUE_AUTO, "_s", new Bundle());
        zzjiVar.zzgg().zzaku.set(zzjiVar.zzbt().currentTimeMillis());
    }
}
