# avro test

avro 是一个数据序列化系统！

avro最关键的就是schema，任一自定义类型想要使用avro序列化/反序列化的话，都需要schema！

schema的编写，详见官方文档！http://avro.apache.org/docs/current/gettingstartedjava.html

## 生成代码，再序列化、反序列化
就是avro可以根据schema生成Class！

有了schema之后，就可以生成源码了：
```bash
java -jar /path/to/avro-tools-1.8.2.jar compile schema <schema file> <destination>
```
当然，如果使用了avro的maven插件，会在编译时自动生成。也可以手动执行 avro:schema

### 
本模块中，src/main/avro/user.avsc 就是schema，使用maven avro:schema 即可生成相应的User源码，只不过是在target/generated-sources/avro 下面，需要手动复制回去。

然后，我们就创建User对象，序列化到磁盘，再读取出来！

需要注意：
1. 使用无参构造器创建对象时，null值可以省略不设置。缺点就是不会校验是否满足条件，必须等到序列化时才会校验！
2. 使用builder创建对象时，null值也必须设置！缺点是需要创建副本，耗费略高。

## 不生成代码，直接序列化、反序列化
实际上，avro的数据都是跟相应的schema存储在一起的，所以，我们可以不需要生成代码 就直接进行序列化/反序列化操作！

这样，就不能使用 SpecificDatumReader/Writer 了，需要使用 GenericDatumReader/Writer ！
而且，需要Schema对象！

GenericRecord接口提供了根据“field”名称获取值的方法：Object get(String fieldName)；
不过需要声明，这内部实现并不是基于map，而是一个数组，数组和Schema声明的Fileds按照index对应。
put操作根据field名称找到对应的index，然后赋值；get反之。那么在对待Schema兼容性上和“代码生成”基本一致。

## 详见两个测试类

- AvroTestWithCodeGeneration
- AvroTestWithoutCodeGeneration

## 其他
代码生成时，User的Schema信息已经作为一个静态常量写入了User.java中，同时根据schema中fields的列表严格顺序，显式的生成Fields数组，数组的index为schema中field的声明的位置，比如name字段为0，age为1，email为2。
这个严格有序性，保证了writer按照field的顺序依次编码，同时reader也按照此顺序依次解码；这也意味着**开发工程者不能随意更改field在Schema中的顺序**，这个特性和protobuf、thrift都一样。

在writer写入实际数据之前，首先**把schema作为header写入文件**，这个header将作为Avro数据文件的合法性校验提供帮助，如果reader和writer使用的schema无法兼容（通过此Header校验），将导致数据文件无法读取。

在创建reader时会指定Schema，这个Schema称为“expected”，而writer写入文件中还有一个Schema，这个称为“actual”，那么在reader读取数据时，究竟哪个生效呢？
如果“expected”没有指定，那么将使用“actual”，否则最终的Schema将会是这两个Schema互相兼容的结果：
>Avro约定，Field顺序不能更改，且相应的index上Field的Type必须**兼容**（一致，或者兼容，比如long兼容int）；如果对应index上的Field名称不同，那么它们应该可以通过“别名”（aliases）互相兼容，即field的name可以不一样，但是允许使用aliases来声明它的曾用名。

##
其他：将Avro信息序列化到文件，这是我们在大数据存储和“数据迁移”、“分析”时常用的手段，有可能数据文件是多次append的结果（但不可能是多个线程同时append），那么开发者需要注意这一点，那么**每次append时，我们需要首先seek到文件的尾部**。

Avro提供了内部的`SeekableInput`类，可以封装File。（其实最常用的办法是，每次都新建数据文件）

```java
File diskFile = new File("/data/users.avro");
long length = diskFile.length();
DatumWriter<User> userDatumWriter = new SpecificDatumWriter<User>(User.class);
DataFileWriter<User> dataFileWriter = new DataFileWriter<User>(userDatumWriter);
if(length == 0) {
    dataFileWriter.create(user.getSchema(), diskFile);//如果是新文件，则插入Schema
}else {
    dataFileWriter.appendTo(diskFile);//对于现有文件，则直接追加到文件的尾部
}
//....
```

##
avro在RPC层面和thrift还有很大的差距，在使用thrift做RPC应用时非常简单而且是production级别可用。
更倾向于使用avro做数据存储和解析。