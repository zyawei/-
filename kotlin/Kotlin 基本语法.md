# Kotlin 基本语法	



## 使用字符串模板

```kotlin
var a = 1
// 模板中的简单名称：
val s1 = "a is $a" 
a = 2
// 模板中的任意表达式：
val s2 = "${s1.replace("is", "was")}, but now is $a"
```

## 使用条件表达式

```kotlin
fun maxOf(a: Int, b: Int): Int {
    if (a > b) {
        return a
    } else {
        return b
    }
}
```

使用 *if* 作为表达式:(kotlin 没有三元运算符)

```kotlin
fun maxOf(a: Int, b: Int) = if (a > b) a else b
```

## 使用 `for` 循环(.indices)

```kotlin
val items = listOf("apple", "banana", "kiwifruit")
for (item in items) {
    println(item)
}
val items = listOf("apple", "banana", "kiwifruit")

// 关键:.indices
for (index in items.indices) {
    println("item at $index is ${items[index]}")
}
```

## 使用 `when` 表达式

```kotlin
fun describe(obj: Any): String =
    when (obj) {
        1          -> "One"
        "Hello"    -> "Greeting"
        is Long    -> "Long"
        !is String -> "Not a string"
        else       -> "Unknown"
    }
```

## 使用集合

对集合进行迭代:

```kotlin
for (item in items) {
    println(item)
}
```

使用 *in* 运算符来判断集合内是否包含某实例：

```kotlin
when {
    "orange" in items -> println("juicy")
    "apple" in items -> println("apple is fine too")
}
```

使用 lambda 表达式来过滤（filter）与映射（map）集合：

```kotlin
val fruits = listOf("banana", "avocado", "apple", "kiwifruit")
fruits
  .filter { it.startsWith("a") }
  .sortedBy { it }
  .map { it.toUpperCase() }
  .forEach { println(it) }
```

