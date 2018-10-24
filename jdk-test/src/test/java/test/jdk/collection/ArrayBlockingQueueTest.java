package test.jdk.collection;

import java.time.LocalDateTime;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

/**
 * ArrayBlockingQueue 容量固定的阻塞式队列。
 * 默认fairness为false，即不保证线程插入的先后顺序，可以设为true - 会降低throughput，但有序了。 
 * <p>
 * LinkedBlockingQueue 性能一般比基于数组的队列高。也可以设定容量，默认int.max。
 * <p>
 * @author Administrator
 *
 */
public class ArrayBlockingQueueTest {

    @Test
    public void r1() {
        BlockingQueue<String> arrayBlockingQueue = new ArrayBlockingQueue<String>(1024);
        // arrayBlockingQueue.add(null);//NullPointerException
        try {
            // String take = arrayBlockingQueue.take(); // 会阻塞，如果empty
            String take = arrayBlockingQueue.poll(1000L, TimeUnit.MILLISECONDS); // 会阻塞指定时间，如果empty

            System.out.println(take + " @ " + LocalDateTime.now());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void r2() {
        BlockingQueue<String> linkedBlockingQueue = new LinkedBlockingQueue<String>();
        linkedBlockingQueue.add(null);// NullPointerException
    }
}
