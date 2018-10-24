# rpc test

rpc原理：
1. 传输层 TCP
2. 消息协议 HTTP/WS/DUBBO/GRPC/RMI...
3. Proxy - 这个要注意
4. App Client/Server

Server需要发布服务，Client需要调用服务！

1. 首先，`pojo` 和 `service api` 是通用的。
2. 其次，`service` 是通过 `server` 发布的，其原理就是作为web服务 单独开启！。
3. 第三，`client` 通过 `proxy` 调用 `server` 的 `service`，代理内部通过远程访问获取结果。

本模块：
1. `test.rpc.api` 是共用模块。
    1. `HelloService` 是服务接口。
    2. `Message` 是消息格式。
2. `test.rpc.pojo` 是共用的实体类。
3. `test.rpc.server` 是服务端模块。
    1. `HelloServiceImpl` 是服务实现。
    2. `Publisher` 是服务发布器 - 本质上是通过web服务开启接口的调用和输出。
    3. `Main` 启动。
4. `test.rpc.client` 是客户端模块。
    1. `TransferLayer` 是传输层方法，也就是负责消息的发送和接收（同时负责序列化、反序列化）。
    2. `MessageInvocationHandler` 是代理需要的实际处理器，负责将传输层的内容转成方法需要的内容。
    3. `Main` 启动类 - 构建了一个JDK代理，然后调用相应的方法！