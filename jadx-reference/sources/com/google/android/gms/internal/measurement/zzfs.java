package com.google.android.gms.internal.measurement;

import android.content.SharedPreferences;
import android.os.Build;
import android.text.TextUtils;
import android.util.Pair;
import androidx.work.WorkRequest;
import backtraceio.library.services.BacktraceMetrics;
import com.adjust.sdk.Constants;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Locale;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzfs extends zzhi {
    static final Pair<String, Long> zzakb = new Pair<>("", 0L);
    private SharedPreferences zzabf;
    public zzfw zzakc;
    public final zzfv zzakd;
    public final zzfv zzake;
    public final zzfv zzakf;
    public final zzfv zzakg;
    public final zzfv zzakh;
    public final zzfv zzaki;
    public final zzfv zzakj;
    public final zzfx zzakk;
    private String zzakl;
    private boolean zzakm;
    private long zzakn;
    private String zzako;
    private long zzakp;
    private final Object zzakq;
    public final zzfv zzakr;
    public final zzfv zzaks;
    public final zzfu zzakt;
    public final zzfv zzaku;
    public final zzfv zzakv;
    public boolean zzakw;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzfs(zzgm zzgmVar) {
        super(zzgmVar);
        this.zzakd = new zzfv(this, "last_upload", 0L);
        this.zzake = new zzfv(this, "last_upload_attempt", 0L);
        this.zzakf = new zzfv(this, "backoff", 0L);
        this.zzakg = new zzfv(this, "last_delete_stale", 0L);
        this.zzakr = new zzfv(this, "time_before_start", WorkRequest.MIN_BACKOFF_MILLIS);
        this.zzaks = new zzfv(this, "session_timeout", BacktraceMetrics.defaultTimeIntervalMs);
        this.zzakt = new zzfu(this, "start_new_session", true);
        this.zzaku = new zzfv(this, "last_pause_time", 0L);
        this.zzakv = new zzfv(this, "time_active", 0L);
        this.zzakh = new zzfv(this, "midnight_offset", 0L);
        this.zzaki = new zzfv(this, "first_open_time", 0L);
        this.zzakj = new zzfv(this, "app_install_time", 0L);
        this.zzakk = new zzfx(this, "app_instance_id", null);
        this.zzakq = new Object();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final SharedPreferences zzjf() {
        zzab();
        zzch();
        return this.zzabf;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void setMeasurementEnabled(boolean z) {
        zzab();
        zzgf().zziz().zzg("Setting measurementEnabled", Boolean.valueOf(z));
        SharedPreferences.Editor edit = zzjf().edit();
        edit.putBoolean("measurement_enabled", z);
        edit.apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final Pair<String, Boolean> zzbn(String str) {
        zzab();
        long elapsedRealtime = zzbt().elapsedRealtime();
        if (this.zzakl != null && elapsedRealtime < this.zzakn) {
            return new Pair<>(this.zzakl, Boolean.valueOf(this.zzakm));
        }
        this.zzakn = elapsedRealtime + zzgh().zza(str, zzey.zzagq);
        AdvertisingIdClient.setShouldSkipGmsCoreVersionCheck(true);
        try {
            AdvertisingIdClient.Info advertisingIdInfo = AdvertisingIdClient.getAdvertisingIdInfo(getContext());
            if (advertisingIdInfo != null) {
                this.zzakl = advertisingIdInfo.getId();
                this.zzakm = advertisingIdInfo.isLimitAdTrackingEnabled();
            }
            if (this.zzakl == null) {
                this.zzakl = "";
            }
        } catch (Exception e) {
            zzgf().zziy().zzg("Unable to get advertising id", e);
            this.zzakl = "";
        }
        AdvertisingIdClient.setShouldSkipGmsCoreVersionCheck(false);
        return new Pair<>(this.zzakl, Boolean.valueOf(this.zzakm));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final String zzbo(String str) {
        zzab();
        String str2 = (String) zzbn(str).first;
        MessageDigest messageDigest = zzkc.getMessageDigest(Constants.MD5);
        if (messageDigest == null) {
            return null;
        }
        return String.format(Locale.US, "%032X", new BigInteger(1, messageDigest.digest(str2.getBytes())));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void zzbp(String str) {
        zzab();
        SharedPreferences.Editor edit = zzjf().edit();
        edit.putString("gmp_app_id", str);
        edit.apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void zzbq(String str) {
        synchronized (this.zzakq) {
            this.zzako = str;
            this.zzakp = zzbt().elapsedRealtime();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void zzf(boolean z) {
        zzab();
        zzgf().zziz().zzg("Setting useService", Boolean.valueOf(z));
        SharedPreferences.Editor edit = zzjf().edit();
        edit.putBoolean("use_service", z);
        edit.apply();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final boolean zzg(boolean z) {
        zzab();
        return zzjf().getBoolean("measurement_enabled", z);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void zzh(boolean z) {
        zzab();
        zzgf().zziz().zzg("Updating deferred analytics collection", Boolean.valueOf(z));
        SharedPreferences.Editor edit = zzjf().edit();
        edit.putBoolean("deferred_analytics_collection", z);
        edit.apply();
    }

    @Override // com.google.android.gms.internal.measurement.zzhi
    protected final boolean zzhh() {
        return true;
    }

    @Override // com.google.android.gms.internal.measurement.zzhi
    protected final void zzin() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("com.google.android.gms.measurement.prefs", 0);
        this.zzabf = sharedPreferences;
        boolean z = sharedPreferences.getBoolean("has_been_opened", false);
        this.zzakw = z;
        if (!z) {
            SharedPreferences.Editor edit = this.zzabf.edit();
            edit.putBoolean("has_been_opened", true);
            edit.apply();
        }
        this.zzakc = new zzfw(this, "health_monitor", Math.max(0L, zzey.zzagr.get().longValue()));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final String zzjg() {
        zzab();
        return zzjf().getString("gmp_app_id", null);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final String zzjh() {
        synchronized (this.zzakq) {
            if (Math.abs(zzbt().elapsedRealtime() - this.zzakp) >= 1000) {
                return null;
            }
            return this.zzako;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final Boolean zzji() {
        zzab();
        if (zzjf().contains("use_service")) {
            return Boolean.valueOf(zzjf().getBoolean("use_service", false));
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void zzjj() {
        zzab();
        zzgf().zziz().log("Clearing collection preferences.");
        boolean contains = zzjf().contains("measurement_enabled");
        boolean zzg = contains ? zzg(true) : true;
        SharedPreferences.Editor edit = zzjf().edit();
        edit.clear();
        edit.apply();
        if (contains) {
            setMeasurementEnabled(zzg);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final String zzjk() {
        zzab();
        String string = zzjf().getString("previous_os_version", null);
        zzfx().zzch();
        String str = Build.VERSION.RELEASE;
        if (!TextUtils.isEmpty(str) && !str.equals(string)) {
            SharedPreferences.Editor edit = zzjf().edit();
            edit.putString("previous_os_version", str);
            edit.apply();
        }
        return string;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final boolean zzjl() {
        zzab();
        return zzjf().getBoolean("deferred_analytics_collection", false);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final boolean zzjm() {
        return this.zzabf.contains("deferred_analytics_collection");
    }
}
