package com.google.android.gms.internal.measurement;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;
import androidx.core.os.EnvironmentCompat;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.GoogleServices;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.wrappers.InstantApps;
import com.google.firebase.iid.FirebaseInstanceId;
import java.math.BigInteger;
import java.util.Locale;

/* loaded from: classes.dex */
public final class zzfc extends zzhi {
    private String zzadm;
    private String zzadt;
    private long zzadx;
    private int zzaen;
    private int zzain;
    private long zzaio;
    private String zztg;
    private String zzth;
    private String zzti;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzfc(zzgm zzgmVar) {
        super(zzgmVar);
    }

    private final String zzgl() {
        zzab();
        zzfs();
        if (zzgh().zzax(this.zzti) && !this.zzacw.isEnabled()) {
            return null;
        }
        try {
            return FirebaseInstanceId.getInstance().getId();
        } catch (IllegalStateException unused) {
            zzgf().zziv().log("Failed to retrieve Firebase Instance Id");
            return null;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzhh, com.google.android.gms.internal.measurement.zzed
    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final String getGmpAppId() {
        zzch();
        return this.zzadm;
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ void zzab() {
        super.zzab();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final String zzah() {
        zzch();
        return this.zzti;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final zzdz zzbh(String str) {
        zzab();
        zzfs();
        String zzah = zzah();
        String gmpAppId = getGmpAppId();
        zzch();
        String str2 = this.zzth;
        long zzip = zzip();
        zzch();
        String str3 = this.zzadt;
        zzch();
        zzab();
        if (this.zzaio == 0) {
            this.zzaio = this.zzacw.zzgc().zzd(getContext(), getContext().getPackageName());
        }
        long j = this.zzaio;
        boolean isEnabled = this.zzacw.isEnabled();
        boolean z = !zzgg().zzakw;
        String zzgl = zzgl();
        zzch();
        long j2 = this.zzadx;
        long zzkb = this.zzacw.zzkb();
        int zziq = zziq();
        zzeg zzgh = zzgh();
        zzgh.zzfs();
        Boolean zzar = zzgh.zzar("google_analytics_adid_collection_enabled");
        boolean booleanValue = Boolean.valueOf(zzar == null || zzar.booleanValue()).booleanValue();
        zzeg zzgh2 = zzgh();
        zzgh2.zzfs();
        Boolean zzar2 = zzgh2.zzar("google_analytics_ssaid_collection_enabled");
        return new zzdz(zzah, gmpAppId, str2, zzip, str3, 12451L, j, str, isEnabled, z, zzgl, j2, zzkb, zziq, booleanValue, Boolean.valueOf(zzar2 == null || zzar2.booleanValue()).booleanValue(), zzgg().zzjl());
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
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x00b8  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x00e3  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0147  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x018a  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0195  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0150 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x00ad  */
    @Override // com.google.android.gms.internal.measurement.zzhi
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected final void zzin() {
        String str;
        String str2;
        PackageInfo packageInfo;
        boolean z;
        zzfj zzix;
        String str3;
        String packageName = getContext().getPackageName();
        PackageManager packageManager = getContext().getPackageManager();
        String str4 = "Unknown";
        String str5 = "";
        String str6 = EnvironmentCompat.MEDIA_UNKNOWN;
        int i = Integer.MIN_VALUE;
        if (packageManager == null) {
            zzgf().zzis().zzg("PackageManager is null, app identity information might be inaccurate. appId", zzfh.zzbl(packageName));
        } else {
            try {
                str6 = packageManager.getInstallerPackageName(packageName);
            } catch (IllegalArgumentException unused) {
                zzgf().zzis().zzg("Error retrieving app installer package name. appId", zzfh.zzbl(packageName));
            }
            if (str6 == null) {
                str6 = "manual_install";
            } else if ("com.android.vending".equals(str6)) {
                str6 = "";
            }
            try {
                packageInfo = packageManager.getPackageInfo(getContext().getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException unused2) {
                str = "Unknown";
            }
            if (packageInfo != null) {
                CharSequence applicationLabel = packageManager.getApplicationLabel(packageInfo.applicationInfo);
                str2 = !TextUtils.isEmpty(applicationLabel) ? applicationLabel.toString() : "Unknown";
                try {
                    str4 = packageInfo.versionName;
                    i = packageInfo.versionCode;
                } catch (PackageManager.NameNotFoundException unused3) {
                    String str7 = str4;
                    str4 = str2;
                    str = str7;
                    zzgf().zzis().zze("Error retrieving package info. appId, appName", zzfh.zzbl(packageName), str4);
                    String str8 = str4;
                    str4 = str;
                    str2 = str8;
                    this.zzti = packageName;
                    this.zzadt = str6;
                    this.zzth = str4;
                    this.zzain = i;
                    this.zztg = str2;
                    this.zzaio = 0L;
                    zzgi();
                    Status initialize = GoogleServices.initialize(getContext());
                    boolean z2 = true;
                    if (initialize == null) {
                    }
                    if (!z) {
                    }
                    if (z) {
                    }
                    z2 = false;
                    this.zzadm = "";
                    this.zzadx = 0L;
                    zzgi();
                    if (this.zzacw.zzka() == null) {
                    }
                    if (Build.VERSION.SDK_INT < 16) {
                    }
                }
                this.zzti = packageName;
                this.zzadt = str6;
                this.zzth = str4;
                this.zzain = i;
                this.zztg = str2;
                this.zzaio = 0L;
                zzgi();
                Status initialize2 = GoogleServices.initialize(getContext());
                boolean z22 = true;
                z = initialize2 == null && initialize2.isSuccess();
                if (!z) {
                    if (initialize2 == null) {
                        zzgf().zzis().log("GoogleService failed to initialize (no status)");
                    } else {
                        zzgf().zzis().zze("GoogleService failed to initialize, status", Integer.valueOf(initialize2.getStatusCode()), initialize2.getStatusMessage());
                    }
                }
                if (z) {
                    Boolean zzhk = zzgh().zzhk();
                    if (zzgh().zzhj()) {
                        zzix = zzgf().zzix();
                        str3 = "Collection disabled with firebase_analytics_collection_deactivated=1";
                    } else if (zzhk != null && !zzhk.booleanValue()) {
                        zzix = zzgf().zzix();
                        str3 = "Collection disabled with firebase_analytics_collection_enabled=0";
                    } else {
                        if (zzhk != null || !GoogleServices.isMeasurementExplicitlyDisabled()) {
                            zzgf().zziz().log("Collection enabled");
                            this.zzadm = "";
                            this.zzadx = 0L;
                            zzgi();
                            if (this.zzacw.zzka() == null) {
                                this.zzadm = this.zzacw.zzka();
                            } else {
                                try {
                                    String googleAppId = GoogleServices.getGoogleAppId();
                                    if (!TextUtils.isEmpty(googleAppId)) {
                                        str5 = googleAppId;
                                    }
                                    this.zzadm = str5;
                                    if (z22) {
                                        zzgf().zziz().zze("App package, google app id", this.zzti, this.zzadm);
                                    }
                                } catch (IllegalStateException e) {
                                    zzgf().zzis().zze("getGoogleAppId or isMeasurementEnabled failed with exception. appId", zzfh.zzbl(packageName), e);
                                }
                            }
                            if (Build.VERSION.SDK_INT < 16) {
                                this.zzaen = InstantApps.isInstantApp(getContext()) ? 1 : 0;
                                return;
                            } else {
                                this.zzaen = 0;
                                return;
                            }
                        }
                        zzix = zzgf().zzix();
                        str3 = "Collection disabled with google_app_measurement_enable=0";
                    }
                    zzix.log(str3);
                }
                z22 = false;
                this.zzadm = "";
                this.zzadx = 0L;
                zzgi();
                if (this.zzacw.zzka() == null) {
                }
                if (Build.VERSION.SDK_INT < 16) {
                }
            }
        }
        str2 = "Unknown";
        this.zzti = packageName;
        this.zzadt = str6;
        this.zzth = str4;
        this.zzain = i;
        this.zztg = str2;
        this.zzaio = 0L;
        zzgi();
        Status initialize22 = GoogleServices.initialize(getContext());
        boolean z222 = true;
        if (initialize22 == null) {
        }
        if (!z) {
        }
        if (z) {
        }
        z222 = false;
        this.zzadm = "";
        this.zzadx = 0L;
        zzgi();
        if (this.zzacw.zzka() == null) {
        }
        if (Build.VERSION.SDK_INT < 16) {
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final String zzio() {
        byte[] bArr = new byte[16];
        zzgc().zzll().nextBytes(bArr);
        return String.format(Locale.US, "%032x", new BigInteger(1, bArr));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final int zzip() {
        zzch();
        return this.zzain;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final int zziq() {
        zzch();
        return this.zzaen;
    }
}
