# libMagiaLegacy.so：以 MagiaLegacy.cpp 为审计基线，构建时只替换不安全文本层。
# 已经真机验证成功的全局字体路径 hook 必须保留；生成器会逐项断言。
cmake_minimum_required(VERSION 3.18)
project(MagiaLegacy C CXX)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)
set(CMAKE_POSITION_INDEPENDENT_CODE ON)
add_link_options(-Wl,--undefined-version)

include(FetchContent)
FetchContent_Declare(
    shadowhook
    GIT_REPOSITORY https://github.com/bytedance/android-inline-hook.git
    GIT_TAG        v2.0.1
    GIT_SHALLOW    TRUE
    SOURCE_SUBDIR  shadowhook/src/main/cpp
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

find_package(Python3 REQUIRED COMPONENTS Interpreter)

set(BASELINE_CPP "${CMAKE_CURRENT_SOURCE_DIR}/src/MagiaLegacy.cpp")
set(TEXT_INCLUDE "${CMAKE_CURRENT_SOURCE_DIR}/src/RuntimeTextI18n.inc")
set(FONT_COMPAT_INCLUDE "${CMAKE_CURRENT_SOURCE_DIR}/src/RuntimeFontCompat.inc")
set(GENERATOR "${CMAKE_CURRENT_SOURCE_DIR}/../tools/prepare-native-text-i18n.py")
set(GENERATED_DIR "${CMAKE_CURRENT_BINARY_DIR}/generated")
set(GENERATED_CPP "${GENERATED_DIR}/MagiaLegacy.cpp")
set(GENERATED_REPORT "${GENERATED_DIR}/native-text-i18n-report.json")

add_custom_command(
    OUTPUT "${GENERATED_CPP}"
    BYPRODUCTS "${GENERATED_REPORT}"
    COMMAND "${CMAKE_COMMAND}" -E make_directory "${GENERATED_DIR}"
    COMMAND "${Python3_EXECUTABLE}" "${GENERATOR}"
            --source "${BASELINE_CPP}"
            --output "${GENERATED_CPP}"
            --report "${GENERATED_REPORT}"
    DEPENDS
            "${BASELINE_CPP}"
            "${TEXT_INCLUDE}"
            "${FONT_COMPAT_INCLUDE}"
            "${GENERATOR}"
    COMMENT "Generating ABI-safe text hooks while preserving verified font-path hooks"
    VERBATIM
)
set_source_files_properties("${GENERATED_CPP}" PROPERTIES GENERATED TRUE)

add_library(MagiaLegacy SHARED "${GENERATED_CPP}")
target_include_directories(MagiaLegacy PRIVATE
    "${CMAKE_CURRENT_SOURCE_DIR}/src"
    "${shadowhook_SOURCE_DIR}/shadowhook/src/main/cpp/include"
)
target_link_libraries(MagiaLegacy PRIVATE shadowhook log android)

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
