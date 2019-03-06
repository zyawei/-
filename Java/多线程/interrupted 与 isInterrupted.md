# Java Thread interrupt() 与 isInterrupted()

## Java源码

### Android 里面的源码 

```java
public void interrupt(){
    if (this != Thread.currentThread())
            checkAccess();

        synchronized (blockerLock) {
            Interruptible b = blocker;
            if (b != null) {
                nativeInterrupt();
                b.interrupt(this);
                return;
            }
        }
        nativeInterrupt();
}

@FastNative
public static native boolean interrupted();

@FastNative
public native boolean isInterrupted();
@FastNative
private native void nativeInterrupt();

```

### OpenJdk1.7 里面的源码

```java
public void interrupt() {
    if (this != Thread.currentThread())
        checkAccess();

    synchronized (blockerLock) {
        Interruptible b = blocker;
        if (b != null) {
            interrupt0();           // Just to set the interrupt flag
            b.interrupt(this);
            return;
        }
    }
    interrupt0();
}
public static boolean interrupted() {
    return currentThread().isInterrupted(true);
}
public boolean isInterrupted() {
    return isInterrupted(false);
}
private native boolean isInterrupted(boolean ClearInterrupted);
private native void interrupt0();


```

**1、Jdk 里面的源码和Android 里面的源码是不一样的，但是文档是一样的 ！**

**2、从Android的源码里面只能从文档看出来 `interrupted()`和`isInterrupted()`的区别，但是看Java的源码，直接就能看出来区别了。**

## 方法说明

### interrupt()

a、如果线程堵塞在object.wait、Thread.join和Thread.sleep，将会清除线程的中断状态，并抛出InterruptedException;(中断状态先设置为true，抛异常，再把中断状态设置为false)

b、如果线程堵塞在java.nio.channels.InterruptibleChannel的IO上，Channel将会被关闭，线程被置为中断状态，并抛出java.nio.channels.ClosedByInterruptException；

c、如果线程堵塞在java.nio.channels.Selector上，线程被置为中断状态，select方法会马上返回，类似调用wakeup的效果；

d、如果不是以上三种情况，thread.interrupt()方法仅仅是设置线程的中断状态为true。

**总结：设置中断标志位 和 抛异常**

### interrupted()

获取当前线程是否中断，并清除中断标志位

### isInterrupted()

获取当前线程是否中断

## OpenJDK1.7 C源码

**thread.cpp**

```c++
void Thread::interrupt(Thread* thread) {
  trace("interrupt", thread);
  debug_only(check_for_dangling_thread_pointer(thread);)
  os::interrupt(thread);
}

bool Thread::is_interrupted(Thread* thread, bool clear_interrupted) {
  trace("is_interrupted", thread);
  debug_only(check_for_dangling_thread_pointer(thread);)
  // Note:  If clear_interrupted==false, this simply fetches and
  // returns the value of the field osthread()->interrupted().
  return os::is_interrupted(thread, clear_interrupted);
}

```

**os_linux.cpp**

```c++
////////////////////////////////////////////////////////////////////////////////
// interrupt support

void os::interrupt(Thread* thread) {
  assert(Thread::current() == thread || Threads_lock->owned_by_self(),
    "possibility of dangling Thread pointer");

  OSThread* osthread = thread->osthread();

  if (!osthread->interrupted()) {
    osthread->set_interrupted(true);
    // More than one thread can get here with the same value of osthread,
    // resulting in multiple notifications.  We do, however, want the store
    // to interrupted() to be visible to other threads before we execute unpark().
    OrderAccess::fence();
    ParkEvent * const slp = thread->_SleepEvent ;
    if (slp != NULL) slp->unpark() ;
  }

  // For JSR166. Unpark even if interrupt status already was set
  if (thread->is_Java_thread())
    ((JavaThread*)thread)->parker()->unpark();

  ParkEvent * ev = thread->_ParkEvent ;
  if (ev != NULL) ev->unpark() ;

}

bool os::is_interrupted(Thread* thread, bool clear_interrupted) {
  assert(Thread::current() == thread || Threads_lock->owned_by_self(),
    "possibility of dangling Thread pointer");

  OSThread* osthread = thread->osthread();

  bool interrupted = osthread->interrupted();

  if (interrupted && clear_interrupted) {
    osthread->set_interrupted(false);
    // consider thread->_SleepEvent->reset() ... optional optimization
  }

  return interrupted;
}

```

**osThread.hpp**

```c++
 volatile bool interrupted() const                 { return _interrupted != 0; }
 void set_interrupted(bool z)                      { _interrupted = z ? 1 : 0; }
```



