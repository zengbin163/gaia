package test.jdk.executor;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

/**
 * 测试下Executors创建的线程池，据说有一个巨大的bug，就是会吞掉异常。 下面的例子，如果不调用future.get()，就不会抛出异常，为什么？
 * 因为FutureTask的run()会捕获并设置异常！
 * 
 * 参考：http://blog.csdn.net/jtaizlx0102/article/details/51127355
 * 
 * @author Administrator
 *
 */
public class ExecutorsTest {

    // 线程都执行了，但没有任何异常 -- 已经强行抛出了异常
    @Test
    public void r1() {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        final AtomicInteger i = new AtomicInteger(0);
        Runnable r = () -> {
            try {
                System.out.println("i=" + i.get());
                Thread.sleep(1000L * i.incrementAndGet()); // 为什么这里的i不是想要的值？
                // Thread.sleep(1000L);
                System.out.println("我是线程：" + Thread.currentThread().getId());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            throw new RuntimeException(Thread.currentThread().getId() + "");
        };
        Future<?> future1 = threadPool.submit(r, 1);
        Future<?> future2 = threadPool.submit(r, 2);
        Future<?> future3 = threadPool.submit(r, 3);

        try {
            Thread.sleep(1000L * 10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 只有调用future.get()，才会抛出异常。
    @Test
    public void r2() {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        final AtomicInteger i = new AtomicInteger(0);

        Runnable r = () -> {
            try {
                i.incrementAndGet();
                System.out.println("i=" + i.get());
                Thread.sleep(2000L * i.get()); // 为什么这里的i不是想要的值？
                // Thread.sleep(1000L);
                System.out.println("我是线程：" + Thread.currentThread().getId());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            throw new RuntimeException(Thread.currentThread().getId() + "");
        };

        Future<?> future1 = threadPool.submit(r, 1);
        Future<?> future2 = threadPool.submit(r, 2);
        Future<?> future3 = threadPool.submit(r, 3);

        try {
            System.out.println(future1.get());
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        } catch (ExecutionException e1) {
            e1.printStackTrace();
        }
        try {
            System.out.println(future2.get());
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        } catch (ExecutionException e1) {
            e1.printStackTrace();
        }
        try {
            System.out.println(future3.get());
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        } catch (ExecutionException e1) {
            e1.printStackTrace();
        }

    }
}
