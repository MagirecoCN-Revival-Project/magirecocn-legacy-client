package io.kamihama.magianative;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

/**
 * 热更新 ZIP 的可恢复事务提交器。
 *
 * <p>旧流程把条目直接写进 {@code files/} 活动目录；进程被杀或后续条目失败会留下
 * 半新半旧前端。本类先在同一文件系统的事务目录完整解压，再写入清单并切换到
 * COMMITTING 状态，之后通过 rename 备份旧文件、提交新文件。进程中途死亡时，
 * 下一次启动依据 stage/backup 的存在状态自动回滚。
 */
final class CNHotUpdateTransaction {

    private static final String TAG = "MagiaCNHotTxn";
    private static final String TX_ROOT_NAME = ".cn_hot_transaction";
    private static final String STAGE_NAME = "stage";
    private static final String BACKUP_NAME = "backup";
    private static final String MANIFEST_NAME = "manifest.json";
    private static final String STATE_NAME = "state";

    private static final String STATE_PREPARING = "PREPARING";
    private static final String STATE_PREPARED = "PREPARED";
    private static final String STATE_COMMITTING = "COMMITTING";
    private static final String STATE_ROLLING_BACK = "ROLLING_BACK";
    private static final String STATE_COMMITTED = "COMMITTED";

    private static final int MAX_ENTRIES = 100000;
    private static final long MAX_ENTRY_BYTES = 2L * 1024L * 1024L * 1024L;
    private static final long MAX_TOTAL_BYTES = 8L * 1024L * 1024L * 1024L;
    private static final Charset UTF8 = Charset.forName("UTF-8");

    private CNHotUpdateTransaction() {}

    private static final class Entry {
        final String path;
        final boolean existed;

        Entry(String path, boolean existed) {
            this.path = path;
            this.existed = existed;
        }
    }

    /** 启动时调用；有未完成提交就回滚，只有 COMMITTED 状态会直接清理。 */
    static void recover(File filesRoot) throws IOException {
        File txRoot = new File(filesRoot, TX_ROOT_NAME);
        if (!txRoot.exists()) return;
        requireContained(filesRoot, txRoot);

        String state = readState(txRoot);
        if (STATE_COMMITTED.equals(state)) {
            CNLog.i(TAG, "发现已提交但未清理的事务，完成清理");
            deleteTree(txRoot, txRoot);
            return;
        }
        if (STATE_PREPARING.equals(state) || STATE_PREPARED.equals(state)
                || state.length() == 0) {
            // 活动目录尚未开始变化。
            CNLog.w(TAG, "发现未开始提交的残留事务，删除 staging");
            deleteTree(txRoot, txRoot);
            return;
        }
        if (!STATE_COMMITTING.equals(state) && !STATE_ROLLING_BACK.equals(state)) {
            throw new IOException("未知热更新事务状态: " + state);
        }

        List<Entry> entries = readManifest(new File(txRoot, MANIFEST_NAME));
        writeState(txRoot, STATE_ROLLING_BACK);
        if (!rollback(filesRoot, txRoot, entries)) {
            throw new IOException("热更新事务回滚不完整，保留事务目录等待下次重试");
        }
        deleteTree(txRoot, txRoot);
        CNLog.i(TAG, "未完成热更新事务已完整回滚");
    }

    /** 完整解压、事务提交；成功返回后活动目录已经是整包新版本。 */
    static void apply(File archive, File filesRoot) throws IOException {
        recover(filesRoot);
        File txRoot = new File(filesRoot, TX_ROOT_NAME);
        File stageRoot = new File(txRoot, STAGE_NAME);
        File backupRoot = new File(txRoot, BACKUP_NAME);
        if (!stageRoot.mkdirs() && !stageRoot.isDirectory()) {
            throw new IOException("无法创建热更新 staging: " + stageRoot);
        }
        if (!backupRoot.mkdirs() && !backupRoot.isDirectory()) {
            throw new IOException("无法创建热更新 backup: " + backupRoot);
        }
        writeState(txRoot, STATE_PREPARING);

        List<String> paths;
        try {
            paths = extractToStage(archive, stageRoot);
        } catch (IOException error) {
            deleteTree(txRoot, txRoot);
            throw error;
        }

        List<Entry> entries = new ArrayList<Entry>(paths.size());
        for (String relative : paths) {
            File destination = safeDestination(filesRoot, relative);
            entries.add(new Entry(relative, destination.exists()));
        }
        writeManifest(new File(txRoot, MANIFEST_NAME), entries);
        writeState(txRoot, STATE_PREPARED);
        writeState(txRoot, STATE_COMMITTING);

        try {
            commit(filesRoot, txRoot, entries);
            writeState(txRoot, STATE_COMMITTED);
        } catch (IOException error) {
            CNLog.e(TAG, "事务提交失败，开始回滚", error);
            writeState(txRoot, STATE_ROLLING_BACK);
            boolean restored = rollback(filesRoot, txRoot, entries);
            if (restored) deleteTree(txRoot, txRoot);
            else CNLog.e(TAG, "回滚不完整，事务目录保留: " + txRoot);
            throw error;
        }

        deleteTree(txRoot, txRoot);
        CNLog.i(TAG, "热更新事务提交完成: " + entries.size() + " 个文件");
    }

    private static List<String> extractToStage(File archive, File stageRoot)
            throws IOException {
        if (!archive.isFile()) throw new IOException("热更新包不存在: " + archive);
        String rootCanonical = stageRoot.getCanonicalPath();
        String prefix = rootCanonical + File.separator;
        Set<String> exact = new HashSet<String>();
        Set<String> folded = new HashSet<String>();
        List<String> paths = new ArrayList<String>();
        long total = 0L;

        ZipFile zip = new ZipFile(archive);
        try {
            Enumeration<? extends ZipEntry> enumeration = zip.entries();
            int count = 0;
            while (enumeration.hasMoreElements()) {
                ZipEntry entry = enumeration.nextElement();
                String name = normalizeEntry(entry.getName());
                if (entry.isDirectory()) continue;
                if (++count > MAX_ENTRIES) throw new ZipException("ZIP 条目过多");
                if (!exact.add(name)) throw new ZipException("ZIP 重复路径: " + name);
                String lower = name.toLowerCase(Locale.US);
                if (!folded.add(lower)) throw new ZipException("ZIP 大小写冲突: " + name);

                long declared = entry.getSize();
                if (declared > MAX_ENTRY_BYTES) {
                    throw new ZipException("ZIP 单文件过大: " + name);
                }
                File target = new File(stageRoot, name);
                String canonical = target.getCanonicalPath();
                if (!canonical.startsWith(prefix)) {
                    throw new ZipException("ZIP 路径越界: " + name);
                }
                File parent = target.getParentFile();
                if (parent != null && !parent.isDirectory()
                        && !parent.mkdirs() && !parent.isDirectory()) {
                    throw new IOException("无法创建 staging 目录: " + parent);
                }

                InputStream input = null;
                FileOutputStream raw = null;
                BufferedOutputStream output = null;
                long copied = 0L;
                try {
                    input = new BufferedInputStream(zip.getInputStream(entry), 65536);
                    raw = new FileOutputStream(target);
                    output = new BufferedOutputStream(raw, 65536);
                    byte[] buffer = new byte[65536];
                    int read;
                    while ((read = input.read(buffer)) >= 0) {
                        if (read == 0) continue;
                        copied += read;
                        total += read;
                        if (copied > MAX_ENTRY_BYTES || total > MAX_TOTAL_BYTES) {
                            throw new ZipException("ZIP 解压尺寸超限: " + name);
                        }
                        output.write(buffer, 0, read);
                    }
                    output.flush();
                    raw.getFD().sync();
                } finally {
                    closeQuietly(output);
                    closeQuietly(raw);
                    closeQuietly(input);
                }
                if (declared >= 0 && copied != declared) {
                    throw new ZipException("ZIP 条目长度不符: " + name);
                }
                paths.add(name);
            }
        } finally {
            try { zip.close(); } catch (Throwable ignore) {}
        }
        if (paths.isEmpty()) throw new ZipException("热更新包没有文件条目");
        return paths;
    }

    private static String normalizeEntry(String raw) throws ZipException {
        if (raw == null || raw.length() == 0 || raw.startsWith("/")
                || raw.indexOf('\\') >= 0 || raw.indexOf('\u0000') >= 0
                || raw.indexOf('\t') >= 0 || raw.indexOf('\n') >= 0
                || raw.indexOf('\r') >= 0) {
            throw new ZipException("不安全 ZIP 路径: " + raw);
        }
        String[] parts = raw.split("/", -1);
        if (parts.length < 2
                || !("magica".equals(parts[0]) || "madomagi".equals(parts[0]))) {
            throw new ZipException("不允许的热更新根路径: " + raw);
        }
        StringBuilder normalized = new StringBuilder();
        for (String part : parts) {
            if (part.length() == 0 || ".".equals(part) || "..".equals(part)) {
                throw new ZipException("不安全 ZIP 路径段: " + raw);
            }
            if (normalized.length() > 0) normalized.append('/');
            normalized.append(part);
        }
        if (normalized.toString().startsWith(TX_ROOT_NAME + "/")) {
            throw new ZipException("ZIP 不得覆盖事务目录");
        }
        return normalized.toString();
    }

    private static void commit(File filesRoot, File txRoot, List<Entry> entries)
            throws IOException {
        File stageRoot = new File(txRoot, STAGE_NAME);
        File backupRoot = new File(txRoot, BACKUP_NAME);
        for (Entry entry : entries) {
            File staged = safeDestination(stageRoot, entry.path);
            File destination = safeDestination(filesRoot, entry.path);
            File backup = safeDestination(backupRoot, entry.path);
            if (!staged.isFile()) throw new IOException("staging 文件缺失: " + entry.path);

            File destinationParent = destination.getParentFile();
            if (destinationParent != null && !destinationParent.isDirectory()
                    && !destinationParent.mkdirs() && !destinationParent.isDirectory()) {
                throw new IOException("无法创建目标目录: " + destinationParent);
            }
            if (entry.existed) {
                if (!destination.exists()) {
                    throw new IOException("准备提交时旧文件消失: " + entry.path);
                }
                File backupParent = backup.getParentFile();
                if (backupParent != null && !backupParent.isDirectory()
                        && !backupParent.mkdirs() && !backupParent.isDirectory()) {
                    throw new IOException("无法创建备份目录: " + backupParent);
                }
                if (!destination.renameTo(backup)) {
                    throw new IOException("无法备份旧文件: " + entry.path);
                }
            } else if (destination.exists()) {
                throw new IOException("准备提交时目标意外出现: " + entry.path);
            }

            if (!staged.renameTo(destination)) {
                // 当前文件的旧版本已经移走，立刻尝试原地恢复；其余由统一 rollback。
                if (entry.existed && backup.exists()) backup.renameTo(destination);
                throw new IOException("无法提交 staging 文件: " + entry.path);
            }
        }
    }

    private static boolean rollback(File filesRoot, File txRoot, List<Entry> entries) {
        File stageRoot = new File(txRoot, STAGE_NAME);
        File backupRoot = new File(txRoot, BACKUP_NAME);
        boolean ok = true;
        for (int i = entries.size() - 1; i >= 0; --i) {
            Entry entry = entries.get(i);
            try {
                File staged = safeDestination(stageRoot, entry.path);
                File destination = safeDestination(filesRoot, entry.path);
                File backup = safeDestination(backupRoot, entry.path);
                if (entry.existed) {
                    if (backup.exists()) {
                        if (destination.exists() && !deleteTree(filesRoot, destination)) ok = false;
                        File parent = destination.getParentFile();
                        if (parent != null && !parent.isDirectory()
                                && !parent.mkdirs() && !parent.isDirectory()) ok = false;
                        if (!backup.renameTo(destination)) ok = false;
                    } else if (!staged.exists() && !destination.exists()) {
                        // staging 已移出、backup 又不存在：无法证明旧文件还在。
                        ok = false;
                    }
                } else if (!staged.exists() && destination.exists()) {
                    // 原本不存在且 staging 已移出，说明新文件已提交，应删除。
                    if (!deleteTree(filesRoot, destination)) ok = false;
                }
            } catch (Throwable error) {
                CNLog.e(TAG, "回滚条目失败: " + entry.path, error);
                ok = false;
            }
        }
        return ok;
    }

    private static void writeManifest(File file, List<Entry> entries) throws IOException {
        JSONArray array = new JSONArray();
        for (Entry entry : entries) {
            JSONObject object = new JSONObject();
            object.put("path", entry.path);
            object.put("existed", entry.existed);
            array.put(object);
        }
        writeAtomic(file, array.toString());
    }

    private static List<Entry> readManifest(File file) throws IOException {
        try {
            String text = readSmall(file, 16 * 1024 * 1024);
            JSONArray array = new JSONArray(text);
            List<Entry> entries = new ArrayList<Entry>(array.length());
            for (int i = 0; i < array.length(); ++i) {
                JSONObject object = array.getJSONObject(i);
                String path = normalizeEntry(object.getString("path"));
                entries.add(new Entry(path, object.getBoolean("existed")));
            }
            return entries;
        } catch (Exception error) {
            throw new IOException("无法读取热更新事务清单", error);
        }
    }

    private static void writeState(File txRoot, String state) throws IOException {
        writeAtomic(new File(txRoot, STATE_NAME), state + "\n");
    }

    private static String readState(File txRoot) throws IOException {
        File file = new File(txRoot, STATE_NAME);
        if (!file.isFile()) return "";
        return readSmall(file, 1024).trim();
    }

    private static void writeAtomic(File destination, String text) throws IOException {
        File parent = destination.getParentFile();
        if (parent != null && !parent.isDirectory()
                && !parent.mkdirs() && !parent.isDirectory()) {
            throw new IOException("无法创建事务元数据目录: " + parent);
        }
        File temp = new File(destination.getPath() + ".tmp");
        FileOutputStream output = new FileOutputStream(temp);
        try {
            output.write(text.getBytes(UTF8));
            output.flush();
            output.getFD().sync();
        } finally {
            closeQuietly(output);
        }
        if (destination.exists() && !destination.delete()) {
            throw new IOException("无法替换事务元数据: " + destination);
        }
        if (!temp.renameTo(destination)) {
            throw new IOException("无法提交事务元数据: " + destination);
        }
    }

    private static String readSmall(File file, int limit) throws IOException {
        if (!file.isFile() || file.length() > limit) {
            throw new IOException("事务元数据缺失或过大: " + file);
        }
        InputStream input = new BufferedInputStream(new java.io.FileInputStream(file));
        try {
            java.io.ByteArrayOutputStream output = new java.io.ByteArrayOutputStream();
            byte[] buffer = new byte[8192];
            int read;
            while ((read = input.read(buffer)) >= 0) {
                if (read == 0) continue;
                if (output.size() + read > limit) throw new IOException("事务元数据过大");
                output.write(buffer, 0, read);
            }
            return output.toString("UTF-8");
        } finally {
            closeQuietly(input);
        }
    }

    private static File safeDestination(File root, String relative) throws IOException {
        File destination = new File(root, relative);
        String rootCanonical = root.getCanonicalPath();
        String destinationCanonical = destination.getCanonicalPath();
        if (!destinationCanonical.startsWith(rootCanonical + File.separator)) {
            throw new IOException("目标路径越界: " + relative);
        }
        return destination;
    }

    private static void requireContained(File root, File child) throws IOException {
        String rootCanonical = root.getCanonicalPath();
        String childCanonical = child.getCanonicalPath();
        if (!childCanonical.startsWith(rootCanonical + File.separator)) {
            throw new IOException("事务目录越界: " + child);
        }
    }

    /** 删除受控树；若节点是符号链接，只删除链接本身，不跟随到树外。 */
    private static boolean deleteTree(File root, File node) throws IOException {
        requireContained(root, node);
        File absolute = node.getAbsoluteFile();
        File canonical = node.getCanonicalFile();
        boolean symlink = !absolute.equals(canonical);
        if (!symlink && node.isDirectory()) {
            File[] children = node.listFiles();
            if (children == null) return false;
            for (File child : children) {
                if (!deleteTree(root, child)) return false;
            }
        }
        return !node.exists() || node.delete();
    }

    private static void closeQuietly(java.io.Closeable closeable) {
        if (closeable == null) return;
        try { closeable.close(); } catch (Throwable ignore) {}
    }
}
