# Legacy Client Runtime I18n 交付报告

## 约束执行结果

- 唯一工作分支：`feature/legacy-client-runtime-i18n`
- 基线分支：`main`，只读
- 未向 `main` 提交、推送、合并或部署
- 未创建 Release
- 未修改线上 `version_js.json`、客户端版本配置或服务器文件
- 新增构建与验证工作流均只接受 `workflow_dispatch`

## 1. 引擎字体与硬编码文本

保留并锁定已经真机验证成功的方案：运行时 hook 引擎读取的字体路径，将
`fonts/MTF4a5kp.ttf` 定向到 `fonts/TTZhiHeiGB3-W4.ttf`，不替换 APK 内字体文件。

实际编译链现在直接使用：

```text
magia-native/src/MagiaLegacy.cpp
```

并保留以下 hook：

- `Label::createWithTTF(const TTFConfig&, ...)`
- `Label::createWithTTF(const std::string&, const std::string&, ...)`
- `Label::setTTFConfigInternal(...)`

审查期间发现一组并行改动会在 CMake 构建时生成替代源，并主动删除上述成功
hook。该路线已撤销；冲突生成器和未采用 include 已删除，验证工作流增加了字体
hook 存在性守卫。

原有文本层翻译 hook 继续存在：`Label::setString`、`LabelAtlas::setString`、
`MenuItemLabel::setString`、`LoadingSceneLayerInfo::setText` 和
`LbUtility::initLabel`。

## 2. 游戏前端英文 UI 与漏网日文

新增：

```text
tools/apply-cn-js-english-i18n.py
```

该工具按完整 JavaScript 字符串、HTML 可见文本节点和经过人工审查的文件级片段
替换，不做任意子串替换，不修改标识符、CSS 类名、模板表达式或 23 个游戏数据
字典的 jQuery 注入逻辑。

本次生成的运行时包：

```text
cn_js_update_v6_full_cn_runtime_i18n.zip
SHA-256: 3dee4c687495441833f4862098f7d27a98c1c77294fd320f262718cbecdcce0e
```

验收结果：

- ZIP 文件：401
- JavaScript：197
- HTML：181
- JSON：23
- 修改文件：130
- 审查替换：689
- 197 个 JavaScript 全部通过 `node --check`
- 23 个 JSON 全部解析成功
- 181 个 HTML/EJS 的边界结构与 v5 基线一致
- 路径和条目顺序与 v5 完全相同
- 二次独立构建逐字节一致
- 已登记的目标英文／日文残留：0

“残留为 0”仅指本次 QA 中明确登记的运行时 UI 项，不代表把品牌名、技术缩写、
调试标识或所有拉丁字符机械清零。

## 3. CN 外部资源下载 UI 文本导出

提供两种用途不同的文本清单：

```text
i18n/cn-download-ui-texts.tsv
```

面向人工查看，使用稳定 ID 和 `{placeholder}` 表达动态字段，覆盖下载浮层、版本
检查、热更新、序章询问、日志面板、重试／错误、署名与 15 个文件槽位。

```text
i18n/cn-downloader-ui-text.tsv
```

面向后续程序化修改，记录源码路径、精确出现次数、原始 Java 字符串、目标简体
中文、界面类型和备注。配套工具：

```text
tools/prepare-cn-downloader-ui-text.py
```

该工具只处理完整 Java 字符串字面量；源码或表格发生漂移时失败，不会模糊替换。
当前 APK 构建仍直接编译仓库 Java 源码；该表和物化器用于下一次集中修改与人工
校验，不在本轮静默改变下载器行为。

## 4. JS／HTML 热更新机制

客户端流程是：

```text
下载 cn_js_update.zip
→ 安全解压到应用 files/ 根目录
→ 按 magica/** 原路径覆盖
```

解压器不按扩展名过滤。因此完整包中的 `magica/template/**` 会与
`magica/js/**` 一起生效，HTML 替换机制已经启动，无需新增开关。

推荐分层：

- 当前分发包：v6 完整运行时覆盖包
- 回退包：v5 完整兼容包
- 下一代维护源：vNext 去重 `uiTextList.json`

vNext 源包只有 2 个文件，不能直接作为客户端 `cn_js_update.zip` 使用。验证结果：

- 343 个唯一简体中文条目
- 346 个唯一日文来源
- 402 个引用位置
- 49 个被引用业务文件
- 184 个 HTML 引用、218 个 JS 引用
- 稳定 `TXT_` / `SRC_` ID 全部匹配
- 引用缺失：0

vNext ZIP 的 SHA-256 是：

```text
075bb319a2169741242eb9b8a1a1bd5ddec68ded7252f93399560911c76be30a
```

说明中的：

```text
7c8cd008655d48a8155ee206a2ad23c9279b3cc4a0f3e6052715f904fda2814d
```

对应 ZIP 内单独的 `uiTextList.json`，不是 ZIP 本身。

`Build_JS_Injector.py` 继续只负责把 23 个游戏数据 JSON 注入 jQuery；它不会把
vNext 的 402 个 UI 引用物化回业务 JS/HTML。仓库已保留严格失败的参考物化器：

```text
tools/materialize-ui-text-overlay.py
```

它要求干净前端基线，并逐引用核对原文和位置后才生成覆盖树；尚未替代本次已经
验收的 v6 兼容构建流程。

## 5. 构建、签名与 Actions

APK 工作流仍执行：

```text
Java 源码 → javac → d8 → baksmali
C++ 源码 → NDK 双 ABI
apktool b → zipalign → apksigner
```

签名指纹强制校验为 AOSP testkey：

```text
a40da80a59d170caa950cf15c18c454d47a39b26989d8b640ecd745ba71bf5dc
```

新增的前端包工作流：

```text
.github/workflows/build-runtime-i18n.yml
```

它会手动下载基础包、可选校验 SHA-256、两次独立生成并比较、执行完整兼容性
验收，然后只上传 workflow artifact。

由于当前 GitHub 连接器没有 `workflow_dispatch` 调用能力，本轮没有伪造“CI 已
成功”的结论。最新提交没有自动状态检查或 PR 工作流运行。APK 双 ABI 编译、签名
和最终 artifact 仍需在 GitHub Actions 页面手动选择本分支运行。

## 6. 当前未执行的动作

- 未把 v6 覆盖为线上正式 `cn_js_update.zip`
- 未提高 `version_js.json`
- 未生成或上传 Release
- 未把工作分支合并到 `main`
- 未宣称 APK 已通过本轮 Actions 或新一轮真机验收
