package test.reactor.c01;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * TODO 先看C01_basic，再看这个，最后看C01_flatmap。
 * <p>
 * Reactor对于线程的使用是非常节俭的，因为这会给你最佳的性能体验！
 * 如果你一直在使用线程、线程池、异步执行 等方式来榨干服务的每一份性能，也许你会觉得奇怪。
 * 但这是真的：如果没有必要的线程切换，即便JVM高度优化了线程切换，其速度也不如单线程计算快！
 * Reactor掌握了这些关键点，并用来控制异步处理，并假定你知道你在做什么！
 * <p>
 * Flux，提供了少数配置方法，用于控制线程边界。例如，你可以配置使用一个背景线程：Flux.subscribeOn()
 * <p>
 * Created by 张少昆 on 2018/3/5.
 */
public class C01_thread {
    //使用单线程（非主线程）完成任务
    @Test
    public void r01() throws InterruptedException{
        Flux<String> flux = Flux.just("hello", "hi", "hey", "hue", "hoho", "haha");

        flux
                .log()
                .map(String::toUpperCase)
                .subscribeOn(Schedulers.single())
                .subscribe(e -> {
                    System.out.println("-------->> " + e);
                });

        Thread.sleep(1000L * 10);
    }

    //使用单线程（非主线程）完成任务
    @Test
    public void r02() throws InterruptedException{
        Flux<String> flux = Flux.just("hello", "hi", "hey", "hue", "hoho", "haha");

        flux
                .log()
                .map(String::toUpperCase)
                .subscribeOn(Schedulers.parallel())
                .subscribe(e -> {
                    System.out.println("-------->> " + e);
                });

        Thread.sleep(1000L * 10);
    }

    @Test
    public void r03() throws InterruptedException{
        Flux.just("red", "white", "blue")
                .log()
                .map(String::toUpperCase)
                .subscribeOn(Schedulers.newParallel("sub"))
                .publishOn(Schedulers.newParallel("pub"), 2)
                .subscribe(value -> {
                    System.out.println("-------->> " + value);
                });
        Thread.sleep(1000L * 10);
    }
}
