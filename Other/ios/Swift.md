# Swift



## 关键字

```swift
struct 结构体
```

```swift
enum 枚举
```

```swift
extension 扩展
使用 extension来给现存的类型增加功能，比如说新的方法和计算属性。你可以使用扩展来使协议来别处定义的类型，或者你导入的其他库或框架。

```

```swift
mutating 变异 ；修饰struct/enum 中的方法，使之可以修改属性值
注意使用 mutating关键字来声明在 SimpleStructure中使方法可以修改结构体。在 SimpleClass中则不需要这样声明，因为类里的方法总是可以修改其自身属性的。
```



- 结构体

- ```swift
  struct Player {
      var name: String
      var highScore: Int = 0
      var history: [Int] = []
  
      init(_ name: String) {
          self.name = name
      }
  }
  
  var player = Player("Tomas")
  ```

- 枚举

  ```swift
  enum TriStateSwitch {
      case Off, Low, High
      mutating func next() {
          switch self {
          case Off:
              self = Low
          case Low:
              self = High
          case High:
              self = Off
          }
      }
  }
  var ovenLight = TriStateSwitch.Low
  ovenLight.next()
  // ovenLight is now equal to .High
  ovenLight.next()
  // ovenLight is now equal to .Off”
  ```

  

- mutating

- ```swift
  extension Player {
      mutating func updateScore(_ newScore: Int) {
          history.append(newScore)
          if highScore < newScore {
              print("\(newScore)! A new high score for \(name)! 🎉")
              highScore = newScore
          }
      }
  }
  ```

- 扩展

  ```swift
  extension Collection where Element == Player {
      // Returns the highest score of all the players,
      // or `nil` if the collection is empty.
      func highestScoringPlayer() -> Player? {
          return self.max(by: { $0.highScore < $1.highScore })
      }
  }
  ```

- 可选类型

- ```swift
  if let bestPlayer = players.highestScoringPlayer() {
      recordHolder = """
          The record holder is \(bestPlayer.name),\
          with a high score of \(bestPlayer.highScore)!
          """
  } else {
      recordHolder = "No games have been played yet.")
  }
  print(recordHolder)
  // The record holder is Erin, with a high score of 271!
  
  let highestScore = players.highestScoringPlayer()?.highScore ?? 0
  // highestScore == 271
  ```



## 集合

- 数组 <List>

```swift
let emptyArray = [String]()
var history: [Int] = []
let array = ["张三", "李四", "王五"]	
let array = [1, 2, 3, 4, 5]
let array = ["张三", 1] as [Any]     
// 遍历 ： 索引
for i in 0..<array.count {
    print(array[i])
}
// 遍历 ： 值
for s in array {
    print(s)
}
// 遍历 ：索引，值
for (n, s) in array.enumerated() {
    print("\(n) \(s)")
}
// 遍历 ： 枚举
for e in array.enumerated() {
    print(e)
    print("\(e.offset) \(e.element)")
}
// 遍历 ： 倒序：reversed
for s in array.reversed() {
  print(s)
}
// 合并 (不同类型的数组，不能直接进行合并。)
array += array2
```

- 字典<Map>

```swift
// 如果字典中'value'的类型不同,则需要指定字典的类型为[String : Any]
let dict: [String : Any] = ["name":"王红庆", "age":18]
// 字典的数组
let array: [[String : Any]] = [
    ["name":"王红庆", "age":18],
    ["name":"王白庆", "age":180]
]
// 增&改
dict["title"] = "iOS开发菜鸡"
// 删
dict.removeValue(forKey: "name")
// 遍历
for e in dict {
    print(e)
	  print("\(e.key) \(e.value)")
}

for (key, value) in dict {
    print("\(key) \(value)")
}
// 合并
let dict2: [String : Any] = ["name": "王白庆", "height": 190]
for e in dict2 {
    dict1[e.key] = dict2[e.key]
}
```



```swift
let players = getPlayers()

// Sort players, with best high scores first
let ranked = players.sorted(by: { player1, player2 in
    player1.highScore > player2.highScore
})

// Create an array with only the players’ names
let rankedNames = ranked.map { $0.name }
// ["Erin", "Rosana", "Tomas"]
```



## 控制流

使用 if和 switch来做逻辑判断，使用 for-in， for`，` while``，以及 repeat-while来做循环。使用圆括号把条件或者循环变量括起来不再是强制的了，不过仍旧需要使用花括号来括住代码块。



## 闭包

一段可以被随后调用的代码块。**闭包中的代码可以访问其生效范围内的变量和函数**，就算是闭包在它声明的范围之外被执行——你已经在内嵌函数的栗子上感受过了。你可以使用花括号（ {}）括起一个没有名字的闭包。**在闭包中使用 in来分隔实际参数和返回类型**。

```swift
var numbers = [20, 19, 7, 12]
numbers.map({
    (number: Int) -> Int in
    let result = 3 * number
    return result
})
```

当一个闭包的类型已经可知，比如说某个委托的回调，你可以去掉它的参数类型，它的返回类型，或者都去掉。

单语句闭包隐式地返回语句执行的结果。

```swift
let mappedNumbers = numbers.map({ number in 3 * number })
print(mappedNumbers)
```

当一个闭包作为函数最后一个参数出入时，可以直接跟在圆括号后边。如果闭包是函数的唯一参数，你可以去掉圆括号直接写闭包。

```swift
let mappedNumbers = numbers.map{ $0 * 3 }
let sortedNumbers = numbers.sorted { $0 > $1 }
print(sortedNumbers)
```



## 错误处理

定义

```swift
enum PrinterError: Error {
    case outOfPaper
    case noToner
    case onFire
}
```

抛

```swift
func send(job: Int, toPrinter printerName: String) throws -> String {
    if printerName == "Never Has Toner" {
        throw PrinterError.noToner
    }
    return "Job sent"
}
```

处理

```swift
do {
    let printerResponse = try send(job: 1440, toPrinter: "Gutenberg")
    print(printerResponse)
} catch PrinterError.onFire {
    print("I'll just put this over here, with the rest of the fire.")
} catch let printerError as PrinterError {
    print("Printer error: \(printerError).")
} catch {
    print(error)
}
```

简化

```swift
let printerSuccess = try? send(job: 1884, toPrinter: "Mergenthaler")
```

defer

```java
defer is java finally
```



## JSON

```swift
import Foundation
let encoder = JSONEncoder()
try encoder.encode(player)
```



## 类型

- `struct` (value type)
- `enum` (value type)
- `class`  ( reference type)
- `protocol `(?)







## todo

- Swift 概览 enum