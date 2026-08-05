# APK 构建、签名与分支边界验收

## 分支边界

本轮工作的唯一写入分支是：

```text
feature/legacy-client-runtime-i18n
```

`main` 只作为只读基线。不得向 `main` 提交、推送、合并或部署。本分支继承并保留了已经真机验证成功的运行时字体路径 hook：

```text
fonts/MTF4a5kp.ttf
→ fonts/TTZhiHeiGB3-W4.ttf
```

该机制在 `MagiaLegacy.cpp` 中 hook `createWithTTF` 两个重载与 `setTTFConfigInternal`，不直接替换 APK 内的字体文件。本轮没有修改该段实现。

## Java 补丁事实来源

`patch/src/main/java/io/kamihama/magianative/` 是 Java 补丁的唯一事实来源。APK 构建工作流执行：

1. `javac` 编译 Java 源码；
2. `d8` 生成 dex；
3. `baksmali` 还原为 smali；
4. 用生成结果覆盖补丁类对应的 `smali_classes2/` 与 `smali_classes3/`；
5. 再由 `apktool b` 重组 APK。

因此，不应把新增功能直接写入生成的补丁 smali。现存的 `RestClient.cnDownloadFileFull` 委托是仓库明确保留的唯一手工 smali 桥接点。

## Native 构建

工作流从 `magia-native/` 为以下 ABI 重新编译：

- `arm64-v8a`
- `armeabi-v7a`

产物包括：

- `libMagiaLegacy.so`
- `libshadowhook.so`

工作流要求旧的 `libuwasa.so` 与 `libcn_hook.so` 不得重新出现，避免多个 hook 引擎对同一函数地址重复挂钩。

## APK 重组与签名

构建链路为：

```text
Java/C++ 编译
→ d8 / baksmali
→ apktool b --use-aapt2
→ zipalign -f 4
→ apksigner
```

签名使用 AOSP 公开 testkey，与既有上游包保持相同签名身份，以支持覆盖安装。工作流在签名后强制校验证书 SHA-256：

```text
a40da80a59d170caa950cf15c18c454d47a39b26989d8b640ecd745ba71bf5dc
```

同时启用 v1、v2、v3 签名。该公开测试密钥仅用于保持安装身份兼容，不提供发行真实性背书。

## 构建后强制检查

APK 上传前必须通过：

- `zipalign -c 4`；
- `apksigner verify --print-certs`；
- BGM OGG 未压缩检查；
- 所有 native `DT_NEEDED` 依赖可解析检查；
- 新编译 `.so` 和 Java 补丁类确实进入 APK 的新鲜度检查；
- AOSP testkey 指纹精确匹配；
- `classes3.dex` 存在检查。

这些检查用于阻断“源码编译成功，但新 `.so` 或补丁类没有进入最终 APK”的历史回归。

## 触发与发布限制

`.github/workflows/build-apk.yml` 只接受 `workflow_dispatch`，不会因 push 自动构建。它只上传临时 workflow artifact，不创建 Release、不修改线上配置、不部署客户端。

新增的 `.github/workflows/build-runtime-i18n.yml` 同样只接受 `workflow_dispatch`，只生成和验收 `cn_js_update` 运行时覆盖包，不创建 Release，也不更新 `version_js.json`。

本轮未把任何工作流改为自动触发，未创建 Release，未修改服务器版本号。

## 当前验证边界

已完成：

- 工作流源码与分支约束审查；
- 前端覆盖包的本地生成、确定性复构建和全量静态验收；
- 字体 hook 在 `main` 与工作分支保持同一 blob，未被本轮改动；
- JS、JSON、HTML/EJS、ZIP 结构和 vNext 引用完整性检查。

尚需通过 GitHub Actions 的手动运行完成：

- 双 ABI NDK 实际编译；
- Java → dex → smali → APK 全链路；
- APK 对齐、签名和最终产物级检查。

在没有实际 workflow run 和 artifact 前，不应宣称本分支 APK 已构建成功或已完成真机验证。
