package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import com.adjust.sdk.Constants;
import java.util.HashMap;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* loaded from: classes.dex */
public final class zzcx {
    static final Map<String, String> zza = new HashMap();
    private static String zzb;

    public static String zza(String str, String str2) {
        if (str2 == null) {
            if (str.length() > 0) {
                return str;
            }
            return null;
        }
        String valueOf = String.valueOf(str);
        return Uri.parse(valueOf.length() != 0 ? "http://hostname/?".concat(valueOf) : new String("http://hostname/?")).getQueryParameter(str2);
    }

    public static String zzb(Context context, String str) {
        if (zzb == null) {
            synchronized (zzcx.class) {
                if (zzb == null) {
                    SharedPreferences sharedPreferences = context.getSharedPreferences("gtm_install_referrer", 0);
                    if (sharedPreferences != null) {
                        zzb = sharedPreferences.getString(Constants.REFERRER, "");
                    } else {
                        zzb = "";
                    }
                }
            }
        }
        return zza(zzb, str);
    }

    public static void zzc(Context context, String str) {
        String zza2 = zza(str, "conv");
        if (zza2 == null || zza2.length() <= 0) {
            return;
        }
        zza.put(zza2, str);
        zzfg.zza(context, "gtm_click_referrers", zza2, str);
    }

    public static void zzd(String str) {
        synchronized (zzcx.class) {
            zzb = str;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void zze(Context context, String str) {
        zzfg.zza(context, "gtm_install_referrer", Constants.REFERRER, str);
        zzc(context, str);
    }
}
