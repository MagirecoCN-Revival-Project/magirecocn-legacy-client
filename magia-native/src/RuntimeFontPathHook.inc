// 已验证字体路线的双 ABI 类型安全实现。
//
// 路线不变：仍然 hook Label::createWithTTF 两个重载与
// Label::setTTFConfigInternal，在运行时把 MTF4a5kp 的读取路径定向到
// TTZhiHeiGB3-W4；不替换 APK 字体文件。不同点仅是由 NDK 编译器处理
// std::string / TTFConfig / Size 和 bool 返回值 ABI，不再原地写调用者内存。
// createWithTTF 入口同时查询文本翻译表，覆盖“构造后不再 setString”的标签。

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

static const char RUNTIME_FONT_FROM[] = "fonts/MTF4a5kp.ttf";
static const char RUNTIME_FONT_TO[] = "fonts/TTZhiHeiGB3-W4.ttf";
static std::atomic<uint64_t> g_runtimeFontRemaps{0};

static bool runtimeMapFontPath(
        const std::string& source, std::string& target,
        const char* surface) {
    if (source != RUNTIME_FONT_FROM) return false;
    target = RUNTIME_FONT_TO;
    uint64_t number = ++g_runtimeFontRemaps;
    if (number <= 20 || number % 100 == 0) {
        LOGI("[font] %s: MTF4a5kp → TTZhiHeiGB3-W4 (#%llu)",
             surface, static_cast<unsigned long long>(number));
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

static CreateWithTtfConfigFn createWithTtfCfgOld = nullptr;
static CreateWithTtfPathFn createWithTtfStrOld = nullptr;
static SetTtfConfigInternalFn setTtfCfgInternalOld = nullptr;

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

template <class HookInstaller>
static void installRuntimeFontPathHooks(HookInstaller& H) {
    H("_ZN7cocos2d5Label13createWithTTFERKNS_10_ttfConfigERKNSt6__ndk112basic_stringIcNS4_11char_traitsIcEENS4_9allocatorIcEEEENS_14TextHAlignmentEi",
      (void*)createWithTtfCfgNew, (void**)&createWithTtfCfgOld,
      "font: createWithTTF(cfg) typed MTF4a5kp→TTZhiHeiGB3-W4");
    H("_ZN7cocos2d5Label13createWithTTFERKNSt6__ndk112basic_stringIcNS1_11char_traitsIcEENS1_9allocatorIcEEEES9_fRKNS_4SizeENS_14TextHAlignmentENS_14TextVAlignmentE",
      (void*)createWithTtfStrNew, (void**)&createWithTtfStrOld,
      "font: createWithTTF(str) typed MTF4a5kp→TTZhiHeiGB3-W4");
    H("_ZN7cocos2d5Label20setTTFConfigInternalERKNS_10_ttfConfigE",
      (void*)setTtfCfgInternalNew, (void**)&setTtfCfgInternalOld,
      "font: setTTFConfigInternal typed MTF4a5kp→TTZhiHeiGB3-W4");
}
