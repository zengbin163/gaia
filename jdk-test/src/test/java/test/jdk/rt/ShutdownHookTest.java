package test.jdk.rt;

import org.junit.Test;

/**
 * 当JVM开始shutdown sequence时，会无序的调用所有已注册的钩子。
 * <p>
 * Created by 张少昆 on 2018/5/7.
 */
public class ShutdownHookTest {

    @Test
    public void r1(){
        Runtime rt = Runtime.getRuntime();
        System.out.println("freeMemory: " + rt.freeMemory());
        System.out.println("maxMemory: " + rt.maxMemory());
        System.out.println("totalMemory: " + rt.totalMemory());
        System.out.println("availableProcessors: " + rt.availableProcessors());
        rt.runFinalization();
    }

    @Test
    public void r2(){
        Runtime runtime = Runtime.getRuntime();
        runtime.addShutdownHook(new Thread(() -> {
            System.out.println("钩子函数，关闭时启用本线程1");
        }));
        runtime.addShutdownHook(new Thread(() -> {
            System.out.println("钩子函数，关闭时启用本线程2");
        }));
        runtime.addShutdownHook(new Thread(() -> {
            System.out.println("钩子函数，关闭时启用本线程3");
        }));
    }
}
