package com.google.android.gms.measurement;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import com.google.android.gms.internal.measurement.zzjd;
import com.google.android.gms.internal.measurement.zzjh;

/* loaded from: classes.dex */
public final class AppMeasurementJobService extends JobService implements zzjh {
    private zzjd<AppMeasurementJobService> zzade;

    private final zzjd<AppMeasurementJobService> zzfq() {
        if (this.zzade == null) {
            this.zzade = new zzjd<>(this);
        }
        return this.zzade;
    }

    @Override // com.google.android.gms.internal.measurement.zzjh
    public final boolean callServiceStopSelfResult(int i) {
        throw new UnsupportedOperationException();
    }

    @Override // android.app.Service
    public final void onCreate() {
        super.onCreate();
        zzfq().onCreate();
    }

    @Override // android.app.Service
    public final void onDestroy() {
        zzfq().onDestroy();
        super.onDestroy();
    }

    @Override // android.app.Service
    public final void onRebind(Intent intent) {
        zzfq().onRebind(intent);
    }

    @Override // android.app.job.JobService
    public final boolean onStartJob(JobParameters jobParameters) {
        return zzfq().onStartJob(jobParameters);
    }

    @Override // android.app.job.JobService
    public final boolean onStopJob(JobParameters jobParameters) {
        return false;
    }

    @Override // android.app.Service
    public final boolean onUnbind(Intent intent) {
        return zzfq().onUnbind(intent);
    }

    @Override // com.google.android.gms.internal.measurement.zzjh
    public final void zza(JobParameters jobParameters, boolean z) {
        jobFinished(jobParameters, false);
    }

    @Override // com.google.android.gms.internal.measurement.zzjh
    public final void zzb(Intent intent) {
    }
}
