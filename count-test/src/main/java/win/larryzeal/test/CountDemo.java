package win.larryzeal.test;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 自定义简化版限流。
 */
public class CountDemo {
    private static int REQCOUNT = 0;
    private static final int LIMIT = 10;
    private static final int INTERVAL = 1000;
    private static long TIMESTAMP = System.currentTimeMillis();

    /**
     * 简单的计数器方式限流。
     * 问题在于：以一秒限流100次为例，如果前999毫秒进来90次，最后1ms进来100次，那会突破这个限流。
     * 怎么解决？这就用到了滑动窗口协议。
     *
     * @return
     */
    public static boolean canAccess(){
        long now = System.currentTimeMillis();
        if(now < TIMESTAMP + INTERVAL){
            REQCOUNT++;
            return REQCOUNT <= LIMIT;
        }
        TIMESTAMP = now;
        REQCOUNT = 1;
        return true;
    }

    public static void main(String[] args){
        ExecutorService executorService = Executors.newFixedThreadPool(200);
        Worker worker = new Worker();
        for(int i = 0; i < 20000; i++){
            Random random = new Random();
            try{
                Thread.sleep(random.nextInt(10));
            } catch(InterruptedException e){
                e.printStackTrace();
            }
            executorService.submit(worker);
        }
    }


    static class Worker implements Runnable {

        @Override
        public void run(){
            if(canAccess()){
                System.out.println("--------------------allowed to go---------------------");
            } else{
                System.out.println("forbidden to go");
            }
        }
    }

}
