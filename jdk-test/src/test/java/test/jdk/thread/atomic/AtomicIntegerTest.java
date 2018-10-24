package test.jdk.thread.atomic;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 原子值是乐观锁机制，多个线程读取原子值时，会反复重试（while(..)），线程数太多会导致性能严重降低。
 * 为此，Java 8 提供了LongAdder和LongAccumulator。
 * LongAdder由多个变量组成，所有变量的和即为当前值。
 * 当存在高度竞争时，请使用LongAdder代替AtomicLong。
 * <p>
 * Created by 张少昆 on 2017/10/6.
 */
public class AtomicIntegerTest {

    // look，尽管没有同步，但还是不会出现重复数字，这就是所谓的原子数 -- 其实内部有同步
    @Test
    public void r1() throws InterruptedException{
        AtomicInteger atomicInteger = new AtomicInteger();

        for(int i = 0; i < 10; i++){
            new Thread(() -> {
                for(int j = 0; j < 3; j++){

                    try{
                        Thread.sleep((long) (Math.random() * 1 * 1000));
                    } catch(InterruptedException e){
                        e.printStackTrace();
                    }
                    // System.out.println(atomicInteger.incrementAndGet());// 自增，然后获取
                    // System.out.println(atomicInteger.addAndGet(2));// +2，然后获取
                    // System.out.println(atomicInteger.decrementAndGet());// 自减，然后获取
                    // System.out.println(atomicInteger.getAndIncrement());// 获取，然后自增
                    System.out.println(atomicInteger.getAndDecrement());// 获取，然后自减
                }
            }).start();
        }
        Thread.sleep(1000 * 15L);
    }

    // CAS - 应该模拟一种场景，例如变量x不断产生新值，要求atomicInteger必须在自身和x中选取大的那个
    // 由于atomicInteger用于多线程环境下，获取、判断And设置是两个操作，会因为竞争而导致问题，所以必须使用下面的方式来设置！！！
    // Java 8中还有更简单的写法，见r3()
    @Test
    public void r2() throws InterruptedException{
        AtomicInteger atomicInteger = new AtomicInteger();
        // 多个线程，随机数
        for(int i = 0; i < 100; i++){
            final int tmp = i;
            final int observed = tmp * tmp - 12 * tmp + 36;//
            new Thread(() -> {
                int oldVal;
                int newVal;
                do{
                    oldVal = atomicInteger.get();
                    newVal = Math.max(oldVal, observed);// 这一步，没必要吧？？？直接compareAndSet(oldVal, observed) 不行吗？
                    System.out.println("time-" + tmp + ": " + oldVal + " - " + newVal);
                } while(!atomicInteger.compareAndSet(oldVal, newVal));// WTF???
                System.out.println("time-" + tmp + ": " + atomicInteger.get());// 这个和上面的赋值已经是两个操作了

                try{
                    Thread.sleep(1000);
                } catch(InterruptedException e){
                    e.printStackTrace();
                }
            }).start();
        }
        Thread.sleep(3000);
        System.out.println(atomicInteger);
        Thread.sleep(5000);
    }

    @Test
    public void r3() throws InterruptedException{
        AtomicInteger atomicInteger = new AtomicInteger();
        // 多个线程，随机数
        for(int i = 0; i < 100; i++){
            final int tmp = i;
            final int observed = tmp * tmp - 12 * tmp + 36;//
            new Thread(() -> {
                int value = atomicInteger.updateAndGet(e -> Math.max(e, observed)); // 或者下面这样
                // int value = atomicInteger.accumulateAndGet(observed, Math::max); // 或者上面那样，其实都是r2()的封装
                System.out.println("time-" + tmp + ": " + value);

                try{
                    Thread.sleep(1000);
                } catch(InterruptedException e){
                    e.printStackTrace();
                }
            }).start();
        }
        Thread.sleep(3000);
        System.out.println(atomicInteger);
        Thread.sleep(5000);
    }
}
