# magirecocn-legacy-client

魔法纪录中文化客户端（上一代 / Totentanz 系）的**可重新构建存档**。

本仓库不是从零写的客户端，而是把一个既有的成品 APK 反编译成 apktool 工程后入库，
使它可以被 CI 重新构建；在此基础上叠加了一层用 Java 写的补丁。

> **与 [`magireco-cnv-client`](https://github.com/MagirecoCN-Revival-Project/magireco-cnv-client) 的关系**
>
> 那个仓库是复兴计划自建的客户端（已归档）。本仓库是**另一支**：基础 APK 来自
> `io.kamihama.totentanz`（署名见下方「原始署名」），我们在其上做 UI 与下载逻辑的改造。
> 两者的工程结构与 CI 思路一致，但代码不共享。

---

## 仓库结构

```
AndroidManifest.xml  apktool.yml  original/  unknown/  kotlin/  META-INF/
res/  assets/  lib/            ← apktool 反编译产物，构建时原样使用
smali/                          ← classes.dex（引擎本体，未改动）
smali_classes2/                 ← classes2.dex；含 RestClient 的一处委托改动
smali_classes3/                 ← classes3.dex；全部由 patch/ 的 Java 编译而来
patch/src/main/java/            ← ★ 补丁源码，唯一事实来源
jadx-reference/                 ← jadx 反编译产物（只读参考，不参与构建）
tools/                          ← 测试套件、构建前置检查、汉化与资源工具
config.json                    ← 线上 config.json 的快照（真实配置，非样例）
```

`config.json` 是**某一时刻的拷贝，不参与构建，也不会自动跟着线上走**。它的用途是
让人在本地看清字段长什么样。真正生效的永远是
<https://api.magireco.top/legacy/config.json>；改线路、改 `proxy`、改
`min_speed_kbps` 都是改线上那一份，改这里没有任何效果。发现两边对不上时以线上为准，
顺手把这份快照更新一下。

### 为什么 jadx 产物不参与构建

`jadx-reference/` 是给人读的。jadx 输出的 Java **不可重新编译**——里面有
`/* JADX WARN */` 标注、无法还原的控制流，以及像 `installArchive` 那样直接变成
`throw new UnsupportedOperationException("Method not decompiled")` 的桩。
能被 `apktool b` 重组的是 `smali/`，所以构建走 smali，jadx 只作参考。

---

## 补丁层（`patch/src/main/java/io/kamihama/magianative/`）

| 类 | 职责 |
|---|---|
| `CNCNDownloadUI` | 资源下载浮层。背景图 + 毛玻璃底板 + 左列署名区 + 右列文件槽位/总进度；左上 LOG 胶囊、右上主题切换与 GitHub 胶囊 |
| `CNDownloaderFix` | 资源安装器。15 个基础包的下载、解压校验、完成标记、重试 |
| `CNChunkedDownload` | 多线程分片下载 + 断点续传 |
| `CNMirrors` | 线路目录：从 `config.json` 拉取线路表，失败/停滞/过慢时自动换线 |
| `CNHotUpdate` | 热更新的文件下载，与首次安装共用同一套选线与分片逻辑 |
| `CNHotUpdateCheck` | 热更检查流程：启动时比对台词包/前端脚本包版本，必要时下载并应用。重写自原包的 `RestClient.checkAndApplyHotUpdate`——那版浮层自始至终不出现，无从判断跑没跑 |
| `CNHotUpdateTx` | 热更包的**事务化应用**：暂存 → 备份 → 换入，出错整体回滚，崩溃后按 journal 恢复。只用于热更，安装器的大包仍直接解压 |
| `CNWebProxy` | WebView 拦截层代理：把原 `WebViewClient` 包一层，本地文件没命中的 GET 可改走 `/stream/`。默认纯透传，模式由 `config.json` 的 `proxy.web_mode`（`off` / `measure` / `on`）下发，切换不用重打 APK。端点级代理在真机上五次会话零命中（见「网络出口」一节），这是替代路线 |
| `CNSafeLink` | 外链统一出口：只放行 HTTPS 且域名在**写死在客户端**的允许列表内。挡的是「服务端被攻破后靠改配置把玩家导去任意地址」与配置写错，**不是**中间人——那一层已由 DNSSEC + 完整 TLS 验证覆盖 |
| `CNVersionCheck` | 客户端版本检查，跑在热更检查**之前**。本端版本硬编码在 native（`CLIENT_VERSION`，与 APK 的 versionName/versionCode 无关），云端版本在 `config.json` 的 `client` 段。任何异常一律放行，绝不因网络抖动挡住进游戏 |
| `CNRestart` | 重启本进程：先用 `AlarmManager` 把启动 Intent 排到 ~300ms 后再自杀。原包的 `RestClient.restartApp()` 是坏的——它开头会重跑旧热更（浮层再现），且新 Activity 起在同进程里被随后那一刀砍掉 |
| `CNTutorialPrompt` | 「下次启动去播序章」的标记读写与「自动询问只问一次」的记忆，另含给 native 用的隐藏/恢复前端界面入口。真正的触发在 native 侧（拦 `pushSceneTop` 改调 `pushScenePrologue`） |
| `CNBgm` | 安装浮层的 BGM。不用 `MediaPlayer`——它只能整文件循环，会放出尾部 235 帧 padding 且接缝有空隙；这里自己 `MediaExtractor`+`MediaCodec` 解码喂 `AudioTrack`，按 HCA 循环点做采样级无缝循环。全类绝不外抛 |
| `CNLog` | 统一日志：logcat + 内存环形缓冲 + 文件，LOG 面板直接渲染同一份缓冲区 |

补丁类的 smali（`smali_classes2/…/CNCNDownloadUI*` 与整个 `smali_classes3/`）
**每次 CI 构建都会用 Java 源码重新生成**，手工改这些 .smali 不会影响产物。

### 铁律：安装器入口绝不能抛异常

native hook 拦下引擎的 `DownloadSceneLayer::init` 后，用
`CallStaticVoidMethod` 转调 `RestClient.startCNDownload`（其体即
`CNDownloaderFix.runInstaller(); return;`），随后做 `ExceptionCheck` /
`ExceptionClear`。

**一旦有 Throwable 逃逸进 JNI，hook 会清掉它并放行引擎自带的下载场景——
玩家就会看到原生安装界面。** 这是必须避免的终态。

因此被 native 直接或间接调用的入口（`runInstaller`、`getEndpoint`）都在最外层
套了 `catch (Throwable)`：宁可停在我们自己的浮层上显示错误，也绝不把控制权
交回引擎。改动这两个方法时不要破坏这一层。

同理，`CNCNDownloadUI.show()` 只有在浮层**确实挂上 decorView** 之后才置
`isShowing = true`；无条件置位会导致一次创建失败后，本进程内后续所有
`show()` 都在开头直接返回，浮层再也建不起来。

### 唯一一处手工 smali 改动

`smali_classes2/io/kamihama/magianative/RestClient.smali` 里
`cnDownloadFileFull` 的方法体被替换成一次委托：

```smali
invoke-static {p0, p1, p2, p3}, Lio/kamihama/magianative/CNHotUpdate;->download(...)Z
```

这样热更新的文件下载也走支线。改这一处而不是重写整个 `RestClient`，是因为
`RestClient` 里有被 native 调用的方法和 jadx 无法完整还原的代码，整类重写风险过大。

### 右上角胶囊：`right_pill`

浮层右上角那个胶囊默认是「GitHub」，`config.json` 下发 `right_pill` 时会变成
「支持我们」——点了弹内建样式弹窗，弹窗里的「去支持」跳 `url`（一律先过
`CNSafeLink`，只放行 HTTPS + 允许列表内的域名）。

```json
"right_pill": {
  "enabled": true,
  "label": "♥  支持我们",
  "title": "支持我们",
  "content": "弹窗正文",
  "url": "https://afdian.com/a/xxx"
}
```

`enabled` **缺省 true**（老配置行为不变），置 `false` 时**无视其余字段**直接回落
默认 GitHub 胶囊。之所以要这个显式开关：原先只看「有没有 `right_pill` 且 `label`
非空」，想临时关掉就得把整段删了或把 `label` 清空，改回来还得把字段重新敲一遍。

---

## 安全模型：config.json 是半可信输入

客户端的信任锚只有三样，都**写死在包里**：`CNMirrors.MIRRORS_URL`（去哪儿取配置）、
`CNSafeLink` 的外链允许列表、APK 签名。**其余一切都来自 `config.json`，而它是
「服务端被攻破就能改」的东西**——所以从它流出来的每个字符串都要当输入校验。

### 🔴 只收 https：这是整条信任链的第一环

本项目的完整性前提是「DNSSEC + 完整 TLS 验证都开着，能在这种情况下劫持约等于
服务器已被攻破」。问题在于**这个前提本身就是 `config.json` 能关掉的**：只要往
`mirrors[].base` 或 `proxy.base` 里填一个 `http://`，TLS 就整个不参与了。

后果不止「被人看见下了什么」，能串成一条完整的执行链：

```
配置被改 → mirrors 填 http:// → 明文 / 任意主机投毒
  → extractChecked 只验结构不验内容，照单全收
  → 恶意 JS 落进 <files>/magica/js/
  → shouldInterceptRequest 本地优先、热更只写不删 → 永久执行
  → androidCommand.jsCallback 进 native
```

**安装器那 15 个基础包没有 md5/sha 校验**（只有热更包有 `verifyZip`），完整性
全押在 TLS 上——这让上面那条链只差一个 `http://` 就能走通。

所以 `CNMirrors.normalizeBase` 现在**只收 https**，另外拒掉内嵌控制字符
（换行/CRLF 会把一次请求拆成两条）。代价是零：线上六条线路本来全是 https。

> 仍然欠着的一层：给 15 个基础包也加上 md5/大小校验。那需要服务端先出一份清单，
> 属于运维改动，本仓库这边没有单方面能做的部分。**在那之前，基础包的完整性
> 完全依赖 https 不被绕过**——这也正是上面那条只收 https 的规则不能松的原因。

### `proxy.domains` 的最小粒度

它是**后缀**匹配（`magi-reco.com` 命中 `dorothy.magi-reco.com`）。没有下限的话，
填一个 `"com"` 就能把玩家所有 `.com` 流量吸进代理——配置被改时这是最省事的全量
劫持。`isSaneProxyDomain` 要求至少两段、每段非空、纯 ASCII，并点名拒掉常见的两级
公共后缀（`com.cn` / `co.uk` / `pages.dev` / `github.io` …）。

挡不住所有情况（多级公共后缀仍会漏），但「一个词吸走整个顶级域」这条最便宜的路
被堵死了。

### 解压膨胀比上限

归档的 md5/大小校验管的是**压缩后**那份，管不到解压出来有多大。一个几十 MB 的包
可以炸出几十 GB 把玩家存储写满，而写满之后倒霉的不只是游戏。`extractChecked`
边写边看膨胀比，超过 200 倍且已写出 256 MB 就中止——游戏资源本来就是 PNG/音频，
实测膨胀比接近 1，离 200 差两个数量级，正常包不会误伤。

这一条与内容是否可信无关，纯粹是别让一个坏包造成不可逆的破坏。

### 资源与生命周期（2026-08 审查补的两处）

- **代理响应流要能自己收尾。** `WebResourceResponse` 拿走的是一个裸
  `InputStream`，什么时候关全在 WebView 手里。读到 EOF 再 close 时
  `HttpURLConnection` 会把连接放回池子；但页面被换掉、请求被取消时只有 close
  没有 EOF，那条连接就悬着。所以 `CNWebProxy` 把 `disconnect()` 挂在流的
  `close()` 上（`DisconnectOnClose`），两种收尾都覆盖得到。
- **`hide()` 要把 static 视图引用清干净。** `CNCNDownloadUI` 的视图引用全是
  `static`，生命周期是整个进程；漏掉一个就等于把 `Activity` 钉住不放。原先漏了
  `vGitHubChip` / `githubChipBg` / `supportModal` / `vFooter` 四个，
  其中 `vGitHubChip` 身上还挂着 `SupportClick`，那个监听器里又捏着一个 Activity。
  加新视图字段时记得同步 `HideRunnable` 的清理列表。
- **启动期的东西要挂在 `triggerInstaller()` 的分支之前。** 那个方法分两支：
  安装标记存在走「版本检查 → 热更检查」，不存在走 `runInstaller()` 然后
  **直接 return**。挂在热更检查里的东西，首次安装那一整个会话都不会跑
  ——`CNWebProxy.install()` 一开始就踩了这个坑。装完是否重启还取决于
  `NO_RESTART_FLAG`，不重启就一路裸奔进游戏。

### 判据的回归测试

上面这些判据都在 `tools/ConfigGuardTest.java` 里钉着（43 项）。改动它们之前先跑：

```bash
java -cp .build-test:.cache/deps/android.jar ConfigGuardTest
```

---

## 网络出口：谁走支线、谁直连主线

约定是 **支线只负责分发文件，配置一律直连主线**。

| 请求 | 去向 | 位置 |
|---|---|---|
| `config.json`（线路表本身） | 直连主线 | `CNMirrors.MIRRORS_URL` |
| `version_scenario.json` | **走支线** | `CNHotUpdateCheck.fetchVersion` |
| `version_js.json` | **走支线** | `CNHotUpdateCheck.fetchVersion` |
| `/magica/api/snaa`（端点发现） | 代理配置已下发则经 `/stream/`，否则直连 Totentanz | `CNDownloaderFix.snaaUrl()` |
| 15 个基础资源包 | **走支线** | `CNDownloaderFix.fetchArchive` |
| `cn_scenario_update.zip` / `cn_js_update.zip`（热更新） | **走支线** | `CNHotUpdate.download` |
| **游戏本身的 API / 页面 / 图片** | **不经上述任何一条** | 见下 |
| 同上，但 `proxy.web_mode=on` 时的 GET | 经 `/stream/` 转发（失败即回退直连） | `CNWebProxy.fetchViaProxy` |
| 同上，`proxy.web_mode=measure` 时的配对测速 | 直连与 `/stream/` 各拉一次，只记时不接管 | `CNWebProxy.probe` |

换线只改「从哪里取字节」。安装完成标记里记的始终是规范 URL
（`https://assets.magireco.top/` + 文件名），所以换线不会让既有安装失效。

### 游戏运行时的流量走 WebView，不走上面这些（2026-08-07 真机查明）

上表全是**我们自己的**下载链路。游戏跑起来之后的流量是另一套，完全不经过它们：

```
WebView（jp.f4samurai.web.WebViewImpl$WebViewClientImpl.shouldInterceptRequest）
    ↓ 每个请求先记一行 MagiaHook-URL
    ↓ URL 含 /magica/ → 映射到 /data/data/<包名>/files/magica/<其后部分>
    ├─ 本地有这个文件 → MagiaHook-Found，直接本地供给（不出网）
    └─ 没有            → super.shouldInterceptRequest()，真的走网络
```

这段拦截在**原始 APK 的 `classes.dex` 里**（`smali/jp/f4samurai/web/`），不是本仓库
写的。一次真机会话的实测分布：143 个请求里 93 个命中本地文件，出网的少数里包含
`/magica/api/page/TopPage`、`MyPage`、`MainQuest` 这些真正的业务请求。

它的形状由 `tools/check-webview-interceptor.py` 守着（13 项断言）——这些是**前提
而非选择**，改了会变成运行时一个静默失效，而不是构建失败。

> **HTML 覆盖不是「推测可用」，是有调用链的。**（数据来自
> `feature/legacy-client-runtime-i18n` 分支的静态交叉核对，该分支已归档为
> `archive/legacy-client-runtime-i18n-20260806`）
>
> 对当时的 v6 包做静态扫描：401 个文件 = 197 JS + 181 HTML + 23 JSON；其中
> **154 个打包 JavaScript 含 `text!template/….html` 加载**，共 211 次模板引用、
> 192 个唯一模板目标，**119 个打包 HTML 明确被打包 JavaScript 引用**。
>
> 也就是说 `magica/template/**` 不只是被解压到目录躺着——业务 JS 确实通过
> RequireJS 的 `text!template/` 路径去请求它们。这和 CSS 走
> `text!css/…` 是同一个机制，**因此同一个「进过包就再也拿不出来」的陷阱对
> HTML 与 JS 一样成立**（见下方 CSS 那节）。

**推论**：`UrlConfig::api` / `chat` 这两个 native getter 在整场会话里**一次都没被
调用**——游戏的 API 地址是前端 JS 按页面 origin 拼出来的，走 WebView 发出去。
所以任何挂在 `UrlConfig` 上的代理都碰不到游戏的实际流量。

#### 端点级代理已判定为零命中（2026-08-07，五次会话）

`endpointRewrite` 在真正改写时会打一行 `[proxy] api[n]: 原址 -> 新址`。
0103 / 0104 / 0105 / 0107 / 0112 五份真机日志里，这一行**一次都没有出现过**。
原因是两条独立的死路正好凑齐：

- 引擎自始至终只读 `api[0]`，而它的值是个**裸主机名**（`dorothy.magi-reco.com`，
  没有 scheme），native 的 `tryRewriteUrl` 第一道 `"https://"` 判断就返回 false；
- `api[1..13]` 与 `chat[0..5]` 确实是完整 URL——`probeEndpointSlots` 在 0112 里把
  20 个槽位全 dump 出来了，这正是当初写那个探针要回答的问题。但那些值走的是
  **原始** getter，只观测不改写；引擎自己压根没调过这些槽位的钩子。

结论：端点级这条路在当前引擎行为下**不可能生效**。钩子仍然装着（观测有价值，
且成本只有几次字符串比较），但不要再指望它代理到任何东西。

#### 拦截层与端点级不是同一件事（别把黑屏记到它头上）

`45289988` 的黑屏是**改写 `UrlConfig::web`** 造成的：web 端点一改，页面的 origin
跟着变，前端里写死指向原域名的绝对地址全部跨域。那条改写至今停用。

`CNWebProxy` 走的是另一层——`shouldInterceptRequest`。它把字节**交回**给 WebView，
页面 origin 始终是 `dorothy.magi-reco.com`，浏览器根本不知道数据是从哪拿的，
所以跨域无从谈起。两者机制不同，黑屏那笔账不适用于它。

它还有一个端点级永远做不到的性质：**能失败回退**。端点级改写是「改完就交给引擎去
连」，连没连上我们这边根本不知道，代理一挂玩家就永远进不去（这正是当年删掉代理配置
磁盘缓存的理由）。拦截层取不到就 `return null`，WebView 自己按原地址直连。

硬限制：Android 的 `WebResourceRequest` **不提供请求体**，所以只有 GET 能代理，
POST 一律透传。Range 请求也主动不接管（见 `CNWebProxy.afterLocalMiss` 的注释）。

默认 `off`。目的是加速，而加速必须先证明——开发机（境外容器）量出 `/stream/` 每次
都比直连慢 2～8 倍，但那个数字对国内玩家没有参考价值。先发 `measure` 收真机数字，
数字说得通再从 `config.json` 翻成 `on`，不用重打 APK。

##### 代理线路表 `proxy.lines`：**和下载线路是两回事，永远不要合并**

代理入口也会换机器、也会临时不通，所以做成表而不是单个 `base`：

```json
"proxy": {
  "base": "https://api.magireco.top/stream/",
  "domains": ["magi-reco.com", "sisyphus.systems", "magica.f4samurai.com"],
  "lines": [
    { "name": "香港小鸡 • hk.example",   "base": "https://hk.example/stream/",       "weight": 100, "enabled": true },
    { "name": "主站 • api.magireco.top", "base": "https://api.magireco.top/stream/", "weight": 50,  "enabled": true }
  ],
  "web_mode": "measure"
}
```

- 按 `weight` **降序**取第一条没在冷却里的；某条失败（连不上/超时/5xx）打进 60 秒
  冷却，下一次请求自动落到下一条；全在冷却就回退直连。
- `lines` 缺省时从 `base` 合成一条，老配置一个字都不用改。
- `base` 仍然是下发给 native 的那个（native 只吃单值，改它要动 `.so`，不值当）。
  只写 `lines` 没写 `base` 时，权重最高那条会被拿去给 native。

**字段名和 `mirrors` 长得一样，纯粹是为了填配置的人少记一套约定。两张表不可
互换，也不共用任何选路逻辑：**

| | `mirrors`（下载线路） | `proxy.lines`（代理线路） |
|---|---|---|
| 成员是什么 | 绝大多数是**公共 CDN**（EdgeOne / ESA / gh-proxy / 对象存储） | 我们自己的反代入口 |
| 能不能转发 API | **不能**。它们只分发我们放上去的静态文件，API 指过去只会拿到 404 或它们的错误页 | 能，这就是它存在的理由 |
| 选路判据 | **吞吐**（`raceTopMirrors` 用 256 KB 预热对象量 KB/s）——那边是几 GB 的大文件 | **首字节延迟**——这边是几 KB 的 API 往返，吞吐再高也救不了 RTT |
| 失败后果 | 换线续传，字节不丢 | 回退直连，代价是这一个请求慢一点 |

拿吞吐去挑代理线，会挑出一条「带宽大但绕地球一圈」的。所以 `CNWebProxy` 自成一套
线路表 + 冷却，**不复用 `CNMirrors` 的任何竞速/降级机制，也不从 `mirrors` 取任何
一条**。

`measure` 模式下会拉同一个 URL、直连与**每条线**各测一次 TTFB，横排记进同一行
日志——要回答的不再是「代理比直连快吗」，而是「哪条最快、值不值得把权重调过去」。

##### 拿 WebView 实例：读 `WebViewHelper.sWebView`，别遍历 view 树找 tag

`WebViewImpl` 的构造函数里有 `setTag("WebViewImpl")`，看着像是给外人留的门。
**它不是。** 创建它的 `WebViewHelper.createWebView()` 紧接着就把标签覆盖掉了：

```java
WebViewHelper.sWebView = new WebViewImpl(sAppActivity);
WebViewHelper.sWebView.setTag("WebView");        // ← 覆盖成 "WebView"
WebViewHelper.sFrameLayout.addView(...);
```

第一版 `CNWebProxy` 就是照构造函数写的 `findViewWithTag("WebViewImpl")`，真机
（0117）上等满 180 秒也找不到——而 WebView 其实在开机后 **9 秒**就建好了。

现在直接反射读 `jp.f4samurai.web.WebViewHelper.sWebView`：引擎自己就是靠这个字段
握着唯一那个 WebView 的（`createWebView` 赋值、`removeWebView` 置空），是**事实
来源**，不会被别处改名。

顺带两点：

- `removeWebView()` 会 `destroy()` 掉当前 WebView 并把字段置空，之后可能再
  `createWebView()` 建一个**新的**——新对象身上是引擎自己的 WebViewClient。
  所以等待线程**不在包上之后就收工**，而是长期比对实例身份，换了对象就重新包
  （前 180 秒 1 秒一轮，之后降到 5 秒一轮）。
- 找不到时按 10/30/60/120 秒各记一行。第一版全程静默，真机上只看得到
  「等了 180s 没等到」，**分不清是「引擎还没建」还是「建了但我找错地方」**——
  而那次恰恰是后者。

`CNHotUpdate` 只在 URL 确实指向主线资源根、且其后只剩一段文件名时才换线；
其余地址一律原样使用。

### 线上线路实测（2026-08-07）

线路表在 2026-08 换过一整轮：`assets-cdn1/2/3` 那套已经**整体退役**，现役是下面这些
（权重取自线上 `config.json`，schema 8）。拿 `cn_js_update.zip` 逐条打 HEAD：

| 线路 | 权重 | 探测结果 | ETag 形态 |
|---|---|---|---|
| `hkcdn.assets.magireco.top` | 100 | ✅ 8414194 / 支持 Range | `"0x8DEF471691E6CB0"` |
| `edgeone.assets.magireco.top` | 80 | ✅ 8414194 / 支持 Range | `"7a7cecf8…-2"` |
| `esa.assets.magireco.top` | 60 | ✅ 8414194 / 支持 Range | `"7a7cecf8…-2"` |
| `r2.assets.magireco.top` | 40 | ⏸ 可达但 `enabled:false` | `"7a7cecf8…-2"` |
| `v4.gh-proxy.org` → GitHub Release | 30 | ✅ 8414194 / 支持 Range | `"0x8DEF471691E6CB0"` |
| `gh-proxy.org` → GitHub Release | 20 | 同上 | 同上 |

> `r2` 关掉是因为 NS 换出 Cloudflare 之后 R2 自定义域失效（自定义域只在 Cloudflare
> 接管 DNS 时才生效）。它现在还能回 200 是因为前面挡着别的层，但不该再依赖它。

ETag 恰好分成两族，正是下面第 1 条设计决定的现场证据：`hkcdn` 与两条 gh-proxy 都
是转 GitHub Release，拿到的是对象存储的版本号；`edgeone`/`esa`/`r2` 都在 R2 前面，
拿到的是 S3 分段上传的 `<md5>-<段数>`。同一个文件、同一份字节，**四种线路给出三种
格式**。

两个由此定下的设计决定：

1. **ETag 只在同一条线路上比对。** 三条线路对同一文件给出的 ETag 格式互不相同
   （nginx 的 inode-mtime、CDN 的 MD5、对象存储的版本号）。若跨线路照比，
   每次自动换线都会判定「文件变了」并丢弃全部断点——换线与续传互相抵消。
   现在断点元数据里记录写入时所用的完整 URL：URL 相同才比 ETag，
   URL 不同（= 换了线）则只依赖总长度一致。三条线路的长度实测一致。

   代价：跨线路续传时无法察觉两端内容不同。兜底是解压阶段的
   `extractChecked`——内容对不上会抛 `ZipException`，随后删档重下。

2. **`min_speed_kbps` 按「千比特每秒」解释。** 字段名里的 kbps 按惯例是 bit，
   所以 `CNChunkedDownload` 里是 `minSpeedKbps() * 1000 / 8` 换成字节每秒。
   曾经按 KiB/s 解释过，那会把当时线上的 `800` 变成 800 KiB/s ≈ 6.5 Mbit/s 的下限。

3. **`config.json` 拉不到要带退避重试。** 2026-08-07 真机（0117）：6 次
   `refresh` 全部以 `SSLHandshakeException: connection closed` 失败，而且**全挤在
   同一秒**（开机后约 1 秒），`CNVersion` 也在同一刻挂掉；8 秒后 WebView 拉
   `dorothy.magi-reco.com` 一切正常——**网络那会儿只是还没就绪**。

   根因是调用方那对「先代理、失败再直连」背靠背发出，相隔几毫秒，网络没起来时
   必然一起失败，而 `refresh` 本身没有任何重试。后果是全局的：整场会话跑在内置
   默认线路上，`proxy` 段从来没下发过，`CNWebProxy` 拿不到配置一直是 off。
   而这一切只留下一行 WARN。

   现在 `CNMirrors.ensureLoadedAsync()` 在后台按 3/6/12/24/48/48 秒退避重试，
   成功即停。配置迟到不要紧：拦截层读的是 volatile 的 mode，中途变更即时生效。

4. **拉版本 json 失败不打冷却。** 这条是 2026-08-07 从真机日志里挖出来的：
   启动时先做镜像竞速，量的是 `cn_js_update.zip` 前 256 KB 的**吞吐**，
   在**已经预热**的对象上；紧接着去拉几百字节的 `version_js.json`，那是个
   **冷对象**，量的是**首字节延迟**。两件事根本不是一个维度。

   结果就是：竞速刚把 EdgeOne 提为首选，版本 json 一超时就
   `reportFailure` 把它打进 60 秒冷却——**自己刚选出来的最快线，被自己刷掉了**。
   现在 `fetchMeta` 拉版本 json 失败只记日志、换下一条镜像，不再上报失败；
   读超时也从 8s 放宽到 12s（`VER_READ_TIMEOUT_MS`），冷对象值得多等一会儿。

### 「过慢」这条线到底会怎样（别把它读成"装不上"）

线上现在是 `min_speed_kbps: 6400`，即 **800 KB/s ≈ 6.4 Mbit/s**。这个值是**有意
设定**的：整套资源加热更 15 GB 量级，低于这个速度的下载体验已经很难接受，6400
本身就是妥协过的结果。

它的语义是**「这条线不够快，换一条」，不是「你太慢，不给你装」**——这点以前这份
文档写得偏重，特此更正：

- `CNChunkedDownload` 每 10 秒测一次窗口吞吐，低于阈值就 `abort` 并抛
  `IOException("线路过慢：…")`；
- `CNHotUpdate.download` 接住它，`reportFailure` 把该线打进冷却，退避
  2/4/8 秒后**换线重试**，最多 4 次（`MAX_ATTEMPTS`）；
- **`.part` 文件和断点元数据全程不删**，`markFailed` 只改浮层状态。所以四次尝试是
  **接力续传**，每次从上次断的地方接着走；四次用完这个文件本次失败，但
  `.part` 还在，下次启动继续。

也就是说慢速用户是多花几次启动，不是被永久锁死。

> 历史记录：`assets-cdn1` 曾经从构建环境访问 100% 403（nginx 原样返回，与 UA /
> Referer 无关，连 `config.json` 本身也 403），而它是最高权重线路，每次安装第一次
> 尝试都会撞上。这套线路已在 2026-08 整体退役，问题随之消失；留着这段是因为它是
> `switch_after_failures: 1` 这个配置能救场的实证——撞一次立刻进 60 秒冷却，
> 代价被限制在一次尝试 + 2 秒退避。

---

## 构建

CI：`.github/workflows/build-apk.yml`，**仅手动触发**（`workflow_dispatch`）。

不需要任何 Secret 或 Variable：本仓库的 smali 已是成品，没有构建期注入的信任锚；
签名用 AOSP testkey，由 CI 从 `android.googlesource.com` 取。

流程：编译 `patch/` → d8 → baksmali → 覆盖补丁 smali → `apktool b` → zipalign →
apksigner → 上传 artifact。

### 关于签名

产物用 **AOSP testkey** 签名，与上游发行包同一签名身份（SHA-256
`a40da80a…a71bf5dc`），因此可以直接覆盖安装。这是一把**公开的测试密钥**，
不提供任何真实性保证——它只解决「能否覆盖安装」，不代表产物经过谁的背书。

### apktool 重组的等价性

`apktool b` 会重新编码 dex、`resources.arsc` 与 manifest，所以产物与逐字节替换 dex
的做法**不会二进制相同**。当时验证过语义等价：

- 三个 dex 的类全集完全一致（8457 / 443 / 22 个类）
- `resources.arsc` 资源条目数一致（1422）
- 831 个 `assets/` 与 `lib/` 文件逐字节一致
- AndroidManifest 去掉行号标注后完全一致

> ⚠ 上面这组数字是**那一次验证的快照，不是当前值**。`smali/` 的 8457 没动过
> （引擎本体我们不碰），但另外两个会随补丁层增长：当前仓库是
> `smali_classes2/` 447 个、`smali_classes3/` 27 个 `.smali`。
>
> 而且 `smali_classes3/` 里那 27 个**本身就是过期的**——它由 `patch/` 的 Java
> 每次 CI 重新生成，当前源码编出来是 59 个类。这不是问题（构建时先覆盖再
> `apktool b`），但别拿仓库里的 smali 去推断产物内容，**唯一事实来源是
> `patch/src/main/java/`**。
>
> 要重新做等价性验证，比的应该是「`smali/` 这一份」和「原始 APK 的 classes.dex」，
> 补丁那两个 dex 本来就该不一样。

---

## 前端资源汉化

前端（WebView 那一半）的汉化不走 APK，走热更包 `cn_js_update.zip`：
`tools/i18n-extract.py` 抽取待译串 → `i18n-apply.py` 回填 → `i18n-package.py`
打包。客户端不需要任何改动——`WebViewImpl$WebViewClientImpl.shouldInterceptRequest`
会把所有 `/magica/<path>`（`api/` 除外）重定向到 `<files>/magica/<path>`，
而热更包正好解压到那里。

### 「进游戏后还是英文」分别归谁管

这条清单来自 `feature/legacy-client-runtime-i18n` 的排查（分支已归档为
`archive/legacy-client-runtime-i18n-20260806`）。**不能把所有英文都归到 JS 上**——
不同来源要动的层完全不同：

| 英文出现在哪 | 归谁管 | 现状 |
|---|---|---|
| JS 里的按钮、确认框、错误提示、动态拼接文本 | 前端热更包（`magica/js/**`） | 已覆盖 |
| HTML／EJS 模板里的静态标题、标签、按钮 | 前端热更包（`magica/template/**`） | 已覆盖，且确有 RequireJS 调用链（见上节） |
| 数据 JSON 里的角色/道具/技能/章节等结构化字段 | 前端热更包（23 个 JSON + 注入器） | 已覆盖 |
| cocos2d 原生弹窗、下载／网络错误 | **native 文本 hook**（`MagiaLegacy.cpp` 的 i18n 表） | 已做，实测「已加载 295 条 + 2 前缀规则」 |
| 原生中文被渲染成日文字形 | **native 字体路径 hook**（`fontPathOverwrite`） | 已做，见 `check-fonts.py` |
| 资源下载浮层 | Java 补丁（`CNCNDownloadUI`） | 已做 |
| **烘焙进 PNG／plist 图集的英文** | **只能改图片资源** | 见下 |
| 服务端直接返回、未经注入器的字段 | 需扩展数据映射，或服务端处理 | 未做 |

**图片是提取器的盲区。** 提取器只找日文假名/汉字，遇到「文字被画进 PNG 里」的
就完全看不见。已知的一处是切页面时右下角那条英文 `Connecting...`——
它是 `base.css` 里 `#loading p` 的背景图 `connecting.png`（334×54），
不是文本，所以两轮汉化都漏了。

> 真要系统性做图片汉化，起点是全量图像清单——`research/apk-image-classification`
> 分支上有一份（APK 图像双层清单 + plist 图集拆解，4.4 万行）。目前没用上，
> 留着是因为那件事迟早要做。

`tools/make-connecting-sprite.py` 用**APK 里已有的国服素材**把它重拼成中文版：
文字取自 `assets/package/loading/loading_icon.png` 的
「数据加载中 . . .」，丘比取自 `loading_char.png` 的 8 帧奔跑循环，
底条从原图第 53 行逐列复制（那一行是纯底条）。

```bash
python3 tools/make-connecting-sprite.py <原版connecting.png> connecting.png
```

输出是**同名同尺寸的 APNG**，直接替换即可，**不用改 CSS**：不认 APNG 的
旧 WebView 会当普通 PNG 显示第 1 帧（静止中文），降级是干净的。
脚本自带回读校验，逐帧比对写出的 APNG 与合成结果。
另有 `--sheet` 输出 334×432 竖向雪碧图 + 配套 `steps(8)` CSS，作为
真机上 APNG 万一不动时的退路。

**真机反馈追加的一处修正：描边。** 原版 `Connecting...` 是画在纯白背景上的，
自带描边；而国服素材里的丘比是白色的，直接搬过来在白底上就只剩"半只"。
所以 `outline()` 给丘比补了一圈 **2px 深色描边**（`STROKE_RGB = (27,25,23)`，
取自字芯同族色，不是原版那种洋红）。

两个踩过的坑记在这里，省得下次重来：

- **描边不能做成线性渐变**，那读起来是"发光"不是"描边"。现在的做法是内圈实心
  255、只有最外 1 圈做淡出。
- **4px 太粗**，在白底上像给丘比镶了道黑框；3px 仍偏重，最后定在 2px。
- 文字**不额外描边**：试过之后笔画之间会糊在一起，反而更难认。

产出的图随 `cn_js_update` 走热更（v22 是无描边版，**v23 起是 2px 描边版**），
路径 `magica/resource/image_web/common/global/connecting.png`。

> 图和 CSS（若走雪碧图）必须**同时**到达客户端。把两者放进同一个
> `cn_js_update.zip` 就是原子的——热更是一次性解压覆盖。

### 热更包里的 CSS 是哪儿来的（重要）

包里的 CSS 分三类，来路完全不同，弄混会出事。**当前是 13 个**（`cn_js_update` v22
起；此前只有前两个）：

| 包内路径 | 来源 | 性质 |
|---|---|---|
| `magica/css/_common/fonts.css` | **重写**。线上原文只有 73 字节、一条 `@font-face{font-family:koruri;src:url(…/koruri-semibold.ttf)}`；包里保留同一族名，把 `src` 改指包内的 `magica/fonts/TTZhiHeiGB3-W4.ttf` | 完整覆盖，无遗漏——全站只有这一处 `@font-face`，`motoya`/`mbm` 是系统侧回退（native 的 `fontPathOverwrite` 管），不在 CSS 里 |
| `magica/css/_common/common.css` | **快照 + 追加**。第 1 行 = 线上 `common.css` 原封不动的 265927 字节（md5 `18b32a9b…`，与 `index.html` 里 `common.css?18b32a9b…` 一致），其后追加 2890 字节 `cn-patch` 覆盖规则 | 冻结了线上文件 |
| 其余 11 个（`quest/MainQuest.css`、`quest/QuestCommon.css`、`quest/PuellaHistoriaTop.css`、`quest/PuellaHistoriaLastBattle/*.css` 四个、`quest/QuestBattleSelect.css`、`top/Top.css`、`user/MyPage.css`、`collection/StoryCollection.css`） | **原样照抄服务端现役内容**，一个字节都没改 | 纯解毒用——见下节 |

第三类不是为了改样式，而是为了**盖掉设备上早年冻住的旧快照**。所以它们的正确状态
就是「与服务端逐字节相同」，`tools/check-css-freeze.py` 正是在守这一条。

范围也是刻意收窄的：最初一版放了全站 188 个，后来收到 13 个——按「主页 + 历史篇这
两条链路上每个页面模块 `text!css/…` 依赖」逐个推出来的。放得越多，将来要负责同步的
就越多，而每一个都是不可逆的（见下节）。

为什么必须整文件替换：`common.css` 由 `index.html` 的静态 `<link>` 加载，
不走 requirejs，没有别的注入点。而 `shouldInterceptRequest` **只按路径匹配、
会把 `?<md5>` 查询串丢掉**——所以只要本地存在这个文件，线上再怎么改版本号
都不会生效。

> ⚠ **这是个会过期的冻结**。今天 md5 还对得上；哪天服务端改了 `common.css`，
> 玩家端仍然吃我们这份旧的，新增的样式/入口会静默消失。改版前先跑一遍：
>
> ```bash
> curl -s https://dorothy.magi-reco.com/magica/index.html | grep common.css
> head -c 265927 <包内common.css> | md5sum      # 两个 md5 必须一致
> ```
>
> 不一致就要用新的线上原文重做快照，再把 `cn-patch` 段重新追加上去。

`cn-patch` 段做的事：右侧菜单 `#sideMenu #menuBtns` 的基础 CSS 指向
`common/global/update2/global_*.png`（线上那套是**英文**图标），把它们改指
`common/global/` 根目录的国服中文图标；`sideBigBtns` 的 quest/battle 与
`#globalBackBtn` 本来就指根目录，显式重写一遍防路径漂移。靠「文件末尾追加、
后写覆盖」生效，不用 `!important`。

### 🔴 CSS 进过热更包就再也拿不出来了

**每个页面的 CSS 也走拦截。** 不只是 `index.html` 里那四个 `<link>`——
其余页面的 CSS 是用 requirejs 的 text 插件当**文本**读进来再注进
`<style id="headStyle">` 的：

```js
// js/quest/MainQuest.js
define("… text!css/quest/MainQuest.css text!css/quest/QuestCommon.css …",
       function(…){ … a.setStyle(k + l); … })
```

两条路径最后都是请求 `/magica/css/**`，而 `shouldInterceptRequest` 只按路径
匹配、把 `?<md5>` 丢掉。**再叠上热更「只写不删」**（`RestClient.unzip` 只
`mkdirs` + 写文件，包里没有的既不删也不还原）——

> **往热更包里放过一次某个 CSS，这个动作不可逆。** 从包里移除它只是以后不再
> 更新它；设备上那份**永远留着、永远赢过服务端**。

这已经出过一次事故：有人为了改样式把某个页面 CSS 整份放进热更包，那份快照
缺了 `#QuestMap #toPuellaHistoriaTopButtonWrap` 的规则。这个 div 在
`template/quest/MainQuest.html` 里是**无条件渲染**的，尺寸/背景图/定位全靠
CSS 给——规则一没，它塌成 0 高度空 div，**历史篇（Puella Historia）入口就此
消失**，而模板、js、图片、控制台全都正常。

解毒只有一条路：**把服务端现役内容原样放回包里再发一次**，把设备上那份盖掉。

> **这一例已解决（`cn_js_update` v22）。** 包里 `magica/css/quest/MainQuest.css`
> 的 md5 是 `0344b11c…`，与服务端现役版本逐字节相同；同批还有
> `QuestCommon.css`、`PuellaHistoriaTop.css` 等共 13 个。
>
> 验证方式值得记一笔，因为"我改对了吗"在这类问题上特别难判：把线上四个
> `<link>`（sanitize / common / base / fonts）加 `<style id="headStyle">`
> 注入的 `MainQuest.css + QuestCommon.css`，再加 `MainQuest.html` 的 DOM 骨架，
> 在真 Chromium 里渲染，读 `#toPuellaHistoriaTopButtonWrap` 的计算样式——
> 包内版本与服务端原版**逐项一致**（`display:block` / visible / 160×56 /
> 定位 (200,430) / z-index 100 / 背景图 `btn_main.png`）。
>
> 顺带证否了一个当时很自然的猜测：「国服整体 `common.css` 和 Totentanz 的散乱
> per-page CSS 打架」。打不起来——`index.html` 里 `<style id="headStyle">` 排在四个
> `<link>` **之后**，同特异性下 per-page CSS 永远赢；而我们追加的那 2890 字节
> 只写了 `#sideMenu` 和 `#globalMenu`，压根没碰 `#QuestMap`。

所以只要包里有 CSS，就永远要负责让它和服务端同步。发包前跑：

```bash
python3 tools/check-css-freeze.py <cn_js_update.zip>
```

它从服务端 `js/system/replacement.js` 的 `fileTimeStamp` 取每个文件的 md5
（`index.html` 的 `?<hash>` 就是从这来的），逐个比对包里的 CSS；
`fonts.css` 在豁免名单里，`common.css` 按「前 N 字节 == 服务端原文」校验。

---

## 测试

`tools/` 下是补丁层的测试套件，跑在 JVM 上，不需要设备。先编译一次：

```bash
javac -nowarn -source 8 -target 8 -encoding UTF-8 \
      -cp .cache/deps/android.jar -d .build-test \
      $(find patch/src/main/java -name '*.java') tools/*Test.java
```

> ⚠ **运行时也要把 `android.jar` 挂上**，不只是编译时：
>
> ```bash
> java -cp .build-test:.cache/deps/android.jar <类名>
> ```
>
> 少了它，`BgmLoopTest` / `ThrottleTest` 会以
> `NoClassDefFoundError: android/content/Context`（或 `org/json/JSONObject`）
> 挂掉——**看起来像测试失败，其实是 classpath 少了一截**。踩过，记下来。

| 测试 | 断言数 | 需要测试服务器 | 覆盖 |
|---|---|---|---|
| `HotUpdateTxTest` | 57 | 否 | 事务化应用：正常提交、中途失败回滚、崩溃在提交前/后的两个恢复方向、恶意包拒收、残留事务不污染下一轮、幂等，以及**清单与孤儿清理**（跨版本删除、白名单外子树不删、无清单时不删、孤儿删除也能回滚、`listEntries` 拒收 7 类非法路径 + 重复条目 + 空包） |
| `SafeLinkTest` | 40 | 否 | 外链白名单：协议、authority 伪装、公共后缀（`pages.dev`）、空白与控制字符、大小写与末尾点归一化 |
| `ConfigGuardTest` | 43 | 否 | `config.json` 里云端可控字符串的准入：线路/代理 `base` 只收 https（明文 http 一律拒）、CRLF 与控制字符注入、`proxy.domains` 的最小粒度（裸 TLD 与常见公共后缀必须拦） |
| `WebProxyTest` | 47 | 否 | 代理改写判据：后缀匹配必须卡在点上、排除自身（`*.magireco.top`）、只改 https、端口摘取、配置不全一律透传、白名单里的空串不得变成"匹配一切" |
| `LogTest` | 20 | 否 | 日志缓冲与文件落盘 |
| `BgmLoopTest` | 19 | 否 | HCA 循环点的采样级无缝拼接 |
| `ThrottleTest` | 7 | 否 | 「值不值得换线」的相对基线决策 |
| `FlushTest` | 5 | 否 | 日志 flush 时序 |
| `ProxyFetchTest` | 32 | **是**（`proxy-test-server.py`） | 把 `CNWebProxy.fetchViaProxy` 真跑一遍：200 明文 / gzip / **条件请求 304 不得接管** / 跨协议 301 / 上游 5xx（进冷却）/ 上游 4xx（不进冷却）/ 分块传输 / 缺 Content-Type 的扩展名兜底 / 请求头转发白名单 / 连不上 |
| `ResumeTest` | 28 | **是** | 完整下载、短读拒绝、断点复用、临时文件丢失、同线路 ETag 变化（拒绝复用）、越界多发、跨线路续传（复用断点）、**服务端忽略 Range 返回 200（清断点而非反复撞墙）** |
| `HotUpdateTest` | 13 | **是** | 非主线地址走直连单线程、目标已存在则不重复下载、服务端提前断流时报失败而**不提交残缺文件**、承接上一步残片续传补齐 |

> **`ProxyFetchTest` 要多带一个 stub。** `CNWebProxy.fetchViaProxy` 成功路径一定会
> `new WebResourceResponse(...)`，而 android.jar 里那个构造函数是
> `throw new RuntimeException("Stub!")`——不盖掉它，这条路径在 JVM 上一步都跑不了。
> `tools/teststubs/android/webkit/WebResourceResponse.java` 是个能用的实现，
> 编译时**加进源文件列表**即可（先匹配者胜）：
>
> ```bash
> python3 tools/proxy-test-server.py 8791 &
> javac -nowarn -source 8 -target 8 -encoding UTF-8 \
>       -cp .cache/deps/android.jar -d .build-test \
>       $(find patch/src/main/java -name '*.java') \
>       tools/teststubs/android/webkit/WebResourceResponse.java tools/ProxyFetchTest.java
> java -cp .build-test:.cache/deps/android.jar ProxyFetchTest 8791
> ```
>
> 有一条**桌面上验不到**：Android 的 `HttpURLConnection` 底层是 OkHttp，会自己加
> `Accept-Encoding: gzip` 并透明解压（这正是我们不转发 WebView 那个
> `Accept-Encoding` 的理由）；桌面 JDK 不会自动加，所以 gzip 协商那一支只能在真机
> 验。测试会打一行 `⏭` 明说这件事，而不是假装验过了。

表里前七个（`HotUpdateTxTest` 到 `FlushTest`）直接 `java -cp … <类名>` 就能跑。
`ResumeTest` / `HotUpdateTest` 是**集成测试**，要先起测试服务器、
再把地址/sha/大小当命令行参数传进去（少传参数会以
`ArrayIndexOutOfBoundsException` 挂掉，那不是断言失败）：

```bash
python3 tools/server.py 2097152 8771          # 支持 Range 的测试服务器
java -cp .build-test:.cache/deps/android.jar ResumeTest <base> <sha256> <size>
```

除测试外，`tools/` 里还有几个构建前置检查，都是纯 Python、随时可跑：

| 脚本 | 守的是什么 |
|---|---|
| `check-proxy-hooks.py` | native 侧哪些代理钩子该装、哪些必须保持停用（把"停用"从注释里的承诺变成可核验的事实） |
| `check-webview-interceptor.py` | 基础 APK 里 WebView 拦截链的形状是否还和 `CNWebProxy` 的假设对得上（13 项）：`WebViewHelper.sWebView` 字段、两个 `shouldInterceptRequest` 重载及其转调关系、`api/` 排除、`?<md5>` 丢弃、本地根目录、未命中回落 `super`。**这些是前提不是选择**——`smali/jp/f4samurai/web/` 是原始 APK 的产物，我们只是在运行时包了一层 |
| `check-base-urls.py` | 规范前缀与各条热更地址的一致性，防止某条被硬绑到具体 CDN |
| `check-entry-guard.py` | 被 native 调用的入口方法体首条语句必须是 `try`（见「铁律：安装器入口绝不能抛异常」） |
| `check-css-freeze.py` | 热更包里的 CSS 是否还与服务端现役内容一致（见「CSS 进过热更包就再也拿不出来了」） |
| `check-fonts.py` | 钉死 `assets/fonts/` 下每个文件的哈希，并校验内容与文件名相符 |
| `check-so-deps.py` | 每个 `.so` 的 `DT_NEEDED` 都能在包内或系统里找到。踩过：`libMagiaLegacy.so` 链接 shadowhook，但 CI 只拷了前者，`libshadowhook.so` 落在构建目录没带上——**能打包、能签名、能安装，只在真机启动那一刻炸** |
| `check-asset-compression.py` | BGM 的 ogg 在 APK 里必须是 Stored 而非 deflate。`AssetManager.openFd()` 打不开压缩过的 asset，后果是「界面一切正常、就是没声音」 |
| `check-apk-freshness.py` | 产物确实是刚编译出来的那一份，不是上一版残留。踩过两次：`CNBgm` 编出了 `.class` 却不在任何一组 d8 输入里；`libMagiaLegacy.so` 编好了却忘了拷进 `lib/` |

### 热更包的文件清单与孤儿清理

解压是**只写不删**的：把一个文件从热更包里拿掉，只是「以后不再更新它」——设备上
那份会永远留着，而且因为 `shouldInterceptRequest` 本地优先且忽略 `?<md5>`，
它会**永远盖住服务端的版本**。历史篇入口就是这么丢的。

`CNHotUpdateTx` 现在在解压之前先 `listEntries()` 读 **zip 的中央目录**（不解压、
不解码，O(条目数)），拿到包内完整路径清单，用来做三件事：

1. 非法路径在**写第一个字节之前**整包拒收（绝对路径、`..` 穿越、盘符、写进
   `.cnv_tx/` 或 `.cnv_manifest/`、重复条目、只有目录条目的空包）；
2. 解压完和实际落盘的结果**对账**——多一个少一个都中止；
3. 和 `<files>/.cnv_manifest/<tag>.list`（上一轮下发的清单）做差集，算出**孤儿**，
   在同一个事务里删掉。删掉之后请求回落到服务端，等于恢复成「我们从没碰过它」。

删除范围由白名单限死，因为热更包和安装器的十几个大包写的是同一棵树：

| tag | 允许清理的前缀 | 依据 |
|---|---|---|
| `js` | `magica/js/`、`magica/template/`、`magica/css/`、`magica/fonts/` | 只有热更包写这几棵子树 |
| `scenario` | `madomagi/resource/scenario/json/` | `cn_scenario_img.zip` 全在 `.../scenario/img/` 下，两者路径交集为 0 |

`magica/resource/` **不在白名单里**——`cn_magica_resource.zip` 的 9547 项全部落在
这个前缀下，删孤儿会把安装包给的国服图标一起删掉。

清单在 `COMMITTED` 之后写：中间崩掉的话下一轮读到的是上一版清单，算出来的孤儿只会
更少（漏删）而不会更多（错删）——失败方向永远偏安全。首次启用时没有清单，什么都不删，
**已经中招的设备救不回来**，那只能靠把服务端现役内容原样发一遍去覆盖。

测试服务器支持用控制端点在不改变请求 URL 的前提下改变服务端行为
（`/settruncate?v=N` 截断、`/setetag?v=X` 换 ETag）——这很关键：
用查询串制造差异会让 URL 变化，而 URL 变化在新规则下等同于「换线」，
两种语义就区分不开了。

---

## 原始署名

基础 APK 的汉化与下载系统由以下作者完成，本仓库只是在其成品上做二次改造：

- 核心逆向开发：**MadeInMagius**（B站 ID，独立完成汉化引擎、下载系统与日服国服资源合并）
- 国服文件之外的翻译和校对：水银h2oag
- 下载加速及资源自动化推送：CyberNova
- 国服数据留存：segfault
- 国内加速与修复：@PhotonFlow

项目官网：<https://www.magireco.top>

---

## 远端分支现状（2026-08-07 盘点）

| 分支 | 状态 |
|---|---|
| `main` | 唯一在维护的线 |
| `archive/legacy-client-runtime-i18n-20260806` | **归档，只读**。原 `feature/legacy-client-runtime-i18n`，见下 |
| `research/adv-native-evidence-20260807` | ADV 剧情播放器（`magiaexedralive2dviewer`）的取证，**不是本客户端的活**，停错仓库了，别动 |
| `research/apk-image-classification` | APK 图像双层清单 + plist 图集拆解（4.4 万行）。目前没用上，**留着**——真要做图片汉化，起点就是它 |

### `archive/legacy-client-runtime-i18n-20260806` 里有什么

2026-08-05/06 的一条**平行实现路线**，124 个提交、42 个文件。功能已全部被 main
取代且实现更完整，因此不再维护。对应关系：

| 分支上的 | 被 main 的什么取代 |
|---|---|
| `CNHotUpdateTransaction.java` | `CNHotUpdateTx.java`（事务化应用 + 孤儿清理，57 项测试） |
| `CNSafeExternalLinks.java` | `CNSafeLink.java`（外链白名单，40 项测试） |
| `jp/f4samurai/web/WebViewImpl.java`（用 Java 整个重写拦截器） | 运行时包一层（`CNWebProxy.Delegating`）——**两者互斥**，后者不必替换原类，原行为是结构上的保证 |

**已从它回收进 main 的**：WebView 拦截链的机器守卫（想法来自
`audit-webview-runtime-overlay.py`，脚本本身不可用——它的 `FILES` 表指的全是那条
分支自己的产物）→ `tools/check-webview-interceptor.py`；v6 包的静态交叉核对数据
与「进游戏后还是英文」的来源分层清单 → 本文上面两节。

**没回收、要看得去翻它的**：`docs/` 五篇（构建签名校验、字体加载、js 包运行时
兼容、i18n 交付报告、WebView 覆盖安全审计）；`validate-js-update-package.py` /
`verify-cn-js-update-package.py`（热更包的构建期校验——但打包发生在
`magireco-cn-patch`，本仓库用不上）；`RuntimeTextI18n.inc` / `RuntimeFontPathHook.inc`
（native i18n 与字体 hook 的另一种组织方式，main 已有等价能力）。

> 归档用的是**分支**而不是 tag：本仓库的自动化会话凭证推 tag 会 403，推分支正常。
> 想要一个真 tag 的话，本地跑：
>
> ```bash
> git tag -a archive/legacy-client-runtime-i18n-20260806 \
>         archive/legacy-client-runtime-i18n-20260806 -m "归档：已被 main 取代"
> git push origin archive/legacy-client-runtime-i18n-20260806
> ```

---

## 状态提醒

与 `magireco-cnv-client` 一样，本仓库不做自动发版：CI 只有手动触发，产物只上传为
workflow artifact，不建 Release。原因相同——游戏后端不由我们掌控，自动产出对外包
只会让玩家装到连不通的版本。
