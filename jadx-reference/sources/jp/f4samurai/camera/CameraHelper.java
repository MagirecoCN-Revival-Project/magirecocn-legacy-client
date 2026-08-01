package jp.f4samurai.camera;

import android.os.Build;
import android.widget.FrameLayout;
import jp.f4samurai.AppActivity;
import jp.f4samurai.utils.RuntimePermissionUtils;

/* loaded from: classes.dex */
public class CameraHelper {
    private static final String TAG = "CameraHelper";
    private static RuntimePermissionUtils.Callback mCameraCallback = new RuntimePermissionUtils.Callback() { // from class: jp.f4samurai.camera.CameraHelper.1
        @Override // jp.f4samurai.utils.RuntimePermissionUtils.Callback
        public void onGranted() {
            CameraHelper.sAppActivity.runOnUiThread(new Runnable() { // from class: jp.f4samurai.camera.CameraHelper.1.1
                @Override // java.lang.Runnable
                public void run() {
                    if (CameraHelper.mCameraView == null) {
                        CameraHelper.launch();
                    }
                }
            });
        }

        @Override // jp.f4samurai.utils.RuntimePermissionUtils.Callback
        public void onDenied() {
            CameraHelper._launchCallback(false, "カメラへのアクセスに失敗しました。<br>端末の「設定」->「アプリ」->「マギレコ」->「権限」を選択して<br>カメラアクセスを許可して下さい。");
        }
    };
    private static CameraViewBase mCameraView;
    private static FrameLayout mFrameLayout;
    private static AppActivity sAppActivity;

    public static native void launchCallback(boolean z, String str);

    public static native void storeCallback(boolean z, String str);

    public CameraHelper(FrameLayout frameLayout) {
        mFrameLayout = frameLayout;
        sAppActivity = (AppActivity) AppActivity.getContext();
    }

    public static void launch() {
        if (Build.VERSION.SDK_INT >= 21) {
            mCameraView = new Camera2View(sAppActivity, mFrameLayout);
        } else {
            mCameraView = new CameraView(sAppActivity, mFrameLayout);
        }
        mFrameLayout.addView(mCameraView, 1);
    }

    public static void add() {
        sAppActivity.runOnUiThread(new Runnable() { // from class: jp.f4samurai.camera.CameraHelper.2
            @Override // java.lang.Runnable
            public void run() {
                if (CameraHelper.mCameraView == null) {
                    if (!RuntimePermissionUtils.hasSelfPermissions(CameraHelper.sAppActivity, "android.permission.CAMERA")) {
                        RuntimePermissionUtils.requestPermission(CameraHelper.sAppActivity, "android.permission.CAMERA", 1, CameraHelper.mCameraCallback);
                    } else {
                        CameraHelper.launch();
                    }
                }
            }
        });
    }

    public static void remove() {
        sAppActivity.runOnUiThread(new Runnable() { // from class: jp.f4samurai.camera.CameraHelper.3
            @Override // java.lang.Runnable
            public void run() {
                if (CameraHelper.mCameraView != null) {
                    CameraHelper.mFrameLayout.removeView(CameraHelper.mCameraView);
                    CameraHelper.mCameraView = null;
                }
            }
        });
    }

    public static void swap() {
        sAppActivity.runOnUiThread(new Runnable() { // from class: jp.f4samurai.camera.CameraHelper.4
            @Override // java.lang.Runnable
            public void run() {
                if (CameraHelper.mCameraView != null) {
                    CameraHelper.mCameraView.swap();
                }
            }
        });
    }

    public static void zoom(final float f) {
        sAppActivity.runOnUiThread(new Runnable() { // from class: jp.f4samurai.camera.CameraHelper.5
            @Override // java.lang.Runnable
            public void run() {
                if (CameraHelper.mCameraView != null) {
                    CameraHelper.mCameraView.zoom(f);
                }
            }
        });
    }

    public static void takeScreenShot() {
        sAppActivity.runOnUiThread(new Runnable() { // from class: jp.f4samurai.camera.CameraHelper.6
            @Override // java.lang.Runnable
            public void run() {
                if (CameraHelper.mCameraView != null) {
                    CameraHelper.mCameraView.takeScreenShot();
                }
            }
        });
    }

    public static void onPause() {
        sAppActivity.runOnUiThread(new Runnable() { // from class: jp.f4samurai.camera.CameraHelper.7
            @Override // java.lang.Runnable
            public void run() {
                if (CameraHelper.mCameraView != null) {
                    CameraHelper.mCameraView.onPause();
                }
            }
        });
    }

    public static void onResume() {
        sAppActivity.runOnUiThread(new Runnable() { // from class: jp.f4samurai.camera.CameraHelper.8
            @Override // java.lang.Runnable
            public void run() {
                if (CameraHelper.mCameraView != null) {
                    CameraHelper.mCameraView.onResume();
                }
            }
        });
    }

    public static void _launchCallback(boolean z, String str) {
        launchCallback(z, str);
    }

    public static void _storeCallback(boolean z, String str) {
        storeCallback(z, str);
    }
}
