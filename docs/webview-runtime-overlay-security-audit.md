# WebView JS／HTML 覆盖调用链与安全审计

## 结论

游戏对 `cn_js_update.zip` 中的 JavaScript 和 HTML 覆盖不是“推测可用”，而是存在明确的运行时代码调用链：

1. `CNHotUpdateCheck` 下载前端包并调用 `CNDownloaderFix.extractChecked()`，解压根为应用 `files/`；
2. 游戏的 `WebViewImpl.WebViewClientImpl.shouldInterceptRequest()` 拦截包含 `/magica/` 的非 API 请求；
3. 请求路径被映射到 `/data/data/io.kamihama.totentanz/files/magica/`；
4. 本地文件存在时直接返回 `WebResourceResponse`；
5. `.js` 使用 `application/javascript`，`.html` 使用 `text/html`，`.css` 和 `.json` 也有对应 MIME；
6. 文件不存在时回落到 WebView 原来的网络请求。

因此：

- `magica/js/**` 覆盖已开启；
- `magica/template/**` 覆盖已开启；
- HTML 不需要额外开关；
- 部分覆盖是合法设计，ZIP 中没有的文件会回落到原始前端；
- API 请求被排除，不会被静态文件覆盖误拦。

对 v6 包的静态交叉检查结果：

- 401 个文件；
- 197 个 JavaScript；
- 181 个 HTML；
- 23 个 JSON；
- 154 个打包 JavaScript 含 `text!template/...html` 加载；
- 共发现 211 次模板引用、192 个唯一模板目标；
- 其中 119 个打包 HTML 明确被打包 JavaScript 引用。

这证明 HTML 不只是被解压到目录，业务 JavaScript 也确实通过 RequireJS `text!template/` 路径请求这些模板。

## 英文 UI 的来源分类

此前提到的大多数“进入游戏后仍为英文”的界面属于 Web 前端层，可由完整 JS／HTML 覆盖包解决，包括：

- JavaScript 中的按钮、确认框、错误提示和动态拼接文本；
- HTML／EJS 模板中的静态标题、标签和按钮；
- 23 个数据 JSON 和 jQuery 注入器处理的角色、道具、技能、章节等结构化字段。

但不能把所有英文都归为 JS：

- cocos2d 原生弹窗与下载／网络错误：C++ 文本 hook；
- 原生中文使用日文字形：已验证成功的运行时字体路径 hook；
- CN 外部资源下载浮层：Java 补丁；
- 烘焙在 PNG／plist 图集里的英文：必须修改图片资源；
- 服务端直接返回且未经过当前 JSON 注入器的字段：需扩展数据映射或服务端数据处理。

## 已发现的高风险缺陷

### 1. 热更新后不重启，当前进程不保证立即使用新 JS／HTML

现有流程在覆盖文件后继续当前进程。此时可能已经存在：

- Chromium WebView HTTP 缓存；
- RequireJS 已加载模块缓存；
- 已编译的 HTML 模板缓存；
- 当前页面已经持有旧模块对象。

所以“文件已经写入”不等于“当前会话已经切换”。首次安装结束本来会重启，因此首次安装路径可靠；既有用户的热更新路径没有重启，存在继续运行旧代码或半新半旧代码的风险。

修复原则：前端脚本包成功提交后，使用现有的受保护重启链，在所有热更新包处理完毕后重启。仅清 WebView cache 不足以清 RequireJS 内存模块，重启更可靠。

### 2. 可执行热更新包没有密码学完整性校验

`version_js.json` 当前客户端只读取整数 `version`。下载完成后依赖 HTTP 长度、ZIP 可打开和条目大小判断，没有验证 SHA-256。

JS／HTML 在 WebView 中运行，并能接触 `androidCommand` 原生桥，因此它不是普通数据包。镜像误配置、缓存污染、上游文件错位或分发端被篡改都会变成客户端代码执行面。

修复原则：版本清单加入 SHA-256 和文件大小；下载后、解压前必须逐字节验证，摘要不符时失败关闭，不写版本号、不覆盖现有文件。

### 3. 热更新直接逐文件写入活动目录，不具备事务性

`extractChecked()` 对每个 ZIP 条目直接 `FileOutputStream` 写最终路径。进程被杀、磁盘写失败或后续条目异常时，前面文件已覆盖，后面文件仍旧，形成混合版本。

修复原则：先解压到独立 staging 目录，完成路径、数量、摘要和关键哨兵验证后再提交；提交失败时保留旧版本或回滚。提交后重启进程。

### 4. WebView 本地覆盖拦截没有来源和路径边界验证

当前拦截器使用 `URL.contains("/magica/")`，再把后面的字符串直接拼到应用私有目录。它没有：

- HTTPS／host 白名单；
- 精确 `/magica/` 路径前缀约束；
- canonical path 必须位于根目录内的验证；
- 请求方法检查。

至少会使任意已进入该 WebView 的来源有机会请求本地覆盖文件；在特定 URL 规范化行为下还需防止目录穿越。

修复原则：使用 `Uri` 解析；只允许项目确认过的 HTTPS 游戏域名；只接受 GET；规范化相对路径；候选文件 canonical path 必须等于根目录或以根目录加分隔符开头；拒绝目录和未知扩展名。

### 5. WebView 允许 mixed content，且 JS bridge 不做来源隔离

产品 WebView 调用了 `setMixedContentMode(0)`，即允许 HTTPS 页面加载 HTTP 内容；同时 `androidCommand` 注入到整个 WebView，不区分页面来源。

在确认所有游戏端点均已 HTTPS 后，应改为禁止 mixed content；同时限制 WebView 顶级导航和子资源来源。不能只依赖桥接参数以 `game` 开头。

### 6. Native 文本 hook 存在双 ABI 和并发风险

保留成功字体路径 hook不等于现有文本 hook 全部安全。当前还存在：

- `NdkStrView` 按 arm64 libc++ `std::string` 的 `+8/+16` 偏移读取，但工作流同时构建 `armeabi-v7a`；
- `FakeNdkStr` 同样依赖手写 ABI；
- `CNColor4B` 只声明 RGB 三字节，而 cocos2d `Color4B` 是 RGBA 四字节；
- `LoadingSceneLayerInfo::setText` 的按值 `std::string` 参数被按通用指针转发；
- 翻译表热重载对全局 `unordered_map`／vector 执行 swap，渲染线程同时无锁读取，存在数据竞争。

修复时必须把“已验证字体路径 hook”与“需要重写的文本 ABI 层”分开：字体路径路线保留，文本入口改为由 NDK 编译器生成的真实 `std::string` 函数签名，并使用不可变快照或统一锁。

## 中风险缺陷

- `i18n/cn-downloader-ui-text.tsv` 已导出 Java 下载浮层英文诊断，但 APK 工作流仍直接编译仓库 Java 源码，没有运行物化工具，因此这些英文不保证进入最终 APK；
- 云端 `ui_credits` 和客户端更新地址经 `Uri.parse()` 直接交给 `ACTION_VIEW`，应限制为 HTTPS 并拒绝 opaque URI、userinfo 和非允许域名；
- mixed content 和 `setAllowFileAccess(true)` 扩大 WebView 攻击面；
- 本地覆盖命中只有 logcat 记录，没有结构化启动自检，用户侧无法直接确认关键 JS／HTML 是否命中。

## 推荐修复顺序

1. 前端包成功应用后强制安全重启；
2. 给 `version_js.json` 增加 SHA-256／size 并在解压前验证；
3. staging 解压、完整验证、事务提交和回滚；
4. 重写 WebView 本地文件拦截器的来源和 canonical path 验证；
5. 修复 native `std::string`／`Color4B` ABI 与翻译表数据竞争，保留字体路径 hook；
6. 把 Java 下载 UI 文本物化接入真正的 APK 构建；
7. 增加启动自检：记录关键 JS、HTML、jQuery 注入器分别命中本地覆盖，并把版本、摘要和命中情况写入 CNLog。

## 验收边界

本报告确认了源码中的调用链和 v6 包内引用关系，但不替代真机验证。最终验收仍需：

- 手动运行工作分支 APK Actions；
- 安装产物后清晰记录 `MagiaHook-Found` 的关键 JS／HTML；
- 对既有安装触发一次 `version_js` 更新，确认下载、摘要校验、事务提交、重启和新文案全部生效；
- arm64 与 armv7 分别验证 native 文本和字体 hook。
