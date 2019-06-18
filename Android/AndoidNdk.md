## 问题

```cmake
${ANDROID_STL} is no longer supported. Please switch to either c++_shared or \
c++_static.
```



## 原因

Android NDK r18b 移除了`gnustl` ` gabi++` `stlport`，[链接](https://developer.android.google.cn/ndk/downloads/revision_history)

```
gnustl, gabi++, and stlport have been removed.
```



## 解决办法

1,手动下载 Android NDK r17c，[链接](https://developer.android.google.cn/ndk/downloads/older_releases.html#ndk-17c-downloads)

2,解压，使用新的 DNK r17c路径

