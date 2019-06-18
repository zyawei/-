# sqlite3 主键会自动创建索引吗？

```
The PRIMARY KEY attribute normally creates a UNIQUE index on the column or columns that are specified as the PRIMARY KEY. The only exception to this behavior is special INTEGER PRIMARY KEY column, described below. According to the SQL standard, PRIMARY KEY should imply NOT NULL. Unfortunately, due to a long-standing coding oversight, this is not the case in SQLite. SQLite allows NULL values in a PRIMARY KEY column. We could change SQLite to conform to the standard (and we might do so in the future), but by the time the oversight was discovered, SQLite was in such wide use that we feared breaking legacy code if we fixed the problem. So for now we have chosen to continue allowing NULLs in PRIMARY KEY columns. Developers should be aware, however, that we may change SQLite to conform to the SQL standard in future and should design new programs accordingly.
```

*官方文档：除 INTEGER PRIMARY KEY  以外，其它的都会自动创建UNIQUE 索引。*

## 验证

1.创建integer主键，没有自动创建索引

```shell
sqlite> create table 'test_int' ('id' INTEGER PRIMARY KEY , 'name' TEXT) ;
sqlite> .indexes test_int 
```

2.创建text主键,自动创建索引:sqlite_autoindex_test_str_1

```shell
sqlite> create table 'test_str' ('id' TEXT PRIMARY KEY , 'name' TEXT);
sqlite> .indexes test_str
sqlite_autoindex_test_str_1
```

3.创建int,自动创建索引:sqlite_autoindex_test0_1

```shell
sqlite> create table 'test0' ('id' int primary key);
sqlite> .indexes test0
sqlite_autoindex_test0_1
```

**只有integer primary key  不会自动创建索引；**

## 解释

在SQLite中，类型为INTEGER PRIMARY KEY的列是ROWID的别名（WITHOUT ROWID表除外），它始终是64位有符号整数。

是的，有一点不同：INTEGER是SQLite中的一个特例，当数据库没有创建单独的主键时，而是重用ROWID列。当您使用INT（或在内部“映射”到INTEGER的任何其他类型）时，会创建一个单独的主键。

当使用integer时,SQLite重用整数主键的内置索引结构，使得不需要自动索引。

**附件说明：**

您创建的INTEGER PRIMARY KEY列只是ROWID或_ROWID_或OID的别名。如果添加了AUTOINCREMENT关键字，则插入的每个新记录都是最后一个ROWID的1的增量，最后一个ROWID由名为sqlite_sequence的sqlite内部表保存。

**为何int类型也会自动创建**

- 在sqlite中，int类型和其他类型一样，是无类型(或者说是亲和类型)
- INTEGER 不创建索引的原因是重用rowId，64bit

## 结论

- INTEGER PRIMARY KEY 不会自动创建索引；

- 其他类型(TEXT , INT ..) PRIMARY KEY  会自动创建索引；

  

