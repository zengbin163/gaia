package test.jms.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import java.io.IOException;

/**
 * Created by zengbin on 2018/3/17.
 */
public class TopicSub {
    private static final String BROKER_URL = "tcp://127.0.0.1:61616";
    private static final String TOPIC_A = "topic-test-a";

    @Test
    public void sub() throws JMSException, IOException{
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
        TopicConnection topicConnection = activeMQConnectionFactory.createTopicConnection();
        topicConnection.start();

        TopicSession topicSession = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

        Topic topic = topicSession.createTopic(TOPIC_A);
        TopicSubscriber subscriber = topicSession.createSubscriber(topic);

        subscriber.setMessageListener(msg -> {
            TextMessage message = (TextMessage) msg;
            System.out.println(message);
        });

        System.in.read();

        subscriber.close();
        topicSession.close();
        topicConnection.close();
    }
}
