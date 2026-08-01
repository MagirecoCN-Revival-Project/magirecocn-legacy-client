package jp.f4samurai.purchase;

import jp.f4samurai.AppActivity;

/* loaded from: classes.dex */
public class PurchaseHelper {
    private static final String TAG = "PurchaseHelper";
    private static AppActivity sAppActivity;
    private static PurchaseImpl sPurchaseImpl;

    public static native void errorCallback(String str);

    public static native void sendReceipt(String str);

    public PurchaseHelper() {
        sAppActivity = (AppActivity) AppActivity.getContext();
    }

    public static void prepare() {
        sAppActivity.runOnUiThread(new Runnable() { // from class: jp.f4samurai.purchase.PurchaseHelper.1
            @Override // java.lang.Runnable
            public void run() {
                PurchaseHelper.sPurchaseImpl = new PurchaseImpl(PurchaseHelper.sAppActivity, PurchaseHelper.sAppActivity.getApplication());
            }
        });
    }

    public static void refresh() {
        PurchaseImpl purchaseImpl = sPurchaseImpl;
        if (purchaseImpl != null) {
            purchaseImpl.onDestroy();
            sPurchaseImpl = null;
        }
    }

    public static void startPurchase(final String str) {
        sAppActivity.runOnUiThread(new Runnable() { // from class: jp.f4samurai.purchase.PurchaseHelper.2
            @Override // java.lang.Runnable
            public void run() {
                if (PurchaseHelper.sPurchaseImpl != null) {
                    PurchaseHelper.sPurchaseImpl.startPurchase(PurchaseHelper.sAppActivity, str);
                }
            }
        });
    }

    public static void finishPurchaseWithStatus(final boolean z) {
        sAppActivity.runOnUiThread(new Runnable() { // from class: jp.f4samurai.purchase.PurchaseHelper.3
            @Override // java.lang.Runnable
            public void run() {
                if (PurchaseHelper.sPurchaseImpl != null) {
                    PurchaseHelper.sPurchaseImpl.finishPurchaseWithStatus(z);
                }
            }
        });
    }

    public static void _sendReceipt(String str) {
        sendReceipt(str);
    }

    public static void _errorCallback(String str) {
        errorCallback(str);
    }

    public void onDestroy() {
        PurchaseImpl purchaseImpl = sPurchaseImpl;
        if (purchaseImpl != null) {
            purchaseImpl.onDestroy();
        }
    }
}
