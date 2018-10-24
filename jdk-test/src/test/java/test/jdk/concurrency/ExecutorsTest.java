package test.jdk.concurrency;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

/**
 * 看看这些参数都是干嘛的.
 * 
 * 
 * 
 * 
 * @author Administrator
 *
 */
public class ExecutorsTest {

    @Test
    public void r1() {
        // 新方式
        ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        // 原始方式
        int corePoolSize = Runtime.getRuntime().availableProcessors();// 这个返回值通常有问题
        int maxPoolSize = corePoolSize * 2;
        long keppAliveTime = 120L;
        TimeUnit unit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(maxPoolSize);// 数组阻塞式队列
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keppAliveTime, unit, workQueue);

    }

    @Test
    public void r2() {
        int corePoolSize = Runtime.getRuntime().availableProcessors();
        System.out.println("core size is " + corePoolSize);
    }
}
