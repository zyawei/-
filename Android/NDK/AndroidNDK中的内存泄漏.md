# NewByteArray 导致的内存泄漏

## Android4.4中的内存泄漏

最近在Android4.4的环境下碰到了一次内存溢出，经过一番测试发现是下面这个方法发生内存泄漏导致的；

```c
extern "C"
JNIEXPORT jbyteArray JNICALL
Java_org_zz_jni_image_MXPictureUtils_rotateYuv180_1(JNIEnv *env, jclass type, jbyteArray data_,
                                                  jint width, jint height) {
    jbyte *data = env->GetByteArrayElements(data_, NULL);
    int dataLength = env->GetArrayLength(data_);

    jbyteArray yue = env->NewByteArray(dataLength);
    jbyte *yueBytes = env->GetByteArrayElements(yue, NULL);

    int count = 0;
    for (int i = width * height - 1; i >= 0; i--) {
        yueBytes[count] = data[i];
        count++;
    }
    for (int i = width * height * 3 / 2 - 1; i >= width * height; i -= 2) {
        yueBytes[count++] = data[i - 1];
        yueBytes[count++] = data[i];
    }
    //env->ReleaseByteArrayElements(yue, yueBytes, 0);
    env->ReleaseByteArrayElements(data_, data, 0);
    //env->DeleteLocalRef(yue);
    return yue;
}

```

这是一个旋转YUV图像数据的本地方法，反复观察感觉只可能是`jbyteArray yue = env->NewByteArray(dataLength);`

但是这里不应该出问题：

- 由`env->NewByteArray(...)`申请的内存应该在合适的时候调用`env->DeleteLocalRef(...)`来释放本地引用
- 如果作为Java返回值，则不需要；应该由调用该方法的Java对象负责释放引用；

虽然如此，还是强行尝试一波：

```c
JNIEXPORT jbyteArray JNICALL
Java_org_zz_jni_image_MXPictureUtils_rotateYuv180_1(JNIEnv *env, jclass type, jbyteArray data_,
    //...
    env->DeleteLocalRef(yue);
    return yue;
}

```

结果悲惨，蹦了；死马当活马医，尝试`env->ReleaseByteArrayElements(yue, yueBytes, 0);`

```c
JNIEXPORT jbyteArray JNICALL
Java_org_zz_jni_image_MXPictureUtils_rotateYuv180_1(JNIEnv *env, jclass type, jbyteArray data_,
                                                  jint width, jint height) {
    // .. 
    env->ReleaseByteArrayElements(yue, yueBytes, 0);
    return yue;
}
```

竟然不泄漏了！！！

**但是运行在Android5.0上即使没任何释放动作，也不会导致内存泄漏，猜测可能是Dalvik虚拟机有BUG吧，咱也不敢乱说呀；**

## 这次BUG中学习到的东西

### 哪些需要释放

- 不需要:基本数据类型
- 需要：应用类型（jstring，jobject ，jobjectArray，jintArray ，jclass ，jmethodID）

顺手解决了之前写的隐藏BUG，就是创建的jclass 和jmethodID，都没释放！！；

### 如何释放

**回收NewByteArray**

```c
jbyteArray array = jnienv->NewByteArray(**);
jnienv->DeleteLocalRef(array);
```

- 由`env->NewByteArray(...)`申请的内存应该在合适的时候调用`env->DeleteLocalRef(...)`来释放本地引用
- 如果作为Java返回值，则不需要；应该由调用该方法的Java对象负责释放引用；

**GetByteArrayElements**

````c
jbyte *data = env->GetByteArrayElements(data_, NULL);
env->ReleaseByteArrayElements(data_, data, 0);
````

- GetByteArrayElements 中的第二个参数:

  第二个参数返回的数组指针是原始数组，还是拷贝原始数据到临时缓冲区的指针，如果是 JNI_TRUE：表示临时缓冲区数组指针，JNI_FALSE：表示临时原始数组指针。开发当中，我们并不关心它从哪里返回的数组指针，这个参数填 NULL 即可，但在获取到的指针必须做校验。

- ReleaseByteArrayElements 中的第三个参数:

  0，释放data所指向的内存，并把临时缓冲区数据写入JAVA数组。

  JNI_COMMIT，不释放，写入JAVA数组；

  JNI_ABORT，释放，不写入JAVA数组；（当JNI_TRUE时，即此array仅是副本，可以使用，否是数据就丢失了）

## TODO

1.Java 内存与native 内存的互操作

2.通过jni传递数组的时候，内存都有哪些变化。

**期待完成日期：5月底前**

**方式：阅读Java虚拟机**



