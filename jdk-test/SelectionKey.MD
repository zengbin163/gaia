核心：选择键封装了特定的channel与特定的selector的注册关系。   
selectionKey = channel.register(selector, opsSet)  

java.nio.channels  

    public abstract class SelectionKey  
    extends Object  

A token representing the registration of a SelectableChannel with a Selector.  
代表了一个SelectableChannel带有Selector的注册。  

A selection key is created each time a channel is registered with a selector.  
每次一个channel被注册了一个selector都会创建一个selection key。  

A key remains valid until it is cancelled by invoking its cancel method, by closing its channel, or by closing its selector.   
除非调用其cancel方法，或者关闭其channel，或者关闭其selector，否则一个key一直有效。  

Cancelling a key does not immediately remove it from its selector;   
it is instead added to the selector's cancelled-key set for removal during the next selection operation.   
cancel一个key，不会立即从其selector中移除它；  
而是添加到selector的cancelled-key set中，等待下次selection操作再删除。  

The validity of a key may be tested by invoking its isValid method.   
一个key的有效性可以通过调用其isValid方法来测试。  

A selection key contains two operation sets represented as integer values.   
一个selection key包含两个操作集，用整数值表示。  

Each bit of an operation set denotes a category of selectable operations that are supported by the key's channel.  
一个操作集的每一个bit都意味着可选择操作的类型 - 由key的channel支持的。  
  
The interest set determines which operation categories will be tested for readiness the next time one of the selector's selection methods is invoked.   
兴趣集（interest set）决定了什么操作类型需要被测试可读性 - 在下一次selector的selection的某个方法被调用时。  

The interest set is initialized with the value given when the key is created; it may later be changed via the interestOps(int) method.  
兴趣集的初始化是使用key创建时提供的value；稍后可以通过interestOps(int)方法来更改。  

The ready set identifies the operation categories for which the key's channel has been detected to be ready by the key's selector.   
ready set 意味着key的channel已经被key的selector探测，且结果是ready。  

The ready set is initialized to zero when the key is created;   
it may later be updated by the selector during a selection operation, but it cannot be updated directly.  
ready set 被初始化成0；  
稍后可以在一个selection操作中被selector更新，但不能被直接更新。  

That a selection key's ready set indicates that its channel is ready for some operation category is a hint, 
but not a guarantee, 
that an operation in such a category may be performed by a thread without causing the thread to block.   
一个selection key的ready set指示其channel已经就绪，可以进行某些操作，但仅仅是一个暗示，而非保证！！！  
该类型的某个操作可能在某个线程中执行，而没有引起线程阻塞。  

A ready set is most likely to be accurate immediately after the completion of a selection operation.   
一个ready set在一个selection操作完成之后更精确（名副其实）。  

It is likely to be made inaccurate by external events and by I/O operations that are invoked upon the corresponding channel.  
在相应channel上执行的外部事件和I/O操作，可能会导致不精确。  

This class defines all known operation-set bits, but precisely which bits are supported by a given channel depends upon the type of the channel.   
该类定义了所有已知的操作集bits，但，具体的channel支持哪些bits却是取决于具体的channel类型。  

Each subclass of SelectableChannel defines an validOps() method which returns a set identifying just those operations that are supported by the channel.   
SelectableChannel的每个子类都定义了validOps()方法，该方法会返回该channel支持的操作。  

An attempt to set or test an operation-set bit that is not supported by a key's channel will result in an appropriate run-time exception.  
试图设置或者测试一个不被key的channel支持的操作集bit，会导致一个runtime异常。  

It is often necessary to associate some application-specific data with a selection key,   
for example an object that represents the state of a higher-level protocol and handles readiness notifications in order to implement that protocol.   
经常很有必要将某些应用特定的数据与一个selection key关联起来，  
例如，一个代表了高级协议状态的对象，也能够处理就绪通知，以便实现该协议。（啥意思？）  

Selection keys therefore support the attachment of a single arbitrary object to a key.   
因此，selection keys支持一个任意对象附加到一个key。  

An object can be attached via the attach method and then later retrieved via the attachment method.  
可以通过attach方法来附加一个对象，然后通过attachment方法获取到。

Selection keys are safe for use by multiple concurrent threads.   
selection keys在多并发线程中也是安全的。  

The operations of reading and writing the interest set will, in general, be synchronized with certain operations of the selector.   
对interest set的读、写操作，整体上，是与selector的某个特定操作同步的。  

Exactly how this synchronization is performed is implementation-dependent: 

    In a naive implementation, reading or writing the interest set may block indefinitely if a selection operation is already in progress;   
    in a high-performance implementation, reading or writing the interest set may block briefly, if at all.  
    In any case, a selection operation will always use the interest-set value that was current at the moment that the operation began.

Since:
1.4
See Also:
SelectableChannel, Selector


OP_READ = 1  
Suppose that a selection key's interest set contains OP_READ at the start of a selection operation.   
If the selector detects that the corresponding channel is ready for reading, has reached end-of-stream, has been remotely shut down for further reading, or has an error pending, then it will add OP_READ to the key's ready-operation set and add the key to its selected-key set.
如果selector探测到相应的channel：读就绪、流结尾、远程读关闭、或者错误发生，selector就会添加OP_READ到key的ready-operation set中去，同时，将key添加到它的selected-key set中去。  

OP_WRITE = 4  
Suppose that a selection key's interest set contains OP_WRITE at the start of a selection operation.   
If the selector detects that the corresponding channel is ready for writing, has been remotely shut down for further writing, or has an error pending, then it will add OP_WRITE to the key's ready set and add the key to its selected-key set.  
如果selector探测到相应的channel：写就绪、远程写关闭、或者错误发生，selector就会添加OP_WRITE到key的ready set中去，同时将key添加到它的selected-key set中去。  

OP_CONNECT = 8  
Suppose that a selection key's interest set contains OP_CONNECT at the start of a selection operation.   
If the selector detects that the corresponding socket channel is ready to complete its connection sequence, or has an error pending, then it will add OP_CONNECT to the key's ready set and add the key to its selected-key set.
如果selector探测到相应的socket channel：完成其链接序列就绪、或者错误发生，selector就会添加OP_CONNECT到key的ready set中去，同时将key添加到它的selected-key set中去。  

OP_ACCEPT = 16  
Suppose that a selection key's interest set contains OP_ACCEPT at the start of a selection operation.   
If the selector detects that the corresponding server-socket channel is ready to accept another connection, or has an error pending, then it will add OP_ACCEPT to the key's ready set and add the key to its selected-key set.
如果selector探测到相应的socket channel：接受另一个链接就绪、或者错误发生，selector就会添加OP_ACCEPT到key的ready set中去，同时将key添加到它的selected-key set中去。  


