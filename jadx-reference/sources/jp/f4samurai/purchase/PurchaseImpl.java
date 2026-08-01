package jp.f4samurai.purchase;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.ProductDetails;
import com.android.billingclient.api.ProductDetailsResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesResponseListener;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.QueryProductDetailsParams;
import com.android.billingclient.api.QueryPurchasesParams;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import jp.f4samurai.madomagi.BuildConfig;

/* loaded from: classes.dex */
public class PurchaseImpl implements PurchasesUpdatedListener, BillingClientStateListener, ProductDetailsResponseListener, ConsumeResponseListener, PurchasesResponseListener {
    public static String PURCHASE_DEVELOPER_PAYLOAD = null;
    public static String PURCHASE_LICENSE_KEY = null;
    private static final String TAG = "PurchaseImpl";
    private static String mProgressPurchase;
    private static boolean mSetupDone;
    private BillingClient billingClient;
    private Activity mAppActivity = null;
    private ArrayList<String> mProductIdList = new ArrayList<>();
    private List<Purchase> mPurchaseList = null;
    List<ProductDetails> myProductDetailsList;

    public void onDestroy() {
    }

    public PurchaseImpl(Context context, Application application) {
        PURCHASE_LICENSE_KEY = BuildConfig.PURCHASE_LICENSE_KEY;
        PURCHASE_DEVELOPER_PAYLOAD = BuildConfig.PURCHASE_DEVELOPER_PAYLOAD;
        InitializeBillingClient(application);
    }

    private void InitializeBillingClient(Application application) {
        BillingClient.Builder newBuilder = BillingClient.newBuilder(application);
        newBuilder.setListener(this);
        newBuilder.enablePendingPurchases();
        BillingClient build = newBuilder.build();
        this.billingClient = build;
        build.startConnection(this);
    }

    public void startPurchase(Activity activity, String str) {
        if (!mSetupDone) {
            executeErrorCallback("購入ができない状態です。\nアプリを再起動してください。");
            return;
        }
        ProductDetails productDetails = getProductDetails(str);
        if (productDetails != null) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(BillingFlowParams.ProductDetailsParams.newBuilder().setProductDetails(productDetails).build());
            BillingResult launchBillingFlow = this.billingClient.launchBillingFlow(activity, BillingFlowParams.newBuilder().setProductDetailsParamsList(arrayList).build());
            showResponseCode(launchBillingFlow.getResponseCode(), launchBillingFlow.getDebugMessage());
            mProgressPurchase = null;
            return;
        }
        this.mProductIdList.add(str);
        queryProductDetails(this.mProductIdList);
        mProgressPurchase = str;
        this.mAppActivity = activity;
    }

    public void finishPurchaseWithStatus(boolean z) {
        List<Purchase> list = this.mPurchaseList;
        if (list == null) {
            return;
        }
        Purchase purchase = list.get(0);
        this.mPurchaseList.remove(0);
        if (z) {
            String handlePurchase = handlePurchase(purchase);
            Log.d(TAG, handlePurchase + " " + purchase.getProducts().get(0));
        }
        if (this.mPurchaseList.size() > 0) {
            verifyReceipt();
        } else {
            this.mPurchaseList = null;
        }
    }

    private void verifyReceipt() {
        List<Purchase> list = this.mPurchaseList;
        if (list != null) {
            try {
                Purchase purchase = list.get(0);
                int purchaseState = purchase.getPurchaseState();
                if (purchaseState == 1) {
                    if (!purchase.getPurchaseToken().equals("")) {
                        PurchaseHelper._sendReceipt(("COMMAND_TYPE=" + URLEncoder.encode("3", "UTF-8")) + "&" + ("INAPP_PURCHASE_DATA=" + URLEncoder.encode(purchase.getOriginalJson(), "UTF-8")) + "&" + ("INAPP_DATA_SIGNATURE=" + URLEncoder.encode(purchase.getSignature(), "UTF-8")));
                    } else {
                        executeErrorCallback("購入の認証に失敗しました");
                        finishPurchaseWithStatus(false);
                    }
                } else if (purchaseState == 2) {
                    executeErrorCallback("アイテムのご購入は保留中となっています。\nお支払い完了後、順次反映されます。");
                    finishPurchaseWithStatus(false);
                } else if (purchaseState == 0) {
                    executeErrorCallback("購入の認証に失敗しました");
                    finishPurchaseWithStatus(false);
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    private void executeErrorCallback(String str) {
        PurchaseHelper._errorCallback(str);
    }

    @Override // com.android.billingclient.api.BillingClientStateListener
    public void onBillingSetupFinished(BillingResult billingResult) {
        if (billingResult == null) {
            Log.wtf(TAG, "onBillingSetupFinished: null BillingResult");
            return;
        }
        int responseCode = billingResult.getResponseCode();
        showResponseCode(responseCode, billingResult.getDebugMessage());
        if (responseCode == 0) {
            queryPurchases();
            mSetupDone = true;
        }
    }

    @Override // com.android.billingclient.api.BillingClientStateListener
    public void onBillingServiceDisconnected() {
        Log.d(TAG, "onBillingServiceDisconnected");
    }

    @Override // com.android.billingclient.api.ProductDetailsResponseListener
    public void onProductDetailsResponse(BillingResult billingResult, List<ProductDetails> list) {
        if (billingResult == null) {
            Log.wtf(TAG, "onProductDetailsResponse: null BillingResult");
            return;
        }
        int responseCode = billingResult.getResponseCode();
        showResponseCode(responseCode, billingResult.getDebugMessage());
        if (responseCode == 0) {
            this.myProductDetailsList = list;
        }
        String str = mProgressPurchase;
        if (str != null) {
            startPurchase(this.mAppActivity, str);
        }
    }

    ProductDetails getProductDetails(String str) {
        List<ProductDetails> list = this.myProductDetailsList;
        ProductDetails productDetails = null;
        if (list == null) {
            Log.d(TAG, "Exec [Get ProductIds] first");
        } else {
            for (ProductDetails productDetails2 : list) {
                if (productDetails2.getProductId().equals(str)) {
                    productDetails = productDetails2;
                }
            }
            if (productDetails == null) {
                Log.d(TAG, str + " is not found");
            }
        }
        return productDetails;
    }

    @Override // com.android.billingclient.api.PurchasesUpdatedListener
    public void onPurchasesUpdated(BillingResult billingResult, List<Purchase> list) {
        if (billingResult == null) {
            Log.wtf(TAG, "onPurchasesUpdated: null BillingResult");
            return;
        }
        int responseCode = billingResult.getResponseCode();
        showResponseCode(responseCode, billingResult.getDebugMessage());
        if (responseCode != 0) {
            if (responseCode == 1) {
                executeErrorCallback("購入処理をキャンセルしました");
                return;
            } else {
                executeErrorCallback("購入処理をキャンセルしました");
                return;
            }
        }
        if (list == null) {
            Log.d(TAG, "onPurchasesUpdated: null purchase list");
        } else {
            this.mPurchaseList = list;
            verifyReceipt();
        }
    }

    public void queryProductDetails(List<String> list) {
        Log.d(TAG, "queryProductDetails");
        ArrayList arrayList = new ArrayList();
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(QueryProductDetailsParams.Product.newBuilder().setProductId(it.next()).setProductType("inapp").build());
        }
        QueryProductDetailsParams build = QueryProductDetailsParams.newBuilder().setProductList(arrayList).build();
        Log.i(TAG, "queryProductDetailsAsync");
        this.billingClient.queryProductDetailsAsync(build, this);
    }

    public void queryPurchases() {
        if (!this.billingClient.isReady()) {
            Log.e(TAG, "queryPurchases: BillingClient is not ready");
        }
        Log.d(TAG, "queryPurchases: INAPP");
        this.billingClient.queryPurchasesAsync(QueryPurchasesParams.newBuilder().setProductType("inapp").build(), this);
    }

    @Override // com.android.billingclient.api.PurchasesResponseListener
    public void onQueryPurchasesResponse(BillingResult billingResult, List<Purchase> list) {
        showResponseCode(billingResult.getResponseCode(), "billingClient.onQueryPurchaseResponse");
        if (list.size() > 0) {
            this.mPurchaseList = list;
            verifyReceipt();
        }
    }

    String handlePurchase(Purchase purchase) {
        int purchaseState = purchase.getPurchaseState();
        if (purchaseState != 1) {
            return purchaseState == 2 ? "pending" : purchaseState == 0 ? "unspecified state" : "error";
        }
        this.billingClient.consumeAsync(ConsumeParams.newBuilder().setPurchaseToken(purchase.getPurchaseToken()).build(), this);
        return "purchased";
    }

    @Override // com.android.billingclient.api.ConsumeResponseListener
    public void onConsumeResponse(BillingResult billingResult, String str) {
        if (billingResult == null) {
            Log.wtf(TAG, "onConsumeResponse: null BillingResult");
            return;
        }
        showResponseCode(billingResult.getResponseCode(), billingResult.getDebugMessage());
        if (billingResult.getResponseCode() == 0) {
            verifyReceipt();
        } else {
            executeErrorCallback("購入に失敗しました");
        }
    }

    void showResponseCode(int i, String str) {
        switch (i) {
            case -2:
                Log.d(TAG, "BillingResponseCode:FEATURE_NOT_SUPPORTED " + str);
                return;
            case -1:
                Log.d(TAG, "BillingResponseCode:SERVICE_DISCONNECTED " + str);
                return;
            case 0:
                Log.d(TAG, "BillingResponseCode:OK " + str);
                return;
            case 1:
                Log.d(TAG, "BillingResponseCode:USER_CANCELED " + str);
                return;
            case 2:
                Log.d(TAG, "BillingResponseCode:SERVICE_UNAVAILABLE " + str);
                return;
            case 3:
                Log.d(TAG, "BillingResponseCode:BILLING_UNAVAILABLE " + str);
                return;
            case 4:
                Log.d(TAG, "BillingResponseCode:ITEM_UNAVAILABLE " + str);
                return;
            case 5:
                Log.d(TAG, "BillingResponseCode:DEVELOPER_ERROR " + str);
                return;
            case 6:
                Log.d(TAG, "BillingResponseCode:ERROR " + str);
                return;
            case 7:
                Log.d(TAG, "BillingResponseCode:ITEM_ALREADY_OWNED " + str);
                return;
            case 8:
                Log.d(TAG, "BillingResponseCode:ITEM_NOT_OWNED " + str);
                return;
            default:
                return;
        }
    }
}
