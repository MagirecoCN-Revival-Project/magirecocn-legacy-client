package io.kamihama.magianative;

import android.app.Activity;
import androidx.core.app.NotificationCompat;
import com.loopj.android.http.RequestParams;
import cz.msebera.android.httpclient.HttpHeaders;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.cookie.ClientCookie;
import cz.msebera.android.httpclient.message.TokenParser;
import cz.msebera.android.httpclient.protocol.HTTP;
import io.kamihama.magianative.CNChunkedDownload;
import io.kamihama.magianative.CNMirrors;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicLongArray;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

/* loaded from: classes3.dex */
public final class CNDownloaderFix {
    private static final int ARCHIVE_COUNT = 15;
    private static final String BOOTSTRAP_URL = "https://totentanz-9b.magi-reco.com/magica/api/snaa";
    private static final int CONNECT_TIMEOUT_MS = 15000;
    private static final String FILE_ROOT = "/data/data/io.kamihama.totentanz/files";
    private static final String FINAL_FLAG = "/data/data/io.kamihama.totentanz/files/madomagi/magica/cn_base_done.flag";
    private static final String INSTALL_ROOT = "/data/data/io.kamihama.totentanz/files/";
    private static final int MAX_ATTEMPTS = 4;
    private static final int MAX_DOWNLOADS = 4;
    private static final int MIN_SNAA_VERSION = 128;
    private static final String NO_RESTART_FLAG = "/data/data/io.kamihama.totentanz/files/madomagi/magica/.cn_installer/r128-downloader-v1/no_restart";
    private static final int READ_TIMEOUT_MS = 30000;
    private static final String RESOURCE_BASE_URL = "https://assets.magireco.top/";
    private static final String STATE_ROOT = "/data/data/io.kamihama.totentanz/files/madomagi/magica/.cn_installer/r128-downloader-v1";
    private static final String TAG = "MagiaCNDownloader";
    private static final long STALE_SPEED_NS = TimeUnit.SECONDS.toNanos(2);
    private static final Object EXTRACT_LOCK = new Object();
    private static final String[] FILE_NAMES = {"cn_base_00_db.zip", "cn_base_01_json.zip", "cn_base_02.zip", "cn_base_03.zip", "cn_base_04.zip", "cn_base_05.zip", "cn_base_06.zip", "cn_magica_resource.zip", "cn_scenario_img.zip", "cn_voice_01.zip", "cn_voice_02_done.zip", "cn_js_update.zip", "movie.zip", "movie2.zip", "cn_scenario_update.zip"};
    private static final AtomicLongArray LAST_PROGRESS_NS = new AtomicLongArray(15);
    private static final AtomicIntegerArray ACTIVE = new AtomicIntegerArray(15);

    private CNDownloaderFix() {
    }

    public static String getEndpoint(int i) {
        int max = Math.max(i, 128);
        String str = "{\"version\":" + max + "}";
        CNLog.i(TAG, "snaa-request native_version=" + i + " sent_version=" + max);
        String str2 = null;
        try {
            str2 = postJson(BOOTSTRAP_URL, str, false);
            CNLog.i(TAG, "snaa-response direct=false body=" + str2);
            if (isSnaaResponseCurrent(str2, max)) {
                return str2;
            }
            CNLog.w(TAG, "SNAA response is stale/incompatible; retrying direct");
            String postJson = postJson(BOOTSTRAP_URL, str, true);
            CNLog.i(TAG, "snaa-response direct=true body=" + postJson);
            return postJson;
        } catch (IOException e) {
            CNLog.w(TAG, "SNAA via configured network failed; retrying direct", e);
            try {
                String postJson2 = postJson(BOOTSTRAP_URL, str, true);
                CNLog.i(TAG, "snaa-response direct=true body=" + postJson2);
                return postJson2;
            } catch (IOException e2) {
                e2.addSuppressed(e);
                CNLog.e(TAG, "SNAA discovery failed", e2);
                return str2 == null ? "" : str2;
            }
        }
    }

    private static boolean isSnaaResponseCurrent(String str, int i) {
        return str != null && str.matches("(?s).*\"endpoint\"\\s*:\\s*\"https://[^\"]+\".*") && extractJsonInt(str, NotificationCompat.CATEGORY_STATUS) == 200 && extractJsonInt(str, ClientCookie.VERSION_ATTR) >= i && extractJsonInt(str, "max_threads") > 0;
    }

    private static int extractJsonInt(String str, String str2) {
        Matcher matcher = Pattern.compile("\"" + Pattern.quote(str2) + "\"\\s*:\\s*(\\d+)").matcher(str);
        if (!matcher.find()) {
            return -1;
        }
        try {
            return Integer.parseInt(matcher.group(1));
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static void runInstaller() {
        CNLog.i(TAG, "installer=v2 max_downloads=4");
        try {
            Activity currentActivity = RestClient.getCurrentActivity();
            if (currentActivity != null) {
                CNCNDownloadUI.show(currentActivity);
            }
        } catch (Throwable th) {
            CNLog.e(TAG, "Unable to show installer UI", th);
        }
        File file = new File(FINAL_FLAG);
        if (file.isFile()) {
            CNLog.i(TAG, "Final flag already exists; installer skipped");
            CNCNDownloadUI.hide();
            return;
        }
        File file2 = new File(STATE_ROOT);
        if (!file2.isDirectory() && !file2.mkdirs() && !file2.isDirectory()) {
            failInstaller("Cannot create installer state directory", null);
            return;
        }
        CNCNDownloadUI.updateSimple("准备中", "正在获取下载线路…", 0);
        CNMirrors.refresh(false);
        boolean z = true;
        if (!CNMirrors.isLoaded()) {
            CNMirrors.refresh(true);
        }
        int size = CNMirrors.healthy().size();
        CNLog.i(TAG, "mirrors ready count=" + size + " loaded=" + CNMirrors.isLoaded());
        CNCNDownloadUI.updateSimple("开始下载", "可用线路 " + size + " 条，单文件分片 " + CNMirrors.chunks() + " 线程", 0);
        resetUiForRun();
        ScheduledExecutorService startSpeedWatchdog = startSpeedWatchdog();
        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(4);
        ArrayList arrayList = new ArrayList(15);
        for (int i = 0; i < 15; i++) {
            arrayList.add(newFixedThreadPool.submit(new ArchiveTask(i)));
        }
        newFixedThreadPool.shutdown();
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            try {
                if (!((Boolean) ((Future) arrayList.get(i2)).get()).booleanValue()) {
                    z = false;
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                CNLog.e(TAG, "Installer interrupted while waiting for " + FILE_NAMES[i2], e);
            } catch (ExecutionException e2) {
                CNLog.e(TAG, "Installer worker crashed for " + FILE_NAMES[i2], e2);
                z = false;
            }
        }
        newFixedThreadPool.shutdownNow();
        startSpeedWatchdog.shutdownNow();
        zeroAllSpeeds();
        if (!z || !allMarkersValid()) {
            failInstaller("One or more archives failed; restart to resume", null);
            return;
        }
        try {
            writeAtomic(file, "schema=2\narchives=15\n");
            CNCNDownloadUI.updateSimple("安装完成", "所有资源已验证并提交完成标记", 100);
            CNCNDownloadUI.hide();
            CNLog.i(TAG, "All archives installed; final flag committed atomically");
            if (new File(NO_RESTART_FLAG).isFile()) {
                CNLog.i(TAG, "Test no-restart marker present; restart suppressed");
                return;
            }
            try {
                Thread.sleep(2000L);
                RestClient.restartApp();
            } catch (InterruptedException e3) {
                Thread.currentThread().interrupt();
            }
        } catch (IOException e4) {
            failInstaller("Final flag commit failed", e4);
        }
    }

    /* loaded from: classes3.dex */
    private static final class ArchiveTask implements Callable<Boolean> {
        private final int index;

        ArchiveTask(int i) {
            this.index = i;
        }

        /* JADX DEBUG: Method merged with bridge method: call()Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.concurrent.Callable
        public Boolean call() {
            return Boolean.valueOf(CNDownloaderFix.installArchive(this.index));
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Repeating lines: [313=4] */
    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0269  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x02a0 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean installArchive(int i) {
        String str = FILE_NAMES[i];
        String str2 = "https://assets.magireco.top/" + str;
        File file = new File(FILE_ROOT, str);
        File markerFor = markerFor(str);
        if (isMarkerValid(markerFor, str, str2)) {
            markDone(i);
            CNLog.i(TAG, "marker-hit file=" + str);
            return true;
        }
        int i2 = 1;
        while (true) {
            if (i2 > 4) {
                markFailed(i);
                CNLog.e(TAG, "retry-exhausted file=" + str);
                return false;
            }
            if (Thread.currentThread().isInterrupted()) {
                markFailed(i);
                return false;
            }
            CNMirrors.Mirror pick = CNMirrors.pick(i2);
            boolean z = i2 % 2 == 0;
            setActive(i, true);
            CNCNDownloadUI.setDownloadSpeed(i, 0.0f);
            try {
                try {
                    try {
                        DownloadMetadata fetchArchive = fetchArchive(pick, str, file, i, z);
                        synchronized (EXTRACT_LOCK) {
                            extractChecked(file, new File(INSTALL_ROOT));
                        }
                        writeMarker(markerFor, str, str2, fetchArchive);
                        if (!file.delete() && file.exists()) {
                            CNLog.w(TAG, "Installed archive retained because delete failed: " + file);
                        }
                        deleteQuietly(new File(file.getPath() + ".part"));
                        deleteQuietly(new File(file.getPath() + ".part.meta"));
                        deleteQuietly(CNChunkedDownload.partFileFor(file));
                        deleteQuietly(CNChunkedDownload.metaFileFor(file));
                        CNMirrors.reportSuccess(pick);
                        markDone(i);
                        CNLog.i(TAG, "installed file=" + str + " attempt=" + i2 + " mirror=" + pick.name);
                        return true;
                    } catch (ResetRequired e) {
                        CNLog.w(TAG, "resume-reset file=" + str + " attempt=" + i2 + " reason=" + e.getMessage());
                        if (i2 >= 4) {
                            long j = 2000 << (i2 - 1);
                            CNLog.i(TAG, "retry-wait file=" + str + " delay_ms=" + j);
                            try {
                                Thread.sleep(j);
                            } catch (InterruptedException e2) {
                                Thread.currentThread().interrupt();
                                markFailed(i);
                                return false;
                            }
                        }
                        i2++;
                    }
                } catch (IOException e3) {
                    CNLog.e(TAG, "archive-failed file=" + str + " attempt=" + i2 + " mirror=" + pick.name, e3);
                    CNMirrors.reportFailure(pick, String.valueOf(e3.getMessage()));
                    if (file.isFile()) {
                        deleteQuietly(file);
                    }
                    if (i2 >= 4) {
                    }
                    i2++;
                }
            } catch (RuntimeException e4) {
                try {
                    CNLog.e(TAG, "archive-runtime-failure file=" + str + " attempt=" + i2, e4);
                    CNMirrors.reportFailure(pick, "runtime:" + e4);
                    if (i2 >= 4) {
                    }
                    i2++;
                } finally {
                    setActive(i, false);
                    CNCNDownloadUI.setDownloadSpeed(i, 0.0f);
                    CNCNDownloadUI.throttledUpdate();
                }
            } catch (ZipException e5) {
                CNLog.e(TAG, "corrupt-zip file=" + str + " attempt=" + i2, e5);
                CNMirrors.reportFailure(pick, "corrupt-zip");
                deleteQuietly(file);
                deleteQuietly(new File(file.getPath() + ".part"));
                deleteQuietly(new File(file.getPath() + ".part.meta"));
                deleteQuietly(CNChunkedDownload.partFileFor(file));
                deleteQuietly(CNChunkedDownload.metaFileFor(file));
                if (i2 >= 4) {
                }
                i2++;
            }
            i2++;
        }
    }

    private static DownloadMetadata fetchArchive(CNMirrors.Mirror mirror, String str, File file, int i, boolean z) throws IOException {
        if (file.isFile()) {
            return new DownloadMetadata(file.length(), readSidecarEtag(file));
        }
        String urlFor = mirror.urlFor(str);
        int effectiveChunks = mirror.effectiveChunks();
        if (effectiveChunks > 1) {
            CNChunkedDownload.Probe probe = CNChunkedDownload.probe(urlFor, z);
            if (probe.rangeSupported && probe.total > 0) {
                long minChunkBytes = CNMirrors.minChunkBytes();
                if (minChunkBytes > 0) {
                    long j = probe.total / minChunkBytes;
                    if (j < effectiveChunks) {
                        effectiveChunks = (int) Math.max(1L, j);
                    }
                }
                if (effectiveChunks > 1) {
                    CNLog.i(TAG, "chunked-download file=" + str + " mirror=" + mirror.name + " chunks=" + effectiveChunks + " bytes=" + probe.total + " direct=" + z);
                    updateSize(i, probe.total);
                    updateProgress(i, 0L, probe.total);
                    CNChunkedDownload.Result download = CNChunkedDownload.download(urlFor, file, effectiveChunks, z, probe, new ArchiveSink(i));
                    return new DownloadMetadata(download.totalBytes, download.etag);
                }
            }
            CNLog.i(TAG, "range-unsupported-or-small file=" + str + " mirror=" + mirror.name + " → 单线程续传");
        }
        return downloadOnce(urlFor, file, i, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static final class ArchiveSink implements CNChunkedDownload.Sink {
        private final int index;

        ArchiveSink(int i) {
            this.index = i;
        }

        @Override // io.kamihama.magianative.CNChunkedDownload.Sink
        public void onTotal(long j) {
            CNDownloaderFix.updateSize(this.index, j);
        }

        @Override // io.kamihama.magianative.CNChunkedDownload.Sink
        public void onProgress(long j, long j2) {
            CNDownloaderFix.LAST_PROGRESS_NS.set(this.index, System.nanoTime());
            CNDownloaderFix.updateProgress(this.index, j, j2);
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

    /* JADX DEBUG: Don't trust debug lines info. Repeating lines: [563=7] */
    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:163:0x0439 */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r14v0 */
    /* JADX WARN: Type inference failed for: r14v1, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r14v5 */
    /* JADX WARN: Type inference failed for: r14v6 */
    /* JADX WARN: Type inference failed for: r14v9 */
    /* JADX WARN: Type inference failed for: r1v28, types: [long] */
    /* JADX WARN: Type inference failed for: r2v3, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r2v32 */
    /* JADX WARN: Type inference failed for: r2v33 */
    /* JADX WARN: Type inference failed for: r2v34 */
    /* JADX WARN: Type inference failed for: r2v35 */
    private static DownloadMetadata downloadOnce(String str, File file, int i, boolean z) throws IOException {
        File file2;
        ?? r14;
        ?? r2;
        BufferedInputStream bufferedInputStream;
        long j;
        long parsePositiveLong;
        long j2;
        boolean z2;
        HttpURLConnection httpURLConnection;
        BufferedInputStream bufferedInputStream2;
        ?? r1;
        if (file.isFile()) {
            return new DownloadMetadata(file.length(), readSidecarEtag(file));
        }
        File file3 = new File(file.getPath() + ".part");
        File file4 = new File(file.getPath() + ".part.meta");
        File parentFile = file3.getParentFile();
        if (parentFile != null && !parentFile.isDirectory() && !parentFile.mkdirs() && !parentFile.isDirectory()) {
            throw new IOException("Cannot create download directory: " + parentFile);
        }
        long length = file3.isFile() ? file3.length() : 0L;
        if (length > 0) {
            long readSidecarBytes = readSidecarBytes(file);
            if (readSidecarBytes > 0 && length > readSidecarBytes) {
                CNLog.w(TAG, "resume-reset file=" + file.getName() + " 残片超长 " + length + " > " + readSidecarBytes + "，丢弃重下");
                truncate(file3);
                deleteQuietly(file4);
                resetProgress(i);
                length = 0;
            }
        }
        CNLog.i(TAG, "download-open file=" + file.getName() + " offset=" + length + " direct=" + z);
        URL url = new URL(str);
        HttpURLConnection httpURLConnection2 = (HttpURLConnection) (z ? url.openConnection(Proxy.NO_PROXY) : url.openConnection());
        httpURLConnection2.setConnectTimeout(CONNECT_TIMEOUT_MS);
        httpURLConnection2.setReadTimeout(READ_TIMEOUT_MS);
        httpURLConnection2.setUseCaches(false);
        httpURLConnection2.setRequestProperty("Accept-Encoding", HTTP.IDENTITY_CODING);
        httpURLConnection2.setRequestProperty("Connection", "close");
        String readSidecarEtag = readSidecarEtag(file);
        if (length > 0) {
            httpURLConnection2.setRequestProperty("Range", "bytes=" + length + "-");
            if (readSidecarEtag.length() > 0) {
                httpURLConnection2.setRequestProperty("If-Range", readSidecarEtag);
            }
        }
        try {
            int responseCode = httpURLConnection2.getResponseCode();
            String cleanHeader = cleanHeader(httpURLConnection2.getHeaderField("ETag"));
            try {
                if (length > 0 && responseCode == 200) {
                    truncate(file3);
                    deleteQuietly(file4);
                    resetProgress(i);
                    throw new ResetRequired("server returned 200 for Range offset " + length);
                }
                if (length > 0 && responseCode == 206) {
                    ContentRange parseContentRange = parseContentRange(httpURLConnection2.getHeaderField("Content-Range"));
                    if (parseContentRange == null || parseContentRange.start != length || parseContentRange.end < parseContentRange.start || parseContentRange.total <= parseContentRange.end) {
                        truncate(file3);
                        deleteQuietly(file4);
                        resetProgress(i);
                        throw new ResetRequired("invalid Content-Range for offset " + length);
                    }
                    if (readSidecarEtag.length() > 0 && cleanHeader.length() > 0 && !readSidecarEtag.equals(cleanHeader)) {
                        truncate(file3);
                        deleteQuietly(file4);
                        resetProgress(i);
                        throw new ResetRequired("ETag changed while resuming");
                    }
                    r1 = parseContentRange.total;
                    long j3 = (parseContentRange.end - parseContentRange.start) + 1;
                    z2 = true;
                    j2 = j3;
                    parsePositiveLong = r1;
                    j = -1;
                } else {
                    if (length != 0 || responseCode != 200) {
                        file2 = file;
                        try {
                            if (length <= 0 || responseCode != 416) {
                                throw new IOException("Unexpected HTTP status " + responseCode + " offset=" + length + " url=" + str);
                            }
                            httpURLConnection2 = httpURLConnection2;
                            long parseUnsatisfiedTotal = parseUnsatisfiedTotal(httpURLConnection2.getHeaderField("Content-Range"));
                            if (parseUnsatisfiedTotal <= 0 || parseUnsatisfiedTotal != length) {
                                truncate(file3);
                                deleteQuietly(file4);
                                resetProgress(i);
                                throw new ResetRequired("HTTP 416 did not match local length");
                            }
                            promotePart(file3, file2);
                            deleteQuietly(file4);
                            DownloadMetadata downloadMetadata = new DownloadMetadata(parseUnsatisfiedTotal, readSidecarEtag);
                            closeQuietly((OutputStream) null);
                            closeQuietly((InputStream) null);
                            httpURLConnection2.disconnect();
                            return downloadMetadata;
                        } catch (Throwable th) {
                            th = th;
                            r14 = file2;
                            r2 = file2;
                            closeQuietly((OutputStream) r14);
                            closeQuietly((InputStream) r2);
                            httpURLConnection2.disconnect();
                            throw th;
                        }
                    }
                    j = -1;
                    parsePositiveLong = parsePositiveLong(httpURLConnection2.getHeaderField("Content-Length"), -1L);
                    j2 = parsePositiveLong;
                    z2 = false;
                }
                long parsePositiveLong2 = parsePositiveLong(httpURLConnection2.getHeaderField("Content-Length"), j);
                long j4 = j2;
                if (j4 >= 0 && parsePositiveLong2 >= 0 && j4 != parsePositiveLong2) {
                    throw new IOException("Content-Length mismatch expected=" + j4 + " header=" + parsePositiveLong2);
                }
                try {
                    if (parsePositiveLong <= 0) {
                        throw new IOException("Response does not declare a positive total length");
                    }
                    writeSidecar(file4, cleanHeader, parsePositiveLong);
                    updateSize(i, parsePositiveLong);
                    updateProgress(i, length, parsePositiveLong);
                    BufferedInputStream bufferedInputStream3 = new BufferedInputStream(httpURLConnection2.getInputStream(), 65536);
                    try {
                        FileOutputStream fileOutputStream = new FileOutputStream(file3, z2);
                        try {
                            FileOutputStream fileOutputStream2 = fileOutputStream;
                            byte[] bArr = new byte[65536];
                            long nanoTime = System.nanoTime();
                            long j5 = 0;
                            long j6 = 0;
                            BufferedInputStream bufferedInputStream4 = bufferedInputStream3;
                            while (true) {
                                httpURLConnection = httpURLConnection2;
                                try {
                                    int read = bufferedInputStream4.read(bArr);
                                    if (read < 0) {
                                        break;
                                    }
                                    File file5 = file4;
                                    fileOutputStream.write(bArr, 0, read);
                                    long j7 = j4;
                                    j5 += read;
                                    long nanoTime2 = System.nanoTime();
                                    LAST_PROGRESS_NS.set(i, nanoTime2);
                                    BufferedInputStream bufferedInputStream5 = bufferedInputStream4;
                                    try {
                                        updateProgress(i, length + j5, parsePositiveLong);
                                        long j8 = nanoTime2 - nanoTime;
                                        if (j8 >= TimeUnit.MILLISECONDS.toNanos(500L)) {
                                            CNCNDownloadUI.setDownloadSpeed(i, (float) ((((j5 - j6) * 1.0E9d) / j8) / 1000000.0d));
                                            j6 = j5;
                                            nanoTime = nanoTime2;
                                        }
                                        bufferedInputStream4 = bufferedInputStream5;
                                        httpURLConnection2 = httpURLConnection;
                                        file4 = file5;
                                        j4 = j7;
                                    } catch (Throwable th2) {
                                        th = th2;
                                        bufferedInputStream2 = bufferedInputStream5;
                                        r14 = fileOutputStream;
                                        httpURLConnection2 = httpURLConnection;
                                        r2 = bufferedInputStream2;
                                        closeQuietly((OutputStream) r14);
                                        closeQuietly((InputStream) r2);
                                        httpURLConnection2.disconnect();
                                        throw th;
                                    }
                                } catch (Throwable th3) {
                                    th = th3;
                                    bufferedInputStream2 = bufferedInputStream4;
                                }
                            }
                            BufferedInputStream bufferedInputStream6 = bufferedInputStream4;
                            File file6 = file4;
                            long j9 = j4;
                            fileOutputStream.flush();
                            fileOutputStream.getFD().sync();
                            if (j4 >= 0 && j5 != j9) {
                                throw new IOException("Short response expected=" + j9 + " received=" + j5);
                            }
                            long length2 = file3.length();
                            if (length2 != parsePositiveLong) {
                                throw new IOException("Partial file length mismatch expected=" + parsePositiveLong + " actual=" + length2);
                            }
                            closeQuietly(fileOutputStream);
                            try {
                                closeQuietly(bufferedInputStream6);
                                promotePart(file3, file);
                                deleteQuietly(file6);
                                DownloadMetadata downloadMetadata2 = new DownloadMetadata(parsePositiveLong, cleanHeader);
                                closeQuietly((OutputStream) null);
                                closeQuietly((InputStream) null);
                                httpURLConnection.disconnect();
                                return downloadMetadata2;
                            } catch (Throwable th4) {
                                th = th4;
                                bufferedInputStream = bufferedInputStream6;
                                httpURLConnection2 = httpURLConnection;
                                r14 = null;
                                r2 = bufferedInputStream;
                                closeQuietly((OutputStream) r14);
                                closeQuietly((InputStream) r2);
                                httpURLConnection2.disconnect();
                                throw th;
                            }
                        } catch (Throwable th5) {
                            th = th5;
                            r14 = fileOutputStream;
                            r2 = bufferedInputStream3;
                        }
                    } catch (Throwable th6) {
                        th = th6;
                        bufferedInputStream = bufferedInputStream3;
                    }
                } catch (Throwable th7) {
                    th = th7;
                    httpURLConnection2 = r1;
                    bufferedInputStream = null;
                    r14 = null;
                    r2 = bufferedInputStream;
                    closeQuietly((OutputStream) r14);
                    closeQuietly((InputStream) r2);
                    httpURLConnection2.disconnect();
                    throw th;
                }
            } catch (Throwable th8) {
                th = th8;
            }
        } catch (Throwable th9) {
            th = th9;
            file2 = null;
        }
    }

    private static void extractChecked(File file, File file2) throws IOException {
        BufferedInputStream bufferedInputStream;
        if (!file.isFile()) {
            throw new IOException("Archive is missing: " + file);
        }
        if (!file2.isDirectory() && !file2.mkdirs() && !file2.isDirectory()) {
            throw new IOException("Cannot create extraction root: " + file2);
        }
        String canonicalPath = file2.getCanonicalPath();
        String str = canonicalPath + File.separator;
        ZipFile zipFile = new ZipFile(file);
        try {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            int i = 0;
            boolean z = false;
            while (entries.hasMoreElements()) {
                ZipEntry nextElement = entries.nextElement();
                File file3 = new File(file2, nextElement.getName());
                String canonicalPath2 = file3.getCanonicalPath();
                if (!canonicalPath2.equals(canonicalPath) && !canonicalPath2.startsWith(str)) {
                    throw new ZipException("ZIP entry escapes extraction root: " + nextElement.getName());
                }
                if (nextElement.isDirectory()) {
                    if (!file3.isDirectory() && !file3.mkdirs() && !file3.isDirectory()) {
                        throw new IOException("Cannot create directory " + file3);
                    }
                } else {
                    File parentFile = file3.getParentFile();
                    if (parentFile != null && !parentFile.isDirectory() && !parentFile.mkdirs() && !parentFile.isDirectory()) {
                        throw new IOException("Cannot create directory " + parentFile);
                    }
                    BufferedOutputStream bufferedOutputStream = null;
                    try {
                        bufferedInputStream = new BufferedInputStream(zipFile.getInputStream(nextElement), 65536);
                        try {
                            BufferedOutputStream bufferedOutputStream2 = new BufferedOutputStream(new FileOutputStream(file3), 65536);
                            try {
                                byte[] bArr = new byte[65536];
                                long j = 0;
                                while (true) {
                                    int read = bufferedInputStream.read(bArr);
                                    if (read < 0) {
                                        break;
                                    }
                                    bufferedOutputStream2.write(bArr, i, read);
                                    j += read;
                                    bArr = bArr;
                                    i = 0;
                                }
                                bufferedOutputStream2.flush();
                                if (nextElement.getSize() >= 0 && j != nextElement.getSize()) {
                                    throw new ZipException("Entry size mismatch: " + nextElement.getName());
                                }
                                closeQuietly(bufferedOutputStream2);
                                closeQuietly(bufferedInputStream);
                                z = true;
                                i = 0;
                            } catch (Throwable th) {
                                th = th;
                                bufferedOutputStream = bufferedOutputStream2;
                                closeQuietly(bufferedOutputStream);
                                closeQuietly(bufferedInputStream);
                                throw th;
                            }
                        } catch (Throwable th2) {
                            th = th2;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        bufferedInputStream = null;
                    }
                }
            }
            if (!z) {
                throw new ZipException("Archive contains no file entries: " + file);
            }
        } finally {
            zipFile.close();
        }
    }

    private static ScheduledExecutorService startSpeedWatchdog() {
        ScheduledExecutorService newSingleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        newSingleThreadScheduledExecutor.scheduleAtFixedRate(new SpeedWatchdog(), 1L, 1L, TimeUnit.SECONDS);
        return newSingleThreadScheduledExecutor;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static final class SpeedWatchdog implements Runnable {
        private SpeedWatchdog() {
        }

        @Override // java.lang.Runnable
        public void run() {
            long nanoTime = System.nanoTime();
            boolean z = false;
            for (int i = 0; i < 15; i++) {
                if (CNDownloaderFix.ACTIVE.get(i) != 0) {
                    long j = CNDownloaderFix.LAST_PROGRESS_NS.get(i);
                    if (j != 0 && nanoTime - j >= CNDownloaderFix.STALE_SPEED_NS && CNDownloaderFix.LAST_PROGRESS_NS.compareAndSet(i, j, 0L)) {
                        CNCNDownloadUI.setDownloadSpeed(i, 0.0f);
                        CNLog.i(CNDownloaderFix.TAG, "stale-speed-zero file=" + CNDownloaderFix.FILE_NAMES[i]);
                        z = true;
                    }
                }
            }
            if (z) {
                CNCNDownloadUI.throttledUpdate();
            }
        }
    }

    private static String postJson(String str, String str2, boolean z) throws IOException {
        InputStream inputStream;
        URL url = new URL(str);
        HttpURLConnection httpURLConnection = (HttpURLConnection) (z ? url.openConnection(Proxy.NO_PROXY) : url.openConnection());
        httpURLConnection.setConnectTimeout(CONNECT_TIMEOUT_MS);
        httpURLConnection.setReadTimeout(READ_TIMEOUT_MS);
        httpURLConnection.setRequestMethod(HttpPost.METHOD_NAME);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        httpURLConnection.setRequestProperty(HttpHeaders.ACCEPT, RequestParams.APPLICATION_JSON);
        httpURLConnection.setRequestProperty("Connection", "close");
        OutputStream outputStream = null;
        try {
            byte[] bytes = str2.getBytes(StandardCharsets.UTF_8);
            httpURLConnection.setFixedLengthStreamingMode(bytes.length);
            OutputStream outputStream2 = httpURLConnection.getOutputStream();
            try {
                outputStream2.write(bytes);
                outputStream2.flush();
                int responseCode = httpURLConnection.getResponseCode();
                if (responseCode < 200 || responseCode >= 300) {
                    throw new IOException("SNAA returned HTTP " + responseCode);
                }
                InputStream inputStream2 = httpURLConnection.getInputStream();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] bArr = new byte[8192];
                while (true) {
                    int read = inputStream2.read(bArr);
                    if (read >= 0) {
                        byteArrayOutputStream.write(bArr, 0, read);
                    } else {
                        String str3 = new String(byteArrayOutputStream.toByteArray(), StandardCharsets.UTF_8);
                        closeQuietly(outputStream2);
                        closeQuietly(inputStream2);
                        httpURLConnection.disconnect();
                        return str3;
                    }
                }
            } catch (Throwable th) {
                th = th;
                inputStream = null;
                outputStream = outputStream2;
                closeQuietly(outputStream);
                closeQuietly(inputStream);
                httpURLConnection.disconnect();
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            inputStream = null;
        }
    }

    private static void writeMarker(File file, String str, String str2, DownloadMetadata downloadMetadata) throws IOException {
        writeAtomic(file, "schema=1\nfile=" + str + "\nurl=" + str2 + "\nbytes=" + downloadMetadata.totalBytes + "\netag=" + sanitizeLine(downloadMetadata.etag) + "\n");
    }

    private static boolean isMarkerValid(File file, String str, String str2) {
        if (!file.isFile() || file.length() <= 0 || file.length() > 16384) {
            return false;
        }
        try {
            String readSmallUtf8 = readSmallUtf8(file);
            if (readSmallUtf8.contains("schema=1\n") && readSmallUtf8.contains("file=" + str + "\n") && readSmallUtf8.contains("url=" + str2 + "\n")) {
                return readSmallUtf8.matches("(?s).*\\nbytes=[1-9][0-9]*\\n.*");
            }
            return false;
        } catch (IOException e) {
            CNLog.e(TAG, "Cannot read marker " + file, e);
            return false;
        }
    }

    private static boolean allMarkersValid() {
        for (String str : FILE_NAMES) {
            if (!isMarkerValid(markerFor(str), str, "https://assets.magireco.top/" + str)) {
                CNLog.e(TAG, "Marker verification failed for " + str);
                return false;
            }
        }
        return true;
    }

    private static File markerFor(String str) {
        return new File(STATE_ROOT, str + ".done");
    }

    private static void writeAtomic(File file, String str) throws IOException {
        File parentFile = file.getParentFile();
        if (parentFile != null && !parentFile.isDirectory() && !parentFile.mkdirs() && !parentFile.isDirectory()) {
            throw new IOException("Cannot create parent directory: " + parentFile);
        }
        File file2 = new File(file.getPath() + ".tmp");
        FileOutputStream fileOutputStream = null;
        try {
            FileOutputStream fileOutputStream2 = new FileOutputStream(file2, false);
            try {
                fileOutputStream2.write(str.getBytes(StandardCharsets.UTF_8));
                fileOutputStream2.flush();
                fileOutputStream2.getFD().sync();
                closeQuietly(fileOutputStream2);
                if (file.exists() && !file.delete()) {
                    throw new IOException("Cannot replace " + file);
                }
                if (!file2.renameTo(file)) {
                    throw new IOException("Atomic rename failed: " + file2 + " -> " + file);
                }
                closeQuietly((OutputStream) null);
            } catch (Throwable th) {
                th = th;
                fileOutputStream = fileOutputStream2;
                closeQuietly(fileOutputStream);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private static void writeSidecar(File file, String str, long j) throws IOException {
        writeAtomic(file, "etag=" + sanitizeLine(str) + "\nbytes=" + j + "\n");
    }

    private static long readSidecarBytes(File file) {
        File file2 = new File(file.getPath() + ".part.meta");
        if (!file2.isFile() || file2.length() > 16384) {
            return -1L;
        }
        try {
            for (String str : readSmallUtf8(file2).split("\\n")) {
                if (str.startsWith("bytes=")) {
                    return parsePositiveLong(str.substring(6).trim(), -1L);
                }
            }
        } catch (IOException e) {
            CNLog.w(TAG, "Cannot read resume metadata " + file2, e);
        }
        return -1L;
    }

    private static String readSidecarEtag(File file) {
        File file2 = new File(file.getPath() + ".part.meta");
        if (!file2.isFile() || file2.length() > 16384) {
            return "";
        }
        try {
            for (String str : readSmallUtf8(file2).split("\\n")) {
                if (str.startsWith("etag=")) {
                    return str.substring(5).trim();
                }
            }
        } catch (IOException e) {
            CNLog.w(TAG, "Cannot read resume metadata " + file2, e);
        }
        return "";
    }

    private static String readSmallUtf8(File file) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        FileInputStream fileInputStream = null;
        try {
            FileInputStream fileInputStream2 = new FileInputStream(file);
            try {
                byte[] bArr = new byte[4096];
                int i = 0;
                while (true) {
                    int read = fileInputStream2.read(bArr);
                    if (read >= 0) {
                        i += read;
                        if (i > 16384) {
                            throw new IOException("State file is too large: " + file);
                        }
                        byteArrayOutputStream.write(bArr, 0, read);
                    } else {
                        String str = new String(byteArrayOutputStream.toByteArray(), StandardCharsets.UTF_8);
                        closeQuietly(fileInputStream2);
                        return str;
                    }
                }
            } catch (Throwable th) {
                th = th;
                fileInputStream = fileInputStream2;
                closeQuietly(fileInputStream);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private static void promotePart(File file, File file2) throws IOException {
        if (file2.exists() && !file2.delete()) {
            throw new IOException("Cannot replace destination " + file2);
        }
        if (!file.renameTo(file2)) {
            throw new IOException("Cannot rename " + file + " to " + file2);
        }
    }

    private static void truncate(File file) throws IOException {
        FileOutputStream fileOutputStream = null;
        try {
            FileOutputStream fileOutputStream2 = new FileOutputStream(file, false);
            try {
                fileOutputStream2.flush();
                fileOutputStream2.getFD().sync();
                closeQuietly(fileOutputStream2);
            } catch (Throwable th) {
                th = th;
                fileOutputStream = fileOutputStream2;
                closeQuietly(fileOutputStream);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private static ContentRange parseContentRange(String str) {
        if (str == null) {
            return null;
        }
        String lowerCase = str.trim().toLowerCase(Locale.US);
        if (!lowerCase.startsWith("bytes ")) {
            return null;
        }
        int indexOf = lowerCase.indexOf(45, 6);
        int i = indexOf + 1;
        int indexOf2 = lowerCase.indexOf(47, i);
        if (indexOf < 0 || indexOf2 < 0) {
            return null;
        }
        try {
            return new ContentRange(Long.parseLong(lowerCase.substring(6, indexOf).trim()), Long.parseLong(lowerCase.substring(i, indexOf2).trim()), Long.parseLong(lowerCase.substring(indexOf2 + 1).trim()));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static long parseUnsatisfiedTotal(String str) {
        if (str == null) {
            return -1L;
        }
        String lowerCase = str.trim().toLowerCase(Locale.US);
        if (!lowerCase.startsWith("bytes */")) {
            return -1L;
        }
        return parsePositiveLong(lowerCase.substring(8), -1L);
    }

    private static long parsePositiveLong(String str, long j) {
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

    private static void resetUiForRun() {
        for (int i = 0; i < 15; i++) {
            String[] strArr = FILE_NAMES;
            if (!isMarkerValid(markerFor(strArr[i]), strArr[i], "https://assets.magireco.top/" + strArr[i])) {
                if (CNCNDownloadUI.fileStatus != null) {
                    CNCNDownloadUI.fileStatus[i] = 0;
                }
                if (CNCNDownloadUI.fileProgress != null) {
                    CNCNDownloadUI.fileProgress[i] = 0;
                }
                CNCNDownloadUI.setDownloadSpeed(i, 0.0f);
                CNCNDownloadUI.setFileDownloaded(i, 0.0f);
            } else {
                markDone(i);
            }
        }
        CNCNDownloadUI.throttledUpdate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void updateSize(int i, long j) {
        CNCNDownloadUI.setFileSize(i, (float) (j / 1000000.0d));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void updateProgress(int i, long j, long j2) {
        int i2;
        if (j2 > 0) {
            i2 = (int) Math.min(100L, Math.max(0L, (j * 100) / j2));
        } else {
            i2 = 0;
        }
        CNCNDownloadUI.setFileDownloaded(i, (float) (j / 1000000.0d));
        CNCNDownloadUI.updateFileProgress(i, i2);
    }

    private static void resetProgress(int i) {
        CNCNDownloadUI.setDownloadSpeed(i, 0.0f);
        CNCNDownloadUI.setFileDownloaded(i, 0.0f);
        CNCNDownloadUI.updateFileProgress(i, 0);
    }

    private static void markDone(int i) {
        setActive(i, false);
        CNCNDownloadUI.setDownloadSpeed(i, 0.0f);
        CNCNDownloadUI.markFileDone(i);
    }

    private static void markFailed(int i) {
        setActive(i, false);
        CNCNDownloadUI.setDownloadSpeed(i, 0.0f);
        if (CNCNDownloadUI.fileStatus != null) {
            CNCNDownloadUI.fileStatus[i] = 3;
        }
        CNCNDownloadUI.throttledUpdate();
    }

    private static void setActive(int i, boolean z) {
        ACTIVE.set(i, z ? 1 : 0);
        LAST_PROGRESS_NS.set(i, z ? System.nanoTime() : 0L);
    }

    private static void zeroAllSpeeds() {
        for (int i = 0; i < 15; i++) {
            ACTIVE.set(i, 0);
            LAST_PROGRESS_NS.set(i, 0L);
            CNCNDownloadUI.setDownloadSpeed(i, 0.0f);
        }
        CNCNDownloadUI.throttledUpdate();
    }

    private static void failInstaller(String str, Throwable th) {
        zeroAllSpeeds();
        if (th == null) {
            CNLog.e(TAG, str);
        } else {
            CNLog.e(TAG, str, th);
        }
        CNCNDownloadUI.updateSimple("安装暂停", str, 0);
    }

    private static String cleanHeader(String str) {
        return str == null ? "" : str.trim();
    }

    private static String sanitizeLine(String str) {
        return str == null ? "" : str.replace(TokenParser.CR, TokenParser.SP).replace('\n', TokenParser.SP);
    }

    private static void deleteQuietly(File file) {
        if (file.exists() && !file.delete()) {
            CNLog.w(TAG, "Cannot delete " + file);
        }
    }

    private static void closeQuietly(InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
            }
        }
    }

    private static void closeQuietly(OutputStream outputStream) {
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public static final class DownloadMetadata {
        final String etag;
        final long totalBytes;

        DownloadMetadata(long j, String str) {
            this.totalBytes = j;
            this.etag = str == null ? "" : str;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public static final class ContentRange {
        final long end;
        final long start;
        final long total;

        ContentRange(long j, long j2, long j3) {
            this.start = j;
            this.end = j2;
            this.total = j3;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public static final class ResetRequired extends IOException {
        private static final long serialVersionUID = 1;

        ResetRequired(String str) {
            super(str);
        }
    }
}
