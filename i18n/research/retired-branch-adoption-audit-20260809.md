# 旧分支采纳决策审计（等待维护者裁决）

> 状态：**只读审计完成，旧实现尚未应用。**
>
> 本文是决策清单，不是合并计划。`main` 是唯一权威基线；没有任何旧提交适合整笔
> cherry-pick。每一项必须由维护者明确选择后，才会在最新 `main` 上手工实现或清理。

## 核对基线

| 对象 | 审计时 HEAD／位置 | 处置 |
|---|---|---|
| 权威 `origin/main` | `d63367852daf7a11877983a78d71ad40b42f7585` | 只读基线；之后的 `8203aab5` 只改网络证据与测试，不影响本审计结论 |
| 退役本地功能分支 | `e4afc3844da9b112030b8eba0093ca827f97b4e6` | 保留，等待裁决 |
| dirty checkpoint | `D:\magia\MyProducts\MagiaRe\.codex-handoff\low-quota-v2-legacy-client\checkpoints\20260808-201526119-retired-feature-dirty` | 保留哈希、补丁和未跟踪证据 |
| ADV 研究分支 | `7480d2e027a300fc55e8665740a2e5634877578a` | 等待 D5 裁决 |
| 图像研究分支 | `a698f8f26fefd19393ffe09b4da594ffb303c132` | 明确保留，不参加清理 |

## D1：native i18n 并发与 ABI 设计

**建议：采纳设计，但只在最新 `main` 上手工重写；不 cherry-pick。**

可取内容仅限：

- `RuntimeTextI18n.inc` / `prepare-native-text-i18n.py` 中的不可变
  `shared_ptr` 快照与 `atomic_load` / `atomic_store`；
- 前缀规则按最长键优先；
- 真实 C++ `std::string` ABI：Label／LabelAtlas／MenuItemLabel 是 const-ref，
  `LoadingSceneLayerInfo::setText` 是 by-value；
- 相关旧提交：`50de3b02`、`1930937f`、`6653de7b`、`a8703612`、`30753802`。

审计发现当前 `main` 仍值得修的风险：

1. reload 对全局 `unordered_map` / `vector` 做 swap，而渲染线程无锁读取，构成 data race；
2. 文本 hook 仍使用手工 `NdkStr` / `FakeNdk` arm64 布局；
3. `setText` 符号是 by-value，却按指针形状处理；
4. prefix 没有最长优先，并带 E3/E4 首字节预筛。

不能整段搬运的原因：旧生成器会替换当前 hook 块，丢掉 `setTitle`、Connecting 抑制、
调试开关和 miss log；旧 FontCompat 又回到 TTZhi／arm64 路线，与当前
`mbm_20160902` 字体重定向冲突。

若采纳，验收至少包括：

- `check-native-syntax`；
- 静态守卫禁止文本 hook 使用 `NdkStr` / `FakeNdk`；
- exact／空译文／ASCII prefix／最长前缀／并发 reload-read 测试；
- 双 ABI NDK 构建；
- arm64＋armv7 的 setText／setTitle／initLabel／反复 reload 冒烟；
- 字体哈希与 `mbm_20160902` 重定向不变。

## D2：退役 checkpoint 的引擎字符串研究

**建议：只采纳研究证据，不采纳运行时表或 Java 双数据源。**

只读统计：

- 当前原生库中 427 个唯一含假名串、842 次出现；
- checkpoint 运行时表 291 keys；当前权威表 297 keys；
- 共享 236：仅 111 译文相同，125 冲突；
- checkpoint-only 55：28 条 replace 候选＋27 条 debug；
- 权威-only 61。

冲突包含实质语义，例如“环彩羽／环伊吕波”“MP槽／MP蓄能条”，以及拼接式
`上昇する` 在权威表中必须为空，旧表却填“提升”。因此下列内容不进入 APK：

- `CNEngineI18n.java` / `CNEngineI18nData.java`；
- `assets/cnv/engine_i18n.tsv`；
- `i18n/engine-strings.tsv`。

可保留的只有 inventory／rich／review／summary／generator 研究证据（排除 pycache）。
28 条 replace 候选要逐项经过 `logI18nMiss` 和画面语境验证，再写入补丁仓库唯一
`madomagi/engine_i18n.tsv`。

## D3：外部下载器 UI 文本目录

**可选：若仍需要集中维护文本，就从当前 `main` 重新提取；旧规则不直接采纳。**

- 旧 `i18n/cn-downloader-ui-text.tsv` 共 130 rules；
- 对当前 `main`：88 条计数完全一致、34 条 count drift、8 条已经不存在；
- 合计 42／130 漂移。

旧清单可以做人工历史参考；exact-count 生成器不能直接运行。若选择重建，应从当前
Java 源重新提取、重新定数并补测试，不 cherry-pick
`5507612d`、`1a1cb79f`、`8b127412`、`8041ad32`、`4eca2060`。

## D4：退役分支其余内容

**建议：不采纳；维护者确认后清理工作树，保留 checkpoint。**

- 分叉规模：权威 main 118 commits、旧支 55 commits；最终 diff 32 files，
  `+5756/-550`；
- 整体应用会删除或回退当前 guard、配置、文档、classes 与测试；
- `CNHotUpdateTransaction` 已被更强的 `CNHotUpdateTx` 替代；
- `CNSafeExternalLinks` 已被更完整的 `CNSafeLink` 替代；
- 旧完整 WebViewImpl 与当前 `CNWebProxy.Delegating` 互斥；
- WebView 审计思路已进入 `check-webview-interceptor.py`；
- 旧 JS/vNext/v6 validator/materializer 属补丁仓库或历史机制；
- 旧 `build-apk-core.sh` 包含 checkout/clean，违反现行保留纪律；
- 旧 workflow 没有 `e4afc384` 的成功构建证据；
- Color4B 已由 main 的 `f5e733ea` 吸收。

## D5：ADV native evidence 分支

**建议：legacy client 不采纳；维护者二选一。**

该分支只有 4 个文件：2 个研究 workflow、1 个 smoke mjs、1 个 marker；没有 APK 或
运行时修改。PR #9 明示 temporary research-only / do not merge，且已经关闭。

- 方案 A：删除前下载一个最新 native artifact，并把 workflow／script 移到正确的
  ADV viewer 仓库，更新 URL／release gate 后再测；
- 方案 B：不保留，直接把该分支列入后续清理。

审计时可用的两个 native artifact：`8992659017`（39,548,968 bytes）与
`8992660052`（39,547,064 bytes），预计 2026-08-14 过期，二者留一个即可。
browser 最后一次 run `31175498780` 因部署 release 字符串变化而首步失败，没有
browser artifact。

## D6：图像研究分支

**按维护者既定要求保留，不参与采纳／清理。**

相对旧基线唯一提交 `a698f8f`，15 files、`+44,538`，全部位于
`research/apk-image-classification`。报告统计：APK direct 347、embedded 77、
physical 424（370 unique）、logical atlas 1008（967 unique）、total 1432。

## 请维护者回复的裁决格式

可以直接回复一行，例如：

```text
D1采纳手工重写；D2仅研究证据；D3重建；D4清理；D5选A；D6保留
```

收到裁决前：不应用旧实现、不删除旧工作树、不删除远端研究分支。收到裁决后：先保存
最终哈希与补丁，再只移除获准清理的非图像 worktree；不 force-push、不覆盖 `main`、
不碰图像研究树。
