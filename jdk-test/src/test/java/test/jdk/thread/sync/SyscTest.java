package test.jdk.thread.sync;

import org.junit.Test;

import java.io.IOException;

/**
 * 测试变量作为Lock是否可靠。
 * <p>
 * Created by zengbin on 2018/4/23.
 */
public class SyscTest {
    String lock = "123";
    int count = 0;

    @Test
    public void r1() throws InterruptedException, IOException{
        Runnable producer = () -> {
            while(true){
                synchronized(lock){
                    if(count == 20){
                        try{
                            lock.wait();
                        } catch(InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                    {
                        count++;
                        System.out.println("+++1，现在的数量是：" + count);
                        lock.notify();
                    }
                }
            }
        };
        Runnable consumer = () -> {
            while(true){
                synchronized(lock){ //现场是在循环抢夺锁！
                    // lock = "456"; //FIXME 改变了就不行了！
                    if(count == 0){
                        try{
                            lock.wait();
                        } catch(InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                    {
                        count--;
                        System.out.println("---1，现在的数量是：" + count);
                        lock.notify();
                    }
                    // lock = "456"; //FIXME 改变了就不行了！

                }
            }
        };


        // for(int i = 0; i < 100; i++){
        new Thread(producer).start();
        new Thread(consumer).start();
        // new Thread(r2).start();
        // }
        // new Thread(r1).start();

        System.in.read();
    }


    @Test
    public void r2() throws IOException{
        Runnable r1 = () -> {
            while(true){
                synchronized(lock){
                    System.out.println(Thread.currentThread().getName() + " count:" + (++count));
                    // lock = System.currentTimeMillis() + "";//FIXME 不能改变，否则就是没有关系的竞争了
                    try{
                        Thread.sleep(10);
                    } catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        };
        new Thread(r1, "t1").start();
        new Thread(r1, "t2").start();
        new Thread(r1, "t3").start();
        new Thread(r1, "t4").start();

        System.in.read();
    }
}
