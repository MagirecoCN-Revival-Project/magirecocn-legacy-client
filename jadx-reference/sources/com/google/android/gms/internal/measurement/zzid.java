package com.google.android.gms.internal.measurement;

import android.os.Bundle;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzid implements Runnable {
    private final /* synthetic */ String val$name;
    private final /* synthetic */ String zzanr;
    private final /* synthetic */ String zzant;
    private final /* synthetic */ zzhl zzaog;
    private final /* synthetic */ long zzaoi;
    private final /* synthetic */ Bundle zzaoo;
    private final /* synthetic */ boolean zzaop;
    private final /* synthetic */ boolean zzaoq;
    private final /* synthetic */ boolean zzaor;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzid(zzhl zzhlVar, String str, String str2, long j, Bundle bundle, boolean z, boolean z2, boolean z3, String str3) {
        this.zzaog = zzhlVar;
        this.zzanr = str;
        this.val$name = str2;
        this.zzaoi = j;
        this.zzaoo = bundle;
        this.zzaop = z;
        this.zzaoq = z2;
        this.zzaor = z3;
        this.zzant = str3;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzaog.zza(this.zzanr, this.val$name, this.zzaoi, this.zzaoo, this.zzaop, this.zzaoq, this.zzaor, this.zzant);
    }
}
