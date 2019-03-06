# CountDownLatch 用法及源码

## 功能

给定一个初始值，每次减一，此值为零时，等待在此的所有线程被唤醒。

**好比：**有一个门上了N把锁，M个人在门外等着。锁一旦打开所有人都可以进来

## 用处举例

###  让多个线程同时启动（一把锁，M个人等待）

如果直接启动N个线程，毕竟代码有先后执行顺序，不会是真正的同时开启。
** 使用`CountDownLatch`的方式：

```java
public void test() {
   	final CountDownLatch latch = new CountDownLatch(1);
    Thread a = new Thread(new Runnable(){
        @Override
        public void run(){
			System.out.println("a start");
			latch.await();    
            System.out.println("a go");
        }
    });
    Thread b = new Thread(new Runnable(){
        @Override
        public void run(){
        	System.out.println("b start");
 			latch.await();    
            System.out.println("b go");
        }
    });
    a.start();
    b.start();
    // 为什么要sleep ?
    Thread.sleep(100);
    latch.countDown();   
}
```

** 使用`wait/notify`的方式:**

```java
public void test2() throw InterruptedException{
   	final Object lock = new Object();
    Thread a = new Thread(new Runnable(){
        @Override
        public void run(){
        	System.out.println("a start ");
            synchronized(lock){
                lock.wait();    
            }
            System.out.println("a go");
        }
    });
    Thread b = new Thread(new Runnable(){
        @Override
        public void run(){
            synchronized(lock){
            	System.out.println("b start ");
				lock.wait();
                System.out.println("b go ");
            }
        }
    });
    a.start();
    b.start();
	// 为什么要sleep ?
    Thread.sleep(100);
    synchronized(lock){
        lock.notifyAll();
    }
}
```
- `Thread.sleep(100)` 是因为代码执行到这里的时候线程a与b可能还没启动，达不到想要的效果。
- 移除`Thread.sleep(100)`，代码1依旧能执行，但是代码2的线程会一直等待（因为notify执行在wait之前）
- 代码1 相当于代码2加计数器。

### 等待多个线程的执行结果 （多把锁，一个人在等待）

下面的代码来自于`CountDownLatch`的文档：

```java
 class Driver2 { // ...
   void main() throws InterruptedException {
     CountDownLatch doneSignal = new CountDownLatch(N);
     Executor e = ...

     for (int i = 0; i < N; ++i) // create and start threads
       e.execute(new WorkerRunnable(doneSignal, i));

     doneSignal.await();           // wait for all to finish
   }
 }

 class WorkerRunnable implements Runnable {
   private final CountDownLatch doneSignal;
   private final int i;
   WorkerRunnable(CountDownLatch doneSignal, int i) {
     this.doneSignal = doneSignal;
     this.i = i;
   }
   public void run() {
     try {
       doWork(i);
       doneSignal.countDown();
     } catch (InterruptedException ex) {} // return;
   }

   void doWork() { ... }
 }
```

### 让多个线程同时启动并等待结果

下面的代码来自于`CountDownLatch`的文档：

```java
class Driver { // ...
   void main() throws InterruptedException {
     CountDownLatch startSignal = new CountDownLatch(1);
     CountDownLatch doneSignal = new CountDownLatch(N);

     for (int i = 0; i < N; ++i) // create and start threads
       new Thread(new Worker(startSignal, doneSignal)).start();

     doSomethingElse();            // don't let run yet
     startSignal.countDown();      // let all threads proceed
     doSomethingElse();
     doneSignal.await();           // wait for all to finish
   }
 }

 class Worker implements Runnable {
   private final CountDownLatch startSignal;
   private final CountDownLatch doneSignal;
   Worker(CountDownLatch startSignal, CountDownLatch doneSignal) {
     this.startSignal = startSignal;
     this.doneSignal = doneSignal;
   }
   public void run() {
     try {
       startSignal.await();
       doWork();
       doneSignal.countDown();
     } catch (InterruptedException ex) {} // return;
   }

   void doWork() { ... }
 }
```

**额，这个就相当于把前面两个加起来了吧。。。**


