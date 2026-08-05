// 仅供已经真机验证成功的全局字体路径 hook 复用。
//
// RuntimeTextI18n.inc 的文本翻译不使用本结构；文本入口已经全部改为真实
// std::string 函数签名。这里暂时保持原 arm64 libc++ 读取方式，避免在同一轮
// 同时改变已验证字体路线。armeabi-v7a 仍必须单独真机验证，之后再把字体路径
// 读取也改为该 ABI 的编译器类型。
struct NdkStrView { const char* data; size_t size; };
static NdkStrView ndkStrRead(const void* strObj) {
    const unsigned char* value = static_cast<const unsigned char*>(strObj);
    if (value[0] & 1) {
        return {
            *reinterpret_cast<const char* const*>(value + 16),
            *reinterpret_cast<const size_t*>(value + 8)
        };
    }
    return {
        reinterpret_cast<const char*>(value + 1),
        static_cast<size_t>(value[0] >> 1)
    };
}
