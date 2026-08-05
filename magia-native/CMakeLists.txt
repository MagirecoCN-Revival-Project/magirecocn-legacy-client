# CMake build for libMagiaLegacy.so —— 归档版老客户端的 native hook 库。
#
# 本库取代无源码的 libuwasa.so / libcn_hook.so。原始逆向实现保留在
# src/MagiaLegacy.cpp 供审计；构建时由 prepare-native-runtime-i18n.py 生成
# 实际编译单元，移除危险的全局字体路径改写并接入局部译文字形策略。

cmake_minimum_required(VERSION 3.18)
project(MagiaLegacy C CXX)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)
set(CMAKE_POSITION_INDEPENDENT_CODE ON)

# shadowhook 的版本脚本同时列出 armv7/arm64 符号。NDK 27 的 lld 默认会对
# 当前架构不存在的版本脚本符号报错，恢复旧版宽松行为。
add_link_options(-Wl,--undefined-version)

include(FetchContent)
FetchContent_Declare(
    shadowhook
    GIT_REPOSITORY https://github.com/bytedance/android-inline-hook.git
    GIT_TAG        v2.0.1
    GIT_SHALLOW    TRUE
    SOURCE_SUBDIR  shadowhook/src/main/cpp
    # (1) 去掉会被 Clang 18 新警告击穿的 -Weverything/-Werror。
    # (2) linker mod 在 Android 16 上可能初始化失败；本库只同步 hook 已加载的
    #     libmadomagi_native.so，不依赖延迟 hook，因此将该失败改为非致命。
    # (3) 上游写了并未实际需要的 CMake 4.0.2，降到 runner 可用的 3.22。
    PATCH_COMMAND  sed -i
                   -e "s/-Weverything//g"
                   -e "s/-Werror//g"
                   -e "s/GOTO_END(SHADOWHOOK_ERRNO_INIT_LINKER)/((void)0)/"
                   -e "s/cmake_minimum_required(VERSION 4\\.0\\.2)/cmake_minimum_required(VERSION 3.22)/"
                   shadowhook/src/main/cpp/CMakeLists.txt
                   shadowhook/src/main/cpp/shadowhook.c
)
FetchContent_MakeAvailable(shadowhook)

# shadowhook_nothing 是上游 Gradle 占位 so；本项目不需要，避免 NDK 27 在
# -nostdlib 占位目标上浪费构建时间或失败。
if(TARGET shadowhook_nothing)
    set_target_properties(shadowhook_nothing PROPERTIES EXCLUDE_FROM_ALL TRUE)
endif()

find_package(Python3 REQUIRED COMPONENTS Interpreter)

set(MAGIA_LEGACY_BASELINE
    "${CMAKE_CURRENT_SOURCE_DIR}/src/MagiaLegacy.cpp")
set(MAGIA_LEGACY_RUNTIME_INCLUDE
    "${CMAKE_CURRENT_SOURCE_DIR}/src/RuntimeI18n.inc")
set(MAGIA_LEGACY_GENERATOR
    "${CMAKE_CURRENT_SOURCE_DIR}/../tools/prepare-native-runtime-i18n.py")
set(MAGIA_LEGACY_GENERATED_DIR
    "${CMAKE_CURRENT_BINARY_DIR}/generated")
set(MAGIA_LEGACY_GENERATED_CPP
    "${MAGIA_LEGACY_GENERATED_DIR}/MagiaLegacy.cpp")
set(MAGIA_LEGACY_GENERATED_REPORT
    "${MAGIA_LEGACY_GENERATED_DIR}/runtime-i18n-report.json")

add_custom_command(
    OUTPUT "${MAGIA_LEGACY_GENERATED_CPP}"
    BYPRODUCTS "${MAGIA_LEGACY_GENERATED_REPORT}"
    COMMAND "${CMAKE_COMMAND}" -E make_directory
            "${MAGIA_LEGACY_GENERATED_DIR}"
    COMMAND "${Python3_EXECUTABLE}" "${MAGIA_LEGACY_GENERATOR}"
            --source "${MAGIA_LEGACY_BASELINE}"
            --runtime-include "${MAGIA_LEGACY_RUNTIME_INCLUDE}"
            --output "${MAGIA_LEGACY_GENERATED_CPP}"
            --report "${MAGIA_LEGACY_GENERATED_REPORT}"
    DEPENDS
            "${MAGIA_LEGACY_BASELINE}"
            "${MAGIA_LEGACY_RUNTIME_INCLUDE}"
            "${MAGIA_LEGACY_GENERATOR}"
    COMMENT "Generating audited MagiaLegacy runtime-i18n source"
    VERBATIM
)
set_source_files_properties(
    "${MAGIA_LEGACY_GENERATED_CPP}" PROPERTIES GENERATED TRUE)

add_library(MagiaLegacy SHARED
    "${MAGIA_LEGACY_GENERATED_CPP}"
)

target_include_directories(MagiaLegacy PRIVATE
    "${CMAKE_CURRENT_SOURCE_DIR}/src"
    "${shadowhook_SOURCE_DIR}/shadowhook/src/main/cpp/include"
)

target_link_libraries(MagiaLegacy PRIVATE
    shadowhook
    log
    android
)

# 编译期与链接期加固；CI 不再额外 strip。
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
