# zookeeper test

## 关于 zookeeper
强一致性，监听、通知。

尽管ZooKeeper可以被看做是一个文件系统，但是处于便利，摒弃了一些文件系统地操作原语。因为文件非常的小并且使整体读写的，所以不需要打开、关闭或是寻地的操作。 

ZooKeeper所提供的服务主要是通过：数据结构Znode+原语+watcher机制，三个部分来实现的。

1) 引用方式
    
    Zonde通过路径引用，如同Unix中的文件路径。路径必须是绝对的，因此他们必须由斜杠字符来开头。除此以外，他们必须是唯一的，也就是说每一个路径只有一个表示，因此这些路径不能改变。在ZooKeeper中，路径由Unicode字符串组成，并且有一些限制。字符串"/zookeeper"用以保存管理信息，比如关键配额信息。

2) Znode结构

    ZooKeeper命名空间中的Znode，兼具文件和目录两种特点。既像文件一样维护着数据、元信息、ACL、时间戳等数据结构，又像目录一样可以作为路径标识的一部分。图中的每个节点称为一个Znode。 每个Znode由3部分组成:
    
        - ① stat：此为状态信息, 描述该Znode的版本, 权限等信息 
        - ② data：与该Znode关联的数据
        - ③ children：该Znode下的子节点

    ZooKeeper虽然可以关联一些数据，但并没有被设计为常规的数据库或者大数据存储，相反的是，它用来管理调度数据，比如分布式应用中的配置文件信息、状态信息、汇集位置等等。这些数据的共同特性就是它们都是很小的数据，通常以KB为大小单位。ZooKeeper的服务器和客户端都被设计为严格检查并限制每个Znode的数据大小至多1M，但常规使用中应该远小于此值。

3) 数据访问

    ZooKeeper中的每个节点存储的数据要被原子性的操作。也就是说读操作将获取与节点相关的所有数据，写操作也将替换掉节点的所有数据。另外，每一个节点都拥有自己的ACL(访问控制列表)，这个列表规定了用户的权限，即限定了特定用户对目标节点可以执行的操作。

4) 节点类型

    ZooKeeper中的节点有两种，分别为**临时节点**和**永久节点**。__节点的类型在创建时即被确定，并且不能改变__。

    - ① 临时节点：该节点的生命周期依赖于创建它们的会话。一旦会话(Session)结束，临时节点将被自动删除，当然可以也可以手动删除。虽然每个临时的Znode都会绑定到一个客户端会话，但他们**对所有的客户端还是可见的**。另外，ZooKeeper的临时节点不允许拥有子节点。
    - ② 永久节点：该节点的生命周期不依赖于会话，并且只有在客户端显示执行删除操作的时候，他们才能被删除。

5) 顺序节点

    当创建Znode的时候，用户可以请求在ZooKeeper的路径结尾添加一个**递增的计数**。这个计数对于**此节点的父节点**来说是唯一的，它的格式为"%10d"(10位数字，没有数值的数位用0补充，例如"0000000001")。当计数值大于2^32-1时，计数器将溢出。

6) 观察

    客户端可以在节点上设置watch，我们称之为监视器。当节点状态发生改变时(Znode的增、删、改)将会触发watch所对应的操作。当watch被触发时，ZooKeeper将会向客户端发送且仅发送一条通知，因为**watch只能被触发一次**，这样可以减少网络流量。

## ZooKeeper中的时间

ZooKeeper有多种记录时间的形式，其中包含以下几个主要属性：

1) Zxid

    致使ZooKeeper节点状态改变的每一个操作都将使节点接收到一个Zxid格式的时间戳，并且这个时间戳全局有序。也就是说，也就是说，每个对 节点的改变都将产生一个唯一的Zxid。如果Zxid1的值小于Zxid2的值，那么Zxid1所对应的事件发生在Zxid2所对应的事件之前。实际 上，ZooKeeper的每个节点维护者三个Zxid值，为别为：cZxid、mZxid、pZxid。

    ① cZxid： 是节点的创建时间所对应的Zxid格式时间戳。
    ② mZxid：是节点的修改时间所对应的Zxid格式时间戳。

    实现中Zxid是一个64为的数字，它**高32位是epoch用来标识leader关系是否改变，每次一个leader被选出来，它都会有一个 新的epoch**。**低32位是个递增计数**。 

2) 版本号

    **对节点的每一个操作都将致使这个节点的版本号增加**。每个节点维护着三个版本号，他们分别为：
    
    - ① version：节点数据版本号
    - ② cversion：子节点版本号
    - ③ aversion：节点所拥有的ACL版本号 

- **推荐**[ZooKeeper学习第一期---Zookeeper简单介绍](http://www.cnblogs.com/sunddenly/p/4033574.html)
- [Zookeeper可以干什么](https://www.cnblogs.com/yuyijq/p/3424473.html)


- [ZooKeeper 权限管理](https://blog.csdn.net/yueyedeai/article/details/17106147)
- [ACL权限控制遇到第二级目录no auth的问题](https://blog.csdn.net/simonchi/article/details/51144436)
- [zookeeper acl权限](https://blog.csdn.net/wuhenzhangxing/article/details/52936040)


- [Zookeeper数据与存储](https://www.cnblogs.com/leesf456/p/6179118.html)
- [Zookeeper的功能以及工作原理](https://www.cnblogs.com/felixzh/p/5869212.html)

## 客户端
目前流行的Zookeeper Java客户端有两个：ZkClient和Curator。

ZkClient：明显不同于zookeeper官方api的地方在于，将一次性的watcher注册改成了持久性的。
其实还是一次性的，只不过底层自动再注册。

ZkClient:
- https://blog.csdn.net/wo541075754/article/details/68929512
- https://blog.csdn.net/done58/article/details/51014846

Curator:
- https://blog.csdn.net/wo541075754/article/details/69138878
- http://blueyan.iteye.com/blog/2298276


