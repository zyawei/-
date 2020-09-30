# kotlin lambda

## 匿名内部类

```kotlin
Transformations.switchMap(source , object : Funtion<I,R>{
  override fun apply(input : T ):R {
   	return T as R 
  }
})
```

## lambda

```kotlin
Transformations.switchMap(source , input -> { })
// 最后项可以放在()外面
Transformations.switchMap(source , ){ input ->  }

```

## :: 

```
Transformations.switchMap(source , ){ input ->  }
```

