package io.kamihama.magianative;

import cz.msebera.android.httpclient.HttpHeaders;
import cz.msebera.android.httpclient.message.TokenParser;
import cz.msebera.android.httpclient.protocol.HTTP;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongArray;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes3.dex */
public final class CNChunkedDownload {
    private static final int CONNECT_TIMEOUT_MS = 15000;
    private static final String META_MAGIC = "CNVPROG2";
    private static final int READ_TIMEOUT_MS = 30000;
    private static final String TAG = "MagiaCNChunk";

    /* loaded from: classes3.dex */
    public interface Sink {
        boolean isCancelled();

        void onProgress(long j, long j2);

        void onSpeed(float f);

        void onTotal(long j);
    }

    private CNChunkedDownload() {
    }

    /* loaded from: classes3.dex */
    public static final class Probe {
        public final String etag;
        public final boolean rangeSupported;
        public final long total;

        Probe(long j, String str, boolean z) {
            this.total = j;
            this.etag = str == null ? "" : str.trim();
            this.rangeSupported = z;
        }
    }

    /* loaded from: classes3.dex */
    public static final class Result {
        public final String etag;
        public final long totalBytes;

        Result(long j, String str) {
            this.totalBytes = j;
            this.etag = str == null ? "" : str;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static final class Resume {
        int chunks;
        long[] done;
        String etag;
        long total;

        private Resume() {
            this.etag = "";
        }
    }

    private static HttpURLConnection open(String str, boolean z) throws IOException {
        URL url = new URL(str);
        HttpURLConnection httpURLConnection = (HttpURLConnection) (z ? url.openConnection(Proxy.NO_PROXY) : url.openConnection());
        httpURLConnection.setConnectTimeout(CONNECT_TIMEOUT_MS);
        httpURLConnection.setReadTimeout(READ_TIMEOUT_MS);
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setInstanceFollowRedirects(true);
        httpURLConnection.setRequestProperty("Accept-Encoding", HTTP.IDENTITY_CODING);
        httpURLConnection.setRequestProperty("Connection", "close");
        return httpURLConnection;
    }

    /* JADX DEBUG: Don't trust debug lines info. Repeating lines: [148=4, 167=4] */
    /* JADX WARN: Can't wrap try/catch for region: R(13:1|(3:2|3|4)|(3:6|7|(4:10|(1:34)(1:14)|15|(1:(3:27|(2:30|31)|29))(3:18|(2:22|23)|20)))|(2:36|37)|39|40|41|42|(2:44|(3:46|(2:49|50)|48))(1:(2:61|(3:63|(2:66|67)|65)))|(2:54|55)|57|58|(1:(0))) */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x00d9, code lost:
    
        if (r11 != null) goto L71;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x00db, code lost:
    
        r11.disconnect();
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x00d8, code lost:
    
        r11 = null;
     */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0094 A[Catch: all -> 0x00d5, TryCatch #3 {all -> 0x00d5, blocks: (B:42:0x007c, B:44:0x0094, B:46:0x00a2, B:61:0x00b3, B:63:0x00bf), top: B:41:0x007c }] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00cf A[Catch: all -> 0x00d3, TRY_ENTER, TryCatch #1 {all -> 0x00d3, blocks: (B:54:0x00cf, B:73:0x00db), top: B:39:0x0078 }] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00af  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Probe probe(String str, boolean z) {
        HttpURLConnection httpURLConnection;
        int responseCode;
        HttpURLConnection httpURLConnection2;
        try {
            try {
                httpURLConnection2 = open(str, z);
            } catch (Throwable th) {
                httpURLConnection2 = null;
            }
        } catch (Throwable th2) {
        }
        try {
            httpURLConnection2.setRequestMethod("HEAD");
            int responseCode2 = httpURLConnection2.getResponseCode();
            if (responseCode2 >= 200 && responseCode2 < 300) {
                long parseLong = parseLong(httpURLConnection2.getHeaderField("Content-Length"), -1L);
                String headerField = httpURLConnection2.getHeaderField(HttpHeaders.ACCEPT_RANGES);
                String headerField2 = httpURLConnection2.getHeaderField("ETag");
                boolean z2 = headerField != null && headerField.toLowerCase(Locale.US).contains("bytes");
                if (parseLong > 0 && z2) {
                    Probe probe = new Probe(parseLong, headerField2, true);
                    if (httpURLConnection2 != null) {
                        try {
                            httpURLConnection2.disconnect();
                        } catch (Throwable th3) {
                        }
                    }
                    return probe;
                }
                if (parseLong > 0) {
                    Probe probe2 = new Probe(parseLong, headerField2, false);
                    if (httpURLConnection2 != null) {
                        try {
                            httpURLConnection2.disconnect();
                        } catch (Throwable th4) {
                        }
                    }
                    return probe2;
                }
            }
        } catch (Throwable th5) {
            if (httpURLConnection2 != null) {
                httpURLConnection2.disconnect();
            }
            httpURLConnection = open(str, z);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("Range", "bytes=0-0");
            responseCode = httpURLConnection.getResponseCode();
            String headerField3 = httpURLConnection.getHeaderField("ETag");
            if (responseCode != 206) {
            }
            if (httpURLConnection != null) {
            }
            return new Probe(-1L, "", false);
        }
        if (httpURLConnection2 != null) {
            httpURLConnection2.disconnect();
        }
        httpURLConnection = open(str, z);
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setRequestProperty("Range", "bytes=0-0");
        responseCode = httpURLConnection.getResponseCode();
        String headerField32 = httpURLConnection.getHeaderField("ETag");
        if (responseCode != 206) {
            long j = totalFromContentRange(httpURLConnection.getHeaderField("Content-Range"));
            if (j > 0) {
                Probe probe3 = new Probe(j, headerField32, true);
                if (httpURLConnection != null) {
                    try {
                        httpURLConnection.disconnect();
                    } catch (Throwable th6) {
                    }
                }
                return probe3;
            }
        } else if (responseCode >= 200 && responseCode < 300) {
            long parseLong2 = parseLong(httpURLConnection.getHeaderField("Content-Length"), -1L);
            if (parseLong2 > 0) {
                Probe probe4 = new Probe(parseLong2, headerField32, false);
                if (httpURLConnection != null) {
                    try {
                        httpURLConnection.disconnect();
                    } catch (Throwable th7) {
                    }
                }
                return probe4;
            }
        }
        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
        }
        return new Probe(-1L, "", false);
    }

    public static File partFileFor(File file) {
        return new File(file.getPath() + ".cpart");
    }

    public static File metaFileFor(File file) {
        return new File(file.getPath() + ".cpart.prog");
    }

    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:107:0x034e */
    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:111:0x0294 */
    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:130:0x033c */
    /* JADX DEBUG: Finally have unexpected throw blocks count: 2, expect 1 */
    /* JADX WARN: Can't wrap try/catch for region: R(13:(2:19|(20:21|(2:24|22)|25|26|27|(3:29|(2:31|32)(2:34|35)|33)|36|37|38|39|(1:41)|43|44|45|(1:47)|48|49|(1:51)|52|(4:54|(1:56)|57|58)(11:59|(1:61)|62|63|(7:64|65|66|67|68|69|(5:71|(2:73|(1:75))|111|112|(3:133|134|135)(3:114|(4:116|117|118|(2:120|(1:126)(3:122|123|124))(1:129))(2:131|132)|125))(2:136|137))|79|80|81|82|83|(4:85|(2:86|(1:88)(1:89))|90|(2:92|(4:94|(1:96)|97|98)(2:99|100))(2:101|102))(1:103)))(1:153))|38|39|(0)|43|44|45|(0)|48|49|(0)|52|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:127:0x02fb, code lost:
    
        r1.set(true);
        io.kamihama.magianative.CNChunkedDownload$$ExternalSyntheticBackportWithForwarding0.m(r10, null, new java.io.IOException("线路过慢：" + (r5 / 1024) + " KB/s < " + io.kamihama.magianative.CNMirrors.minSpeedKbps() + " KB/s"));
        r4 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x0281, code lost:
    
        r1.set(true);
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x0287, code lost:
    
        r4 = r18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x0289, code lost:
    
        io.kamihama.magianative.CNChunkedDownload$$ExternalSyntheticBackportWithForwarding0.m(r10, null, new java.io.IOException(r4));
        r16 = 0;
        r4 = r4;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:103:0x043a  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00ec  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0132 A[Catch: all -> 0x043b, TRY_LEAVE, TryCatch #7 {all -> 0x043b, blocks: (B:39:0x012a, B:41:0x0132), top: B:38:0x012a }] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x014c A[LOOP:2: B:46:0x014a->B:47:0x014c, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x015c  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x016e  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x01a3  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x038e  */
    /* JADX WARN: Type inference failed for: r4v17 */
    /* JADX WARN: Type inference failed for: r4v18 */
    /* JADX WARN: Type inference failed for: r4v20 */
    /* JADX WARN: Type inference failed for: r4v21 */
    /* JADX WARN: Type inference failed for: r4v27 */
    /* JADX WARN: Type inference failed for: r4v28 */
    /* JADX WARN: Type inference failed for: r4v29 */
    /* JADX WARN: Type inference failed for: r4v30 */
    /* JADX WARN: Type inference failed for: r4v31 */
    /* JADX WARN: Type inference failed for: r4v6 */
    /* JADX WARN: Type inference failed for: r4v7, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r4v8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Result download(String str, File file, int i, boolean z, Probe probe, Sink sink) throws IOException {
        int i2;
        long[] jArr;
        int i3;
        int i4;
        RandomAccessFile randomAccessFile;
        AtomicLong atomicLong;
        int i5;
        Sink sink2;
        AtomicLongArray atomicLongArray;
        long j;
        ?? r4;
        long j2;
        IOException iOException;
        int i6;
        CountDownLatch countDownLatch;
        long j3;
        long j4 = probe.total;
        long j5 = 0;
        if (j4 <= 0) {
            throw new IOException("未知的文件长度");
        }
        File partFileFor = partFileFor(file);
        File metaFileFor = metaFileFor(file);
        File parentFile = partFileFor.getParentFile();
        if (parentFile != null && !parentFile.isDirectory() && !parentFile.mkdirs() && !parentFile.isDirectory()) {
            throw new IOException("无法创建下载目录: " + parentFile);
        }
        int i7 = i;
        if (i7 < 1) {
            i7 = 1;
        }
        Resume readResume = readResume(metaFileFor);
        try {
            if (readResume != null) {
                String resumeRejectReason = resumeRejectReason(readResume, j4, probe.etag, partFileFor);
                if (resumeRejectReason == null) {
                    int i8 = readResume.chunks;
                    jArr = readResume.done;
                    for (long j6 : jArr) {
                        j5 += j6;
                    }
                    CNLog.i(TAG, "resume-accept file=" + file.getName() + " chunks=" + i8 + " have=" + j5 + "/" + j4);
                    i2 = i8;
                    String str2 = " chunks=";
                    String str3 = TAG;
                    long j7 = i2;
                    long j8 = ((j4 + j7) - 1) / j7;
                    long[] jArr2 = new long[i2];
                    long[] jArr3 = new long[i2];
                    AtomicLongArray atomicLongArray2 = new AtomicLongArray(i2);
                    i3 = 0;
                    while (i3 < i2) {
                        int i9 = i2;
                        String str4 = str2;
                        long j9 = i3 * j8;
                        jArr2[i3] = j9;
                        File file2 = metaFileFor;
                        String str5 = str3;
                        jArr3[i3] = Math.min((j9 + j8) - 1, j4 - 1);
                        atomicLongArray2.set(i3, jArr != null ? jArr[i3] : 0L);
                        i3++;
                        i2 = i9;
                        str2 = str4;
                        metaFileFor = file2;
                        str3 = str5;
                    }
                    i4 = i2;
                    String str6 = str2;
                    File file3 = metaFileFor;
                    String str7 = str3;
                    randomAccessFile = new RandomAccessFile(partFileFor, "rw");
                    if (randomAccessFile.length() != j4) {
                        randomAccessFile.setLength(j4);
                    }
                    randomAccessFile.close();
                    saveMeta(file3, j4, probe.etag, atomicLongArray2);
                    atomicLong = new AtomicLong(0L);
                    for (i5 = 0; i5 < i4; i5++) {
                        atomicLong.addAndGet(atomicLongArray2.get(i5));
                    }
                    sink2 = sink;
                    if (sink2 != null) {
                        sink2.onTotal(j4);
                        sink2.onProgress(atomicLong.get(), j4);
                    }
                    if (atomicLong.get() < j4) {
                        promote(partFileFor, file);
                        deleteQuietly(file3);
                        if (sink2 != null) {
                            sink2.onProgress(j4, j4);
                        }
                        CNLog.i(str7, "resume-complete file=" + file.getName() + " 无需再下载");
                        return new Result(j4, probe.etag);
                    }
                    AtomicReference<IOException> atomicReference = new AtomicReference<>(null);
                    String str8 = "已取消";
                    AtomicBoolean atomicBoolean = new AtomicBoolean(false);
                    AtomicReference<IOException> atomicReference2 = atomicReference;
                    AtomicLong atomicLong2 = new AtomicLong(System.nanoTime());
                    AtomicBoolean atomicBoolean2 = atomicBoolean;
                    AtomicLong atomicLong3 = new AtomicLong(System.nanoTime());
                    AtomicLong atomicLong4 = new AtomicLong(0L);
                    ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(i4, new ChunkThreadFactory());
                    CountDownLatch countDownLatch2 = new CountDownLatch(i4);
                    int i10 = 0;
                    while (i10 < i4) {
                        int i11 = i4;
                        ExecutorService executorService = newFixedThreadPool;
                        ChunkTask chunkTask = new ChunkTask();
                        chunkTask.url = str;
                        chunkTask.part = partFileFor;
                        chunkTask.start = jArr2[i10];
                        chunkTask.end = jArr3[i10];
                        chunkTask.done = atomicLongArray2;
                        chunkTask.idx = i10;
                        chunkTask.direct = z;
                        chunkTask.meta = file3;
                        chunkTask.total = j4;
                        chunkTask.etag = probe.etag;
                        chunkTask.totalDone = atomicLong;
                        chunkTask.windowStart = atomicLong3;
                        chunkTask.windowBytes = atomicLong4;
                        chunkTask.lastMoveNs = atomicLong2;
                        AtomicBoolean atomicBoolean3 = atomicBoolean2;
                        chunkTask.abort = atomicBoolean3;
                        sink2 = sink;
                        chunkTask.sink = sink2;
                        AtomicLong atomicLong5 = atomicLong3;
                        AtomicReference<IOException> atomicReference3 = atomicReference2;
                        chunkTask.firstErr = atomicReference3;
                        chunkTask.latch = countDownLatch2;
                        executorService.submit(chunkTask);
                        i10++;
                        i4 = i11;
                        partFileFor = partFileFor;
                        atomicBoolean2 = atomicBoolean3;
                        newFixedThreadPool = executorService;
                        atomicLong4 = atomicLong4;
                        atomicReference2 = atomicReference3;
                        atomicLong3 = atomicLong5;
                    }
                    ExecutorService executorService2 = newFixedThreadPool;
                    int i12 = i4;
                    File file4 = partFileFor;
                    AtomicReference<IOException> atomicReference4 = atomicReference2;
                    AtomicBoolean atomicBoolean4 = atomicBoolean2;
                    long nanos = TimeUnit.SECONDS.toNanos(CNMirrors.stallSeconds());
                    AtomicLongArray atomicLongArray3 = atomicLongArray2;
                    long minSpeedKbps = CNMirrors.minSpeedKbps() * 1024;
                    long nanoTime = System.nanoTime();
                    long j10 = atomicLong.get();
                    while (true) {
                        atomicLongArray = atomicLongArray3;
                        try {
                            j = j4;
                        } catch (InterruptedException e) {
                            j = j4;
                        }
                        try {
                            boolean await = countDownLatch2.await(1L, TimeUnit.SECONDS);
                            if (await) {
                                j2 = 0;
                                break;
                            }
                            long nanoTime2 = System.nanoTime();
                            r4 = await;
                            if (sink2 != null) {
                                boolean isCancelled = sink.isCancelled();
                                r4 = isCancelled;
                                if (isCancelled) {
                                    break;
                                }
                            }
                            try {
                                r4 = str8;
                                if (nanoTime2 - atomicLong2.get() > nanos) {
                                    atomicBoolean4.set(true);
                                    CNChunkedDownload$$ExternalSyntheticBackportWithForwarding0.m(atomicReference4, null, new IOException("线路停滞：" + CNMirrors.stallSeconds() + " 秒内没有任何数据"));
                                    j2 = 0;
                                    r4 = r4;
                                    break;
                                }
                                long j11 = nanoTime2 - nanoTime;
                                j2 = 0;
                                if (minSpeedKbps <= 0) {
                                    countDownLatch = countDownLatch2;
                                    j3 = nanos;
                                } else {
                                    countDownLatch = countDownLatch2;
                                    try {
                                        j3 = nanos;
                                        if (j11 >= TimeUnit.SECONDS.toNanos(10L)) {
                                            long j12 = (long) ((atomicLong.get() - j10) / (j11 / 1.0E9d));
                                            if (j12 < minSpeedKbps) {
                                                break;
                                            }
                                            j10 = atomicLong.get();
                                            nanoTime = nanoTime2;
                                        } else {
                                            continue;
                                        }
                                    } catch (InterruptedException e2) {
                                        Thread.currentThread().interrupt();
                                        atomicBoolean4.set(true);
                                        CNChunkedDownload$$ExternalSyntheticBackportWithForwarding0.m(atomicReference4, null, new IOException((String) r4));
                                        executorService2.shutdownNow();
                                        executorService2.awaitTermination(5L, TimeUnit.SECONDS);
                                        long j13 = j;
                                        saveMeta(file3, j13, probe.etag, atomicLongArray);
                                        iOException = atomicReference4.get();
                                        if (iOException == null) {
                                        }
                                    }
                                }
                                countDownLatch2 = countDownLatch;
                                j4 = j;
                                nanos = j3;
                                str8 = r4;
                                atomicLongArray3 = atomicLongArray;
                            } catch (InterruptedException e3) {
                                j2 = 0;
                                Thread.currentThread().interrupt();
                                atomicBoolean4.set(true);
                                CNChunkedDownload$$ExternalSyntheticBackportWithForwarding0.m(atomicReference4, null, new IOException((String) r4));
                                executorService2.shutdownNow();
                                executorService2.awaitTermination(5L, TimeUnit.SECONDS);
                                long j132 = j;
                                saveMeta(file3, j132, probe.etag, atomicLongArray);
                                iOException = atomicReference4.get();
                                if (iOException == null) {
                                }
                            }
                        } catch (InterruptedException e4) {
                            r4 = str8;
                            j2 = 0;
                            Thread.currentThread().interrupt();
                            atomicBoolean4.set(true);
                            CNChunkedDownload$$ExternalSyntheticBackportWithForwarding0.m(atomicReference4, null, new IOException((String) r4));
                            executorService2.shutdownNow();
                            executorService2.awaitTermination(5L, TimeUnit.SECONDS);
                            long j1322 = j;
                            saveMeta(file3, j1322, probe.etag, atomicLongArray);
                            iOException = atomicReference4.get();
                            if (iOException == null) {
                            }
                        }
                    }
                    executorService2.shutdownNow();
                    try {
                        executorService2.awaitTermination(5L, TimeUnit.SECONDS);
                    } catch (InterruptedException e5) {
                    }
                    long j13222 = j;
                    saveMeta(file3, j13222, probe.etag, atomicLongArray);
                    iOException = atomicReference4.get();
                    if (iOException == null) {
                        throw iOException;
                    }
                    long j14 = j2;
                    int i13 = 0;
                    while (true) {
                        i6 = i12;
                        if (i13 >= i6) {
                            break;
                        }
                        j14 += atomicLongArray.get(i13);
                        i13++;
                        i12 = i6;
                    }
                    if (j14 != j13222) {
                        throw new IOException("下载不完整: 已写 " + j14 + " / " + j13222);
                    }
                    long length = file4.length();
                    if (length != j13222) {
                        throw new IOException("临时文件大小异常: " + length + " / " + j13222);
                    }
                    promote(file4, file);
                    deleteQuietly(file3);
                    if (sink2 != null) {
                        sink2.onProgress(j13222, j13222);
                        sink2.onSpeed(0.0f);
                    }
                    CNLog.i(str7, "分片下载完成 file=" + file.getName() + " bytes=" + j13222 + str6 + i6);
                    return new Result(j13222, probe.etag);
                }
                CNLog.w(TAG, "resume-reject file=" + file.getName() + " reason=" + resumeRejectReason);
                deleteQuietly(metaFileFor);
            }
            if (randomAccessFile.length() != j4) {
            }
            randomAccessFile.close();
            saveMeta(file3, j4, probe.etag, atomicLongArray2);
            atomicLong = new AtomicLong(0L);
            while (i5 < i4) {
            }
            sink2 = sink;
            if (sink2 != null) {
            }
            if (atomicLong.get() < j4) {
            }
        } finally {
        }
        i2 = i7;
        jArr = null;
        String str22 = " chunks=";
        String str32 = TAG;
        long j72 = i2;
        long j82 = ((j4 + j72) - 1) / j72;
        long[] jArr22 = new long[i2];
        long[] jArr32 = new long[i2];
        AtomicLongArray atomicLongArray22 = new AtomicLongArray(i2);
        i3 = 0;
        while (i3 < i2) {
        }
        i4 = i2;
        String str62 = str22;
        File file32 = metaFileFor;
        String str72 = str32;
        randomAccessFile = new RandomAccessFile(partFileFor, "rw");
    }

    private static String resumeRejectReason(Resume resume, long j, String str, File file) {
        if (resume.total != j) {
            return "总长度不符 " + resume.total + " != " + j;
        }
        if (resume.chunks < 1 || resume.done == null || resume.done.length != resume.chunks) {
            return "分片信息损坏";
        }
        if (!file.isFile()) {
            return "临时文件不存在";
        }
        if (file.length() != j) {
            return "临时文件长度不符 " + file.length() + " != " + j;
        }
        if (resume.etag.length() > 0 && str != null && str.length() > 0 && !resume.etag.equals(str)) {
            return "ETag 已变化";
        }
        long j2 = ((resume.chunks + j) - 1) / resume.chunks;
        for (int i = 0; i < resume.chunks; i++) {
            long j3 = i * j2;
            long min = (Math.min((j3 + j2) - 1, j - 1) - j3) + 1;
            if (resume.done[i] < 0 || resume.done[i] > min) {
                return "分片 " + i + " 进度越界 " + resume.done[i] + " / " + min;
            }
        }
        return null;
    }

    /* loaded from: classes3.dex */
    private static final class ChunkThreadFactory implements ThreadFactory {
        private ChunkThreadFactory() {
        }

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable, "cnv-chunk");
            thread.setDaemon(true);
            return thread;
        }
    }

    /* loaded from: classes3.dex */
    private static final class ChunkTask implements Runnable {
        AtomicBoolean abort;
        boolean direct;
        AtomicLongArray done;
        long end;
        String etag;
        AtomicReference<IOException> firstErr;
        int idx;
        AtomicLong lastMoveNs;
        CountDownLatch latch;
        File meta;
        File part;
        Sink sink;
        long start;
        long total;
        AtomicLong totalDone;
        String url;
        AtomicLong windowBytes;
        AtomicLong windowStart;

        private ChunkTask() {
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                CNChunkedDownload.oneChunk(this.url, this.part, this.start, this.end, this.done, this.idx, this.direct, this.meta, this.total, this.etag, this.totalDone, this.windowStart, this.windowBytes, this.lastMoveNs, this.abort, this.sink);
            } finally {
                try {
                } finally {
                }
            }
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Repeating lines: [523=6] */
    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:138:0x00f0, code lost:
    
        throw new java.io.IOException("已取消");
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x01f3, code lost:
    
        r0 = r31.get(r32);
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x01f9, code lost:
    
        if (r0 < r16) goto L100;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x01fb, code lost:
    
        saveMeta(r3, r5, r37, r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x01fe, code lost:
    
        r18.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x023f, code lost:
    
        throw new java.io.IOException(r29 + r32 + " 短读: " + r0 + " / " + r16);
     */
    /* JADX WARN: Removed duplicated region for block: B:106:0x026e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0275 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void oneChunk(String str, File file, long j, long j2, AtomicLongArray atomicLongArray, int i, boolean z, File file2, long j3, String str2, AtomicLong atomicLong, AtomicLong atomicLong2, AtomicLong atomicLong3, AtomicLong atomicLong4, AtomicBoolean atomicBoolean, Sink sink) throws IOException {
        File file3;
        long j4;
        HttpURLConnection httpURLConnection;
        Throwable th;
        BufferedInputStream bufferedInputStream;
        RandomAccessFile randomAccessFile;
        BufferedInputStream bufferedInputStream2;
        String str3;
        long j5;
        long addAndGet;
        AtomicLongArray atomicLongArray2 = atomicLongArray;
        int i2 = i;
        long j6 = (j2 - j) + 1;
        long j7 = atomicLongArray.get(i);
        if (j7 >= j6) {
            return;
        }
        long j8 = j + j7;
        HttpURLConnection open = open(str, z);
        open.setRequestMethod("GET");
        open.setRequestProperty("Range", "bytes=" + j8 + "-" + j2);
        if (str2 != null && str2.length() > 0) {
            open.setRequestProperty("If-Range", str2);
        }
        open.connect();
        int responseCode = open.getResponseCode();
        String str4 = "分片 ";
        if (responseCode != 206) {
            try {
                open.disconnect();
            } catch (Throwable th2) {
            }
            throw new IOException("分片 " + i2 + " 期望 206，实得 HTTP " + responseCode);
        }
        long rangeStart = rangeStart(open.getHeaderField("Content-Range"));
        if (rangeStart >= 0 && rangeStart != j8) {
            try {
                open.disconnect();
            } catch (Throwable th3) {
            }
            throw new IOException("分片 " + i2 + " Content-Range 起点不符: " + rangeStart + " != " + j8);
        }
        try {
            BufferedInputStream bufferedInputStream3 = new BufferedInputStream(open.getInputStream(), 65536);
            try {
                RandomAccessFile randomAccessFile2 = new RandomAccessFile(file, "rw");
                try {
                    randomAccessFile2.seek(j8);
                    byte[] bArr = new byte[32768];
                    long nanoTime = System.nanoTime();
                    while (true) {
                        int read = bufferedInputStream3.read(bArr);
                        if (read == -1) {
                            file3 = file2;
                            str3 = str4;
                            randomAccessFile = randomAccessFile2;
                            bufferedInputStream2 = bufferedInputStream3;
                            j5 = j6;
                            httpURLConnection = open;
                            j4 = j3;
                            break;
                        }
                        if (read != 0) {
                            if (atomicBoolean.get()) {
                                throw new IOException("已中断");
                            }
                            if (sink != null) {
                                try {
                                    if (sink.isCancelled()) {
                                        break;
                                    }
                                } catch (Throwable th4) {
                                    file3 = file2;
                                    th = th4;
                                    randomAccessFile = randomAccessFile2;
                                    bufferedInputStream = bufferedInputStream3;
                                    httpURLConnection = open;
                                    j4 = j3;
                                    saveMeta(file3, j4, str2, atomicLongArray2);
                                    if (randomAccessFile != null) {
                                    }
                                    if (bufferedInputStream != null) {
                                    }
                                    try {
                                        httpURLConnection.disconnect();
                                        throw th;
                                    } catch (Throwable th5) {
                                        throw th;
                                    }
                                }
                            }
                            httpURLConnection = open;
                            j5 = j6;
                            try {
                                int min = (int) Math.min(read, j6 - atomicLongArray.get(i));
                                if (min <= 0) {
                                    file3 = file2;
                                    str3 = str4;
                                    randomAccessFile = randomAccessFile2;
                                    bufferedInputStream2 = bufferedInputStream3;
                                    j4 = j3;
                                    break;
                                }
                                randomAccessFile2.write(bArr, 0, min);
                                long j9 = min;
                                long addAndGet2 = atomicLongArray2.addAndGet(i2, j9);
                                str3 = str4;
                                long addAndGet3 = atomicLong.addAndGet(j9);
                                byte[] bArr2 = bArr;
                                long nanoTime2 = System.nanoTime();
                                bufferedInputStream2 = bufferedInputStream3;
                                try {
                                    atomicLong4.set(nanoTime2);
                                    addAndGet = atomicLong3.addAndGet(j9);
                                    randomAccessFile = randomAccessFile2;
                                } catch (Throwable th6) {
                                    th = th6;
                                    file3 = file2;
                                    randomAccessFile = randomAccessFile2;
                                }
                                try {
                                    long j10 = atomicLong2.get();
                                    long j11 = (nanoTime2 - j10) / 1000000;
                                    if (j11 >= 500) {
                                        try {
                                            if (atomicLong2.compareAndSet(j10, nanoTime2)) {
                                                atomicLong3.set(0L);
                                                if (sink != null) {
                                                    j4 = j3;
                                                    try {
                                                        sink.onProgress(addAndGet3, j4);
                                                        sink.onSpeed((float) (((addAndGet * 1000.0d) / j11) / 1000000.0d));
                                                    } catch (Throwable th7) {
                                                        th = th7;
                                                        bufferedInputStream = bufferedInputStream2;
                                                        atomicLongArray2 = atomicLongArray;
                                                        file3 = file2;
                                                        th = th;
                                                        saveMeta(file3, j4, str2, atomicLongArray2);
                                                        if (randomAccessFile != null) {
                                                        }
                                                        if (bufferedInputStream != null) {
                                                        }
                                                        httpURLConnection.disconnect();
                                                        throw th;
                                                    }
                                                } else {
                                                    j4 = j3;
                                                }
                                            } else {
                                                j4 = j3;
                                            }
                                        } catch (Throwable th8) {
                                            th = th8;
                                            j4 = j3;
                                        }
                                    } else {
                                        j4 = j3;
                                    }
                                    if (nanoTime2 - nanoTime > 2000000000) {
                                        atomicLongArray2 = atomicLongArray;
                                        file3 = file2;
                                        try {
                                            saveMeta(file3, j4, str2, atomicLongArray2);
                                            nanoTime = nanoTime2;
                                        } catch (Throwable th9) {
                                            th = th9;
                                            bufferedInputStream = bufferedInputStream2;
                                            th = th;
                                            saveMeta(file3, j4, str2, atomicLongArray2);
                                            if (randomAccessFile != null) {
                                                try {
                                                    randomAccessFile.close();
                                                } catch (Throwable th10) {
                                                }
                                            }
                                            if (bufferedInputStream != null) {
                                                try {
                                                    bufferedInputStream.close();
                                                } catch (Throwable th11) {
                                                }
                                            }
                                            httpURLConnection.disconnect();
                                            throw th;
                                        }
                                    } else {
                                        atomicLongArray2 = atomicLongArray;
                                        file3 = file2;
                                    }
                                    if (addAndGet2 >= j5) {
                                        break;
                                    }
                                    bArr = bArr2;
                                    str4 = str3;
                                    bufferedInputStream3 = bufferedInputStream2;
                                    i2 = i;
                                    open = httpURLConnection;
                                    j6 = j5;
                                    randomAccessFile2 = randomAccessFile;
                                } catch (Throwable th12) {
                                    th = th12;
                                    file3 = file2;
                                    j4 = j3;
                                    bufferedInputStream = bufferedInputStream2;
                                    th = th;
                                    saveMeta(file3, j4, str2, atomicLongArray2);
                                    if (randomAccessFile != null) {
                                    }
                                    if (bufferedInputStream != null) {
                                    }
                                    httpURLConnection.disconnect();
                                    throw th;
                                }
                            } catch (Throwable th13) {
                                th = th13;
                                file3 = file2;
                                randomAccessFile = randomAccessFile2;
                                bufferedInputStream2 = bufferedInputStream3;
                            }
                        }
                    }
                } catch (Throwable th14) {
                    th = th14;
                    file3 = file2;
                    randomAccessFile = randomAccessFile2;
                    bufferedInputStream2 = bufferedInputStream3;
                    httpURLConnection = open;
                }
            } catch (Throwable th15) {
                file3 = file2;
                httpURLConnection = open;
                j4 = j3;
                bufferedInputStream = bufferedInputStream3;
                th = th15;
                randomAccessFile = null;
                saveMeta(file3, j4, str2, atomicLongArray2);
                if (randomAccessFile != null) {
                }
                if (bufferedInputStream != null) {
                }
                httpURLConnection.disconnect();
                throw th;
            }
        } catch (Throwable th16) {
            file3 = file2;
            j4 = j3;
            httpURLConnection = open;
            th = th16;
            bufferedInputStream = null;
        }
        try {
            bufferedInputStream2.close();
        } catch (Throwable th17) {
        }
        try {
            httpURLConnection.disconnect();
        } catch (Throwable th18) {
            return;
        }
        httpURLConnection.disconnect();
    }

    private static synchronized void saveMeta(File file, long j, String str, AtomicLongArray atomicLongArray) {
        OutputStreamWriter outputStreamWriter;
        synchronized (CNChunkedDownload.class) {
            File file2 = new File(file.getAbsolutePath() + ".tmp");
            OutputStreamWriter outputStreamWriter2 = null;
            try {
                outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file2, false), "UTF-8");
                try {
                    StringBuilder sb = new StringBuilder();
                    sb.append(META_MAGIC).append('\n');
                    sb.append(j).append(TokenParser.SP).append(atomicLongArray.length()).append('\n');
                    sb.append(sanitize(str)).append('\n');
                    for (int i = 0; i < atomicLongArray.length(); i++) {
                        sb.append(atomicLongArray.get(i)).append('\n');
                    }
                    outputStreamWriter.write(sb.toString());
                    outputStreamWriter.flush();
                    try {
                        outputStreamWriter.close();
                    } catch (Throwable th) {
                    }
                    if (!file2.renameTo(file)) {
                        deleteQuietly(file);
                        if (!file2.renameTo(file)) {
                            deleteQuietly(file2);
                        }
                    }
                } catch (Throwable th2) {
                    if (outputStreamWriter != null) {
                        try {
                            outputStreamWriter.close();
                        } catch (Throwable th3) {
                        }
                    } else {
                        outputStreamWriter2 = outputStreamWriter;
                    }
                    try {
                        deleteQuietly(file2);
                    } finally {
                        if (outputStreamWriter2 != null) {
                            try {
                                outputStreamWriter2.close();
                            } catch (Throwable th4) {
                            }
                        }
                    }
                }
            } catch (Throwable th5) {
                outputStreamWriter = null;
            }
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Repeating lines: [595=7] */
    private static synchronized Resume readResume(File file) {
        BufferedReader bufferedReader;
        synchronized (CNChunkedDownload.class) {
            if (file.isFile()) {
                if (file.length() <= 1048576) {
                    try {
                        bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
                        try {
                            if (!META_MAGIC.equals(bufferedReader.readLine())) {
                                try {
                                    bufferedReader.close();
                                } catch (Throwable th) {
                                }
                                return null;
                            }
                            String readLine = bufferedReader.readLine();
                            if (readLine == null) {
                                try {
                                    bufferedReader.close();
                                } catch (Throwable th2) {
                                }
                                return null;
                            }
                            String[] split = readLine.trim().split("\\s+");
                            if (split.length < 2) {
                                try {
                                    bufferedReader.close();
                                } catch (Throwable th3) {
                                }
                                return null;
                            }
                            Resume resume = new Resume();
                            resume.total = Long.parseLong(split[0]);
                            resume.chunks = Integer.parseInt(split[1]);
                            if (resume.total > 0 && resume.chunks >= 1 && resume.chunks <= 64) {
                                String readLine2 = bufferedReader.readLine();
                                resume.etag = readLine2 == null ? "" : readLine2.trim();
                                resume.done = new long[resume.chunks];
                                for (int i = 0; i < resume.chunks; i++) {
                                    String readLine3 = bufferedReader.readLine();
                                    if (readLine3 == null) {
                                        try {
                                            bufferedReader.close();
                                        } catch (Throwable th4) {
                                        }
                                        return null;
                                    }
                                    resume.done[i] = Long.parseLong(readLine3.trim());
                                }
                                try {
                                    bufferedReader.close();
                                } catch (Throwable th5) {
                                }
                                return resume;
                            }
                            try {
                                bufferedReader.close();
                            } catch (Throwable th6) {
                            }
                            return null;
                        } catch (Throwable th7) {
                            if (bufferedReader != null) {
                                try {
                                    bufferedReader.close();
                                } catch (Throwable th8) {
                                }
                            }
                            return null;
                        }
                    } catch (Throwable th9) {
                        bufferedReader = null;
                    }
                }
            }
            return null;
        }
    }

    private static String sanitize(String str) {
        return str == null ? "" : str.replace(TokenParser.CR, TokenParser.SP).replace('\n', TokenParser.SP).trim();
    }

    private static void promote(File file, File file2) throws IOException {
        if (file2.exists() && !file2.delete()) {
            throw new IOException("无法替换目标文件 " + file2);
        }
        if (!file.renameTo(file2)) {
            throw new IOException("无法重命名 " + file + " -> " + file2);
        }
    }

    private static void deleteQuietly(File file) {
        if (file != null && file.exists() && !file.delete()) {
            CNLog.w(TAG, "无法删除 " + file);
        }
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

    private static long rangeStart(String str) {
        int indexOf;
        if (str == null) {
            return -1L;
        }
        String lowerCase = str.trim().toLowerCase(Locale.US);
        if (lowerCase.startsWith("bytes ") && (indexOf = lowerCase.indexOf(45, 6)) >= 0) {
            return parseLong(lowerCase.substring(6, indexOf), -1L);
        }
        return -1L;
    }

    private static long totalFromContentRange(String str) {
        String lowerCase;
        int indexOf;
        if (str != null && (indexOf = (lowerCase = str.trim().toLowerCase(Locale.US)).indexOf(47)) >= 0) {
            return parseLong(lowerCase.substring(indexOf + 1), -1L);
        }
        return -1L;
    }
}
