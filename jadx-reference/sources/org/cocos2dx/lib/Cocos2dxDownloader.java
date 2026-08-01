package org.cocos2dx.lib;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.message.BasicHeader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import javax.net.ssl.SSLException;

/* loaded from: classes2.dex */
public class Cocos2dxDownloader {
    private static HashMap<String, Boolean> _resumingSupport = new HashMap<>();
    private int _countOfMaxProcessingTasks;
    private int _id;
    private String _tempFileNameSufix;
    private AsyncHttpClient _httpClient = new AsyncHttpClient();
    private HashMap _taskMap = new HashMap();
    private Queue<Runnable> _taskQueue = new LinkedList();
    private int _runningTaskCount = 0;

    native void nativeOnFinish(int i, int i2, int i3, String str, byte[] bArr);

    native void nativeOnProgress(int i, int i2, long j, long j2, long j3);

    /* JADX INFO: Access modifiers changed from: package-private */
    public void onProgress(final int i, final long j, final long j2, final long j3) {
        DownloadTask downloadTask = (DownloadTask) this._taskMap.get(Integer.valueOf(i));
        if (downloadTask != null) {
            downloadTask.bytesReceived = j;
            downloadTask.totalBytesReceived = j2;
            downloadTask.totalBytesExpected = j3;
        }
        Cocos2dxHelper.runOnGLThread(new Runnable() { // from class: org.cocos2dx.lib.Cocos2dxDownloader.1
            @Override // java.lang.Runnable
            public void run() {
                Cocos2dxDownloader cocos2dxDownloader = Cocos2dxDownloader.this;
                cocos2dxDownloader.nativeOnProgress(cocos2dxDownloader._id, i, j, j2, j3);
            }
        });
    }

    public void onStart(int i) {
        DownloadTask downloadTask = (DownloadTask) this._taskMap.get(Integer.valueOf(i));
        if (downloadTask != null) {
            downloadTask.resetStatus();
        }
    }

    public void onFinish(final int i, final int i2, final String str, final byte[] bArr) {
        if (((DownloadTask) this._taskMap.get(Integer.valueOf(i))) == null) {
            return;
        }
        this._taskMap.remove(Integer.valueOf(i));
        Cocos2dxHelper.runOnGLThread(new Runnable() { // from class: org.cocos2dx.lib.Cocos2dxDownloader.2
            @Override // java.lang.Runnable
            public void run() {
                Cocos2dxDownloader cocos2dxDownloader = Cocos2dxDownloader.this;
                cocos2dxDownloader.nativeOnFinish(cocos2dxDownloader._id, i, i2, str, bArr);
            }
        });
    }

    public static void setResumingSupport(String str, Boolean bool) {
        _resumingSupport.put(str, bool);
    }

    public static Cocos2dxDownloader createDownloader(int i, int i2, String str, int i3) {
        Cocos2dxDownloader cocos2dxDownloader = new Cocos2dxDownloader();
        cocos2dxDownloader._id = i;
        cocos2dxDownloader._httpClient.setEnableRedirects(true);
        if (i2 > 0) {
            cocos2dxDownloader._httpClient.setTimeout(i2 * 1000);
        }
        AsyncHttpClient.allowRetryExceptionClass(SSLException.class);
        cocos2dxDownloader._httpClient.setURLEncodingEnabled(false);
        cocos2dxDownloader._tempFileNameSufix = str;
        cocos2dxDownloader._countOfMaxProcessingTasks = i3;
        return cocos2dxDownloader;
    }

    public static void createTask(final Cocos2dxDownloader cocos2dxDownloader, final int i, final String str, final String str2) {
        cocos2dxDownloader.enqueueTask(new Runnable() { // from class: org.cocos2dx.lib.Cocos2dxDownloader.3
            @Override // java.lang.Runnable
            public void run() {
                DownloadTask downloadTask = new DownloadTask();
                if (str2.length() == 0) {
                    downloadTask.handler = new DataTaskHandler(cocos2dxDownloader, i);
                    downloadTask.handle = cocos2dxDownloader._httpClient.get(Cocos2dxHelper.getActivity(), str, downloadTask.handler);
                }
                if (str2.length() != 0) {
                    try {
                        String host = new URI(str).getHost();
                        if (host.startsWith("www.")) {
                            host = host.substring(4);
                        }
                        String str3 = host;
                        Boolean bool = false;
                        Boolean bool2 = true;
                        if (Cocos2dxDownloader._resumingSupport.containsKey(str3)) {
                            bool = (Boolean) Cocos2dxDownloader._resumingSupport.get(str3);
                            bool2 = false;
                        }
                        if (!bool2.booleanValue()) {
                            File file = new File(str2 + cocos2dxDownloader._tempFileNameSufix);
                            if (!file.isDirectory()) {
                                File parentFile = file.getParentFile();
                                if (parentFile.isDirectory() || parentFile.mkdirs()) {
                                    File file2 = new File(str2);
                                    if (!file2.isDirectory()) {
                                        downloadTask.handler = new FileTaskHandler(cocos2dxDownloader, i, file, file2);
                                        Header[] headerArr = null;
                                        long length = file.length();
                                        if (bool.booleanValue() && length > 0) {
                                            ArrayList arrayList = new ArrayList();
                                            arrayList.add(new BasicHeader("Range", "bytes=" + length + "-"));
                                            headerArr = (Header[]) arrayList.toArray(new Header[arrayList.size()]);
                                        } else if (length > 0) {
                                            try {
                                                PrintWriter printWriter = new PrintWriter(file);
                                                printWriter.print("");
                                                printWriter.close();
                                            } catch (FileNotFoundException unused) {
                                            }
                                        }
                                        downloadTask.handle = cocos2dxDownloader._httpClient.get(Cocos2dxHelper.getActivity(), str, headerArr, (RequestParams) null, downloadTask.handler);
                                    }
                                }
                            }
                        } else {
                            downloadTask.handler = new HeadTaskHandler(cocos2dxDownloader, i, str3, str, str2);
                            downloadTask.handle = cocos2dxDownloader._httpClient.head(Cocos2dxHelper.getActivity(), str, null, null, downloadTask.handler);
                        }
                    } catch (URISyntaxException unused2) {
                    }
                }
                if (downloadTask.handle != null) {
                    cocos2dxDownloader._taskMap.put(Integer.valueOf(i), downloadTask);
                    return;
                }
                final String str4 = "Can't create DownloadTask for " + str;
                Cocos2dxHelper.runOnGLThread(new Runnable() { // from class: org.cocos2dx.lib.Cocos2dxDownloader.3.1
                    @Override // java.lang.Runnable
                    public void run() {
                        cocos2dxDownloader.nativeOnFinish(cocos2dxDownloader._id, i, 0, str4, null);
                    }
                });
            }
        });
    }

    public static void cancelAllRequests(Cocos2dxDownloader cocos2dxDownloader) {
        Cocos2dxHelper.getActivity().runOnUiThread(new Runnable() { // from class: org.cocos2dx.lib.Cocos2dxDownloader.4
            @Override // java.lang.Runnable
            public void run() {
                Iterator it = Cocos2dxDownloader.this._taskMap.entrySet().iterator();
                while (it.hasNext()) {
                    DownloadTask downloadTask = (DownloadTask) ((Map.Entry) it.next()).getValue();
                    if (downloadTask.handle != null) {
                        downloadTask.handle.cancel(true);
                    }
                }
            }
        });
    }

    public void enqueueTask(Runnable runnable) {
        synchronized (this._taskQueue) {
            if (this._runningTaskCount < this._countOfMaxProcessingTasks) {
                Cocos2dxHelper.getActivity().runOnUiThread(runnable);
                this._runningTaskCount++;
            } else {
                this._taskQueue.add(runnable);
            }
        }
    }

    public void runNextTaskIfExists() {
        synchronized (this._taskQueue) {
            Runnable poll = this._taskQueue.poll();
            if (poll != null) {
                Cocos2dxHelper.getActivity().runOnUiThread(poll);
            } else {
                this._runningTaskCount--;
            }
        }
    }
}
