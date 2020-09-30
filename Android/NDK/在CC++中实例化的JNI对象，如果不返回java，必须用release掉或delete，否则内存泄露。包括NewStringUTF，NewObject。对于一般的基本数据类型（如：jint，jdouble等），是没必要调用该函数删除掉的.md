在C/C++中实例化的JNI对象，如果不返回java，必须用release掉或delete，否则内存泄露。包括NewStringUTF，NewObject。对于一般的基本数据类型（如：jint，jdouble等），是没必要调用该函数删除掉的。如果返回java不必delete，java会自己回收。


作者：大头呆
链接：https://juejin.im/post/6844903743352209422
来源：掘金
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。