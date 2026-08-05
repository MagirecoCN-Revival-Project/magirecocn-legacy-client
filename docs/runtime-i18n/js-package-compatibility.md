# 前端热更新包与 HTML 覆盖机制验收

## 结论

当前老客户端的前端热更新机制是“下载 ZIP → 安全解压到应用 `files/` 根目录”。
它不按扩展名筛选，也不只允许 `js/libs`：只要 ZIP 条目位于正确的相对路径，
`magica/js/**`、`magica/template/**`、`magica/css/**` 和数据 JSON 都会被原样覆盖。
因此 HTML 替换机制已经在客户端侧启用；不需要再新增一个单独的 HTML 开关。

生产分发应继续使用**完整覆盖包**。去重 JSON 应作为人工维护与构建输入，不能把
只有 `uiTextList.json` 的源包直接发布成 `cn_js_update.zip`。

## 客户端执行链

1. `CNHotUpdateCheck` 比较 `version_js.json` 与本地 `js_version`。
2. 新版本由 `CNHotUpdate.download` 下载为临时 ZIP。
3. `CNDownloaderFix.extractChecked` 将所有安全条目解压到
   `/data/data/io.kamihama.totentanz/files/`。
4. 游戏前端继续从该目录读取 `magica/js/**` 与 `magica/template/**`，同路径文件
   覆盖旧版本。

`extractChecked` 只做 ZIP Slip、目录创建和条目尺寸校验，没有 JS/HTML 白名单。
这也是完整覆盖包中的 HTML 模板能够生效的直接原因。

## 两套候选包实测

### `cn_js_update_v5_full_cn_compat.zip`

- SHA-256：`ba37a06e18fd30bd3374dbb0da6f483d3a047ced69e8a7bb78812ac1327b8e03`
- 文件：401
- JavaScript：197
- HTML：181
- JSON：23
- 可直接覆盖的 JS/HTML/CSS：378
- `magica/js/libs/jquery-3.7.1.min.js`：存在
- 197 个 JavaScript：全部通过 `node --check`
- 分类：`deployable-overlay`

该包路径与客户端的覆盖根完全一致，能够同时替换业务 JavaScript、HTML 模板与
23 个数据字典，适合作为现阶段生产 `cn_js_update.zip`。

HTML 中存在生成二级模板的 EJS/Underscore 嵌套表达式，不能简单以 `<%` 与 `%>`
数量相等或单一静态 DOM 树作为失败条件；验收工具只把 UTF-8 可读性作为通用硬性
条件，保留现有专项 EJS/HTML QA 负责更深检查。

### `cn_js_update_vNext_json_source.zip`

- ZIP SHA-256：`075bb319a2169741242eb9b8a1a1bd5ddec68ded7252f93399560911c76be30a`
- 文件：2（`README.txt`、`magica/js/libs/uiTextList.json`）
- 唯一简体中文：343
- 唯一日文来源：346
- 引用位置：402
- 待人工复核：0
- 分类：`authoring-source-only`

说明中给出的 `7c8cd008655d48a8155ee206a2ad23c9279b3cc4a0f3e6052715f904fda2814d`
与本次 ZIP 不一致。它可能是独立 JSON 的哈希或另一版产物；发布前必须明确哈希所
对应的具体文件，不能把两者混写。

客户端和现有 `Build_JS_Injector.py` 都不会读取
`magireco-cn-ui-text-source/v2`：后者只枚举现有游戏数据 JSON，生成
`cardList`、`itemList` 等固定字典，并把结果追加到 jQuery。它没有把
`uiTextList.json` 的 402 个引用物化回业务 JS/HTML 的步骤。

所以该 ZIP 直接替换线上 `cn_js_update.zip` 后，实际结果是：

- 业务 JS/HTML 不会被覆盖；
- 去重字典不会被前端自动加载；
- 现有页面文本不会因此改变；
- 首次安装和热更新虽然会成功解压，但汉化会发生功能性回退。

## 推荐结构

采用“两层产物”而不是二选一：

- **维护层**：vNext 去重 JSON，作为唯一人工校验源；
- **分发层**：由确定性构建脚本读取维护层和干净前端基线，按引用生成完整的
  `magica/js/**`、`magica/template/**` 覆盖树，再与 23 个数据 JSON 和生成后的
  jQuery 字典合并为 `cn_js_update.zip`。

在物化脚本完成并通过逐引用校验之前，线上继续采用 v5 完整覆盖包。

## 仓库验收工具

```bash
python3 tools/validate-js-update-package.py cn_js_update.zip --require-deployable
```

工具检查：

- ZIP Slip、绝对路径、反斜杠、重复路径和大小写冲突；
- 所有分发文件是否位于 `magica/`；
- JSON 与 UTF-8；
- JavaScript 语法（系统存在 Node.js 时）；
- vNext schema、稳定 ID、简中值、日文来源的唯一性；
- 自动分类为 `deployable-overlay`、`authoring-source-only` 或失败。

CI/发布脚本应对最终 `cn_js_update.zip` 使用 `--require-deployable`，从机制上阻止
把仅数据源包误发到线上。
