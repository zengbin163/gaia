package test.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.QueueConnection;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.TopicConnection;

/**
 * Created by 张少昆 on 2018/3/14.
 */
public class JmsTest {
    @Test
    public void r1(){
        // 定义JMS-ActiveMQ连接信息 TODO 默认为Openwire协议
        // ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616", "username", "password");
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
        ConnectionFactory connectionFactory = activeMQConnectionFactory; //TODO 就是为了说明JMS是接口规范

        System.out.println(ActiveMQConnectionFactory.DEFAULT_BROKER_URL); //failover://tcp://localhost:61616 TODO 注意，不是写死的
        System.out.println(ActiveMQConnectionFactory.DEFAULT_BROKER_BIND_URL); //tcp://localhost:61616 TODO 注意，不是写死的
        try{
            //进行连接
            Connection connection = connectionFactory.createConnection();//在此之前 可以有其他设置
            connection.start(); //TODO 不需要中间设置吗？

            QueueConnection queueConnection = activeMQConnectionFactory.createQueueConnection();
            TopicConnection topicConnection = activeMQConnectionFactory.createTopicConnection();

            //建立会话
            Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
            //建立queue（当然如果有了就不会重复建立）
            Destination sendQueue = session.createQueue("");
            //建立消息发送者对象
            MessageProducer sender = session.createProducer(sendQueue);
            TextMessage outMessage = session.createTextMessage();
            outMessage.setText("这是发送的消息内容");

            //发送（JMS是支持事务的）
            sender.send(outMessage);
            session.commit();

            //关闭
            sender.close();
            connection.close();
            // connectionFactory.close();//FIXME 没有这个方法了
        } catch(JMSException e){
            e.printStackTrace();
        }

    }
}
