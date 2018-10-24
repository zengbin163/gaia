package test.jdk.collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * LinkedBlockingQueue 内部有序，FIFO，呆的最久的元素就是head，呆的时间最少的元素就是tail。 新加入的元素都是追加倒tail。
 * 容量最大为int的最大值。 
 * 性能一般比基于数组的阻塞式队列高。
 * TODO 注意，所有BlockingQueue实现的操作在不满足条件时都有四种类型：异常；返回指定值（null/false）；等待；等待指定时间。
 * <p>
 * Created by 张少昆 on 2018/2/8.
 */
public class LinkedBlockingQueueTest {
    private LinkedBlockingQueue<Integer> linkedBlockingQueue = null;

    @Before
    public void init() {
        linkedBlockingQueue = new LinkedBlockingQueue<>(10);

        for (int i = 0; i < 10; i++) {
            linkedBlockingQueue.add(i);
        }
    }

    @After
    public void after() {
        System.out.println("size: " + linkedBlockingQueue.size());
        System.out.println("remainingCapacity: " + linkedBlockingQueue.remainingCapacity());
        System.out.println(linkedBlockingQueue);
    }

    @Test
    public void r1() throws InterruptedException {
        System.out.println(linkedBlockingQueue);
        System.out.println(linkedBlockingQueue.peek()); // 获取head，但不移除
        System.out.println(linkedBlockingQueue.poll()); // 获取head，并移除
        System.out.println(linkedBlockingQueue.poll(3L, TimeUnit.SECONDS)); // 获取head，并移除 -- 最多等待三秒。如果超出仍无满足，返回null。
        System.out.println(linkedBlockingQueue.take()); // 获取head，并移除。如果需要，可以等待直到元素可用
        System.out.println(linkedBlockingQueue.remove());// 如果empty，异常。否则同poll

    }

    @Test
    public void put() throws InterruptedException {
        // 如果没有足够的空间，就等待。否则同add
        linkedBlockingQueue.put(999);

        // FIXME 应该使用多线程啊
        linkedBlockingQueue.poll();
    }

    @Test
    public void offer() throws InterruptedException {
        System.out.println(linkedBlockingQueue.offer(777));
        System.out.println(linkedBlockingQueue.offer(888, 1000 * 5, TimeUnit.MICROSECONDS));
    }

}
