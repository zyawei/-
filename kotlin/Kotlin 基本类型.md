# 基本类型

- 数字 (Double,FLaot,Long,Int)
- 字符 (Char)
- 布尔 (Boolean)
- 字符串 (String)

## 基本类型：数字

下划线使之易读

```kotlin
val oneMillion = 1_000_000
val creditCardNumber = 1234_5678_9012_3456L
val socialSecurityNumber = 999_99_9999L
val hexBytes = 0xFF_EC_DE_5E
val bytes = 0b11010010_01101001_10010100_10010010
```

### 表示方式

在 Java 平台数字是物理存储为 JVM 的原生类型，除非我们需要一个可空的引用（如 `Int?`）或泛型。
后者情况下会把数字装箱。

**注意:数字装箱不一定保留同一性**:

```kotlin
val a: Int = 10000
println(a === a) // 输出“true”
val boxedA: Int? = a
val anotherBoxedA: Int? = a
println(boxedA === anotherBoxedA) // ！！！输出“false”！！！
```

另一方面，它保留了相等性:

```kotlin
val a: Int = 10000
println(a == a) // 输出“true”
val boxedA: Int? = a
val anotherBoxedA: Int? = a
println(boxedA == anotherBoxedA) // 输出“true”
```

**注意：**

`==`是java 的 equals

` ===`是java 的 `==`

### 显式转换

较小类型并不是较大类型的子类型,原因如下:

```kotlin
// 假想的代码，实际上并不能编译：
val a: Int? = 1 // 一个装箱的 Int (java.lang.Integer)
val b: Long? = a // 隐式转换产生一个装箱的 Long (java.lang.Long)
print(b == a) // 惊！这将输出“false”鉴于 Long 的 equals() 会检测另一个是否也为 Long
```

so,显式转换

```kotlin
val b: Byte = 1 // OK, 字面值是静态检测的
val i: Int = b // 错误
val i: Int = b.toInt() // OK：显式拓宽
print(i)
```

每个数字类型都可以显式转换维其他数字类型；

### 运算

**位运算**，没有特殊字符来表示，而只可用中缀方式调用命名函数，例如:

```kotlin
val x = (1 shl 2) and 0x000FF000
```

位运算符号表

- `shl(bits)` – 有符号左移 (Java 的 `<<`)
- `shr(bits)` – 有符号右移 (Java 的 `>>`)
- `ushr(bits)` – 无符号右移 (Java 的 `>>>`)
- `and(bits)` – 位与
- `or(bits)` – 位或
- `xor(bits)` – 位异或
- `inv()` – 位非

## 基本类型：字符

***注意:在 Kotlin 中字符不是数字***，可以显式转换为数字(eg:`'0'.toInt()`)

## 基本类型：布尔

类似数字，物理存储为 JVM 的原生类型，可空是装箱

## 基本类型：字符串

### 字符串字面值

*原始字符串* 使用三个引号（`"""`）分界符括起来，内部没有转义并且可以包含换行以及任何其他字符:

```kotlin
val text = """
    for (c in "foo")
        print(c)
"""
```

你可以通过 [`trimMargin()`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.text/trim-margin.html) 函数去除前导空格：


```kotlin
val text = """
    |Tell me and I forget.
    |Teach me and I remember.
    |Involve me and I learn.
    |(Benjamin Franklin)
    """.trimMargin()
```

### 字符串模板

模板表达式以美元符（`$`）开头，由一个简单的名字构成:

```kotlin
val i = 10
println("i = $i") // 输出“i = 10”
```

花括号括起来的任意表达式:`${表达式}`

```kotlin
val s = "abc"
println("$s.length is ${s.length}") // 输出“abc.length is 3”
```

显示`$`

```kotlin
val price = """
${'$'}9.99
"""
```

## 基本类型：数组

数组在 Kotlin 中使用 `Array` 类来表示；

具有初始值:
` arrayOf(1,2,3)`

不具有初始值:
`arrayOfNull(3)`

手动初始化：

```kotlin 
// 创建一个 Array<String> 初始化为 ["0", "1", "4", "9", "16"]
val asc = Array(5) { i -> (i * i).toString() }
asc.forEach { println(it) }
```

### 原生类型：数组

Kotlin 也有无装箱开销的专门的类来表示原生类型数组: `ByteArray`、
`ShortArray`、`IntArray` 等等。这些类与 `Array` 并没有继承关系，但是它们有同样的方法属性集。它们也都有相应的工厂方法:

```kotlin
val x: IntArray = intArrayOf(1, 2, 3)
x[0] = x[1] + x[2]
```

注意:**
Array<Char> 与CharArray之间无继承关系
上面两个都是Char数组，但是无法互相转换，原生数组类型可以转成String.