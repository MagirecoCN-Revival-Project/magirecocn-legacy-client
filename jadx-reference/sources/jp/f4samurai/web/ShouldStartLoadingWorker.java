package jp.f4samurai.web;

import java.util.concurrent.CountDownLatch;

/* compiled from: WebViewImpl.java */
/* loaded from: classes.dex */
class ShouldStartLoadingWorker implements Runnable {
    private CountDownLatch mLatch;
    private boolean[] mResult;
    private final String mUrl;

    /* JADX INFO: Access modifiers changed from: package-private */
    public ShouldStartLoadingWorker(CountDownLatch countDownLatch, boolean[] zArr, String str) {
        this.mLatch = countDownLatch;
        this.mResult = zArr;
        this.mUrl = str;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.mResult[0] = WebViewHelper._shouldStartLoading(this.mUrl);
        this.mLatch.countDown();
    }
}
