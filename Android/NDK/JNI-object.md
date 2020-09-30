# JNI



## 创建对象



###  1、第1种创建Java对象的方法

```c

    // java中java.util.Date的 class对象  
    jclass clazzDate = env->FindClass("java/util/Date");
    // java.util.Date的默认构造函数
    jmethodID mthodidDataConstructor = env->GetMethodID(clazzDate, "<init>", "()V");
    // 使用java.util.Date的默认构造函数，来创建java.util.Date对象
    jobject objDate = env->NewObject(clazzDate, mthodidDataConstructor);
```

### 2、第2种创建Java对象的方法

```java
jclass clazzInteger = env->FindClass("java/lang/Integer");
jobject jobjInteger = env->AllocObject(clazzInteger);
```

