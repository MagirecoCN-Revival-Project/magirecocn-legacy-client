// ─── 引擎硬编码串翻译与局部中文字形选择 ─────────────────────
//
// 文本仍由 files/madomagi/engine_i18n.tsv 热维护。与旧实现不同：
//
// 1. 直接使用 NDK libc++ 的 std::string ABI，不再手工猜 arm64 的 +8/+16
//    布局；同一份源码因此能正确覆盖 arm64-v8a 与 armeabi-v7a。
// 2. 翻译表以 immutable snapshot 原子替换；渲染线程不会与 3 秒热重载互相
//    读写 unordered_map/vector。
// 3. 不再全局改写 fontFilePath，也不修改调用者传入的 const TTFConfig。
//    只有确实命中翻译表的 Label 才拿一份本地 TTFConfig 副本，选择中文字形；
//    未命中文本和非 TTF 标签完全保持原样。
// 4. 对先建空标签、后 setString 的路径，观察并缓存该 Label 最近一次由引擎
//    设置的原始 TTFConfig；译文出现时临时套中文字体，未命中时恢复原配置。
// 5. 表内可写「中文<TAB>同一中文」作为 font-only identity 规则：适用于已经
//    被其他补丁改成中文、但仍需要强制使用中文字形的硬编码串。
//
// 可选元指令：
//   @font<TAB>fonts/TTZhiHeiGB3-W4.ttf
// 只影响命中翻译的标签；不替换 APK 内字体文件，不影响其他标签。

static const std::string ENGINE_I18N_PATH =
    "/data/data/io.kamihama.totentanz/files/madomagi/engine_i18n.tsv";
static const char* const ENGINE_CN_FONT_FALLBACK =
    "fonts/TTZhiHeiGB3-W4.ttf";

struct EngineI18nSnapshot {
    std::unordered_map<std::string, std::string> exact;
    std::vector<std::pair<std::string, std::string>> prefixes;
    std::string chineseFontPath = ENGINE_CN_FONT_FALLBACK;
    time_t mtime = 0;
};

static std::shared_ptr<const EngineI18nSnapshot> g_engineI18nSnapshot;
static std::mutex            g_engineI18nLoadMutex;
static std::atomic<time_t>    g_engineI18nLastCheck{0};
static std::atomic<uint64_t>  g_engineI18nHits{0};
static std::mutex             g_engineI18nMissMutex;
static std::unordered_set<std::string> g_engineI18nMisses;

static std::string i18nUnescape(const std::string& s) {
    std::string out;
    out.reserve(s.size());
    for (size_t i = 0; i < s.size(); i++) {
        if (s[i] == '\\' && i + 1 < s.size()) {
            char c = s[++i];
            if (c == 'n')      out += '\n';
            else if (c == 't') out += '\t';
            else               out += c;
        } else {
            out += s[i];
        }
    }
    return out;
}

static std::string i18nEscapeForLog(const std::string& s) {
    std::string out;
    out.reserve(s.size() + 16);
    for (char c : s) {
        if (c == '\n') out += "\\n";
        else if (c == '\t') out += "\\t";
        else if (c == '\r') out += "\\r";
        else if (c == '\\') out += "\\\\";
        else out += c;
        if (out.size() >= 512) {
            out += "...";
            break;
        }
    }
    return out;
}

static bool decodeUtf8(const std::string& text, size_t& offset, uint32_t& cp) {
    if (offset >= text.size()) return false;
    const unsigned char* p =
        reinterpret_cast<const unsigned char*>(text.data());
    unsigned char b0 = p[offset++];
    if (b0 < 0x80) {
        cp = b0;
        return true;
    }
    int continuation = 0;
    uint32_t value = 0;
    if ((b0 & 0xE0) == 0xC0) {
        continuation = 1; value = b0 & 0x1F;
    } else if ((b0 & 0xF0) == 0xE0) {
        continuation = 2; value = b0 & 0x0F;
    } else if ((b0 & 0xF8) == 0xF0) {
        continuation = 3; value = b0 & 0x07;
    } else {
        cp = 0xFFFD;
        return true;
    }
    if (offset + static_cast<size_t>(continuation) > text.size()) {
        offset = text.size();
        cp = 0xFFFD;
        return true;
    }
    for (int i = 0; i < continuation; ++i) {
        unsigned char bx = p[offset++];
        if ((bx & 0xC0) != 0x80) {
            cp = 0xFFFD;
            return true;
        }
        value = (value << 6) | (bx & 0x3F);
    }
    cp = value;
    return true;
}

static bool containsJapaneseKana(const std::string& text) {
    size_t offset = 0;
    uint32_t cp = 0;
    while (decodeUtf8(text, offset, cp)) {
        if ((cp >= 0x3040 && cp <= 0x30FF) ||
            (cp >= 0x31F0 && cp <= 0x31FF) ||
            (cp >= 0xFF66 && cp <= 0xFF9D)) {
            return true;
        }
    }
    return false;
}

static void auditEngineI18nMiss(const std::string& source,
                                const char* surface) {
    if (!containsJapaneseKana(source)) return;
    bool first = false;
    {
        std::lock_guard<std::mutex> lock(g_engineI18nMissMutex);
        if (g_engineI18nMisses.size() < 1024) {
            first = g_engineI18nMisses.insert(source).second;
        }
    }
    if (first) {
        std::string escaped = i18nEscapeForLog(source);
        LOGE("[i18n-miss] surface=%s source=%s",
             surface, escaped.c_str());
    }
}

static void loadEngineI18n() {
    std::lock_guard<std::mutex> lock(g_engineI18nLoadMutex);

    FILE* f = fopen(ENGINE_I18N_PATH.c_str(), "rb");
    if (!f) {
        if (std::atomic_load_explicit(
                &g_engineI18nSnapshot, std::memory_order_acquire)) {
            LOGI("[i18n] 表文件暂缺，继续使用已加载快照: %s",
                 ENGINE_I18N_PATH.c_str());
        }
        return;
    }

    auto fresh = std::make_shared<EngineI18nSnapshot>();
    char buf[8192];
    size_t lineno = 0;
    size_t bad = 0;
    while (fgets(buf, sizeof(buf), f)) {
        ++lineno;
        std::string line(buf);
        const bool complete =
            !line.empty() && (line.back() == '\n' || feof(f));
        if (!complete) {
            ++bad;
            int ch;
            while ((ch = fgetc(f)) != EOF && ch != '\n') {}
            continue;
        }
        while (!line.empty() &&
               (line.back() == '\n' || line.back() == '\r'))
            line.pop_back();
        if (line.empty() || line[0] == '#') continue;

        size_t tab = line.find('\t');
        if (tab == std::string::npos) {
            ++bad;
            continue;
        }
        if (line.compare(0, tab, "@font") == 0) {
            std::string font = i18nUnescape(line.substr(tab + 1));
            if (font.empty()) ++bad;
            else fresh->chineseFontPath = font;
            continue;
        }
        if (line[0] == '^') {
            std::string source =
                i18nUnescape(line.substr(1, tab - 1));
            std::string target =
                i18nUnescape(line.substr(tab + 1));
            if (source.empty()) ++bad;
            else fresh->prefixes.emplace_back(
                    std::move(source), std::move(target));
            continue;
        }
        std::string source = i18nUnescape(line.substr(0, tab));
        std::string target = i18nUnescape(line.substr(tab + 1));
        if (source.empty()) ++bad;
        else fresh->exact[std::move(source)] = std::move(target);
    }
    fclose(f);

    std::sort(fresh->prefixes.begin(), fresh->prefixes.end(),
        [](const auto& a, const auto& b) {
            return a.first.size() > b.first.size();
        });

    struct stat st;
    if (::stat(ENGINE_I18N_PATH.c_str(), &st) == 0)
        fresh->mtime = st.st_mtime;

    const size_t exactCount = fresh->exact.size();
    const size_t prefixCount = fresh->prefixes.size();
    const std::string fontPath = fresh->chineseFontPath;
    std::shared_ptr<const EngineI18nSnapshot> published = fresh;
    std::atomic_store_explicit(
        &g_engineI18nSnapshot, std::move(published),
        std::memory_order_release);

    LOGI("[i18n] 已原子加载 %zu 条 + %zu 前缀规则，中文字形=%s"
         "（第 %zu 行止，坏行 %zu）",
         exactCount, prefixCount, fontPath.c_str(), lineno, bad);
}

static void maybeReloadEngineI18n() {
    time_t now = ::time(nullptr);
    time_t last =
        g_engineI18nLastCheck.load(std::memory_order_relaxed);
    if (now - last < 3) return;
    if (!g_engineI18nLastCheck.compare_exchange_strong(
            last, now, std::memory_order_relaxed)) return;

    struct stat st;
    if (::stat(ENGINE_I18N_PATH.c_str(), &st) != 0) return;
    auto snapshot = std::atomic_load_explicit(
        &g_engineI18nSnapshot, std::memory_order_acquire);
    if (!snapshot || st.st_mtime != snapshot->mtime) {
        LOGI("[i18n] 检测到表变更，重新加载");
        loadEngineI18n();
    }
}

static std::string currentChineseFontPath() {
    auto snapshot = std::atomic_load_explicit(
        &g_engineI18nSnapshot, std::memory_order_acquire);
    if (snapshot && !snapshot->chineseFontPath.empty())
        return snapshot->chineseFontPath;
    return ENGINE_CN_FONT_FALLBACK;
}

static bool translateEngineText(const std::string& source,
                                std::string& target,
                                std::string* fontPath,
                                const char* surface) {
    auto snapshot = std::atomic_load_explicit(
        &g_engineI18nSnapshot, std::memory_order_acquire);
    if (!snapshot) {
        auditEngineI18nMiss(source, surface);
        return false;
    }

    auto exact = snapshot->exact.find(source);
    if (exact != snapshot->exact.end()) {
        target = exact->second;
        if (fontPath) *fontPath = snapshot->chineseFontPath;
        uint64_t n = ++g_engineI18nHits;
        if (n <= 10 || n % 100 == 0) {
            std::string escaped = i18nEscapeForLog(source);
            LOGI("[i18n] 精确替换 #%llu surface=%s source=%s",
                 static_cast<unsigned long long>(n), surface,
                 escaped.c_str());
        }
        return true;
    }

    for (const auto& rule : snapshot->prefixes) {
        if (source.size() >= rule.first.size() &&
            source.compare(0, rule.first.size(), rule.first) == 0) {
            target = rule.second;
            target.append(
                source, rule.first.size(), std::string::npos);
            if (fontPath) *fontPath = snapshot->chineseFontPath;
            uint64_t n = ++g_engineI18nHits;
            if (n <= 10 || n % 100 == 0) {
                std::string escaped = i18nEscapeForLog(source);
                LOGI("[i18n] 前缀替换 #%llu surface=%s source=%s",
                     static_cast<unsigned long long>(n), surface,
                     escaped.c_str());
            }
            return true;
        }
    }

    auditEngineI18nMiss(source, surface);
    return false;
}

// 与 cocos2d-x 3.10 的 TTFConfig 字段顺序一致。std::string 由同一 NDK
// libc++ ABI 提供，编译器自动处理 arm64/armv7 的尺寸与对齐。
struct TtfConfigAbi {
    std::string fontFilePath;
    float fontSize;
    int glyphs;
    const char* customGlyphs;
    bool distanceFieldEnabled;
    int outlineSize;
};

struct LabelFontState {
    TtfConfigAbi engineConfig;
    bool hasConfig = false;
    bool forcedChinese = false;
    std::string activeChineseFontPath;
};

static std::mutex g_labelFontMutex;
static std::unordered_map<void*, LabelFontState> g_labelFontStates;

using SetTtfConfigInternalFn =
    bool (*)(void*, const TtfConfigAbi&);
static SetTtfConfigInternalFn setTtfConfigInternalOld = nullptr;

static bool setTtfConfigInternalNew(
        void* self, const TtfConfigAbi& config) {
    {
        std::lock_guard<std::mutex> lock(g_labelFontMutex);
        LabelFontState& state = g_labelFontStates[self];
        state.engineConfig = config;
        state.hasConfig = true;
        state.forcedChinese = false;
        state.activeChineseFontPath.clear();
    }
    return setTtfConfigInternalOld(self, config);
}

static void syncTranslatedLabelFont(
        void* self, bool translated,
        const std::string& chineseFontPath) {
    if (!setTtfConfigInternalOld || !self) return;

    TtfConfigAbi apply;
    bool shouldApply = false;
    {
        std::lock_guard<std::mutex> lock(g_labelFontMutex);
        auto found = g_labelFontStates.find(self);
        if (found == g_labelFontStates.end() ||
            !found->second.hasConfig) {
            return;
        }

        LabelFontState& state = found->second;
        if (translated) {
            std::string desired = chineseFontPath.empty()
                ? std::string(ENGINE_CN_FONT_FALLBACK)
                : chineseFontPath;
            if (state.engineConfig.fontFilePath != desired &&
                (!state.forcedChinese ||
                 state.activeChineseFontPath != desired)) {
                apply = state.engineConfig;
                apply.fontFilePath = desired;
                state.forcedChinese = true;
                state.activeChineseFontPath = desired;
                shouldApply = true;
            }
        } else if (state.forcedChinese) {
            apply = state.engineConfig;
            state.forcedChinese = false;
            state.activeChineseFontPath.clear();
            shouldApply = true;
        }
    }
    if (shouldApply) {
        // 直接调用 shadowhook 返回的原函数，绕过观察 hook，避免把临时中文
        // 配置覆盖成新的“原配置”。
        setTtfConfigInternalOld(self, apply);
    }
}

using LabelDtorFn = void (*)(void*);
static LabelDtorFn labelDtorOld = nullptr;
static void labelDtorNew(void* self) {
    {
        std::lock_guard<std::mutex> lock(g_labelFontMutex);
        g_labelFontStates.erase(self);
    }
    labelDtorOld(self);
}

static thread_local unsigned g_forceChineseFontDepth = 0;
struct ForceChineseFontScope {
    ForceChineseFontScope() { ++g_forceChineseFontDepth; }
    ~ForceChineseFontScope() { --g_forceChineseFontDepth; }
};

using SetStringFn = void (*)(void*, const std::string&);
static SetStringFn labelSetStringOld = nullptr;
static SetStringFn labelAtlasSetStringOld = nullptr;
static SetStringFn menuItemSetStringOld = nullptr;

static void labelSetStringNew(
        void* self, const std::string& text) {
    maybeReloadEngineI18n();
    std::string translated;
    std::string fontPath;
    const bool hit = translateEngineText(
        text, translated, &fontPath, "Label::setString");
    const bool forceFont =
        hit || g_forceChineseFontDepth != 0;
    if (forceFont && fontPath.empty())
        fontPath = currentChineseFontPath();
    syncTranslatedLabelFont(self, forceFont, fontPath);
    labelSetStringOld(self, hit ? translated : text);
}

static void labelAtlasSetStringNew(
        void* self, const std::string& text) {
    maybeReloadEngineI18n();
    std::string translated;
    const bool hit = translateEngineText(
        text, translated, nullptr, "LabelAtlas::setString");
    labelAtlasSetStringOld(self, hit ? translated : text);
}

static void menuItemSetStringNew(
        void* self, const std::string& text) {
    maybeReloadEngineI18n();
    std::string translated;
    const bool hit = translateEngineText(
        text, translated, nullptr, "MenuItemLabel::setString");
    menuItemSetStringOld(self, hit ? translated : text);
}

// LoadingSceneLayerInfo::setText 的符号不含 RK，参数是按值 std::string；
// 旧实现误按 const-ref/void* 转发，在不同 ABI 下并不可靠。
using LoadingSetTextFn = void (*)(void*, std::string);
static LoadingSetTextFn loadingSetTextOld = nullptr;
static void loadingSetTextNew(void* self, std::string text) {
    maybeReloadEngineI18n();
    std::string translated;
    if (translateEngineText(
            text, translated, nullptr,
            "LoadingSceneLayerInfo::setText")) {
        text = std::move(translated);
    }
    loadingSetTextOld(self, std::move(text));
}

// LbUtility::initLabel(Node*, Label*&, const char*, float, Vec2, int,
// Size, Color4B, int)。Color4B 是 RGBA 四字节；旧结构漏掉 alpha。
struct CNVec2    { float x, y; };
struct CNSize    { float w, h; };
struct CNColor4B { unsigned char r, g, b, a; };
static_assert(sizeof(CNColor4B) == 4, "Color4B ABI must be RGBA");

using InitLabelFn = void (*)(void*, void*, const char*, float,
                             CNVec2, int, CNSize,
                             CNColor4B, int);
static InitLabelFn initLabelOld = nullptr;
static void initLabelNew(
        void* node, void* label, const char* text, float f,
        CNVec2 v2, int i1, CNSize sz, CNColor4B c4b, int i2) {
    maybeReloadEngineI18n();
    if (!text) {
        initLabelOld(
            node, label, text, f, v2, i1, sz, c4b, i2);
        return;
    }
    std::string source(text);
    std::string translated;
    std::string fontPath;
    const bool hit = translateEngineText(
        source, translated, &fontPath,
        "LbUtility::initLabel");
    if (!hit) {
        initLabelOld(
            node, label, text, f, v2, i1, sz, c4b, i2);
        return;
    }
    ForceChineseFontScope force;
    initLabelOld(
        node, label, translated.c_str(),
        f, v2, i1, sz, c4b, i2);
}

using CreateWithTtfCfgFn =
    void* (*)(const TtfConfigAbi&, const std::string&,
              int, int);
using CreateWithTtfStrFn =
    void* (*)(const std::string&, const std::string&, float,
              const CNSize&, int, int);
static CreateWithTtfCfgFn createWithTtfCfgOld = nullptr;
static CreateWithTtfStrFn createWithTtfStrOld = nullptr;

static void* createWithTtfCfgNew(
        const TtfConfigAbi& config,
        const std::string& text,
        int hAlignment, int maxLineWidth) {
    maybeReloadEngineI18n();
    std::string translated;
    std::string fontPath;
    const bool hit = translateEngineText(
        text, translated, &fontPath,
        "Label::createWithTTF(config)");
    const bool forceFont =
        hit || g_forceChineseFontDepth != 0;
    if (!forceFont) {
        return createWithTtfCfgOld(
            config, text, hAlignment, maxLineWidth);
    }
    if (fontPath.empty())
        fontPath = currentChineseFontPath();
    TtfConfigAbi local = config;
    local.fontFilePath = fontPath;
    return createWithTtfCfgOld(
        local, hit ? translated : text,
        hAlignment, maxLineWidth);
}

static void* createWithTtfStrNew(
        const std::string& text,
        const std::string& fontFile,
        float fontSize, const CNSize& dimensions,
        int hAlignment, int vAlignment) {
    maybeReloadEngineI18n();
    std::string translated;
    std::string fontPath;
    const bool hit = translateEngineText(
        text, translated, &fontPath,
        "Label::createWithTTF(path)");
    const bool forceFont =
        hit || g_forceChineseFontDepth != 0;
    if (!forceFont) {
        return createWithTtfStrOld(
            text, fontFile, fontSize, dimensions,
            hAlignment, vAlignment);
    }
    if (fontPath.empty())
        fontPath = currentChineseFontPath();
    return createWithTtfStrOld(
        hit ? translated : text, fontPath,
        fontSize, dimensions, hAlignment, vAlignment);
}

template <class HookInstaller>
static void installRuntimeI18nHooks(HookInstaller& H) {
    // 先加载表。缺表时所有 hook 原样放行；热更新放入表后，3 秒 mtime
    // 检查会自动发布新快照。
    loadEngineI18n();

    // 只观察每个 Label 的原始 TTFConfig；不修改传入对象。动态 setString
    // 命中译文时才通过原函数套一份局部中文配置。
    H("_ZN7cocos2d5Label20setTTFConfigInternalERKNS_10_ttfConfigE",
      (void*)setTtfConfigInternalNew,
      (void**)&setTtfConfigInternalOld,
      "i18n-font: observe Label::setTTFConfigInternal");
    H("_ZN7cocos2d5LabelD2Ev",
      (void*)labelDtorNew, (void**)&labelDtorOld,
      "i18n-font: Label::~Label state cleanup");

    // 创建期优先翻译：原引擎从建 FontAtlas 开始就使用中文字体，不会先
    // 生成日文字形缓存、再靠 setString 补救。
    H("_ZN7cocos2d5Label13createWithTTFERKNS_10_ttfConfigERKNSt6__ndk112basic_stringIcNS4_11char_traitsIcEENS4_9allocatorIcEEEENS_14TextHAlignmentEi",
      (void*)createWithTtfCfgNew, (void**)&createWithTtfCfgOld,
      "i18n-font: createWithTTF(config)");
    H("_ZN7cocos2d5Label13createWithTTFERKNSt6__ndk112basic_stringIcNS1_11char_traitsIcEENS1_9allocatorIcEEEES9_fRKNS_4SizeENS_14TextHAlignmentENS_14TextVAlignmentE",
      (void*)createWithTtfStrNew, (void**)&createWithTtfStrOld,
      "i18n-font: createWithTTF(path)");

    H("_ZN7cocos2d5Label9setStringERKNSt6__ndk112basic_stringIcNS1_11char_traitsIcEENS1_9allocatorIcEEEE",
      (void*)labelSetStringNew, (void**)&labelSetStringOld,
      "i18n: Label::setString");
    H("_ZN7cocos2d10LabelAtlas9setStringERKNSt6__ndk112basic_stringIcNS1_11char_traitsIcEENS1_9allocatorIcEEEE",
      (void*)labelAtlasSetStringNew,
      (void**)&labelAtlasSetStringOld,
      "i18n: LabelAtlas::setString");
    H("_ZN7cocos2d13MenuItemLabel9setStringERKNSt6__ndk112basic_stringIcNS1_11char_traitsIcEENS1_9allocatorIcEEEE",
      (void*)menuItemSetStringNew,
      (void**)&menuItemSetStringOld,
      "i18n: MenuItemLabel::setString");
    H("_ZN21LoadingSceneLayerInfo7setTextENSt6__ndk112basic_stringIcNS0_11char_traitsIcEENS0_9allocatorIcEEEE",
      (void*)loadingSetTextNew, (void**)&loadingSetTextOld,
      "i18n: LoadingSceneLayerInfo::setText");
    H("_ZN9LbUtility9initLabelEPN7cocos2d4NodeERPNS0_5LabelEPKcfNS0_4Vec2EiNS0_4SizeENS0_7Color4BEi",
      (void*)initLabelNew, (void**)&initLabelOld,
      "i18n-font: LbUtility::initLabel");
}
