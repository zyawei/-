# ANRs

## 可能的原因
- Slow code on the main thread
- IO on the main thread
- Lock contention
- Deadlocks



## 分析ANR trace.txt

## 文件位置

- `/data/anr/traces.txt ` old OS
- `/data/anr/anr_*` newer OS

```bash
adb root
adb shell ls /data/anr
adb pull /data/anr/<filename>
```



### 部分名词
```java
DALVIK THREADS:
(mutexes: tll=0 tsl=0 tscl=0 ghl=0)
```

- ttl - thread list lock
- tsl：thread suspend lock
- tscl：thread suspend count lock
- ghl：gc heap lock

```java
"main" prio=5 tid=1 NATIVE
```

- thread name
- prio 优先级
- thread Id
- thread status

### 系统线程

- **main**：主线程ActivityThread，事件分发、UI更新。

- **JDWP** Java Debug Wire Protocol 调试用的服务线程;

- **Signal Catcher** 捕获并处理Linux的信号。

- **GC** Garbage Collector 虚拟机的垃圾收集线程

- **Compiler**  这个线程就是JIT即使编译器线程

- **ReferenceQueueDaemon** 引用队列守护线程 。在创建引用对象的时候，可以关联一个队列。当被引用对象引用的对象被GC回收的时候，被引用对象就会被加入到其创建时关联的队列去。这个加入队列的操作就是由ReferenceQueueDaemon守护线程来完成的。这样应用程序就可以知道哪些被引用的对象已经被回收了。

- **FinalizerDaemon** 析构守护线程。对于重写了成员函数finalize的对象，它们被GC决定回收时，并没有马上被回收，而是被放入到一个队列中，等待FinalizerDaemon守护线程去调用它们的成员函数finalize，然后再被回收。

- **FinalizerWatchdogDaemon** 析构监护守护线程。用来监控FinalizerDaemon线程的执行。一旦检测那些重写了finalize的对象在执行成员函数finalize时超出一定时间，那么就会退出VM。

- **Binder_1，Binder_2 ** 应用使用binder进行进程间通信时使用。

  



### Thread States

| Java Status | Vm Status | 说明                    |
| ----------- | ------------ | ----------------------- |
| NEW         | INITIALIZING |                         |
|             | STARTING     |                         |
| RUNNABLE    | RUNNING      | dump..        |
|             | SUSPENDED    | dump all ..（debug）      |
|             | NATIVE       | 本地代码&不是Jni代码       |
| BLOCKED     | MONITOR      | 等待锁(synchronized)	 |
| WAITING     | WAIT 		 | wait()            	   |
|             | VMWAIT       | 等待资源(Compiler,GC，) |
| TIMED_WAITING | TIMED_WAIT | wait(100),sleep(100),join(100) |
| TERMINATED  | ZOMBIE       |                         |

- INITIALIZING - not yet running.
- STARTING - not yet running, but almost there.
- ZOMBIE - deceased (you shouldn't see this).
- RUNNING (a/k/a RUNNABLE) - thread is actively running. The VM has to suspend all threads to do the stack dump, so you generally won't see this for any thread other than the one that is dumping the stack.
- WAIT - the thread called wait(), and is patiently waiting.
- TIMED_WAIT - thread called wait(), with a timeout. (Thread.sleep() is implemented as a timed wait.)
- MONITOR - thread is blocked on a monitor lock, i.e. it's stuck trying to enter a "synchronized" block.
- NATIVE - thread is executing in native code. The VM doesn't suspend threads in native code unless they make a JNI call (at which point they transition to RUNNING, and then immediately to SUSPENDED).
- VMWAIT - thread is blocked acquiring a VM resource, like an internal mutex. Or maybe waiting for something to do (e.g. the Compiler and GC threads).
- SUSPENDED - thread was runnable, but has been suspended. As noted earlier, the stack dumper likes to suspend all threads before traversing their stacks, so your busy threads will generally show up this way. (In older releases, this state did not exist; "suspended" used to be "RUNNING with a nonzero sCount".)
