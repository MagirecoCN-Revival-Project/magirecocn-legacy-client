package jp.f4samurai;

import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.core.app.ActivityCompat;
import jp.f4samurai.backtrace.BacktraceHandler;
import jp.f4samurai.bridge.NativeBridge;
import jp.f4samurai.camera.CameraHelper;
import jp.f4samurai.editbox.EditBoxHelper;
import jp.f4samurai.notification.NotificationCommandHelper;
import jp.f4samurai.pnote.PnoteHelper;
import jp.f4samurai.purchase.PurchaseHelper;
import jp.f4samurai.thinkingdata.ThinkingDataHelper;
import jp.f4samurai.utils.RuntimePermissionUtils;
import jp.f4samurai.web.WebViewHelper;
import org.cocos2dx.lib.Cocos2dxActivity;
import org.cocos2dx.lib.Cocos2dxHelper;

/* loaded from: classes.dex */
public class AppActivity extends Cocos2dxActivity implements Cocos2dxHelper.Cocos2dxHelperListener, ActivityCompat.OnRequestPermissionsResultCallback {
    private static final String TAG = "AppActivity";
    private RuntimePermissionUtils.Callback mCallback;
    private WebViewHelper mWebViewHelper;
    private NativeBridge mNativeBridge = null;
    private PurchaseHelper mPurchaseHelper = null;
    private PnoteHelper mPnoteHelper = null;
    private NotificationCommandHelper mAlarmWeeklyHelper = null;
    private EditBoxHelper mEditBoxHelper = null;
    private ThinkingDataHelper mTEHelper = null;
    private CameraHelper mCameraHelper = null;

    @Override // android.app.Activity
    public void onBackPressed() {
    }

    @Override // org.cocos2dx.lib.Cocos2dxActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        BacktraceHandler.startBacktrace(getApplicationContext());
        if (Build.VERSION.SDK_INT >= 33 && !RuntimePermissionUtils.hasSelfPermissions(this, "android.permission.POST_NOTIFICATIONS")) {
            RuntimePermissionUtils.requestPermission(this, "android.permission.POST_NOTIFICATIONS", 4, null);
        }
        Display defaultDisplay = getWindowManager().getDefaultDisplay();
        Point point = new Point(0, 0);
        defaultDisplay.getRealSize(point);
        float f = point.x > point.y ? point.x : point.y;
        float f2 = point.x > point.y ? point.y : point.x;
        if (f / f2 > 1.7777778f) {
            resizeLayout((int) ((f2 / 9.0f) * 16.0f), (int) f2);
        } else {
            resizeLayout((int) f, (int) f2);
        }
        if (this.mNativeBridge == null) {
            this.mNativeBridge = new NativeBridge();
        }
        if (this.mWebViewHelper == null) {
            this.mWebViewHelper = new WebViewHelper(this, this.mFrameLayout);
        }
        if (this.mPurchaseHelper == null) {
            this.mPurchaseHelper = new PurchaseHelper();
        }
        if (this.mPnoteHelper == null) {
            this.mPnoteHelper = new PnoteHelper();
        }
        if (this.mAlarmWeeklyHelper == null) {
            this.mAlarmWeeklyHelper = new NotificationCommandHelper();
        }
        if (this.mEditBoxHelper == null) {
            this.mEditBoxHelper = new EditBoxHelper(this.mFrameLayout);
        }
        if (this.mTEHelper == null) {
            this.mTEHelper = new ThinkingDataHelper();
            ThinkingDataHelper.initialize();
        }
        if (this.mCameraHelper == null) {
            this.mCameraHelper = new CameraHelper(this.mFrameLayout);
        }
        retrieveIntent(getIntent());
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == 4) {
            if (keyEvent.getAction() != 0) {
                return true;
            }
            NativeBridge._onBackKeyReleased();
            return true;
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    @Override // org.cocos2dx.lib.Cocos2dxActivity, android.app.Activity
    protected void onResume() {
        Log.d(TAG, "onResume()");
        super.onResume();
        this.mWebViewHelper.onResume();
        CameraHelper.onResume();
    }

    @Override // android.app.Activity
    protected void onStart() {
        super.onStart();
    }

    @Override // android.app.Activity
    protected void onStop() {
        super.onStop();
    }

    @Override // org.cocos2dx.lib.Cocos2dxActivity, android.app.Activity, android.view.Window.Callback
    public void onWindowFocusChanged(boolean z) {
        Log.d(TAG, "onWindowFocusChanged() hasFocus=" + z);
        super.onWindowFocusChanged(z);
        this.mEditBoxHelper.onWindowFocusChanged(z);
        if (z) {
            getWindow().setSoftInputMode(3);
            new Handler().postDelayed(new Runnable() { // from class: jp.f4samurai.AppActivity.1
                @Override // java.lang.Runnable
                public void run() {
                    AppActivity.this.hideNavigation();
                }
            }, 1000L);
        }
    }

    @Override // org.cocos2dx.lib.Cocos2dxActivity, android.app.Activity
    protected void onPause() {
        Log.d(TAG, "onPause()");
        super.onPause();
        this.mWebViewHelper.onPause();
        CameraHelper.onPause();
    }

    @Override // org.cocos2dx.lib.Cocos2dxActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        this.mWebViewHelper.onDestroy();
        this.mPurchaseHelper.onDestroy();
    }

    @Override // android.app.Activity
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        retrieveIntent(intent);
    }

    @Override // org.cocos2dx.lib.Cocos2dxActivity, org.cocos2dx.lib.Cocos2dxHelper.Cocos2dxHelperListener
    public void showDialog(String str, String str2) {
        super.showDialog(str, str2);
    }

    @Override // org.cocos2dx.lib.Cocos2dxActivity, org.cocos2dx.lib.Cocos2dxHelper.Cocos2dxHelperListener
    public void runOnGLThread(Runnable runnable) {
        super.runOnGLThread(runnable);
    }

    private void retrieveIntent(Intent intent) {
        setIntent(intent);
        this.mPnoteHelper.onNewIntent(intent);
        if (intent.getData() != null) {
            Uri data = intent.getData();
            if (data.getScheme().contentEquals("magireco.reward")) {
                NativeBridge._setRewardData(data.toString().substring(18));
            }
        }
    }

    public void hideNavigation() {
        getWindow().getDecorView().setSystemUiVisibility(5894);
    }

    private void resizeLayout(int i, int i2) {
        ViewGroup viewGroup = (ViewGroup) this.mFrameLayout.getParent();
        viewGroup.removeView(this.mFrameLayout);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(i, i2);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setGravity(17);
        linearLayout.addView(this.mFrameLayout, layoutParams);
        viewGroup.addView(linearLayout);
    }

    public void setPermissionCallback(RuntimePermissionUtils.Callback callback) {
        this.mCallback = callback;
    }

    @Override // android.app.Activity, androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        if (RuntimePermissionUtils.checkGrantResults(i, iArr)) {
            RuntimePermissionUtils.Callback callback = this.mCallback;
            if (callback != null) {
                callback.onGranted();
                this.mCallback = null;
                return;
            }
            return;
        }
        RuntimePermissionUtils.Callback callback2 = this.mCallback;
        if (callback2 != null) {
            callback2.onDenied();
            this.mCallback = null;
        }
        super.onRequestPermissionsResult(i, strArr, iArr);
    }
}
