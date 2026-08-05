# 既有 APK 图像研究只读审计

- 审计对象：`D:\magia\MyProducts\MagiaRe\Magia_CN_Project\research\apk-image-audit-2026-08-02`
- 对应 APK：`D:\magia\MyProducts\MagiaRe\Magia_CN_Project\dist\MagiaCN-r128-downloader-fix1.apk`
- 审计方式：只读遍历、CSV/JSON 解析、逐文件 SHA-256/尺寸/模式/解码核对、APK ZIP/plist 独立枚举、联系表索引核对、脚本静态语法检查和少量可视抽查。
- 源研究目录未被修改；临时核对程序与输出仅写入当前共享工作区 `work\`。

## 1. 总结

既有研究中的**最新版 APK 提取结果本身可信且可复用**：347 个 APK 直接位图、77 个 plist 内嵌位图实例、1,008 个逻辑图集帧均存在、可解码，清单路径、SHA-256 和尺寸与磁盘文件一致。APK 现文件大小、SHA-256 和 ZIP 完整性也都通过核对。

但它仍只是首轮提取与 OCR 候选阶段，离完整任务有明显距离：

1. 没有任何 pHash；没有统一的物理载荷/逻辑帧主清单 JSON。
2. OCR 只扫描 347 个直接位图，未扫描 77 个内嵌位图和 1,008 个逻辑帧。
3. 没有人工可复核的四类最终标签（纯英文、英日混合、品牌/符号/无需翻译、误报），目前只有自动脚本分类。
4. 旧国服根目录发现、压缩包/plist/图集处理完全未做。
5. 没有新旧资源同图/可替换匹配、SSIM/特征比对、替换清单、不可替换原因或双端对照图。
6. 没有 Markdown 总报告、发布资产、Release、克隆复现和带命令/输入/输出/退出状态的验证记录。

因此应当**复用现有 1,432 个导出图像文件及对应三份 CSV，不要把 347 误当成全部物理载荷，也不要把 1,008 个逻辑帧加到物理载荷数中**。

## 2. 独立核对后的计数

### 2.1 APK 和 plist 覆盖

| 项目 | 核对结果 |
|---|---:|
| APK 大小 | 99,199,438 bytes |
| APK SHA-256 | `BD3AAE6D80F87044A9C6780AC0226746AA53D568CF81A6203C7F08D60C7B67F5` |
| ZIP `testzip()` | 通过，无坏条目 |
| APK 非目录条目 | 839 |
| `.plist` 条目 | 145 |
| plistlib 可解析且为 dict | 145/145 |
| 含 `textureImageData` 的 plist | 77；base64+gzip 均可解 |
| 含 `frames` 的图集 plist | 68；合计 1,008 帧 |
| 两类 plist 重叠 | 0 |
| 图集 descriptor 格式 | format 0：47 个；format 2：19 个；format 3：2 个 |
| 按帧计的格式分布 | format 0：282；format 2：713；format 3：13 |

这与 `plist-images-summary.json` 的 77/68/1,008 完全吻合。145 个 plist 在当前 APK 中恰好分成 77 个内嵌纹理 plist 和 68 个图集 plist，没有解析失败或未归类 plist。

### 2.2 物理载荷与逻辑帧必须分开计数

| 层级 | 实例/文件数 | 唯一 SHA-256 | 格式/说明 |
|---|---:|---:|---|
| APK 直接物理位图 | 347 | 334 | PNG 339、JPEG 8 |
| plist 内嵌物理位图实例 | 77 | 36 | PNG 47、TIFF 30 |
| **物理位图载荷合计** | **424** | **370** | PNG 386、JPEG 8、TIFF 30；直接与内嵌之间无相同 SHA |
| 派生逻辑图集帧 | 1,008 | 967 | 由 68 个 descriptor、67 个直接纹理路径生成 |
| 导出的载荷及逻辑帧文件合计 | 1,432 | 不宜混合解释 | 424 物理 + 1,008 逻辑 |

补充：

- 347 个直接位图有 5 个重复 SHA 组，重复实例超出量 13，最大同 SHA 实例数 6。
- 77 个内嵌实例有 12 个重复 SHA 组，重复实例超出量 41，最大同 SHA 实例数 12；53 个实例属于重复组。
- 1,008 个逻辑帧有 18 个重复 SHA 组，重复实例超出量 41，最大同 SHA 帧数 11。
- 图集使用 67 个 APK 直接纹理路径、63 个唯一纹理 SHA；这些路径和 SHA 全部能在 `all-images.csv` 中找到。
- 图集帧中 31 个为 trimmed frame，0 个 rotated frame。3 帧使用 `-1` 边缘坐标，现脚本按透明越界裁切成功恢复：
  - `assets/package/top/toppage_bg_021.plist :: ef_start01.png`，`x=-1,y=517`
  - `assets/package/top/toppage_bg_021.plist :: hane_01.png`，`x=856,y=-1`
  - `assets/package/top/toppage_bg_021.plist :: logo_01.png`，`x=2,y=-1`
- 77 个内嵌逻辑文件名都以 `.png` 结尾，但其中 30 个实际 magic 是 TIFF；脚本正确另存为 `.tiff`，清单记录了 `extension_magic_match=False`。

## 3. 清单与实际文件一致性

本次不是抽样哈希，而是对三组全部导出文件做了逐文件核对。

| 清单 | CSV 行数 | 磁盘文件 | 路径缺失 | 孤儿文件 | SHA 不符 | 尺寸不符 | 模式不符 | 解码失败 |
|---|---:|---:|---:|---:|---:|---:|---:|---:|
| `all-images.csv` / `all-images/` | 347 | 347 | 0 | 0 | 0 | 0 | 0 | 0 |
| `embedded-images.csv` / `embedded-images/` | 77 | 77 | 0 | 0 | 0 | 0 | 0 | 0 |
| `atlas-frames.csv` / `atlas-frames/` | 1,008 | 1,008 | 0 | 0 | 0 | 0 | 不适用 | 0 |

所有清单的 `index` 也都唯一，输出路径均唯一。`ocr-all.csv` 与 `all-images.csv` 的 347 个 index、APK 路径和导出路径一一对应，无错位。

### 3.1 现有字段

`all-images.csv`（16 字段）：

`index, apk_path, extension, magic_format, extension_match, compressed_bytes, uncompressed_bytes, sha256, width, height, mode, frames, has_alpha, decode_ok, decode_error, extracted_path`

`embedded-images.csv`（19 字段）：

`index, source_plist, logical_texture_name, texture_path, wrapper, format, logical_extension, extension_magic_match, width, height, mode, sha256, duplicate_direct_count, duplicate_direct_entries, duplicate_embedded_count, duplicate_embedded_sources, output_path, success, error`

`atlas-frames.csv`（23 字段）：

`index, source_plist, plist_format, texture_path, texture_sha256, frame_name, frame_x, frame_y, packed_width, packed_height, trimmed_width, trimmed_height, source_width, source_height, offset_x, offset_y, paste_x, paste_y, rotated, sha256, output_path, success, error`

`ocr-all.csv` 与 `english-candidates-unreviewed.csv`（13 字段）：

`index, apk_path, width, height, classification, latin_line_count, foreign_line_count, ocr_line_count, best_score, latin_text, foreign_text, all_text, extracted_path`

### 3.2 字段缺口

- 三层清单都没有 pHash，也没有像素归一化哈希。
- 没有统一 `source_kind`（direct/embedded/atlas_frame）、统一稳定 ID、仓库相对路径和绝对路径字段。
- 逻辑帧清单没有显式输出格式、mode、alpha、文件字节数；当前输出实际均为 PNG/RGBA，但只能从文件或脚本推断。
- 直接与内嵌清单是物理层，图集清单是逻辑层，现阶段没有一个明确合并但保持层级边界的主清单。
- 除摘要 JSON 外没有完整 CSV 对应 JSON 数据清单。
- 没有旧国服端字段、匹配方法/分数、匹配等级、人工决策、复核人或复核证据字段。

## 4. OCR 和候选分类现状

### 4.1 自动分类覆盖

OCR 仅处理 `all-images.csv` 的 347 个 APK 直接位图：

| 自动分类 | 数量 |
|---|---:|
| `no_text_detected` | 248 |
| `non_english_text` | 14 |
| `nonletter_or_uncertain` | 24 |
| `english_candidate` | 52 |
| `mixed_language` | 9 |
| 合计 | 347 |

- `ocr-raw.json` 有 531 个背景变体记录，覆盖 347 个 index：347 个白底，184 个实际带 A 通道图像另有黑底记录；没有漏 index。
- `english-candidates-unreviewed.csv` 只含 52 个 `english_candidate`，**没有包含 9 个 `mixed_language`**。
- 候选联系表包含两类合计 61 个，因此候选 CSV 与候选联系表的口径不同。
- 61 个候选全部来自 `assets/`；`res/` 211 张中没有 OCR 英文候选。
- 61 个候选中至少 14 个 OCR 文本含 `No Data`/`No Image` 一类占位语。

### 4.2 尚无人工最终分类

文件名明确写着 `unreviewed`，CSV 中也没有 manual label、decision、reviewer、reason、needs_translation 等字段。现有脚本只按 OCR 字符种类自动分为英文候选、混合语言、非英文等，尚未区分任务要求的：

1. 纯英文且需中文替换；
2. 英日混合且需中文替换；
3. 符号/品牌/无需翻译；
4. OCR 误报。

### 4.3 可视抽查证明 OCR 候选既有误报也有漏报

- `#212 assets/fonts/witchText-export.png` 被列为 `english_candidate`，OCR 文本为 `10 | S | 00 | 68! | C | A2O | 3`；可视内容是魔女文字/符号字形表，属于明显需人工排除的误报样例。
- `#271 assets/package/startup/logo02.png` 实际清晰显示 `f4samurai` 品牌标识，却被标为 `no_text_detected`；它应进入“品牌/无需翻译”复核桶，而不是从文本候选中消失。
- 以下 4 张实际清晰显示白色 `No Data`，却均为 `no_text_detected`：
  - `#334 assets/resource/image_native/mini/anime_v2/mini_xxxxxx_d_l0.png`
  - `#335 assets/resource/image_native/mini/anime_v2/mini_xxxxxx_d_r0.png`
  - `#338 assets/resource/image_native/mini/anime_v2/mini_xxxxxx_m_l0.png`
  - `#339 assets/resource/image_native/mini/anime_v2/mini_xxxxxx_m_r0.png`

这几项足以说明 52+9 只能作为候选起点，不能视为“全部英文 UI 图像”。而且 77 个内嵌图和 1,008 个逻辑帧尚未跑 OCR/联系表，逻辑帧层的英文清单目前为零。

## 5. 联系表核对

现有 `contact-sheets/` 共 35 张 JPEG 联系表和 3 个 `index.json`：

| 分组 | 图像数 | 页数 | index 覆盖 | 页文件一致性 |
|---|---:|---:|---|---|
| `res` | 211 | 14 | 完整 | 无缺页/孤儿页 |
| `candidates` | 61 | 16 | 完整 | 无缺页/孤儿页 |
| `missed-assets` | 75 | 5 | 完整 | 无缺页/孤儿页 |

说明：

- `assets` 总数为 136；`candidates` 61 + `missed-assets` 75 正好覆盖全部 `assets`。
- 脚本支持独立 `assets` 分组，但该分组目录和索引未生成。
- 没有 `embedded-images` 或 `atlas-frames` 联系表。
- 候选表使用 2×2 大格，其他表使用 4×4；索引中的 image index 与 OCR 清单完全对应。

## 6. 脚本审计

4 个脚本均通过 Python AST 语法解析。

### `extract_apk_images.py`

优点：

- 按扩展名和文件头双重发现 APK ZIP 顶层条目；当前 347 个扩展名候选和 347 个 magic 候选完全相同。
- 路径穿越检查、SHA-256、压缩/未压缩字节数、Pillow 尺寸/模式/alpha/解码结果齐全。
- 当前 APK 347/347 可解码，无扩展名/magic 不一致。

缺口：

- 只处理单个 APK/ZIP，不做目录全量发现、嵌套压缩包或 plist 内嵌/图集。
- 不生成 pHash/归一化像素哈希。
- 输出目录不先清理；若复用同一路径处理变化后的 APK，旧文件可能成为孤儿。
- 没有记录精确运行命令、运行环境和退出状态。

### `extract_plist_images.py`

优点：

- 当前 APK 的 145 个 plist 全覆盖；处理顶层 `textureImageData` 的 base64+gzip 内嵌位图，以及 TexturePacker/Cocos formats 0/2/3。
- 保留来源 plist、纹理路径、帧坐标/trim/offset/paste、纹理 SHA 和输出 SHA。
- 现有 77 个内嵌载荷与 1,008 帧全部成功，路径无碰撞，负一边缘坐标也正确处理。

缺口：

- 只扫描单个 APK 中扩展名为 `.plist` 的顶层 ZIP 条目；不是旧国服资料根的递归发现器，也不递归嵌套压缩包。
- 只识别当前使用的顶层 `textureImageData` 结构；对其他 plist 图像包装形式没有通用发现记录。
- 解析失败 plist 会静默跳过；当前 APK 恰好没有失败，但用于未知旧资源时应显式清单化失败项。
- 无 pHash/像素哈希；逻辑帧缺少显式格式/mode/alpha/字节数字段。
- 同样不清理旧输出，也没有可审计运行记录。

### `scan_english_ocr.py`

优点：

- 为透明 A 通道图像跑白底/黑底，保留原始 box、文本、置信度、背景和耗时。
- 347 个直接图像索引覆盖完整，汇总与原始 JSON 一致。

缺口：

- 输入写死为 `all-images.csv`，未处理 `embedded-images.csv` 和 `atlas-frames.csv`。
- 输出只是字符脚本启发式候选，不是任务所需的人工分类。
- `english-candidates-unreviewed.csv` 丢掉 `mixed_language` 行；候选口径不统一。
- 现有可视抽查已经证明误报和漏报。
- 没有 requirements/锁定模型资产或 Python/Pillow/numpy 版本；摘要只记录 `rapidocr_onnxruntime=1.4.4` 和阈值 0.45。

### `make_contact_sheets.py`

优点：

- 透明棋盘背景、路径/index/OCR 文本标签和 JSON 页索引适合人工复核。
- 现有 3 组索引与页面完整一致。

缺口：

- 输入也写死为 `ocr-all.csv`，不能直接覆盖内嵌载荷、逻辑帧、旧国服候选或双端匹配对照。
- `assets` 全量组虽有代码但未运行；无内嵌/图集分组。
- 字体路径优先硬编码 Windows 字体，跨平台复现仅依赖 Pillow 默认字体回退。

## 7. 按完整任务要求评估现状

| 完整要求 | 既有研究状态 | 仍缺内容 |
|---|---|---|
| 1. 最新 APK 全部物理图像和逻辑帧清单 | **提取已完成，清单基础可靠** | pHash、统一主清单 JSON、统一来源/绝对与仓库路径、逻辑帧格式/alpha/字节数 |
| 2. 英文 UI 全量、可复核四类分类 | **仅直接物理层 OCR 候选** | 内嵌与逻辑帧 OCR；人工分类；漏报补查；纯英文/英日混合/品牌无需翻译/误报最终标签 |
| 3. 旧国服资料根全量发现 | **未开始** | APK、解包目录、压缩包、plist 内嵌、图集帧的递归发现和清单 |
| 4. 两层候选与国服匹配 | **未开始** | 物理整图层、逻辑帧层；SHA/归一化/pHash/SSIM/特征/尺寸/alpha/atlas 证据；四档结果；双端路径和对照图 |
| 5. 替换/不可替换/候选目录/报告 | **只有首轮联系表** | 替换清单、原因、旧国服候选目录、双端联系表、CSV/JSON/Markdown 总报告、校验记录 |
| 6. Git 分支与 Release | **未开始** | 研究分支提交、推送、Release 大包、资产清单/SHA/链接 |
| 7. 克隆复现与 ZIP/哈希验证 | **当前源结果一致性已核对，但无发布复现** | clean clone 脚本运行、清单/文件计数断言、ZIP 打开与哈希、精确命令/输出/退出状态 |

## 8. 可直接承接的资产与建议优先级

可直接复用：

- `all-images/` + `all-images.csv`
- `embedded-images/` + `embedded-images.csv`
- `atlas-frames/` + `atlas-frames.csv`
- `ocr-raw.json` / `ocr-all.csv` 作为直接物理层候选证据
- 现有 35 张联系表及 3 份页索引
- 4 个脚本的路径安全、当前 APK plist 解析和图集恢复逻辑

承接时首要补齐：

1. 在不混淆层级的前提下为 424 个物理实例和 1,008 个逻辑帧补 pHash/像素归一化字段和统一 ID。
2. 对 77 个内嵌图、1,008 个逻辑帧以及直接层 OCR 非候选做复核候选生成；建立人工分类 CSV/JSON。
3. 写通用递归来源发现器扫描旧国服根及嵌套压缩包，再复用 plist/图集恢复核心。
4. 建立新旧双层匹配和双端可视对照；之后再生成替换决策、报告、压缩包和 Release。

## 9. 复现性与日志缺口

现有 `extract-output.txt` 和 `ocr-output.txt` 只保存摘要/进度，不保存完整命令、Python 路径、输入参数解析结果和退出状态；plist 提取与联系表也没有对应执行日志。根目录没有 README、requirements、锁文件、总报告或 verification record。因只读审计约束，本次没有在源目录重跑提取脚本；但 APK、CSV/JSON、全部输出文件之间的现状一致性已经独立核验。
