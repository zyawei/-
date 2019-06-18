# HexStringUtils

将byte[]转换为HexString的几种方式比较

## 方式一：调用Integer.toHexString

```java
public static String bytesToHexString(byte[] bytes) {
    StringBuilder sb = new StringBuilder(bytes.length * 2);
    for (byte aByte : bytes) {
        sb.append(Integer.toHexString(aByte & 0xff)).append(' ');
    }
    return sb.toString();
}
```

核心方法在`Integer.toHexString(aByte & 0xff)`:

- `&0xff` 移除负数可能带来的坏影响
- 将int值转换为十六进制字符串

结果：循环10 000 000次耗时14330ms

## 方式二:查表法
```java
	private static final String HEXES_TABLE = "0123456789abcdef";
    private static final char SPACE = ' ';

    public static String hexToString(byte[] data) {
        StringBuilder hex = new StringBuilder(2 * data.length);
        for (byte b : data) {
        hex.append(HEXES_TABLE.charAt(b >> 4 & 0xf))
        	.append(HEXES_TABLE.charAt((b & 0xf)))
        	.append(SPACE);
        }
        return hex.toString();
    }
```
**核心点**

- 查表法
- `b >> 4 & 0xf `取byte高四位（eg:0xC1经过运算后为0xC）
- `b & 0xf` 去byte低四位（eg:0xC1经过运算后为0x1）

**测试过程中发现的其他问题：**

- `StringBuilder#append(' ')`与`StringBuilder#append(" ")`在这里会影响一倍的性能。
	原因在于追加字符串比追加字符的运算更多
- `HEXES_TABLE`作为`String`和作为`char[]`不存在性能差异
	String.charAt()直接调jni返回字符，速度与获取数组中的某个字符不存在性能差异，实际上String就是char[]
	

**结果：**

循环10 000 000次耗时3169ms

## 方式三:查表法，移除StringBuilder
```java
	final static char[] hexArray = "0123456789abcdef".toCharArray();

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 3];
        for (int j = 0, len = bytes.length; j < len; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 3] = hexArray[v >>> 4];
            hexChars[j * 3 + 1] = hexArray[v & 0xF];
            hexChars[j * 3 + 2] = ' ';
        }
        return new String(hexChars);
    }
```
**核心点：**
- 不适用StringBuild，提高性能

**结果：**

循环10 000 000次耗时1885ms

## 总结：
### 优化过程
- 移除Integer.toHexString()
- 使用字符' '，替代字符串“ ”
- 移除StringBuilder

**查表法关键点在于获取byte字符的高4位：**

[如何获取byte高四位](https://github.com/zyawei/Essay/blob/master/Java/Java%20byte%20%E7%A7%BB%E4%BD%8D%E6%93%8D%E4%BD%9C.md)

**完整的HexStringUtils：**

[HexStringUtils](https://github.com/zyawei/Essay/blob/master/utils/HexStringUtils.java)


