#!/usr/bin/env python3
"""Harden the pinned-main parallel hot-update checker in a generated tree."""
from __future__ import annotations

import argparse
import json
from pathlib import Path
import sys

RELATIVE = Path("io/kamihama/magianative/CNHotUpdateCheck.java")


class PrepareError(RuntimeError):
    pass


def replace_once(text: str, old: str, new: str, label: str) -> str:
    count = text.count(old)
    if count != 1:
        raise PrepareError(f"{label}: expected one match, found {count}")
    return text.replace(old, new, 1)


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("--java-root", type=Path, required=True)
    parser.add_argument("--report", type=Path)
    args = parser.parse_args()
    target = args.java_root / RELATIVE

    try:
        text = target.read_text("utf-8")
        required_main = (
            "java.util.concurrent.Executors.newFixedThreadPool(2)",
            "java.util.LinkedHashMap<Integer, java.util.concurrent.Future<Boolean>>",
            "需要更新的包并行下载",
            "收下载结果 → md5/size 校验 → 顺序解压",
        )
        for marker in required_main:
            if marker not in text:
                raise PrepareError(f"latest-main performance marker missing: {marker}")

        text = replace_once(
            text,
            '''        final String md5;
        VerMeta(int v, long s, String m) { version = v; size = s; md5 = m; }''',
            '''        final String sha256;
        final String md5;
        VerMeta(int v, long s, String sha, String m) {
            version = v;
            size = s;
            sha256 = sha == null ? "" : sha.trim().toLowerCase(java.util.Locale.US);
            md5 = m == null ? "" : m.trim().toLowerCase(java.util.Locale.US);
        }''',
            "manifest sha256 fields",
        )
        text = replace_once(
            text,
            '''            return new VerMeta(o.getInt("version"),
                             o.optLong("size", -1L),
                             o.optString("md5", ""));''',
            '''            return new VerMeta(o.getInt("version"),
                             o.optLong("size", -1L),
                             o.optString("sha256", ""),
                             o.optString("md5", ""));''',
            "manifest parser",
        )

        verify_start = text.find("    /** 下载完工校验：size 对得上、md5 对得上才放行；返回 null 表示通过。 */")
        verify_end_marker = "\n    // ==================================================================\n    // 浮层"
        verify_end = text.find(verify_end_marker, verify_start)
        if verify_start < 0 or verify_end < 0:
            raise PrepareError("verifyZip method boundary drift")
        verify_impl = '''    /**
     * 下载完工校验：长度必须匹配；SHA-256 优先，当前旧清单仅有 MD5 时
     * 兼容验证并记录降级警告。摘要缺失时拒绝可执行热更新。
     */
    private static String verifyZip(File file, VerMeta meta) {
        if (meta == null || file == null || !file.isFile()) return "文件缺失";
        if (meta.size <= 0) return "清单缺少有效 size";
        if (file.length() != meta.size) {
            return "大小不符 " + file.length() + " != " + meta.size;
        }
        String algorithm;
        String expected;
        if (meta.sha256.length() > 0) {
            if (!meta.sha256.matches("[0-9a-f]{64}")) return "sha256 格式无效";
            algorithm = "SHA-256";
            expected = meta.sha256;
        } else if (meta.md5.length() > 0) {
            if (!meta.md5.matches("[0-9a-f]{32}")) return "md5 格式无效";
            algorithm = "MD5";
            expected = meta.md5;
            CNLog.w(TAG, "清单尚无 SHA-256，暂用 MD5 兼容校验；分发端应升级");
        } else {
            return "清单没有 sha256 或 md5";
        }
        try {
            java.security.MessageDigest digest =
                    java.security.MessageDigest.getInstance(algorithm);
            InputStream input = new BufferedInputStream(new FileInputStream(file), 65536);
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
            if (!actual.equals(expected)) return algorithm + " 不符";
            CNLog.i(TAG, algorithm + " 与长度校验通过: " + file.getName());
            return null;
        } catch (Throwable error) {
            return algorithm + " 计算失败: " + error;
        }
    }

    private static String hex(byte[] value) {
        char[] digits = "0123456789abcdef".toCharArray();
        char[] output = new char[value.length * 2];
        for (int i = 0; i < value.length; i++) {
            int b = value[i] & 0xff;
            output[i * 2] = digits[b >>> 4];
            output[i * 2 + 1] = digits[b & 0x0f];
        }
        return new String(output);
    }
'''
        text = text[:verify_start] + verify_impl + text[verify_end:]

        text = replace_once(
            text,
            '''                    } catch (Throwable th) {
                        CNLog.e(TAG, "热更检查异常终止: " + th, th);
                        try { CNCNDownloadUI.hide(); } catch (Throwable ignore) {}
                    }
                }''',
            '''                    } catch (Throwable th) {
                        CNLog.e(TAG, "热更检查异常终止: " + th, th);
                        try { CNCNDownloadUI.hide(); } catch (Throwable ignore) {}
                    } finally {
                        running = false;
                    }
                }''',
            "worker running cleanup",
        )
        text = replace_once(
            text,
            '''        } catch (Throwable t) {
            try { android.util.Log.e(TAG, "热更检查线程起不来", t); } catch (Throwable ignore) {}
        }''',
            '''        } catch (Throwable t) {
            running = false;
            try { android.util.Log.e(TAG, "热更检查线程起不来", t); } catch (Throwable ignore) {}
        }''',
            "thread startup cleanup",
        )

        old_finish = '''        // running 要在浮层收掉之前清掉：之后再点胶囊（浮层还在的最后一刻）
        // 应当走「自己重启」那条路，而不是挂在一个马上就结束的检查上。
        running = false;
        CNCNDownloadUI.hide();

        // 检查本身不重启——热更是启动早期跑的，引擎此时还没读到台词/脚本，
        // 原地替换即可生效，原实现也是这么做的。唯一的例外是玩家在检查进行中
        // 点了教程胶囊：那次重启不能打断下载/解压，于是接力到这里来做。
        String msg = pendingRestartMsg;
        pendingRestartMsg = null;
        if (msg != null) {
            CNLog.i(TAG, "检查已收工，执行教程胶囊请求的重启");
            CNDownloaderFix.noticeAndRestart(msg);
        }'''
        new_finish = '''        running = false;
        CNCNDownloadUI.hide();
        String requested = pendingRestartMsg;
        pendingRestartMsg = null;
        if (applied) {
            if (requested != null) {
                CNLog.i(TAG, "教程胶囊重启请求已并入热更新重启");
            }
            CNDownloaderFix.noticeAndRestart("热更新已应用，3 秒后自动重启游戏");
            return;
        }
        if (requested != null) {
            CNLog.i(TAG, "检查已收工，执行教程胶囊请求的重启");
            CNDownloaderFix.noticeAndRestart(requested);
        }'''
        text = replace_once(text, old_finish, new_finish, "restart after update")
        text = text.replace(
            'CNCNDownloadUI.updateSimple("更新完成", "热更新已应用，即将进入游戏", 0);',
            'CNCNDownloadUI.updateSimple("更新完成", "热更新已应用，即将重启游戏", 0);',
            1,
        )

        required_final = (
            'o.optString("sha256", "")',
            'algorithm = "SHA-256"',
            'algorithm = "MD5"',
            '热更新已应用，3 秒后自动重启游戏',
            'java.util.LinkedHashMap<Integer, java.util.concurrent.Future<Boolean>>',
        )
        for marker in required_final:
            if marker not in text:
                raise PrepareError(f"hardened marker missing: {marker}")
        target.write_text(text, "utf-8")
        report = {
            "schema": 1,
            "target": str(target),
            "latestMainParallelVersionQuery": True,
            "latestMainParallelPackageDownload": True,
            "sequentialDiskApply": True,
            "sha256Preferred": True,
            "md5Compatibility": True,
            "restartAfterApply": True,
            "runningStateCleanup": True,
        }
        rendered = json.dumps(report, ensure_ascii=False, indent=2) + "\n"
        if args.report:
            args.report.parent.mkdir(parents=True, exist_ok=True)
            args.report.write_text(rendered, "utf-8")
        print(rendered, end="")
        return 0
    except (OSError, PrepareError) as error:
        print(f"ERROR: {error}", file=sys.stderr)
        return 1


if __name__ == "__main__":
    raise SystemExit(main())
