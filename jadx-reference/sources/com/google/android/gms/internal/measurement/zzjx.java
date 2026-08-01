package com.google.android.gms.internal.measurement;

import android.content.Context;
import com.google.android.gms.common.internal.Preconditions;

/* loaded from: classes.dex */
public final class zzjx {
    final Context zzqx;

    public zzjx(Context context) {
        Preconditions.checkNotNull(context);
        Context applicationContext = context.getApplicationContext();
        Preconditions.checkNotNull(applicationContext);
        this.zzqx = applicationContext;
    }
}
