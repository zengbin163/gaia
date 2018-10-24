package test.jdk.async;

import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Supplier;

/**
 * ref: http://blog.csdn.net/tangyongzhe/article/details/49851769
 * <p>
 * Created by zengbin on 2017/10/7.
 */
public class CompletableFutureTest {

    // jdk8之前的异步
    @Test
    public void jdk5() throws ExecutionException, InterruptedException{
        ExecutorService executor = Executors.newFixedThreadPool(1);
        // Future代表了线程执行完以后的结果，可以通过future获得执行的结果。
        // 但是jdk1.8之前的Future有点鸡肋，并不能实现真正的异步，需要阻塞的获取结果，或者不断的轮询。
        // 通常我们希望当线程执行完一些耗时的任务后，能够自动的通知我们结果，很遗憾这在原生jdk1.8之前
        // 是不支持的，但是我们可以通过第三方的库实现真正的异步回调。
        Future<String> f = executor.submit(new Callable<String>() {

            @Override
            public String call() throws Exception{
                System.out.println("task started!");
                Thread.sleep(3000);
                System.out.println("task finished!");
                return "hello";
            }
        });

        //此处阻塞main线程
        System.out.println(f.get());
        System.out.println("main thread is blocked");
    }

    // jdk8的异步 - CompletableFuture
    // 所有的方法都有两种形式：要么提供一个threadpool，要么使用默认的fork/join框架
    @Test
    public void jdk8() throws ExecutionException, InterruptedException{
        // 两个线程的线程池
        ExecutorService executor = Executors.newFixedThreadPool(2);
        //jdk1.8之前的实现方式
        CompletableFuture<String> future = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get(){
                System.out.println("task started!");
                try{
                    //模拟耗时操作
                    Thread.sleep(2000);
                } catch(InterruptedException e){
                    e.printStackTrace();
                }
                return "task finished!";
            }
        }, executor);// 所有的方法都有两种形式：要么提供一个threadpool，要么使用默认的fork/join框架

        //采用lambada的实现方式 - 这是用的fork/join吗？感觉不像！应该只有async才使用fork/join或者threadpool。
        future.thenAccept(e -> System.out.println(e + " ok"));

        System.out.println("main thread is running");
        // while(true){}
        System.out.println(future.get());
    }

    // 奇怪，都是then，那这里的thenRun和thenRunAsync有区别吗？？？没发现啊！！
    @Test
    public void r1() throws InterruptedException{
        CompletableFuture.runAsync(() -> { // 异步
            System.out.println("1st step start.." + Thread.currentThread().getName());
            try{
                Thread.sleep(2000);
            } catch(InterruptedException e){
                e.printStackTrace();
            }
            System.out.println("1st step end..");
        })// CompletableFuture<Void>
                .thenRun(() -> { // 同步
                    System.out.println("2nd step start.." + Thread.currentThread().getName());
                    try{
                        Thread.sleep(2000);
                    } catch(InterruptedException e){
                        e.printStackTrace();
                    }
                    System.out.println("2nd step end..");
                })// CompletableFuture<Void>
                .thenRunAsync(() -> { // 异步
                    System.out.println("3rd step start.." + Thread.currentThread().getName());
                    try{
                        Thread.sleep(3000);
                    } catch(InterruptedException e){
                        e.printStackTrace();
                    }
                    System.out.println("3rd step end..");
                })// CompletableFuture<Void>
                .thenRunAsync(() -> { // 异步
                    System.out.println("4th step start.." + Thread.currentThread().getName());
                    try{
                        Thread.sleep(3000);
                    } catch(InterruptedException e){
                        e.printStackTrace();
                    }
                    System.out.println("4th step end..");
                });// CompletableFuture<Void>

        System.out.println("hehe.." + Thread.currentThread().getName());
        while(true){
        }
    }
}
