package test.jdk.thread.multithread;

import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * TODO 测试 Lock + Condition，定向选中（非唤醒）功能！
 * TODO lock本身是获取锁，而condition#await是释放锁，如果另一个线程的condition#signal，则选中这个释放了锁的线程（不代表就拥有了锁，还是要去acquire）。
 * <p>
 * Created by 张少昆 on 2018/4/17.
 */
public class LockConditionTest {
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();//可以new多个condition，然后呼朋唤友！

    //r1 和 r2 是一个condition
    Runnable r1 = () -> {
        System.out.println(Thread.currentThread().getName() + " 获取锁中，lock @" + LocalDateTime.now());
        lock.lock();
        System.out.println(Thread.currentThread().getName() + " 获取了锁，一秒后进行condition.await（释放锁并在condition中等待）.. @" + LocalDateTime.now());
        try{
            Thread.sleep(1000L);
            condition.await(); //TODO 关键！另一个关键是signal
        } catch(InterruptedException e){
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " 被唤醒（重新获取了锁），一秒后unlock @" + LocalDateTime.now());
        try{
            Thread.sleep(1000L);
        } catch(InterruptedException e){
            e.printStackTrace();
        }
        lock.unlock();//
    };

    Runnable r2 = () -> {
        System.out.println(Thread.currentThread().getName() + " 获取锁中，lock @" + LocalDateTime.now());
        lock.lock();
        System.out.println(Thread.currentThread().getName() + " 获取了锁，一秒后signal（选择condition中的一个等待线程）.. @" + LocalDateTime.now());
        try{
            Thread.sleep(1000L);
        } catch(InterruptedException e){
            e.printStackTrace();
        }
        condition.signal();//TODO 另一个关键！这里选中condition中的线程，但还没释放锁！另外，唤醒不代表就能执行，还需要竞争锁！
        try{
            Thread.sleep(1000);
        } catch(InterruptedException e){
            e.printStackTrace();
        }
        lock.unlock();//再释放
    };

    Runnable r3 = () -> {
        System.out.println(Thread.currentThread().getName() + " 获取锁中，lock @" + LocalDateTime.now());
        lock.lock();
        System.out.println(Thread.currentThread().getName() + " 获取了锁，一秒后unlock（释放锁）.. @" + LocalDateTime.now());
        try{
            Thread.sleep(1000L);
        } catch(InterruptedException e){
            e.printStackTrace();
        }
        lock.unlock();//
    };

    @Test
    public void r(){
        ReentrantLock lock = new ReentrantLock();
        Condition condition1 = lock.newCondition();
        Condition condition2 = lock.newCondition();

        System.out.println(condition1 == condition2);//TODO false  卧槽 好吧
    }

    @Test
    public void r1() throws IOException, InterruptedException{
        new Thread(r1, "r1t1").start();
        Thread.sleep(1000);//TODO 防止r2先signal
        new Thread(r2, "r2t1").start();

        System.in.read();
    }

    @Test
    public void r2() throws IOException, InterruptedException{
        new Thread(r1, "r1t1").start();
        Thread.sleep(1000);//TODO 防止r2先signal
        new Thread(r2, "r2t1").start();
        //TODO 再加个t3，结果可以表明，condition.signal()仅仅是选中condition中的下一个线程，而非唤醒！还是需要acquire锁！
        new Thread(r3, "r3t1").start();

        System.in.read();
    }

    @Test
    public void r3() throws IOException{
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();


        //TODO 验证：一旦r1获得了锁，那所有r1的线程都要先执行完才轮得到r2。
        new Thread(r1, "r1t1").start();
        new Thread(r1, "r1t2").start();
//        new Thread(r1, "r1t3").start();
//        new Thread(r1, "r1t4").start();
        new Thread(r2, "r2t1").start();
        new Thread(r2, "r2t2").start();
//        new Thread(r2, "r2t3").start();
//        new Thread(r2, "r2t4").start();

        System.in.read();
    }
}
