# Java RSA 

[TOC]

**大数的质因数分解**

公钥=modulus+publicExponent

私钥=modulus+privateExponent

- modulus 
- publicExponent 一般选取65537

- privateExponent 泄露，等于私钥泄露

>  以上三个，都是数字，在Java中，可以用	表示。



## Cipher

```java
// Android 默认
Cipher cipher = Cipher.getInstance("RSA/None/NoPadding", "BC");
cipher.init(Cipher.ENCRYPT_MODE, publicKey);
byte[] result = cipher.doFinal(keyWithCrc);
```

### 生成公私钥

```java
KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
// 初始化密钥对生成器，密钥大小为96-1024位
keyPairGen.initialize(1024, new SecureRandom());
// 生成一个密钥对，保存在keyPair中
KeyPair keyPair = keyPairGen.generateKeyPair();
RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥
RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥
```

### 导入公私钥

**通过固定格式**

java中，公钥使用X.509格式保存，私钥使用PKCS#8保存

```java
KeyFactory keyFactory = KeyFactory.getInstance("RSA");
PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(null));
```

**通过modulus + exponent构造** 

```java
BigInteger bigIntModulus = new BigInteger(strM, 16);
BigInteger bigIntExponent = new BigInteger(strP, 16);
BigInteger bigIntExponent = new BigInteger(1，strP);
RSAPublicKeySpec keySpec = new RSAPublicKeySpec(bigIntModulus, bigIntPrivateExponent);
KeyFactory keyFactory = KeyFactory.getInstance("RSA");
PublicKey publicKey = keyFactory.generatePublic(keySpec);
```

> 构造BitInteger时，
>
> 注意modulus 与 Exponent一定是正整数。
>
> 注意原始数据的类型（进制、byte?）



## Provider

如果需要使用NoPadding，选择BC，但是安全性略低。


## Padding
- 每次生成的密文都不一致证明你选用的加密算法很安全。
- RSA1024的秘钥长度是128，（128*8=1024）

默认实现：

- Java 默认的 RSA 实现是 "RSA/None/PKCS1Padding"

- Bouncy Castle 的默认 RSA 实现是 "RSA/None/NoPadding"。

>  Android 7.0 默认BC(Bouncy Castle) 

### NoPadding

不填充，同一个秘钥多次加密得到的结果一样.

### PKCS1Padding

PKCS #1  RSA Encryption Version 1.5

在进行RSA运算时需要将源数据D转化为Encryption block（EB）。其中pkcs1padding V1.5的填充模式安装以下方式进行

(1) EB = 00+ BT+PS +00 + D

说明

EB：为转化后Hex进制表示的数据块，长度为128个字节（密钥1024位的情况下）

00：开头为00。个人认为应该是一个保留位。因为目前BT的类型至于三种（00，01,02）一个字节就可以表示。

BT：用一个字节表示,在目前的版本上,有三个值00 01 02,如果使用公钥操作,BT永远为02,如果用私钥操作则可能为00或01。

PS：为填充位PS由k-3-D这么多个字节构成，k表示密钥的字节长度,如果我们用1024bit的RSA密钥,这个长度就是1024/8=128 ,D表示明文数据D的字节长度 

对于BT为00的,则这些字节全部为00,对于BT为01的这些值全部为FF,对于BT为02的,这些字节的值随机产生但不能是0字节(就是00)。

00：在源数据D前一个字节用00表示

D：实际源数据

公式(1)整个EB的长度就是密钥字节的长度。

对于BT为00的,数据D中的数据就不能以00字节开头,要不然会有歧义,因为这时候你PS填充的也是00,就分不清哪些是填充数据哪些是明文数据了
但如果你的明文数据就是以00字节开头怎么办呢?对于私钥操作,你可以把BT的值设为01,这时PS填充的FF,那么用00字节就可以区分填充数据和明文数据对于公钥操作,填充的都是非00字节,也能够用00字节区分开。如果你使用私钥加密,建议你BT使用01,保证了安全性。

对于BT为02和01的,PS至少要有8个字节长,BT为02肯定是公钥加密,01肯定是私钥加密，要保证PS有八个字节长
因为EB= 00+BT+PS+00+D=k

所以D<=k-11，所以当我们使用128字节密钥对数据进行加密时，明文数据的长度不能超过过128-11=117字节

当RSA要加密数据大于 k-11字节时怎么办呢？把明文数据按照D的最大长度分块然后逐块加密,最后把密文拼起来就行。

英文文档参考RFC 2313 PKCS #1: RSA Encryption

