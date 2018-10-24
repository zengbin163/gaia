package test.spring.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * `@EnableScheduling`
 * By default, will be searching for an associated scheduler definition:
 * either a unique org.springframework.scheduling.TaskScheduler bean in the context,
 * or a TaskScheduler bean named "taskScheduler" otherwise;
 * the same lookup will also be performed for a java.util.concurrent.ScheduledExecutorService bean.
 * If neither of the two is resolvable, a local single-threaded default scheduler will be created and used within the registrar.
 * 一句话，需要设置taskScheduler和ScheduledExecutorService，否则使用单线程执行调度。
 * <p>
 * Created by 张少昆 on 2018/1/31.
 */
@Configuration
@EnableAsync
@EnableScheduling
@ComponentScan(basePackages = "test.spring")
public class MyTask {

    //    @Async
//    @Scheduled( cron = "0/5 * *  * * ? " )
    @Scheduled( fixedRate = 3 )
    public void doTask1() throws InterruptedException{
//        while(true){
        System.out.println("task1 @@ " + Thread.currentThread().getId() + " is running..");
        Thread.sleep(5000);
//        }
    }


}