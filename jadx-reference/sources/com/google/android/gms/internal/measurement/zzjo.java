package com.google.android.gms.internal.measurement;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PersistableBundle;
import androidx.core.app.NotificationCompat;
import com.google.android.gms.common.util.Clock;

/* loaded from: classes.dex */
public final class zzjo extends zzjr {
    private final zzeo zzaqh;
    private final AlarmManager zzyi;
    private Integer zzyj;

    /* JADX INFO: Access modifiers changed from: protected */
    public zzjo(zzjs zzjsVar) {
        super(zzjsVar);
        this.zzyi = (AlarmManager) getContext().getSystemService(NotificationCompat.CATEGORY_ALARM);
        this.zzaqh = new zzjp(this, zzjsVar.zzlj(), zzjsVar);
    }

    private final int getJobId() {
        if (this.zzyj == null) {
            String valueOf = String.valueOf(getContext().getPackageName());
            this.zzyj = Integer.valueOf((valueOf.length() != 0 ? "measurement".concat(valueOf) : new String("measurement")).hashCode());
        }
        return this.zzyj.intValue();
    }

    private final PendingIntent zzek() {
        Intent className = new Intent().setClassName(getContext(), "com.google.android.gms.measurement.AppMeasurementReceiver");
        className.setAction("com.google.android.gms.measurement.UPLOAD");
        return PendingIntent.getBroadcast(getContext(), 0, className, 0);
    }

    private final void zzku() {
        JobScheduler jobScheduler = (JobScheduler) getContext().getSystemService("jobscheduler");
        zzgf().zziz().zzg("Cancelling job. JobID", Integer.valueOf(getJobId()));
        jobScheduler.cancel(getJobId());
    }

    public final void cancel() {
        zzch();
        this.zzyi.cancel(zzek());
        this.zzaqh.cancel();
        if (Build.VERSION.SDK_INT >= 24) {
            zzku();
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzhh, com.google.android.gms.internal.measurement.zzed
    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ void zzab() {
        super.zzab();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh, com.google.android.gms.internal.measurement.zzed
    public final /* bridge */ /* synthetic */ Clock zzbt() {
        return super.zzbt();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ void zzfr() {
        super.zzfr();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ void zzfs() {
        super.zzfs();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ void zzft() {
        super.zzft();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzdu zzfu() {
        return super.zzfu();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzhl zzfv() {
        return super.zzfv();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzfc zzfw() {
        return super.zzfw();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzeq zzfx() {
        return super.zzfx();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzij zzfy() {
        return super.zzfy();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzig zzfz() {
        return super.zzfz();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzfd zzga() {
        return super.zzga();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzff zzgb() {
        return super.zzgb();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzkc zzgc() {
        return super.zzgc();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzji zzgd() {
        return super.zzgd();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh, com.google.android.gms.internal.measurement.zzed
    public final /* bridge */ /* synthetic */ zzgh zzge() {
        return super.zzge();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh, com.google.android.gms.internal.measurement.zzed
    public final /* bridge */ /* synthetic */ zzfh zzgf() {
        return super.zzgf();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzfs zzgg() {
        return super.zzgg();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzeg zzgh() {
        return super.zzgh();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh, com.google.android.gms.internal.measurement.zzed
    public final /* bridge */ /* synthetic */ zzec zzgi() {
        return super.zzgi();
    }

    public final void zzh(long j) {
        zzch();
        zzgi();
        if (!zzgc.zza(getContext())) {
            zzgf().zziy().log("Receiver not registered/enabled");
        }
        zzgi();
        if (!zzkc.zza(getContext(), false)) {
            zzgf().zziy().log("Service not registered/enabled");
        }
        cancel();
        long elapsedRealtime = zzbt().elapsedRealtime() + j;
        if (j < Math.max(0L, zzey.zzahm.get().longValue()) && !this.zzaqh.zzef()) {
            zzgf().zziz().log("Scheduling upload with DelayedRunnable");
            this.zzaqh.zzh(j);
        }
        zzgi();
        if (Build.VERSION.SDK_INT < 24) {
            zzgf().zziz().log("Scheduling upload with AlarmManager");
            this.zzyi.setInexactRepeating(2, elapsedRealtime, Math.max(zzey.zzahh.get().longValue(), j), zzek());
            return;
        }
        zzgf().zziz().log("Scheduling upload with JobScheduler");
        ComponentName componentName = new ComponentName(getContext(), "com.google.android.gms.measurement.AppMeasurementJobService");
        JobScheduler jobScheduler = (JobScheduler) getContext().getSystemService("jobscheduler");
        JobInfo.Builder builder = new JobInfo.Builder(getJobId(), componentName);
        builder.setMinimumLatency(j);
        builder.setOverrideDeadline(j << 1);
        PersistableBundle persistableBundle = new PersistableBundle();
        persistableBundle.putString("action", "com.google.android.gms.measurement.UPLOAD");
        builder.setExtras(persistableBundle);
        JobInfo build = builder.build();
        zzgf().zziz().zzg("Scheduling job. JobID", Integer.valueOf(getJobId()));
        jobScheduler.schedule(build);
    }

    @Override // com.google.android.gms.internal.measurement.zzjr
    protected final boolean zzhh() {
        this.zzyi.cancel(zzek());
        if (Build.VERSION.SDK_INT < 24) {
            return false;
        }
        zzku();
        return false;
    }

    @Override // com.google.android.gms.internal.measurement.zzjq
    public final /* bridge */ /* synthetic */ zzjy zzjc() {
        return super.zzjc();
    }

    @Override // com.google.android.gms.internal.measurement.zzjq
    public final /* bridge */ /* synthetic */ zzeb zzjd() {
        return super.zzjd();
    }

    @Override // com.google.android.gms.internal.measurement.zzjq
    public final /* bridge */ /* synthetic */ zzej zzje() {
        return super.zzje();
    }
}
