## Gradle

## task

```groovy
build.gradle

task hello << {
  // << 相当于 doLast()
  println "hello << "
}

task hello2(dependsOn : hello){
  ext.myPropety = "asda"
  println "hello"
}

hello2.doLast{
  println "hello2 doLast"
}

task hello3(){
  println hello2.myPropety 
  // asda
}

```

### Ant Task

```
—javac：用于编译一个或者多个Java源文件，通常需要srcdir和destdir两个属性，用于指定Java源文件的位置和编译后class文件的保存位置。
—java：用于运行某个Java类，通常需要classname属性，用于指定需要运行哪个类。
—jar：用于生成JAR包，通常需要指定destfile属性，用于指定所创建JAR包的文件名。除此之外，通常还应指定一个文件集，表明需要将哪些文件打包到JAR包里。
—sql：用于执行一条或多条SQL语句，通常需要driver、url、userid和password等属性，用于指定连接数据库的驱动类、数据库URL、用户名和密码等，还可以通过src来指定需要指定的SQL脚本文件，或者直接使用文本内容的方式指定SQL脚本字符串。
—echo：输出某个字符串。
—exec：执行操作系统的特定命令，通常需要executable属性，用于指定想执行的命令。
—copy：用于复制文件或路径。
—delete：用于删除文件或路径。
—mkdir：用于创建文件夹。
—move：用户移动文件和路径。
..........
```



## Property

```groovy
// 获取local.properties的属性
def getOpenCvSdk() {
    Properties properties = new Properties()
    properties.load(project.rootProject.file('local.properties').newDataInputStream())
    return properties.getProperty("sdk.opencv")
}
```



