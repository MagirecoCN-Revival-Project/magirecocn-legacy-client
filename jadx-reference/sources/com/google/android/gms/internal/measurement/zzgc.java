package com.google.android.gms.internal.measurement;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import com.adjust.sdk.Constants;
import com.google.android.gms.common.internal.Preconditions;

/* loaded from: classes.dex */
public final class zzgc {
    private final zzgf zzalj;

    public zzgc(zzgf zzgfVar) {
        Preconditions.checkNotNull(zzgfVar);
        this.zzalj = zzgfVar;
    }

    public static boolean zza(Context context) {
        ActivityInfo receiverInfo;
        Preconditions.checkNotNull(context);
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager != null && (receiverInfo = packageManager.getReceiverInfo(new ComponentName(context, "com.google.android.gms.measurement.AppMeasurementReceiver"), 0)) != null) {
                if (receiverInfo.enabled) {
                    return true;
                }
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
        return false;
    }

    public final void onReceive(Context context, Intent intent) {
        zzgm zza = zzgm.zza(context, null, null);
        zzfh zzgf = zza.zzgf();
        if (intent == null) {
            zzgf.zziv().log("Receiver called with null intent");
            return;
        }
        zza.zzgi();
        String action = intent.getAction();
        zzgf.zziz().zzg("Local receiver got", action);
        if ("com.google.android.gms.measurement.UPLOAD".equals(action)) {
            Intent className = new Intent().setClassName(context, "com.google.android.gms.measurement.AppMeasurementService");
            className.setAction("com.google.android.gms.measurement.UPLOAD");
            zzgf.zziz().log("Starting wakeful intent.");
            this.zzalj.doStartService(context, className);
            return;
        }
        if ("com.android.vending.INSTALL_REFERRER".equals(action)) {
            try {
                zza.zzge().zzc(new zzgd(this, zza, zzgf));
            } catch (Exception e) {
                zzgf.zziv().zzg("Install Referrer Reporter encountered a problem", e);
            }
            BroadcastReceiver.PendingResult doGoAsync = this.zzalj.doGoAsync();
            String stringExtra = intent.getStringExtra(Constants.REFERRER);
            if (stringExtra == null) {
                zzgf.zziz().log("Install referrer extras are null");
                if (doGoAsync != null) {
                    doGoAsync.finish();
                    return;
                }
                return;
            }
            zzgf.zzix().zzg("Install referrer extras are", stringExtra);
            if (!stringExtra.contains("?")) {
                String valueOf = String.valueOf(stringExtra);
                stringExtra = valueOf.length() != 0 ? "?".concat(valueOf) : new String("?");
            }
            Bundle zza2 = zza.zzgc().zza(Uri.parse(stringExtra));
            if (zza2 == null) {
                zzgf.zziz().log("No campaign defined in install referrer broadcast");
                if (doGoAsync != null) {
                    doGoAsync.finish();
                    return;
                }
                return;
            }
            long longExtra = intent.getLongExtra("referrer_timestamp_seconds", 0L) * 1000;
            if (longExtra == 0) {
                zzgf.zziv().log("Install referrer is missing timestamp");
            }
            zza.zzge().zzc(new zzge(this, zza, longExtra, zza2, context, zzgf, doGoAsync));
        }
    }
}
