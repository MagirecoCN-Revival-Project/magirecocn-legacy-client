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
tools/                          ← 断点续传 / 热更新的测试套件
mirrors.json                    ← 下载线路列表样例
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
| `CNMirrors` | 线路目录：从 `mirrors.json` 拉取线路表，失败/停滞/过慢时自动换线 |
| `CNHotUpdate` | 热更新的文件下载，与首次安装共用同一套选线与分片逻辑 |
| `CNLog` | 统一日志：logcat + 内存环形缓冲 + 文件，LOG 面板直接渲染同一份缓冲区 |

补丁类的 smali（`smali_classes2/…/CNCNDownloadUI*` 与整个 `smali_classes3/`）
**每次 CI 构建都会用 Java 源码重新生成**，手工改这些 .smali 不会影响产物。

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
| `mirrors.json`（线路表本身） | 直连主线 | `CNMirrors.MIRRORS_URL` |
| `version_scenario.json` | 直连主线 | `RestClient.checkAndApplyHotUpdate` |
| `version_js.json` | 直连主线 | `RestClient.checkAndApplyHotUpdate` |
| `/magica/api/snaa`（端点发现） | 直连 Totentanz | `CNDownloaderFix.BOOTSTRAP_URL` |
| 15 个基础资源包 | **走支线** | `CNDownloaderFix.fetchArchive` |
| `cn_scenario_update.zip` / `cn_js_update.zip`（热更新） | **走支线** | `CNHotUpdate.download` |

换线只改「从哪里取字节」。安装完成标记里记的始终是规范 URL
（`https://assets.magireco.top/` + 文件名），所以换线不会让既有安装失效。

`CNHotUpdate` 只在 URL 确实指向主线资源根、且其后只剩一段文件名时才换线；
其余地址一律原样使用。

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

## 测试

`tools/` 下是断点续传与热更新下载的测试套件，跑在 JVM 上，不需要设备：

```bash
python3 tools/server.py 2097152 8771          # 支持 Range 的测试服务器
# 另开一个终端，按 tools/*.java 头部注释编译运行
```

`ResumeTest` 覆盖 19 项断言（完整下载、短读拒绝、断点复用、临时文件丢失、
ETag 变化、越界多发），`HotUpdateTest` 覆盖 12 项。

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
