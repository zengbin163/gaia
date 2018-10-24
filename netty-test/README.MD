# netty test

Netty是一个高性能、**异步事件驱动**的NIO框架，它提供了对TCP、UDP和文件传输的支持；作为一个异步NIO框架，Netty的所有IO操作都是异步非阻塞的，通过`Future-Listene`r机制，用户可以方便的 **主动获取**或者 **通过通知机制**获得IO操作结果。

##
1. 传输
2. 协议
3. 线程

##
### 异步非阻塞通信
Netty的IO线程`NioEventLoop`由于聚合了多路复用器Selector，可以同时并发处理成百上千个客户端Channel，由于读写操作都是非阻塞的，这就可以充分提升IO线程的运行效率，避免由于频繁IO阻塞导致的线程挂起。

另外，由于Netty采用了异步通信模式，一个IO线程可以并发处理N个客户端连接和读写操作，这从根本上解决了传统同步阻塞IO一连接一线程模型，架构的性能、弹性伸缩能力和可靠性都得到了极大的提升。

### 零拷贝
Netty的“零拷贝”主要体现在如下三个方面：
1. Netty的接收和发送ByteBuffer采用DIRECT BUFFERS，使用堆外直接内存进行Socket读写，不需要进行字节缓冲区的二次拷贝。如果使用传统的堆内存（HEAP BUFFERS）进行Socket读写，JVM会将堆内存Buffer拷贝一份到直接内存中，然后才写入Socket中。相比于堆外直接内存，消息在发送过程中多了一次缓冲区的内存拷贝。

2. Netty提供了组合Buffer对象，可以聚合多个ByteBuffer对象，用户可以像操作一个Buffer那样方便的对组合Buffer进行操作，避免了传统通过内存拷贝的方式将几个小Buffer合并成一个大的Buffer。

3. Netty的文件传输采用了transferTo方法，它可以直接将文件缓冲区的数据发送到目标Channel，避免了传统通过循环write方式导致的内存拷贝问题。

>java.nio.channels.FileChannel#transferTo/From

>**很多操作系统可以直接将文件缓冲区的内容发送到目标Channel中，而不需要通过拷贝的方式，这是一种更加高效的传输方式，它实现了文件传输的“零拷贝”**。

### 内存池
随着JVM虚拟机和JIT即时编译技术的发展，对象的分配和回收是个非常轻量级的工作。但是对于缓冲区Buffer，情况却稍有不同，特别是对于堆外直接内存的分配和回收，是一件耗时的操作。为了尽量重用缓冲区，Netty提供了基于内存池的缓冲区重用机制。

一直到4.1.24，默认的Allocator都是 `UnpooledByteBufAllocator`，看下面的源码。（据说5.x已经改成了PooledByteBufAllocator）

### 高效的Reactor线程模型

### 无锁化的串行设计理念

### 高效的并发编程

### 高性能的序列化框架

### 灵活的TCP参数配置能力

## 关于Channel/ChannelPipeline/ChannelInitializer
- Encoder是用于输出的，所以是ChannelOutboundHandler部分。
- Decoder是用于输入的，所以是ChannelInboundHandler部分。

因此，
- channel.pipeline.addLast(encoder, somehandler)
- channel.pipeline.addLast(decoder, somehandler)

## 推荐参考
- [ByteBuf源码分析](https://blog.csdn.net/summerZBH123/article/details/80220215)
- [Netty4详解三：Netty架构设计](https://blog.csdn.net/suifeng3051/article/details/28861883)

## HTTP Server
根据下面的资料，HTTP部分之所以分成这么多，就是为了让用户选择是否使用`HttpObjectAggregator`，如果使用，则会得到整体的数据，否则一次只会得到部分。

https://stackoverflow.com/questions/29213793/how-to-use-httprequest-fullhttprequest-httpmessage-fullhttpmessage-an

    HttpRequest
    FullHttpRequest
    HttpMessage
    FullHttpMessage
    HttpResponse
    FullHttpResponse
    LastHttpContent

When an HTTP message is decoded by an HttpObjectDecoder, the decoder produces the following objects:

1. An `HttpRequest` or an `HttpResponse` that provides the properties decoded from the initial line and its following headers.
2. A **series** of `HttpContent`. **The last HttpContent is `LastHttpContent`**.

A typical handler code will look like the following:
```java
if (msg instanceof HttpRequest) {
    //...
}
if (msg instanceof HttpContent) {
    //...
    if (msg instanceof LastHttpContent) {
        //...
    }
}
```

Please note that the `if` blocks are not mutually exclusive and thus the handler does not return when one of the 3 conditions above is met. Take a look into [`HttpSnoopServerHandler`](https://github.com/netty/netty/blob/4.1/example/src/main/java/io/netty/example/http/snoop/HttpSnoopServerHandler.java#L60) for a concrete example.

Now, let's take a look at `FullHttpRequest`. It implements `HttpRequest`, `HttpContent`, and `LastHttpContent`. The well-written handler should just work when we inserted `HttpObjectAggregator` into the pipeline without changing any code.

>So, the intention of this weird-looking class hierarchy is to enable a user to choose to use `HttpObjectAggregator` or not.

However, I do agree this is not intuitive. We are going to fix this in Netty 5 in such a way that the decoder produces only a single HTTP message object and streaming its content to it later. 

>一个Channel会对应一个EventLoop，而一个EventLoop会对应着一个线程，也就是说，仅有一个线程在负责一个Channel的IO操作。

我们知道InboundHandler和OutboundHandler在ChannelPipeline中是混合在一起的，那么它们如何区分彼此呢？
其实很容易，因为它们各自实现的是不同的接口，对于inbound event，Netty会自动跳过OutboundHandler,相反若是outbound event，ChannelInboundHandler会被忽略掉。

当一个ChannelHandler被加入到ChannelPipeline中时，它便会获得一个ChannelHandlerContext的引用，而ChannelHandlerContext可以用来读写Netty中的数据流。
因此，现在可以有两种方式来发送数据，一种是把数据直接写入Channel，一种是把数据写入ChannelHandlerContext。
>它们的区别是写入Channel的话，数据流会从Channel的头开始传递，而如果写入ChannelHandlerContext的话，数据流会流入管道中的下一个Handler。  
