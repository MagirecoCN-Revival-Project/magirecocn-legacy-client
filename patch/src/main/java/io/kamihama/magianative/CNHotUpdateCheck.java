package io.kamihama.magianative;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Locale;

import org.json.JSONObject;

/**
 * 热更新检查：并行查询台词包和前端包清单，下载、完整性校验、应用并安全重启。
 *
 * <p>前端覆盖发生时 Activity/WebView 已经存在，RequireJS 可能已经持有旧模块；
 * 因此任一包成功提交后必须重启。版本清单同时作为下载包的完整性声明：优先
 * SHA-256；当前服务端仍只有 MD5 时兼容校验并记录降级警告。长度或摘要不符时
 * 失败关闭，不解压、不写本地版本号。
 */
public final class CNHotUpdateCheck {

    private static final String TAG = "MagiaCNHotUpdate";
    private static final String FILES_DIR  = "/data/data/io.kamihama.totentanz/files/";
    private static final String FINAL_FLAG = FILES_DIR + "madomagi/magica/cn_base_done.flag";
    private static final String PREFS_NAME = "MagiaCN";

    private static final int  ACTIVITY_WAIT_TRIES = 150;
    private static final long ACTIVITY_WAIT_STEP_MS = 100L;
    private static final long IDLE_LINGER_MS = 1800L;
    private static final long WATCHDOG_PERIOD_MS = 1000L;
    private static final int VER_CONNECT_TIMEOUT_MS = 5000;
    private static final int VER_READ_TIMEOUT_MS    = 8000;

    private static final java.util.concurrent.atomic.AtomicBoolean STARTED =
            new java.util.concurrent.atomic.AtomicBoolean(false);
    private static volatile boolean running = false;
    private static volatile String pendingRestartMsg = null;

    static boolean isRunning() { return running; }

    static boolean requestRestartWhenDone(String toastText) {
        if (!running) return false;
        pendingRestartMsg = toastText;
        return true;
    }

    private CNHotUpdateCheck() {}

    private static final class Pkg {
        final String label;
        final String versionUrl;
        final String versionKey;
        final String zipUrl;
        final String tmpName;
        final int slot;

        Pkg(String label, String versionUrl, String versionKey,
            String zipUrl, String tmpName, int slot) {
            this.label = label;
            this.versionUrl = versionUrl;
            this.versionKey = versionKey;
            this.zipUrl = zipUrl;
            this.tmpName = tmpName;
            this.slot = slot;
        }
    }

    /** 服务端清单；size/摘要属于版本身份的一部分。 */
    private static final class RemoteManifest {
        final int version;
        final long size;
        final String sha256;
        final String md5;

        RemoteManifest(int version, long size, String sha256, String md5) {
            this.version = version;
            this.size = size;
            this.sha256 = sha256;
            this.md5 = md5;
        }
    }

    private static final Pkg[] PACKAGES = {
        new Pkg("台词包",
                "https://r2.assets.magireco.top/version_scenario.json", "scenario_version",
                "https://r2.assets.magireco.top/cn_scenario_update.zip",
                "cn_scenario_update.zip", 0),
        new Pkg("前端脚本",
                "https://r2.assets.magireco.top/version_js.json", "js_version",
                "https://r2.assets.magireco.top/cn_js_update.zip",
                "cn_js_update_hot.zip", 1),
    };

    public static void start() {
        try {
            if (!STARTED.compareAndSet(false, true)) {
                CNLog.i(TAG, "热更检查已经在跑，忽略重复调用");
                return;
            }
            Thread thread = new Thread("cnv-hotupdate") {
                @Override public void run() {
                    try {
                        runInner();
                    } catch (Throwable error) {
                        CNLog.e(TAG, "热更检查异常终止: " + error, error);
                        try { CNCNDownloadUI.hide(); } catch (Throwable ignore) {}
                    } finally {
                        running = false;
                    }
                }
            };
            thread.setDaemon(true);
            thread.start();
        } catch (Throwable error) {
            running = false;
            try { android.util.Log.e(TAG, "热更检查线程起不来", error); }
            catch (Throwable ignore) {}
        }
    }

    private static void runInner() {
        if (!new File(FINAL_FLAG).isFile()) {
            CNLog.i(TAG, "安装完成标记不存在，跳过热更检查（首次安装会把两个热更包一并下完）");
            return;
        }
        CNLog.i(TAG, "热更检查开始");

        new Thread(new Runnable() {
            @Override public void run() {
                CNMirrors.refresh(false);
                if (!CNMirrors.isLoaded()) CNMirrors.refresh(true);
            }
        }, "cnv-mirrors-prewarm").start();

        Activity activity = awaitUsableActivity();
        if (activity == null) {
            CNLog.w(TAG, "等不到可用的 Activity，本次热更检查将无浮层运行");
        } else {
            showOverlay(activity);
        }

        java.util.concurrent.ScheduledExecutorService watchdog = startWatchdog(activity);
        boolean applied = false;
        running = true;
        try {
            CNCNDownloadUI.updateSimple("检查热更新", "正在查询台词与前端脚本的版本…", 0);
            final java.util.concurrent.ExecutorService pool =
                    java.util.concurrent.Executors.newFixedThreadPool(2);
            java.util.concurrent.Future<RemoteManifest> scenarioFuture =
                    pool.submit(new java.util.concurrent.Callable<RemoteManifest>() {
                        @Override public RemoteManifest call() {
                            return fetchManifestSafe(PACKAGES[0]);
                        }
                    });
            java.util.concurrent.Future<RemoteManifest> jsFuture =
                    pool.submit(new java.util.concurrent.Callable<RemoteManifest>() {
                        @Override public RemoteManifest call() {
                            return fetchManifestSafe(PACKAGES[1]);
                        }
                    });
            RemoteManifest[] manifests = new RemoteManifest[2];
            try {
                manifests[0] = scenarioFuture.get();
                manifests[1] = jsFuture.get();
            } catch (Throwable error) {
                CNLog.w(TAG, "并行版本查询异常: " + error);
            } finally {
                pool.shutdownNow();
            }
            for (int i = 0; i < PACKAGES.length; i++) {
                if (applyIfNewer(PACKAGES[i], manifests[i])) applied = true;
            }
        } finally {
            stopWatchdog(watchdog);
        }

        if (applied) {
            CNLog.i(TAG, "热更检查完毕：已应用更新，将重启以启用全部新文件");
            CNCNDownloadUI.updateSimple("更新完成", "热更新已应用，即将重启游戏", 0);
        } else {
            CNLog.i(TAG, "热更检查完毕：无需更新");
            CNCNDownloadUI.updateSimple("已是最新", "台词与前端脚本均为最新版本，即将进入游戏", 0);
        }
        sleep(IDLE_LINGER_MS);

        running = false;
        CNCNDownloadUI.hide();
        String requested = pendingRestartMsg;
        pendingRestartMsg = null;

        if (applied) {
            if (requested != null) CNLog.i(TAG, "教程胶囊的重启请求已并入热更新重启");
            CNDownloaderFix.noticeAndRestart("热更新已应用，3 秒后自动重启游戏");
            return;
        }
        if (requested != null) {
            CNLog.i(TAG, "检查已收工，执行教程胶囊请求的重启");
            CNDownloaderFix.noticeAndRestart(requested);
        }
    }

    private static RemoteManifest fetchManifestSafe(Pkg pkg) {
        try {
            return fetchManifest(pkg.versionUrl);
        } catch (Throwable error) {
            CNLog.w(TAG, "[" + pkg.label + "] 清单查询失败，跳过：" + error);
            CNCNDownloadUI.updateSimple("检查热更新",
                    pkg.label + "：版本查询失败，跳过本项", 0);
            return null;
        }
    }

    private static boolean applyIfNewer(Pkg pkg, RemoteManifest manifest) {
        if (manifest == null || manifest.version <= 0) return false;

        int local = readLocalVersion(pkg.versionKey);
        CNLog.i(TAG, "[" + pkg.label + "] server=" + manifest.version
                + " local=" + local + " size=" + manifest.size);
        if (manifest.version <= local) {
            CNCNDownloadUI.updateSimple("检查热更新",
                    pkg.label + "：已是最新（v" + local + "）", 0);
            return false;
        }

        CNLog.i(TAG, "[" + pkg.label + "] 需要更新 " + local + " → " + manifest.version);
        CNCNDownloadUI.updateSimple("下载热更新",
                pkg.label + "：v" + local + " → v" + manifest.version + "，正在下载…", 0);

        File tmp = new File(FILES_DIR, pkg.tmpName);
        if (tmp.exists() && !tmp.delete()) {
            CNLog.w(TAG, "[" + pkg.label + "] 删不掉旧的临时包 " + tmp + "，放弃本项");
            return false;
        }
        if (!CNHotUpdate.download(pkg.zipUrl, tmp.getAbsolutePath(), pkg.tmpName, pkg.slot)) {
            CNLog.e(TAG, "[" + pkg.label + "] 下载失败，本项不更新（版本号保持 " + local + "）");
            CNCNDownloadUI.updateSimple("下载热更新", pkg.label + "：下载失败，已跳过", 0);
            return false;
        }

        CNCNDownloadUI.updateSimple("验证热更新", pkg.label + "：正在校验文件完整性…", 0);
        try {
            verifyArchive(tmp, manifest, pkg.label);
        } catch (Throwable error) {
            CNLog.e(TAG, "[" + pkg.label + "] 完整性校验失败，拒绝解压", error);
            CNCNDownloadUI.updateSimple("验证热更新", pkg.label + "：文件校验失败，已拒绝应用", 0);
            deleteQuietly(tmp);
            return false;
        }

        CNCNDownloadUI.updateSimple("应用热更新", pkg.label + "：正在解压…", 0);
        try {
            CNDownloaderFix.extractChecked(tmp, new File(FILES_DIR));
        } catch (Throwable error) {
            CNLog.e(TAG, "[" + pkg.label + "] 解压失败，版本号保持 " + local, error);
            CNCNDownloadUI.updateSimple("应用热更新", pkg.label + "：解压失败，已跳过", 0);
            deleteQuietly(tmp);
            return false;
        }
        deleteQuietly(tmp);
        saveLocalVersion(pkg.versionKey, manifest.version);
        CNLog.i(TAG, "[" + pkg.label + "] 更新完成，版本号记为 " + manifest.version);
        CNCNDownloadUI.updateSimple("应用热更新",
                pkg.label + "：已更新到 v" + manifest.version, 0);
        return true;
    }

    /** 长度 + 摘要校验。SHA-256 优先；MD5 只为兼容当前服务端清单。 */
    private static void verifyArchive(File archive, RemoteManifest manifest, String label)
            throws Exception {
        if (!archive.isFile()) throw new java.io.IOException("下载文件不存在");
        if (manifest.size <= 0) throw new java.io.IOException("清单缺少有效 size");
        if (archive.length() != manifest.size) {
            throw new java.io.IOException("长度不符: " + archive.length()
                    + " / " + manifest.size);
        }

        String algorithm;
        String expected;
        if (!manifest.sha256.isEmpty()) {
            algorithm = "SHA-256";
            expected = manifest.sha256;
        } else if (!manifest.md5.isEmpty()) {
            algorithm = "MD5";
            expected = manifest.md5;
            CNLog.w(TAG, "[" + label + "] 清单尚无 SHA-256，使用 MD5 兼容校验；"
                    + "分发端应尽快增加 sha256 字段");
        } else {
            throw new java.io.IOException("清单没有 sha256 或 md5，拒绝可执行热更新");
        }

        MessageDigest digest = MessageDigest.getInstance(algorithm);
        InputStream input = new BufferedInputStream(new FileInputStream(archive), 65536);
        try {
            byte[] buffer = new byte[65536];
            int count;
            while ((count = input.read(buffer)) >= 0) {
                if (count > 0) digest.update(buffer, 0, count);
            }
        } finally {
            try { input.close(); } catch (Throwable ignore) {}
        }
        String actual = hex(digest.digest());
        if (!actual.equals(expected)) {
            throw new java.io.IOException(algorithm + " 不符: " + actual + " / " + expected);
        }
        CNLog.i(TAG, "[" + label + "] " + algorithm + " 与长度校验通过");
    }

    private static String hex(byte[] value) {
        char[] digits = "0123456789abcdef".toCharArray();
        char[] output = new char[value.length * 2];
        for (int i = 0; i < value.length; i++) {
            int b = value[i] & 0xff;
            output[i * 2] = digits[b >>> 4];
            output[i * 2 + 1] = digits[b & 0xf];
        }
        return new String(output);
    }

    private static Activity awaitUsableActivity() {
        Activity last = null;
        for (int i = 0; i < ACTIVITY_WAIT_TRIES; i++) {
            Activity activity = null;
            try { activity = RestClient.getCurrentActivity(); } catch (Throwable ignore) {}
            if (activity != null) {
                last = activity;
                try {
                    if (activity.getWindow() != null
                            && activity.getWindow().peekDecorView() != null) {
                        if (i > 0) CNLog.i(TAG, "等到可用 Activity，耗时约 " + (i * 100) + "ms");
                        return activity;
                    }
                } catch (Throwable ignore) {}
            }
            sleep(ACTIVITY_WAIT_STEP_MS);
        }
        return last;
    }

    private static void showOverlay(Activity activity) {
        for (int i = 0; i < 3; i++) {
            try {
                CNCNDownloadUI.show(activity);
                CNCNDownloadUI.ensureVisible(activity);
            } catch (Throwable error) {
                CNLog.w(TAG, "show() 第 " + (i + 1) + " 次失败：" + error);
            }
            if (CNCNDownloadUI.isShowing) return;
            sleep(400L);
        }
        CNLog.e(TAG, "浮层始终建不起来，热更将无界面运行");
    }

    private static java.util.concurrent.ScheduledExecutorService startWatchdog(
            final Activity activity) {
        if (activity == null) return null;
        try {
            java.util.concurrent.ScheduledExecutorService executor =
                    java.util.concurrent.Executors.newSingleThreadScheduledExecutor();
            executor.scheduleWithFixedDelay(new Runnable() {
                @Override public void run() {
                    try { CNCNDownloadUI.ensureVisible(activity); }
                    catch (Throwable ignore) {}
                }
            }, WATCHDOG_PERIOD_MS, WATCHDOG_PERIOD_MS,
               java.util.concurrent.TimeUnit.MILLISECONDS);
            return executor;
        } catch (Throwable error) {
            CNLog.w(TAG, "看门狗起不来：" + error);
            return null;
        }
    }

    private static void stopWatchdog(java.util.concurrent.ScheduledExecutorService executor) {
        if (executor != null) {
            try { executor.shutdownNow(); } catch (Throwable ignore) {}
        }
    }

    private static RemoteManifest fetchManifest(String url) throws Exception {
        String base = CNMirrors.DEFAULT_BASE;
        String name = url.startsWith(base) ? url.substring(base.length()) : url;
        if (!CNMirrors.isLoaded()) {
            CNMirrors.refresh(false);
            if (!CNMirrors.isLoaded()) CNMirrors.refresh(true);
        }
        Exception last = null;
        for (CNMirrors.Mirror mirror : CNMirrors.healthy()) {
            try {
                return fetchManifestDirect(mirror.urlFor(name));
            } catch (Exception error) {
                CNLog.w(TAG, "版本 json 线路失败 mirror=" + mirror.name + ": " + error);
                CNMirrors.reportFailure(mirror, "version json");
                last = error;
            }
        }
        throw last != null ? last : new java.io.IOException("无可用线路");
    }

    private static RemoteManifest fetchManifestDirect(String url) throws Exception {
        HttpURLConnection connection =
                (HttpURLConnection) new URL(url).openConnection(Proxy.NO_PROXY);
        try {
            connection.setConnectTimeout(VER_CONNECT_TIMEOUT_MS);
            connection.setReadTimeout(VER_READ_TIMEOUT_MS);
            connection.setInstanceFollowRedirects(true);
            int code = connection.getResponseCode();
            if (code / 100 != 2) throw new java.io.IOException("HTTP " + code);
            InputStream input = new BufferedInputStream(connection.getInputStream(), 8192);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            try {
                byte[] buffer = new byte[8192];
                int count;
                while ((count = input.read(buffer)) >= 0 && output.size() < 65536) {
                    if (count > 0) output.write(buffer, 0, count);
                }
            } finally {
                try { input.close(); } catch (Throwable ignore) {}
            }
            JSONObject json = new JSONObject(output.toString("UTF-8"));
            int version = json.getInt("version");
            long size = json.optLong("size", -1L);
            String sha256 = normalizeDigest(json.optString("sha256", ""), 64, "sha256");
            String md5 = normalizeDigest(json.optString("md5", ""), 32, "md5");
            if (size <= 0) throw new java.io.IOException("清单 size 无效");
            if (sha256.isEmpty() && md5.isEmpty()) {
                throw new java.io.IOException("清单缺少摘要");
            }
            return new RemoteManifest(version, size, sha256, md5);
        } finally {
            try { connection.disconnect(); } catch (Throwable ignore) {}
        }
    }

    private static String normalizeDigest(String value, int length, String field)
            throws java.io.IOException {
        String normalized = value == null ? "" : value.trim().toLowerCase(Locale.US);
        if (normalized.isEmpty()) return "";
        if (normalized.length() != length || !normalized.matches("[0-9a-f]+")) {
            throw new java.io.IOException("清单 " + field + " 格式无效");
        }
        return normalized;
    }

    private static SharedPreferences prefs() {
        Context context = appContext();
        return context == null ? null
                : context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    private static int readLocalVersion(String key) {
        try {
            SharedPreferences preferences = prefs();
            return preferences == null ? 0 : preferences.getInt(key, 0);
        } catch (Throwable error) {
            CNLog.w(TAG, "读本地版本号失败（" + key + "）：" + error);
            return 0;
        }
    }

    private static void saveLocalVersion(String key, int value) {
        try {
            SharedPreferences preferences = prefs();
            if (preferences == null) {
                CNLog.e(TAG, "拿不到 Context，版本号 " + key + "=" + value + " 没能落盘");
                return;
            }
            if (!preferences.edit().putInt(key, value).commit()) {
                CNLog.e(TAG, "版本号 commit 返回 false（" + key + "）");
            }
        } catch (Throwable error) {
            CNLog.e(TAG, "写本地版本号失败（" + key + "）", error);
        }
    }

    private static Context appContext() {
        try {
            Class<?> cls = Class.forName("android.app.ActivityThread");
            Object thread = cls.getMethod("currentActivityThread").invoke(null);
            return (Context) cls.getMethod("getApplication").invoke(thread);
        } catch (Throwable error) {
            return null;
        }
    }

    private static void deleteQuietly(File file) {
        try {
            if (file != null && file.exists() && !file.delete()) {
                CNLog.w(TAG, "删不掉临时文件 " + file);
            }
        } catch (Throwable ignore) {}
    }

    private static void sleep(long millis) {
        try { Thread.sleep(millis); }
        catch (InterruptedException interrupted) {
            Thread.currentThread().interrupt();
        }
    }
}
