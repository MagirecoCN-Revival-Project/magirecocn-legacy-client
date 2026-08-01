package com.google.android.gms.internal.measurement;

import com.google.android.gms.common.internal.Preconditions;
import java.lang.Thread;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzgj implements Thread.UncaughtExceptionHandler {
    private final String zzamh;
    private final /* synthetic */ zzgh zzami;

    public zzgj(zzgh zzghVar, String str) {
        this.zzami = zzghVar;
        Preconditions.checkNotNull(str);
        this.zzamh = str;
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public final synchronized void uncaughtException(Thread thread, Throwable th) {
        this.zzami.zzgf().zzis().zzg(this.zzamh, th);
    }
}
