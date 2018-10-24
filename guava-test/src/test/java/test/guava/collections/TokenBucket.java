package test.guava.collections;

import com.google.common.util.concurrent.RateLimiter;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * TODO 奇怪，有问题，不能启动，不应该放在 src/test下面。
 * TODO 已移动至 count-test模块中。
 * Created by zengbin on 2017/9/13.
 */
public class TokenBucket {
    private static final ConcurrentHashMap<String, RateLimiter> LIMITER_CONCURRENT_MAP = new ConcurrentHashMap<String, RateLimiter>();

    public static void createFlowLimitMap(String resource, double qps){
        RateLimiter rateLimiter = LIMITER_CONCURRENT_MAP.get(resource);
        if((rateLimiter == null)){
            rateLimiter = RateLimiter.create(qps);
            LIMITER_CONCURRENT_MAP.putIfAbsent(resource, rateLimiter);
        }
        rateLimiter.setRate(qps);
    }

    public static boolean enter(String resource){
        RateLimiter rateLimiter = LIMITER_CONCURRENT_MAP.get(resource);
        return rateLimiter.tryAcquire();
    }

    @Test
    public void test(){
        createFlowLimitMap("/getUser", 10);
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
            if(enter("/getUser")){
                System.out.println("--------------------allowed to go---------------------");
            } else{
                System.out.println("forbidden to go");
            }
        }
    }
}
