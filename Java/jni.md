```c
jclass cls = env->FindClass("com/miaxis/livedetection/jni/vo/FaceInfo");
//里面字符串 不能加类型 ( L***; )
jfieldID fld = env->GetFieldID(faceInfoClass, "area", "Landroid/graphics/Rect;");
//里面字符串 加不加类型都可以 ( L***; )


```

