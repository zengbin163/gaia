package test.jdk.thread.multithread.reentrant;

import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁？？？
 * lock，tryLock，lockInterruptibly的区别
 * 参考：https://www.zhihu.com/question/36771163
 * <p>
 * 1）lock(), 拿不到lock就不罢休，不然线程就一直block。 比较无赖的做法。
 * 2）tryLock()，马上返回，拿到lock就返回true，不然返回false。 比较潇洒的做法。
 * 带时间限制的tryLock()，拿不到lock，就等一段时间，超时返回false。比较聪明的做法。
 * 3）lockInterruptibly()就稍微难理解一些。
 * <p>
 * 作者：郭无心
 * 链接：https://www.zhihu.com/question/36771163/answer/68974735
 * 来源：知乎
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 *
 * 可重入锁，重入就体现在可以多次lock() - 考虑下遇到递归的情况，可重入就非常有必要了！
 * 或者更常用的场景，一个类中多个代码块用了同一个Lock，调完这个方法调下一个。

 */
public class ReentrantLockTest {

    @Test
    public void r1(){
        ReentrantLock lock = new ReentrantLock();

        System.out.println("isFair: " + lock.isFair());//false
        System.out.println("isLocked: " + lock.isLocked());
        System.out.println("isHeldByCurrentThread: " + lock.isHeldByCurrentThread());

        System.out.println(lock.getHoldCount());
        System.out.println(lock.getQueueLength());
    }

    @Test
    public void r2(){
        ReentrantLock lock = new ReentrantLock();

        boolean b = lock.tryLock();
        System.out.println(b);
    }

    @Test
    public void r3(){
        ReentrantLock lock = new ReentrantLock();

        boolean b = false;
        try{
            b = lock.tryLock(5, TimeUnit.SECONDS);
        } catch(InterruptedException e){
            e.printStackTrace();
        }
        System.out.println(b);
    }

    @Test
    public void r4() throws IOException, InterruptedException{
        ReentrantLock lock = new ReentrantLock();

        boolean b = lock.tryLock();//非阻塞
        System.out.println(b);
        b = lock.tryLock();
        System.out.println(b);
        b = lock.tryLock();
        System.out.println(b);

        Runnable r1 = () -> {
            for(int i = 0; i < 10; i++){
                System.out.println(lock.tryLock());
                System.out.println("还继续不？");//卧槽，false也继续，非阻塞！！
                try{
                    Thread.sleep(1000);
                } catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        new Thread(r1).start();
        Thread.sleep(3050L);
        lock.unlock();//TODO 获取几次，就要释放几次！
        Thread.sleep(1000L);
        lock.unlock();//TODO 获取几次，就要释放几次！
        Thread.sleep(1000L);
        lock.unlock();//TODO 获取几次，就要释放几次！
        System.in.read();
    }

    @Test
    public void r44() throws IOException, InterruptedException{
        ReentrantLock lock = new ReentrantLock();

        lock.lock();
        lock.lock();
        lock.lock();

        Runnable r1 = () -> {
            for(int i = 0; i < 10; i++){
                lock.lock();//阻塞
                System.out.println("还继续不？");//lock.lock() 阻塞，就不继续了！
                try{
                    Thread.sleep(1000);
                } catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        new Thread(r1).start();
        Thread.sleep(3050L);
        lock.unlock();//TODO 无论是lock()还是tryLock()，获取几次，就要释放几次！
        Thread.sleep(1000L);
        lock.unlock();//TODO 无论是lock()还是tryLock()，获取几次，就要释放几次！
        Thread.sleep(1000L);
        lock.unlock();//TODO 无论是lock()还是tryLock()，获取几次，就要释放几次！

        System.in.read();
    }

    @Test
    public void r5(){
        final ExecutorService exec = Executors.newFixedThreadPool(4);
        final ReentrantLock lock = new ReentrantLock();
        final Condition condition = lock.newCondition();
        final int time = 5;
        final Runnable add = new Runnable() {
            public void run(){
                System.out.println("Pre " + lock);
                lock.lock();
                try{
                    // condition.await(time, TimeUnit.SECONDS); //await和sleep是两种效果。
                    Thread.sleep(5000);
                } catch(InterruptedException e){
                    e.printStackTrace();
                } finally{
                    System.out.println("Post " + lock.toString());
                    lock.unlock();
                }
            }
        };
        for(int index = 0; index < 4; index++){
            exec.submit(add);
        }
        exec.shutdown();


        try{
            System.in.read();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    @Test
    public void lockInterrupt() throws Exception{
        final Lock lock = new ReentrantLock();
        lock.lock(); //阻塞
        System.out.println("主线程已获取锁");

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run(){
                System.out.println(Thread.currentThread().getName() + " entered. wait for the lock");
                // lock.lock();//TODO 阻塞。完全无视其他线程对本线程的interrupt
                try{
                    lock.lockInterruptibly();//TODO 阻塞。但是，这个lock被其他线程interrupt啦
                    System.out.println("-------------------");
                } catch(InterruptedException e){
                    System.out.println("t1线程被打扰了？");
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " interrupted.");
            }
        }, "child thread -1");

        t1.start();
        Thread.sleep(1000);//sleep还持有锁

        System.out.println("主线程要interrupt t1线程！"); //TODO 但是，t1线程的lock.lock() 完全不买账
        t1.interrupt();

        Thread.sleep(1000000);
    }
}
