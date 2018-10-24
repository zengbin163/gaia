package test.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * TODO 如需测试，最好从零开始，否则可能会影响结果。
 * <p>
 * TODO kafka，应该无法通过代码指定某个topic的分区数量（错误，可以使用AdminClient指定），只能手动创建，或者server.properties中指定num.partitions=N。
 * <p>
 * TODO consumer，有两种方式去消费 records，①subscribe，②assign & seek ，不能共存！
 * <p>
 * TODO 同一个group中，对应topic的一个partition的，仅有一个consumer；但是，一个consumer可以对应多个partition！
 * <p>
 * TODO 要注意，server.properties中有一个group.initial.rebalance.delay.ms=0，是为了开发测试用的，生产中需要设为3！！！
 * <p>
 * Created by zengbin on 2018/4/25.
 */
public class C01 {
    public static final Properties props = new Properties();
    public static final String topic1 = "my-topic";
    public static final String topic2 = "test-topic1";
    public static final String topic3 = "test-topic1";
    public static final String group1 = "test";
    public static final String group2 = "test-1";

    public static void initProducer(){
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        //0，代表producer不会等待broker确认 - 也就无法保证真的被持久化了，相应的，retries也就不会生效；每个record的offset都是-1。
        //1，代表leader写入后就会响应确认，但不会等待所有follower的反馈 - 有丢失的可能。
        //all或-1，最安全的措施！
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        //生成的records速度比发送速度快时，一般会将多个放入一个请求。
        //但是，有时候客户端为了减少请求次数，会故意设置一个等待时间，等待时间内的records会放入一个请求。
        //注意，优先级比BATCH_SIZE低。
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);//这么小，就是为了随机拼凑吧
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);//32MB
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
    }

    public static void initConsuemr(){
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, group1);//consumer group id
        //设为true，则consumer的offset会被周期性的提交！
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
    }

    ////////////////////////////////////
    @Test
    public void producer_demo() throws InterruptedException{// KafkaProducer javadoc中的例子
        initProducer();
        Producer<String, String> producer = new KafkaProducer<>(props);

        for(int i = 0; i < 100; i++){
            //关于topic，可以提前创建，也可以设置kafka自动创建新的topic
            ProducerRecord record = new ProducerRecord(topic1, Integer.toString(i), Integer.toString(i) + " >> " + LocalDateTime.now());
            producer.send(record);

            System.out.println("topic: " + record.topic());
            System.out.println("key: " + record.key());
            System.out.println("value: " + record.value());
            System.out.println("partition: " + record.partition());//null
            System.out.println("timestamp: " + record.timestamp());//null
//            record.headers();//null
            System.out.println("---------------");

            //再加两个topic
            if(i % 2 == 0){
                ProducerRecord record2 = new ProducerRecord(topic2, Integer.toString(i), Integer.toString(i) + " >> " + LocalDateTime.now());
                producer.send(record2);
            }
            if(i % 3 == 0){
                ProducerRecord record3 = new ProducerRecord(topic3, Integer.toString(i), Integer.toString(i) + " >> " + LocalDateTime.now());
                producer.send(record3);
            }

            Thread.sleep(500);
        }

        producer.close();
    }

    /**
     * TODO: 默认是从latest获取，怎么从beginning开始获取？
     * <p>
     * TODO: assign和subscribe，是互斥的？Subscription to topics, partitions and pattern are mutually exclusive
     */
    @Test
    public void consumer_demo_subscribe(){
        initConsuemr();
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        //TODO subscribe，是动态地获取分区。如果多次订阅，后面的会取代前面的。且，如果列表是empty，相当于unsubscribe！
        consumer.subscribe(Arrays.asList(topic1, topic2, topic3));

        //获取所有topic的信息，结果是个map，key是topic名字，value是PartitionInfo
        consumer.listTopics().forEach((k, v) -> {
            System.out.println("k: " + k);
            System.out.println("v: " + v);
            System.out.println("---------------");
        });

        //也可以拿到单个topic的信息
        List<PartitionInfo> partitionInfos = consumer.partitionsFor(topic1);
        System.out.println(partitionInfos);

        //FIXME 和下面的不能共存
//        TopicPartition p = new TopicPartition("my-topic", 0);
//        consumer.assign(Arrays.asList(p));
//        consumer.seek(p, 0);

        pollRecords(consumer, 100);

    }

    @Test
    public void consumer_demo_assign(){
        initConsuemr();
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        List<PartitionInfo> partitionInfos = consumer.partitionsFor(topic1);
        System.out.println(partitionInfos);

        TopicPartition p = new TopicPartition(topic1, 0);
        consumer.assign(Arrays.asList(p));//TODO 手动分配partition！注意，可以分配多个（隶属于不同的topic吧？）
        consumer.seek(p, 0);//重新定位offset

        pollRecords(consumer, 100);
        return;

    }

    public static void pollRecords(KafkaConsumer<String, String> consumer, long timeout){
        while(true){
//            System.out.println("consumer: " + consumer.hashCode());
            ConsumerRecords<String, String> records = consumer.poll(timeout);//poll，可以通过seek设置offset，默认latest
            records.forEach(e -> {
                System.out.println("topic: " + e.topic());
                System.out.println("key: " + e.key());
                System.out.println("value: " + e.value());
                System.out.println("partition: " + e.partition());
                System.out.println("offset: " + e.offset());
                System.out.println("timestamp: " + e.timestamp());
                System.out.println("timestampType: " + e.timestampType());
//                e.headers();
                System.out.println("---------------");
            });
        }
    }
}
