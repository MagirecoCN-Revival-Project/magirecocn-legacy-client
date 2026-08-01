package jp.f4samurai.camera;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.widget.FrameLayout;
import java.util.List;

/* loaded from: classes.dex */
public class CameraView extends CameraViewBase implements SurfaceHolder.Callback, Camera.PictureCallback {
    private Camera mCamera;
    private int mCameraFacing;
    private SurfaceHolder mHolder;

    public CameraView(Context context, FrameLayout frameLayout) {
        super(context, frameLayout);
        this.mCamera = null;
        this.mCameraFacing = 0;
        this.mHolder = getHolder();
    }

    @Override // jp.f4samurai.camera.CameraViewBase, android.view.SurfaceHolder.Callback
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        super.surfaceCreated(surfaceHolder);
        Camera createCamera = createCamera();
        this.mCamera = createCamera;
        try {
            createCamera.setPreviewDisplay(this.mHolder);
        } catch (Exception unused) {
            Camera camera = this.mCamera;
            if (camera != null) {
                camera.release();
                this.mCamera = null;
            }
        }
    }

    @Override // jp.f4samurai.camera.CameraViewBase, android.view.SurfaceHolder.Callback
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        super.surfaceChanged(surfaceHolder, i, i2, i3);
        this.mCamera.startPreview();
        CameraHelper._launchCallback(true, "カメラの起動に成功しました。");
    }

    @Override // jp.f4samurai.camera.CameraViewBase, android.view.SurfaceHolder.Callback
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        Camera camera = this.mCamera;
        if (camera != null) {
            deleteCamera(camera);
            this.mCamera = null;
        }
        super.surfaceDestroyed(surfaceHolder);
    }

    public Camera createCamera() {
        Camera open = Camera.open(this.mCameraFacing);
        Camera.Parameters parameters = open.getParameters();
        if (hasFeatureAutoFocus() && parameters.getSupportedFocusModes().contains("continuous-picture")) {
            parameters.setFocusMode("continuous-picture");
        }
        List<Camera.Size> supportedPictureSizes = parameters.getSupportedPictureSizes();
        List<Camera.Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
        Camera.Size size = supportedPictureSizes.get(0);
        Camera.Size size2 = supportedPreviewSizes.get(0);
        parameters.setPictureSize(size.width, size.height);
        parameters.setPreviewSize(size2.width, size2.height);
        open.setParameters(parameters);
        return open;
    }

    public void deleteCamera(Camera camera) {
        camera.setPreviewCallback(null);
        camera.stopPreview();
        camera.release();
    }

    public boolean hasFeatureAutoFocus() {
        return mActivity.getApplicationContext().getPackageManager().hasSystemFeature("android.hardware.camera.autofocus");
    }

    public void switchCameraId() {
        if (Camera.getNumberOfCameras() < 2) {
            this.mCameraFacing = 0;
        } else if (this.mCameraFacing == 0) {
            this.mCameraFacing = 1;
        } else {
            this.mCameraFacing = 0;
        }
    }

    @Override // jp.f4samurai.camera.CameraViewBase
    public void swap() {
        Camera camera = this.mCamera;
        if (camera != null) {
            deleteCamera(camera);
            switchCameraId();
            Camera createCamera = createCamera();
            this.mCamera = createCamera;
            try {
                createCamera.setPreviewDisplay(this.mHolder);
                this.mCamera.startPreview();
            } catch (Exception unused) {
                Camera camera2 = this.mCamera;
                if (camera2 != null) {
                    camera2.release();
                    this.mCamera = null;
                }
            }
        }
    }

    @Override // jp.f4samurai.camera.CameraViewBase
    public void zoom(float f) {
        Camera camera = this.mCamera;
        if (camera != null) {
            Camera.Parameters parameters = camera.getParameters();
            if (parameters.isZoomSupported()) {
                parameters.setZoom(Math.max(1, Math.min((((int) ((f - 1.0f) * 10.0f)) * 5) + 1, parameters.getMaxZoom())));
                this.mCamera.setParameters(parameters);
            }
        }
    }

    @Override // jp.f4samurai.camera.CameraViewBase
    public void takeScreenShot() {
        takePicture();
        super.takeScreenShot();
    }

    private void takePicture() {
        this.mCamera.takePicture(null, null, this);
    }

    @Override // android.hardware.Camera.PictureCallback
    public void onPictureTaken(byte[] bArr, Camera camera) {
        try {
            this.mCamera.startPreview();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 1;
            Bitmap decodeByteArray = BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
            this.mPhotoBitmap = resize(decodeByteArray, this.mGLSurfaceView.getWidth());
            decodeByteArray.recycle();
        } catch (Exception unused) {
        }
    }
}
