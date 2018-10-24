package test.guava;

import com.google.common.util.concurrent.RateLimiter;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zengbin on 2017/9/5.
 */
public class GuavaRateLimiterTest {

    @Test
    public void r1() throws InterruptedException{
        RateLimiter rateLimiter = RateLimiter.create(2.0);

        for(int i = 0; i != 10; ++i){
//			System.out.println("---第 " + i + " 次执行，时间是：" + Instant.now().getEpochSecond());
            System.out.println("---第 " + i + " 次执行，时间是：" + LocalDateTime.now().getMinute() + ":" + LocalDateTime.now().getSecond());
            rateLimiter.acquire();
//			if(rateLimiter.tryAcquire(5)){ //为什么这个不行？
//				System.out.println("每秒执行次数已触及限制上限，强制休息200ms");
//				Thread.sleep(200);
//			}
        }
    }

    @Test
    public void r2(){
        Map map = new HashMap(1);
        map.put("name", "Larry");

        Map<String, String> eMap = Collections.emptyMap();//不可修改的空map！！！
//		eMap.put("name", "Larry");

        Map<String, String> uMap = Collections.unmodifiableMap(map);//不可修改
        uMap.values().forEach(System.out::println);
//		uMap.put("age", "18");
//		System.out.println(uMap.replace("name", "Lala"));
    }

    @Test
    public void r3(){
        double count = 10000;
        RateLimiter rateLimiter = RateLimiter.create(count);

        double acquire = rateLimiter.acquire();
        boolean b = rateLimiter.tryAcquire(10);
        boolean b1 = rateLimiter.tryAcquire();
    }


    @Test
    public void r4(){

    }

    /**
     * @param rate n条每秒
     */
    public void msg(int rate){
        if(rate>360000){
            throw new RuntimeException("超过上限（360000）了！");
        }

//        rate/1000


        RateLimiter rateLimiter = RateLimiter.create(3.0);
    }
}
