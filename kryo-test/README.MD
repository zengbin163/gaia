# Kryo test

Kryo 是一个Java的序列化/反序列化框架，只专注于速度、高效！

据说比protobuf更快，且更简单。

## Serializers
当序列化一个对象时，会先创建一个已经被注册过的Serializer的对象，也就是默认的Serializers！
默认的Serializer可以处理基本类型、lists、maps、enums等等。
如果找不到符合的，那就使用`FieldSerializer` - 可以处理几乎所有类型。

## 读/写
- writeClass / readClass
- writeObject / readObject
- writeClassAndObject / readClassAndObject

注意，可以读写任意多个对象和Class！
读取的时候，也需要按照相应的顺序来。

writeClassAndObject / readClassAndObject 其实是先写/读了Class，紧跟着又写/读了Object！
换句话说，这组信息就是自解释的。

但是，你还是可以先读取Class(readClass)，再读取Object(readObject)，其结果和readClassAndObject完全一样！

同理，写入的时候，也可以先写入Class(writeClass)，再写入Object(writeObject)，其结果和writeClassAndObject完全一样！

## 注意
别忘了output.flush()！否则不会写入！因为其他的操作(除了flush/close)，都是操作buffer，而非outputStream的。

Output本身内部是有一个buffer，是一个byte[]，但还可以将内容输出到另一个OutputStream！
用户可以自由选择！

>如果被序列化的类中不包含无参的构造函数，则在Kryo的序列化中，性能将会大打折扣，此时我们在底层将用Java的序列化来透明的取代Kryo序列
化。所以，尽可能为每一个被序列化的类添加无参构造函数是一种最佳实践。

## ref
http://www.baeldung.com/kryo
