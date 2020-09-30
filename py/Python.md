# Python



方法的第一个参数时self，方法的参数默认值

```python
def demoGitCommit(self,msg ='auto commit'):
```



指定工作目录

```python
os.chdir(self.projectPath)
```



获取Shell输出

```python
# 仅获取执行结果
import os
val = os.system('ls -al')
print val
# 获取输出
outLine = os.popen('git status').readlines();
# outLine is list
print(outLine[0])
# 循环获取输出
buildWork = os.popen('./gradlew build')
outLine = 'Call gradlew build ...'
while outLine != '':
  print('build %s'%outLine.strip())
  outLine = buildWork.readline()
else:	
  print('gradlew build done ! ')

# 获取执行结果和输出
import commands
(status,ouputs) = commands.getstatysoutput("ls -l")
  
```

print显示颜色

```python
-------------------------------------------
-------------------------------------------
字体色     |       背景色     |      颜色描述
-------------------------------------------
30        |        40       |       黑色
31        |        41       |       红色
32        |        42       |       绿色
33        |        43       |       黃色
34        |        44       |       蓝色
35        |        45       |       紫红色
36        |        46       |       青蓝色
37        |        47       |       白色
-------------------------------------------
-------------------------------
显示方式     |      效果
-------------------------------
0           |     终端默认设置
1           |     高亮显示
4           |     使用下划线
5           |     闪烁
7           |     反白显示
8           |     不可见
-------------------------------

print('This is a \033[1;35m test \033[0m!')
print('This is a \033[1;32;43m test \033[0m!')
print('\033[1;33;44mThis is a test !\033[0m')
```





## 字符串

去除尾部字符

```python
outLint = 'test'
print('build %s'%outLine.strip())
```



