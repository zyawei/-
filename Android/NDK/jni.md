###  Log

```c
//
// Created by zhang.yw on 2018/11/26.
//
#include <android/log.h>
#define TAG "MX-jni" // 这个是自定义的LOG的标识
#define SHOW_LOG false
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,TAG ,__VA_ARGS__) // 定义LOGD类型
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,TAG ,__VA_ARGS__) // 定义LOGI类型
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN,TAG ,__VA_ARGS__) // 定义LOGW类型
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,TAG ,__VA_ARGS__) // 定义LOGE类型
#define LOGV(...) __android_log_print(ANDROID_LOG_VERBOSE,TAG ,__VA_ARGS__) // 定义LOGE类型

```



### 类型


```c
jclass cls = env->FindClass("com/miaxis/livedetection/jni/vo/FaceInfo");
//里面字符串 不能加类型 ( L***; )
jfieldID fld = env->GetFieldID(faceInfoClass, "area", "Landroid/graphics/Rect;");
//里面字符串 加不加类型都可以 ( L***; )


```



CMakeLists

```cmake
set(pathToProject my_path)
# 链接动态库/静态库
add_library(libcrypto SHARED IMPORTED)
set_target_properties(libcrypto
        PROPERTIES IMPORTED_LOCATION
        ${pathToProject}/app/src/main/jniLibs/${ANDROID_ABI}/libcrypto.so)

add_library(libssl SHARED IMPORTED)
set_target_properties(libssl
        PROPERTIES IMPORTED_LOCATION
        ${pathToProject}/app/src/main/jniLibs/${ANDROID_ABI}/libssl.so)、
# 添加所有文件        
file(GLOB LicenseCode
        "LicenseCode/*/*/*/*.cpp"
        "LicenseCode/*/*/*/*.c"
        "LicenseCode/*/*/*/*.h"

        "LicenseCode/*/*/*.cpp"
        "LicenseCode/*/*/*.c"
        "LicenseCode/*/*/*.h"


        "LicenseCode/*/*.cpp"
        "LicenseCode/*/*.c"
        "LicenseCode/*/*.h"

        "LicenseCode/*.c"
        "LicenseCode/*.cpp"
        )
## 引用上面指定的文件        
add_library( # Sets the name of the library.
        MXLicenseAPI
        # Sets the library as a shared library.
        SHARED
        # Provides a relative path to your source file(s).
        ${LicenseCode}
        native-lib.cpp)
```

### unsigned long int 

```c

// 64位操作系统下是8个字节，32操作系统下是4个字节
typedef unsigned long int UINT4;
// 都是4个字节
typedef unsigned int UINT4;
```

### Android NDK

`ndk-build`自动去需找`jni/Application.mk`,`jni/Application.mk`里面指定了`Android.mk`的位置;

