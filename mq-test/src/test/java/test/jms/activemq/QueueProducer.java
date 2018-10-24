package test.jms.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.time.LocalDateTime;

/**
 * TODO 说明，配合window版本的activeMQ（命令：activemq.bat start），可以直接测试！
 * <p>
 * Created by zengbin on 2018/3/17.
 */
public class QueueProducer {
    private static final String BROKER_URL = "tcp://127.0.0.1:61616";
    private static final String QUEUE_A = "queue-test-a";


    @Test
    public void provider1() throws JMSException, InterruptedException{
        //1. connection factory
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(BROKER_URL);

        //2. connection
        // Connection connection = activeMQConnectionFactory.createConnection();//可以用下面的，更准确
        QueueConnection queueConnection = activeMQConnectionFactory.createQueueConnection(); //嗯？？仅用于Queue而已

        //3. connection start
        queueConnection.start();

        //4. create session TODO 如果开启事务的话，可能收不到消息，原因待查
        QueueSession queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);//boolean transacted, int acknowledgeMode

        //5. 使用session创建 queue - TODO 也可以不使用，而直接创建；区别在于，Producer或Consumer能否直接发送消息！
        Queue queue = queueSession.createQueue(QUEUE_A);

        //6. create provider through session
        MessageProducer producer = queueSession.createProducer(queue);

        //7. 使用session创建消息，共有5种：Stream/Map/Text/Object/Bytes
        for(int i = 0; i < 100; i++){
            TextMessage textMessage = queueSession.createTextMessage("from producer a @" + LocalDateTime.now());
            producer.send(textMessage);
            producer.send(queue, textMessage);//TODO 注意区别！

            Thread.sleep(1000L*2);
        }

        //8. 关闭资源
        producer.close();
        queueSession.close();
        queueConnection.close();
    }
}
