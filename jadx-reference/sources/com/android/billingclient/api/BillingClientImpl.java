package com.android.billingclient.api;

import android.R;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.view.View;
import androidx.core.app.BundleCompat;
import androidx.work.WorkRequest;
import com.android.billingclient.BuildConfig;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.QueryProductDetailsParams;
import com.google.android.gms.internal.play_billing.zzfl;
import com.google.android.gms.internal.play_billing.zzfm;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.json.JSONException;

/* compiled from: com.android.billingclient:billing@@5.2.1 */
/* loaded from: classes.dex */
public class BillingClientImpl extends BillingClient {
    private volatile int zza;
    private final String zzb;
    private final Handler zzc;
    private volatile zzo zzd;
    private Context zze;
    private volatile com.google.android.gms.internal.play_billing.zze zzf;
    private volatile zzap zzg;
    private boolean zzh;
    private boolean zzi;
    private int zzj;
    private boolean zzk;
    private boolean zzl;
    private boolean zzm;
    private boolean zzn;
    private boolean zzo;
    private boolean zzp;
    private boolean zzq;
    private boolean zzr;
    private boolean zzs;
    private boolean zzt;
    private boolean zzu;
    private boolean zzv;
    private boolean zzw;
    private boolean zzx;
    private ExecutorService zzy;
    private zzbh zzz;

    private BillingClientImpl(Activity activity, boolean z, boolean z2, String str) {
        this(activity.getApplicationContext(), z, z2, new zzat(), str, null, null);
    }

    private void initialize(Context context, PurchasesUpdatedListener purchasesUpdatedListener, boolean z, boolean z2, AlternativeBillingListener alternativeBillingListener, String str) {
        this.zze = context.getApplicationContext();
        zzfl zzu = zzfm.zzu();
        zzu.zzj(str);
        zzu.zzi(this.zze.getPackageName());
        this.zzz = new zzbh();
        if (purchasesUpdatedListener == null) {
            com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", "Billing client should have a valid listener but the provided is null.");
        }
        this.zzd = new zzo(this.zze, purchasesUpdatedListener, alternativeBillingListener, this.zzz);
        this.zzv = z;
        this.zzw = z2;
        this.zzx = alternativeBillingListener != null;
    }

    private int launchBillingFlowCpp(Activity activity, BillingFlowParams billingFlowParams) {
        return launchBillingFlow(activity, billingFlowParams).getResponseCode();
    }

    private void launchPriceChangeConfirmationFlow(Activity activity, PriceChangeFlowParams priceChangeFlowParams, long j) {
        launchPriceChangeConfirmationFlow(activity, priceChangeFlowParams, new zzat(j));
    }

    private void startConnection(long j) {
        zzat zzatVar = new zzat(j);
        if (isReady()) {
            com.google.android.gms.internal.play_billing.zzb.zzi("BillingClient", "Service connection is valid. No need to re-initialize.");
            zzatVar.onBillingSetupFinished(zzbc.zzl);
            return;
        }
        if (this.zza == 1) {
            com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", "Client is already in the process of connecting to billing service.");
            zzatVar.onBillingSetupFinished(zzbc.zzd);
            return;
        }
        if (this.zza == 3) {
            com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", "Client was already closed and can't be reused. Please create another instance.");
            zzatVar.onBillingSetupFinished(zzbc.zzm);
            return;
        }
        this.zza = 1;
        this.zzd.zze();
        com.google.android.gms.internal.play_billing.zzb.zzi("BillingClient", "Starting in-app billing setup.");
        this.zzg = new zzap(this, zzatVar, null);
        Intent intent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
        intent.setPackage("com.android.vending");
        List<ResolveInfo> queryIntentServices = this.zze.getPackageManager().queryIntentServices(intent, 0);
        if (queryIntentServices != null && !queryIntentServices.isEmpty()) {
            ResolveInfo resolveInfo = queryIntentServices.get(0);
            if (resolveInfo.serviceInfo != null) {
                String str = resolveInfo.serviceInfo.packageName;
                String str2 = resolveInfo.serviceInfo.name;
                if (!"com.android.vending".equals(str) || str2 == null) {
                    com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", "The device doesn't have valid Play Store.");
                } else {
                    ComponentName componentName = new ComponentName(str, str2);
                    Intent intent2 = new Intent(intent);
                    intent2.setComponent(componentName);
                    intent2.putExtra("playBillingLibraryVersion", this.zzb);
                    if (this.zze.bindService(intent2, this.zzg, 1)) {
                        com.google.android.gms.internal.play_billing.zzb.zzi("BillingClient", "Service was bonded successfully.");
                        return;
                    }
                    com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", "Connection to Billing service is blocked.");
                }
            }
        }
        this.zza = 0;
        com.google.android.gms.internal.play_billing.zzb.zzi("BillingClient", "Billing service unavailable on device.");
        zzatVar.onBillingSetupFinished(zzbc.zzc);
    }

    public final Handler zzH() {
        return Looper.myLooper() == null ? this.zzc : new Handler(Looper.myLooper());
    }

    private final BillingResult zzI(final BillingResult billingResult) {
        if (Thread.interrupted()) {
            return billingResult;
        }
        this.zzc.post(new Runnable() { // from class: com.android.billingclient.api.zzag
            @Override // java.lang.Runnable
            public final void run() {
                BillingClientImpl.this.zzG(billingResult);
            }
        });
        return billingResult;
    }

    public final BillingResult zzJ() {
        if (this.zza == 0 || this.zza == 3) {
            return zzbc.zzm;
        }
        return zzbc.zzj;
    }

    private static String zzK() {
        try {
            return (String) Class.forName("com.android.billingclient.ktx.BuildConfig").getField("VERSION_NAME").get(null);
        } catch (Exception unused) {
            return BuildConfig.VERSION_NAME;
        }
    }

    public final Future zzL(Callable callable, long j, final Runnable runnable, Handler handler) {
        if (this.zzy == null) {
            this.zzy = Executors.newFixedThreadPool(com.google.android.gms.internal.play_billing.zzb.zza, new zzal(this));
        }
        try {
            final Future submit = this.zzy.submit(callable);
            handler.postDelayed(new Runnable() { // from class: com.android.billingclient.api.zzaf
                @Override // java.lang.Runnable
                public final void run() {
                    Future future = submit;
                    Runnable runnable2 = runnable;
                    if (future.isDone() || future.isCancelled()) {
                        return;
                    }
                    future.cancel(true);
                    com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", "Async task is taking too long, cancel it!");
                    if (runnable2 != null) {
                        runnable2.run();
                    }
                }
            }, (long) (j * 0.95d));
            return submit;
        } catch (Exception e) {
            com.google.android.gms.internal.play_billing.zzb.zzk("BillingClient", "Async task throws exception!", e);
            return null;
        }
    }

    private final void zzM(final BillingResult billingResult, final PriceChangeConfirmationListener priceChangeConfirmationListener) {
        if (Thread.interrupted()) {
            return;
        }
        this.zzc.post(new Runnable() { // from class: com.android.billingclient.api.zzx
            @Override // java.lang.Runnable
            public final void run() {
                PriceChangeConfirmationListener.this.onPriceChangeConfirmationResult(billingResult);
            }
        });
    }

    private final void zzN(String str, final PurchaseHistoryResponseListener purchaseHistoryResponseListener) {
        if (!isReady()) {
            purchaseHistoryResponseListener.onPurchaseHistoryResponse(zzbc.zzm, null);
        } else if (zzL(new zzaj(this, str, purchaseHistoryResponseListener), WorkRequest.DEFAULT_BACKOFF_DELAY_MILLIS, new Runnable() { // from class: com.android.billingclient.api.zzw
            @Override // java.lang.Runnable
            public final void run() {
                PurchaseHistoryResponseListener.this.onPurchaseHistoryResponse(zzbc.zzn, null);
            }
        }, zzH()) == null) {
            purchaseHistoryResponseListener.onPurchaseHistoryResponse(zzJ(), null);
        }
    }

    private final void zzO(String str, final PurchasesResponseListener purchasesResponseListener) {
        if (!isReady()) {
            purchasesResponseListener.onQueryPurchasesResponse(zzbc.zzm, com.google.android.gms.internal.play_billing.zzu.zzk());
            return;
        }
        if (TextUtils.isEmpty(str)) {
            com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", "Please provide a valid product type.");
            purchasesResponseListener.onQueryPurchasesResponse(zzbc.zzg, com.google.android.gms.internal.play_billing.zzu.zzk());
        } else if (zzL(new zzai(this, str, purchasesResponseListener), WorkRequest.DEFAULT_BACKOFF_DELAY_MILLIS, new Runnable() { // from class: com.android.billingclient.api.zzad
            @Override // java.lang.Runnable
            public final void run() {
                PurchasesResponseListener.this.onQueryPurchasesResponse(zzbc.zzn, com.google.android.gms.internal.play_billing.zzu.zzk());
            }
        }, zzH()) == null) {
            purchasesResponseListener.onQueryPurchasesResponse(zzJ(), com.google.android.gms.internal.play_billing.zzu.zzk());
        }
    }

    private final boolean zzP() {
        return this.zzu && this.zzw;
    }

    public static /* bridge */ /* synthetic */ zzas zzg(BillingClientImpl billingClientImpl, String str) {
        com.google.android.gms.internal.play_billing.zzb.zzi("BillingClient", "Querying purchase history, item type: ".concat(String.valueOf(str)));
        ArrayList arrayList = new ArrayList();
        Bundle zzc = com.google.android.gms.internal.play_billing.zzb.zzc(billingClientImpl.zzm, billingClientImpl.zzu, billingClientImpl.zzv, billingClientImpl.zzw, billingClientImpl.zzb);
        String str2 = null;
        while (billingClientImpl.zzk) {
            try {
                Bundle zzh = billingClientImpl.zzf.zzh(6, billingClientImpl.zze.getPackageName(), str, str2, zzc);
                BillingResult zza = zzbl.zza(zzh, "BillingClient", "getPurchaseHistory()");
                if (zza != zzbc.zzl) {
                    return new zzas(zza, null);
                }
                ArrayList<String> stringArrayList = zzh.getStringArrayList("INAPP_PURCHASE_ITEM_LIST");
                ArrayList<String> stringArrayList2 = zzh.getStringArrayList("INAPP_PURCHASE_DATA_LIST");
                ArrayList<String> stringArrayList3 = zzh.getStringArrayList("INAPP_DATA_SIGNATURE_LIST");
                for (int i = 0; i < stringArrayList2.size(); i++) {
                    String str3 = stringArrayList2.get(i);
                    String str4 = stringArrayList3.get(i);
                    com.google.android.gms.internal.play_billing.zzb.zzi("BillingClient", "Purchase record found for sku : ".concat(String.valueOf(stringArrayList.get(i))));
                    try {
                        PurchaseHistoryRecord purchaseHistoryRecord = new PurchaseHistoryRecord(str3, str4);
                        if (TextUtils.isEmpty(purchaseHistoryRecord.getPurchaseToken())) {
                            com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", "BUG: empty/null token!");
                        }
                        arrayList.add(purchaseHistoryRecord);
                    } catch (JSONException e) {
                        com.google.android.gms.internal.play_billing.zzb.zzk("BillingClient", "Got an exception trying to decode the purchase!", e);
                        return new zzas(zzbc.zzj, null);
                    }
                }
                str2 = zzh.getString("INAPP_CONTINUATION_TOKEN");
                com.google.android.gms.internal.play_billing.zzb.zzi("BillingClient", "Continuation token: ".concat(String.valueOf(str2)));
                if (TextUtils.isEmpty(str2)) {
                    return new zzas(zzbc.zzl, arrayList);
                }
            } catch (RemoteException e2) {
                com.google.android.gms.internal.play_billing.zzb.zzk("BillingClient", "Got exception trying to get purchase history, try to reconnect", e2);
                return new zzas(zzbc.zzm, null);
            }
        }
        com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", "getPurchaseHistory is not supported on current device");
        return new zzas(zzbc.zzq, null);
    }

    public static /* bridge */ /* synthetic */ zzbk zzi(BillingClientImpl billingClientImpl, String str) {
        Bundle zzi;
        com.google.android.gms.internal.play_billing.zzb.zzi("BillingClient", "Querying owned items, item type: ".concat(String.valueOf(str)));
        ArrayList arrayList = new ArrayList();
        Bundle zzc = com.google.android.gms.internal.play_billing.zzb.zzc(billingClientImpl.zzm, billingClientImpl.zzu, billingClientImpl.zzv, billingClientImpl.zzw, billingClientImpl.zzb);
        String str2 = null;
        do {
            try {
                if (billingClientImpl.zzm) {
                    zzi = billingClientImpl.zzf.zzj(true != billingClientImpl.zzu ? 9 : 19, billingClientImpl.zze.getPackageName(), str, str2, zzc);
                } else {
                    zzi = billingClientImpl.zzf.zzi(3, billingClientImpl.zze.getPackageName(), str, str2);
                }
                BillingResult zza = zzbl.zza(zzi, "BillingClient", "getPurchase()");
                if (zza != zzbc.zzl) {
                    return new zzbk(zza, null);
                }
                ArrayList<String> stringArrayList = zzi.getStringArrayList("INAPP_PURCHASE_ITEM_LIST");
                ArrayList<String> stringArrayList2 = zzi.getStringArrayList("INAPP_PURCHASE_DATA_LIST");
                ArrayList<String> stringArrayList3 = zzi.getStringArrayList("INAPP_DATA_SIGNATURE_LIST");
                for (int i = 0; i < stringArrayList2.size(); i++) {
                    String str3 = stringArrayList2.get(i);
                    String str4 = stringArrayList3.get(i);
                    com.google.android.gms.internal.play_billing.zzb.zzi("BillingClient", "Sku is owned: ".concat(String.valueOf(stringArrayList.get(i))));
                    try {
                        Purchase purchase = new Purchase(str3, str4);
                        if (TextUtils.isEmpty(purchase.getPurchaseToken())) {
                            com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", "BUG: empty/null token!");
                        }
                        arrayList.add(purchase);
                    } catch (JSONException e) {
                        com.google.android.gms.internal.play_billing.zzb.zzk("BillingClient", "Got an exception trying to decode the purchase!", e);
                        return new zzbk(zzbc.zzj, null);
                    }
                }
                str2 = zzi.getString("INAPP_CONTINUATION_TOKEN");
                com.google.android.gms.internal.play_billing.zzb.zzi("BillingClient", "Continuation token: ".concat(String.valueOf(str2)));
            } catch (Exception e2) {
                com.google.android.gms.internal.play_billing.zzb.zzk("BillingClient", "Got exception trying to get purchasesm try to reconnect", e2);
                return new zzbk(zzbc.zzm, null);
            }
        } while (!TextUtils.isEmpty(str2));
        return new zzbk(zzbc.zzl, arrayList);
    }

    @Override // com.android.billingclient.api.BillingClient
    public final void acknowledgePurchase(final AcknowledgePurchaseParams acknowledgePurchaseParams, final AcknowledgePurchaseResponseListener acknowledgePurchaseResponseListener) {
        if (!isReady()) {
            acknowledgePurchaseResponseListener.onAcknowledgePurchaseResponse(zzbc.zzm);
            return;
        }
        if (TextUtils.isEmpty(acknowledgePurchaseParams.getPurchaseToken())) {
            com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", "Please provide a valid purchase token.");
            acknowledgePurchaseResponseListener.onAcknowledgePurchaseResponse(zzbc.zzi);
        } else if (!this.zzm) {
            acknowledgePurchaseResponseListener.onAcknowledgePurchaseResponse(zzbc.zzb);
        } else if (zzL(new Callable() { // from class: com.android.billingclient.api.zzz
            @Override // java.util.concurrent.Callable
            public final Object call() {
                BillingClientImpl.this.zzk(acknowledgePurchaseParams, acknowledgePurchaseResponseListener);
                return null;
            }
        }, WorkRequest.DEFAULT_BACKOFF_DELAY_MILLIS, new Runnable() { // from class: com.android.billingclient.api.zzaa
            @Override // java.lang.Runnable
            public final void run() {
                AcknowledgePurchaseResponseListener.this.onAcknowledgePurchaseResponse(zzbc.zzn);
            }
        }, zzH()) == null) {
            acknowledgePurchaseResponseListener.onAcknowledgePurchaseResponse(zzJ());
        }
    }

    @Override // com.android.billingclient.api.BillingClient
    public final void consumeAsync(final ConsumeParams consumeParams, final ConsumeResponseListener consumeResponseListener) {
        if (!isReady()) {
            consumeResponseListener.onConsumeResponse(zzbc.zzm, consumeParams.getPurchaseToken());
        } else if (zzL(new Callable() { // from class: com.android.billingclient.api.zzu
            @Override // java.util.concurrent.Callable
            public final Object call() {
                BillingClientImpl.this.zzl(consumeParams, consumeResponseListener);
                return null;
            }
        }, WorkRequest.DEFAULT_BACKOFF_DELAY_MILLIS, new Runnable() { // from class: com.android.billingclient.api.zzv
            @Override // java.lang.Runnable
            public final void run() {
                ConsumeResponseListener.this.onConsumeResponse(zzbc.zzn, consumeParams.getPurchaseToken());
            }
        }, zzH()) == null) {
            consumeResponseListener.onConsumeResponse(zzJ(), consumeParams.getPurchaseToken());
        }
    }

    @Override // com.android.billingclient.api.BillingClient
    public final void endConnection() {
        try {
            this.zzd.zzd();
            if (this.zzg != null) {
                this.zzg.zzc();
            }
            if (this.zzg != null && this.zzf != null) {
                com.google.android.gms.internal.play_billing.zzb.zzi("BillingClient", "Unbinding from service.");
                this.zze.unbindService(this.zzg);
                this.zzg = null;
            }
            this.zzf = null;
            ExecutorService executorService = this.zzy;
            if (executorService != null) {
                executorService.shutdownNow();
                this.zzy = null;
            }
        } catch (Exception e) {
            com.google.android.gms.internal.play_billing.zzb.zzk("BillingClient", "There was an exception while ending connection!", e);
        } finally {
            this.zza = 3;
        }
    }

    @Override // com.android.billingclient.api.BillingClient
    public final int getConnectionState() {
        return this.zza;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.android.billingclient.api.BillingClient
    public final BillingResult isFeatureSupported(String str) {
        char c;
        if (!isReady()) {
            return zzbc.zzm;
        }
        switch (str.hashCode()) {
            case -422092961:
                if (str.equals(BillingClient.FeatureType.SUBSCRIPTIONS_UPDATE)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 96321:
                if (str.equals("aaa")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 97314:
                if (str.equals(BillingClient.FeatureType.IN_APP_MESSAGING)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 98307:
                if (str.equals("ccc")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 99300:
                if (str.equals("ddd")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 100293:
                if (str.equals("eee")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 101286:
                if (str.equals(BillingClient.FeatureType.PRODUCT_DETAILS)) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case 102279:
                if (str.equals("ggg")) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case 103272:
                if (str.equals("hhh")) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case 207616302:
                if (str.equals(BillingClient.FeatureType.PRICE_CHANGE_CONFIRMATION)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 1987365622:
                if (str.equals(BillingClient.FeatureType.SUBSCRIPTIONS)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                if (this.zzh) {
                    return zzbc.zzl;
                }
                return zzbc.zzo;
            case 1:
                if (this.zzi) {
                    return zzbc.zzl;
                }
                return zzbc.zzp;
            case 2:
                if (this.zzl) {
                    return zzbc.zzl;
                }
                return zzbc.zzr;
            case 3:
                return this.zzo ? zzbc.zzl : zzbc.zzw;
            case 4:
                return this.zzq ? zzbc.zzl : zzbc.zzs;
            case 5:
                return this.zzp ? zzbc.zzl : zzbc.zzu;
            case 6:
            case 7:
                return this.zzr ? zzbc.zzl : zzbc.zzt;
            case '\b':
                return this.zzs ? zzbc.zzl : zzbc.zzv;
            case '\t':
                if (this.zzt) {
                    return zzbc.zzl;
                }
                return zzbc.zzz;
            case '\n':
                if (this.zzt) {
                    return zzbc.zzl;
                }
                return zzbc.zzA;
            default:
                com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", "Unsupported feature: ".concat(String.valueOf(str)));
                return zzbc.zzy;
        }
    }

    @Override // com.android.billingclient.api.BillingClient
    public final boolean isReady() {
        return (this.zza != 2 || this.zzf == null || this.zzg == null) ? false : true;
    }

    /* JADX WARN: Removed duplicated region for block: B:120:0x03b4  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x03bf  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x03fc  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x0462 A[Catch: Exception -> 0x04a7, CancellationException -> 0x04ba, TimeoutException -> 0x04bc, TryCatch #4 {CancellationException -> 0x04ba, TimeoutException -> 0x04bc, Exception -> 0x04a7, blocks: (B:132:0x0450, B:134:0x0462, B:136:0x048d), top: B:131:0x0450 }] */
    /* JADX WARN: Removed duplicated region for block: B:136:0x048d A[Catch: Exception -> 0x04a7, CancellationException -> 0x04ba, TimeoutException -> 0x04bc, TRY_LEAVE, TryCatch #4 {CancellationException -> 0x04ba, TimeoutException -> 0x04bc, Exception -> 0x04a7, blocks: (B:132:0x0450, B:134:0x0462, B:136:0x048d), top: B:131:0x0450 }] */
    /* JADX WARN: Removed duplicated region for block: B:148:0x040b A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:152:0x0416  */
    /* JADX WARN: Removed duplicated region for block: B:153:0x0419  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x03c7  */
    @Override // com.android.billingclient.api.BillingClient
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final BillingResult launchBillingFlow(Activity activity, final BillingFlowParams billingFlowParams) {
        final String productId;
        final String productType;
        String str;
        String str2;
        Future zzL;
        int zzb;
        boolean z;
        String str3;
        SkuDetails skuDetails;
        BillingFlowParams.ProductDetailsParams productDetailsParams;
        String str4;
        String str5;
        String str6;
        boolean z2;
        Intent intent;
        String str7;
        final int i;
        BillingFlowParams.ProductDetailsParams productDetailsParams2;
        BillingClientImpl billingClientImpl = this;
        if (!isReady()) {
            zzba.zza(2, 2, zzbc.zzm);
            BillingResult billingResult = zzbc.zzm;
            billingClientImpl.zzI(billingResult);
            return billingResult;
        }
        ArrayList<SkuDetails> zzf = billingFlowParams.zzf();
        List zzg = billingFlowParams.zzg();
        SkuDetails skuDetails2 = (SkuDetails) com.google.android.gms.internal.play_billing.zzz.zza(zzf, null);
        BillingFlowParams.ProductDetailsParams productDetailsParams3 = (BillingFlowParams.ProductDetailsParams) com.google.android.gms.internal.play_billing.zzz.zza(zzg, null);
        if (skuDetails2 != null) {
            productId = skuDetails2.getSku();
            productType = skuDetails2.getType();
        } else {
            productId = productDetailsParams3.zza().getProductId();
            productType = productDetailsParams3.zza().getProductType();
        }
        if (!productType.equals("subs") || billingClientImpl.zzh) {
            if (!billingFlowParams.zzp() || billingClientImpl.zzk) {
                if (zzf.size() <= 1 || billingClientImpl.zzr) {
                    if (zzg.isEmpty() || billingClientImpl.zzs) {
                        if (billingClientImpl.zzk) {
                            boolean z3 = billingClientImpl.zzm;
                            boolean z4 = billingClientImpl.zzu;
                            boolean z5 = billingClientImpl.zzv;
                            boolean z6 = billingClientImpl.zzw;
                            boolean z7 = billingClientImpl.zzx;
                            str = "BUY_INTENT";
                            String str8 = billingClientImpl.zzb;
                            final Bundle bundle = new Bundle();
                            final String str9 = productType;
                            bundle.putString("playBillingLibraryVersion", str8);
                            if (billingFlowParams.zza() != 0) {
                                bundle.putInt("prorationMode", billingFlowParams.zza());
                            }
                            if (!TextUtils.isEmpty(billingFlowParams.zzb())) {
                                bundle.putString(BillingFlowParams.EXTRA_PARAM_KEY_ACCOUNT_ID, billingFlowParams.zzb());
                            }
                            if (!TextUtils.isEmpty(billingFlowParams.zzc())) {
                                bundle.putString("obfuscatedProfileId", billingFlowParams.zzc());
                            }
                            if (billingFlowParams.zzo()) {
                                bundle.putBoolean("isOfferPersonalizedByDeveloper", true);
                            }
                            if (!TextUtils.isEmpty(null)) {
                                bundle.putStringArrayList("skusToReplace", new ArrayList<>(Arrays.asList(null)));
                            }
                            if (!TextUtils.isEmpty(billingFlowParams.zzd())) {
                                bundle.putString("oldSkuPurchaseToken", billingFlowParams.zzd());
                            }
                            String str10 = null;
                            if (!TextUtils.isEmpty(null)) {
                                bundle.putString("oldSkuPurchaseId", null);
                            }
                            if (!TextUtils.isEmpty(billingFlowParams.zze())) {
                                bundle.putString("originalExternalTransactionId", billingFlowParams.zze());
                                str10 = null;
                            }
                            if (!TextUtils.isEmpty(str10)) {
                                bundle.putString("paymentsPurchaseParams", str10);
                            }
                            if (z3 && z5) {
                                z = true;
                                bundle.putBoolean("enablePendingPurchases", true);
                            } else {
                                z = true;
                            }
                            if (z4 && z6) {
                                bundle.putBoolean("enablePendingPurchaseForSubscriptions", z);
                            }
                            if (z7) {
                                bundle.putBoolean("enableAlternativeBilling", z);
                            }
                            if (!zzf.isEmpty()) {
                                ArrayList<String> arrayList = new ArrayList<>();
                                ArrayList<String> arrayList2 = new ArrayList<>();
                                str4 = productId;
                                ArrayList<String> arrayList3 = new ArrayList<>();
                                str3 = "proxyPackageVersion";
                                ArrayList<Integer> arrayList4 = new ArrayList<>();
                                str5 = "BillingClient";
                                ArrayList<String> arrayList5 = new ArrayList<>();
                                boolean z8 = false;
                                boolean z9 = false;
                                boolean z10 = false;
                                boolean z11 = false;
                                for (SkuDetails skuDetails3 : zzf) {
                                    if (skuDetails3.zzf().isEmpty()) {
                                        productDetailsParams2 = productDetailsParams3;
                                    } else {
                                        productDetailsParams2 = productDetailsParams3;
                                        arrayList.add(skuDetails3.zzf());
                                    }
                                    String zzc = skuDetails3.zzc();
                                    SkuDetails skuDetails4 = skuDetails2;
                                    String zzb2 = skuDetails3.zzb();
                                    int zza = skuDetails3.zza();
                                    String zze = skuDetails3.zze();
                                    arrayList2.add(zzc);
                                    z8 |= !TextUtils.isEmpty(zzc);
                                    arrayList3.add(zzb2);
                                    z9 |= !TextUtils.isEmpty(zzb2);
                                    arrayList4.add(Integer.valueOf(zza));
                                    z10 |= zza != 0;
                                    z11 |= !TextUtils.isEmpty(zze);
                                    arrayList5.add(zze);
                                    productDetailsParams3 = productDetailsParams2;
                                    skuDetails2 = skuDetails4;
                                }
                                skuDetails = skuDetails2;
                                productDetailsParams = productDetailsParams3;
                                if (!arrayList.isEmpty()) {
                                    bundle.putStringArrayList("skuDetailsTokens", arrayList);
                                }
                                if (z8) {
                                    bundle.putStringArrayList("SKU_OFFER_ID_TOKEN_LIST", arrayList2);
                                }
                                if (z9) {
                                    bundle.putStringArrayList("SKU_OFFER_ID_LIST", arrayList3);
                                }
                                if (z10) {
                                    bundle.putIntegerArrayList("SKU_OFFER_TYPE_LIST", arrayList4);
                                }
                                if (z11) {
                                    bundle.putStringArrayList("SKU_SERIALIZED_DOCID_LIST", arrayList5);
                                }
                                if (zzf.size() > 1) {
                                    ArrayList<String> arrayList6 = new ArrayList<>(zzf.size() - 1);
                                    ArrayList<String> arrayList7 = new ArrayList<>(zzf.size() - 1);
                                    for (int i2 = 1; i2 < zzf.size(); i2++) {
                                        arrayList6.add(((SkuDetails) zzf.get(i2)).getSku());
                                        arrayList7.add(((SkuDetails) zzf.get(i2)).getType());
                                    }
                                    bundle.putStringArrayList("additionalSkus", arrayList6);
                                    bundle.putStringArrayList("additionalSkuTypes", arrayList7);
                                }
                            } else {
                                str3 = "proxyPackageVersion";
                                skuDetails = skuDetails2;
                                productDetailsParams = productDetailsParams3;
                                str4 = productId;
                                str5 = "BillingClient";
                                ArrayList<String> arrayList8 = new ArrayList<>(zzg.size() - 1);
                                ArrayList<String> arrayList9 = new ArrayList<>(zzg.size() - 1);
                                ArrayList<String> arrayList10 = new ArrayList<>();
                                ArrayList<String> arrayList11 = new ArrayList<>();
                                ArrayList<String> arrayList12 = new ArrayList<>();
                                for (int i3 = 0; i3 < zzg.size(); i3++) {
                                    BillingFlowParams.ProductDetailsParams productDetailsParams4 = (BillingFlowParams.ProductDetailsParams) zzg.get(i3);
                                    ProductDetails zza2 = productDetailsParams4.zza();
                                    if (!zza2.zzb().isEmpty()) {
                                        arrayList10.add(zza2.zzb());
                                    }
                                    arrayList11.add(productDetailsParams4.zzb());
                                    if (!TextUtils.isEmpty(zza2.zzc())) {
                                        arrayList12.add(zza2.zzc());
                                    }
                                    if (i3 > 0) {
                                        arrayList8.add(((BillingFlowParams.ProductDetailsParams) zzg.get(i3)).zza().getProductId());
                                        arrayList9.add(((BillingFlowParams.ProductDetailsParams) zzg.get(i3)).zza().getProductType());
                                    }
                                }
                                bundle.putStringArrayList("SKU_OFFER_ID_TOKEN_LIST", arrayList11);
                                if (!arrayList10.isEmpty()) {
                                    bundle.putStringArrayList("skuDetailsTokens", arrayList10);
                                }
                                if (!arrayList12.isEmpty()) {
                                    bundle.putStringArrayList("SKU_SERIALIZED_DOCID_LIST", arrayList12);
                                }
                                if (!arrayList8.isEmpty()) {
                                    bundle.putStringArrayList("additionalSkus", arrayList8);
                                    bundle.putStringArrayList("additionalSkuTypes", arrayList9);
                                }
                            }
                            billingClientImpl = this;
                            if (!bundle.containsKey("SKU_OFFER_ID_TOKEN_LIST") || billingClientImpl.zzp) {
                                if (skuDetails == null || TextUtils.isEmpty(skuDetails.zzd())) {
                                    if (productDetailsParams != null && !TextUtils.isEmpty(productDetailsParams.zza().zza())) {
                                        bundle.putString("skuPackageName", productDetailsParams.zza().zza());
                                    } else {
                                        str6 = null;
                                        z2 = false;
                                        if (!TextUtils.isEmpty(str6)) {
                                            bundle.putString("accountName", str6);
                                        }
                                        intent = activity.getIntent();
                                        if (intent != null) {
                                            str2 = str5;
                                            com.google.android.gms.internal.play_billing.zzb.zzj(str2, "Activity's intent is null.");
                                        } else {
                                            str2 = str5;
                                            if (!TextUtils.isEmpty(intent.getStringExtra("PROXY_PACKAGE"))) {
                                                String stringExtra = intent.getStringExtra("PROXY_PACKAGE");
                                                bundle.putString("proxyPackage", stringExtra);
                                                try {
                                                    str7 = str3;
                                                    try {
                                                        bundle.putString(str7, billingClientImpl.zze.getPackageManager().getPackageInfo(stringExtra, 0).versionName);
                                                    } catch (PackageManager.NameNotFoundException unused) {
                                                        bundle.putString(str7, "package not found");
                                                        if (billingClientImpl.zzs) {
                                                        }
                                                        if (billingClientImpl.zzq) {
                                                        }
                                                        final String str11 = str4;
                                                        zzL = zzL(new Callable() { // from class: com.android.billingclient.api.zzab
                                                            @Override // java.util.concurrent.Callable
                                                            public final Object call() {
                                                                return BillingClientImpl.this.zzc(i, str11, str9, billingFlowParams, bundle);
                                                            }
                                                        }, 5000L, null, billingClientImpl.zzc);
                                                        Bundle bundle2 = (Bundle) zzL.get(5000L, TimeUnit.MILLISECONDS);
                                                        zzb = com.google.android.gms.internal.play_billing.zzb.zzb(bundle2, str2);
                                                        String zzf2 = com.google.android.gms.internal.play_billing.zzb.zzf(bundle2, str2);
                                                        if (zzb == 0) {
                                                        }
                                                    }
                                                } catch (PackageManager.NameNotFoundException unused2) {
                                                    str7 = str3;
                                                }
                                            }
                                        }
                                        if (billingClientImpl.zzs || zzg.isEmpty()) {
                                            i = (billingClientImpl.zzq || !z2) ? billingClientImpl.zzm ? 9 : 6 : 15;
                                        } else {
                                            i = 17;
                                        }
                                        final String str112 = str4;
                                        zzL = zzL(new Callable() { // from class: com.android.billingclient.api.zzab
                                            @Override // java.util.concurrent.Callable
                                            public final Object call() {
                                                return BillingClientImpl.this.zzc(i, str112, str9, billingFlowParams, bundle);
                                            }
                                        }, 5000L, null, billingClientImpl.zzc);
                                    }
                                } else {
                                    bundle.putString("skuPackageName", skuDetails.zzd());
                                }
                                str6 = null;
                                z2 = true;
                                if (!TextUtils.isEmpty(str6)) {
                                }
                                intent = activity.getIntent();
                                if (intent != null) {
                                }
                                if (billingClientImpl.zzs) {
                                }
                                if (billingClientImpl.zzq) {
                                }
                                final String str1122 = str4;
                                zzL = zzL(new Callable() { // from class: com.android.billingclient.api.zzab
                                    @Override // java.util.concurrent.Callable
                                    public final Object call() {
                                        return BillingClientImpl.this.zzc(i, str1122, str9, billingFlowParams, bundle);
                                    }
                                }, 5000L, null, billingClientImpl.zzc);
                            } else {
                                zzba.zza(21, 2, zzbc.zzu);
                                BillingResult billingResult2 = zzbc.zzu;
                                billingClientImpl.zzI(billingResult2);
                                return billingResult2;
                            }
                        } else {
                            str = "BUY_INTENT";
                            str2 = "BillingClient";
                            zzL = zzL(new Callable() { // from class: com.android.billingclient.api.zzac
                                @Override // java.util.concurrent.Callable
                                public final Object call() {
                                    return BillingClientImpl.this.zzd(productId, productType);
                                }
                            }, 5000L, null, billingClientImpl.zzc);
                        }
                        try {
                            Bundle bundle22 = (Bundle) zzL.get(5000L, TimeUnit.MILLISECONDS);
                            zzb = com.google.android.gms.internal.play_billing.zzb.zzb(bundle22, str2);
                            String zzf22 = com.google.android.gms.internal.play_billing.zzb.zzf(bundle22, str2);
                            if (zzb == 0) {
                                com.google.android.gms.internal.play_billing.zzb.zzj(str2, "Unable to buy item, Error response code: " + zzb);
                                BillingResult.Builder newBuilder = BillingResult.newBuilder();
                                newBuilder.setResponseCode(zzb);
                                newBuilder.setDebugMessage(zzf22);
                                BillingResult build = newBuilder.build();
                                zzba.zza(3, 2, build);
                                billingClientImpl.zzI(build);
                                return build;
                            }
                            Intent intent2 = new Intent(activity, (Class<?>) ProxyBillingActivity.class);
                            String str12 = str;
                            intent2.putExtra(str12, (PendingIntent) bundle22.getParcelable(str12));
                            activity.startActivity(intent2);
                            return zzbc.zzl;
                        } catch (CancellationException e) {
                            e = e;
                            com.google.android.gms.internal.play_billing.zzb.zzk(str2, "Time out while launching billing flow. Try to reconnect", e);
                            zzba.zza(4, 2, zzbc.zzn);
                            BillingResult billingResult3 = zzbc.zzn;
                            billingClientImpl.zzI(billingResult3);
                            return billingResult3;
                        } catch (TimeoutException e2) {
                            e = e2;
                            com.google.android.gms.internal.play_billing.zzb.zzk(str2, "Time out while launching billing flow. Try to reconnect", e);
                            zzba.zza(4, 2, zzbc.zzn);
                            BillingResult billingResult32 = zzbc.zzn;
                            billingClientImpl.zzI(billingResult32);
                            return billingResult32;
                        } catch (Exception e3) {
                            com.google.android.gms.internal.play_billing.zzb.zzk(str2, "Exception while launching billing flow. Try to reconnect", e3);
                            zzba.zza(5, 2, zzbc.zzm);
                            BillingResult billingResult4 = zzbc.zzm;
                            billingClientImpl.zzI(billingResult4);
                            return billingResult4;
                        }
                    }
                    com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", "Current client doesn't support purchases with ProductDetails.");
                    zzba.zza(20, 2, zzbc.zzv);
                    BillingResult billingResult5 = zzbc.zzv;
                    billingClientImpl.zzI(billingResult5);
                    return billingResult5;
                }
                com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", "Current client doesn't support multi-item purchases.");
                zzba.zza(19, 2, zzbc.zzt);
                BillingResult billingResult6 = zzbc.zzt;
                billingClientImpl.zzI(billingResult6);
                return billingResult6;
            }
            com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", "Current client doesn't support extra params for buy intent.");
            zzba.zza(18, 2, zzbc.zzh);
            BillingResult billingResult7 = zzbc.zzh;
            billingClientImpl.zzI(billingResult7);
            return billingResult7;
        }
        com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", "Current client doesn't support subscriptions.");
        zzba.zza(9, 2, zzbc.zzo);
        BillingResult billingResult8 = zzbc.zzo;
        billingClientImpl.zzI(billingResult8);
        return billingResult8;
    }

    @Override // com.android.billingclient.api.BillingClient
    public void queryProductDetailsAsync(final QueryProductDetailsParams queryProductDetailsParams, final ProductDetailsResponseListener productDetailsResponseListener) {
        if (!isReady()) {
            productDetailsResponseListener.onProductDetailsResponse(zzbc.zzm, new ArrayList());
            return;
        }
        if (!this.zzs) {
            com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", "Querying product details is not supported.");
            productDetailsResponseListener.onProductDetailsResponse(zzbc.zzv, new ArrayList());
        } else if (zzL(new Callable() { // from class: com.android.billingclient.api.zzs
            @Override // java.util.concurrent.Callable
            public final Object call() {
                BillingClientImpl.this.zzm(queryProductDetailsParams, productDetailsResponseListener);
                return null;
            }
        }, WorkRequest.DEFAULT_BACKOFF_DELAY_MILLIS, new Runnable() { // from class: com.android.billingclient.api.zzt
            @Override // java.lang.Runnable
            public final void run() {
                ProductDetailsResponseListener.this.onProductDetailsResponse(zzbc.zzn, new ArrayList());
            }
        }, zzH()) == null) {
            productDetailsResponseListener.onProductDetailsResponse(zzJ(), new ArrayList());
        }
    }

    @Override // com.android.billingclient.api.BillingClient
    public void queryPurchaseHistoryAsync(QueryPurchaseHistoryParams queryPurchaseHistoryParams, PurchaseHistoryResponseListener purchaseHistoryResponseListener) {
        zzN(queryPurchaseHistoryParams.zza(), purchaseHistoryResponseListener);
    }

    @Override // com.android.billingclient.api.BillingClient
    public void queryPurchasesAsync(QueryPurchasesParams queryPurchasesParams, PurchasesResponseListener purchasesResponseListener) {
        zzO(queryPurchasesParams.zza(), purchasesResponseListener);
    }

    @Override // com.android.billingclient.api.BillingClient
    public final void querySkuDetailsAsync(SkuDetailsParams skuDetailsParams, final SkuDetailsResponseListener skuDetailsResponseListener) {
        if (!isReady()) {
            skuDetailsResponseListener.onSkuDetailsResponse(zzbc.zzm, null);
            return;
        }
        String skuType = skuDetailsParams.getSkuType();
        List<String> skusList = skuDetailsParams.getSkusList();
        if (TextUtils.isEmpty(skuType)) {
            com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", "Please fix the input params. SKU type can't be empty.");
            skuDetailsResponseListener.onSkuDetailsResponse(zzbc.zzf, null);
            return;
        }
        if (skusList != null) {
            ArrayList arrayList = new ArrayList();
            for (String str : skusList) {
                zzbw zzbwVar = new zzbw(null);
                zzbwVar.zza(str);
                arrayList.add(zzbwVar.zzb());
            }
            if (zzL(new Callable(skuType, arrayList, null, skuDetailsResponseListener) { // from class: com.android.billingclient.api.zzq
                public final /* synthetic */ String zzb;
                public final /* synthetic */ List zzc;
                public final /* synthetic */ SkuDetailsResponseListener zzd;

                {
                    this.zzd = skuDetailsResponseListener;
                }

                @Override // java.util.concurrent.Callable
                public final Object call() {
                    BillingClientImpl.this.zzn(this.zzb, this.zzc, null, this.zzd);
                    return null;
                }
            }, WorkRequest.DEFAULT_BACKOFF_DELAY_MILLIS, new Runnable() { // from class: com.android.billingclient.api.zzy
                @Override // java.lang.Runnable
                public final void run() {
                    SkuDetailsResponseListener.this.onSkuDetailsResponse(zzbc.zzn, null);
                }
            }, zzH()) == null) {
                skuDetailsResponseListener.onSkuDetailsResponse(zzJ(), null);
                return;
            }
            return;
        }
        com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", "Please fix the input params. The list of SKUs can't be empty - set SKU list or SkuWithOffer list.");
        skuDetailsResponseListener.onSkuDetailsResponse(zzbc.zze, null);
    }

    @Override // com.android.billingclient.api.BillingClient
    public BillingResult showInAppMessages(final Activity activity, InAppMessageParams inAppMessageParams, InAppMessageResponseListener inAppMessageResponseListener) {
        if (!isReady()) {
            com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", "Service disconnected.");
            return zzbc.zzm;
        }
        if (!this.zzo) {
            com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", "Current client doesn't support showing in-app messages.");
            return zzbc.zzw;
        }
        View findViewById = activity.findViewById(R.id.content);
        IBinder windowToken = findViewById.getWindowToken();
        Rect rect = new Rect();
        findViewById.getGlobalVisibleRect(rect);
        final Bundle bundle = new Bundle();
        BundleCompat.putBinder(bundle, "KEY_WINDOW_TOKEN", windowToken);
        bundle.putInt("KEY_DIMEN_LEFT", rect.left);
        bundle.putInt("KEY_DIMEN_TOP", rect.top);
        bundle.putInt("KEY_DIMEN_RIGHT", rect.right);
        bundle.putInt("KEY_DIMEN_BOTTOM", rect.bottom);
        bundle.putString("playBillingLibraryVersion", this.zzb);
        bundle.putIntegerArrayList("KEY_CATEGORY_IDS", inAppMessageParams.getInAppMessageCategoriesToShow());
        final zzak zzakVar = new zzak(this, this.zzc, inAppMessageResponseListener);
        zzL(new Callable() { // from class: com.android.billingclient.api.zzae
            @Override // java.util.concurrent.Callable
            public final Object call() {
                BillingClientImpl.this.zzo(bundle, activity, zzakVar);
                return null;
            }
        }, 5000L, null, this.zzc);
        return zzbc.zzl;
    }

    public final /* synthetic */ void zzG(BillingResult billingResult) {
        if (this.zzd.zzc() != null) {
            this.zzd.zzc().onPurchasesUpdated(billingResult, null);
        } else {
            this.zzd.zzb();
            com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", "No valid listener is set in BroadcastManager");
        }
    }

    public final /* synthetic */ Bundle zzc(int i, String str, String str2, BillingFlowParams billingFlowParams, Bundle bundle) throws Exception {
        return this.zzf.zzg(i, this.zze.getPackageName(), str, str2, null, bundle);
    }

    public final /* synthetic */ Bundle zzd(String str, String str2) throws Exception {
        return this.zzf.zzf(3, this.zze.getPackageName(), str, str2, null);
    }

    public final /* synthetic */ Bundle zze(String str, Bundle bundle) throws Exception {
        return this.zzf.zzm(8, this.zze.getPackageName(), str, "subs", bundle);
    }

    public final /* synthetic */ Object zzk(AcknowledgePurchaseParams acknowledgePurchaseParams, AcknowledgePurchaseResponseListener acknowledgePurchaseResponseListener) throws Exception {
        try {
            com.google.android.gms.internal.play_billing.zze zzeVar = this.zzf;
            String packageName = this.zze.getPackageName();
            String purchaseToken = acknowledgePurchaseParams.getPurchaseToken();
            String str = this.zzb;
            Bundle bundle = new Bundle();
            bundle.putString("playBillingLibraryVersion", str);
            Bundle zzd = zzeVar.zzd(9, packageName, purchaseToken, bundle);
            int zzb = com.google.android.gms.internal.play_billing.zzb.zzb(zzd, "BillingClient");
            String zzf = com.google.android.gms.internal.play_billing.zzb.zzf(zzd, "BillingClient");
            BillingResult.Builder newBuilder = BillingResult.newBuilder();
            newBuilder.setResponseCode(zzb);
            newBuilder.setDebugMessage(zzf);
            acknowledgePurchaseResponseListener.onAcknowledgePurchaseResponse(newBuilder.build());
            return null;
        } catch (Exception e) {
            com.google.android.gms.internal.play_billing.zzb.zzk("BillingClient", "Error acknowledge purchase!", e);
            acknowledgePurchaseResponseListener.onAcknowledgePurchaseResponse(zzbc.zzm);
            return null;
        }
    }

    public final /* synthetic */ Object zzl(ConsumeParams consumeParams, ConsumeResponseListener consumeResponseListener) throws Exception {
        int zza;
        String str;
        String purchaseToken = consumeParams.getPurchaseToken();
        try {
            com.google.android.gms.internal.play_billing.zzb.zzi("BillingClient", "Consuming purchase with token: " + purchaseToken);
            if (this.zzm) {
                com.google.android.gms.internal.play_billing.zze zzeVar = this.zzf;
                String packageName = this.zze.getPackageName();
                boolean z = this.zzm;
                String str2 = this.zzb;
                Bundle bundle = new Bundle();
                if (z) {
                    bundle.putString("playBillingLibraryVersion", str2);
                }
                Bundle zze = zzeVar.zze(9, packageName, purchaseToken, bundle);
                zza = zze.getInt("RESPONSE_CODE");
                str = com.google.android.gms.internal.play_billing.zzb.zzf(zze, "BillingClient");
            } else {
                zza = this.zzf.zza(3, this.zze.getPackageName(), purchaseToken);
                str = "";
            }
            BillingResult.Builder newBuilder = BillingResult.newBuilder();
            newBuilder.setResponseCode(zza);
            newBuilder.setDebugMessage(str);
            BillingResult build = newBuilder.build();
            if (zza == 0) {
                com.google.android.gms.internal.play_billing.zzb.zzi("BillingClient", "Successfully consumed purchase.");
                consumeResponseListener.onConsumeResponse(build, purchaseToken);
                return null;
            }
            com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", "Error consuming purchase with token. Response code: " + zza);
            consumeResponseListener.onConsumeResponse(build, purchaseToken);
            return null;
        } catch (Exception e) {
            com.google.android.gms.internal.play_billing.zzb.zzk("BillingClient", "Error consuming purchase!", e);
            consumeResponseListener.onConsumeResponse(zzbc.zzm, purchaseToken);
            return null;
        }
    }

    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:31:? */
    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:32:? */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r8v0, types: [int] */
    /* JADX WARN: Type inference failed for: r8v1 */
    /* JADX WARN: Type inference failed for: r8v7 */
    /* JADX WARN: Type inference failed for: r8v8 */
    /* JADX WARN: Type inference failed for: r8v9 */
    public final /* synthetic */ Object zzm(QueryProductDetailsParams queryProductDetailsParams, ProductDetailsResponseListener productDetailsResponseListener) throws Exception {
        String str;
        Object obj;
        int i;
        com.google.android.gms.internal.play_billing.zze zzeVar;
        String packageName;
        Bundle bundle;
        int i2;
        BillingClientImpl billingClientImpl = this;
        ArrayList arrayList = new ArrayList();
        String zzb = queryProductDetailsParams.zzb();
        com.google.android.gms.internal.play_billing.zzu zza = queryProductDetailsParams.zza();
        int size = zza.size();
        int i3 = 0;
        while (true) {
            str = "Item is unavailable for purchase.";
            if (i3 >= size) {
                obj = null;
                str = "";
                i = 0;
                break;
            }
            ?? r8 = i3 + 20;
            ArrayList arrayList2 = new ArrayList(zza.subList(i3, r8 > size ? size : r8));
            ArrayList<String> arrayList3 = new ArrayList<>();
            int size2 = arrayList2.size();
            for (int i4 = 0; i4 < size2; i4++) {
                arrayList3.add(((QueryProductDetailsParams.Product) arrayList2.get(i4)).zza());
            }
            Bundle bundle2 = new Bundle();
            bundle2.putStringArrayList("ITEM_ID_LIST", arrayList3);
            bundle2.putString("playBillingLibraryVersion", billingClientImpl.zzb);
            try {
                zzeVar = billingClientImpl.zzf;
                packageName = billingClientImpl.zze.getPackageName();
                boolean zzP = zzP();
                String str2 = billingClientImpl.zzb;
                bundle = new Bundle();
                bundle.putString("playBillingLibraryVersion", str2);
                bundle.putBoolean("enablePendingPurchases", true);
                bundle.putString("SKU_DETAILS_RESPONSE_FORMAT", "PRODUCT_DETAILS");
                if (zzP) {
                    bundle.putBoolean("enablePendingPurchaseForSubscriptions", true);
                }
                ArrayList<String> arrayList4 = new ArrayList<>();
                ArrayList<String> arrayList5 = new ArrayList<>();
                int size3 = arrayList2.size();
                int i5 = 0;
                boolean z = false;
                while (i5 < size3) {
                    QueryProductDetailsParams.Product product = (QueryProductDetailsParams.Product) arrayList2.get(i5);
                    ArrayList arrayList6 = arrayList2;
                    try {
                        arrayList4.add(null);
                        z |= !TextUtils.isEmpty(null);
                        String zzb2 = product.zzb();
                        boolean z2 = r8;
                        if (zzb2.equals("first_party")) {
                            r8 = 0;
                            try {
                                com.google.android.gms.internal.play_billing.zzm.zzc(null, "Serialized DocId is required for constructing ExtraParams to query ProductDetails for all first party products.");
                                arrayList5.add(null);
                            } catch (Exception e) {
                                e = e;
                                obj = r8;
                                com.google.android.gms.internal.play_billing.zzb.zzk("BillingClient", "queryProductDetailsAsync got a remote exception (try to reconnect).", e);
                                str = "An internal error occurred.";
                                i = 6;
                                BillingResult.Builder newBuilder = BillingResult.newBuilder();
                                newBuilder.setResponseCode(i);
                                newBuilder.setDebugMessage(str);
                                productDetailsResponseListener.onProductDetailsResponse(newBuilder.build(), arrayList);
                                return obj;
                            }
                        }
                        i5++;
                        r8 = z2;
                        arrayList2 = arrayList6;
                    } catch (Exception e2) {
                        e = e2;
                        obj = null;
                    }
                }
                i2 = r8;
                if (z) {
                    bundle.putStringArrayList("SKU_OFFER_ID_TOKEN_LIST", arrayList4);
                }
                if (!arrayList5.isEmpty()) {
                    bundle.putStringArrayList("SKU_SERIALIZED_DOCID_LIST", arrayList5);
                }
                obj = null;
            } catch (Exception e3) {
                e = e3;
                obj = null;
            }
            try {
                Bundle zzl = zzeVar.zzl(17, packageName, zzb, bundle2, bundle);
                if (zzl != null) {
                    if (zzl.containsKey("DETAILS_LIST")) {
                        ArrayList<String> stringArrayList = zzl.getStringArrayList("DETAILS_LIST");
                        if (stringArrayList != null) {
                            for (int i6 = 0; i6 < stringArrayList.size(); i6++) {
                                try {
                                    ProductDetails productDetails = new ProductDetails(stringArrayList.get(i6));
                                    com.google.android.gms.internal.play_billing.zzb.zzi("BillingClient", "Got product details: ".concat(productDetails.toString()));
                                    arrayList.add(productDetails);
                                } catch (JSONException e4) {
                                    com.google.android.gms.internal.play_billing.zzb.zzk("BillingClient", "Got a JSON exception trying to decode ProductDetails. \n Exception: ", e4);
                                    str = "Error trying to decode SkuDetails.";
                                    i = 6;
                                    BillingResult.Builder newBuilder2 = BillingResult.newBuilder();
                                    newBuilder2.setResponseCode(i);
                                    newBuilder2.setDebugMessage(str);
                                    productDetailsResponseListener.onProductDetailsResponse(newBuilder2.build(), arrayList);
                                    return obj;
                                }
                            }
                            i3 = i2;
                            billingClientImpl = this;
                        } else {
                            com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", "queryProductDetailsAsync got null response list");
                            break;
                        }
                    } else {
                        i = com.google.android.gms.internal.play_billing.zzb.zzb(zzl, "BillingClient");
                        str = com.google.android.gms.internal.play_billing.zzb.zzf(zzl, "BillingClient");
                        if (i != 0) {
                            com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", "getSkuDetails() failed for queryProductDetailsAsync. Response code: " + i);
                        } else {
                            com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", "getSkuDetails() returned a bundle with neither an error nor a product detail list for queryProductDetailsAsync.");
                        }
                    }
                } else {
                    com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", "queryProductDetailsAsync got empty product details response.");
                    break;
                }
            } catch (Exception e5) {
                e = e5;
                com.google.android.gms.internal.play_billing.zzb.zzk("BillingClient", "queryProductDetailsAsync got a remote exception (try to reconnect).", e);
                str = "An internal error occurred.";
                i = 6;
                BillingResult.Builder newBuilder22 = BillingResult.newBuilder();
                newBuilder22.setResponseCode(i);
                newBuilder22.setDebugMessage(str);
                productDetailsResponseListener.onProductDetailsResponse(newBuilder22.build(), arrayList);
                return obj;
            }
        }
        i = 4;
        BillingResult.Builder newBuilder222 = BillingResult.newBuilder();
        newBuilder222.setResponseCode(i);
        newBuilder222.setDebugMessage(str);
        productDetailsResponseListener.onProductDetailsResponse(newBuilder222.build(), arrayList);
        return obj;
    }

    public final /* synthetic */ Object zzn(String str, List list, String str2, SkuDetailsResponseListener skuDetailsResponseListener) throws Exception {
        String str3;
        int i;
        int i2;
        String str4;
        Bundle zzk;
        ArrayList arrayList = new ArrayList();
        int size = list.size();
        int i3 = 0;
        while (true) {
            String str5 = "Item is unavailable for purchase.";
            if (i3 >= size) {
                str3 = "";
                i = 0;
                break;
            }
            int i4 = i3 + 20;
            ArrayList arrayList2 = new ArrayList(list.subList(i3, i4 > size ? size : i4));
            ArrayList<String> arrayList3 = new ArrayList<>();
            int size2 = arrayList2.size();
            for (int i5 = 0; i5 < size2; i5++) {
                arrayList3.add(((zzby) arrayList2.get(i5)).zza());
            }
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("ITEM_ID_LIST", arrayList3);
            bundle.putString("playBillingLibraryVersion", this.zzb);
            try {
                if (this.zzn) {
                    com.google.android.gms.internal.play_billing.zze zzeVar = this.zzf;
                    String packageName = this.zze.getPackageName();
                    int i6 = this.zzj;
                    boolean z = this.zzv;
                    boolean zzP = zzP();
                    String str6 = this.zzb;
                    Bundle bundle2 = new Bundle();
                    if (i6 >= 9) {
                        bundle2.putString("playBillingLibraryVersion", str6);
                    }
                    if (i6 >= 9 && z) {
                        bundle2.putBoolean("enablePendingPurchases", true);
                    }
                    if (zzP) {
                        bundle2.putBoolean("enablePendingPurchaseForSubscriptions", true);
                    }
                    if (i6 >= 14) {
                        ArrayList<String> arrayList4 = new ArrayList<>();
                        ArrayList<String> arrayList5 = new ArrayList<>();
                        ArrayList arrayList6 = new ArrayList();
                        int size3 = arrayList2.size();
                        int i7 = 0;
                        boolean z2 = false;
                        boolean z3 = false;
                        while (i7 < size3) {
                            arrayList4.add(null);
                            z2 |= !TextUtils.isEmpty(null);
                            arrayList5.add(null);
                            z3 |= !TextUtils.isEmpty(null);
                            arrayList6.add(0);
                            i7++;
                            str5 = str5;
                            size = size;
                        }
                        i2 = size;
                        str4 = str5;
                        if (z2) {
                            bundle2.putStringArrayList("SKU_OFFER_ID_TOKEN_LIST", arrayList4);
                        }
                        if (z3) {
                            bundle2.putStringArrayList("SKU_OFFER_ID_LIST", arrayList5);
                        }
                    } else {
                        i2 = size;
                        str4 = "Item is unavailable for purchase.";
                    }
                    zzk = zzeVar.zzl(10, packageName, str, bundle, bundle2);
                } else {
                    i2 = size;
                    str4 = "Item is unavailable for purchase.";
                    zzk = this.zzf.zzk(3, this.zze.getPackageName(), str, bundle);
                }
                if (zzk != null) {
                    if (zzk.containsKey("DETAILS_LIST")) {
                        ArrayList<String> stringArrayList = zzk.getStringArrayList("DETAILS_LIST");
                        if (stringArrayList != null) {
                            for (int i8 = 0; i8 < stringArrayList.size(); i8++) {
                                try {
                                    SkuDetails skuDetails = new SkuDetails(stringArrayList.get(i8));
                                    com.google.android.gms.internal.play_billing.zzb.zzi("BillingClient", "Got sku details: ".concat(skuDetails.toString()));
                                    arrayList.add(skuDetails);
                                } catch (JSONException e) {
                                    com.google.android.gms.internal.play_billing.zzb.zzk("BillingClient", "Got a JSON exception trying to decode SkuDetails.", e);
                                    str3 = "Error trying to decode SkuDetails.";
                                    arrayList = null;
                                    i = 6;
                                    BillingResult.Builder newBuilder = BillingResult.newBuilder();
                                    newBuilder.setResponseCode(i);
                                    newBuilder.setDebugMessage(str3);
                                    skuDetailsResponseListener.onSkuDetailsResponse(newBuilder.build(), arrayList);
                                    return null;
                                }
                            }
                            i3 = i4;
                            size = i2;
                        } else {
                            com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", "querySkuDetailsAsync got null response list");
                            break;
                        }
                    } else {
                        int zzb = com.google.android.gms.internal.play_billing.zzb.zzb(zzk, "BillingClient");
                        str3 = com.google.android.gms.internal.play_billing.zzb.zzf(zzk, "BillingClient");
                        if (zzb != 0) {
                            com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", "getSkuDetails() failed. Response code: " + zzb);
                            i = zzb;
                        } else {
                            com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", "getSkuDetails() returned a bundle with neither an error nor a detail list.");
                        }
                    }
                } else {
                    com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", "querySkuDetailsAsync got null sku details list");
                    break;
                }
            } catch (Exception e2) {
                com.google.android.gms.internal.play_billing.zzb.zzk("BillingClient", "querySkuDetailsAsync got a remote exception (try to reconnect).", e2);
                i = -1;
                str3 = "Service connection is disconnected.";
                arrayList = null;
            }
        }
        str3 = str4;
        arrayList = null;
        i = 4;
        BillingResult.Builder newBuilder2 = BillingResult.newBuilder();
        newBuilder2.setResponseCode(i);
        newBuilder2.setDebugMessage(str3);
        skuDetailsResponseListener.onSkuDetailsResponse(newBuilder2.build(), arrayList);
        return null;
    }

    public final /* synthetic */ Object zzo(Bundle bundle, Activity activity, ResultReceiver resultReceiver) throws Exception {
        this.zzf.zzn(12, this.zze.getPackageName(), bundle, new zzar(new WeakReference(activity), resultReceiver, null));
        return null;
    }

    @Override // com.android.billingclient.api.BillingClient
    public void launchPriceChangeConfirmationFlow(Activity activity, PriceChangeFlowParams priceChangeFlowParams, PriceChangeConfirmationListener priceChangeConfirmationListener) {
        if (!isReady()) {
            zzM(zzbc.zzm, priceChangeConfirmationListener);
            return;
        }
        if (priceChangeFlowParams == null || priceChangeFlowParams.getSkuDetails() == null) {
            com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", "Please fix the input params. priceChangeFlowParams must contain valid sku.");
            zzM(zzbc.zzk, priceChangeConfirmationListener);
            return;
        }
        final String sku = priceChangeFlowParams.getSkuDetails().getSku();
        if (sku == null) {
            com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", "Please fix the input params. priceChangeFlowParams must contain valid sku.");
            zzM(zzbc.zzk, priceChangeConfirmationListener);
            return;
        }
        if (!this.zzl) {
            com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", "Current client doesn't support price change confirmation flow.");
            zzM(zzbc.zzr, priceChangeConfirmationListener);
            return;
        }
        final Bundle bundle = new Bundle();
        bundle.putString("playBillingLibraryVersion", this.zzb);
        bundle.putBoolean("subs_price_change", true);
        try {
            Bundle bundle2 = (Bundle) zzL(new Callable() { // from class: com.android.billingclient.api.zzr
                @Override // java.util.concurrent.Callable
                public final Object call() {
                    return BillingClientImpl.this.zze(sku, bundle);
                }
            }, 5000L, null, this.zzc).get(5000L, TimeUnit.MILLISECONDS);
            int zzb = com.google.android.gms.internal.play_billing.zzb.zzb(bundle2, "BillingClient");
            String zzf = com.google.android.gms.internal.play_billing.zzb.zzf(bundle2, "BillingClient");
            BillingResult.Builder newBuilder = BillingResult.newBuilder();
            newBuilder.setResponseCode(zzb);
            newBuilder.setDebugMessage(zzf);
            BillingResult build = newBuilder.build();
            if (zzb != 0) {
                com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", "Unable to launch price change flow, error response code: " + zzb);
                zzM(build, priceChangeConfirmationListener);
                return;
            }
            zzah zzahVar = new zzah(this, this.zzc, priceChangeConfirmationListener);
            Intent intent = new Intent(activity, (Class<?>) ProxyBillingActivity.class);
            intent.putExtra("SUBS_MANAGEMENT_INTENT", (PendingIntent) bundle2.getParcelable("SUBS_MANAGEMENT_INTENT"));
            intent.putExtra("result_receiver", zzahVar);
            activity.startActivity(intent);
        } catch (CancellationException e) {
            e = e;
            com.google.android.gms.internal.play_billing.zzb.zzk("BillingClient", "Time out while launching Price Change Flow for sku: " + sku + "; try to reconnect", e);
            zzM(zzbc.zzn, priceChangeConfirmationListener);
        } catch (TimeoutException e2) {
            e = e2;
            com.google.android.gms.internal.play_billing.zzb.zzk("BillingClient", "Time out while launching Price Change Flow for sku: " + sku + "; try to reconnect", e);
            zzM(zzbc.zzn, priceChangeConfirmationListener);
        } catch (Exception e3) {
            com.google.android.gms.internal.play_billing.zzb.zzk("BillingClient", "Exception caught while launching Price Change Flow for sku: " + sku + "; try to reconnect", e3);
            zzM(zzbc.zzm, priceChangeConfirmationListener);
        }
    }

    @Override // com.android.billingclient.api.BillingClient
    public final void queryPurchaseHistoryAsync(String str, PurchaseHistoryResponseListener purchaseHistoryResponseListener) {
        zzN(str, purchaseHistoryResponseListener);
    }

    @Override // com.android.billingclient.api.BillingClient
    public void queryPurchasesAsync(String str, PurchasesResponseListener purchasesResponseListener) {
        zzO(str, purchasesResponseListener);
    }

    private BillingClientImpl(Context context, boolean z, boolean z2, PurchasesUpdatedListener purchasesUpdatedListener, String str, String str2, AlternativeBillingListener alternativeBillingListener) {
        this.zza = 0;
        this.zzc = new Handler(Looper.getMainLooper());
        this.zzj = 0;
        this.zzb = str;
        initialize(context, purchasesUpdatedListener, z, z2, alternativeBillingListener, str);
    }

    private BillingClientImpl(String str) {
        this.zza = 0;
        this.zzc = new Handler(Looper.getMainLooper());
        this.zzj = 0;
        this.zzb = str;
    }

    public BillingClientImpl(String str, boolean z, Context context, zzbf zzbfVar) {
        this.zza = 0;
        this.zzc = new Handler(Looper.getMainLooper());
        this.zzj = 0;
        this.zzb = zzK();
        this.zze = context.getApplicationContext();
        zzfl zzu = zzfm.zzu();
        zzu.zzj(zzK());
        zzu.zzi(this.zze.getPackageName());
        this.zzz = new zzbh();
        com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", "Billing client should have a valid listener but the provided is null.");
        this.zzd = new zzo(this.zze, null, this.zzz);
        this.zzv = z;
    }

    public BillingClientImpl(String str, boolean z, boolean z2, Context context, PurchasesUpdatedListener purchasesUpdatedListener, AlternativeBillingListener alternativeBillingListener) {
        this(context, z, false, purchasesUpdatedListener, zzK(), null, alternativeBillingListener);
    }

    @Override // com.android.billingclient.api.BillingClient
    public final void startConnection(BillingClientStateListener billingClientStateListener) {
        if (isReady()) {
            com.google.android.gms.internal.play_billing.zzb.zzi("BillingClient", "Service connection is valid. No need to re-initialize.");
            billingClientStateListener.onBillingSetupFinished(zzbc.zzl);
            return;
        }
        if (this.zza == 1) {
            com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", "Client is already in the process of connecting to billing service.");
            billingClientStateListener.onBillingSetupFinished(zzbc.zzd);
            return;
        }
        if (this.zza == 3) {
            com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", "Client was already closed and can't be reused. Please create another instance.");
            billingClientStateListener.onBillingSetupFinished(zzbc.zzm);
            return;
        }
        this.zza = 1;
        this.zzd.zze();
        com.google.android.gms.internal.play_billing.zzb.zzi("BillingClient", "Starting in-app billing setup.");
        this.zzg = new zzap(this, billingClientStateListener, null);
        Intent intent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
        intent.setPackage("com.android.vending");
        List<ResolveInfo> queryIntentServices = this.zze.getPackageManager().queryIntentServices(intent, 0);
        if (queryIntentServices != null && !queryIntentServices.isEmpty()) {
            ResolveInfo resolveInfo = queryIntentServices.get(0);
            if (resolveInfo.serviceInfo != null) {
                String str = resolveInfo.serviceInfo.packageName;
                String str2 = resolveInfo.serviceInfo.name;
                if (!"com.android.vending".equals(str) || str2 == null) {
                    com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", "The device doesn't have valid Play Store.");
                } else {
                    ComponentName componentName = new ComponentName(str, str2);
                    Intent intent2 = new Intent(intent);
                    intent2.setComponent(componentName);
                    intent2.putExtra("playBillingLibraryVersion", this.zzb);
                    if (this.zze.bindService(intent2, this.zzg, 1)) {
                        com.google.android.gms.internal.play_billing.zzb.zzi("BillingClient", "Service was bonded successfully.");
                        return;
                    }
                    com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", "Connection to Billing service is blocked.");
                }
            }
        }
        this.zza = 0;
        com.google.android.gms.internal.play_billing.zzb.zzi("BillingClient", "Billing service unavailable on device.");
        billingClientStateListener.onBillingSetupFinished(zzbc.zzc);
    }
}
