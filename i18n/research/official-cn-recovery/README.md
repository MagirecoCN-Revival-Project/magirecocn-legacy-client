# 旧国服权威中文回收证据

> 本目录是**研究证据与待审阅清单**，不是运行时译表。
> `madomagi/engine_i18n.tsv` 的唯一权威源仍在
> `HiiragiNemu/magireco-cn-patch`；不要把本目录整体打进 APK 或热更包。

## 结论

旧国服资料可以优先供应战斗引擎中文，而且能避开“看到日文就重新翻一遍”的做法。
本轮采用两条互相独立、都能追溯到具体文件哈希的证据链：

1. **双 ABI 原生库完整字符串核验**：现行引擎表 297 条中，有 73 条中文以完整
   NUL 结尾 UTF-8 C 字符串同时存在于旧国服 arm64-v8a 与 armeabi-v7a
   `libmadomagi_native.so`，标为 `exact_authoritative`。
2. **角色号＋消息号精确对齐**：日文接口对象里的 `(charaNo, messageId)` 对齐旧国服
   `vo_char_<charaNo>_00_<messageId>_hca` 字典。在 1,705 条唯一日文记录中得到
   759 条 `key_aligned`，没有同一日文多译冲突，也没有与现行 297 条引擎表重叠。
   其中消息号 43–46 的战斗结束台词是 276 条，其余 483 条属于角色资料／大厅候选，
   还要先确认它们是否也经过 native hook。

已知漏译已由第二条链路确定：

```text
charaNo=1041, messageId=44
vo_char_1041_00_44_hca
カーテンコールで終いやな
→ 在欢声中谢幕吧
```

另外 946 条日文记录在旧国服字典里没有同键中文，通常是国服停服后新增的角色或
内容；它们保持 `fuzzy_review`，不能冒充国服权威译文。

## 重要边界

- `origin_apktool_decoded` 能提供 APK 内原生 UI 的权威中文，但角色战斗台词是服务端
  动态数据，不会完整塞在 APK 内。因此 759 条键对齐还需要同一份国服研究留档中的
  `json_web/lobby_vo_char_text.json` 和日文接口留档。
- 276 条战斗结束候选中有 56 条日文带 `@` 分行标记。当前证据尚未证明进入 native label hook 时
  它保持 `@`、变成换行，还是已经被移除。这 56 条在报告中
  `requires_runtime_capture=true`，要先用 `logI18nMiss` 抓实际字符串；其余 220 条
  战斗结束台词没有这个歧义。483 条角色资料／大厅候选也保持
  `requires_runtime_capture=true`，原因是渲染链路尚未确认，而不是译文键不可靠。
- “中文文字在旧国服库里出现”证明该措辞来自官方客户端，但短词可能在多个界面复用。
  因此 73 条是权威**文字证据**；要改当前译文时仍应结合函数/画面上下文。另有 6 条
  只命中长字符串后缀，已被完整 C 字符串边界检查排除，没有计入 73 条。
- `binary_context_aligned` 只允许作为补充证据。简单按同名函数里的字符串顺序对齐会
  在编译器重排后把标题和正文互换，本轮已实测复现，所以工具没有自动放行这种结果。

## 产物

| 文件 | 用途 |
|---|---|
| `manifest.json` | 输入绝对路径、大小、SHA-256、策略和总数 |
| `engine-current-official-literal-audit.tsv/.json` | 现行 297 条引擎译文的旧国服双 ABI 核验 |
| `voice-official-cn-evidence.tsv/.json` | 全部 1,705 条结构化对齐证据，含未命中项 |
| `voice-engine-additions-review.tsv` | 759 条唯一键对齐候选；多列审阅表，不是运行时两列表 |

所有生成文件已经用相同输入独立运行两次，文件集合和每个文件 SHA-256 均一致。

## 重建

在仓库根目录运行：

```powershell
python tools/recover-official-cn-i18n.py `
  --decoded-root 'D:\magia\MyProducts\MAGIA RECORD CN\origin_apktool_decoded' `
  --engine-table '<magireco-cn-patch>\madomagi\engine_i18n.tsv' `
  --jp-bundle '<国服研究留档>\stage2_private_raw_bundle.json' `
  --cn-voice-json 'D:\magia\MyProducts\MAGIA RECORD CN\json_web\lobby_vo_char_text.json' `
  --output-dir 'i18n\research\official-cn-recovery'

python tools/test-recover-official-cn-i18n.py
```

工具不会写入运行时表。采纳步骤应当是：

1. 从 `voice-engine-additions-review.tsv` 选择 `confidence=key_aligned`、
   `state=new`、`message_scope=battle_result` 且 `requires_runtime_capture=false` 的
   220 条首批行；
2. 在设备上开启 `logI18nMiss` 验证这些原文确实经过 native hook；
3. 把验证后的两列内容提交到补丁仓库的唯一 `madomagi/engine_i18n.tsv`；
4. 对 56 条 `@` 候选先抓取 hook 实际收到的字符串，再决定键中写 `@` 还是 `\n`；
5. 重新打 `cn_scenario_update.zip`，核对加载条数、坏行数与实际战斗画面。
