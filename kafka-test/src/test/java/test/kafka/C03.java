package test.kafka;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * @link test.jdk.nanana.LimitTest
 *
 * Created by 张少昆 on 2018/4/26.
 */
@Deprecated
public class C03 {
    //固定时间发送固定数量的记录
    @Test
    public void r1(){

        int count = 10000;
        while(true){
            CountDownLatch countDownLatch = new CountDownLatch(count);

            new Thread(() -> {
                while(true){
                    //producer.send(record);
                    countDownLatch.countDown(); //fori 就行啊
                }
            }).start();

            try{
                countDownLatch.await();
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }

    }

    @Test
    public void r2() throws InterruptedException{
        int count = 10000;

        while(true){
            long start = System.currentTimeMillis();

            for(int i = 0; i < count; i++){
                //producer.send(record);
            }

            if(System.currentTimeMillis() - start >= 1000){
                System.out.println(System.currentTimeMillis() - start >= 1000);
                continue;
            }

            System.out.println(start + 1000 - System.currentTimeMillis());
            Thread.sleep(start + 1000 - System.currentTimeMillis());
        }
    }
}
