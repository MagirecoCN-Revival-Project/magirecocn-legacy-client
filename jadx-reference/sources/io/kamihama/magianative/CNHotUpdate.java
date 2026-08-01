package io.kamihama.magianative;

import cz.msebera.android.httpclient.protocol.HTTP;
import io.kamihama.magianative.CNChunkedDownload;
import io.kamihama.magianative.CNMirrors;
import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public final class CNHotUpdate {
    private static final int CONNECT_TIMEOUT_MS = 15000;
    private static final int MAX_ATTEMPTS = 4;
    private static final int READ_TIMEOUT_MS = 30000;
    private static final String TAG = "MagiaCNHotUpdate";

    private CNHotUpdate() {
    }

    public static boolean download(String str, String str2, String str3, int i) {
        if (str == null || str2 == null) {
            CNLog.e(TAG, "参数为空，放弃下载 url=" + str + " dest=" + str2);
            return false;
        }
        File file = new File(str2);
        if (file.isFile()) {
            CNLog.i(TAG, "目标已存在，跳过下载: " + str2);
            markDone(i);
            return true;
        }
        String mainLineFileName = mainLineFileName(str);
        if (mainLineFileName == null) {
            CNLog.i(TAG, "非主线地址，直连下载: " + str);
            try {
                singleStream(str, file, i, false);
                markDone(i);
                return true;
            } catch (Throwable th) {
                CNLog.e(TAG, "直连下载失败: " + str, th);
                return false;
            }
        }
        if (!CNMirrors.isLoaded()) {
            CNMirrors.refresh(false);
            if (!CNMirrors.isLoaded()) {
                CNMirrors.refresh(true);
            }
        }
        CNLog.i(TAG, "开始下载 " + str3 + " file=" + mainLineFileName + " 可用线路=" + CNMirrors.healthy().size());
        for (int i2 = 1; i2 <= 4; i2++) {
            if (Thread.currentThread().isInterrupted()) {
                CNLog.w(TAG, "线程被中断，放弃 " + mainLineFileName);
                markFailed(i);
                return false;
            }
            CNMirrors.Mirror pick = CNMirrors.pick(i2);
            boolean z = i2 % 2 == 0;
            String urlFor = pick.urlFor(mainLineFileName);
            CNCNDownloadUI.setDownloadSpeed(i, 0.0f);
            try {
                fetch(urlFor, file, i, z, pick);
                CNMirrors.reportSuccess(pick);
                markDone(i);
                CNLog.i(TAG, "下载完成 " + mainLineFileName + " attempt=" + i2 + " mirror=" + pick.name);
                return true;
            } catch (Throwable th2) {
                CNMirrors.reportFailure(pick, String.valueOf(th2.getMessage()));
                CNLog.w(TAG, "下载失败 " + mainLineFileName + " attempt=" + i2 + " mirror=" + pick.name, th2);
                if (i2 < 4) {
                    long j = 2000 << (i2 - 1);
                    CNLog.i(TAG, "等待 " + j + "ms 后换线重试");
                    try {
                        Thread.sleep(j);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        markFailed(i);
                        return false;
                    }
                }
            }
        }
        CNLog.e(TAG, "全部线路均失败: " + mainLineFileName);
        markFailed(i);
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0036  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static void fetch(String str, File file, int i, boolean z, CNMirrors.Mirror mirror) throws IOException {
        int i2;
        int effectiveChunks = mirror.effectiveChunks();
        if (effectiveChunks > 1) {
            CNChunkedDownload.Probe probe = CNChunkedDownload.probe(str, z);
            if (probe.rangeSupported && probe.total > 0) {
                long minChunkBytes = CNMirrors.minChunkBytes();
                if (minChunkBytes > 0) {
                    long j = probe.total / minChunkBytes;
                    if (j < effectiveChunks) {
                        i2 = (int) Math.max(1L, j);
                        if (i2 > 1) {
                            CNLog.i(TAG, "分片下载 " + file.getName() + " chunks=" + i2 + " bytes=" + probe.total + " mirror=" + mirror.name);
                            CNCNDownloadUI.setFileSize(i, (float) (probe.total / 1000000.0d));
                            CNChunkedDownload.download(str, file, i2, z, probe, new HotSink(i));
                            return;
                        }
                    }
                }
                i2 = effectiveChunks;
                if (i2 > 1) {
                }
            }
            CNLog.i(TAG, "不支持 Range 或文件过小，改用单线程: " + file.getName());
        }
        singleStream(str, file, i, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static final class HotSink implements CNChunkedDownload.Sink {
        private final int index;

        HotSink(int i) {
            this.index = i;
        }

        @Override // io.kamihama.magianative.CNChunkedDownload.Sink
        public void onTotal(long j) {
            CNCNDownloadUI.setFileSize(this.index, (float) (j / 1000000.0d));
        }

        @Override // io.kamihama.magianative.CNChunkedDownload.Sink
        public void onProgress(long j, long j2) {
            CNCNDownloadUI.setFileDownloaded(this.index, (float) (j / 1000000.0d));
            CNCNDownloadUI.updateFileProgress(this.index, j2 > 0 ? (int) Math.min(100L, Math.max(0L, (j * 100) / j2)) : 0);
        }

        @Override // io.kamihama.magianative.CNChunkedDownload.Sink
        public void onSpeed(float f) {
            CNCNDownloadUI.setDownloadSpeed(this.index, f);
        }

        @Override // io.kamihama.magianative.CNChunkedDownload.Sink
        public boolean isCancelled() {
            return Thread.currentThread().isInterrupted();
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Repeating lines: [267=4] */
    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:110:0x0264 */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0, types: [int] */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v2 */
    private static void singleStream(String str, File file, int i, boolean z) throws IOException {
        Throwable th;
        FileOutputStream fileOutputStream;
        BufferedInputStream bufferedInputStream;
        long parseLong;
        boolean z2;
        BufferedInputStream bufferedInputStream2;
        FileOutputStream fileOutputStream2;
        ?? r2 = i;
        File file2 = new File(file.getPath() + ".part");
        File parentFile = file2.getParentFile();
        if (parentFile != null && !parentFile.isDirectory() && !parentFile.mkdirs() && !parentFile.isDirectory()) {
            throw new IOException("无法创建下载目录: " + parentFile);
        }
        long length = file2.isFile() ? file2.length() : 0L;
        URL url = new URL(str);
        HttpURLConnection httpURLConnection = (HttpURLConnection) (z ? url.openConnection(Proxy.NO_PROXY) : url.openConnection());
        httpURLConnection.setConnectTimeout(CONNECT_TIMEOUT_MS);
        httpURLConnection.setReadTimeout(READ_TIMEOUT_MS);
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setInstanceFollowRedirects(true);
        httpURLConnection.setRequestProperty("Accept-Encoding", HTTP.IDENTITY_CODING);
        httpURLConnection.setRequestProperty("Connection", "close");
        if (length > 0) {
            httpURLConnection.setRequestProperty("Range", "bytes=" + length + "-");
        }
        try {
            int responseCode = httpURLConnection.getResponseCode();
            try {
                if (length > 0 && responseCode == 206) {
                    try {
                        long parseLong2 = parseLong(httpURLConnection.getHeaderField("Content-Length"), -1L);
                        parseLong = parseLong2 >= 0 ? length + parseLong2 : -1L;
                        z2 = true;
                    } catch (Throwable th2) {
                        th = th2;
                        bufferedInputStream = null;
                        fileOutputStream = null;
                        closeQuietly(fileOutputStream);
                        closeQuietly(bufferedInputStream);
                        try {
                            httpURLConnection.disconnect();
                            throw th;
                        } catch (Throwable th3) {
                            throw th;
                        }
                    }
                } else {
                    if (responseCode != 200) {
                        throw new IOException("HTTP " + responseCode + " offset=" + length + " url=" + str);
                    }
                    parseLong = parseLong(httpURLConnection.getHeaderField("Content-Length"), -1L);
                    length = 0;
                    z2 = false;
                }
                if (parseLong > 0) {
                    CNCNDownloadUI.setFileSize(r2, (float) (parseLong / 1000000.0d));
                }
                bufferedInputStream2 = new BufferedInputStream(httpURLConnection.getInputStream(), 65536);
                try {
                    fileOutputStream2 = new FileOutputStream(file2, z2);
                } catch (Throwable th4) {
                    th = th4;
                    fileOutputStream = null;
                    bufferedInputStream = bufferedInputStream2;
                }
            } catch (Throwable th5) {
                th = th5;
                th = th;
                fileOutputStream = r2;
                bufferedInputStream = r2;
                closeQuietly(fileOutputStream);
                closeQuietly(bufferedInputStream);
                httpURLConnection.disconnect();
                throw th;
            }
        } catch (Throwable th6) {
            th = th6;
            r2 = 0;
        }
        try {
            byte[] bArr = new byte[65536];
            long nanoTime = System.nanoTime();
            long j = 0;
            long j2 = 0;
            while (true) {
                int read = bufferedInputStream2.read(bArr);
                if (read == -1) {
                    break;
                }
                if (read != 0) {
                    fileOutputStream2.write(bArr, 0, read);
                    j += read;
                    long j3 = length + j;
                    CNCNDownloadUI.setFileDownloaded(r2, (float) (j3 / 1000000.0d));
                    if (parseLong > 0) {
                        CNCNDownloadUI.updateFileProgress(r2, (int) Math.min(100L, Math.max(0L, (j3 * 100) / parseLong)));
                    }
                    long nanoTime2 = System.nanoTime();
                    long j4 = nanoTime2 - nanoTime;
                    long j5 = length;
                    byte[] bArr2 = bArr;
                    if (j4 >= TimeUnit.MILLISECONDS.toNanos(500L)) {
                        CNCNDownloadUI.setDownloadSpeed(r2, (float) ((((j - j2) * 1.0E9d) / j4) / 1000000.0d));
                        j2 = j;
                        nanoTime = nanoTime2;
                    }
                    bArr = bArr2;
                    length = j5;
                }
            }
            fileOutputStream2.flush();
            fileOutputStream2.getFD().sync();
            closeQuietly(fileOutputStream2);
            closeQuietly(bufferedInputStream2);
            if (parseLong > 0 && file2.length() != parseLong) {
                throw new IOException("下载不完整: " + file2.length() + " / " + parseLong);
            }
            if (file.exists() && !file.delete()) {
                throw new IOException("无法替换目标文件 " + file);
            }
            if (!file2.renameTo(file)) {
                throw new IOException("无法重命名 " + file2 + " -> " + file);
            }
            closeQuietly(null);
            closeQuietly(null);
            try {
                httpURLConnection.disconnect();
            } catch (Throwable th7) {
            }
        } catch (Throwable th8) {
            th = th8;
            bufferedInputStream = bufferedInputStream2;
            fileOutputStream = fileOutputStream2;
            closeQuietly(fileOutputStream);
            closeQuietly(bufferedInputStream);
            httpURLConnection.disconnect();
            throw th;
        }
    }

    private static String mainLineFileName(String str) {
        if (!str.startsWith(CNMirrors.DEFAULT_BASE)) {
            return null;
        }
        String substring = str.substring(CNMirrors.DEFAULT_BASE.length());
        if (substring.length() != 0 && substring.indexOf(47) < 0 && substring.indexOf(63) < 0 && substring.indexOf(35) < 0) {
            return substring;
        }
        return null;
    }

    private static void markDone(int i) {
        CNCNDownloadUI.setDownloadSpeed(i, 0.0f);
        CNCNDownloadUI.markFileDone(i);
    }

    private static void markFailed(int i) {
        CNCNDownloadUI.setDownloadSpeed(i, 0.0f);
        if (CNCNDownloadUI.fileStatus != null && i >= 0 && i < CNCNDownloadUI.fileStatus.length) {
            CNCNDownloadUI.fileStatus[i] = 3;
        }
        CNCNDownloadUI.throttledUpdate();
    }

    private static long parseLong(String str, long j) {
        if (str == null) {
            return j;
        }
        try {
            long parseLong = Long.parseLong(str.trim());
            return parseLong >= 0 ? parseLong : j;
        } catch (NumberFormatException e) {
            return j;
        }
    }

    private static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Throwable th) {
            }
        }
    }
}
