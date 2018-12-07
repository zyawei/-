# Java Clone

## Object.clone()`

```java
public class Object{
    protected Object clone() throws CloneNotSupportedException {
        if (!(this instanceof Cloneable)) {
            throw new CloneNotSupportedException("");
        }
        return internalClone();
    }    
}
```

1、Java对象默认是不可以被复制的

​	`clone()`是`protected`方法

​	如果子类不实现`Cloneable`会抛异常

2、如果想自定义类可以被clone,需要

​	实现`Cloneable`

​	`clone()`方法修改未`public`

3、克隆时引用类型仅复制地址

## 浅克隆

参考2

## 深克隆

step1：参考2

step2：引用类型手动clone,此引用类型也要实现`Cloneable`

```java
public class Teacher implements Cloneable{
    
}
public class Student implements Cloneable{
    
     Teacher teacher;
    
     public Object clone() throws CloneNotSupportedException{
        Student newStudent = (Student) super.clone();
        newStudent.teacher = (Teacher) teacher.clone();
        return newStudent;
    }
}
```

