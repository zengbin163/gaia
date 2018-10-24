# mapdb-test

## 简介

MapDB是一个开放源代码的（Apache 2.0授权）、嵌入式的Java数据库引擎和收集框架。它提供带有范围查询、时效限制、压缩、超栈存储和流功能的map、set、list、queue、Bitmap。

MapDB可能是当前最快的Java数据库，性能可与java.util 集合相当。它还提供高级功能，如ACID事务，快照，增量备份等等。

本手册是一项正在进行的工作，它将与MapDB 3.0版本一起完成。

## 快速简介

MapDB是灵活的，具有许多配置选项。但是在大多数情况下，它只配置了几行代码。

## 获取MapDB

MapDB二进制文件托管在Maven Central存储库中。这里是MapDB的依赖部分。

```xml
<dependency>
   <groupId>org.mapdb</groupId>
   <artifactId>mapdb</artifactId>
   <version>${VERSION}</version>
</dependency>
```   

VERSION 是Maven Central 的最后一个版本号。
每日版本都在快照库中。最新快照的版本号在这里。

```xml
<repositories>
    <repository>
    <id>sonatype-snapshots</id>
    <url>https://oss.sonatype.org/content/repositories/snapshots</url>
    </repository>
</repositories>
<dependencies>
    <dependency>
        <groupId>org.mapdb</groupId>
        <artifactId>mapdb</artifactId>
        <version>${VERSION}</version>
    </dependency>
</dependencies>
```

您也可以直接从 Maven Central 下载MapDB jar文件。在这种情况下，请注意，MapDB依赖于Eclipse Collections、Guava，Kotlin库和其他一些库。

## Hello World!

以下是一个简单的例子。它打开内存中的HashMap，它使用堆栈存储，它不受垃圾收集的限制：

```java
//import org.mapdb.*
DB db = DBMaker.memoryDB().make();
ConcurrentMap map = db.hashMap("map").createOrOpen();
map.put("something", "here");
```

HashMap（和其他集合）也可以存储在文件中。在这种情况下，可以在JVM重新启动之间保留内容。有必要调用DB.close（）来保护文件免受数据损坏。其他选项是使用写入日志来启用事务。
```java
DB db = DBMaker.fileDB("file.db").make(); 
ConcurrentMap map = db.hashMap("map").createOrOpen();
map.put("something", "here");
db.close();
```

Hello World示例没有覆盖提交。

默认情况下，MapDB使用泛型序列化，可以序列化任何数据类型。使用专门的串行器，速度更快，记忆效率更高。此外，我们还可以在64位操作系统上启用更快速的内存映射文件：

```java
DB db = DBMaker.fileDB("file.db").fileMmapEnable().make(); 
ConcurrentMap<String,Long> map = 
    db.hashMap("map", Serializer.STRING, Serializer.LONG)
        .createOrOpen(); 
map.put("something", 111L);
db.close();
```

快速提示

- 内存映射文件要快得多，应该在64位系统上启用，以获得更好的性能。
- MapDB具有快速批量导入集合的机制。它比Map.put（）快得多。事务具有性能开销，但没有它们，如果没有正确关闭，则存储将被损坏。
- 存储在MapDB中的数据（键和值）应该是不可变的。MapDB在后台序列化对象。
- MapDB有时需要压缩。运行DB.compact（）或查看背景压缩选项。

## DB和DBMaker‌
   
MapDB可以像Lego一样插拔。有两个类就像不同部分的插接块一样，即DBMaker和DB类。
DBMaker类处理数据库配置、创建和打开。MapDB有几种模式和配置选项，都可以使用这个类设置。
一个数据库实例代表了一个打开的数据库（或单个事务会话）。它可用于创建和打开集合存储。它还可以使用commit()，rollback()和close()等方法来处理数据库的生命周期。
要打开（或创建）一个存储，可以使用许多*DB 静态方法，例如DBMaker.fileDB()。MapDB具有很多格式和模式，每个xxxDB()使用不同的模式：memoryDB()打开一个由byte [] 数组支持的内存数据库，
appendFileDB()打开一个使追加的日志文件等。
一个xxxDB()方法后可跟一个或多个配置项，最后一个应用所有选项的make()方法打开选定的存储并返回一个DB 对象。此示例打开启用加密的文件存储：

```java
DB db = DBMaker.fileDB("/some/file")
//TODO encryption API
//.encryptionEnable("password")
.make();
```

## 打开并创建集合

一旦你创建了DB，你可以打开一个集合或其他记录。DB使用构建器样式配置。它可以选择集合类型（hashMap ，treeSet …）并命名，然后进行配置，最终可以直接进行操作。
下边的例子打开（或创建新的）TreeSet命名为“example”

```java
NavigableSet treeSet = db.treeSet("example").createOrOpen();
```

还可以应用其他配置：

```java
NavigableSet<String> treeSet = 
    db.treeSet("treeSet").maxNodeSize(112).
        createOrOpen();
```

构建器可以以三种不同的方法结束：

- create() 创建新的集合。 如果集合存在，将扔出异常。
- open() 打开存在的集合。 如果集合不存在，将扔出异常。
- createOrOpen() 如果存在就打开, 否则创建。

DB不限于集合, 还可以创建其他类型的记录，如原子记录:

```java
Atomic.Var<Person> var = db.atomicVar("mainPerson",Person.SERIALIZER).createOrOpen();
```

## 事务

DB具有处理事务生命周期的方法: commit() , rollback() and close() .
一个DB 对象表示单个事务。上面的例子每个存储使用单个全局事务，这对于一些应用是足够的：

```java
ConcurrentNavigableMap<Integer,String> map = 
    db.treeMap("collectionName", Serializer.INTEGER, Serializer.STRING).
        createOrOpen();
map.put(1,"one");
map.put(2,"two");
//map.keySet() is now [1,2] even before commit
db.commit(); //persist changes into disk map.put(3,"three");
//map.keySet() is now [1,2,3] db.rollback(); //revert recent changes
//map.keySet() is now [1,2]
db.close();
```

## 原文

[MapDB 教程一 ](http://blog.csdn.net/cnhome/article/details/75467000)
[MapDB 教程二 ](http://blog.csdn.net/cnhome/article/details/75505502)
[MapDB 教程三 ](http://blog.csdn.net/cnhome/article/details/76131658)