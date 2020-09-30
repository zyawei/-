# ADB

VSS表示Virtual Set Size 虚拟耗用内存（包含共享库占用的内存），表示进程可以访问的地址空间。

RSS表示Resident Set Size 实际使用物理内存（包含共享库占用的内存）

PSS表示Proportional Set Size 实际使用的物理内存（比例分配共享库占用的内存）

USS表示Unique Set Size 进程独自占用的物理内存（不包含共享库占用的内存）内存泄漏分析





### adb shell getprop

```bash

[dalvik.vm.dexopt-flags]: m=y
[dalvik.vm.heapgrowthlimit]: 96m
[dalvik.vm.heapmaxfree]: 8m
[dalvik.vm.heapminfree]: 2m
[dalvik.vm.heapsize]: 384m
[dalvik.vm.heapstartsize]: 8m
[dalvik.vm.heaptargetutilization]: 0.75
[dalvik.vm.stack-trace-file]: /data/anr/traces.txt
[persist.sys.dalvik.vm.lib]: libdvm.so
```

### adb shell procrank 

```bash
# 每个应用使用的内存
  PID       Vss      Rss      Pss      Uss  cmdline
  645   726400K   54732K   33715K   30888K  com.android.systemui
  791   750116K   48260K   27802K   25252K  com.android.launcher
  518   717524K   45012K   26924K   21784K  system_server
24043   722956K   38384K   18885K   16480K  com.miaxis.mr860test
```



需要关注的是。meminfo拿到的信息和procrank拿到的内存数据有差异。

PSS的信息应该保持一致，USS应该与private dirty一致



### adb shell dumpsys meminfo 

```bash
# 每个应用使用的PSS
Total PSS by process:
...
Total PSS by OOM adjustment:
...
Total PSS by category:
...

Total RAM: 765096 kB
 Free RAM: 577952 kB (38008 cached pss + 200580 cached + 339364 free)
 Used RAM: 215454 kB (185658 used pss + 3268 buffers + 240 shmem + 26288 slab)
 Lost RAM: -28310 kB
     ZRAM: 4 kB physical used for 0 kB in swap (262140 kB total swap)
   Tuning: 96 (large 384), oom 122880 kB, restore limit 40960 kB (high-end-gfx)
```

### adb shell dumpsys meminfo -packagename 

```bash
# 单一应用分析
Applications Memory Usage (kB):
Uptime: 3060846 Realtime: 3060846

** MEMINFO in pid 24043 [com.miaxis.mr860test] **
                   Pss  Private  Private  Swapped     Heap     Heap     Heap
                 Total    Dirty    Clean    Dirty     Size    Alloc     Free
                 
                 
参数含义：
dalvik : dalvik使用的内存
native : native堆上的内存，指C\C++堆的内存（android 3.0以后bitmap就是放在这儿）
other  : 除了dalvik和native的内存，包含C\C++非堆内存······
Pss    : 该内存指将共享内存按比例分配到使用了共享内存的进程
allocated : 已使用的内存
free      : 空闲的内存
private dirty : 非共享，又不能被换页出去的内存（比如linux系统中为了提高分配内存速度而缓冲的小对象，即使你的进程已经退出，该内存也不会被释放）
share dirty   : 共享，但有不能被换页出去的内存
```





### adb shell procmem

```bash
# 单一应用分析-细则


```

