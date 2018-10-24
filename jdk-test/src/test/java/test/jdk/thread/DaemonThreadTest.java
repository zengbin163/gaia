package test.jdk.thread;

import org.junit.Test;

import java.time.LocalDateTime;

/**
 * 测试下 守护线程。FIXME 必须在main函数中开启。
 * <p>
 * 守护线程，就是不会阻止JVM退出的线程。
 * <p>
 * 必须在start之前开启。
 * <p>
 * Created by zengbin on 2018/5/28.
 */
public class DaemonThreadTest {

    Runnable runnable = () -> {
        while(true){
            try{
                Thread.sleep(1000L);
                System.out.println("-------@" + LocalDateTime.now());
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    };
    Thread thread = new Thread(runnable);

    @Test
    public void r0(){
        thread.setDaemon(false);
        thread.start();
    }

    @Test
    public void r1(){
        thread.setDaemon(true);
        thread.start();
    }
}
