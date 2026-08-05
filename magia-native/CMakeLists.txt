# CMake build for libMagiaLegacy.so —— 归档版老客户端的 native hook 库。
#
# 直接编译 src/MagiaLegacy.cpp。该文件包含已经真机验证成功的运行时字体
# 路径 hook：MTF4a5kp.ttf → TTZhiHeiGB3-W4.ttf；构建期不得生成一个删除
# 该 hook 的替代编译单元，也不得直接替换 APK 内字体文件。

cmake_minimum_required(VERSION 3.18)
project(MagiaLegacy C CXX)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)
set(CMAKE_POSITION_INDEPENDENT_CODE ON)

# shadowhook 的版本脚本同时列出 armv7/arm64 符号。NDK 27 的 lld 默认会对
# 当前架构不存在的符号报错，恢复旧版宽松行为。
add_link_options(-Wl,--undefined-version)

include(FetchContent)
FetchContent_Declare(
    shadowhook
    GIT_REPOSITORY https://github.com/bytedance/android-inline-hook.git
    GIT_TAG        v2.0.1
    GIT_SHALLOW    TRUE
    SOURCE_SUBDIR  shadowhook/src/main/cpp
    # (1) 去掉 Clang 18 下会被 -Werror 击穿的全警告配置。
    # (2) Android 16 上 linker mod 初始化可能失败；本库只同步 hook 已加载的
    #     libmadomagi_native.so，不依赖延迟 hook，因此改为非致命。
    # (3) 上游并未实际使用 CMake 4 特性，降到 runner 可用版本。
    PATCH_COMMAND  sed -i
                   -e "s/-Weverything//g"
                   -e "s/-Werror//g"
                   -e "s/GOTO_END(SHADOWHOOK_ERRNO_INIT_LINKER)/((void)0)/"
                   -e "s/cmake_minimum_required(VERSION 4\\.0\\.2)/cmake_minimum_required(VERSION 3.22)/"
                   shadowhook/src/main/cpp/CMakeLists.txt
                   shadowhook/src/main/cpp/shadowhook.c
)
FetchContent_MakeAvailable(shadowhook)

if(TARGET shadowhook_nothing)
    set_target_properties(shadowhook_nothing PROPERTIES EXCLUDE_FROM_ALL TRUE)
endif()

add_library(MagiaLegacy SHARED
    src/MagiaLegacy.cpp
)

target_include_directories(MagiaLegacy PRIVATE
    ${shadowhook_SOURCE_DIR}/shadowhook/src/main/cpp/include
)

target_link_libraries(MagiaLegacy PRIVATE
    shadowhook
    log
    android
)

target_compile_options(MagiaLegacy PRIVATE
    -fvisibility=hidden
    -ffunction-sections
    -fdata-sections
    -Wno-unused-parameter
    -fstack-protector-strong
    -D_FORTIFY_SOURCE=2
    -Wformat
    -Wformat-security
)

target_link_options(MagiaLegacy PRIVATE
    -Wl,--gc-sections
    -Wl,--exclude-libs,ALL
    -Wl,-z,relro
    -Wl,-z,now
    -Wl,-z,noexecstack
)
