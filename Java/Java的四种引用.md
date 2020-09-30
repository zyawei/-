# Java的四种引用

- 强引用
- 软引用
- 弱引用
- 虚引用

## 强引用

不会被回收，内存不够时抛出OutOfMemoryError

## 软引用

内存不够时会被回收

## 弱引用

GC时会被回收

## 虚引用

不会影响对象的回收，仅再被回收时添加到它对象的RefenceQueue中。

## ReferenceQueue

与SoftReference,WeekReference,PhantomReference配合使用，当Reference所引用的对象将要被回收时，此Reference对象会被添加到这个队列中。

## WeakHashMap

当一个键对象被垃圾回收器回收时，那么相应的值对象的引用会从Map中删除。



##  思考

### 软引用存在的意义

构建缓存

### 弱引用存在的意义

一个对象被弱引用，GC时会回收它

一个对象被==null，GC时会回收它

看着弱引用好像没用。区别在于弱引用相对于==nul，你还可以得到它

## 虚引用存在的意义

想以==null的方式管理它，但是又想知道何时被回收。

​	

https://www.cnblogs.com/huajiezh/p/5835618.html