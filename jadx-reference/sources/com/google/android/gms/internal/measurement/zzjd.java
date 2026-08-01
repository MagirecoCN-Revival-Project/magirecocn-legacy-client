package com.google.android.gms.internal.measurement;

import android.app.job.JobParameters;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.measurement.zzjh;

/* loaded from: classes.dex */
public final class zzjd<T extends Context & zzjh> {
    private final T zzabm;

    public zzjd(T t) {
        Preconditions.checkNotNull(t);
        this.zzabm = t;
    }

    private final void zzb(Runnable runnable) {
        zzjs zzg = zzjs.zzg(this.zzabm);
        zzg.zzge().zzc(new zzjg(this, zzg, runnable));
    }

    private final zzfh zzgf() {
        return zzgm.zza(this.zzabm, null, null).zzgf();
    }

    public final IBinder onBind(Intent intent) {
        if (intent == null) {
            zzgf().zzis().log("onBind called with null intent");
            return null;
        }
        String action = intent.getAction();
        if ("com.google.android.gms.measurement.START".equals(action)) {
            return new zzgo(zzjs.zzg(this.zzabm));
        }
        zzgf().zziv().zzg("onBind received unknown action", action);
        return null;
    }

    public final void onCreate() {
        zzgm zza = zzgm.zza(this.zzabm, null, null);
        zzfh zzgf = zza.zzgf();
        zza.zzgi();
        zzgf.zziz().log("Local AppMeasurementService is starting up");
    }

    public final void onDestroy() {
        zzgm zza = zzgm.zza(this.zzabm, null, null);
        zzfh zzgf = zza.zzgf();
        zza.zzgi();
        zzgf.zziz().log("Local AppMeasurementService is shutting down");
    }

    public final void onRebind(Intent intent) {
        if (intent == null) {
            zzgf().zzis().log("onRebind called with null intent");
        } else {
            zzgf().zziz().zzg("onRebind called. action", intent.getAction());
        }
    }

    public final int onStartCommand(final Intent intent, int i, final int i2) {
        zzgm zza = zzgm.zza(this.zzabm, null, null);
        final zzfh zzgf = zza.zzgf();
        if (intent == null) {
            zzgf.zziv().log("AppMeasurementService started with null intent");
            return 2;
        }
        String action = intent.getAction();
        zza.zzgi();
        zzgf.zziz().zze("Local AppMeasurementService called. startId, action", Integer.valueOf(i2), action);
        if ("com.google.android.gms.measurement.UPLOAD".equals(action)) {
            zzb(new Runnable(this, i2, zzgf, intent) { // from class: com.google.android.gms.internal.measurement.zzje
                private final int zzabp;
                private final zzjd zzapy;
                private final zzfh zzapz;
                private final Intent zzaqa;

                /* JADX INFO: Access modifiers changed from: package-private */
                {
                    this.zzapy = this;
                    this.zzabp = i2;
                    this.zzapz = zzgf;
                    this.zzaqa = intent;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    this.zzapy.zza(this.zzabp, this.zzapz, this.zzaqa);
                }
            });
        }
        return 2;
    }

    public final boolean onStartJob(final JobParameters jobParameters) {
        zzgm zza = zzgm.zza(this.zzabm, null, null);
        final zzfh zzgf = zza.zzgf();
        String string = jobParameters.getExtras().getString("action");
        zza.zzgi();
        zzgf.zziz().zzg("Local AppMeasurementJobService called. action", string);
        if (!"com.google.android.gms.measurement.UPLOAD".equals(string)) {
            return true;
        }
        zzb(new Runnable(this, zzgf, jobParameters) { // from class: com.google.android.gms.internal.measurement.zzjf
            private final JobParameters zzabs;
            private final zzjd zzapy;
            private final zzfh zzaqb;

            /* JADX INFO: Access modifiers changed from: package-private */
            {
                this.zzapy = this;
                this.zzaqb = zzgf;
                this.zzabs = jobParameters;
            }

            @Override // java.lang.Runnable
            public final void run() {
                this.zzapy.zza(this.zzaqb, this.zzabs);
            }
        });
        return true;
    }

    public final boolean onUnbind(Intent intent) {
        if (intent == null) {
            zzgf().zzis().log("onUnbind called with null intent");
            return true;
        }
        zzgf().zziz().zzg("onUnbind called for intent. action", intent.getAction());
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final /* synthetic */ void zza(int i, zzfh zzfhVar, Intent intent) {
        if (this.zzabm.callServiceStopSelfResult(i)) {
            zzfhVar.zziz().zzg("Local AppMeasurementService processed last upload request. StartId", Integer.valueOf(i));
            zzgf().zziz().log("Completed wakeful intent.");
            this.zzabm.zzb(intent);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final /* synthetic */ void zza(zzfh zzfhVar, JobParameters jobParameters) {
        zzfhVar.zziz().log("AppMeasurementJobService processed last upload request.");
        this.zzabm.zza(jobParameters, false);
    }
}
