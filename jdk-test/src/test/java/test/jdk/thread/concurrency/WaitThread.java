package test.jdk.thread.concurrency;

/**
 * 目前仅用于CyclicBarrierTest。
 *
 * Created by 张少昆 on 2017/9/6.
 */
public class WaitThread implements Runnable {
    @Override
    public void run(){
        System.out.println("上来就wait");
        try{
            wait();
        } catch(InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("被唤醒了？？？");
    }
}
