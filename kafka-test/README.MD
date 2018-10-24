# kafka-test

- kafka，可以通过代码(AdminClient)创建topic，指定其分区数量、复制因子，或者手动创建，或者在server.properties中指定`num.partitions=N`。
- consumer，有两种方式去消费 records，①`subscribe`，②`assign` & `seek`，不能共存！
- 同一个group中，对应topic的一个partition的，仅有一个consumer；但是，一个consumer可以对应多个partition！
- 要注意，server.properties中有一个`group.initial.rebalance.delay.ms=0`，是为了开发测试用的，生产中需要设为3！！！

注意，参数可以在ProducerConfig/ConsumerConfig中找到，而且自带说明！

在kafka0.9.0版本的时候，开始启用了新的consumer config，这个新的consumer config采用`bootstrap.servers`替代之前版本的`zookeeper.connect`，主要是要渐渐弱化zk的依赖，把zk依赖隐藏到broker背后。

## group coordinator
使用bootstrap.servers替代之前版本的zookeeper.connect，相关的有如下两个改动：
1. 在 Server 端增加了 GroupCoordinator 这个角色
2. 将 topic 的 offset 信息由之前存储在 zookeeper(`/consumers/<group.id>/offsets/<topic>/<partitionId>`，zk写操作性能不高) 上改为存储到一个特殊的 topic 中（`__consumer_offsets`）

>从0.8.2版本开始Kafka开始支持将consumer的位移信息保存在Kafka内部的topic中（从0.9.0版本开始默认将offset存储到系统topic中）

>Coordinator一般指的是运行在broker上的group Coordinator，用于管理Consumer Group中各个成员，每个KafkaServer都有一个GroupCoordinator实例，管理多个消费者组，主要用于offset位移管理和Consumer Rebalance。

## rebalance时机
在如下条件下，partition要在consumer中重新分配：
- 条件1：有新的consumer加入
- 条件2：旧的consumer挂了
- 条件3：coordinator挂了，集群选举出新的coordinator
- 条件4：topic的partition新加
- 条件5：consumer调用unsubscrible()，取消topic的订阅
   
## __consumer_offsets
Consumer通过发送OffsetCommitRequest请求到指定broker（偏移量管理者）提交偏移量。
这个请求中包含一系列分区以及在这些分区中的消费位置（偏移量）。
偏移量管理者会追加键值（key－value）形式的消息到一个指定的topic（`__consumer_offsets`）。key是由consumerGroup-topic-partition组成的，而value是偏移量。

内存中也会维护一份最近的记录，为了在指定key的情况下能快速的给出OffsetFetchRequests而不用扫描全部偏移量topic日志。
如果偏移量管理者因某种原因失败，新的broker将会成为偏移量管理者并且通过扫描偏移量topic来重新生成偏移量缓存。

#
[聊聊kafka的group coordinator](https://segmentfault.com/a/1190000011441747)


## 关于record的key
- 如果你指定了分区，那消息会发到指定的分区上
- 如果没指定分区，但指定了key，那相同的key会发送到同一个分区上（hash）
- 如果都没指定，那就轮流发送