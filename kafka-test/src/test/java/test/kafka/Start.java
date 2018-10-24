package test.kafka;

import kafka.producer.KeyedMessage;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by zengbin on 2017/10/19.
 */
public class Start {
	private static final String HOST = "192.168.101.252:9092";
	private static KafkaProducer producer;

	@Before
	public void setUp(){
		Properties props = new Properties();
		props.put("bootstrap.servers", HOST);
		props.put("acks", "all");
		props.put("retries", 0);
		props.put("batch.size", 16384);
		props.put("linger.ms", 1);
		props.put("buffer.memory", 33554432);
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		producer = new KafkaProducer(props);
	}

	@Test
	public void r() throws InterruptedException, ExecutionException{
		for(int i = 0; i < 1000; i++){
			Future future = producer.send(new ProducerRecord<String, String>("test2", Integer.toString(i), "value[" + Integer.toString(i) + "]"));
			Thread.sleep(100);
			if(i % 5 == 0)
				producer.flush();
			System.out.println("::::::" + future.get());
		}
		System.out.println(producer.metrics());
		producer.close();
	}

	@Test
	public void r1(){
		String topic = "learn.topic";
		String messageStr = "This is a simple message from JavaAPI Producer2";

		///Key如何生成的？ - 相同的key发送到相同的分区上
//		KeyedMessage<Integer, String> data = new KeyedMessage<Integer, String>(topic, messageStr);
//		producer.send(data);
		producer.close();
	}
}
