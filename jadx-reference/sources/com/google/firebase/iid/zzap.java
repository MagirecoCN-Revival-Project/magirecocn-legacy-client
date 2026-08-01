package com.google.firebase.iid;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.util.Log;
import androidx.collection.SimpleArrayMap;
import androidx.legacy.content.WakefulBroadcastReceiver;
import cn.thinkingdata.core.router.TRouterMap;
import cz.msebera.android.httpclient.HttpStatus;
import java.util.ArrayDeque;
import java.util.Queue;

/* loaded from: classes.dex */
public final class zzap {
    private static zzap zzcq;
    private final SimpleArrayMap<String, String> zzcr = new SimpleArrayMap<>();
    private Boolean zzcs = null;
    final Queue<Intent> zzct = new ArrayDeque();
    private final Queue<Intent> zzcu = new ArrayDeque();

    private zzap() {
    }

    public static PendingIntent zza(Context context, int i, Intent intent, int i2) {
        Intent intent2 = new Intent(context, (Class<?>) FirebaseInstanceIdReceiver.class);
        intent2.setAction("com.google.firebase.MESSAGING_EVENT");
        intent2.putExtra("wrapped_intent", intent);
        return PendingIntent.getBroadcast(context, i, intent2, 1073741824);
    }

    public static synchronized zzap zzac() {
        zzap zzapVar;
        synchronized (zzap.class) {
            if (zzcq == null) {
                zzcq = new zzap();
            }
            zzapVar = zzcq;
        }
        return zzapVar;
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x00db A[Catch: IllegalStateException -> 0x0110, SecurityException -> 0x0138, TryCatch #4 {IllegalStateException -> 0x0110, SecurityException -> 0x0138, blocks: (B:35:0x00d7, B:37:0x00db, B:40:0x00e4, B:41:0x00ea, B:43:0x00f2, B:45:0x0104, B:49:0x00f7), top: B:34:0x00d7 }] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00f2 A[Catch: IllegalStateException -> 0x0110, SecurityException -> 0x0138, TryCatch #4 {IllegalStateException -> 0x0110, SecurityException -> 0x0138, blocks: (B:35:0x00d7, B:37:0x00db, B:40:0x00e4, B:41:0x00ea, B:43:0x00f2, B:45:0x0104, B:49:0x00f7), top: B:34:0x00d7 }] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0104 A[Catch: IllegalStateException -> 0x0110, SecurityException -> 0x0138, TRY_LEAVE, TryCatch #4 {IllegalStateException -> 0x0110, SecurityException -> 0x0138, blocks: (B:35:0x00d7, B:37:0x00db, B:40:0x00e4, B:41:0x00ea, B:43:0x00f2, B:45:0x0104, B:49:0x00f7), top: B:34:0x00d7 }] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x010e A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00f7 A[Catch: IllegalStateException -> 0x0110, SecurityException -> 0x0138, TryCatch #4 {IllegalStateException -> 0x0110, SecurityException -> 0x0138, blocks: (B:35:0x00d7, B:37:0x00db, B:40:0x00e4, B:41:0x00ea, B:43:0x00f2, B:45:0x0104, B:49:0x00f7), top: B:34:0x00d7 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final int zzb(Context context, Intent intent) {
        String str;
        ComponentName startService;
        synchronized (this.zzcr) {
            str = this.zzcr.get(intent.getAction());
        }
        try {
            if (str == null) {
                ResolveInfo resolveService = context.getPackageManager().resolveService(intent, 0);
                if (resolveService == null || resolveService.serviceInfo == null) {
                    Log.e("FirebaseInstanceId", "Failed to resolve target intent service, skipping classname enforcement");
                } else {
                    ServiceInfo serviceInfo = resolveService.serviceInfo;
                    if (!context.getPackageName().equals(serviceInfo.packageName) || serviceInfo.name == null) {
                        String str2 = serviceInfo.packageName;
                        String str3 = serviceInfo.name;
                        StringBuilder sb = new StringBuilder(String.valueOf(str2).length() + 94 + String.valueOf(str3).length());
                        sb.append("Error resolving target intent service, skipping classname enforcement. Resolved service was: ");
                        sb.append(str2);
                        sb.append("/");
                        sb.append(str3);
                        Log.e("FirebaseInstanceId", sb.toString());
                    } else {
                        str = serviceInfo.name;
                        if (str.startsWith(TRouterMap.DOT)) {
                            String valueOf = String.valueOf(context.getPackageName());
                            String valueOf2 = String.valueOf(str);
                            str = valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
                        }
                        synchronized (this.zzcr) {
                            this.zzcr.put(intent.getAction(), str);
                        }
                    }
                }
                if (this.zzcs == null) {
                    this.zzcs = Boolean.valueOf(context.checkCallingOrSelfPermission("android.permission.WAKE_LOCK") == 0);
                }
                if (this.zzcs.booleanValue()) {
                    startService = context.startService(intent);
                    Log.d("FirebaseInstanceId", "Missing wake lock permission, service start may be delayed");
                } else {
                    startService = WakefulBroadcastReceiver.startWakefulService(context, intent);
                }
                if (startService == null) {
                    return -1;
                }
                Log.e("FirebaseInstanceId", "Error while delivering the message: ServiceIntent not found.");
                return HttpStatus.SC_NOT_FOUND;
            }
            if (this.zzcs == null) {
            }
            if (this.zzcs.booleanValue()) {
            }
            if (startService == null) {
            }
        } catch (IllegalStateException e) {
            String valueOf3 = String.valueOf(e);
            StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf3).length() + 45);
            sb2.append("Failed to start service while in background: ");
            sb2.append(valueOf3);
            Log.e("FirebaseInstanceId", sb2.toString());
            return HttpStatus.SC_PAYMENT_REQUIRED;
        } catch (SecurityException e2) {
            Log.e("FirebaseInstanceId", "Error while delivering the message to the serviceIntent", e2);
            return HttpStatus.SC_UNAUTHORIZED;
        }
        if (Log.isLoggable("FirebaseInstanceId", 3)) {
            String valueOf4 = String.valueOf(str);
            Log.d("FirebaseInstanceId", valueOf4.length() != 0 ? "Restricting intent to a specific service: ".concat(valueOf4) : new String("Restricting intent to a specific service: "));
        }
        intent.setClassName(context.getPackageName(), str);
    }

    public final int zza(Context context, String str, Intent intent) {
        Queue<Intent> queue;
        str.hashCode();
        if (str.equals("com.google.firebase.INSTANCE_ID_EVENT")) {
            queue = this.zzct;
        } else {
            if (!str.equals("com.google.firebase.MESSAGING_EVENT")) {
                String valueOf = String.valueOf(str);
                Log.w("FirebaseInstanceId", valueOf.length() != 0 ? "Unknown service action: ".concat(valueOf) : new String("Unknown service action: "));
                return HttpStatus.SC_INTERNAL_SERVER_ERROR;
            }
            queue = this.zzcu;
        }
        queue.offer(intent);
        Intent intent2 = new Intent(str);
        intent2.setPackage(context.getPackageName());
        return zzb(context, intent2);
    }

    public final Intent zzad() {
        return this.zzcu.poll();
    }
}
