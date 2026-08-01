package com.google.android.gms.internal.measurement;

import android.database.ContentObserver;
import android.os.Handler;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzws extends ContentObserver {
    private final /* synthetic */ zzwr zzbnn;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zzws(zzwr zzwrVar, Handler handler) {
        super(null);
        this.zzbnn = zzwrVar;
    }

    @Override // android.database.ContentObserver
    public final void onChange(boolean z) {
        this.zzbnn.zzsd();
        this.zzbnn.zzsf();
    }
}
