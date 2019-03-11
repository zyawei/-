## 关系型数据库
关系数据库，是建立在关系模型基础上的数据库，借助于集合代数等数学概念和方法来处理数据库中的数据。
现实世界中的各种实体以及实体之间的各种联系均用关系模型来表示。
标准数据查询语言SQL就是一种基于关系数据库的语言。
关系模型由关系数据结构、关系操作集合、关系完整性约束三部分组成。
**简单说，关系型数据库是由多张能互相联接的二维行列表格组成的数据库。**

## 主键 外键 索引






## SQL 中 left join / right join / inner join 的区别

- left join 
  以左表为基础查询右表对应数据
- right join
  以右表为基础查新左表对应数据，右表没有的补null
- inner join
  紧返回两表都有的数据


## Android Room warning : foreign key index 
```java
*** column references a foreign key but it is not part of an index. This may trigger full table scans whenever parent table is modified so you are highly advised to create an index that covers this column.
// 每当修改父表时，这可能会触发子表全表扫描，因此强烈建议您创建一个涵盖此列的索引。   
```

**为什么给外键建立索引：**

	(1) 外键没有索引，确实可能导致子表产生表锁，但是有前提： 
		a. 子表有删改操作。 
		b. 主表有删操作，或者更新主键的操作。 
		满足以上两个条件才会出现主表操作hang状态。
	(2) 外键不建索引，则删除主表记录或主子表关联查询，都会进行子表的全表扫描。
	(3) 主子表任何插入操作，无论顺序，不会产生锁或hang状态。
	(4) 只有外键创建索引，(1)中的操作才不会出现锁或hang状态，(2)中的操作才有可能使用索引。
	原文：https://blog.csdn.net/bisal/article/details/50934304 


## Android Room ForeignKey Nullable
```java
//比如 外键foreignKey
public long foreignKey;// 则外键不可为NULL
public Long foreignKey;// 则外键可NULL

/**原因 ：基本数据类型一定会被覆初始值0，如果0无法对应约束的主键值，那么约束失败。**/

```

