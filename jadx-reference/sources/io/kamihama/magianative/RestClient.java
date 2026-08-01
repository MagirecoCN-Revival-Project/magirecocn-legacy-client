package io.kamihama.magianative;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Process;
import android.util.ArrayMap;
import android.util.Log;
import androidx.work.WorkRequest;
import cz.msebera.android.httpclient.HttpHeaders;
import cz.msebera.android.httpclient.cookie.ClientCookie;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class RestClient {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static OkHttpClient http1Client;
    private final String Endpoint = "https://totentanz-9b.magi-reco.com";
    private final String LogTag = "MagiaClientJNI";
    private String UserAgent = "okhttp3 " + System.getProperty("http.agent");
    private OkHttpClient client = getUnsafeOkHttpClient();

    /* loaded from: classes2.dex */
    public static class DownloadRunnable implements Runnable {
        private final String destPath;
        private final String displayName;
        private final int fileIndex;
        public boolean result = false;
        private final String url;

        public DownloadRunnable(String str, String str2, String str3, int i) {
            this.url = str;
            this.destPath = str2;
            this.displayName = str3;
            this.fileIndex = i;
        }

        @Override // java.lang.Runnable
        public void run() {
            String str = this.url;
            String str2 = this.destPath;
            String str3 = this.displayName;
            int i = this.fileIndex;
            boolean cnDownloadFileFull = RestClient.cnDownloadFileFull(str, str2, str3, i);
            this.result = cnDownloadFileFull;
            if (cnDownloadFileFull) {
                new Thread(new UnzipRunnable(this.destPath, "/data/data/io.kamihama.totentanz/files/", i, true)).start();
            }
        }
    }

    /* loaded from: classes2.dex */
    public static class UnzipRunnable implements Runnable {
        private final boolean deleteAfterUnzip;
        private final String destPath;
        private final int fileIndex;
        private final String zipPath;

        public UnzipRunnable(String str, String str2, int i, boolean z) {
            this.zipPath = str;
            this.destPath = str2;
            this.fileIndex = i;
            this.deleteAfterUnzip = z;
        }

        @Override // java.lang.Runnable
        public void run() {
            String str = this.zipPath;
            String str2 = this.destPath;
            CNCNDownloadUI.updateSimple("⏳ 正在解压...", "⏳ 解压中: " + str.substring(str.lastIndexOf("/") + 1), 80);
            RestClient.unzip(this.zipPath, this.destPath);
            if (this.deleteAfterUnzip) {
                new File(this.zipPath).delete();
            }
            CNCNDownloadUI.markFileDone(this.fileIndex);
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(12:6|(2:7|(1:1)(5:11|12|13|15|16))|(1:20)|(2:21|22)|(2:24|(2:26|(7:28|29|30|(2:32|(2:34|(3:36|37|38)))|39|37|38)))|42|29|30|(0)|39|37|38) */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x011a, code lost:
    
        r1 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x011b, code lost:
    
        android.util.Log.e("MagiaClientJNI", r1.toString());
     */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00b2 A[Catch: Exception -> 0x011a, TryCatch #0 {Exception -> 0x011a, blocks: (B:30:0x00aa, B:32:0x00b2, B:34:0x00e0, B:36:0x00f3, B:39:0x0114), top: B:29:0x00aa }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void checkAndApplyHotUpdate() {
        Activity currentActivity;
        JSONObject fetchVersionJson;
        JSONObject fetchVersionJson2;
        if (!new File("/data/data/io.kamihama.totentanz/files/madomagi/magica/cn_base_done.flag").exists()) {
            Log.i("MagiaClientJNI", "[热更] flag不存在，跳过");
            return;
        }
        int i = 0;
        while (true) {
            currentActivity = getCurrentActivity();
            if (currentActivity != null || i >= 30) {
                break;
            }
            i++;
            try {
                Thread.sleep(100L);
            } catch (InterruptedException unused) {
            }
        }
        if (currentActivity != null) {
            CNCNDownloadUI.show(currentActivity);
            Log.i("MagiaClientJNI", "[热更] UI已显示");
        }
        try {
            fetchVersionJson2 = fetchVersionJson("https://assets.magireco.top/version_scenario.json");
        } catch (Exception e) {
            Log.e("MagiaClientJNI", e.toString());
        }
        if (fetchVersionJson2 != null) {
            int i2 = fetchVersionJson2.getInt(ClientCookie.VERSION_ATTR);
            int readLocalVersionInt = readLocalVersionInt("scenario_version");
            Log.i("MagiaClientJNI", "[热更] scenario server=" + i2 + " local=" + readLocalVersionInt);
            if (i2 > readLocalVersionInt) {
                Log.i("MagiaClientJNI", "[热更] scenario需要更新，开始下载");
                if (cnDownloadFileFull("https://assets.magireco.top/cn_scenario_update.zip", "/data/data/io.kamihama.totentanz/files/cn_scenario_update.zip", "cn_scenario_update.zip", 14)) {
                    Log.i("MagiaClientJNI", "[热更] scenario解压中...");
                    unzip("/data/data/io.kamihama.totentanz/files/cn_scenario_update.zip", "/data/data/io.kamihama.totentanz/files/");
                    new File("/data/data/io.kamihama.totentanz/files/cn_scenario_update.zip").delete();
                    saveLocalVersionInt("scenario_version", i2);
                    Log.i("MagiaClientJNI", "[热更] scenario更新完成");
                    fetchVersionJson = fetchVersionJson("https://assets.magireco.top/version_js.json");
                    if (fetchVersionJson != null) {
                        int i3 = fetchVersionJson.getInt(ClientCookie.VERSION_ATTR);
                        int readLocalVersionInt2 = readLocalVersionInt("js_version");
                        Log.i("MagiaClientJNI", "[热更] js server=" + i3 + " local=" + readLocalVersionInt2);
                        if (i3 > readLocalVersionInt2) {
                            Log.i("MagiaClientJNI", "[热更] js需要更新，开始下载");
                            if (cnDownloadFileFull("https://assets.magireco.top/cn_js_update.zip", "/data/data/io.kamihama.totentanz/files/cn_js_update_hot.zip", "cn_js_update.zip", 11)) {
                                Log.i("MagiaClientJNI", "[热更] js解压中...");
                                unzip("/data/data/io.kamihama.totentanz/files/cn_js_update_hot.zip", "/data/data/io.kamihama.totentanz/files/");
                                new File("/data/data/io.kamihama.totentanz/files/cn_js_update_hot.zip").delete();
                                saveLocalVersionInt("js_version", i3);
                                Log.i("MagiaClientJNI", "[热更] js更新完成");
                                Log.i("MagiaClientJNI", "[热更] 检查完毕，关闭UI");
                                CNCNDownloadUI.hide();
                            }
                        }
                    }
                    Log.i("MagiaClientJNI", "[热更] js无需更新");
                    Log.i("MagiaClientJNI", "[热更] 检查完毕，关闭UI");
                    CNCNDownloadUI.hide();
                }
            }
        }
        Log.i("MagiaClientJNI", "[热更] scenario无需更新");
        fetchVersionJson = fetchVersionJson("https://assets.magireco.top/version_js.json");
        if (fetchVersionJson != null) {
        }
        Log.i("MagiaClientJNI", "[热更] js无需更新");
        Log.i("MagiaClientJNI", "[热更] 检查完毕，关闭UI");
        CNCNDownloadUI.hide();
    }

    public static boolean cnDownloadFileFull(String str, String str2, String str3, int i) {
        return CNHotUpdate.download(str, str2, str3, i);
    }

    private static void downloadAllFiles() {
        Thread thread = new Thread(new DownloadRunnable("https://assets.magireco.top/cn_base_00_db.zip", "/data/data/io.kamihama.totentanz/files/cn_base_00_db.zip", "cn_base_00_db.zip", 0));
        thread.start();
        Thread thread2 = new Thread(new DownloadRunnable("https://assets.magireco.top/cn_base_01_json.zip", "/data/data/io.kamihama.totentanz/files/cn_base_01_json.zip", "cn_base_01_json.zip", 1));
        thread2.start();
        Thread thread3 = new Thread(new DownloadRunnable("https://assets.magireco.top/cn_base_02.zip", "/data/data/io.kamihama.totentanz/files/cn_base_02.zip", "cn_base_02.zip", 2));
        thread3.start();
        Thread thread4 = new Thread(new DownloadRunnable("https://assets.magireco.top/cn_base_03.zip", "/data/data/io.kamihama.totentanz/files/cn_base_03.zip", "cn_base_03.zip", 3));
        thread4.start();
        Thread thread5 = new Thread(new DownloadRunnable("https://assets.magireco.top/cn_base_04.zip", "/data/data/io.kamihama.totentanz/files/cn_base_04.zip", "cn_base_04.zip", 4));
        thread5.start();
        Thread thread6 = new Thread(new DownloadRunnable("https://assets.magireco.top/cn_base_05.zip", "/data/data/io.kamihama.totentanz/files/cn_base_05.zip", "cn_base_05.zip", 5));
        thread6.start();
        Thread thread7 = new Thread(new DownloadRunnable("https://assets.magireco.top/cn_base_06.zip", "/data/data/io.kamihama.totentanz/files/cn_base_06.zip", "cn_base_06.zip", 6));
        thread7.start();
        Thread thread8 = new Thread(new DownloadRunnable("https://assets.magireco.top/cn_magica_resource.zip", "/data/data/io.kamihama.totentanz/files/cn_magica_resource.zip", "cn_magica_resource.zip", 7));
        thread8.start();
        Thread thread9 = new Thread(new DownloadRunnable("https://assets.magireco.top/cn_scenario_img.zip", "/data/data/io.kamihama.totentanz/files/cn_scenario_img.zip", "cn_scenario_img.zip", 8));
        thread9.start();
        Thread thread10 = new Thread(new DownloadRunnable("https://assets.magireco.top/cn_voice_01.zip", "/data/data/io.kamihama.totentanz/files/cn_voice_01.zip", "cn_voice_01.zip", 9));
        thread10.start();
        Thread thread11 = new Thread(new DownloadRunnable("https://assets.magireco.top/cn_voice_02_done.zip", "/data/data/io.kamihama.totentanz/files/cn_voice_02_done.zip", "cn_voice_02_done.zip", 10));
        thread11.start();
        Thread thread12 = new Thread(new DownloadRunnable("https://assets.magireco.top/cn_js_update.zip", "/data/data/io.kamihama.totentanz/files/cn_js_update.zip", "cn_js_update.zip", 11));
        thread12.start();
        Thread thread13 = new Thread(new DownloadRunnable("https://assets.magireco.top/movie.zip", "/data/data/io.kamihama.totentanz/files/movie.zip", "movie.zip", 12));
        thread13.start();
        Thread thread14 = new Thread(new DownloadRunnable("https://assets.magireco.top/movie2.zip", "/data/data/io.kamihama.totentanz/files/movie2.zip", "movie2.zip", 13));
        thread14.start();
        Thread thread15 = new Thread(new DownloadRunnable("https://assets.magireco.top/cn_scenario_update.zip", "/data/data/io.kamihama.totentanz/files/cn_scenario_update.zip", "cn_scenario_update.zip", 14));
        thread15.start();
        Thread[] threadArr = {thread, thread2, thread3, thread4, thread5, thread6, thread7, thread8, thread9, thread10, thread11, thread12, thread13, thread14, thread15};
        for (int i = 0; i < 15; i++) {
            joinThread(threadArr[i]);
        }
    }

    private static JSONObject fetchVersionJson(String str) throws Exception {
        ResponseBody body;
        Response execute = new OkHttpClient().newCall(new Request.Builder().url(str).build()).execute();
        if (!execute.isSuccessful() || (body = execute.body()) == null) {
            return null;
        }
        return new JSONObject(body.string());
    }

    public static Activity getCurrentActivity() {
        try {
            Class<?> cls = Class.forName("android.app.ActivityThread");
            Object invoke = cls.getMethod("currentActivityThread", new Class[0]).invoke(null, new Object[0]);
            Field declaredField = cls.getDeclaredField("mActivities");
            declaredField.setAccessible(true);
            ArrayMap arrayMap = (ArrayMap) declaredField.get(invoke);
            if (arrayMap.size() <= 0) {
                return null;
            }
            Object valueAt = arrayMap.valueAt(0);
            Field declaredField2 = valueAt.getClass().getDeclaredField("activity");
            declaredField2.setAccessible(true);
            return (Activity) declaredField2.get(valueAt);
        } catch (Exception e) {
            Log.e("MagiaClientJNI", e.getMessage());
            return null;
        }
    }

    public static String getFallbackUrl(int fileIndex) {
        String[] strArr;
        if (fileIndex >= 15 || (strArr = CNCNDownloadUI.FILE_URLS) == null) {
            return null;
        }
        return strArr[fileIndex];
    }

    private static OkHttpClient getHttp1Client() {
        OkHttpClient okHttpClient = http1Client;
        if (okHttpClient != null) {
            return okHttpClient;
        }
        OkHttpClient.Builder writeTimeout = new OkHttpClient.Builder().connectTimeout(WorkRequest.DEFAULT_BACKOFF_DELAY_MILLIS, TimeUnit.MILLISECONDS).readTimeout(120000L, TimeUnit.MILLISECONDS).writeTimeout(120000L, TimeUnit.MILLISECONDS);
        ArrayList arrayList = new ArrayList();
        arrayList.add(Protocol.HTTP_1_1);
        OkHttpClient build = writeTimeout.protocols(arrayList).build();
        http1Client = build;
        return build;
    }

    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            TrustManager[] trustManagerArr = {new X509TrustManager() { // from class: io.kamihama.magianative.RestClient.1
                @Override // javax.net.ssl.X509TrustManager
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override // javax.net.ssl.X509TrustManager
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override // javax.net.ssl.X509TrustManager
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }};
            SSLContext sSLContext = SSLContext.getInstance("SSL");
            sSLContext.init(null, trustManagerArr, new SecureRandom());
            OkHttpClient.Builder writeTimeout = new OkHttpClient.Builder().sslSocketFactory(sSLContext.getSocketFactory(), (X509TrustManager) trustManagerArr[0]).hostnameVerifier(new HostnameVerifier() { // from class: io.kamihama.magianative.RestClient.2
                @Override // javax.net.ssl.HostnameVerifier
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            }).connectTimeout(WorkRequest.DEFAULT_BACKOFF_DELAY_MILLIS, TimeUnit.MILLISECONDS).readTimeout(120000L, TimeUnit.MILLISECONDS).writeTimeout(120000L, TimeUnit.MILLISECONDS);
            ArrayList arrayList = new ArrayList();
            arrayList.add(Protocol.HTTP_1_1);
            return writeTimeout.protocols(arrayList).build();
        } catch (Throwable th) {
            Log.e("MagiaClientJNI", th.toString());
            return null;
        }
    }

    public static void joinThread(Thread t) {
        if (t != null) {
            try {
                t.join();
            } catch (InterruptedException unused) {
            }
        }
    }

    private String postRequest(String url, String json) throws IOException {
        String header;
        RequestBody create = RequestBody.create(JSON, json);
        Request build = new Request.Builder().url(url).post(create).removeHeader("User-Agent").addHeader("User-Agent", this.UserAgent).build();
        Response execute = this.client.newCall(build).execute();
        if ((execute.code() != 307 && execute.code() != 308) || (header = execute.header(HttpHeaders.LOCATION)) == null) {
            return execute.body() != null ? execute.body().string() : "";
        }
        Response execute2 = this.client.newCall(build.newBuilder().url(header).post(create).removeHeader("User-Agent").addHeader("User-Agent", this.UserAgent).build()).execute();
        return execute2.body() != null ? execute2.body().string() : "";
    }

    private static int readLocalVersionInt(String str) {
        try {
            Class<?> cls = Class.forName("android.app.ActivityThread");
            return ((Context) cls.getMethod("getApplication", new Class[0]).invoke(cls.getMethod("currentActivityThread", new Class[0]).invoke(null, new Object[0]), new Object[0])).getSharedPreferences("MagiaCN", 0).getInt(str, 0);
        } catch (Exception e) {
            return 0;
        }
    }

    public static void restartApp() {
        Intent launchIntentForPackage;
        checkAndApplyHotUpdate();
        try {
            Activity currentActivity = getCurrentActivity();
            if (currentActivity != null && (launchIntentForPackage = currentActivity.getPackageManager().getLaunchIntentForPackage(currentActivity.getPackageName())) != null) {
                launchIntentForPackage.setFlags(281018368);
                currentActivity.finish();
                Thread.sleep(500L);
                currentActivity.startActivity(launchIntentForPackage);
                Thread.sleep(1000L);
            }
            Process.killProcess(Process.myPid());
        } catch (Exception e) {
            Process.killProcess(Process.myPid());
        }
    }

    private static void saveLocalVersionInt(String str, int i) {
        try {
            Class<?> cls = Class.forName("android.app.ActivityThread");
            ((Context) cls.getMethod("getApplication", new Class[0]).invoke(cls.getMethod("currentActivityThread", new Class[0]).invoke(null, new Object[0]), new Object[0])).getSharedPreferences("MagiaCN", 0).edit().putInt(str, i).apply();
        } catch (Exception e) {
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: BlockProcessor
        jadx.core.utils.exceptions.JadxRuntimeException: Found unreachable blocks
        	at jadx.core.dex.visitors.blocks.DominatorTree.sortBlocks(DominatorTree.java:34)
        	at jadx.core.dex.visitors.blocks.DominatorTree.compute(DominatorTree.java:24)
        	at jadx.core.dex.visitors.blocks.BlockProcessor.computeDominators(BlockProcessor.java:209)
        	at jadx.core.dex.visitors.blocks.BlockProcessor.processBlocksTree(BlockProcessor.java:50)
        	at jadx.core.dex.visitors.blocks.BlockProcessor.visit(BlockProcessor.java:44)
        */
    /* JADX WARN: Unreachable blocks removed: 9, instructions: 22 */
    public static void startCNDownload() {
        /*
            io.kamihama.magianative.CNDownloaderFix.runInstaller()
            return
            java.lang.String r0 = "MagiaClientJNI"
            java.lang.String r1 = "[CN] startCNDownload 被调用"
            android.util.Log.i(r0, r1)
            android.app.Activity r1 = getCurrentActivity()
            if (r1 == 0) goto L1d
            io.kamihama.magianative.CNCNDownloadUI.show(r1)     // Catch: java.lang.Exception -> L15
            goto L1d
        L15:
            r2 = move-exception
            java.lang.String r3 = r2.toString()
            android.util.Log.e(r0, r3)
        L1d:
            java.lang.String r1 = "/data/data/io.kamihama.totentanz/files/madomagi/magica/cn_base_done.flag"
            java.io.File r2 = new java.io.File
            r2.<init>(r1)
            boolean r1 = r2.exists()
            if (r1 == 0) goto L2e
            io.kamihama.magianative.CNCNDownloadUI.hide()
            goto La0
        L2e:
            java.lang.String r1 = "[CN] 开始全新安装(14线程并发模式)"
            android.util.Log.i(r0, r1)
            java.lang.String r1 = "/data/data/io.kamihama.totentanz/files/madomagi/magica"
            java.io.File r2 = new java.io.File
            r2.<init>(r1)
            r2.mkdirs()
            downloadAllFiles()
        L40:
            int[] r5 = io.kamihama.magianative.CNCNDownloadUI.fileStatus
            if (r5 == 0) goto L57
            r6 = 0
            r7 = 15
        L47:
            if (r6 >= r7) goto L57
            r8 = r5[r6]
            r9 = 2
            if (r8 < r9) goto L51
            int r6 = r6 + 1
            goto L47
        L51:
            r8 = 200(0xc8, double:9.9E-322)
            java.lang.Thread.sleep(r8)     // Catch: java.lang.InterruptedException -> L56
        L56:
            goto L40
        L57:
            java.lang.String r1 = "✅ 安装完成！写入标记..."
            java.lang.String r2 = "🔗 游戏即将重启"
            r5 = 100
            io.kamihama.magianative.CNCNDownloadUI.updateSimple(r1, r2, r5)
            java.lang.String r1 = "/data/data/io.kamihama.totentanz/files/madomagi/magica/cn_base_done.flag"
            java.io.File r2 = new java.io.File     // Catch: java.lang.Exception -> L95
            r2.<init>(r1)     // Catch: java.lang.Exception -> L95
            java.io.File r3 = r2.getParentFile()     // Catch: java.lang.Exception -> L95
            r3.mkdirs()     // Catch: java.lang.Exception -> L95
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch: java.lang.Exception -> L95
            java.io.File r3 = new java.io.File     // Catch: java.lang.Exception -> L95
            r3.<init>(r1)     // Catch: java.lang.Exception -> L95
            r2.<init>(r3)     // Catch: java.lang.Exception -> L95
            java.lang.String r3 = "done"
            byte[] r3 = r3.getBytes()     // Catch: java.lang.Exception -> L95
            r2.write(r3)     // Catch: java.lang.Exception -> L95
            r2.close()     // Catch: java.lang.Exception -> L95
            io.kamihama.magianative.CNCNDownloadUI.hide()
            java.lang.String r1 = "[CN] ★ 安装完成，准备重启"
            android.util.Log.i(r0, r1)
            r2 = 2000(0x7d0, double:9.88E-321)
            java.lang.Thread.sleep(r2)     // Catch: java.lang.InterruptedException -> L91
        L91:
            restartApp()
            goto La0
        L95:
            r1 = move-exception
            java.lang.String r2 = r1.getMessage()
            android.util.Log.e(r0, r2)
            io.kamihama.magianative.CNCNDownloadUI.hide()
        La0:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: io.kamihama.magianative.RestClient.startCNDownload():void");
    }

    public static void unzip(String zipFilePath, String destPath) {
        try {
            File file = new File(destPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFilePath));
            for (ZipEntry nextEntry = zipInputStream.getNextEntry(); nextEntry != null; nextEntry = zipInputStream.getNextEntry()) {
                String str = destPath + File.separator + nextEntry.getName();
                if (nextEntry.isDirectory()) {
                    new File(str).mkdirs();
                } else {
                    File file2 = new File(str);
                    if (!file2.isDirectory()) {
                        file2.getParentFile().mkdirs();
                        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(str));
                        byte[] bArr = new byte[4096];
                        while (true) {
                            int read = zipInputStream.read(bArr);
                            if (read == -1) {
                                break;
                            } else {
                                bufferedOutputStream.write(bArr, 0, read);
                            }
                        }
                        bufferedOutputStream.close();
                    }
                }
                zipInputStream.closeEntry();
            }
            zipInputStream.close();
            Log.i("MagiaClientJNI", "【解压】成功: " + zipFilePath);
        } catch (Exception e) {
            Log.e("MagiaClientJNI", "【解压】失败: " + e.getMessage());
        }
    }

    /*  JADX ERROR: NullPointerException in pass: BlockProcessor
        java.lang.NullPointerException: Cannot invoke "jadx.core.dex.nodes.BlockNode.getPredecessors()" because "to" is null
        	at jadx.core.dex.visitors.blocks.BlockSplitter.removeConnection(BlockSplitter.java:164)
        	at jadx.core.dex.visitors.blocks.BlockExceptionHandler.removeExcHandler(BlockExceptionHandler.java:324)
        	at jadx.core.dex.visitors.blocks.BlockExceptionHandler.lambda$prepareTryBlocks$2(BlockExceptionHandler.java:207)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
        	at jadx.core.dex.visitors.blocks.BlockExceptionHandler.prepareTryBlocks(BlockExceptionHandler.java:207)
        	at jadx.core.dex.visitors.blocks.BlockExceptionHandler.process(BlockExceptionHandler.java:60)
        	at jadx.core.dex.visitors.blocks.BlockProcessor.independentBlockTreeMod(BlockProcessor.java:325)
        	at jadx.core.dex.visitors.blocks.BlockProcessor.processBlocksTree(BlockProcessor.java:51)
        	at jadx.core.dex.visitors.blocks.BlockProcessor.visit(BlockProcessor.java:44)
        */
    /* JADX WARN: Unreachable blocks removed: 8, instructions: 30 */
    public java.lang.String GetEndpoint(int r6) {
        /*
            r5 = this;
            java.lang.String r0 = io.kamihama.magianative.CNDownloaderFix.getEndpoint(r6)
            return r0
            org.json.JSONObject r1 = new org.json.JSONObject
            r1.<init>()
            java.lang.String r2 = "version"
            r1.put(r2, r6)     // Catch: org.json.JSONException -> L1a
            java.lang.String r2 = "https://totentanz-9b.magi-reco.com/magica/api/snaa"
            java.lang.String r3 = r1.toString()     // Catch: java.io.IOException -> L3a
            java.lang.String r2 = r5.postRequest(r2, r3)     // Catch: java.io.IOException -> L3a
        L19:
            return r2
        L1a:
            r0 = move-exception
            java.lang.String r2 = "MagiaClientJNI"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Error adding version: "
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r4 = r0.toString()
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            android.util.Log.e(r2, r3)
            java.lang.String r2 = ""
            goto L19
        L3a:
            r0 = move-exception
            java.lang.String r2 = "MagiaClientJNI"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Error with request: "
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r4 = r0.toString()
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            android.util.Log.e(r2, r3)
            java.lang.String r2 = ""
            goto L19
        */
        throw new UnsupportedOperationException("Method not decompiled: io.kamihama.magianative.RestClient.GetEndpoint(int):java.lang.String");
    }
}
