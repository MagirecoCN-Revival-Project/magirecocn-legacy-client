package com.google.android.gms.analytics;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import com.google.android.gms.internal.gtm.zzfm;
import com.google.android.gms.internal.gtm.zzfn;

/* compiled from: com.google.android.gms:play-services-analytics@@17.0.1 */
/* loaded from: classes.dex */
public final class AnalyticsJobService extends JobService implements zzfm {
    private zzfn<AnalyticsJobService> zza;

    private final zzfn<AnalyticsJobService> zzb() {
        if (this.zza == null) {
            this.zza = new zzfn<>(this);
        }
        return this.zza;
    }

    @Override // com.google.android.gms.internal.gtm.zzfm
    public boolean callServiceStopSelfResult(int i) {
        return stopSelfResult(i);
    }

    @Override // android.app.Service
    public final void onCreate() {
        super.onCreate();
        zzb().zze();
    }

    @Override // android.app.Service
    public final void onDestroy() {
        zzb().zzf();
        super.onDestroy();
    }

    @Override // android.app.Service
    public final int onStartCommand(Intent intent, int i, int i2) {
        zzb().zza(intent, i, i2);
        return 2;
    }

    @Override // android.app.job.JobService
    public boolean onStartJob(JobParameters jobParameters) {
        zzb().zzi(jobParameters);
        return true;
    }

    @Override // android.app.job.JobService
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }

    @Override // com.google.android.gms.internal.gtm.zzfm
    public final void zza(JobParameters jobParameters, boolean z) {
        jobFinished(jobParameters, false);
    }
}
