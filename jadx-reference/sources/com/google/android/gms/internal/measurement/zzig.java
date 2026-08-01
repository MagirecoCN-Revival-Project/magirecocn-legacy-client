package com.google.android.gms.internal.measurement;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.collection.ArrayMap;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import java.util.Map;

/* loaded from: classes.dex */
public final class zzig extends zzhi {
    protected zzif zzaov;
    private volatile zzif zzaow;
    private zzif zzaox;
    private final Map<Activity, zzif> zzaoy;
    private zzif zzaoz;
    private String zzapa;

    public zzig(zzgm zzgmVar) {
        super(zzgmVar);
        this.zzaoy = new ArrayMap();
    }

    private final void zza(Activity activity, zzif zzifVar, boolean z) {
        zzif zzifVar2 = this.zzaow == null ? this.zzaox : this.zzaow;
        if (zzifVar.zzaos == null) {
            zzifVar = new zzif(zzifVar.zzul, zzbz(activity.getClass().getCanonicalName()), zzifVar.zzaot);
        }
        this.zzaox = this.zzaow;
        this.zzaow = zzifVar;
        zzge().zzc(new zzih(this, z, zzifVar2, zzifVar));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zza(zzif zzifVar) {
        zzfu().zzk(zzbt().elapsedRealtime());
        if (zzgd().zzl(zzifVar.zzaou)) {
            zzifVar.zzaou = false;
        }
    }

    public static void zza(zzif zzifVar, Bundle bundle, boolean z) {
        if (bundle != null && zzifVar != null && (!bundle.containsKey("_sc") || z)) {
            if (zzifVar.zzul != null) {
                bundle.putString("_sn", zzifVar.zzul);
            } else {
                bundle.remove("_sn");
            }
            bundle.putString("_sc", zzifVar.zzaos);
            bundle.putLong("_si", zzifVar.zzaot);
            return;
        }
        if (bundle != null && zzifVar == null && z) {
            bundle.remove("_sn");
            bundle.remove("_sc");
            bundle.remove("_si");
        }
    }

    private static String zzbz(String str) {
        String[] split = str.split("\\.");
        String str2 = split.length > 0 ? split[split.length - 1] : "";
        return str2.length() > 100 ? str2.substring(0, 100) : str2;
    }

    private final zzif zze(Activity activity) {
        Preconditions.checkNotNull(activity);
        zzif zzifVar = this.zzaoy.get(activity);
        if (zzifVar != null) {
            return zzifVar;
        }
        zzif zzifVar2 = new zzif(null, zzbz(activity.getClass().getCanonicalName()), zzgc().zzlk());
        this.zzaoy.put(activity, zzifVar2);
        return zzifVar2;
    }

    @Override // com.google.android.gms.internal.measurement.zzhh, com.google.android.gms.internal.measurement.zzed
    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    public final void onActivityCreated(Activity activity, Bundle bundle) {
        Bundle bundle2;
        if (bundle == null || (bundle2 = bundle.getBundle("com.google.firebase.analytics.screen_service")) == null) {
            return;
        }
        this.zzaoy.put(activity, new zzif(bundle2.getString("name"), bundle2.getString("referrer_name"), bundle2.getLong("id")));
    }

    public final void onActivityDestroyed(Activity activity) {
        this.zzaoy.remove(activity);
    }

    public final void onActivityPaused(Activity activity) {
        zzif zze = zze(activity);
        this.zzaox = this.zzaow;
        this.zzaow = null;
        zzge().zzc(new zzii(this, zze));
    }

    public final void onActivityResumed(Activity activity) {
        zza(activity, zze(activity), false);
        zzdu zzfu = zzfu();
        zzfu.zzge().zzc(new zzdx(zzfu, zzfu.zzbt().elapsedRealtime()));
    }

    public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        zzif zzifVar;
        if (bundle == null || (zzifVar = this.zzaoy.get(activity)) == null) {
            return;
        }
        Bundle bundle2 = new Bundle();
        bundle2.putLong("id", zzifVar.zzaot);
        bundle2.putString("name", zzifVar.zzul);
        bundle2.putString("referrer_name", zzifVar.zzaos);
        bundle.putBundle("com.google.firebase.analytics.screen_service", bundle2);
    }

    public final void setCurrentScreen(Activity activity, String str, String str2) {
        if (!zzec.isMainThread()) {
            zzgf().zziv().log("setCurrentScreen must be called from the main thread");
            return;
        }
        if (this.zzaow == null) {
            zzgf().zziv().log("setCurrentScreen cannot be called while no activity active");
            return;
        }
        if (this.zzaoy.get(activity) == null) {
            zzgf().zziv().log("setCurrentScreen must be called with an activity in the activity lifecycle");
            return;
        }
        if (str2 == null) {
            str2 = zzbz(activity.getClass().getCanonicalName());
        }
        boolean equals = this.zzaow.zzaos.equals(str2);
        boolean zzs = zzkc.zzs(this.zzaow.zzul, str);
        if (equals && zzs) {
            zzgf().zziw().log("setCurrentScreen cannot be called with the same class and name");
            return;
        }
        if (str != null && (str.length() <= 0 || str.length() > 100)) {
            zzgf().zziv().zzg("Invalid screen name length in setCurrentScreen. Length", Integer.valueOf(str.length()));
            return;
        }
        if (str2 != null && (str2.length() <= 0 || str2.length() > 100)) {
            zzgf().zziv().zzg("Invalid class name length in setCurrentScreen. Length", Integer.valueOf(str2.length()));
            return;
        }
        zzgf().zziz().zze("Setting current screen to name, class", str == null ? "null" : str, str2);
        zzif zzifVar = new zzif(str, str2, zzgc().zzlk());
        this.zzaoy.put(activity, zzifVar);
        zza(activity, zzifVar, true);
    }

    public final void zza(String str, zzif zzifVar) {
        zzab();
        synchronized (this) {
            String str2 = this.zzapa;
            if (str2 == null || str2.equals(str) || zzifVar != null) {
                this.zzapa = str;
                this.zzaoz = zzifVar;
            }
        }
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

    @Override // com.google.android.gms.internal.measurement.zzhi
    protected final boolean zzhh() {
        return false;
    }

    public final zzif zzkk() {
        zzch();
        zzab();
        return this.zzaov;
    }

    public final zzif zzkl() {
        zzfs();
        return this.zzaow;
    }
}
