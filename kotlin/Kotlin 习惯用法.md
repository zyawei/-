# Kotlin 习惯用法

### 创建 DTOs（POJOs/POCOs）

```kotlin
data class Customer(val name: String, val email: String="default")
```

### 过滤 list

```kotlin
val positives = list.filter { x -> x > 0 }
// or
val positives = list.filter { it > 0 }
```

### 类型判断( is )

```kotlin
when (x) {
    is Foo //-> ……
    is Bar //-> ……
    else   //-> ……
}
```

### 遍历 map/pair型list

```kotlin
for ((k, v) in map) {
    println("$k -> $v")
}
```

### 使用区间

```kotlin
for (i in 1..100) { …… }  // 闭区间：包含 100
for (i in 1 until 100) { …… } // 半开区间：不包含 100
for (x in 2..10 step 2) { …… }
for (x in 10 downTo 1) { …… }
if (x in 1..10) { …… }
```

### 只读 list/map

```kotlin
val list = listOf("a", "b", "c")
val map = mapOf("a" to 1, "b" to 2, "c" to 3)
```

### 访问 map

```kotlin
println(map["key"])
map["key"] = value
```

### 延迟属性

```kotlin
val p: String by lazy {
    // 计算该字符串
}
```

### 创建单例

```kotlin
object Resource {
    val name = "Name"
}
// Resource 即是类名，又是对象名
```

### If not null 缩写(?)

```kotlin
val files = File("Test").listFiles()
println(files?.size) 
// null
```

### If not null and else 缩写 (？...  ?: )

```kotlin
println(files?.size ?: "empty")
// empty
```

### if null 执行一个语句 

```kotlin
val values = ……
// map
val email = values["email"] ?: throw IllegalStateException("Email is missing!")
```

### 在可能会空的集合中取第一元素

```kotlin
val emails = …… // 可能会是空集合
//list
val mainEmail = emails.firstOrNull() ?: ""
```

### if not null 执行代码 (?.let)

```kotlin
val value = ……
value?.let {
    …… // 代码会执行到此处, 假如value不为null
}
```

### 映射可空值（如果非空的话）

```kotlin
val value = ……	
val mapped = value?.let { transformValue(it) } ?: defaultValueIfValueIsNull
```

### 返回类型为 `Unit` 的方法的 Builder 风格用法

```kotlin
fun arrayOfMinusOnes(size: Int): IntArray {
    return IntArray(size).apply { fill(-1) }
}
```

### 对一个对象实例调用多个方法 （`with`）

```kotlin
class Turtle {
    fun penDown()
    fun penUp()
    fun turn(degrees: Double)
    fun forward(pixels: Double)
}

val myTurtle = Turtle()
with(myTurtle) { // 画一个 100 像素的正方形
    penDown()
    for(i in 1..4) {
        forward(100.0)
        turn(90.0)
    }
    penUp()
}
```

### 对于需要泛型信息的泛型函数的适宜形式

```kotlin
//  public final class Gson {
//     ……
//     public <T> T fromJson(JsonElement json, Class<T> classOfT) throws JsonSyntaxException {
//     ……

inline fun <reified T: Any> Gson.fromJson(json: JsonElement): T = this.fromJson(json, T::class.java)
```

