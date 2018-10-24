package test.jdk.thread.atomic;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAdder;

/**
 * * 原子值是乐观锁机制，多个线程读取原子值时，会反复重试（while(..)），线程数太多会导致性能严重降低。
 * 为此，Java 8 提供了LongAdder和LongAccumulator。
 * LongAdder由多个变量组成，所有变量的和即为当前值。
 * 当存在高度竞争时，请使用LongAdder代替AtomicLong。
 * LongAccumulator更加灵活，可以自行指定操作。可以认为LongAdder是LongAccumulator的一个特例。
 * <p>
 * Created by zengbin on 2017/10/6.
 */
public class LongAdderTest {

    // 如果中间不需要读取，只需要设置，那就可以用LongAdder代替AtomicInteger。
    @Test
    public void r1() throws InterruptedException{
        LongAdder adder = new LongAdder();

        ExecutorService pool = Executors.newFixedThreadPool(100);
        for(int i = 0; i < 100; i++){
            final int tmp = i;
            pool.submit(() -> {
                if(tmp % 2 == 1){
                    // adder.increment();// 见源码，等同于下面
                    adder.add(1);
                }
            });
        }

        Thread.sleep(2000);
        long sum = adder.sum();
        System.out.println(sum);
    }
}
