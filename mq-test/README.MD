# Message Queue Test

测试JMS。  
测试ActiveMQ、RabbitMQ、RocketMQ、ZeroMQ。

## JMS

http://blog.csdn.net/yinwenjie/article/details/50916518

p2p模式、pub/sub模式。

首先，JMS是接口规范，类似JDBC！

其次，消息、消息协议、消息队列！

消息协议，如STOMP、AMQP、XMPP、MQTT、Openwire等，都是应用层协议！同HTTP一样！

ActiveMQ支持多种协议！
RabbitMQ则是AMQP协议！该协议特有的pub/sub/exchange/virtual host/queue/channel等对象！

大部分消息中间件产品都支持JMS 接口规范。
也就是说，您可以使用JMS API来连接Stomp协议的产品（例如ActiveMQ）。
就像您可以使用JDBC API来连接ORACLE或者MYSQL一样。

注意，ActiveMQ，默认的协议是openwire！

同一个MQ中间件，如果实现了JMS还支持多协议，那可以在协议之间随意切换。
比如发送用a协议，接收用b协议。

## ActiveMQ

本模块关于ActiveMQ的测试，位于`test.jms.activemq`中。
仅仅测试了Queue、Topic基本用法。
并没有测试多个Producer|Consumer的情况！
也没用测试多个Publisher|Subscriber的情况！

