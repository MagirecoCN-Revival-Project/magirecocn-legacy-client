package org.cocos2dx.lib;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import java.util.Iterator;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLDisplay;
import org.cocos2dx.lib.Cocos2dxHandler;
import org.cocos2dx.lib.Cocos2dxHelper;

/* loaded from: classes2.dex */
public abstract class Cocos2dxActivity extends Activity implements Cocos2dxHelper.Cocos2dxHelperListener {
    private static final String TAG = "Cocos2dxActivity";
    private static Cocos2dxActivity sContext;
    private Cocos2dxGLSurfaceView mGLSurfaceView = null;
    private int[] mGLContextAttrs = null;
    private Cocos2dxHandler mHandler = null;
    private Cocos2dxVideoHelper mVideoHelper = null;
    private Cocos2dxWebViewHelper mWebViewHelper = null;
    private Cocos2dxEditBoxHelper mEditBoxHelper = null;
    private boolean hasFocus = false;
    private boolean showVirtualButton = false;
    private boolean gainAudioFocus = false;
    private boolean paused = true;
    protected ResizeLayout mFrameLayout = null;

    private static native int[] getGLContextAttrs();

    public Cocos2dxGLSurfaceView getGLSurfaceView() {
        return this.mGLSurfaceView;
    }

    public static Context getContext() {
        return sContext;
    }

    public void setKeepScreenOn(final boolean z) {
        runOnUiThread(new Runnable() { // from class: org.cocos2dx.lib.Cocos2dxActivity.1
            @Override // java.lang.Runnable
            public void run() {
                Cocos2dxActivity.this.mGLSurfaceView.setKeepScreenOn(z);
            }
        });
    }

    public void setEnableVirtualButton(boolean z) {
        this.showVirtualButton = z;
    }

    public void setEnableAudioFocusGain(boolean z) {
        if (this.gainAudioFocus != z) {
            if (!this.paused) {
                if (z) {
                    Cocos2dxAudioFocusManager.registerAudioFocusListener(this);
                } else {
                    Cocos2dxAudioFocusManager.unregisterAudioFocusListener(this);
                }
            }
            this.gainAudioFocus = z;
        }
    }

    protected void onLoadNativeLibraries() {
        try {
            System.loadLibrary(getPackageManager().getApplicationInfo(getPackageName(), 128).metaData.getString("android.app.lib_name"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (!isTaskRoot()) {
            finish();
            Log.w(TAG, "[Workaround] Ignore the activity started from icon!");
            return;
        }
        hideVirtualButton();
        onLoadNativeLibraries();
        sContext = this;
        this.mHandler = new Cocos2dxHandler(this);
        Cocos2dxHelper.init(this);
        this.mGLContextAttrs = getGLContextAttrs();
        init();
        if (this.mVideoHelper == null) {
            this.mVideoHelper = new Cocos2dxVideoHelper(this, this.mFrameLayout);
        }
        if (this.mWebViewHelper == null) {
            this.mWebViewHelper = new Cocos2dxWebViewHelper(this.mFrameLayout);
        }
        if (this.mEditBoxHelper == null) {
            this.mEditBoxHelper = new Cocos2dxEditBoxHelper(this.mFrameLayout);
        }
        getWindow().setSoftInputMode(32);
        setVolumeControlStream(3);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.app.Activity
    public void onResume() {
        Log.d(TAG, "onResume()");
        this.paused = false;
        super.onResume();
        if (this.gainAudioFocus) {
            Cocos2dxAudioFocusManager.registerAudioFocusListener(this);
        }
        hideVirtualButton();
        resumeIfHasFocus();
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public void onWindowFocusChanged(boolean z) {
        Log.d(TAG, "onWindowFocusChanged() hasFocus=" + z);
        super.onWindowFocusChanged(z);
        this.hasFocus = z;
        resumeIfHasFocus();
    }

    private void resumeIfHasFocus() {
        boolean z = (isDeviceLocked() || isDeviceAsleep()) ? false : true;
        if (this.hasFocus && z) {
            hideVirtualButton();
            Cocos2dxHelper.onResume();
            this.mGLSurfaceView.onResume();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.app.Activity
    public void onPause() {
        Log.d(TAG, "onPause()");
        this.paused = true;
        super.onPause();
        if (this.gainAudioFocus) {
            Cocos2dxAudioFocusManager.unregisterAudioFocusListener(this);
        }
        Cocos2dxHelper.onPause();
        this.mGLSurfaceView.onPause();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.app.Activity
    public void onDestroy() {
        if (this.gainAudioFocus) {
            Cocos2dxAudioFocusManager.unregisterAudioFocusListener(this);
        }
        super.onDestroy();
    }

    public void showDialog(String str, String str2) {
        Message message = new Message();
        message.what = 1;
        message.obj = new Cocos2dxHandler.DialogMessage(str, str2);
        this.mHandler.sendMessage(message);
    }

    public void runOnGLThread(Runnable runnable) {
        this.mGLSurfaceView.queueEvent(runnable);
    }

    @Override // android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        Iterator<PreferenceManager.OnActivityResultListener> it = Cocos2dxHelper.getOnActivityResultListeners().iterator();
        while (it.hasNext()) {
            it.next().onActivityResult(i, i2, intent);
        }
        super.onActivityResult(i, i2, intent);
    }

    public void init() {
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-1, -1);
        ResizeLayout resizeLayout = new ResizeLayout(this);
        this.mFrameLayout = resizeLayout;
        resizeLayout.setLayoutParams(layoutParams);
        ViewGroup.LayoutParams layoutParams2 = new ViewGroup.LayoutParams(-1, -2);
        Cocos2dxEditBox cocos2dxEditBox = new Cocos2dxEditBox(this);
        cocos2dxEditBox.setLayoutParams(layoutParams2);
        this.mFrameLayout.addView(cocos2dxEditBox);
        Cocos2dxGLSurfaceView onCreateView = onCreateView();
        this.mGLSurfaceView = onCreateView;
        this.mFrameLayout.addView(onCreateView);
        this.mGLSurfaceView.setCocos2dxRenderer(new Cocos2dxRenderer());
        this.mGLSurfaceView.setCocos2dxEditText(cocos2dxEditBox);
        setContentView(this.mFrameLayout);
    }

    public Cocos2dxGLSurfaceView onCreateView() {
        Cocos2dxGLSurfaceView cocos2dxGLSurfaceView = new Cocos2dxGLSurfaceView(this);
        if (this.mGLContextAttrs[3] > 0) {
            cocos2dxGLSurfaceView.getHolder().setFormat(-3);
        }
        cocos2dxGLSurfaceView.setEGLConfigChooser(new Cocos2dxEGLConfigChooser(this.mGLContextAttrs));
        cocos2dxGLSurfaceView.setZOrderMediaOverlay(true);
        return cocos2dxGLSurfaceView;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void hideVirtualButton() {
        if (!this.showVirtualButton && Build.VERSION.SDK_INT >= 19) {
            try {
                int intValue = ((Integer) Cocos2dxReflectionHelper.getConstantValue(View.class, "SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION")).intValue();
                int intValue2 = ((Integer) Cocos2dxReflectionHelper.getConstantValue(View.class, "SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN")).intValue();
                int intValue3 = ((Integer) Cocos2dxReflectionHelper.getConstantValue(View.class, "SYSTEM_UI_FLAG_HIDE_NAVIGATION")).intValue();
                Cocos2dxReflectionHelper.invokeInstanceMethod(getWindow().getDecorView(), "setSystemUiVisibility", new Class[]{Integer.TYPE}, new Object[]{Integer.valueOf(((Integer) Cocos2dxReflectionHelper.getConstantValue(View.class, "SYSTEM_UI_FLAG_LAYOUT_STABLE")).intValue() | intValue | intValue2 | intValue3 | ((Integer) Cocos2dxReflectionHelper.getConstantValue(View.class, "SYSTEM_UI_FLAG_FULLSCREEN")).intValue() | ((Integer) Cocos2dxReflectionHelper.getConstantValue(View.class, "SYSTEM_UI_FLAG_IMMERSIVE_STICKY")).intValue())});
            } catch (NullPointerException e) {
                Log.e(TAG, "hideVirtualButton", e);
            }
        }
    }

    private static boolean isAndroidEmulator() {
        String str = Build.MODEL;
        String str2 = TAG;
        Log.d(str2, "model=" + str);
        String str3 = Build.PRODUCT;
        Log.d(str2, "product=" + str3);
        boolean z = false;
        if (str3 != null && (str3.equals("sdk") || str3.contains("_sdk") || str3.contains("sdk_"))) {
            z = true;
        }
        Log.d(str2, "isEmulator=" + z);
        return z;
    }

    private static boolean isDeviceLocked() {
        return ((KeyguardManager) getContext().getSystemService("keyguard")).inKeyguardRestrictedInputMode();
    }

    private static boolean isDeviceAsleep() {
        if (((PowerManager) getContext().getSystemService("power")) == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= 20) {
            return !r0.isInteractive();
        }
        return !r0.isScreenOn();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class Cocos2dxEGLConfigChooser implements GLSurfaceView.EGLConfigChooser {
        private final int EGL_OPENGL_ES2_BIT = 4;
        private final int EGL_OPENGL_ES3_BIT = 64;
        private int[] mConfigAttributes;

        public Cocos2dxEGLConfigChooser(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
            this.mConfigAttributes = new int[]{i, i2, i3, i4, i5, i6, i7};
        }

        public Cocos2dxEGLConfigChooser(int[] iArr) {
            this.mConfigAttributes = iArr;
        }

        @Override // android.opengl.GLSurfaceView.EGLConfigChooser
        public EGLConfig chooseConfig(EGL10 egl10, EGLDisplay eGLDisplay) {
            int[][] iArr = new int[4];
            int[] iArr2 = new int[19];
            iArr2[0] = 12324;
            int[] iArr3 = this.mConfigAttributes;
            iArr2[1] = iArr3[0];
            iArr2[2] = 12323;
            iArr2[3] = iArr3[1];
            iArr2[4] = 12322;
            iArr2[5] = iArr3[2];
            iArr2[6] = 12321;
            iArr2[7] = iArr3[3];
            iArr2[8] = 12325;
            iArr2[9] = iArr3[4];
            iArr2[10] = 12326;
            iArr2[11] = iArr3[5];
            iArr2[12] = 12338;
            iArr2[13] = iArr3[6] > 0 ? 1 : 0;
            iArr2[14] = 12337;
            iArr2[15] = iArr3[6];
            iArr2[16] = 12352;
            iArr2[17] = 4;
            iArr2[18] = 12344;
            iArr[0] = iArr2;
            int[] iArr4 = new int[19];
            iArr4[0] = 12324;
            iArr4[1] = iArr3[0];
            iArr4[2] = 12323;
            iArr4[3] = iArr3[1];
            iArr4[4] = 12322;
            iArr4[5] = iArr3[2];
            iArr4[6] = 12321;
            iArr4[7] = iArr3[3];
            iArr4[8] = 12325;
            iArr4[9] = iArr3[4] >= 24 ? 16 : iArr3[4];
            iArr4[10] = 12326;
            iArr4[11] = iArr3[5];
            iArr4[12] = 12338;
            iArr4[13] = iArr3[6] > 0 ? 1 : 0;
            iArr4[14] = 12337;
            iArr4[15] = iArr3[6];
            iArr4[16] = 12352;
            iArr4[17] = 4;
            iArr4[18] = 12344;
            iArr[1] = iArr4;
            int[] iArr5 = new int[19];
            iArr5[0] = 12324;
            iArr5[1] = iArr3[0];
            iArr5[2] = 12323;
            iArr5[3] = iArr3[1];
            iArr5[4] = 12322;
            iArr5[5] = iArr3[2];
            iArr5[6] = 12321;
            iArr5[7] = iArr3[3];
            iArr5[8] = 12325;
            iArr5[9] = iArr3[4] >= 24 ? 16 : iArr3[4];
            iArr5[10] = 12326;
            iArr5[11] = iArr3[5];
            iArr5[12] = 12338;
            iArr5[13] = 0;
            iArr5[14] = 12337;
            iArr5[15] = 0;
            iArr5[16] = 12352;
            iArr5[17] = 4;
            iArr5[18] = 12344;
            iArr[2] = iArr5;
            iArr[3] = new int[]{12352, 4, 12344};
            for (int i = 0; i < 4; i++) {
                EGLConfig doChooseConfig = doChooseConfig(egl10, eGLDisplay, iArr[i]);
                if (doChooseConfig != null) {
                    return doChooseConfig;
                }
            }
            Log.e("device_policy", "Can not select an EGLConfig for rendering.");
            return null;
        }

        private EGLConfig doChooseConfig(EGL10 egl10, EGLDisplay eGLDisplay, int[] iArr) {
            EGLConfig[] eGLConfigArr = new EGLConfig[1];
            int[] iArr2 = new int[1];
            if (!egl10.eglChooseConfig(eGLDisplay, iArr, eGLConfigArr, 1, iArr2) || iArr2[0] <= 0) {
                return null;
            }
            return eGLConfigArr[0];
        }
    }
}
