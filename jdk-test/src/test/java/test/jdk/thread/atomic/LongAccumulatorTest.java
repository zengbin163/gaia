package test.jdk.thread.atomic;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAccumulator;

/**
 * * 原子值是乐观锁机制，多个线程读取原子值时，会反复重试（while(..)），线程数太多会导致性能严重降低。
 * 为此，Java 8 提供了LongAdder和LongAccumulator。
 * LongAdder由多个变量组成，所有变量的和即为当前值。
 * 当存在高度竞争时，请使用LongAdder代替AtomicLong。
 * LongAccumulator更加灵活，可以自行指定操作。可以认为LongAdder是LongAccumulator的一个特例。
 * <p>
 * Created by zengbin on 2017/10/6.
 */
public class LongAccumulatorTest {
    @Test
    public void r1() throws InterruptedException{
        // 注意这个构造方法，需要提供一个二元Long操作，以及一个中立元素。操作必须是可交换的，即结果与组合顺序无关
        LongAccumulator accumulator = new LongAccumulator(Long::sum, 0);
        ExecutorService pool = Executors.newFixedThreadPool(100);
        for(int i = 0; i < 100; i++){
            final int tmp = i;
            pool.submit(() -> {
                if(tmp % 2 == 1){
                    accumulator.accumulate(1);
                }
            });
        }

        Thread.sleep(2000);
        long result = accumulator.get();
        System.out.println(result);
    }

    @Test
    public void xx(){
        int a = 3, b = 4, c = 5;
        int x;
        int y;
        x = y = b;
        System.out.println(x + " - " + y);
    }
}
