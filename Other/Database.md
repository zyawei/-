## 关系型数据库
关系数据库，是建立在关系模型基础上的数据库，借助于集合代数等数学概念和方法来处理数据库中的数据。
现实世界中的各种实体以及实体之间的各种联系均用关系模型来表示。
标准数据查询语言SQL就是一种基于关系数据库的语言。
关系模型由关系数据结构、关系操作集合、关系完整性约束三部分组成。
**简单说，关系型数据库是由多张能互相联接的二维行列表格组成的数据库。**

## 主键 外键 索引

### 主键

主键是数据表的唯一索引，**用来保证数据完整性**

### 外键

表的外键是另一表的主键（亦可以是唯一性索引）, 外键可以有重复的, 可以是空值。**用来和其他表建立联系用的**

设置外键，在参照表(referencing table) 和被参照表 (referenced table) 中，相对应的两个字段必须都设置索引(index)。？？

### 索引

是对数据库表中一列或多列的值进行排序的一种结构；**是提高查询排序的速度**

索引用来快速地寻找那些具有特定值的记录，所有MySQL索引都以B-树的形式保存。如果没有索引，执行查询时MySQL必须从第一个记录开始扫描整个表的所有记录，直至找到符合要求的记录。

#### 唯一性索引
索引列的所有值都只能出现一次。（主键就是唯一性索引）

作用：1，提升速度；2，避免数据重复出现；

#### 多列索引

当我们执行查询的时候，MySQL只能使用一个索引。如果你有三个单列的索引，MySQL[会试](https://www.baidu.com/s?wd=%E4%BC%9A%E8%AF%95&tn=24004469_oem_dg&rsv_dl=gh_pl_sl_csd)图选择一个限制最严格的索引。但是，即使是限制最严格的单列索引，它的限制能力也肯定远远低于firstname、lastname、age这三个列上的多列索引。 

** 最左前缀 ** 
我们有一个firstname、lastname、age列上的多列索引，我们称这个索引为fname_lname_age。当搜索条件是以下各种列的组合时，MySQL将使用fname_lname_age索引： 

```
firstname，lastname，age
firstname，lastname
firstname
```

从另一方面理解，它相当于我们创建了(firstname，lastname，age)、(firstname，lastname)以及(firstname)这些列组合上的索引。下面这些查询都能够使用这个fname_lname_age索引： 

#### 选择索引列
1、在WHERE子句中出现的列
2、在join子句中出现的列。

参考： https://blog.csdn.net/xrt95050/article/details/5556411



#### 什么情况下要避免使用索引？

虽然索引的目的在于提高数据库的性能，但这里有几个情况需要避免使用索引。使用索引时，应重新考虑下列准则：

- 索引不应该使用在较小的表上。
- 索引不应该使用在有频繁的大批量的更新或插入操作的表上。
- 索引不应该使用在含有大量的 NULL 值的列上。
- 索引不应该使用在频繁操作的列上。






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

## 一些Sql语句

```sql
查询记录数量,同一个user只统计一次
SELECT COUNT(distinct user_id)
FROM record
WHERE user_id= :userId and game_id = :gameId and site= :site
```

```sql
多表查询，连接3张表进行查询
select record.site , record.time ,user_group.name as groupName , user_group.id as groupId, photo.path as photo 
from record 
left join photo on record.id=photo.record_id
left join user on record.user_id=user.id 
left join user_group on user.group_id=user_group.id 
where game_id=:gameId 
order by user_group.id asc 
```

