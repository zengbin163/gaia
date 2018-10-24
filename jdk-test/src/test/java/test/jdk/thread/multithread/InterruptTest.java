package test.jdk.thread.multithread;

import org.junit.Test;

/**
 * Created by zengbin on 2018/4/16.
 */
public class InterruptTest {

    @Test
    public void r1() throws InterruptedException{
        //两个线程，通过一个去调用另一个的interrupt

        Thread t1 = new Thread(() -> {
            while(true){
                try{
                    Thread.sleep(1000);//sleep会被interrupt打断！
                } catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        System.out.println("t1.isInterrupted(): " + t1.isInterrupted());

        Thread.sleep(3000L);
        t1.interrupt();

        System.out.println("t1.isInterrupted(): " + t1.isInterrupted());
    }

    @Test
    public void r2() throws InterruptedException{
        //两个线程，通过一个去调用另一个的interrupt

        Thread t1 = new Thread(() -> {
            while(true){

                try{
                    this.wait();//FIXME 不含锁，IllegalMonitorStateException
                } catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        System.out.println("t1.isInterrupted(): " + t1.isInterrupted());

        Thread.sleep(3000L);
        t1.interrupt();

        System.out.println("t1.isInterrupted(): " + t1.isInterrupted());
    }

    @Test
    public void r3() throws InterruptedException{
        //两个线程，通过一个去调用另一个的interrupt

        Thread t1 = new Thread(() -> {
            while(true){
                //正在运行的线程虽然不会被打断，但是其isInterrupted状态变为true！
            }
        });

        t1.start();
        System.out.println("t1.isInterrupted(): " + t1.isInterrupted());

        Thread.sleep(3000L);
        t1.interrupt();

        System.out.println("t1.isInterrupted(): " + t1.isInterrupted());
    }
}
