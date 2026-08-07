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
config.json                    ← 线上线路列表的快照（真实配置，非样例）
```

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

---

## 网络出口：谁走支线、谁直连主线

约定是 **支线只负责分发文件，配置一律直连主线**。

| 请求 | 去向 | 位置 |
|---|---|---|
| `config.json`（线路表本身） | 直连主线 | `CNMirrors.MIRRORS_URL` |
| `version_scenario.json` | **走支线** | `CNHotUpdateCheck.fetchVersion` |
| `version_js.json` | **走支线** | `CNHotUpdateCheck.fetchVersion` |
| `/magica/api/snaa`（端点发现） | 直连 Totentanz | `CNDownloaderFix.BOOTSTRAP_URL` |
| 15 个基础资源包 | **走支线** | `CNDownloaderFix.fetchArchive` |
| `cn_scenario_update.zip` / `cn_js_update.zip`（热更新） | **走支线** | `CNHotUpdate.download` |
| **游戏本身的 API / 页面 / 图片** | **不经上述任何一条** | 见下 |

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

**推论**：`UrlConfig::api` / `chat` 这两个 native getter 在整场会话里**一次都没被
调用**——游戏的 API 地址是前端 JS 按页面 origin 拼出来的，走 WebView 发出去。
所以任何挂在 `UrlConfig` 上的代理都碰不到游戏的实际流量。要代理它只能在 WebView
这一层动手，而那正是 `45289988` 撞黑屏退回来的地方（页面 origin 一变，前端里写死
指向原域名的绝对地址就跨域了——**此为推测，待抓 WebView console 证实**）。

`CNHotUpdate` 只在 URL 确实指向主线资源根、且其后只剩一段文件名时才换线；
其余地址一律原样使用。

### 线上线路实测（2026-08-01）

拿本仓库的 `CNChunkedDownload.probe()` 直接打线上四条线路，结果：

| 线路 | 权重 | 探测结果 | ETag |
|---|---|---|---|
| `assets-cdn1.magireco.top` | 80 | ❌ **HTTP 403** | — |
| `assets-cdn2.magireco.top` | 60 | ✅ 7437513 / 支持 Range | `"9eea8ff0491d9bd68e0b1a51c12ecf32"` |
| `assets.magireco.top`（主线） | 40 | ✅ 7437513 / 支持 Range | `"6a01a2f8-717cc9"` |
| GitHub Release（默认关闭） | 10 | ✅ 7437513 / 支持 Range | `"0x8DEAF40B1CE13E4"` |

两个由此定下的设计决定：

1. **ETag 只在同一条线路上比对。** 三条线路对同一文件给出的 ETag 格式互不相同
   （nginx 的 inode-mtime、CDN 的 MD5、对象存储的版本号）。若跨线路照比，
   每次自动换线都会判定「文件变了」并丢弃全部断点——换线与续传互相抵消。
   现在断点元数据里记录写入时所用的完整 URL：URL 相同才比 ETag，
   URL 不同（= 换了线）则只依赖总长度一致。三条线路的长度实测一致。

   代价：跨线路续传时无法察觉两端内容不同。兜底是解压阶段的
   `extractChecked`——内容对不上会抛 `ZipException`，随后删档重下。

2. **`min_speed_kbps` 按「千比特每秒」解释。** 字段名里的 kbps 按惯例是 bit，
   而线上配置是 `800`。若按 KiB/s 解释，阈值会变成 800 KiB/s ≈ 6.5 Mbit/s，
   任何慢于此的用户每条线都会在 10 秒后被判「过慢」中断，4 次尝试耗尽即整包
   安装失败。现按 bit 解释：800 kbps = 100 KB/s，是个合理下限。
   **若原意就是 KB/s，请改代码而不是把线上值调大**，否则慢速用户会全量失败。

> `assets-cdn1` 从构建环境访问是 100% 403（nginx 原样返回，与 UA / Referer 无关，
> 连 `config.json` 本身也是 403）。无法区分「对所有人都坏」还是「只挡机房 IP」。
> 它是最高权重线路，所以每次安装的第一次尝试都会撞上它；好在
> `switch_after_failures: 1` 会让它立刻进入 60 秒冷却，后续文件自动跳过，
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
的做法**不会二进制相同**。已验证语义等价：

- 三个 dex 的类全集完全一致（8457 / 443 / 22 个类）
- `resources.arsc` 资源条目数一致（1422）
- 831 个 `assets/` 与 `lib/` 文件逐字节一致
- AndroidManifest 去掉行号标注后完全一致

---

## 前端资源汉化

前端（WebView 那一半）的汉化不走 APK，走热更包 `cn_js_update.zip`：
`tools/i18n-extract.py` 抽取待译串 → `i18n-apply.py` 回填 → `i18n-package.py`
打包。客户端不需要任何改动——`WebViewImpl$WebViewClientImpl.shouldInterceptRequest`
会把所有 `/magica/<path>`（`api/` 除外）重定向到 `<files>/magica/<path>`，
而热更包正好解压到那里。

**它有个盲区：图片。** 提取器只找日文假名/汉字，遇到「文字被画进 PNG 里」的
就完全看不见。已知的一处是切页面时右下角那条英文 `Connecting...`——
它是 `base.css` 里 `#loading p` 的背景图 `connecting.png`（334×54），
不是文本，所以两轮汉化都漏了。

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

> 图和 CSS（若走雪碧图）必须**同时**到达客户端。把两者放进同一个
> `cn_js_update.zip` 就是原子的——热更是一次性解压覆盖。

### 热更包里的 CSS 是哪儿来的（重要）

包里有两个 CSS，来路完全不同，弄混会出事：

| 包内路径 | 来源 | 性质 |
|---|---|---|
| `magica/css/_common/fonts.css` | **重写**。线上原文只有 73 字节、一条 `@font-face{font-family:koruri;src:url(…/koruri-semibold.ttf)}`；包里保留同一族名，把 `src` 改指包内的 `magica/fonts/TTZhiHeiGB3-W4.ttf` | 完整覆盖，无遗漏——全站只有这一处 `@font-face`，`motoya`/`mbm` 是系统侧回退（native 的 `fontPathOverwrite` 管），不在 CSS 里 |
| `magica/css/_common/common.css` | **快照 + 追加**。第 1 行 = 线上 `common.css` 原封不动的 265927 字节（md5 `18b32a9b…`，与 `index.html` 里 `common.css?18b32a9b…` 一致），其后追加一段 `cn-patch` 覆盖规则 | 冻结了线上文件 |

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

所以只要包里有 CSS，就永远要负责让它和服务端同步。发包前跑：

```bash
python3 tools/check-css-freeze.py <cn_js_update.zip>
```

它从服务端 `js/system/replacement.js` 的 `fileTimeStamp` 取每个文件的 md5
（`index.html` 的 `?<hash>` 就是从这来的），逐个比对包里的 CSS；
`fonts.css` 在豁免名单里，`common.css` 按「前 N 字节 == 服务端原文」校验。

---

## 测试

`tools/` 下是断点续传与热更新下载的测试套件，跑在 JVM 上，不需要设备：

```bash
python3 tools/server.py 2097152 8771          # 支持 Range 的测试服务器
# 另开一个终端，按 tools/*.java 头部注释编译运行
```

`ResumeTest` 覆盖 27 项断言：完整下载、短读拒绝、断点复用、临时文件丢失、
同线路 ETag 变化（拒绝复用）、越界多发、跨线路续传（复用断点）、
**服务端忽略 Range 返回 200（清断点而非反复撞墙）**。`HotUpdateTest` 覆盖 12 项。

`HotUpdateTxTest`（26 项）与 `SafeLinkTest`（35 项）只碰文件系统与字符串，
**连测试服务器都不需要**，编译完直接 `java -cp … <类名>` 即可：前者覆盖正常提交、
中途失败回滚、崩溃在提交前/后的两个恢复方向、恶意包拒收、残留事务不污染下一轮、
幂等；后者覆盖协议白名单、authority 伪装、公共后缀（`pages.dev`）、
空白与控制字符、大小写与末尾点归一化。

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

## 状态提醒

与 `magireco-cnv-client` 一样，本仓库不做自动发版：CI 只有手动触发，产物只上传为
workflow artifact，不建 Release。原因相同——游戏后端不由我们掌控，自动产出对外包
只会让玩家装到连不通的版本。
