package com.google.android.gms.internal.measurement;

import android.content.ComponentName;

/* loaded from: classes.dex */
final class zziz implements Runnable {
    private final /* synthetic */ ComponentName val$name;
    private final /* synthetic */ zzix zzapw;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zziz(zzix zzixVar, ComponentName componentName) {
        this.zzapw = zzixVar;
        this.val$name = componentName;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzapw.zzapn.onServiceDisconnected(this.val$name);
    }
}
