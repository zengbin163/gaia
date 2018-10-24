package test.jdk.executor;

import java.util.concurrent.Executor;

import org.junit.Test;

import test.jdk.executor.impl.NewThreadExecutor;
import test.jdk.executor.impl.SimpleExecutor;

/**
 * Executor 是一个将任务与执行解耦和的对象。
 * java.util.concurrent.Executor 只有一个方法，就是execute(Runnable)。
 * 
 * @author Administrator
 *
 */
public class ExecutorTest {

    @Test
    public void simple() {
        System.out.println("main thread: " + Thread.currentThread().getId());

        Executor executor = new SimpleExecutor();
        executor.execute(() -> {
            System.out.println("executor thread: " + Thread.currentThread().getId());
        });
    }
    
    @Test
    public void newThread() {
        System.out.println("main thread: " + Thread.currentThread().getId());

        Executor executor = new NewThreadExecutor();
        executor.execute(() -> {
            System.out.println("executor thread: " + Thread.currentThread().getId());
        });
    }

}