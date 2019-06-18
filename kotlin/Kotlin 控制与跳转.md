# Kotlin 基础

## 控制流

### If 表达式

在 Kotlin 中，*if*是一个表达式，即它会返回一个值。最后的表达式作为该块的值：

```kotlin
val max = if (a > b) {
    print("Choose a")
    a
} else {
    print("Choose b")
    b
}
```

### When 表达式

*** When 表达式最终会被编译为if-else 串，所以可以实现一下功能**

多分支需要用相同的方式处理

```kotlin
when (x) {
    0, 1 -> print("x == 0 or x == 1")
    else -> print("otherwise")
}
```

任意表达式（而不只是常量）作为分支条件

```kotlin
when (x) {
    parseInt(s) -> print("s encodes x")
    else -> print("s does not encode x")
}
```

区间

```kotlin
when (x) {
    in 1..10 -> print("x is in the range")
    in validNumbers -> print("x is valid")
    !in 10..20 -> print("x is outside the range")
    else -> print("none of the above")
}
```

### For 循环

iterator遍历

```kotlin
for(** in xx){
  println(**)
}
```

索引遍历

```kotlin
for (i in array.indices) {
    println(array[i])
}
```

### While 循环

同java

## 返回和跳转

Kotlin 有三种结构化跳转表达式：

- *return*。默认从最直接包围它的函数或者[匿名函数](https://www.kotlincn.net/docs/reference/lambdas.html#匿名函数)返回。

- *break*。终止最直接包围它的循环。

- *continue*。继续下一次最直接包围它的循环。

  

*break* or *continue*

```kotlin
loop@ for (i in 1..100) {
    for (j in 1..100) {
        if (……) break@loop
    }
}
```

*return*

```kotlin
fun foo() {
    listOf(1, 2, 3, 4, 5).forEach lit@{
        if (it == 3) return@lit // 局部返回到该 lambda 表达式的调用者，即 forEach 循环
        print(it)
    }
    print(" done with explicit label")
}
```

*return 隐式*

```kotlin
fun foo() {
    listOf(1, 2, 3, 4, 5).forEach {
        if (it == 3) return@forEach // 局部返回到该 lambda 表达式的调用者，即 forEach 循环
        print(it)
    }
    print(" done with implicit label")
}
```

