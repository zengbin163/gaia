java.nio.channels  

    public abstract class Selector
    extends Object
    implements Closeable

A multiplexor of SelectableChannel objects.  
SelectableChannel对象们的一个多路复用器。  

A selector may be created by invoking the open method of this class, which will use the system's default selector provider to create a new selector.   
A selector may also be created by invoking the openSelector method of a custom selector provider.  
A selector remains open until it is closed via its close method. 
通过调用本类的open方法，可以创建一个selector，使用的是系统默认的selector provider创建的。  
一个selector也可以通过调用自定义的selector provider的openSelector方法来创建。 
除非调用其close方法，否则selector持续open。  

A selectable channel's registration with a selector is represented by a SelectionKey object.   
A selector maintains three sets of selection keys:  

    The key set contains the keys representing the current channel registrations of this selector. This set is returned by the keys method.
    The selected-key set is the set of keys such that each key's channel was detected to be ready for at least one of the operations identified in the key's interest set during a prior selection operation. This set is returned by the selectedKeys method. The selected-key set is always a subset of the key set.
    The cancelled-key set is the set of keys that have been cancelled but whose channels have not yet been deregistered. This set is not directly accessible. The cancelled-key set is always a subset of the key set.
All three sets are empty in a newly-created selector.    

在一个selectable channel上注册一个selector，这种行为是由SelectionKey对象描述的。  
一个selector维护了selection keys的三个集：

    key set 包含了那些代表当前channel对本selector的注册。 该集合由keys方法返回。  
    selected-key set 是



A key is added to a selector's key set as a side effect of registering a channel via the channel's register method. Cancelled keys are removed from the key set during selection operations. The key set itself is not directly modifiable.
A key is added to its selector's cancelled-key set when it is cancelled, whether by closing its channel or by invoking its cancel method. Cancelling a key will cause its channel to be deregistered during the next selection operation, at which time the key will removed from all of the selector's key sets. 
Keys are added to the selected-key set by selection operations. A key may be removed directly from the selected-key set by invoking the set's remove method or by invoking the remove method of an iterator obtained from the set. Keys are never removed from the selected-key set in any other way; they are not, in particular, removed as a side effect of selection operations. Keys may not be added directly to the selected-key set.

Selection
During each selection operation, keys may be added to and removed from a selector's selected-key set and may be removed from its key and cancelled-key sets. Selection is performed by the select(), select(long), and selectNow() methods, and involves three steps:
Each key in the cancelled-key set is removed from each key set of which it is a member, and its channel is deregistered. This step leaves the cancelled-key set empty.
The underlying operating system is queried for an update as to the readiness of each remaining channel to perform any of the operations identified by its key's interest set as of the moment that the selection operation began. For a channel that is ready for at least one such operation, one of the following two actions is performed:
If the channel's key is not already in the selected-key set then it is added to that set and its ready-operation set is modified to identify exactly those operations for which the channel is now reported to be ready. Any readiness information previously recorded in the ready set is discarded.
Otherwise the channel's key is already in the selected-key set, so its ready-operation set is modified to identify any new operations for which the channel is reported to be ready. Any readiness information previously recorded in the ready set is preserved; in other words, the ready set returned by the underlying system is bitwise-disjoined into the key's current ready set.
If all of the keys in the key set at the start of this step have empty interest sets then neither the selected-key set nor any of the keys' ready-operation sets will be updated.
If any keys were added to the cancelled-key set while step (2) was in progress then they are processed as in step (1).
Whether or not a selection operation blocks to wait for one or more channels to become ready, and if so for how long, is the only essential difference between the three selection methods.
Concurrency
Selectors are themselves safe for use by multiple concurrent threads; their key sets, however, are not.
The selection operations synchronize on the selector itself, on the key set, and on the selected-key set, in that order. They also synchronize on the cancelled-key set during steps (1) and (3) above.
Changes made to the interest sets of a selector's keys while a selection operation is in progress have no effect upon that operation; they will be seen by the next selection operation.
Keys may be cancelled and channels may be closed at any time. Hence the presence of a key in one or more of a selector's key sets does not imply that the key is valid or that its channel is open. Application code should be careful to synchronize and check these conditions as necessary if there is any possibility that another thread will cancel a key or close a channel.
A thread blocked in one of the select() or select(long) methods may be interrupted by some other thread in one of three ways:
By invoking the selector's wakeup method,
By invoking the selector's close method, or
By invoking the blocked thread's interrupt method, in which case its interrupt status will be set and the selector's wakeup method will be invoked.
The close method synchronizes on the selector and all three key sets in the same order as in a selection operation. 
A selector's key and selected-key sets are not, in general, safe for use by multiple concurrent threads. If such a thread might modify one of these sets directly then access should be controlled by synchronizing on the set itself. The iterators returned by these sets' iterator methods are fail-fast: If the set is modified after the iterator is created, in any way except by invoking the iterator's own remove method, then a java.util.ConcurrentModificationException will be thrown.
Since:
1.4
See Also:
SelectableChannel, SelectionKey