package com.google.android.gms.internal.measurement;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.RemoteException;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.GooglePlayServicesUtilLight;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.stats.ConnectionTracker;
import com.google.android.gms.common.util.Clock;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final class zzij extends zzhi {
    private final zzix zzapg;
    private zzez zzaph;
    private volatile Boolean zzapi;
    private final zzeo zzapj;
    private final zzjn zzapk;
    private final List<Runnable> zzapl;
    private final zzeo zzapm;

    /* JADX INFO: Access modifiers changed from: protected */
    public zzij(zzgm zzgmVar) {
        super(zzgmVar);
        this.zzapl = new ArrayList();
        this.zzapk = new zzjn(zzgmVar.zzbt());
        this.zzapg = new zzix(this);
        this.zzapj = new zzik(this, zzgmVar);
        this.zzapm = new zzip(this, zzgmVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onServiceDisconnected(ComponentName componentName) {
        zzab();
        if (this.zzaph != null) {
            this.zzaph = null;
            zzgf().zziz().zzg("Disconnected from device MeasurementService", componentName);
            zzab();
            zzdf();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ zzez zza(zzij zzijVar, zzez zzezVar) {
        zzijVar.zzaph = null;
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzcu() {
        zzab();
        this.zzapk.start();
        this.zzapj.zzh(zzey.zzahv.get().longValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzcv() {
        zzab();
        if (isConnected()) {
            zzgf().zziz().log("Inactivity, disconnecting from the service");
            disconnect();
        }
    }

    private final void zzf(Runnable runnable) throws IllegalStateException {
        zzab();
        if (isConnected()) {
            runnable.run();
        } else {
            if (this.zzapl.size() >= 1000) {
                zzgf().zzis().log("Discarding data. Max runnable queue size reached");
                return;
            }
            this.zzapl.add(runnable);
            this.zzapm.zzh(60000L);
            zzdf();
        }
    }

    private final zzdz zzk(boolean z) {
        zzgi();
        return zzfw().zzbh(z ? zzgf().zzjb() : null);
    }

    private final boolean zzkn() {
        zzgi();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzkp() {
        zzab();
        zzgf().zziz().zzg("Processing queued up service tasks", Integer.valueOf(this.zzapl.size()));
        Iterator<Runnable> it = this.zzapl.iterator();
        while (it.hasNext()) {
            try {
                it.next().run();
            } catch (Exception e) {
                zzgf().zzis().zzg("Task exception while flushing queue", e);
            }
        }
        this.zzapl.clear();
        this.zzapm.cancel();
    }

    public final void disconnect() {
        zzab();
        zzch();
        try {
            ConnectionTracker.getInstance().unbindService(getContext(), this.zzapg);
        } catch (IllegalArgumentException | IllegalStateException unused) {
        }
        this.zzaph = null;
    }

    @Override // com.google.android.gms.internal.measurement.zzhh, com.google.android.gms.internal.measurement.zzed
    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    public final boolean isConnected() {
        zzab();
        zzch();
        return this.zzaph != null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void resetAnalyticsData() {
        zzab();
        zzfs();
        zzch();
        zzdz zzk = zzk(false);
        if (zzkn()) {
            zzga().resetAnalyticsData();
        }
        zzf(new zzil(this, zzk));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void zza(zzez zzezVar) {
        zzab();
        Preconditions.checkNotNull(zzezVar);
        this.zzaph = zzezVar;
        zzcu();
        zzkp();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void zza(zzez zzezVar, AbstractSafeParcelable abstractSafeParcelable, zzdz zzdzVar) {
        int i;
        zzfj zzis;
        String str;
        List<AbstractSafeParcelable> zzp;
        zzab();
        zzfs();
        zzch();
        boolean zzkn = zzkn();
        int i2 = 0;
        int i3 = 100;
        while (i2 < 1001 && i3 == 100) {
            ArrayList arrayList = new ArrayList();
            if (!zzkn || (zzp = zzga().zzp(100)) == null) {
                i = 0;
            } else {
                arrayList.addAll(zzp);
                i = zzp.size();
            }
            if (abstractSafeParcelable != null && i < 100) {
                arrayList.add(abstractSafeParcelable);
            }
            int size = arrayList.size();
            int i4 = 0;
            while (i4 < size) {
                Object obj = arrayList.get(i4);
                i4++;
                AbstractSafeParcelable abstractSafeParcelable2 = (AbstractSafeParcelable) obj;
                if (abstractSafeParcelable2 instanceof zzew) {
                    try {
                        zzezVar.zza((zzew) abstractSafeParcelable2, zzdzVar);
                    } catch (RemoteException e) {
                        e = e;
                        zzis = zzgf().zzis();
                        str = "Failed to send event to the service";
                        zzis.zzg(str, e);
                    }
                } else if (abstractSafeParcelable2 instanceof zzjz) {
                    try {
                        zzezVar.zza((zzjz) abstractSafeParcelable2, zzdzVar);
                    } catch (RemoteException e2) {
                        e = e2;
                        zzis = zzgf().zzis();
                        str = "Failed to send attribute to the service";
                        zzis.zzg(str, e);
                    }
                } else if (abstractSafeParcelable2 instanceof zzee) {
                    try {
                        zzezVar.zza((zzee) abstractSafeParcelable2, zzdzVar);
                    } catch (RemoteException e3) {
                        e = e3;
                        zzis = zzgf().zzis();
                        str = "Failed to send conditional property to the service";
                        zzis.zzg(str, e);
                    }
                } else {
                    zzgf().zzis().log("Discarding data. Unrecognized parcel type.");
                }
            }
            i2++;
            i3 = i;
        }
    }

    public final void zza(AtomicReference<String> atomicReference) {
        zzab();
        zzch();
        zzf(new zzim(this, atomicReference, zzk(false)));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void zza(AtomicReference<List<zzee>> atomicReference, String str, String str2, String str3) {
        zzab();
        zzch();
        zzf(new zzit(this, atomicReference, str, str2, str3, zzk(false)));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void zza(AtomicReference<List<zzjz>> atomicReference, String str, String str2, String str3, boolean z) {
        zzab();
        zzch();
        zzf(new zziu(this, atomicReference, str, str2, str3, z, zzk(false)));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void zza(AtomicReference<List<zzjz>> atomicReference, boolean z) {
        zzab();
        zzch();
        zzf(new zziw(this, atomicReference, zzk(false), z));
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ void zzab() {
        super.zzab();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void zzb(zzew zzewVar, String str) {
        Preconditions.checkNotNull(zzewVar);
        zzab();
        zzch();
        boolean zzkn = zzkn();
        zzf(new zzir(this, zzkn, zzkn && zzga().zza(zzewVar), zzewVar, zzk(true), str));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void zzb(zzif zzifVar) {
        zzab();
        zzch();
        zzf(new zzio(this, zzifVar));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void zzb(zzjz zzjzVar) {
        zzab();
        zzch();
        zzf(new zziv(this, zzkn() && zzga().zza(zzjzVar), zzjzVar, zzk(true)));
    }

    @Override // com.google.android.gms.internal.measurement.zzhh, com.google.android.gms.internal.measurement.zzed
    public final /* bridge */ /* synthetic */ Clock zzbt() {
        return super.zzbt();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void zzd(zzee zzeeVar) {
        Preconditions.checkNotNull(zzeeVar);
        zzab();
        zzch();
        zzgi();
        zzf(new zzis(this, true, zzga().zzc(zzeeVar), new zzee(zzeeVar), zzk(true), zzeeVar));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Removed duplicated region for block: B:18:0x00f4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void zzdf() {
        zzfj zziz;
        String str;
        boolean z;
        boolean z2;
        zzfj zziv;
        String str2;
        zzab();
        zzch();
        if (isConnected()) {
            return;
        }
        boolean z3 = false;
        if (this.zzapi == null) {
            zzab();
            zzch();
            Boolean zzji = zzgg().zzji();
            if (zzji == null || !zzji.booleanValue()) {
                zzgi();
                if (zzfw().zziq() != 1) {
                    zzgf().zziz().log("Checking service availability");
                    int isGooglePlayServicesAvailable = GoogleApiAvailabilityLight.getInstance().isGooglePlayServicesAvailable(zzgc().getContext(), GooglePlayServicesUtilLight.GOOGLE_PLAY_SERVICES_VERSION_CODE);
                    if (isGooglePlayServicesAvailable != 0) {
                        if (isGooglePlayServicesAvailable != 1) {
                            if (isGooglePlayServicesAvailable != 2) {
                                if (isGooglePlayServicesAvailable == 3) {
                                    zziv = zzgf().zziv();
                                    str2 = "Service disabled";
                                } else if (isGooglePlayServicesAvailable == 9) {
                                    zziv = zzgf().zziv();
                                    str2 = "Service invalid";
                                } else if (isGooglePlayServicesAvailable != 18) {
                                    zzgf().zziv().zzg("Unexpected service status", Integer.valueOf(isGooglePlayServicesAvailable));
                                } else {
                                    zziz = zzgf().zziv();
                                    str = "Service updating";
                                }
                                zziv.log(str2);
                            } else {
                                zzgf().zziy().log("Service container out of date");
                                if (zzgc().zzlm() >= 12600) {
                                    Boolean zzji2 = zzgg().zzji();
                                    if (zzji2 == null || zzji2.booleanValue()) {
                                        z = true;
                                        z2 = false;
                                        if (z2) {
                                            zzgg().zzf(z);
                                        }
                                    }
                                }
                            }
                            z = false;
                            z2 = false;
                            if (z2) {
                            }
                        } else {
                            zzgf().zziz().log("Service missing");
                        }
                        z = false;
                        z2 = true;
                        if (z2) {
                        }
                    } else {
                        zziz = zzgf().zziz();
                        str = "Service available";
                    }
                    zziz.log(str);
                }
                z = true;
                z2 = true;
                if (z2) {
                }
            } else {
                z = true;
            }
            this.zzapi = Boolean.valueOf(z);
        }
        if (this.zzapi.booleanValue()) {
            this.zzapg.zzkq();
            return;
        }
        zzgi();
        List<ResolveInfo> queryIntentServices = getContext().getPackageManager().queryIntentServices(new Intent().setClassName(getContext(), "com.google.android.gms.measurement.AppMeasurementService"), 65536);
        if (queryIntentServices != null && queryIntentServices.size() > 0) {
            z3 = true;
        }
        if (!z3) {
            zzgf().zzis().log("Unable to use remote or local measurement implementation. Please register the AppMeasurementService service in the app manifest");
            return;
        }
        Intent intent = new Intent("com.google.android.gms.measurement.START");
        Context context = getContext();
        zzgi();
        intent.setComponent(new ComponentName(context, "com.google.android.gms.measurement.AppMeasurementService"));
        this.zzapg.zzc(intent);
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

    @Override // com.google.android.gms.internal.measurement.zzhi
    protected final boolean zzhh() {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void zzkj() {
        zzab();
        zzch();
        zzf(new zzin(this, zzk(true)));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void zzkm() {
        zzab();
        zzch();
        zzf(new zziq(this, zzk(true)));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final Boolean zzko() {
        return this.zzapi;
    }
}
