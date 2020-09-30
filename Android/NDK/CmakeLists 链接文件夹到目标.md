## CmakeLists 链接文件夹到目标

### 源代码结构

```
.
├───code/
|   ├──aaa/
|				├──a.h
|				├──a.cpp
|				...
|   ├──bbb/
|				...
|		├──x.h
|		...
├───native-lib.cpp
├───CmakeLists.txt
```

### CmakeLists

```cmake
cmake_minimum_required(VERSION 3.4.1)
file(GLOB YOUR_REF_NAME
				# 二级目录
        "code/*/*.cpp"
        "code/*/*.c"
        "code/*/*.h"
        # 一级目录
        "code/*.c"
        "code/*.cpp"
        "code/*.h"
        )
        
        
add_library( 
				# Sets the name of the library.
        LibName
        # Sets the library as a shared library.
        SHARED
        # Provides a relative path to your source file(s).
        ${YOUR_REF_NAME}
        native-lib.cpp)
```

