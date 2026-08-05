// 引擎硬编码文本翻译：双 ABI 安全实现。
//
// 字体路径仍由 MagiaLegacy.cpp 中已经真机验证的全局 createWithTTF /
// setTTFConfigInternal hook 负责。本文件只替换文本层，不修改字体策略。

static const std::string ENGINE_I18N_PATH =
    "/data/data/io.kamihama.totentanz/files/madomagi/engine_i18n.tsv";

struct EngineI18nSnapshot {
    std::unordered_map<std::string, std::string> exact;
    std::vector<std::pair<std::string, std::string>> prefixes;
    time_t mtime = 0;
};

static std::shared_ptr<const EngineI18nSnapshot> g_engineI18nSnapshot;
static std::mutex g_engineI18nLoadMutex;
static std::atomic<time_t> g_engineI18nLastCheck{0};
static std::atomic<uint64_t> g_engineI18nHits{0};

static std::string i18nUnescape(const std::string& input) {
    std::string output;
    output.reserve(input.size());
    for (size_t i = 0; i < input.size(); ++i) {
        if (input[i] == '\\' && i + 1 < input.size()) {
            char escaped = input[++i];
            if (escaped == 'n') output += '\n';
            else if (escaped == 't') output += '\t';
            else output += escaped;
        } else {
            output += input[i];
        }
    }
    return output;
}

static void loadEngineI18n() {
    std::lock_guard<std::mutex> lock(g_engineI18nLoadMutex);
    FILE* file = fopen(ENGINE_I18N_PATH.c_str(), "rb");
    if (!file) {
        if (std::atomic_load_explicit(
                &g_engineI18nSnapshot, std::memory_order_acquire)) {
            LOGI("[i18n] 表文件暂缺，继续使用已发布快照: %s",
                 ENGINE_I18N_PATH.c_str());
        }
        return;
    }

    std::shared_ptr<EngineI18nSnapshot> fresh =
        std::make_shared<EngineI18nSnapshot>();
    char buffer[8192];
    size_t lines = 0;
    size_t bad = 0;
    while (fgets(buffer, sizeof(buffer), file)) {
        ++lines;
        std::string line(buffer);
        const bool complete = !line.empty()
            && (line.back() == '\n' || feof(file));
        if (!complete) {
            ++bad;
            int ch;
            while ((ch = fgetc(file)) != EOF && ch != '\n') {}
            continue;
        }
        while (!line.empty()
                && (line.back() == '\n' || line.back() == '\r')) {
            line.pop_back();
        }
        if (line.empty() || line[0] == '#') continue;

        size_t tab = line.find('\t');
        if (tab == std::string::npos) {
            ++bad;
            continue;
        }
        if (line[0] == '^') {
            std::string source = i18nUnescape(line.substr(1, tab - 1));
            std::string target = i18nUnescape(line.substr(tab + 1));
            if (source.empty()) ++bad;
            else fresh->prefixes.emplace_back(
                std::move(source), std::move(target));
            continue;
        }
        // @font 属于旧实验指令；字体路线由全局已验证 hook 固定，不在文本表里改。
        if (line.compare(0, tab, "@font") == 0) continue;
        std::string source = i18nUnescape(line.substr(0, tab));
        std::string target = i18nUnescape(line.substr(tab + 1));
        if (source.empty()) ++bad;
        else fresh->exact[std::move(source)] = std::move(target);
    }
    fclose(file);

    std::sort(fresh->prefixes.begin(), fresh->prefixes.end(),
        [](const std::pair<std::string, std::string>& left,
           const std::pair<std::string, std::string>& right) {
            return left.first.size() > right.first.size();
        });
    struct stat status;
    if (::stat(ENGINE_I18N_PATH.c_str(), &status) == 0) {
        fresh->mtime = status.st_mtime;
    }

    const size_t exactCount = fresh->exact.size();
    const size_t prefixCount = fresh->prefixes.size();
    std::shared_ptr<const EngineI18nSnapshot> published = fresh;
    std::atomic_store_explicit(
        &g_engineI18nSnapshot, std::move(published),
        std::memory_order_release);
    LOGI("[i18n] 已原子发布 %zu 条 + %zu 前缀规则（第 %zu 行止，坏行 %zu）",
         exactCount, prefixCount, lines, bad);
}

static void maybeReloadEngineI18n() {
    time_t now = ::time(nullptr);
    time_t last = g_engineI18nLastCheck.load(std::memory_order_relaxed);
    if (now - last < 3) return;
    if (!g_engineI18nLastCheck.compare_exchange_strong(
            last, now, std::memory_order_relaxed)) return;

    struct stat status;
    if (::stat(ENGINE_I18N_PATH.c_str(), &status) != 0) return;
    std::shared_ptr<const EngineI18nSnapshot> snapshot =
        std::atomic_load_explicit(
            &g_engineI18nSnapshot, std::memory_order_acquire);
    if (!snapshot || status.st_mtime != snapshot->mtime) {
        LOGI("[i18n] 检测到表变更，重新加载");
        loadEngineI18n();
    }
}

static bool translateEngineText(
        const std::string& source, std::string& target,
        const char* surface) {
    std::shared_ptr<const EngineI18nSnapshot> snapshot =
        std::atomic_load_explicit(
            &g_engineI18nSnapshot, std::memory_order_acquire);
    if (!snapshot) return false;

    std::unordered_map<std::string, std::string>::const_iterator exact =
        snapshot->exact.find(source);
    if (exact != snapshot->exact.end()) {
        target = exact->second;
        uint64_t number = ++g_engineI18nHits;
        if (number <= 10 || number % 100 == 0) {
            LOGI("[i18n] 精确替换 #%llu surface=%s",
                 static_cast<unsigned long long>(number), surface);
        }
        return true;
    }
    for (const std::pair<std::string, std::string>& rule : snapshot->prefixes) {
        if (source.size() >= rule.first.size()
                && source.compare(0, rule.first.size(), rule.first) == 0) {
            target = rule.second;
            target.append(source, rule.first.size(), std::string::npos);
            uint64_t number = ++g_engineI18nHits;
            if (number <= 10 || number % 100 == 0) {
                LOGI("[i18n] 前缀替换 #%llu surface=%s",
                     static_cast<unsigned long long>(number), surface);
            }
            return true;
        }
    }
    return false;
}

// 所有 std::string 参数都交由 NDK libc++ 编译器生成 ABI；不再手读 +8/+16，
// 也不伪造 long-string，因此 arm64-v8a 与 armeabi-v7a 共用同一源码。
using RuntimeSetStringFn = void (*)(void*, const std::string&);
static RuntimeSetStringFn labelSetStringOld = nullptr;
static RuntimeSetStringFn labelAtlasSetStringOld = nullptr;
static RuntimeSetStringFn menuItemSetStringOld = nullptr;

static void labelSetStringNew(void* self, const std::string& text) {
    maybeReloadEngineI18n();
    std::string translated;
    labelSetStringOld(self,
        translateEngineText(text, translated, "Label::setString")
            ? translated : text);
}

static void labelAtlasSetStringNew(void* self, const std::string& text) {
    maybeReloadEngineI18n();
    std::string translated;
    labelAtlasSetStringOld(self,
        translateEngineText(text, translated, "LabelAtlas::setString")
            ? translated : text);
}

static void menuItemSetStringNew(void* self, const std::string& text) {
    maybeReloadEngineI18n();
    std::string translated;
    menuItemSetStringOld(self,
        translateEngineText(text, translated, "MenuItemLabel::setString")
            ? translated : text);
}

// 符号中没有 RK，参数是按值 std::string；旧实现错误地按 void* / const-ref 转发。
using LoadingSetTextFn = void (*)(void*, std::string);
static LoadingSetTextFn loadingSetTextOld = nullptr;
static void loadingSetTextNew(void* self, std::string text) {
    maybeReloadEngineI18n();
    std::string translated;
    if (translateEngineText(
            text, translated, "LoadingSceneLayerInfo::setText")) {
        text = std::move(translated);
    }
    loadingSetTextOld(self, std::move(text));
}

struct CNVec2 { float x, y; };
struct CNSize { float w, h; };
struct CNColor4B { unsigned char r, g, b, a; };
static_assert(sizeof(CNColor4B) == 4, "cocos2d::Color4B must be RGBA");

using InitLabelFn = void (*)(void*, void*, const char*, float,
                             CNVec2, int, CNSize, CNColor4B, int);
static InitLabelFn initLabelOld = nullptr;
static void initLabelNew(
        void* node, void* label, const char* text, float fontSize,
        CNVec2 position, int alignment, CNSize dimensions,
        CNColor4B color, int outline) {
    maybeReloadEngineI18n();
    if (!text) {
        initLabelOld(node, label, text, fontSize, position,
                     alignment, dimensions, color, outline);
        return;
    }
    std::string source(text);
    std::string translated;
    const char* use = text;
    if (translateEngineText(source, translated, "LbUtility::initLabel")) {
        use = translated.c_str();
    }
    initLabelOld(node, label, use, fontSize, position,
                 alignment, dimensions, color, outline);
}

template <class HookInstaller>
static void installRuntimeTextI18nHooks(HookInstaller& H) {
    loadEngineI18n();
    H("_ZN7cocos2d5Label9setStringERKNSt6__ndk112basic_stringIcNS1_11char_traitsIcEENS1_9allocatorIcEEEE",
      (void*)labelSetStringNew, (void**)&labelSetStringOld,
      "i18n: Label::setString ABI-safe");
    H("_ZN7cocos2d10LabelAtlas9setStringERKNSt6__ndk112basic_stringIcNS1_11char_traitsIcEENS1_9allocatorIcEEEE",
      (void*)labelAtlasSetStringNew, (void**)&labelAtlasSetStringOld,
      "i18n: LabelAtlas::setString ABI-safe");
    H("_ZN7cocos2d13MenuItemLabel9setStringERKNSt6__ndk112basic_stringIcNS1_11char_traitsIcEENS1_9allocatorIcEEEE",
      (void*)menuItemSetStringNew, (void**)&menuItemSetStringOld,
      "i18n: MenuItemLabel::setString ABI-safe");
    H("_ZN21LoadingSceneLayerInfo7setTextENSt6__ndk112basic_stringIcNS0_11char_traitsIcEENS0_9allocatorIcEEEE",
      (void*)loadingSetTextNew, (void**)&loadingSetTextOld,
      "i18n: LoadingSceneLayerInfo::setText by-value");
    H("_ZN9LbUtility9initLabelEPN7cocos2d4NodeERPNS0_5LabelEPKcfNS0_4Vec2EiNS0_4SizeENS0_7Color4BEi",
      (void*)initLabelNew, (void**)&initLabelOld,
      "i18n: LbUtility::initLabel RGBA");
}
