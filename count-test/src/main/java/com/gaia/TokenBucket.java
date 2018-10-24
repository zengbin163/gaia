package com.gaia;

import com.google.common.util.concurrent.RateLimiter;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 令牌桶
 * <p>
 * Created by 张少昆 on 2017/9/13.
 */
public class TokenBucket {
    private static final ConcurrentHashMap<String, RateLimiter> LIMITER_CONCURRENT_MAP = new ConcurrentHashMap<>();


    /**
     * 针对不同的资源，初始化创建不同的限制。
     *
     * @param resource 需要限流的资源，如URL
     * @param qps      限流的速率
     */
    public static void createFlowLimitMap(String resource, double qps){
        RateLimiter rateLimiter = LIMITER_CONCURRENT_MAP.get(resource);
        if((rateLimiter == null)){
            rateLimiter = RateLimiter.create(qps);
            LIMITER_CONCURRENT_MAP.putIfAbsent(resource, rateLimiter);
        }
        rateLimiter.setRate(qps);
    }

    /**
     * 判断能否进入（限流）桶中。
     *
     * @param resource
     * @return
     */
    public static boolean enter(String resource){
        RateLimiter rateLimiter = LIMITER_CONCURRENT_MAP.get(resource);
        return rateLimiter.tryAcquire();
    }

    public static void main(String[] args){
        String resource = "/getUser";
        int qps = 10;
        createFlowLimitMap(resource, qps);
        Worker worker = new Worker(resource);

        ExecutorService executorService = Executors.newFixedThreadPool(200);
        try{
            for(int i = 0; i < 20000; i++){
                Random random = new Random();
                Thread.sleep(random.nextInt(100));
                executorService.submit(worker);
            }
        } catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    static class Worker implements Runnable {
        private String resource;

        public Worker(String resource){
            this.resource = resource;
        }

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
