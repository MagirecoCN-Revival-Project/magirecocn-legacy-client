# `cn_js_update.zip` 运行时兼容性结论

## 结论

当前客户端的热更新方式是把压缩包逐条解压到应用 `files/` 根目录。因此，能够直接部署的包必须已经包含最终运行路径，例如：

- `magica/js/**`
- `magica/template/**`
- `magica/js/libs/*.json`

现阶段应部署 **保持 401 个原路径的运行时覆盖包**。去重版 `uiTextList.json` 应作为人工校验和构建源保留，不能直接替代线上 `cn_js_update.zip`。

推荐关系：

- 线上部署：`cn_js_update_v6_full_cn_runtime_i18n.zip`
- 回退兼容：`cn_js_update_v5_full_cn_compat.zip`
- 下一代人工维护源：`cn_js_update_vNext_json_source.zip` 内的 `uiTextList.json`

## 客户端为什么支持 JS 与 HTML 覆盖

`CNHotUpdateCheck` 下载前端脚本包后，调用 `CNDownloaderFix.extractChecked()`，目标目录是应用 `files/` 根目录。解压器不按扩展名筛选，而是保留 ZIP 内的相对路径逐项写入，因此 `magica/js/**` 与 `magica/template/**` 会一起覆盖。

这意味着 HTML 替换机制并非另一个需要启用的开关：只要压缩包中包含 `magica/template/**`，热更新解压成功后模板覆盖就已经生效。当前运行时包包含 181 个 HTML 模板，验收器已识别为 `html_overlay_present=true`。

## 三种包的实际结果

| 产物 | ZIP SHA-256 | 内容 | 客户端可直接使用 |
|---|---|---|---|
| v5 兼容包 | `ba37a06e18fd30bd3374dbb0da6f483d3a047ced69e8a7bb78812ac1327b8e03` | 401 项：197 JS、181 HTML、23 JSON | 是 |
| v6 运行时包 | `3dee4c687495441833f4862098f7d27a98c1c77294fd320f262718cbecdcce0e` | 与 v5 路径和顺序完全一致；补齐英文 UI 与漏网日文 | 是，优先采用 |
| vNext 源包 | `075bb319a2169741242eb9b8a1a1bd5ddec68ded7252f93399560911c76be30a` | 仅 `uiTextList.json` 与 README，共 2 项 | 否 |

vNext 说明中的 `7c8cd008655d48a8155ee206a2ad23c9279b3cc4a0f3e6052715f904fda2814d` 是压缩包内 **`uiTextList.json` 文件本身**的 SHA-256，不是 ZIP 的 SHA-256；两者并不矛盾。

## vNext 的验证结果

`uiTextList.json` 已通过以下一致性检查：

- 343 个唯一简体中文条目；
- 346 个唯一日文来源；
- 402 个实际引用；
- 中文值、日文原文、`TXT_` ID、`SRC_` ID 均无重复；
- 所有稳定 ID 均与 `SHA1(文本)` 规则一致；
- 402 个引用指向的文件都存在于运行时覆盖包中；
- `_meta` 中的计数与重新计算结果一致。

它适合作为下一代单一事实来源，但客户端没有读取 `uiTextList.json` 并自行改写业务 JS/HTML 的运行时代码。外部构建器必须先读取引用位置，把译文回填到业务文件，再产出完整运行时覆盖 ZIP。

## 与 `Build_JS_Injector.py` 的关系

`Build_JS_Injector.py` 的职责是：

1. 从纯净 jQuery 重建目标文件；
2. 读取 23 个游戏数据 JSON；
3. 把角色、道具、技能、章节等数据字典注入 `jquery-3.7.1.min.js`；
4. 在 `JSON.parse` / XHR 响应路径中替换结构化数据字段。

它并不会读取 vNext 的业务界面引用并生成 197 个 JS 与 181 个 HTML。两套机制应分开：

- 23 个游戏数据 JSON继续由 `Build_JS_Injector.py` 生成 jQuery 内嵌字典；
- 业务 JS/HTML 文案由 vNext 源和外部构建器生成运行时覆盖树；
- 最终将两者装入同一个 401 路径兼容包。

## 验收命令

```bash
python tools/verify-cn-js-update-package.py \
  --runtime cn_js_update_v5_full_cn_compat.zip \
  --runtime cn_js_update_v6_full_cn_runtime_i18n.zip \
  --source cn_js_update_vNext_json_source.zip \
  --report cn_js_update_package_compatibility_QA.json
```

本次实测结果：

- 两个运行时包均识别为 `runtime-overlay`；
- 两个运行时包的 197 个 JavaScript 均通过 `node --check`；
- 23 个数据字典全部存在且 JSON 可解析；
- ZIP 无路径穿越、重复项或大小写冲突；
- vNext 识别为 `deduplicated-source-only`；
- 所有包验收结果均为 `valid=true`。

## 发布规则

不得把只有 `uiTextList.json` 的 vNext 源包改名为 `cn_js_update.zip` 发布。这样解压虽会成功，但游戏没有可执行的业务 JS/HTML 覆盖，等同于未应用界面汉化。

正式发布包应满足：

1. 保持原 `magica/**` 路径；
2. 包含完整 JS/HTML 覆盖，而不是增量引用表；
3. 保留 23 个数据 JSON 与重新生成的 jQuery 注入器；
4. 全量通过语法、JSON、路径与引用检查；
5. 更新服务端 `version_js.json` 后再下发。
