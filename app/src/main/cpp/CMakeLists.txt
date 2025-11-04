cmake_minimum_required(VERSION 3.10)
project(Mverify)

# 设置C++标准
set(CMAKE_CXX_STANDARD 14)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

# 源文件
set(SOURCES
        md5.cpp
        cJSON.c
        base64.cpp
)

# JNI源文件
set(JNI_SOURCES
        ${SOURCES}
        Mverify.cpp
)

# 查找log库
find_library(log-lib log)

# 创建共享库
add_library(Mverify SHARED ${JNI_SOURCES})

# 链接log库
target_link_libraries(Mverify ${log-lib})

# 设置输出名称
set_target_properties(Mverify PROPERTIES
        OUTPUT_NAME "Mverify"
        PREFIX "lib"
)

# 编译选项
if (CMAKE_BUILD_TYPE STREQUAL "Release")
    target_compile_options(Mverify PRIVATE -O3 -DNDEBUG)
else ()
    target_compile_options(Mverify PRIVATE -g -O0)
endif ()