package test.jdk.thread.multithread.join;

import org.junit.Test;

/**
 * TODO thread.join() 可保证顺序执行。
 * TODO 其原理是：join会拿到锁，然后wait！Waits for this thread to die.
 * <p>
 * Created by 张少昆 on 2018/3/17.
 */
public class JoinTest {
    @Test
    public void join() throws InterruptedException{
        Thread t1 = new Thread(() -> {
            System.out.println("t1");
        });
        Thread t2 = new Thread(() -> {
            System.out.println("t2");
        });
        Thread t3 = new Thread(() -> {
            System.out.println("t3");
        });

        // 这样，是乱序的
        // t1.start();
        // t2.start();
        // t3.start();

        // 这样，才能保证有序
        t1.start();
        t1.join();

        t2.start();
        t2.join();
        t3.start();
        t3.join();
    }
}
