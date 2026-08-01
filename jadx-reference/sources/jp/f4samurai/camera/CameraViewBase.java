package jp.f4samurai.camera;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.opengl.GLException;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Handler;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.FrameLayout;
import java.nio.IntBuffer;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.opengles.GL10;
import jp.f4samurai.AppActivity;
import jp.f4samurai.utils.FileUtils;
import jp.f4samurai.utils.RuntimePermissionUtils;

/* loaded from: classes.dex */
public class CameraViewBase extends SurfaceView implements SurfaceHolder.Callback {
    protected static AppActivity mActivity;
    protected Bitmap mGLBitmap;
    protected GLCaptureReadyCallback mGLCaptureReadyCallback;
    protected GLSurfaceView mGLSurfaceView;
    protected Handler mHandler;
    protected FrameLayout mLayout;
    protected Bitmap mPhotoBitmap;
    protected Runnable mRunnable;
    private RuntimePermissionUtils.Callback mStoreCaptureCallback;
    protected Bitmap mSynthesizedBitmap;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public interface GLCaptureReadyCallback {
        void onGLCaptureReady(Bitmap bitmap);
    }

    public void onResume() {
    }

    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
    }

    public void surfaceCreated(SurfaceHolder surfaceHolder) {
    }

    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
    }

    public void swap() {
    }

    public void zoom(float f) {
    }

    public CameraViewBase(Context context, FrameLayout frameLayout) {
        super(context);
        this.mStoreCaptureCallback = new RuntimePermissionUtils.Callback() { // from class: jp.f4samurai.camera.CameraViewBase.1
            @Override // jp.f4samurai.utils.RuntimePermissionUtils.Callback
            public void onGranted() {
                CameraViewBase.this.storeCapture();
            }

            @Override // jp.f4samurai.utils.RuntimePermissionUtils.Callback
            public void onDenied() {
                CameraHelper._storeCallback(false, "ストレージへのアクセスに失敗しました。<br>端末の「設定」->「アプリ」->「マギレコ」->「権限」を選択して<br>ストレージアクセスを許可して下さい。");
            }
        };
        this.mGLCaptureReadyCallback = new GLCaptureReadyCallback() { // from class: jp.f4samurai.camera.CameraViewBase.4
            @Override // jp.f4samurai.camera.CameraViewBase.GLCaptureReadyCallback
            public void onGLCaptureReady(Bitmap bitmap) {
                CameraViewBase.this.mGLBitmap = bitmap;
            }
        };
        getHolder().addCallback(this);
        this.mLayout = frameLayout;
        mActivity = (AppActivity) context;
        this.mHandler = new Handler();
        this.mGLSurfaceView = mActivity.getGLSurfaceView();
    }

    public void takeScreenShot() {
        Bitmap bitmap = this.mPhotoBitmap;
        if (bitmap != null) {
            bitmap.recycle();
            this.mPhotoBitmap = null;
        }
        Bitmap bitmap2 = this.mGLBitmap;
        if (bitmap2 != null) {
            bitmap2.recycle();
            this.mGLBitmap = null;
        }
        Runnable runnable = this.mRunnable;
        if (runnable != null) {
            this.mHandler.removeCallbacks(runnable);
            this.mRunnable = null;
        }
        captureGL(this.mGLCaptureReadyCallback);
        Runnable runnable2 = new Runnable() { // from class: jp.f4samurai.camera.CameraViewBase.2
            @Override // java.lang.Runnable
            public void run() {
                if (CameraViewBase.this.mPhotoBitmap != null && CameraViewBase.this.mGLBitmap != null) {
                    CameraViewBase cameraViewBase = CameraViewBase.this;
                    cameraViewBase.mSynthesizedBitmap = cameraViewBase.synthesizeBitmap();
                    if (Build.VERSION.SDK_INT < 29) {
                        if (!RuntimePermissionUtils.hasSelfPermissions(CameraViewBase.mActivity, "android.permission.WRITE_EXTERNAL_STORAGE")) {
                            RuntimePermissionUtils.requestPermission(CameraViewBase.mActivity, "android.permission.WRITE_EXTERNAL_STORAGE", 2, CameraViewBase.this.mStoreCaptureCallback);
                            return;
                        } else {
                            CameraViewBase.this.storeCapture();
                            return;
                        }
                    }
                    CameraViewBase.this.storeCaptureByMediaApi();
                    return;
                }
                CameraViewBase.this.mHandler.postDelayed(this, 300L);
            }
        };
        this.mRunnable = runnable2;
        this.mHandler.post(runnable2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void terminateScreenShot() {
        Bitmap bitmap = this.mPhotoBitmap;
        if (bitmap != null) {
            bitmap.recycle();
            this.mPhotoBitmap = null;
        }
        Bitmap bitmap2 = this.mGLBitmap;
        if (bitmap2 != null) {
            bitmap2.recycle();
            this.mGLBitmap = null;
        }
        Runnable runnable = this.mRunnable;
        if (runnable != null) {
            this.mHandler.removeCallbacks(runnable);
            this.mRunnable = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void storeCapture() {
        if (FileUtils.canUseSd()) {
            FileUtils.saveToSd(mActivity, this.mSynthesizedBitmap);
            CameraHelper._storeCallback(true, "画像を保存しました。");
        } else {
            CameraHelper._storeCallback(false, "画像の保存に失敗しました。");
        }
        terminateStoreCapture();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void storeCaptureByMediaApi() {
        if (FileUtils.canReadSd()) {
            FileUtils.saveToMedia(mActivity, this.mSynthesizedBitmap);
            CameraHelper._storeCallback(true, "画像を保存しました。");
        } else {
            CameraHelper._storeCallback(false, "画像の保存に失敗しました。");
        }
        terminateStoreCapture();
    }

    private void terminateStoreCapture() {
        this.mSynthesizedBitmap.recycle();
        this.mPhotoBitmap.recycle();
        this.mGLBitmap.recycle();
        this.mPhotoBitmap = null;
        this.mGLBitmap = null;
        this.mSynthesizedBitmap = null;
    }

    public void onPause() {
        Runnable runnable = this.mRunnable;
        if (runnable != null) {
            this.mHandler.removeCallbacks(runnable);
            this.mRunnable = null;
        }
    }

    public GLSurfaceView getGLSurfaceView() {
        return this.mGLSurfaceView;
    }

    public void setPhotoBitmap(Bitmap bitmap) {
        this.mPhotoBitmap = bitmap;
    }

    protected Bitmap rotate(Bitmap bitmap) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        canvas.rotate(180.0f, r0 / 2, r1 / 2);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
        return createBitmap;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Bitmap resize(Bitmap bitmap, int i) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float f = i / width;
        matrix.setScale(f, f);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }

    protected void captureGL(final GLCaptureReadyCallback gLCaptureReadyCallback) {
        mActivity.runOnGLThread(new Runnable() { // from class: jp.f4samurai.camera.CameraViewBase.3
            @Override // java.lang.Runnable
            public void run() {
                GL10 gl10 = (GL10) ((EGL10) EGLContext.getEGL()).eglGetCurrentContext().getGL();
                CameraViewBase cameraViewBase = CameraViewBase.this;
                final Bitmap createBitmapFromGLSurface = cameraViewBase.createBitmapFromGLSurface(0, 0, cameraViewBase.mGLSurfaceView.getWidth(), CameraViewBase.this.mGLSurfaceView.getHeight(), gl10);
                CameraViewBase.mActivity.runOnUiThread(new Runnable() { // from class: jp.f4samurai.camera.CameraViewBase.3.1
                    @Override // java.lang.Runnable
                    public void run() {
                        gLCaptureReadyCallback.onGLCaptureReady(createBitmapFromGLSurface);
                    }
                });
            }
        });
    }

    protected Bitmap createBitmapFromGLSurface(int i, int i2, int i3, int i4, GL10 gl10) {
        int i5 = i3 * i4;
        int[] iArr = new int[i5];
        int[] iArr2 = new int[i5];
        IntBuffer wrap = IntBuffer.wrap(iArr);
        wrap.position(0);
        try {
            gl10.glReadPixels(i, i2, i3, i4, 6408, 5121, wrap);
            for (int i6 = 0; i6 < i4; i6++) {
                int i7 = i6 * i3;
                int i8 = ((i4 - i6) - 1) * i3;
                for (int i9 = 0; i9 < i3; i9++) {
                    int i10 = iArr[i7 + i9];
                    iArr2[i8 + i9] = (i10 & (-16711936)) | ((i10 << 16) & 16711680) | ((i10 >> 16) & 255);
                }
            }
            return Bitmap.createBitmap(iArr2, i3, i4, Bitmap.Config.ARGB_8888);
        } catch (GLException unused) {
            return null;
        }
    }

    protected Bitmap synthesizeBitmap() {
        Bitmap createBitmap = Bitmap.createBitmap(this.mGLSurfaceView.getWidth(), this.mGLSurfaceView.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawBitmap(this.mPhotoBitmap, 0.0f, (this.mGLSurfaceView.getHeight() - this.mPhotoBitmap.getHeight()) / 2, (Paint) null);
        canvas.drawBitmap(this.mGLBitmap, 0.0f, 0.0f, (Paint) null);
        return createBitmap;
    }
}
