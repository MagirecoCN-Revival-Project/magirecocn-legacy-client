package com.google.android.gms.analytics;

import android.net.Uri;
import android.text.TextUtils;
import com.adjust.sdk.Constants;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.gtm.zzbs;
import com.google.android.gms.internal.gtm.zzbv;
import com.google.android.gms.internal.gtm.zzez;
import com.google.android.gms.internal.gtm.zzfr;
import com.google.android.gms.internal.gtm.zzfs;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
public class Tracker extends zzbs {
    private boolean zza;
    private final Map<String, String> zzb;
    private final Map<String, String> zzc;
    private final zzez zzd;
    private final zzv zze;
    private ExceptionReporter zzf;
    private zzfr zzg;

    /* JADX INFO: Access modifiers changed from: package-private */
    public Tracker(zzbv zzbvVar, String str, zzez zzezVar) {
        super(zzbvVar);
        HashMap hashMap = new HashMap();
        this.zzb = hashMap;
        this.zzc = new HashMap();
        if (str != null) {
            hashMap.put("&tid", str);
        }
        hashMap.put("useSecure", "1");
        hashMap.put("&a", Integer.toString(new Random().nextInt(Integer.MAX_VALUE) + 1));
        this.zzd = new zzez(60, 2000L, "tracking", zzC());
        this.zze = new zzv(this, zzbvVar);
    }

    private static void zzZ(Map<String, String> map, Map<String, String> map2) {
        Preconditions.checkNotNull(map2);
        if (map == null) {
            return;
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String zzn = zzn(entry);
            if (zzn != null) {
                map2.put(zzn, entry.getValue());
            }
        }
    }

    private static String zzn(Map.Entry<String, String> entry) {
        String key = entry.getKey();
        if (!key.startsWith("&") || key.length() < 2) {
            return null;
        }
        return entry.getKey().substring(1);
    }

    public void enableAdvertisingIdCollection(boolean z) {
        this.zza = z;
    }

    public void enableAutoActivityTracking(boolean z) {
        this.zze.zzc(z);
    }

    public void enableExceptionReporting(boolean z) {
        synchronized (this) {
            ExceptionReporter exceptionReporter = this.zzf;
            if ((exceptionReporter != null) == z) {
                return;
            }
            if (!z) {
                Thread.setDefaultUncaughtExceptionHandler(exceptionReporter.zza());
                zzO("Uncaught exceptions will not be reported to Google Analytics");
            } else {
                ExceptionReporter exceptionReporter2 = new ExceptionReporter(this, Thread.getDefaultUncaughtExceptionHandler(), zzo());
                this.zzf = exceptionReporter2;
                Thread.setDefaultUncaughtExceptionHandler(exceptionReporter2);
                zzO("Uncaught exceptions will be reported to Google Analytics");
            }
        }
    }

    public String get(String str) {
        zzW();
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (this.zzb.containsKey(str)) {
            return this.zzb.get(str);
        }
        if (str.equals("&ul")) {
            return zzfs.zzd(Locale.getDefault());
        }
        if (str.equals("&cid")) {
            return zzv().zzb();
        }
        if (str.equals("&sr")) {
            return zzx().zzb();
        }
        if (str.equals("&aid")) {
            return zzu().zza().zzd();
        }
        if (str.equals("&an")) {
            return zzu().zza().zzf();
        }
        if (str.equals("&av")) {
            return zzu().zza().zzg();
        }
        if (str.equals("&aiid")) {
            return zzu().zza().zze();
        }
        return null;
    }

    public void send(Map<String, String> map) {
        long currentTimeMillis = zzC().currentTimeMillis();
        if (zzp().getAppOptOut()) {
            zzF("AppOptOut is set to true. Not sending Google Analytics hit");
            return;
        }
        boolean isDryRunEnabled = zzp().isDryRunEnabled();
        HashMap hashMap = new HashMap();
        zzZ(this.zzb, hashMap);
        zzZ(map, hashMap);
        String str = this.zzb.get("useSecure");
        int i = 1;
        boolean z = str == null || str.equalsIgnoreCase("true") || str.equalsIgnoreCase("yes") || str.equalsIgnoreCase("1") || !(str.equalsIgnoreCase("false") || str.equalsIgnoreCase("no") || str.equalsIgnoreCase("0"));
        Map<String, String> map2 = this.zzc;
        Preconditions.checkNotNull(hashMap);
        for (Map.Entry<String, String> entry : map2.entrySet()) {
            String zzn = zzn(entry);
            if (zzn != null && !hashMap.containsKey(zzn)) {
                hashMap.put(zzn, entry.getValue());
            }
        }
        this.zzc.clear();
        String str2 = hashMap.get("t");
        if (TextUtils.isEmpty(str2)) {
            zzz().zzc(hashMap, "Missing hit type parameter");
            return;
        }
        String str3 = hashMap.get("tid");
        if (!TextUtils.isEmpty(str3)) {
            boolean z2 = this.zza;
            synchronized (this) {
                if ("screenview".equalsIgnoreCase(str2) || "pageview".equalsIgnoreCase(str2) || "appview".equalsIgnoreCase(str2) || TextUtils.isEmpty(str2)) {
                    String str4 = this.zzb.get("&a");
                    Preconditions.checkNotNull(str4);
                    int parseInt = Integer.parseInt(str4) + 1;
                    if (parseInt < Integer.MAX_VALUE) {
                        i = parseInt;
                    }
                    this.zzb.put("&a", Integer.toString(i));
                }
            }
            zzq().zzi(new zzu(this, hashMap, z2, str2, currentTimeMillis, isDryRunEnabled, z, str3));
            return;
        }
        zzz().zzc(hashMap, "Missing tracking id parameter");
    }

    public void set(String str, String str2) {
        Preconditions.checkNotNull(str, "Key should be non-null");
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.zzb.put(str, str2);
    }

    public void setAnonymizeIp(boolean z) {
        set("&aip", zzfs.zzc(z));
    }

    public void setAppId(String str) {
        set("&aid", str);
    }

    public void setAppInstallerId(String str) {
        set("&aiid", str);
    }

    public void setAppName(String str) {
        set("&an", str);
    }

    public void setAppVersion(String str) {
        set("&av", str);
    }

    public void setCampaignParamsOnNextHit(Uri uri) {
        if (uri == null || uri.isOpaque()) {
            return;
        }
        String queryParameter = uri.getQueryParameter(Constants.REFERRER);
        if (TextUtils.isEmpty(queryParameter)) {
            return;
        }
        String valueOf = String.valueOf(queryParameter);
        Uri parse = Uri.parse(valueOf.length() != 0 ? "http://hostname/?".concat(valueOf) : new String("http://hostname/?"));
        String queryParameter2 = parse.getQueryParameter("utm_id");
        if (queryParameter2 != null) {
            this.zzc.put("&ci", queryParameter2);
        }
        String queryParameter3 = parse.getQueryParameter("anid");
        if (queryParameter3 != null) {
            this.zzc.put("&anid", queryParameter3);
        }
        String queryParameter4 = parse.getQueryParameter("utm_campaign");
        if (queryParameter4 != null) {
            this.zzc.put("&cn", queryParameter4);
        }
        String queryParameter5 = parse.getQueryParameter("utm_content");
        if (queryParameter5 != null) {
            this.zzc.put("&cc", queryParameter5);
        }
        String queryParameter6 = parse.getQueryParameter("utm_medium");
        if (queryParameter6 != null) {
            this.zzc.put("&cm", queryParameter6);
        }
        String queryParameter7 = parse.getQueryParameter("utm_source");
        if (queryParameter7 != null) {
            this.zzc.put("&cs", queryParameter7);
        }
        String queryParameter8 = parse.getQueryParameter("utm_term");
        if (queryParameter8 != null) {
            this.zzc.put("&ck", queryParameter8);
        }
        String queryParameter9 = parse.getQueryParameter("dclid");
        if (queryParameter9 != null) {
            this.zzc.put("&dclid", queryParameter9);
        }
        String queryParameter10 = parse.getQueryParameter("gclid");
        if (queryParameter10 != null) {
            this.zzc.put("&gclid", queryParameter10);
        }
        String queryParameter11 = parse.getQueryParameter(FirebaseAnalytics.Param.ACLID);
        if (queryParameter11 != null) {
            this.zzc.put("&aclid", queryParameter11);
        }
    }

    public void setClientId(String str) {
        set("&cid", str);
    }

    public void setEncoding(String str) {
        set("&de", str);
    }

    public void setHostname(String str) {
        set("&dh", str);
    }

    public void setLanguage(String str) {
        set("&ul", str);
    }

    public void setLocation(String str) {
        set("&dl", str);
    }

    public void setPage(String str) {
        set("&dp", str);
    }

    public void setReferrer(String str) {
        set("&dr", str);
    }

    public void setSampleRate(double d) {
        set("&sf", Double.toString(d));
    }

    public void setScreenColors(String str) {
        set("&sd", str);
    }

    public void setScreenName(String str) {
        set("&cd", str);
    }

    public void setSessionTimeout(long j) {
        this.zze.zze(j * 1000);
    }

    public void setTitle(String str) {
        set("&dt", str);
    }

    public void setUseSecure(boolean z) {
        set("useSecure", zzfs.zzc(z));
    }

    public void setViewportSize(String str) {
        set("&vp", str);
    }

    @Override // com.google.android.gms.internal.gtm.zzbs
    protected final void zzd() {
        this.zze.zzX();
        String zza = zzB().zza();
        if (zza != null) {
            set("&an", zza);
        }
        String zzb = zzB().zzb();
        if (zzb != null) {
            set("&av", zzb);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void zzm(zzfr zzfrVar) {
        zzO("Loading Tracker config values");
        this.zzg = zzfrVar;
        String str = zzfrVar.zza;
        if (str != null) {
            set("&tid", str);
            zzP("trackingId loaded", str);
        }
        double d = zzfrVar.zzb;
        if (d >= 0.0d) {
            String d2 = Double.toString(d);
            set("&sf", d2);
            zzP("Sample frequency loaded", d2);
        }
        int i = zzfrVar.zzc;
        if (i >= 0) {
            setSessionTimeout(i);
            zzP("Session timeout loaded", Integer.valueOf(i));
        }
        int i2 = zzfrVar.zzd;
        if (i2 != -1) {
            boolean z = 1 == i2;
            enableAutoActivityTracking(z);
            zzP("Auto activity tracking loaded", Boolean.valueOf(z));
        }
        int i3 = zzfrVar.zze;
        if (i3 != -1) {
            if (i3 != 0) {
                set("&aip", "1");
            }
            zzP("Anonymize ip loaded", Boolean.valueOf(1 == i3));
        }
        enableExceptionReporting(zzfrVar.zzf == 1);
    }

    public void setScreenResolution(int i, int i2) {
        if (i >= 0 || i2 >= 0) {
            StringBuilder sb = new StringBuilder(23);
            sb.append(i);
            sb.append("x");
            sb.append(i2);
            set("&sr", sb.toString());
            return;
        }
        zzR("Invalid width or height. The values should be non-negative.");
    }
}
