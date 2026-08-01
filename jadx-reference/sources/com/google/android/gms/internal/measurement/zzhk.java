package com.google.android.gms.internal.measurement;

import android.content.Context;
import com.google.android.gms.common.internal.Preconditions;

/* loaded from: classes.dex */
public final class zzhk {
    String zzamo;
    final Context zzqx;

    public zzhk(Context context, String str) {
        Preconditions.checkNotNull(context);
        Context applicationContext = context.getApplicationContext();
        Preconditions.checkNotNull(applicationContext);
        this.zzqx = applicationContext;
        this.zzamo = null;
    }
}
