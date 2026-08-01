package com.google.android.gms.internal.measurement;

import com.google.android.gms.measurement.AppMeasurement;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzhs implements Runnable {
    private final /* synthetic */ zzhl zzaog;
    private final /* synthetic */ AppMeasurement.ConditionalUserProperty zzaol;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzhs(zzhl zzhlVar, AppMeasurement.ConditionalUserProperty conditionalUserProperty) {
        this.zzaog = zzhlVar;
        this.zzaol = conditionalUserProperty;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzaog.zzb(this.zzaol);
    }
}
