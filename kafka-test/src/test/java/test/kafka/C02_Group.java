package test.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.Test;

import java.util.Arrays;

/**
 * TODO 根据测试，一个topic partition 只能照顾同一个group中的一个consumer，而且不会轮流改变（但可能会被新加入的consumer抢去）！
 * <p>
 * TODO 当然，不同group则彼此不会影响！
 * <p>
 * Created by zengbin on 2018/4/26.
 */
public class C02_Group {

    //多次启动
    @Test
    public void consumerGroup(){
        C01.initConsuemr();
        C01.props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, C01.group1);
        KafkaConsumer consumer = new KafkaConsumer(C01.props);

        consumer.subscribe(Arrays.asList(C01.topic1, C01.topic2, C01.topic3));

        while(true){
            C01.pollRecords(consumer, 100);
        }

    }

    @Test
    public void consumerGroup2(){
        C01.initConsuemr();
        C01.props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, C01.group1);
        KafkaConsumer consumer = new KafkaConsumer(C01.props);

        consumer.subscribe(Arrays.asList(C01.topic1));

        while(true){
            C01.pollRecords(consumer, 100);
        }

    }

    @Test
    public void consumerGroup3(){
        C01.initConsuemr();
        C01.props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, C01.group1);
        KafkaConsumer consumer = new KafkaConsumer(C01.props);

        consumer.subscribe(Arrays.asList(C01.topic2));

        while(true){
            C01.pollRecords(consumer, 100);
        }

    }

    @Test
    public void consumerGroup4(){
        C01.initConsuemr();
        C01.props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, C01.group1);
        KafkaConsumer consumer = new KafkaConsumer(C01.props);

        consumer.subscribe(Arrays.asList(C01.topic3));

        while(true){
            C01.pollRecords(consumer, 100);
        }
    }

    @Test
    public void consumerGroup5(){
        C01.initConsuemr();
        C01.props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, C01.group2);
        KafkaConsumer consumer = new KafkaConsumer(C01.props);

        consumer.subscribe(Arrays.asList(C01.topic3));

        while(true){
            C01.pollRecords(consumer, 100);
        }
    }
}
