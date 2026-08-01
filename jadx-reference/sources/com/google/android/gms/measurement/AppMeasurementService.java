package com.google.android.gms.measurement;

import android.app.Service;
import android.app.job.JobParameters;
import android.content.Intent;
import android.os.IBinder;
import com.google.android.gms.internal.measurement.zzjd;
import com.google.android.gms.internal.measurement.zzjh;

/* loaded from: classes.dex */
public final class AppMeasurementService extends Service implements zzjh {
    private zzjd<AppMeasurementService> zzade;

    private final zzjd<AppMeasurementService> zzfq() {
        if (this.zzade == null) {
            this.zzade = new zzjd<>(this);
        }
        return this.zzade;
    }

    @Override // com.google.android.gms.internal.measurement.zzjh
    public final boolean callServiceStopSelfResult(int i) {
        return stopSelfResult(i);
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return zzfq().onBind(intent);
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

    @Override // android.app.Service
    public final int onStartCommand(Intent intent, int i, int i2) {
        return zzfq().onStartCommand(intent, i, i2);
    }

    @Override // android.app.Service
    public final boolean onUnbind(Intent intent) {
        return zzfq().onUnbind(intent);
    }

    @Override // com.google.android.gms.internal.measurement.zzjh
    public final void zza(JobParameters jobParameters, boolean z) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.android.gms.internal.measurement.zzjh
    public final void zzb(Intent intent) {
        AppMeasurementReceiver.completeWakefulIntent(intent);
    }
}
