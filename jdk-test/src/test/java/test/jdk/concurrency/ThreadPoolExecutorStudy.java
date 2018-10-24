package test.jdk.concurrency;

import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 下面这些静态变量、常量、方法，都是来自ThreadPoolExecutor。
 * 这里是为了研究下它们的值以及作用。
 * <p>
 * https://www.jianshu.com/p/a5a21d48678a
 * <p>
 * Created by zengbin on 2018/5/19.
 */
public class ThreadPoolExecutorStudy {
    private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));
    //TODO 这里是因为3bit需要用来表示5个状态，所以用剩余的29位去作为count位。
    private static final int COUNT_BITS = Integer.SIZE - 3; //TODO 是size（32 bits），不是max_value
    private static final int CAPACITY = (1 << COUNT_BITS) - 1;

    // runState is stored in the high-order bits
    private static final int RUNNING = -1 << COUNT_BITS;
    private static final int SHUTDOWN = 0 << COUNT_BITS;
    private static final int STOP = 1 << COUNT_BITS;
    private static final int TIDYING = 2 << COUNT_BITS;
    private static final int TERMINATED = 3 << COUNT_BITS;

    // Packing and unpacking ctl TODO 注意，~是取反！
    private static int runStateOf(int c){
        return c & ~CAPACITY;
    }

    private static int workerCountOf(int c){
        return c & CAPACITY;
    }

    private static int ctlOf(int rs, int wc){
        return rs | wc;
    }

    //
    @Test
    public void r1(){
        System.out.println("COUNT_BITS: " + COUNT_BITS);
        System.out.println("CAPACITY: " + CAPACITY);
        System.out.println("RUNNING: " + RUNNING);
        System.out.println("SHUTDOWN: " + SHUTDOWN);
        System.out.println("STOP: " + STOP);
        System.out.println("TIDYING: " + TIDYING);
        System.out.println("TERMINATED: " + TERMINATED);
    }

    //测试下default rejectionHandler (abortPolicy)
    //当Runnable无法被execute时出发，前提条件：1.非running状态；或2.队列添加(offer)失败。还有其他吗？
    @Test
    public void testRejectionHandler(){
        int corePoolSize = 2; //提交任务时，如果线程数低于2，则创建新线程。完成后则会始终维持最少2个线程。
        int maximumPoolSize = 5; //线程池最多允许5个线程存在。-和workQueue什么关系呢？
        long keepAliveTime = 5; // 线程数量超过corePoolSize时，如果空闲了，那会空闲多久。
        TimeUnit timeUnit = TimeUnit.SECONDS;
        // TODO 注意，ArrayBlockingQueue是有界队列，还可以用LinkedBlockingQueue 无界队列 - 就是可以submit无限任务！
        ArrayBlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(2);//TODO 该队列仅hold由execute方法提交的Runnable tasks。submit会调用execute！
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, timeUnit, workQueue);
        Runnable runnable = () -> {
            System.out.println(Thread.currentThread().getName());
            try{
                Thread.sleep(1000L * 1000);
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        };

        try{
            for(int i = 0; i < 10; i++){
                System.out.println(i);
                Future<?> submit = threadPoolExecutor.submit(runnable);
                //submit后，Queue里可能有内容，也可能没有 - 可能已经移交给worker线程了！
                //FIXME 下面队列用法不对。因为 只有在线程数少于corePoolSize时，才不走队列。
                BlockingQueue<Runnable> queue = threadPoolExecutor.getQueue();
                System.out.println("queue size: " + queue.size());
                System.out.println("queue.remainingCapacity: " + queue.remainingCapacity()); // 这个可能有用
                // queue.take();//wait until
                System.out.println("queue.peek: " + queue.peek());//奇怪，总是同一个，难道需要同步？
                // queue.poll();//retrive and remove head
            }
            System.out.println("----a");
            threadPoolExecutor.execute(runnable); //嗯？什么时候用这个比较好？
            System.out.println("----b");

        } catch(Exception e){ //FIXME 应该把try-catch放到循环内部，否则会中断循环
            System.out.println(threadPoolExecutor.isShutdown());
            System.out.println(threadPoolExecutor.isTerminated());
            System.out.println(threadPoolExecutor.isTerminating());

            while(true){
                try{
                    System.out.println(threadPoolExecutor.getQueue().take());
                } catch(InterruptedException e1){
                    e1.printStackTrace();
                }
            }
        }
        try{
            threadPoolExecutor.awaitTermination(1L, TimeUnit.HOURS);
        } catch(InterruptedException e){
            e.printStackTrace();
        }
    }

}
