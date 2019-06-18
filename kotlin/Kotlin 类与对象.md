# Kotlin 类与对象

## 类

### 构造函数

```kotlin
class Person constructor(firstName: String) { ... }
```

如果主构造函数没有任何注解或者可见性修饰符，可以省略这个 *constructor*
关键字。

```kotlin
class Person(firstName: String) { ... }
```

主构造函数不能包含任何的代码。初始化的代码可以放到以 *init* 关键字作为前缀的**初始化块（initializer blocks）**中。

初始化块按照它们出现在类体中的顺序执行，与属性初始化器交织在一起：

```kotlin
class InitOrderDemo(name: String) {
    val firstProperty = "First property: $name".also(::println)
    
    init {
        println("First initializer block that prints ${name}")
    }
    
    val secondProperty = "Second property: ${name.length}".also(::println)
    
    init {
        println("Second initializer block that prints ${name.length}")
    }
}
```

如果构造函数有注解或可见性修饰符，这个 *constructor* 关键字是必需的，并且这些修饰符在它前面：

```kotlin
class Customer public @Inject constructor(name: String) { …… }
```

#### 次构造函数

```kotlin
class Person {
    constructor(parent: Person) {
        parent.children.add(this)
    }
}
```

