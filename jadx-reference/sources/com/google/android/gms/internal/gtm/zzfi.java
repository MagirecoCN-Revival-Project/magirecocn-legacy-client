package com.google.android.gms.internal.gtm;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.stats.WakeLock;

/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
public final class zzfi {
    static final Object zza = new Object();
    static WakeLock zzb;
    static Boolean zzc;

    public static boolean zza(Context context) {
        Preconditions.checkNotNull(context);
        Boolean bool = zzc;
        if (bool != null) {
            return bool.booleanValue();
        }
        boolean zzi = zzfs.zzi(context, "com.google.android.gms.analytics.AnalyticsReceiver", false);
        zzc = Boolean.valueOf(zzi);
        return zzi;
    }

    public static final void zzb(Context context, Intent intent) {
        zzbv zzg = zzbv.zzg(context);
        zzfb zzm = zzg.zzm();
        if (intent == null) {
            zzm.zzR("AnalyticsReceiver called with null intent");
            return;
        }
        String action = intent.getAction();
        zzg.zzj();
        zzm.zzP("Local AnalyticsReceiver got", action);
        if ("com.google.android.gms.analytics.ANALYTICS_DISPATCH".equals(action)) {
            boolean zzh = zzfn.zzh(context);
            Intent intent2 = new Intent("com.google.android.gms.analytics.ANALYTICS_DISPATCH");
            intent2.setComponent(new ComponentName(context, "com.google.android.gms.analytics.AnalyticsService"));
            intent2.setAction("com.google.android.gms.analytics.ANALYTICS_DISPATCH");
            synchronized (zza) {
                context.startService(intent2);
                if (zzh) {
                    try {
                        if (zzb == null) {
                            WakeLock wakeLock = new WakeLock(context, 1, "Analytics WakeLock");
                            zzb = wakeLock;
                            wakeLock.setReferenceCounted(false);
                        }
                        zzb.acquire(1000L);
                    } catch (SecurityException unused) {
                        zzm.zzR("Analytics service at risk of not starting. For more reliable analytics, add the WAKE_LOCK permission to your manifest. See http://goo.gl/8Rd3yj for instructions.");
                    }
                }
            }
        }
    }
}
