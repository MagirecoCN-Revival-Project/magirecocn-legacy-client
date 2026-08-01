package com.google.android.gms.internal.measurement;

import android.content.Context;
import android.os.Looper;

/* loaded from: classes.dex */
public final class zzec {
    private final boolean zzaep = false;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzec(Context context) {
    }

    public static boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }
}
