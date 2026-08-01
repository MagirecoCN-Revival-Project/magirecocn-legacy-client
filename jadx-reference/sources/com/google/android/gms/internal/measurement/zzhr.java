package com.google.android.gms.internal.measurement;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzhr implements Runnable {
    private final /* synthetic */ zzhl zzaog;
    private final /* synthetic */ long zzaok;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzhr(zzhl zzhlVar, long j) {
        this.zzaog = zzhlVar;
        this.zzaok = j;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzhl zzhlVar = this.zzaog;
        long j = this.zzaok;
        zzhlVar.zzab();
        zzhlVar.zzfs();
        zzhlVar.zzch();
        zzhlVar.zzgf().zziy().log("Resetting analytics data (FE)");
        zzhlVar.zzgd().zzks();
        if (zzhlVar.zzgh().zzaz(zzhlVar.zzfw().zzah())) {
            zzhlVar.zzgg().zzaki.set(j);
        }
        boolean isEnabled = zzhlVar.zzacw.isEnabled();
        if (!zzhlVar.zzgh().zzhj()) {
            zzhlVar.zzgg().zzh(!isEnabled);
        }
        zzhlVar.zzfy().resetAnalyticsData();
        zzhlVar.zzaoe = !isEnabled;
    }
}
