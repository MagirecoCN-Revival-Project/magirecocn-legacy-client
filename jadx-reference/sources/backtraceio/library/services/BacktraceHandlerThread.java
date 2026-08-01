package backtraceio.library.services;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import backtraceio.library.common.BacktraceSerializeHelper;
import backtraceio.library.interfaces.Api;
import backtraceio.library.logger.BacktraceLogger;
import backtraceio.library.models.BacktraceResult;

/* loaded from: classes.dex */
public class BacktraceHandlerThread extends HandlerThread {
    private static final transient String LOG_TAG = "BacktraceHandlerThread";
    private BacktraceHandler mHandler;
    private SummedEventsHandler mSummedEventsHandler;
    private UniqueEventsHandler mUniqueEventsHandler;
    private final String url;

    /* JADX INFO: Access modifiers changed from: package-private */
    public BacktraceHandlerThread(String name, String url) {
        super(name);
        this.url = url;
        start();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public UniqueEventsHandler createUniqueEventsHandler(BacktraceMetrics backtraceMetrics, Api api) {
        UniqueEventsHandler uniqueEventsHandler = new UniqueEventsHandler(backtraceMetrics, api, this);
        this.mUniqueEventsHandler = uniqueEventsHandler;
        return uniqueEventsHandler;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public SummedEventsHandler createSummedEventsHandler(BacktraceMetrics backtraceMetrics, Api api) {
        SummedEventsHandler summedEventsHandler = new SummedEventsHandler(backtraceMetrics, api, this);
        this.mSummedEventsHandler = summedEventsHandler;
        return summedEventsHandler;
    }

    @Override // android.os.HandlerThread
    protected void onLooperPrepared() {
        super.onLooperPrepared();
        this.mHandler = new BacktraceHandler(getLooper(), this.url);
    }

    Message createMessage(BacktraceHandlerInput data) {
        Message message = new Message();
        message.obj = data;
        return message;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void sendReport(BacktraceHandlerInputReport data) {
        this.mHandler.sendMessage(createMessage(data));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void sendUniqueEvents(BacktraceHandlerInputEvents data) {
        this.mUniqueEventsHandler.sendMessage(createMessage(data));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void sendSummedEvents(BacktraceHandlerInputEvents data) {
        this.mSummedEventsHandler.sendMessage(createMessage(data));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class BacktraceHandler extends Handler {
        private final transient String LOG_TAG;
        String url;

        private BacktraceHandler(Looper looper, String url) {
            super(looper);
            this.LOG_TAG = BacktraceHandler.class.getSimpleName();
            this.url = url;
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            BacktraceResult sendReport;
            BacktraceHandlerInputReport backtraceHandlerInputReport = (BacktraceHandlerInputReport) msg.obj;
            if (backtraceHandlerInputReport.requestHandler != null) {
                BacktraceLogger.d(this.LOG_TAG, "Sending using custom request handler");
                sendReport = backtraceHandlerInputReport.requestHandler.onRequest(backtraceHandlerInputReport.data);
            } else {
                BacktraceLogger.d(this.LOG_TAG, "Sending report using default request handler");
                sendReport = BacktraceReportSender.sendReport(this.url, BacktraceSerializeHelper.toJson(backtraceHandlerInputReport.data), backtraceHandlerInputReport.data.getAttachments(), backtraceHandlerInputReport.data.report, backtraceHandlerInputReport.serverErrorEventListener);
            }
            if (backtraceHandlerInputReport.serverResponseEventListener != null) {
                BacktraceLogger.d(this.LOG_TAG, "Processing result using custom event");
                backtraceHandlerInputReport.serverResponseEventListener.onEvent(sendReport);
            }
        }
    }
}
