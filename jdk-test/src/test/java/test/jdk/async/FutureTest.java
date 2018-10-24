package test.jdk.async;

import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * 最原始的异步方式！
 * guava/netty 等 都提供了自己的异步框架！
 * jdk8的CompletableFuture也比较好！
 *
 * Created by 张少昆 on 2017/10/7.
 */
public class FutureTest {
    @Test
    public void r1(){
        String re = getDataSynchronously();
        System.out.println(re);

        while(true){
        }
    }

    /**
     * 最原始的异步方式！
     *
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @Test
    public void r2() throws InterruptedException, ExecutionException{
        FutureTask<String> futureTask = getDataAsynchronously();

        ExecutorService pool = Executors.newFixedThreadPool(3);
        pool.execute(futureTask);// NOTE HERE, 不能使用Future, 必须 FutureTask !!! 因为实现了Runnable
        // futureTask = pool.submit(callable); // 作用同上面 - 两种执行FutureTask的方式

        System.out.println("the task is executed async.. you can do whatever you want now");
        System.out.println("...");
        System.out.println("ok, now you want to do sth with the result of task, here it is..");

        String str = futureTask.get();
        System.out.println("wow, look, we got this: " + str);
    }

    /**
     * 同步执行的方法。
     *
     * @return
     */
    private String getDataSynchronously(){
        return "getDataSynchronously";
    }

    /**
     * 异步执行的方法。-- 确切的说是要执行的FutureTask。
     *
     * @return
     * @throws InterruptedException
     */
    private FutureTask<String> getDataAsynchronously() throws InterruptedException{
        Callable callable = () -> {
            for(int i = 0; i < 3; i++){
                System.out.println("getDataAsynchronously is sleeping...");
                Thread.sleep(1000L);
            }
            return "getDataAsynchronously";
        };
        FutureTask futureTask = new FutureTask(callable);
        return futureTask;
    }
}
