#练习本地cache、guava cache

###说明 进阶步骤
[1] TestConcurrentHashMapCache  
[2] TestConcurrentHashMapCache2  
[3] TestFutureCache  
[4] TestGuavaCache   

至于ThisTest，那是额外的，为了测试下匿名类和lambda中的this区别。  


CacheBuilder.newBuilder()后面能带一些设置回收的方法：

    (1)maximumSize(long)：设置容量大小，超过就开始回收。
    (2)expireAfterAccess(long, TimeUnit)：在这个时间段内没有被读/写访问，就会被回收。
    (3)expireAfterWrite(long, TimeUnit)：在这个时间段内没有被写访问，就会被回收 。
    (4)removalListener(RemovalListener)：监听事件，在元素被删除时，进行监听。


#参考
http://blog.csdn.net/congcong68/article/details/41146295
