package jp.f4samurai.camera;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.widget.FrameLayout;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import jp.f4samurai.AppActivity;

/* loaded from: classes.dex */
public class Camera2View extends CameraViewBase {
    private static final int MAX_PREVIEW_HEIGHT = 1080;
    private static final int MAX_PREVIEW_WIDTH = 1920;
    private static final SparseIntArray ORIENTATIONS;
    private static final int STATE_PICTURE_TAKEN = 4;
    private static final int STATE_PREVIEW = 0;
    private static final int STATE_WAITING_LOCK = 1;
    private static final int STATE_WAITING_NON_PRECAPTURE = 3;
    private static final int STATE_WAITING_PRECAPTURE = 2;
    private static final String TAG = "Camera2BasicFragment";
    private Handler mBackgroundHandler;
    private HandlerThread mBackgroundThread;
    private CameraDevice mCameraDevice;
    private String mCameraId;
    private Semaphore mCameraOpenCloseLock;
    private CameraViewBase mCameraView;
    private CameraCaptureSession.CaptureCallback mCaptureCallback;
    private CameraCaptureSession mCaptureSession;
    private Integer mFaceing;
    private boolean mFlashSupported;
    private ImageReader mImageReader;
    private final ImageReader.OnImageAvailableListener mOnImageAvailableListener;
    private CaptureRequest mPreviewRequest;
    private CaptureRequest.Builder mPreviewRequestBuilder;
    private Size mPreviewSize;
    private CameraScaler mScaler;
    private int mSensorOrientation;
    private int mState;
    private final CameraDevice.StateCallback mStateCallback;
    private Surface mSurface;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        ORIENTATIONS = sparseIntArray;
        sparseIntArray.append(0, 90);
        sparseIntArray.append(1, 0);
        sparseIntArray.append(2, 270);
        sparseIntArray.append(3, 180);
    }

    @Override // jp.f4samurai.camera.CameraViewBase, android.view.SurfaceHolder.Callback
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        super.surfaceCreated(surfaceHolder);
        this.mSurface = surfaceHolder.getSurface();
    }

    @Override // jp.f4samurai.camera.CameraViewBase, android.view.SurfaceHolder.Callback
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        super.surfaceChanged(surfaceHolder, i, i2, i3);
        this.mSurface = surfaceHolder.getSurface();
    }

    @Override // jp.f4samurai.camera.CameraViewBase, android.view.SurfaceHolder.Callback
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        super.surfaceDestroyed(surfaceHolder);
        closeCamera();
    }

    public Camera2View(Context context, FrameLayout frameLayout) {
        super(context, frameLayout);
        this.mFaceing = 1;
        this.mStateCallback = new CameraDevice.StateCallback() { // from class: jp.f4samurai.camera.Camera2View.1
            @Override // android.hardware.camera2.CameraDevice.StateCallback
            public void onOpened(CameraDevice cameraDevice) {
                Camera2View.this.mCameraOpenCloseLock.release();
                Camera2View.this.mCameraDevice = cameraDevice;
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() { // from class: jp.f4samurai.camera.Camera2View.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (Camera2View.this.mCameraDevice != null && Camera2View.this.mSurface != null) {
                            Camera2View.this.createCameraPreviewSession();
                        } else {
                            handler.postDelayed(this, 100L);
                        }
                    }
                }, 100L);
            }

            @Override // android.hardware.camera2.CameraDevice.StateCallback
            public void onClosed(CameraDevice cameraDevice) {
                super.onClosed(cameraDevice);
            }

            @Override // android.hardware.camera2.CameraDevice.StateCallback
            public void onDisconnected(CameraDevice cameraDevice) {
                Camera2View.this.mCameraOpenCloseLock.release();
                cameraDevice.close();
                Camera2View.this.mCameraDevice = null;
            }

            @Override // android.hardware.camera2.CameraDevice.StateCallback
            public void onError(CameraDevice cameraDevice, int i) {
                Camera2View.this.mCameraOpenCloseLock.release();
                cameraDevice.close();
                Camera2View.this.mCameraDevice = null;
            }
        };
        this.mOnImageAvailableListener = new ImageReader.OnImageAvailableListener() { // from class: jp.f4samurai.camera.Camera2View.2
            @Override // android.media.ImageReader.OnImageAvailableListener
            public void onImageAvailable(ImageReader imageReader) {
                Camera2View.this.mBackgroundHandler.post(new ImageSaver(imageReader.acquireNextImage(), Camera2View.this.mCameraView));
            }
        };
        this.mState = 0;
        this.mCameraOpenCloseLock = new Semaphore(1);
        this.mCaptureCallback = new CameraCaptureSession.CaptureCallback() { // from class: jp.f4samurai.camera.Camera2View.3
            private void process(CaptureResult captureResult) {
                int i = Camera2View.this.mState;
                if (i == 1) {
                    Integer num = (Integer) captureResult.get(CaptureResult.CONTROL_AF_STATE);
                    if (num == null) {
                        Camera2View.this.captureStillPicture();
                        return;
                    }
                    if (4 == num.intValue() || 5 == num.intValue()) {
                        Integer num2 = (Integer) captureResult.get(CaptureResult.CONTROL_AE_STATE);
                        if (num2 == null || num2.intValue() == 2) {
                            Camera2View.this.mState = 4;
                            Camera2View.this.captureStillPicture();
                            return;
                        } else {
                            Camera2View.this.runPrecaptureSequence();
                            return;
                        }
                    }
                    return;
                }
                if (i == 2) {
                    Integer num3 = (Integer) captureResult.get(CaptureResult.CONTROL_AE_STATE);
                    if (num3 == null || num3.intValue() == 5 || num3.intValue() == 4) {
                        Camera2View.this.mState = 3;
                        return;
                    }
                    return;
                }
                if (i != 3) {
                    return;
                }
                Integer num4 = (Integer) captureResult.get(CaptureResult.CONTROL_AE_STATE);
                if (num4 == null || num4.intValue() != 5) {
                    Camera2View.this.mState = 4;
                    Camera2View.this.captureStillPicture();
                }
            }

            @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
            public void onCaptureProgressed(CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, CaptureResult captureResult) {
                process(captureResult);
            }

            @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
            public void onCaptureCompleted(CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, TotalCaptureResult totalCaptureResult) {
                process(totalCaptureResult);
            }
        };
        this.mCameraView = this;
        startBackgroundThread();
        openCamera(getWidth(), getHeight());
    }

    @Override // jp.f4samurai.camera.CameraViewBase
    public void swap() {
        CameraManager cameraManager = (CameraManager) mActivity.getSystemService("camera");
        try {
            boolean z = false;
            for (String str : cameraManager.getCameraIdList()) {
                Integer num = (Integer) cameraManager.getCameraCharacteristics(str).get(CameraCharacteristics.LENS_FACING);
                if (num != null && num.intValue() == 0) {
                    z = true;
                }
            }
            if (z) {
                if (this.mFaceing.intValue() == 1) {
                    this.mFaceing = 0;
                } else {
                    this.mFaceing = 1;
                }
                closeCamera();
                openCamera(getWidth(), getHeight());
            }
        } catch (CameraAccessException unused) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0073  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00b5 A[ORIG_RETURN, RETURN] */
    @Override // jp.f4samurai.camera.CameraViewBase
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void zoom(float f) {
        Rect rect;
        CameraAccessException e;
        float f2;
        CameraScaler cameraScaler;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        try {
            AppActivity appActivity = mActivity;
            CameraCharacteristics cameraCharacteristics = ((CameraManager) AppActivity.getContext().getSystemService("camera")).getCameraCharacteristics(this.mCameraId);
            rect = (Rect) cameraCharacteristics.get(CameraCharacteristics.SENSOR_INFO_ACTIVE_ARRAY_SIZE);
            try {
                f2 = ((Float) cameraCharacteristics.get(CameraCharacteristics.SCALER_AVAILABLE_MAX_DIGITAL_ZOOM)).floatValue();
            } catch (CameraAccessException e2) {
                e = e2;
                e.printStackTrace();
                f2 = 1.0f;
                this.mScaler = new CameraScaler(f2, rect.width(), rect.height(), displayMetrics.heightPixels, displayMetrics.widthPixels);
                AppActivity appActivity2 = mActivity;
                ((CameraManager) AppActivity.getContext().getSystemService("camera")).getCameraCharacteristics(this.mCameraId);
                cameraScaler = this.mScaler;
                if (cameraScaler == null) {
                }
            }
        } catch (CameraAccessException e3) {
            rect = null;
            e = e3;
        }
        this.mScaler = new CameraScaler(f2, rect.width(), rect.height(), displayMetrics.heightPixels, displayMetrics.widthPixels);
        AppActivity appActivity22 = mActivity;
        try {
            ((CameraManager) AppActivity.getContext().getSystemService("camera")).getCameraCharacteristics(this.mCameraId);
            cameraScaler = this.mScaler;
            if (cameraScaler == null) {
                synchronized (cameraScaler) {
                    this.mScaler.zoom(Math.min(f, f2 - 0.1f));
                    try {
                        this.mPreviewRequestBuilder.set(CaptureRequest.SCALER_CROP_REGION, this.mScaler.getCurrentView());
                        this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, 4);
                        setAutoFlash(this.mPreviewRequestBuilder);
                        CaptureRequest build = this.mPreviewRequestBuilder.build();
                        this.mPreviewRequest = build;
                        this.mCaptureSession.setRepeatingRequest(build, this.mCaptureCallback, this.mBackgroundHandler);
                    } catch (CameraAccessException unused) {
                    }
                }
            }
        } catch (CameraAccessException unused2) {
        }
    }

    @Override // jp.f4samurai.camera.CameraViewBase
    public void takeScreenShot() {
        takePicture();
        super.takeScreenShot();
    }

    @Override // jp.f4samurai.camera.CameraViewBase
    public void onResume() {
        super.onResume();
        startBackgroundThread();
        openCamera(getWidth(), getHeight());
    }

    @Override // jp.f4samurai.camera.CameraViewBase
    public void onPause() {
        super.onPause();
        closeCamera();
        stopBackgroundThread();
    }

    private static Size chooseOptimalSize(Size[] sizeArr, int i, int i2, int i3, int i4, Size size) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        int width = size.getWidth();
        int height = size.getHeight();
        for (Size size2 : sizeArr) {
            if (size2.getWidth() <= i3 && size2.getHeight() <= i4 && size2.getHeight() == (size2.getWidth() * height) / width) {
                if (size2.getWidth() >= i && size2.getHeight() >= i2) {
                    arrayList.add(size2);
                } else {
                    arrayList2.add(size2);
                }
            }
        }
        if (arrayList.size() > 0) {
            return (Size) Collections.max(arrayList, new CompareSizesByArea());
        }
        if (arrayList2.size() > 0) {
            return (Size) Collections.max(arrayList2, new CompareSizesByArea());
        }
        return sizeArr[0];
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x0111 A[Catch: CameraAccessException | NullPointerException -> 0x0160, TryCatch #0 {CameraAccessException | NullPointerException -> 0x0160, blocks: (B:3:0x000c, B:5:0x0015, B:7:0x0025, B:11:0x0034, B:12:0x002a, B:15:0x0037, B:18:0x0046, B:20:0x004e, B:26:0x005e, B:27:0x007e, B:29:0x0096, B:30:0x00aa, B:36:0x00d1, B:38:0x00f9, B:40:0x0111, B:45:0x012c, B:48:0x0147, B:50:0x014b, B:51:0x015e, B:55:0x0143, B:68:0x0072, B:70:0x0056), top: B:2:0x000c }] */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0120  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0127  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0142  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x014b A[Catch: CameraAccessException | NullPointerException -> 0x0160, TryCatch #0 {CameraAccessException | NullPointerException -> 0x0160, blocks: (B:3:0x000c, B:5:0x0015, B:7:0x0025, B:11:0x0034, B:12:0x002a, B:15:0x0037, B:18:0x0046, B:20:0x004e, B:26:0x005e, B:27:0x007e, B:29:0x0096, B:30:0x00aa, B:36:0x00d1, B:38:0x00f9, B:40:0x0111, B:45:0x012c, B:48:0x0147, B:50:0x014b, B:51:0x015e, B:55:0x0143, B:68:0x0072, B:70:0x0056), top: B:2:0x000c }] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0143 A[Catch: CameraAccessException | NullPointerException -> 0x0160, TryCatch #0 {CameraAccessException | NullPointerException -> 0x0160, blocks: (B:3:0x000c, B:5:0x0015, B:7:0x0025, B:11:0x0034, B:12:0x002a, B:15:0x0037, B:18:0x0046, B:20:0x004e, B:26:0x005e, B:27:0x007e, B:29:0x0096, B:30:0x00aa, B:36:0x00d1, B:38:0x00f9, B:40:0x0111, B:45:0x012c, B:48:0x0147, B:50:0x014b, B:51:0x015e, B:55:0x0143, B:68:0x0072, B:70:0x0056), top: B:2:0x000c }] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x012a  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0123  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x011a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void setUpCameraOutputs(int i, int i2) {
        StreamConfigurationMap streamConfigurationMap;
        boolean z;
        boolean z2;
        Size maxPreviewAspectSize;
        int i3;
        int i4;
        Boolean bool;
        CameraManager cameraManager = (CameraManager) mActivity.getSystemService("camera");
        try {
            boolean z3 = false;
            for (String str : cameraManager.getCameraIdList()) {
                CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics(str);
                Integer num = (Integer) cameraCharacteristics.get(CameraCharacteristics.LENS_FACING);
                if ((num == null || num == this.mFaceing) && (streamConfigurationMap = (StreamConfigurationMap) cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)) != null) {
                    Size[] outputSizes = streamConfigurationMap.getOutputSizes(SurfaceHolder.class);
                    int length = outputSizes.length;
                    int i5 = 0;
                    while (true) {
                        z = true;
                        if (i5 >= length) {
                            z2 = false;
                            break;
                        }
                        Size size = outputSizes[i5];
                        if (size.getWidth() == MAX_PREVIEW_WIDTH && size.getHeight() == MAX_PREVIEW_HEIGHT) {
                            z2 = true;
                            break;
                        }
                        i5++;
                    }
                    if (z2) {
                        maxPreviewAspectSize = (Size) Collections.max(Arrays.asList(streamConfigurationMap.getOutputSizes(256)), new CompareSizesByArea());
                    } else {
                        maxPreviewAspectSize = getMaxPreviewAspectSize(Arrays.asList(streamConfigurationMap.getOutputSizes(256)));
                    }
                    ImageReader newInstance = ImageReader.newInstance(maxPreviewAspectSize.getWidth(), maxPreviewAspectSize.getHeight(), 256, 2);
                    this.mImageReader = newInstance;
                    newInstance.setOnImageAvailableListener(this.mOnImageAvailableListener, this.mBackgroundHandler);
                    if (!z2) {
                        maxPreviewAspectSize = (Size) Collections.max(Arrays.asList(streamConfigurationMap.getOutputSizes(256)), new CompareSizesByArea());
                    }
                    Size size2 = maxPreviewAspectSize;
                    int rotation = mActivity.getWindowManager().getDefaultDisplay().getRotation();
                    int intValue = ((Integer) cameraCharacteristics.get(CameraCharacteristics.SENSOR_ORIENTATION)).intValue();
                    this.mSensorOrientation = intValue;
                    if (rotation != 0) {
                        if (rotation != 1) {
                            if (rotation != 2) {
                                if (rotation != 3) {
                                    Log.e(TAG, "Display rotation is invalid: " + rotation);
                                    z = false;
                                    Point point = new Point();
                                    mActivity.getWindowManager().getDefaultDisplay().getRealSize(point);
                                    int i6 = point.x;
                                    int i7 = point.y;
                                    if (z) {
                                        i6 = point.y;
                                        i7 = point.x;
                                        i4 = i;
                                        i3 = i2;
                                    } else {
                                        i3 = i;
                                        i4 = i2;
                                    }
                                    this.mPreviewSize = chooseOptimalSize(streamConfigurationMap.getOutputSizes(SurfaceHolder.class), i3, i4, i6 > MAX_PREVIEW_WIDTH ? MAX_PREVIEW_WIDTH : i6, i7 > MAX_PREVIEW_HEIGHT ? MAX_PREVIEW_HEIGHT : i7, size2);
                                    bool = (Boolean) cameraCharacteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
                                    if (bool != null) {
                                        z3 = bool.booleanValue();
                                    }
                                    this.mFlashSupported = z3;
                                    if (!z2) {
                                        getHolder().setFixedSize(this.mPreviewSize.getWidth(), this.mPreviewSize.getHeight());
                                    }
                                    this.mCameraId = str;
                                    return;
                                }
                            }
                        }
                        if (intValue != 0) {
                            if (intValue == 180) {
                            }
                            z = false;
                        }
                        Point point2 = new Point();
                        mActivity.getWindowManager().getDefaultDisplay().getRealSize(point2);
                        int i62 = point2.x;
                        int i72 = point2.y;
                        if (z) {
                        }
                        this.mPreviewSize = chooseOptimalSize(streamConfigurationMap.getOutputSizes(SurfaceHolder.class), i3, i4, i62 > MAX_PREVIEW_WIDTH ? MAX_PREVIEW_WIDTH : i62, i72 > MAX_PREVIEW_HEIGHT ? MAX_PREVIEW_HEIGHT : i72, size2);
                        bool = (Boolean) cameraCharacteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
                        if (bool != null) {
                        }
                        this.mFlashSupported = z3;
                        if (!z2) {
                        }
                        this.mCameraId = str;
                        return;
                    }
                    if (intValue != 90) {
                        if (intValue == 270) {
                        }
                        z = false;
                    }
                    Point point22 = new Point();
                    mActivity.getWindowManager().getDefaultDisplay().getRealSize(point22);
                    int i622 = point22.x;
                    int i722 = point22.y;
                    if (z) {
                    }
                    this.mPreviewSize = chooseOptimalSize(streamConfigurationMap.getOutputSizes(SurfaceHolder.class), i3, i4, i622 > MAX_PREVIEW_WIDTH ? MAX_PREVIEW_WIDTH : i622, i722 > MAX_PREVIEW_HEIGHT ? MAX_PREVIEW_HEIGHT : i722, size2);
                    bool = (Boolean) cameraCharacteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
                    if (bool != null) {
                    }
                    this.mFlashSupported = z3;
                    if (!z2) {
                    }
                    this.mCameraId = str;
                    return;
                }
            }
        } catch (CameraAccessException | NullPointerException unused) {
        }
    }

    private void openCamera(int i, int i2) {
        setUpCameraOutputs(i, i2);
        CameraManager cameraManager = (CameraManager) mActivity.getSystemService("camera");
        try {
            if (!this.mCameraOpenCloseLock.tryAcquire(2500L, TimeUnit.MILLISECONDS)) {
                throw new RuntimeException("Time out waiting to lock camera opening.");
            }
            cameraManager.openCamera(this.mCameraId, this.mStateCallback, this.mBackgroundHandler);
        } catch (CameraAccessException unused) {
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted while trying to lock camera opening.", e);
        }
    }

    private void closeCamera() {
        try {
            try {
                this.mCameraOpenCloseLock.acquire();
                CameraCaptureSession cameraCaptureSession = this.mCaptureSession;
                if (cameraCaptureSession != null) {
                    cameraCaptureSession.close();
                    this.mCaptureSession = null;
                }
                CameraDevice cameraDevice = this.mCameraDevice;
                if (cameraDevice != null) {
                    cameraDevice.close();
                    this.mCameraDevice = null;
                }
                ImageReader imageReader = this.mImageReader;
                if (imageReader != null) {
                    imageReader.close();
                    this.mImageReader = null;
                }
            } catch (InterruptedException e) {
                throw new RuntimeException("Interrupted while trying to lock camera closing.", e);
            }
        } finally {
            this.mCameraOpenCloseLock.release();
        }
    }

    private void startBackgroundThread() {
        HandlerThread handlerThread = new HandlerThread("CameraBackground");
        this.mBackgroundThread = handlerThread;
        handlerThread.start();
        this.mBackgroundHandler = new Handler(this.mBackgroundThread.getLooper());
    }

    private void stopBackgroundThread() {
        this.mBackgroundThread.quitSafely();
        try {
            this.mBackgroundThread.join();
            this.mBackgroundThread = null;
            this.mBackgroundHandler = null;
        } catch (InterruptedException unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void createCameraPreviewSession() {
        try {
            CaptureRequest.Builder createCaptureRequest = this.mCameraDevice.createCaptureRequest(1);
            this.mPreviewRequestBuilder = createCaptureRequest;
            createCaptureRequest.addTarget(this.mSurface);
            this.mCameraDevice.createCaptureSession(Arrays.asList(this.mSurface, this.mImageReader.getSurface()), new CameraCaptureSession.StateCallback() { // from class: jp.f4samurai.camera.Camera2View.4
                @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
                public void onConfigured(CameraCaptureSession cameraCaptureSession) {
                    if (Camera2View.this.mCameraDevice == null) {
                        return;
                    }
                    Camera2View.this.mCaptureSession = cameraCaptureSession;
                    try {
                        CameraHelper._launchCallback(true, "カメラの起動に成功しました。");
                        Camera2View.this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, 4);
                        Camera2View camera2View = Camera2View.this;
                        camera2View.setAutoFlash(camera2View.mPreviewRequestBuilder);
                        Camera2View camera2View2 = Camera2View.this;
                        camera2View2.mPreviewRequest = camera2View2.mPreviewRequestBuilder.build();
                        Camera2View.this.mCaptureSession.setRepeatingRequest(Camera2View.this.mPreviewRequest, Camera2View.this.mCaptureCallback, Camera2View.this.mBackgroundHandler);
                    } catch (CameraAccessException | IllegalStateException unused) {
                    }
                }

                @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
                public void onConfigureFailed(CameraCaptureSession cameraCaptureSession) {
                    CameraHelper._launchCallback(false, "カメラの起動に失敗しました。");
                }

                @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
                public void onClosed(CameraCaptureSession cameraCaptureSession) {
                    if (Camera2View.this.mCaptureSession == null || !Camera2View.this.mCaptureSession.equals(cameraCaptureSession)) {
                        return;
                    }
                    Camera2View.this.mCaptureSession = null;
                }
            }, null);
        } catch (CameraAccessException unused) {
        }
    }

    private void takePicture() {
        lockFocus();
    }

    private void lockFocus() {
        try {
            this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER, 1);
            this.mState = 1;
            this.mCaptureSession.capture(this.mPreviewRequestBuilder.build(), this.mCaptureCallback, this.mBackgroundHandler);
        } catch (CameraAccessException unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void runPrecaptureSequence() {
        try {
            this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER, 1);
            this.mState = 2;
            this.mCaptureSession.capture(this.mPreviewRequestBuilder.build(), this.mCaptureCallback, this.mBackgroundHandler);
        } catch (CameraAccessException unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void captureStillPicture() {
        CameraDevice cameraDevice;
        try {
            if (mActivity != null && (cameraDevice = this.mCameraDevice) != null) {
                CaptureRequest.Builder createCaptureRequest = cameraDevice.createCaptureRequest(2);
                createCaptureRequest.addTarget(this.mImageReader.getSurface());
                createCaptureRequest.set(CaptureRequest.CONTROL_AF_MODE, 4);
                setAutoFlash(createCaptureRequest);
                createCaptureRequest.set(CaptureRequest.JPEG_ORIENTATION, Integer.valueOf(getOrientation(mActivity.getWindowManager().getDefaultDisplay().getRotation())));
                if (this.mScaler != null) {
                    createCaptureRequest.set(CaptureRequest.SCALER_CROP_REGION, this.mScaler.getCurrentView());
                }
                CameraCaptureSession.CaptureCallback captureCallback = new CameraCaptureSession.CaptureCallback() { // from class: jp.f4samurai.camera.Camera2View.5
                    @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
                    public void onCaptureCompleted(CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, TotalCaptureResult totalCaptureResult) {
                        Camera2View.this.unlockFocus();
                    }

                    @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
                    public void onCaptureFailed(CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, CaptureFailure captureFailure) {
                        super.onCaptureFailed(cameraCaptureSession, captureRequest, captureFailure);
                        Camera2View.this.terminateScreenShot();
                        CameraHelper._storeCallback(false, "撮影に失敗しました。");
                    }
                };
                this.mCaptureSession.stopRepeating();
                this.mCaptureSession.abortCaptures();
                this.mCaptureSession.capture(createCaptureRequest.build(), captureCallback, null);
            }
        } catch (CameraAccessException unused) {
        }
    }

    private int getOrientation(int i) {
        return ((ORIENTATIONS.get(i) + this.mSensorOrientation) + 270) % 360;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unlockFocus() {
        try {
            this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER, 2);
            setAutoFlash(this.mPreviewRequestBuilder);
            this.mCaptureSession.capture(this.mPreviewRequestBuilder.build(), this.mCaptureCallback, this.mBackgroundHandler);
            this.mState = 0;
            this.mCaptureSession.setRepeatingRequest(this.mPreviewRequest, this.mCaptureCallback, this.mBackgroundHandler);
        } catch (CameraAccessException unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setAutoFlash(CaptureRequest.Builder builder) {
        if (this.mFlashSupported) {
            builder.set(CaptureRequest.CONTROL_AE_MODE, 2);
        }
    }

    /* loaded from: classes.dex */
    private static class ImageSaver implements Runnable {
        private CameraViewBase mCameraView;
        private final Image mImage;

        ImageSaver(Image image, CameraViewBase cameraViewBase) {
            this.mImage = image;
            this.mCameraView = cameraViewBase;
        }

        @Override // java.lang.Runnable
        public void run() {
            ByteBuffer buffer = this.mImage.getPlanes()[0].getBuffer();
            int remaining = buffer.remaining();
            byte[] bArr = new byte[remaining];
            buffer.get(bArr);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 1;
            Bitmap decodeByteArray = BitmapFactory.decodeByteArray(bArr, 0, remaining, options);
            CameraViewBase cameraViewBase = this.mCameraView;
            Bitmap resize = cameraViewBase.resize(decodeByteArray, cameraViewBase.getGLSurfaceView().getWidth());
            decodeByteArray.recycle();
            this.mCameraView.setPhotoBitmap(resize);
            this.mImage.close();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class CompareSizesByArea implements Comparator<Size> {
        CompareSizesByArea() {
        }

        /* JADX DEBUG: Method merged with bridge method: compare(Ljava/lang/Object;Ljava/lang/Object;)I */
        @Override // java.util.Comparator
        public int compare(Size size, Size size2) {
            return Long.signum((size.getWidth() * size.getHeight()) - (size2.getWidth() * size2.getHeight()));
        }
    }

    /* loaded from: classes.dex */
    public class CameraScaler {
        private Rect current_rect;
        private int xCenter;
        private int xMax;
        private int yCenter;
        private int yMax;
        private float zoomCurrent;
        private float zoomMax;
        private final float ZOOM_MIN = 1.0f;
        private final int X_MIN = 0;
        private final int Y_MIN = 0;

        public CameraScaler(float f, int i, int i2, int i3, int i4) {
            this.xMax = i;
            this.yMax = i2;
            this.zoomMax = f;
            Rect rect = new Rect(0, 0, i, i2);
            this.current_rect = rect;
            this.zoomCurrent = 1.0f;
            this.xCenter = rect.centerX();
            this.yCenter = this.current_rect.centerY();
        }

        public void zoom(float f) {
            float f2 = this.zoomCurrent;
            if (f2 * f >= this.zoomMax || f2 * f <= 1.0f) {
                return;
            }
            this.zoomCurrent = f2 * f;
            int floor = (int) Math.floor((this.xMax / r0) / 2.0d);
            int floor2 = (int) Math.floor((this.yMax / this.zoomCurrent) / 2.0d);
            int i = this.xCenter;
            int i2 = this.yCenter;
            int i3 = i + floor;
            int i4 = this.xMax;
            if (i3 > i4) {
                i = i4 - floor;
            } else if (i - floor < 0) {
                i = floor;
            }
            int i5 = i2 + floor2;
            int i6 = this.yMax;
            if (i5 > i6) {
                i2 = i6 - floor2;
            } else if (i2 - floor2 < 0) {
                i2 = floor2;
            }
            this.current_rect.set(i - floor, i2 - floor2, i + floor, i2 + floor2);
            this.xCenter = this.current_rect.centerX();
            this.yCenter = this.current_rect.centerY();
        }

        public Rect getCurrentView() {
            return this.current_rect;
        }
    }

    private static Size getMaxPreviewAspectSize(List<Size> list) {
        Size size = new Size(0, 0);
        for (Size size2 : list) {
            int width = size2.getWidth();
            if (size2.getHeight() == (width * MAX_PREVIEW_HEIGHT) / MAX_PREVIEW_WIDTH && width > size.getWidth()) {
                size = size2;
            }
        }
        return size.getWidth() == 0 ? (Size) Collections.max(list, new CompareSizesByArea()) : size;
    }
}
