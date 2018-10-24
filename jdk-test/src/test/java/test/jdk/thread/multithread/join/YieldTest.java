package test.jdk.thread.multithread.join;

import org.junit.Test;

import java.time.LocalDateTime;

/**
 * TODO thread.yield()，让出！就是让正在执行的线程让出CPU！但仅仅是个暗示，可以被忽略！
 * <p>
 * Created by 张少昆 on 2018/3/17.
 */
public class YieldTest {
    @Test
    public void yield() throws InterruptedException{
        Thread t1 = new Thread(() -> {
            int i = 0;
            while(true){
                System.out.println("t1 @ " + LocalDateTime.now());
                i++;
                if(i % 10 == 0){
                    Thread.yield();//呵呵 让出CPU，但会等待下次执行！
                }
                try{
                    Thread.sleep(1000); //TODO 不能sleep吧，sleep就成阻塞状态了
                } catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        });
        Thread t2 = new Thread(() -> {
            while(true){
                System.out.println("t2 @ " + LocalDateTime.now());
                try{
                    Thread.sleep(1000); //TODO 不能sleep吧，sleep就成阻塞状态了
                } catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();
        // t1.yield(); //静态方法！

        while(true){
            System.out.println("main..");
            Thread.sleep(1000L);
        }
    }
}
