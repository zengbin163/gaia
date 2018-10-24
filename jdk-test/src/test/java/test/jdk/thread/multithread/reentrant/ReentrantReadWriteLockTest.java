package test.jdk.thread.multithread.reentrant;

import org.junit.Test;

import java.time.LocalTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * TODO 说明，Lock和ReadWriteLock是两个完全无关的接口，除了在同一个package下。
 * TODO 但是，二者的实现是有关系的！
 * <p>
 * TODO ReentrantLock是完全互斥排他的，这样其实效率是不高的。
 * TODO 还有一个ReentrantReadWriteLock，读的时候可以多个线程获取到锁，写的时候只有一个线程获取到锁。
 * TODO 读锁、写锁，都是实现了Lock！但是读锁不支持Condition！
 * TODO 读共享，写唯一，读写还互斥
 * <p>
 * Created by zengbin on 2018/4/17.
 */
public class ReentrantReadWriteLockTest {
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    @Test
    public void r1(){ //all false
        System.out.println(lock.isFair());
        System.out.println(lock.isWriteLocked());
        System.out.println(lock.isWriteLockedByCurrentThread());
    }

    @Test
    public void r2(){
        ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
        ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
    }

    //TODO 结论：读共享，写唯一，读写还互斥。
    @Test
    public void r3(){
        CountDownLatch countDownLatch = new CountDownLatch(6);

        Runnable read = () -> {
            lock.readLock().lock();
            System.out.println(LocalTime.now() + "@ " + Thread.currentThread().getName() + " 获取了读锁，那么，读取中。。");

            try{
                Thread.sleep(3000L);
            } catch(InterruptedException e){
                e.printStackTrace();
            } finally{
                System.out.println(LocalTime.now() + "@ " + Thread.currentThread().getName() + " 释放了读锁");
                lock.readLock().unlock();
            }
            countDownLatch.countDown();
        };
        Runnable write = () -> {
            lock.writeLock().lock();
            System.out.println(LocalTime.now() + "@ " + Thread.currentThread().getName() + " 获取了写锁，那么，写入中。。");

            try{
                Thread.sleep(3000L);
            } catch(InterruptedException e){
                e.printStackTrace();
            } finally{
                System.out.println(LocalTime.now() + "@ " + Thread.currentThread().getName() + " 释放了写锁");
                lock.writeLock().unlock();
            }
            countDownLatch.countDown();
        };

        new Thread(read, "r1").start();
        new Thread(read, "r2").start();
        new Thread(read, "r3").start();
        new Thread(write, "w1").start();
        new Thread(write, "w2").start();
        new Thread(write, "w3").start();

        try{
            countDownLatch.await();
        } catch(InterruptedException e){
            e.printStackTrace();
        }
    }


}
