package com.google.android.gms.internal.measurement;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.ProcessUtils;
import com.google.android.gms.common.wrappers.Wrappers;
import com.google.android.gms.internal.measurement.zzey;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes.dex */
public final class zzeg extends zzhh {
    private zzei zzaeu;
    private Boolean zzxz;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzeg(zzgm zzgmVar) {
        super(zzgmVar);
        this.zzaeu = zzeh.zzaev;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String zzhi() {
        return zzey.zzagp.get();
    }

    public static long zzhl() {
        return zzey.zzahs.get().longValue();
    }

    public static long zzhm() {
        return zzey.zzags.get().longValue();
    }

    public static boolean zzho() {
        return zzey.zzago.get().booleanValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh, com.google.android.gms.internal.measurement.zzed
    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    public final long zza(String str, zzey.zza<Long> zzaVar) {
        if (str != null) {
            String zze = this.zzaeu.zze(str, zzaVar.getKey());
            if (!TextUtils.isEmpty(zze)) {
                try {
                    return zzaVar.get(Long.valueOf(Long.parseLong(zze))).longValue();
                } catch (NumberFormatException unused) {
                }
            }
        }
        return zzaVar.get().longValue();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void zza(zzei zzeiVar) {
        this.zzaeu = zzeiVar;
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ void zzab() {
        super.zzab();
    }

    public final int zzaq(String str) {
        return zzb(str, zzey.zzahd);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final Boolean zzar(String str) {
        Preconditions.checkNotEmpty(str);
        try {
            if (getContext().getPackageManager() == null) {
                zzgf().zzis().log("Failed to load metadata: PackageManager is null");
                return null;
            }
            ApplicationInfo applicationInfo = Wrappers.packageManager(getContext()).getApplicationInfo(getContext().getPackageName(), 128);
            if (applicationInfo == null) {
                zzgf().zzis().log("Failed to load metadata: ApplicationInfo is null");
                return null;
            }
            if (applicationInfo.metaData == null) {
                zzgf().zzis().log("Failed to load metadata: Metadata bundle is null");
                return null;
            }
            if (applicationInfo.metaData.containsKey(str)) {
                return Boolean.valueOf(applicationInfo.metaData.getBoolean(str));
            }
            return null;
        } catch (PackageManager.NameNotFoundException e) {
            zzgf().zzis().zzg("Failed to load metadata: Package name not found", e);
            return null;
        }
    }

    public final boolean zzas(String str) {
        return "1".equals(this.zzaeu.zze(str, "gaia_collection_enabled"));
    }

    public final boolean zzat(String str) {
        return "1".equals(this.zzaeu.zze(str, "measurement.event_sampling_enabled"));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final boolean zzau(String str) {
        return zzd(str, zzey.zzaib);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final boolean zzav(String str) {
        return zzd(str, zzey.zzaid);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final boolean zzaw(String str) {
        return zzd(str, zzey.zzaie);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final boolean zzax(String str) {
        return zzd(str, zzey.zzaif);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final boolean zzay(String str) {
        return zzd(str, zzey.zzaig);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final boolean zzaz(String str) {
        return zzd(str, zzey.zzaij);
    }

    public final int zzb(String str, zzey.zza<Integer> zzaVar) {
        if (str != null) {
            String zze = this.zzaeu.zze(str, zzaVar.getKey());
            if (!TextUtils.isEmpty(zze)) {
                try {
                    return zzaVar.get(Integer.valueOf(Integer.parseInt(zze))).intValue();
                } catch (NumberFormatException unused) {
                }
            }
        }
        return zzaVar.get().intValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh, com.google.android.gms.internal.measurement.zzed
    public final /* bridge */ /* synthetic */ Clock zzbt() {
        return super.zzbt();
    }

    public final double zzc(String str, zzey.zza<Double> zzaVar) {
        if (str != null) {
            String zze = this.zzaeu.zze(str, zzaVar.getKey());
            if (!TextUtils.isEmpty(zze)) {
                try {
                    return zzaVar.get(Double.valueOf(Double.parseDouble(zze))).doubleValue();
                } catch (NumberFormatException unused) {
                }
            }
        }
        return zzaVar.get().doubleValue();
    }

    public final boolean zzd(String str, zzey.zza<Boolean> zzaVar) {
        Boolean bool;
        if (str != null) {
            String zze = this.zzaeu.zze(str, zzaVar.getKey());
            if (!TextUtils.isEmpty(zze)) {
                bool = zzaVar.get(Boolean.valueOf(Boolean.parseBoolean(zze)));
                return bool.booleanValue();
            }
        }
        bool = zzaVar.get();
        return bool.booleanValue();
    }

    public final boolean zzds() {
        if (this.zzxz == null) {
            synchronized (this) {
                if (this.zzxz == null) {
                    ApplicationInfo applicationInfo = getContext().getApplicationInfo();
                    String myProcessName = ProcessUtils.getMyProcessName();
                    if (applicationInfo != null) {
                        String str = applicationInfo.processName;
                        this.zzxz = Boolean.valueOf(str != null && str.equals(myProcessName));
                    }
                    if (this.zzxz == null) {
                        this.zzxz = Boolean.TRUE;
                        zzgf().zzis().log("My process not in the list of running processes");
                    }
                }
            }
        }
        return this.zzxz.booleanValue();
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

    public final boolean zzhj() {
        zzgi();
        Boolean zzar = zzar("firebase_analytics_collection_deactivated");
        return zzar != null && zzar.booleanValue();
    }

    public final Boolean zzhk() {
        zzgi();
        return zzar("firebase_analytics_collection_enabled");
    }

    public final String zzhn() {
        zzfj zzis;
        String str;
        try {
            return (String) Class.forName("android.os.SystemProperties").getMethod("get", String.class, String.class).invoke(null, "debug.firebase.analytics.app", "");
        } catch (ClassNotFoundException e) {
            e = e;
            zzis = zzgf().zzis();
            str = "Could not find SystemProperties class";
            zzis.zzg(str, e);
            return "";
        } catch (IllegalAccessException e2) {
            e = e2;
            zzis = zzgf().zzis();
            str = "Could not access SystemProperties.get()";
            zzis.zzg(str, e);
            return "";
        } catch (NoSuchMethodException e3) {
            e = e3;
            zzis = zzgf().zzis();
            str = "Could not find SystemProperties.get() method";
            zzis.zzg(str, e);
            return "";
        } catch (InvocationTargetException e4) {
            e = e4;
            zzis = zzgf().zzis();
            str = "SystemProperties.get() threw an exception";
            zzis.zzg(str, e);
            return "";
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final boolean zzhp() {
        return zzd(zzfw().zzah(), zzey.zzahw);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final String zzhq() {
        String zzah = zzfw().zzah();
        zzey.zza<String> zzaVar = zzey.zzahx;
        return zzah == null ? zzaVar.get() : zzaVar.get(this.zzaeu.zze(zzah, zzaVar.getKey()));
    }
}
