// 国服字体加载机制的双 ABI 类型安全实现。
//
// 国服 2.2.1 原生库中已确认的字体分工：
//   * 普通 Cocos UI：fonts/TTZhiHeiGB3-W4.ttf
//   * 剧情正文、旁白与剧情日志：fonts/TTDaYuanGB3.ttf
//   * WebView：DataCommand::getFont 读取 UI 字体，Base64 后调用
//     fontDataGet({motoya: ...})，前端据此注册 motoya / mbm 两个 family。
//
// 日服归档客户端仍请求 MTF4a5kp / mbm_20160902。本实现不替换任何字体文件，
// 而是在类型安全的 Cocos 入口按精确路径映射：
//   MTF4a5kp      -> TTZhiHeiGB3-W4
//   mbm_20160902  -> TTDaYuanGB3
// 同时 hook FileUtils::fullPathForFilename，使 WebView 原有 getFontData ->
// fontDataGet(Base64) 调用链读到国服智黑字体，而不是另造一套字体注入协议。
// createWithTTF 入口仍同时查询文本翻译表，覆盖构造后不再 setString 的标签。

struct RuntimeTtfConfig {
    std::string fontFilePath;
    float fontSize;
    int glyphs;
    const char* customGlyphs;
    bool distanceFieldEnabled;
    int outlineSize;
};

struct RuntimeFontSize {
    float width;
    float height;
};

static const char RUNTIME_UI_FONT_FROM[] = "fonts/MTF4a5kp.ttf";
static const char RUNTIME_UI_FONT_TO[] = "fonts/TTZhiHeiGB3-W4.ttf";
static const char RUNTIME_STORY_FONT_FROM[] = "fonts/mbm_20160902.ttf";
static const char RUNTIME_STORY_FONT_TO[] = "fonts/TTDaYuanGB3.ttf";
static std::atomic<uint64_t> g_runtimeFontRemaps{0};

static bool runtimeMapFontPath(
        const std::string& source, std::string& target,
        const char* surface) {
    const char* from = nullptr;
    const char* to = nullptr;
    const char* route = nullptr;
    if (source == RUNTIME_UI_FONT_FROM) {
        from = RUNTIME_UI_FONT_FROM;
        to = RUNTIME_UI_FONT_TO;
        route = "MTF4a5kp -> TTZhiHeiGB3-W4";
    } else if (source == RUNTIME_STORY_FONT_FROM) {
        from = RUNTIME_STORY_FONT_FROM;
        to = RUNTIME_STORY_FONT_TO;
        route = "mbm_20160902 -> TTDaYuanGB3";
    } else {
        return false;
    }

    target.assign(to);
    uint64_t number = ++g_runtimeFontRemaps;
    if (number <= 30 || number % 100 == 0) {
        LOGI("[font] %s: %s (#%llu; source=%s)",
             surface, route, static_cast<unsigned long long>(number), from);
    }
    return true;
}

using CreateWithTtfConfigFn = void* (*)(
    const RuntimeTtfConfig&, const std::string&, int, int);
using CreateWithTtfPathFn = void* (*)(
    const std::string&, const std::string&, float,
    const RuntimeFontSize&, int, int);
using SetTtfConfigInternalFn = bool (*)(
    void*, const RuntimeTtfConfig&);
// Cocos2d-x 3.10: std::string FileUtils::fullPathForFilename(...) const。
// 以普通函数指针表示成员函数入口；std::string 的隐藏返回参数由 NDK 编译器
// 分别按 arm64 与 armeabi-v7a ABI 生成，禁止手工拼接对象布局。
using FullPathForFilenameFn = std::string (*)(
    const void*, const std::string&);

static CreateWithTtfConfigFn createWithTtfCfgOld = nullptr;
static CreateWithTtfPathFn createWithTtfStrOld = nullptr;
static SetTtfConfigInternalFn setTtfCfgInternalOld = nullptr;
static FullPathForFilenameFn fullPathForFilenameOld = nullptr;

static void* createWithTtfCfgNew(
        const RuntimeTtfConfig& config,
        const std::string& text,
        int horizontalAlignment,
        int maxLineWidth) {
    maybeReloadEngineI18n();
    std::string translated;
    const bool textHit = translateEngineText(
        text, translated, "Label::createWithTTF(config)");

    std::string mapped;
    const bool fontHit = runtimeMapFontPath(
        config.fontFilePath, mapped, "createWithTTF(config)");
    if (!fontHit && !textHit) {
        return createWithTtfCfgOld(
            config, text, horizontalAlignment, maxLineWidth);
    }

    if (fontHit) {
        RuntimeTtfConfig local = config;
        local.fontFilePath = mapped;
        return createWithTtfCfgOld(
            local, textHit ? translated : text,
            horizontalAlignment, maxLineWidth);
    }
    return createWithTtfCfgOld(
        config, translated, horizontalAlignment, maxLineWidth);
}

static void* createWithTtfStrNew(
        const std::string& text,
        const std::string& fontFile,
        float fontSize,
        const RuntimeFontSize& dimensions,
        int horizontalAlignment,
        int verticalAlignment) {
    maybeReloadEngineI18n();
    std::string translated;
    const bool textHit = translateEngineText(
        text, translated, "Label::createWithTTF(path)");

    std::string mapped;
    const bool fontHit = runtimeMapFontPath(
        fontFile, mapped, "createWithTTF(path)");
    if (!fontHit && !textHit) {
        return createWithTtfStrOld(
            text, fontFile, fontSize, dimensions,
            horizontalAlignment, verticalAlignment);
    }
    return createWithTtfStrOld(
        textHit ? translated : text,
        fontHit ? mapped : fontFile,
        fontSize, dimensions,
        horizontalAlignment, verticalAlignment);
}

static bool setTtfCfgInternalNew(
        void* self, const RuntimeTtfConfig& config) {
    std::string mapped;
    if (!runtimeMapFontPath(
            config.fontFilePath, mapped, "setTTFConfigInternal")) {
        return setTtfCfgInternalOld(self, config);
    }
    RuntimeTtfConfig local = config;
    local.fontFilePath = mapped;
    return setTtfCfgInternalOld(self, local);
}

static std::string fullPathForFilenameNew(
        const void* self, const std::string& filename) {
    std::string mapped;
    if (!runtimeMapFontPath(
            filename, mapped, "FileUtils::fullPathForFilename")) {
        return fullPathForFilenameOld(self, filename);
    }
    return fullPathForFilenameOld(self, mapped);
}

template <class HookInstaller>
static void installRuntimeFontPathHooks(HookInstaller& H) {
    H("_ZN7cocos2d5Label13createWithTTFERKNS_10_ttfConfigERKNSt6__ndk112basic_stringIcNS4_11char_traitsIcEENS4_9allocatorIcEEEENS_14TextHAlignmentEi",
      (void*)createWithTtfCfgNew, (void**)&createWithTtfCfgOld,
      "font: createWithTTF(cfg) typed CN UI/story routes");
    H("_ZN7cocos2d5Label13createWithTTFERKNSt6__ndk112basic_stringIcNS1_11char_traitsIcEENS1_9allocatorIcEEEES9_fRKNS_4SizeENS_14TextHAlignmentENS_14TextVAlignmentE",
      (void*)createWithTtfStrNew, (void**)&createWithTtfStrOld,
      "font: createWithTTF(str) typed CN UI/story routes");
    H("_ZN7cocos2d5Label20setTTFConfigInternalERKNS_10_ttfConfigE",
      (void*)setTtfCfgInternalNew, (void**)&setTtfCfgInternalOld,
      "font: setTTFConfigInternal typed CN UI/story routes");
    H("_ZNK7cocos2d9FileUtils19fullPathForFilenameERKNSt6__ndk112basic_stringIcNS1_11char_traitsIcEENS1_9allocatorIcEEEE",
      (void*)fullPathForFilenameNew, (void**)&fullPathForFilenameOld,
      "font: FileUtils fullPath typed WebView fontDataGet route");
}
