package jdk.thread;

import java.time.LocalDateTime;

/**
 * 测试下 守护线程。
 * <p>
 * 守护线程，就是不会阻止JVM退出的线程。退出时，守护线程中的finally不会执行！！！
 * <p>
 * 必须在start之前开启。
 * <p>
 * 最佳例子：聊天的客户端，需要后台查询，但是，退出聊天的时候，不需要去管后台，直接关闭即可！- 当然也不太合理，不过方便理解就是。
 * <p>
 * Created by zengbin on 2018/5/28.
 */
public class DaemonThread {
    public static void main(String[] args){
        thread.setDaemon(true); // comment or uncomment this line
        thread.start();

        try{
            Thread.sleep(1000L * 5);
        } catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    //TODO 注意，线程内部创建的新的线程对象，其daemon属性与父线程一致
    static Runnable runnable = () -> {
        while(true){
            try{
                Thread.sleep(1000L);
                System.out.println("-------@" + LocalDateTime.now());
                Thread inherited = new Thread();
                System.out.println("inherited.isDaemon(): " + inherited.isDaemon());
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    };
    static Thread thread = new Thread(runnable);
}
