package com.google.android.gms.internal.measurement;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import com.google.android.gms.common.api.internal.GoogleServices;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.DefaultClock;
import com.google.android.gms.common.wrappers.Wrappers;
import com.google.android.gms.measurement.AppMeasurement;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public class zzgm implements zzhj {
    private static volatile zzgm zzamn;
    private final long zzaem;
    private final zzec zzagd;
    private final String zzamo;
    private final zzeg zzamp;
    private final zzfs zzamq;
    private final zzfh zzamr;
    private final zzgh zzams;
    private final zzji zzamt;
    private final AppMeasurement zzamu;
    private final FirebaseAnalytics zzamv;
    private final zzkc zzamw;
    private final zzff zzamx;
    private final zzig zzamy;
    private final zzhl zzamz;
    private final zzdu zzana;
    private zzfd zzanb;
    private zzij zzanc;
    private zzeq zzand;
    private zzfc zzane;
    private zzfy zzanf;
    private Boolean zzang;
    private long zzanh;
    private int zzani;
    private int zzanj;
    private final Context zzqx;
    private final Clock zzro;
    private boolean zzvo = false;

    private zzgm(zzhk zzhkVar) {
        zzfj zziv;
        String str;
        Preconditions.checkNotNull(zzhkVar);
        zzec zzecVar = new zzec(zzhkVar.zzqx);
        this.zzagd = zzecVar;
        zzey.zza(zzecVar);
        Context context = zzhkVar.zzqx;
        this.zzqx = context;
        this.zzamo = zzhkVar.zzamo;
        zzwu.init(context);
        Clock defaultClock = DefaultClock.getInstance();
        this.zzro = defaultClock;
        this.zzaem = defaultClock.currentTimeMillis();
        this.zzamp = new zzeg(this);
        zzfs zzfsVar = new zzfs(this);
        zzfsVar.zzm();
        this.zzamq = zzfsVar;
        zzfh zzfhVar = new zzfh(this);
        zzfhVar.zzm();
        this.zzamr = zzfhVar;
        zzkc zzkcVar = new zzkc(this);
        zzkcVar.zzm();
        this.zzamw = zzkcVar;
        zzff zzffVar = new zzff(this);
        zzffVar.zzm();
        this.zzamx = zzffVar;
        this.zzana = new zzdu(this);
        zzig zzigVar = new zzig(this);
        zzigVar.zzm();
        this.zzamy = zzigVar;
        zzhl zzhlVar = new zzhl(this);
        zzhlVar.zzm();
        this.zzamz = zzhlVar;
        this.zzamu = new AppMeasurement(this);
        this.zzamv = new FirebaseAnalytics(this);
        zzji zzjiVar = new zzji(this);
        zzjiVar.zzm();
        this.zzamt = zzjiVar;
        zzgh zzghVar = new zzgh(this);
        zzghVar.zzm();
        this.zzams = zzghVar;
        if (context.getApplicationContext() instanceof Application) {
            zzhl zzfv = zzfv();
            if (zzfv.getContext().getApplicationContext() instanceof Application) {
                Application application = (Application) zzfv.getContext().getApplicationContext();
                if (zzfv.zzanz == null) {
                    zzfv.zzanz = new zzie(zzfv, null);
                }
                application.unregisterActivityLifecycleCallbacks(zzfv.zzanz);
                application.registerActivityLifecycleCallbacks(zzfv.zzanz);
                zziv = zzfv.zzgf().zziz();
                str = "Registered activity lifecycle callback";
            }
            zzghVar.zzc(new zzgn(this, zzhkVar));
        }
        zziv = zzgf().zziv();
        str = "Application context is not an Application";
        zziv.log(str);
        zzghVar.zzc(new zzgn(this, zzhkVar));
    }

    public static zzgm zza(Context context, String str, String str2) {
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(context.getApplicationContext());
        if (zzamn == null) {
            synchronized (zzgm.class) {
                if (zzamn == null) {
                    zzamn = new zzgm(new zzhk(context, null));
                }
            }
        }
        return zzamn;
    }

    private static void zza(zzhh zzhhVar) {
        if (zzhhVar == null) {
            throw new IllegalStateException("Component not created");
        }
    }

    private static void zza(zzhi zzhiVar) {
        if (zzhiVar == null) {
            throw new IllegalStateException("Component not created");
        }
        if (zzhiVar.isInitialized()) {
            return;
        }
        String valueOf = String.valueOf(zzhiVar.getClass());
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 27);
        sb.append("Component not initialized: ");
        sb.append(valueOf);
        throw new IllegalStateException(sb.toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zza(zzhk zzhkVar) {
        String concat;
        zzfj zzfjVar;
        zzge().zzab();
        zzeg.zzhi();
        zzeq zzeqVar = new zzeq(this);
        zzeqVar.zzm();
        this.zzand = zzeqVar;
        zzfc zzfcVar = new zzfc(this);
        zzfcVar.zzm();
        this.zzane = zzfcVar;
        zzfd zzfdVar = new zzfd(this);
        zzfdVar.zzm();
        this.zzanb = zzfdVar;
        zzij zzijVar = new zzij(this);
        zzijVar.zzm();
        this.zzanc = zzijVar;
        this.zzamw.zzke();
        this.zzamq.zzke();
        this.zzanf = new zzfy(this);
        this.zzane.zzke();
        zzgf().zzix().zzg("App measurement is starting up, version", 12451L);
        zzgf().zzix().log("To enable debug logging run: adb shell setprop log.tag.FA VERBOSE");
        String zzah = zzfcVar.zzah();
        if (zzgc().zzci(zzah)) {
            zzfjVar = zzgf().zzix();
            concat = "Faster debug mode event logging enabled. To disable, run:\n  adb shell setprop debug.firebase.analytics.app .none.";
        } else {
            zzfj zzix = zzgf().zzix();
            String valueOf = String.valueOf(zzah);
            concat = valueOf.length() != 0 ? "To enable faster debug mode event logging run:\n  adb shell setprop debug.firebase.analytics.app ".concat(valueOf) : new String("To enable faster debug mode event logging run:\n  adb shell setprop debug.firebase.analytics.app ");
            zzfjVar = zzix;
        }
        zzfjVar.log(concat);
        zzgf().zziy().log("Debug-level message logging enabled");
        if (this.zzani != this.zzanj) {
            zzgf().zzis().zze("Not all components initialized", Integer.valueOf(this.zzani), Integer.valueOf(this.zzanj));
        }
        this.zzvo = true;
    }

    private final void zzch() {
        if (!this.zzvo) {
            throw new IllegalStateException("AppMeasurement is not initialized");
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzed
    public final Context getContext() {
        return this.zzqx;
    }

    public final boolean isEnabled() {
        zzge().zzab();
        zzch();
        boolean z = false;
        if (this.zzamp.zzhj()) {
            return false;
        }
        Boolean zzhk = this.zzamp.zzhk();
        if (zzhk != null) {
            z = zzhk.booleanValue();
        } else if (!GoogleServices.isMeasurementExplicitlyDisabled()) {
            z = true;
        }
        return zzgg().zzg(z);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void start() {
        zzge().zzab();
        if (zzgg().zzakd.get() == 0) {
            zzgg().zzakd.set(this.zzro.currentTimeMillis());
        }
        if (Long.valueOf(zzgg().zzaki.get()).longValue() == 0) {
            zzgf().zziz().zzg("Persisting first open", Long.valueOf(this.zzaem));
            zzgg().zzaki.set(this.zzaem);
        }
        if (!zzkd()) {
            if (isEnabled()) {
                if (!zzgc().zzw("android.permission.INTERNET")) {
                    zzgf().zzis().log("App is missing INTERNET permission");
                }
                if (!zzgc().zzw("android.permission.ACCESS_NETWORK_STATE")) {
                    zzgf().zzis().log("App is missing ACCESS_NETWORK_STATE permission");
                }
                if (!Wrappers.packageManager(this.zzqx).isCallerInstantApp()) {
                    if (!zzgc.zza(this.zzqx)) {
                        zzgf().zzis().log("AppMeasurementReceiver not registered/enabled");
                    }
                    if (!zzkc.zza(this.zzqx, false)) {
                        zzgf().zzis().log("AppMeasurementService not registered/enabled");
                    }
                }
                zzgf().zzis().log("Uploading is not possible. App measurement disabled");
                return;
            }
            return;
        }
        if (!TextUtils.isEmpty(zzfw().getGmpAppId())) {
            String zzjg = zzgg().zzjg();
            if (zzjg == null) {
                zzgg().zzbp(zzfw().getGmpAppId());
            } else if (!zzjg.equals(zzfw().getGmpAppId())) {
                zzgf().zzix().log("Rechecking which service to use due to a GMP App Id change");
                zzgg().zzjj();
                this.zzanc.disconnect();
                this.zzanc.zzdf();
                zzgg().zzbp(zzfw().getGmpAppId());
                zzgg().zzaki.set(this.zzaem);
                zzgg().zzakk.zzbr(null);
            }
        }
        zzfv().zzbq(zzgg().zzakk.zzjn());
        if (TextUtils.isEmpty(zzfw().getGmpAppId())) {
            return;
        }
        boolean isEnabled = isEnabled();
        if (!zzgg().zzjm() && !this.zzamp.zzhj()) {
            zzgg().zzh(!isEnabled);
        }
        if (!this.zzamp.zzay(zzfw().zzah()) || isEnabled) {
            zzfv().zzkj();
        }
        zzfy().zza(new AtomicReference<>());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void zzb(zzhi zzhiVar) {
        this.zzani++;
    }

    @Override // com.google.android.gms.internal.measurement.zzed
    public final Clock zzbt() {
        return this.zzro;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void zzfr() {
        throw new IllegalStateException("Unexpected call on client side");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void zzfs() {
    }

    public final zzdu zzfu() {
        zza(this.zzana);
        return this.zzana;
    }

    public final zzhl zzfv() {
        zza((zzhi) this.zzamz);
        return this.zzamz;
    }

    public final zzfc zzfw() {
        zza((zzhi) this.zzane);
        return this.zzane;
    }

    public final zzeq zzfx() {
        zza((zzhi) this.zzand);
        return this.zzand;
    }

    public final zzij zzfy() {
        zza((zzhi) this.zzanc);
        return this.zzanc;
    }

    public final zzig zzfz() {
        zza((zzhi) this.zzamy);
        return this.zzamy;
    }

    public final zzfd zzga() {
        zza((zzhi) this.zzanb);
        return this.zzanb;
    }

    public final zzff zzgb() {
        zza((zzhh) this.zzamx);
        return this.zzamx;
    }

    public final zzkc zzgc() {
        zza((zzhh) this.zzamw);
        return this.zzamw;
    }

    public final zzji zzgd() {
        zza((zzhi) this.zzamt);
        return this.zzamt;
    }

    @Override // com.google.android.gms.internal.measurement.zzed
    public final zzgh zzge() {
        zza((zzhi) this.zzams);
        return this.zzams;
    }

    @Override // com.google.android.gms.internal.measurement.zzed
    public final zzfh zzgf() {
        zza((zzhi) this.zzamr);
        return this.zzamr;
    }

    public final zzfs zzgg() {
        zza((zzhh) this.zzamq);
        return this.zzamq;
    }

    public final zzeg zzgh() {
        return this.zzamp;
    }

    @Override // com.google.android.gms.internal.measurement.zzed
    public final zzec zzgi() {
        return this.zzagd;
    }

    public final zzfh zzjv() {
        zzfh zzfhVar = this.zzamr;
        if (zzfhVar == null || !zzfhVar.isInitialized()) {
            return null;
        }
        return this.zzamr;
    }

    public final zzfy zzjw() {
        return this.zzanf;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final zzgh zzjx() {
        return this.zzams;
    }

    public final AppMeasurement zzjy() {
        return this.zzamu;
    }

    public final FirebaseAnalytics zzjz() {
        return this.zzamv;
    }

    public final String zzka() {
        return this.zzamo;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final long zzkb() {
        Long valueOf = Long.valueOf(zzgg().zzaki.get());
        return valueOf.longValue() == 0 ? this.zzaem : Math.min(this.zzaem, valueOf.longValue());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void zzkc() {
        this.zzanj++;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final boolean zzkd() {
        zzch();
        zzge().zzab();
        Boolean bool = this.zzang;
        if (bool == null || this.zzanh == 0 || (bool != null && !bool.booleanValue() && Math.abs(this.zzro.elapsedRealtime() - this.zzanh) > 1000)) {
            this.zzanh = this.zzro.elapsedRealtime();
            boolean z = false;
            if (zzgc().zzw("android.permission.INTERNET") && zzgc().zzw("android.permission.ACCESS_NETWORK_STATE") && (Wrappers.packageManager(this.zzqx).isCallerInstantApp() || (zzgc.zza(this.zzqx) && zzkc.zza(this.zzqx, false)))) {
                z = true;
            }
            Boolean valueOf = Boolean.valueOf(z);
            this.zzang = valueOf;
            if (valueOf.booleanValue()) {
                this.zzang = Boolean.valueOf(zzgc().zzcf(zzfw().getGmpAppId()));
            }
        }
        return this.zzang.booleanValue();
    }
}
