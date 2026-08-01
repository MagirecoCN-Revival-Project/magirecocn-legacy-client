package com.google.android.gms.internal.measurement;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/* loaded from: classes.dex */
public interface zzgf {
    BroadcastReceiver.PendingResult doGoAsync();

    void doStartService(Context context, Intent intent);
}
