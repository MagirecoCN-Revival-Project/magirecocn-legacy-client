# 国服 2.2.1 权威字体加载机制与复刻方案

## 权威输入

结论仅来自项目方提供的两份国服 2.2.1 APK 解包产物：

- apktool 3.0.1 解包目录
- Bandizip 直接解压目录

两份输入中的四个字体文件逐字节一致：

| 文件 | 用途 | 大小 | SHA-256 |
|---|---|---:|---|
| `MTF4a5kp.ttf` | 日服普通 UI 源字体 | 2,618,612 | `36dbe7b91d30d9d95713ba4b46bfa9b70f5d16bf759e45d3a043eae97da948a1` |
| `TTZhiHeiGB3-W4.ttf` | 国服普通 UI 与 WebView | 8,367,096 | `01a4be2e5fca489c30219b3bec5edac0b7c98128c5fa629c34a0208ed5b0ba34` |
| `mbm_20160902.ttf` | 日服剧情源字体 | 2,450,636 | `37f266883643ca3e3168049a130396a4993b981747f73c4f5068afec2412f5c5` |
| `TTDaYuanGB3.ttf` | 国服剧情正文、旁白与日志 | 17,507,340 | `01bbb65b3b21f8d445fe15412fc3b5864425033f534464be26de0aa7ed8150c0` |

字体文件名和字节必须分别保留。禁止把 `TTDaYuanGB3.ttf` 或
`TTZhiHeiGB3-W4.ttf` 复制后伪装成 `MTF4a5kp.ttf`、
`mbm_20160902.ttf` 或其他字体名。

## 国服原生加载链

国服 arm64-v8a 与 armeabi-v7a 的 `libmadomagi_native.so` 均包含：

- `web::DataCommand::getFont(std::string const&)`
- `cocos2d::FileUtils::getDataFromFile(std::string const&)`
- `cocos2d::FileUtils::fullPathForFilename(std::string const&) const`

原生字符串与调用位置表明：

- 普通 Cocos UI 使用 `fonts/TTZhiHeiGB3-W4.ttf`；
- 剧情正文、旁白和剧情日志使用 `fonts/TTDaYuanGB3.ttf`；
- 魔女文字继续使用 `fonts/witchText-export.fnt`；
- WebView 的 `DataCommand::getFont` 读取智黑字体数据并执行
  `fontDataGet({motoya: ...})`。

国服 `assets/resource/standalone_collection/magica/js/sa/main.js` 中的
`fontDataGet` 将 `json.motoya` 同时注册为：

```css
@font-face { font-family: 'motoya'; ... }
@font-face { font-family: 'mbm'; ... }
```

因此 WebView 并不是通过替换 CSS 文件或改字体文件名实现中文字体，而是由
原生读取 TTF、Base64 编码、回调 JS，再动态注册两个 family。

## 本分支复刻方式

`RuntimeFontPathHook.inc` 使用编译器生成 ABI 的强类型入口：

- `Label::createWithTTF(ttfConfig, ...)`
- `Label::createWithTTF(string, ...)`
- `Label::setTTFConfigInternal(...)`
- `FileUtils::fullPathForFilename(...) const`

映射为：

```text
fonts/MTF4a5kp.ttf      -> fonts/TTZhiHeiGB3-W4.ttf
fonts/mbm_20160902.ttf  -> fonts/TTDaYuanGB3.ttf
```

`FileUtils::fullPathForFilename` 的映射同时覆盖 WebView 原有
`getFontData -> fontDataGet(Base64)` 调用链，不需要重写 `DataCommand::getFont`
或创建新的前端字体协议。

实现不修改常量输入、不写 `std::string` 私有布局、不保存跨调用悬空指针，
由 NDK 分别生成 arm64-v8a 和 armeabi-v7a 的真实返回值 ABI。

## 构建守卫

- `prepare-authoritative-cn-font-assets.py`：只从项目方提供的国服 APK 解包目录
  或 7z 恢复四个原始文件；每个文件必须唯一命中大小和 SHA-256。
- `check-authoritative-cn-fonts.py`：构建前再次核验四文件，并显式拒绝
  `源字体哈希 == 目标字体哈希` 的历史污染。
- `build-local.sh`：设置 `AUTHORITATIVE_CN_FONT_SOURCE` 时先恢复再校验；没有
  权威源且仓库字体不合格时失败关闭，不生成 APK。

## 尚需真机验证

静态机制和双 ABI 符号已确认，但以下结论必须由构建产物真机验证：

1. 普通 Cocos 标签显示智黑且无堆损坏；
2. 剧情正文、旁白和剧情日志显示大圆；
3. WebView 的 `motoya` / `mbm` family 都收到智黑字节；
4. arm64-v8a 与 armeabi-v7a 均无 `std::string` ABI 崩溃；
5. 热更新后重启，JS/HTML 中文化与字体路径同时生效。
