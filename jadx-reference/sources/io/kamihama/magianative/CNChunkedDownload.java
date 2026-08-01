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
    private static final String META_MAGIC = "CNVPROG3";
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
        String url;

        private Resume() {
            this.etag = "";
            this.url = "";
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

    /* JADX DEBUG: Don't trust debug lines info. Repeating lines: [152=4, 171=4] */
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

    /* JADX DEBUG: Duplicate block (B:99:0x0377) to fix multi-entry loop: BACK_EDGE: B:99:0x0377 -> B:71:0x038b */
    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:104:? */
    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:105:0x0369 */
    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:129:0x038b */
    /* JADX DEBUG: Finally have unexpected throw blocks count: 2, expect 1 */
    /* JADX DEBUG: Multi-variable search result rejected for r6v8, resolved type: java.lang.String */
    /* JADX WARN: Can't wrap try/catch for region: R(13:(2:19|(20:21|(2:24|22)|25|26|27|(3:29|(2:31|32)(2:34|35)|33)|36|37|38|39|(1:41)|43|44|45|(1:47)|48|49|(1:51)|52|(4:54|(1:56)|57|58)(10:59|(1:61)|62|63|(6:64|65|66|67|68|(5:70|(2:72|(1:74))|109|110|(3:132|133|134)(3:112|(4:114|115|116|(2:118|(1:124)(3:120|121|122))(1:127))(2:130|131)|123))(2:135|136))|78|79|80|81|(5:83|(1:85)|86|87|(2:89|(4:91|(1:93)(1:96)|94|95)(2:97|98))(2:99|100))(1:101)))(1:152))(1:154)|38|39|(0)|43|44|45|(0)|48|49|(0)|52|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:125:0x0314, code lost:
    
        r4.set(true);
        io.kamihama.magianative.CNChunkedDownload$$ExternalSyntheticBackportWithForwarding0.m(r8, null, new java.io.IOException("线路过慢：" + ((r1 * 8) / 1000) + " kbps < " + io.kamihama.magianative.CNMirrors.minSpeedKbps() + " kbps"));
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x029a, code lost:
    
        r4.set(true);
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x02a0, code lost:
    
        r6 = r19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x02a2, code lost:
    
        io.kamihama.magianative.CNChunkedDownload$$ExternalSyntheticBackportWithForwarding0.m(r8, null, new java.io.IOException((java.lang.String) r6));
        r16 = 0;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:101:0x0465  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0106  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x013f A[Catch: all -> 0x0466, TRY_LEAVE, TryCatch #3 {all -> 0x0466, blocks: (B:39:0x0137, B:41:0x013f), top: B:38:0x0137 }] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0164 A[LOOP:2: B:46:0x0162->B:47:0x0164, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0172  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0184  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x01b9  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x03b4  */
    /* JADX WARN: Type inference failed for: r6v10 */
    /* JADX WARN: Type inference failed for: r6v11 */
    /* JADX WARN: Type inference failed for: r6v15, types: [boolean] */
    /* JADX WARN: Type inference failed for: r6v16 */
    /* JADX WARN: Type inference failed for: r6v17, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r6v19, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r6v38 */
    /* JADX WARN: Type inference failed for: r6v39 */
    /* JADX WARN: Type inference failed for: r6v9, types: [java.lang.String] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Result download(String str, File file, int i, boolean z, Probe probe, Sink sink) throws IOException {
        int i2;
        Object obj;
        String str2;
        String str3;
        File file2;
        int i3;
        long[] jArr;
        int i4;
        RandomAccessFile randomAccessFile;
        int i5;
        AtomicLong atomicLong;
        int i6;
        AtomicLongArray atomicLongArray;
        String str4;
        long j;
        IOException iOException;
        long j2;
        AtomicLong atomicLong2;
        long j3;
        long j4 = probe.total;
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
            if (readResume == null) {
                i2 = i7;
                obj = "已取消";
                str2 = " chunks=";
                str3 = TAG;
                file2 = metaFileFor;
            } else {
                String str5 = probe.etag;
                obj = "已取消";
                str2 = " chunks=";
                str3 = TAG;
                i2 = i7;
                file2 = metaFileFor;
                String resumeRejectReason = resumeRejectReason(readResume, j4, str5, str, partFileFor);
                if (resumeRejectReason == null) {
                    int i8 = readResume.chunks;
                    jArr = readResume.done;
                    long j5 = 0;
                    for (long j6 : jArr) {
                        j5 += j6;
                    }
                    CNLog.i(str3, "resume-accept file=" + file.getName() + str2 + i8 + " have=" + j5 + "/" + j4);
                    i3 = i8;
                    long j7 = i3;
                    long j8 = ((j4 + j7) - 1) / j7;
                    long[] jArr2 = new long[i3];
                    long[] jArr3 = new long[i3];
                    AtomicLongArray atomicLongArray2 = new AtomicLongArray(i3);
                    i4 = 0;
                    while (i4 < i3) {
                        String str6 = str3;
                        long j9 = i4 * j8;
                        jArr2[i4] = j9;
                        long j10 = j8;
                        jArr3[i4] = Math.min((j9 + j8) - 1, j4 - 1);
                        atomicLongArray2.set(i4, jArr != null ? jArr[i4] : 0L);
                        i4++;
                        str3 = str6;
                        j8 = j10;
                    }
                    String str7 = str3;
                    File file3 = partFileFor;
                    randomAccessFile = new RandomAccessFile(file3, "rw");
                    if (randomAccessFile.length() != j4) {
                        randomAccessFile.setLength(j4);
                    }
                    randomAccessFile.close();
                    AtomicLongArray atomicLongArray3 = atomicLongArray2;
                    long[] jArr4 = jArr3;
                    i5 = i3;
                    saveMeta(file2, j4, probe.etag, str, atomicLongArray3);
                    atomicLong = new AtomicLong(0L);
                    for (i6 = 0; i6 < i5; i6++) {
                        atomicLong.addAndGet(atomicLongArray3.get(i6));
                    }
                    if (sink != null) {
                        sink.onTotal(j4);
                        sink.onProgress(atomicLong.get(), j4);
                    }
                    if (atomicLong.get() < j4) {
                        promote(file3, file);
                        deleteQuietly(file2);
                        if (sink != null) {
                            sink.onProgress(j4, j4);
                        }
                        CNLog.i(str7, "resume-complete file=" + file.getName() + " 无需再下载");
                        return new Result(j4, probe.etag);
                    }
                    AtomicReference<IOException> atomicReference = new AtomicReference<>(null);
                    AtomicBoolean atomicBoolean = new AtomicBoolean(false);
                    AtomicLong atomicLong3 = new AtomicLong(System.nanoTime());
                    AtomicLong atomicLong4 = new AtomicLong(System.nanoTime());
                    AtomicLong atomicLong5 = new AtomicLong(0L);
                    ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(i5, new ChunkThreadFactory());
                    CountDownLatch countDownLatch = new CountDownLatch(i5);
                    int i9 = 0;
                    while (i9 < i5) {
                        String str8 = str2;
                        ChunkTask chunkTask = new ChunkTask();
                        chunkTask.url = str;
                        chunkTask.part = file3;
                        AtomicBoolean atomicBoolean2 = atomicBoolean;
                        chunkTask.start = jArr2[i9];
                        chunkTask.end = jArr4[i9];
                        chunkTask.done = atomicLongArray3;
                        chunkTask.idx = i9;
                        chunkTask.direct = z;
                        File file4 = file2;
                        chunkTask.meta = file4;
                        chunkTask.total = j4;
                        chunkTask.etag = probe.etag;
                        chunkTask.totalDone = atomicLong;
                        chunkTask.windowStart = atomicLong4;
                        chunkTask.windowBytes = atomicLong5;
                        AtomicLong atomicLong6 = atomicLong3;
                        chunkTask.lastMoveNs = atomicLong6;
                        chunkTask.abort = atomicBoolean2;
                        chunkTask.sink = sink;
                        chunkTask.firstErr = atomicReference;
                        chunkTask.latch = countDownLatch;
                        newFixedThreadPool.submit(chunkTask);
                        i9++;
                        jArr4 = jArr4;
                        atomicLong4 = atomicLong4;
                        str2 = str8;
                        atomicLong3 = atomicLong6;
                        file2 = file4;
                        atomicBoolean = atomicBoolean2;
                        file3 = file3;
                    }
                    File file5 = file3;
                    AtomicBoolean atomicBoolean3 = atomicBoolean;
                    String str9 = str2;
                    File file6 = file2;
                    AtomicLong atomicLong7 = atomicLong3;
                    long nanos = TimeUnit.SECONDS.toNanos(CNMirrors.stallSeconds());
                    long minSpeedKbps = (CNMirrors.minSpeedKbps() * 1000) / 8;
                    long nanoTime = System.nanoTime();
                    long j11 = atomicLong.get();
                    while (true) {
                        try {
                            atomicLongArray = atomicLongArray3;
                        } catch (InterruptedException e) {
                            atomicLongArray = atomicLongArray3;
                        }
                        try {
                            str4 = countDownLatch.await(1L, TimeUnit.SECONDS);
                            if (str4 != 0) {
                                j = 0;
                                break;
                            }
                            long nanoTime2 = System.nanoTime();
                            str4 = str4;
                            if (sink != null) {
                                boolean isCancelled = sink.isCancelled();
                                str4 = isCancelled;
                                if (isCancelled) {
                                    break;
                                }
                            }
                            try {
                                str4 = obj;
                                if (nanoTime2 - atomicLong7.get() > nanos) {
                                    atomicBoolean3.set(true);
                                    CNChunkedDownload$$ExternalSyntheticBackportWithForwarding0.m(atomicReference, null, new IOException("线路停滞：" + CNMirrors.stallSeconds() + " 秒内没有任何数据"));
                                    j = 0;
                                    break;
                                }
                                long j12 = nanoTime2 - nanoTime;
                                j = 0;
                                if (minSpeedKbps <= 0) {
                                    atomicLong2 = atomicLong7;
                                    j3 = nanos;
                                } else {
                                    atomicLong2 = atomicLong7;
                                    try {
                                        j3 = nanos;
                                        if (j12 >= TimeUnit.SECONDS.toNanos(10L)) {
                                            long j13 = (long) ((atomicLong.get() - j11) / (j12 / 1.0E9d));
                                            if (j13 < minSpeedKbps) {
                                                break;
                                            }
                                            j11 = atomicLong.get();
                                            nanoTime = nanoTime2;
                                        } else {
                                            continue;
                                        }
                                    } catch (InterruptedException e2) {
                                        Thread.currentThread().interrupt();
                                        atomicBoolean3.set(true);
                                        CNChunkedDownload$$ExternalSyntheticBackportWithForwarding0.m(atomicReference, null, new IOException((String) str4));
                                    }
                                }
                                obj = str4;
                                atomicLongArray3 = atomicLongArray;
                                atomicLong7 = atomicLong2;
                                nanos = j3;
                            } catch (InterruptedException e3) {
                                j = 0;
                                Thread.currentThread().interrupt();
                                atomicBoolean3.set(true);
                                CNChunkedDownload$$ExternalSyntheticBackportWithForwarding0.m(atomicReference, null, new IOException((String) str4));
                                newFixedThreadPool.shutdownNow();
                                newFixedThreadPool.awaitTermination(5L, TimeUnit.SECONDS);
                                saveMeta(file6, j4, probe.etag, str, atomicLongArray);
                                iOException = atomicReference.get();
                                if (iOException != null) {
                                }
                            }
                        } catch (InterruptedException e4) {
                            str4 = obj;
                            j = 0;
                            Thread.currentThread().interrupt();
                            atomicBoolean3.set(true);
                            CNChunkedDownload$$ExternalSyntheticBackportWithForwarding0.m(atomicReference, null, new IOException((String) str4));
                            newFixedThreadPool.shutdownNow();
                            newFixedThreadPool.awaitTermination(5L, TimeUnit.SECONDS);
                            saveMeta(file6, j4, probe.etag, str, atomicLongArray);
                            iOException = atomicReference.get();
                            if (iOException != null) {
                            }
                        }
                    }
                    newFixedThreadPool.shutdownNow();
                    try {
                        newFixedThreadPool.awaitTermination(5L, TimeUnit.SECONDS);
                    } catch (InterruptedException e5) {
                    }
                    saveMeta(file6, j4, probe.etag, str, atomicLongArray);
                    iOException = atomicReference.get();
                    if (iOException != null) {
                        throw iOException;
                    }
                    long j14 = j;
                    for (int i10 = 0; i10 < i5; i10++) {
                        j14 += atomicLongArray.get(i10);
                    }
                    if (j14 != j4) {
                        throw new IOException("下载不完整: 已写 " + j14 + " / " + j4);
                    }
                    long length = file5.length();
                    if (length != j4) {
                        throw new IOException("临时文件大小异常: " + length + " / " + j4);
                    }
                    promote(file5, file);
                    deleteQuietly(file6);
                    if (sink == null) {
                        j2 = j4;
                    } else {
                        j2 = j4;
                        sink.onProgress(j2, j2);
                        sink.onSpeed(0.0f);
                    }
                    CNLog.i(str7, "分片下载完成 file=" + file.getName() + " bytes=" + j2 + str9 + i5);
                    return new Result(j2, probe.etag);
                }
                CNLog.w(str3, "resume-reject file=" + file.getName() + " reason=" + resumeRejectReason);
                deleteQuietly(file2);
            }
            if (randomAccessFile.length() != j4) {
            }
            randomAccessFile.close();
            AtomicLongArray atomicLongArray32 = atomicLongArray2;
            long[] jArr42 = jArr3;
            i5 = i3;
            saveMeta(file2, j4, probe.etag, str, atomicLongArray32);
            atomicLong = new AtomicLong(0L);
            while (i6 < i5) {
            }
            if (sink != null) {
            }
            if (atomicLong.get() < j4) {
            }
        } finally {
        }
        i3 = i2;
        jArr = null;
        long j72 = i3;
        long j82 = ((j4 + j72) - 1) / j72;
        long[] jArr22 = new long[i3];
        long[] jArr32 = new long[i3];
        AtomicLongArray atomicLongArray22 = new AtomicLongArray(i3);
        i4 = 0;
        while (i4 < i3) {
        }
        String str72 = str3;
        File file32 = partFileFor;
        randomAccessFile = new RandomAccessFile(file32, "rw");
    }

    private static String resumeRejectReason(Resume resume, long j, String str, String str2, File file) {
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
        if ((resume.url.length() > 0 && resume.url.equals(str2)) && resume.etag.length() > 0 && str != null && str.length() > 0 && !resume.etag.equals(str)) {
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

    /* JADX DEBUG: Don't trust debug lines info. Repeating lines: [538=6] */
    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:130:0x00eb, code lost:
    
        throw new java.io.IOException("已取消");
     */
    /* JADX WARN: Removed duplicated region for block: B:103:0x025b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:107:0x01a3  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x018d  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x01b1  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x01b0 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0262 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void oneChunk(String str, File file, long j, long j2, AtomicLongArray atomicLongArray, int i, boolean z, File file2, long j3, String str2, AtomicLong atomicLong, AtomicLong atomicLong2, AtomicLong atomicLong3, AtomicLong atomicLong4, AtomicBoolean atomicBoolean, Sink sink) throws IOException {
        HttpURLConnection httpURLConnection;
        Throwable th;
        RandomAccessFile randomAccessFile;
        BufferedInputStream bufferedInputStream;
        RandomAccessFile randomAccessFile2;
        String str3;
        long j4;
        long j5;
        AtomicLong atomicLong5 = atomicLong3;
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
            throw new IOException("分片 " + i + " 期望 206，实得 HTTP " + responseCode);
        }
        long rangeStart = rangeStart(open.getHeaderField("Content-Range"));
        if (rangeStart >= 0 && rangeStart != j8) {
            try {
                open.disconnect();
            } catch (Throwable th3) {
            }
            throw new IOException("分片 " + i + " Content-Range 起点不符: " + rangeStart + " != " + j8);
        }
        try {
            BufferedInputStream bufferedInputStream2 = new BufferedInputStream(open.getInputStream(), 65536);
            try {
                RandomAccessFile randomAccessFile3 = new RandomAccessFile(file, "rw");
                try {
                    randomAccessFile3.seek(j8);
                    byte[] bArr = new byte[32768];
                    long nanoTime = System.nanoTime();
                    while (true) {
                        int read = bufferedInputStream2.read(bArr);
                        if (read == -1) {
                            randomAccessFile2 = randomAccessFile3;
                            str3 = str4;
                            j4 = j6;
                            httpURLConnection = open;
                            bufferedInputStream = bufferedInputStream2;
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
                                    th = th4;
                                    randomAccessFile = randomAccessFile3;
                                    httpURLConnection = open;
                                    bufferedInputStream = bufferedInputStream2;
                                    saveMeta(file2, j3, str2, str, atomicLongArray);
                                    if (randomAccessFile != null) {
                                        try {
                                            randomAccessFile.close();
                                        } catch (Throwable th5) {
                                        }
                                    }
                                    if (bufferedInputStream != null) {
                                        try {
                                            bufferedInputStream.close();
                                        } catch (Throwable th6) {
                                        }
                                    }
                                    try {
                                        httpURLConnection.disconnect();
                                        throw th;
                                    } catch (Throwable th7) {
                                        throw th;
                                    }
                                }
                            }
                            BufferedInputStream bufferedInputStream3 = bufferedInputStream2;
                            String str5 = str4;
                            httpURLConnection = open;
                            try {
                                int min = (int) Math.min(read, j6 - atomicLongArray.get(i));
                                if (min <= 0) {
                                    bufferedInputStream = bufferedInputStream3;
                                    str3 = str5;
                                    randomAccessFile2 = randomAccessFile3;
                                    j4 = j6;
                                    break;
                                }
                                randomAccessFile3.write(bArr, 0, min);
                                long j9 = min;
                                long addAndGet = atomicLongArray.addAndGet(i, j9);
                                byte[] bArr2 = bArr;
                                long addAndGet2 = atomicLong.addAndGet(j9);
                                j4 = j6;
                                long nanoTime2 = System.nanoTime();
                                atomicLong4.set(nanoTime2);
                                long addAndGet3 = atomicLong5.addAndGet(j9);
                                RandomAccessFile randomAccessFile4 = randomAccessFile3;
                                try {
                                    long j10 = atomicLong2.get();
                                    long j11 = (nanoTime2 - j10) / 1000000;
                                    if (j11 >= 500) {
                                        try {
                                            if (atomicLong2.compareAndSet(j10, nanoTime2)) {
                                                j5 = 0;
                                                atomicLong5.set(0L);
                                                if (sink != null) {
                                                    try {
                                                        sink.onProgress(addAndGet2, j3);
                                                        sink.onSpeed((float) (((addAndGet3 * 1000.0d) / j11) / 1000000.0d));
                                                    } catch (Throwable th8) {
                                                        th = th8;
                                                        bufferedInputStream = bufferedInputStream3;
                                                        th = th;
                                                        randomAccessFile = randomAccessFile4;
                                                        saveMeta(file2, j3, str2, str, atomicLongArray);
                                                        if (randomAccessFile != null) {
                                                        }
                                                        if (bufferedInputStream != null) {
                                                        }
                                                        httpURLConnection.disconnect();
                                                        throw th;
                                                    }
                                                }
                                                if (nanoTime2 - nanoTime <= 2000000000) {
                                                    randomAccessFile2 = randomAccessFile4;
                                                    bufferedInputStream = bufferedInputStream3;
                                                    str3 = str5;
                                                    try {
                                                        saveMeta(file2, j3, str2, str, atomicLongArray);
                                                        nanoTime = nanoTime2;
                                                    } catch (Throwable th9) {
                                                        th = th9;
                                                        th = th;
                                                        randomAccessFile = randomAccessFile2;
                                                        saveMeta(file2, j3, str2, str, atomicLongArray);
                                                        if (randomAccessFile != null) {
                                                        }
                                                        if (bufferedInputStream != null) {
                                                        }
                                                        httpURLConnection.disconnect();
                                                        throw th;
                                                    }
                                                } else {
                                                    bufferedInputStream = bufferedInputStream3;
                                                    str3 = str5;
                                                    randomAccessFile2 = randomAccessFile4;
                                                }
                                                if (addAndGet < j4) {
                                                    break;
                                                }
                                                atomicLong5 = atomicLong3;
                                                str4 = str3;
                                                randomAccessFile3 = randomAccessFile2;
                                                bufferedInputStream2 = bufferedInputStream;
                                                open = httpURLConnection;
                                                bArr = bArr2;
                                                j6 = j4;
                                            }
                                        } catch (Throwable th10) {
                                            th = th10;
                                        }
                                    }
                                    j5 = 0;
                                    if (nanoTime2 - nanoTime <= 2000000000) {
                                    }
                                    if (addAndGet < j4) {
                                    }
                                } catch (Throwable th11) {
                                    th = th11;
                                    bufferedInputStream = bufferedInputStream3;
                                    randomAccessFile2 = randomAccessFile4;
                                }
                            } catch (Throwable th12) {
                                th = th12;
                                bufferedInputStream = bufferedInputStream3;
                                randomAccessFile2 = randomAccessFile3;
                            }
                        }
                    }
                    long j12 = atomicLongArray.get(i);
                    if (j12 < j4) {
                        throw new IOException(str3 + i + " 短读: " + j12 + " / " + j4);
                    }
                    saveMeta(file2, j3, str2, str, atomicLongArray);
                    try {
                        randomAccessFile2.close();
                    } catch (Throwable th13) {
                    }
                    try {
                        bufferedInputStream.close();
                    } catch (Throwable th14) {
                    }
                    try {
                        httpURLConnection.disconnect();
                    } catch (Throwable th15) {
                    }
                } catch (Throwable th16) {
                    th = th16;
                    randomAccessFile2 = randomAccessFile3;
                    httpURLConnection = open;
                    bufferedInputStream = bufferedInputStream2;
                }
            } catch (Throwable th17) {
                httpURLConnection = open;
                bufferedInputStream = bufferedInputStream2;
                th = th17;
                randomAccessFile = null;
            }
        } catch (Throwable th18) {
            httpURLConnection = open;
            th = th18;
            randomAccessFile = null;
            bufferedInputStream = null;
        }
    }

    private static synchronized void saveMeta(File file, long j, String str, String str2, AtomicLongArray atomicLongArray) {
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
                    sb.append(sanitize(str2)).append('\n');
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

    /* JADX DEBUG: Don't trust debug lines info. Repeating lines: [615=7] */
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
                                String readLine3 = bufferedReader.readLine();
                                resume.url = readLine3 == null ? "" : readLine3.trim();
                                resume.done = new long[resume.chunks];
                                for (int i = 0; i < resume.chunks; i++) {
                                    String readLine4 = bufferedReader.readLine();
                                    if (readLine4 == null) {
                                        try {
                                            bufferedReader.close();
                                        } catch (Throwable th4) {
                                        }
                                        return null;
                                    }
                                    resume.done[i] = Long.parseLong(readLine4.trim());
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
