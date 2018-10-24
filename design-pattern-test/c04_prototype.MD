# prototype 原型模式/克隆模式

深拷贝、浅拷贝
1. Object.clone()
    - Cloneable接口
    - Java的 clone() 替我们坐了大部分工作。

2. 利用反射和反序列化实现深拷贝

    ```java
    // JDK序列化
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    ObjectOutputStream oos = new ObjectOutputStream(bos);
    
    oos.writeObject(obj);
    byte[] bs = bos.toByteArray();
 
    // JDK反序列化
    ByteArrayInputStream bis = new ByteArrayInputStream();
    ObjectInputStream ois = new ObjectInputStream(bis);
    
    Object obj = ois.readObject(bs);
    ```