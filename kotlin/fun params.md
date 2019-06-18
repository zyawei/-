# Kotlin FAQ

### 1、如何修改方法参数

**你不能修改方法参数，因为kotlin认为这容易导致问题；kotlin方法参数默认是final型（`val`）的.**

```java
//类似以下java代码，在kotlin里面是实现不了的。
void test(Bean bean){
    if(bean = null){
        bean = new Bean();
    }
    ... 
}
```

```kotlin
//曾经可以这样,但是现在不可以了
fun (var bean:Bean){
    if(bean==null){
        bean = Bean()
    }
}

```



### 2、空安全之全局变量

```kotlin

class Test{
    var camera: Camera? = null
    fun open(){
        camera?.let{
            //使用camera(全局变量)要加!!
            camera!!.parameters ..
            //使用it可以不加!!
            it.parameters ..
        }
         //或者也可以不加!!
        camera?.let{ cm->
	         cm.parameters..
        }
    }
    //let 返回的变量是校验过的，let内部使用全局变量无法保证非空
    
}
```

