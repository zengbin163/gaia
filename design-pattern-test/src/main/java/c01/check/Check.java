package c01.check;

import c01.singleton.DoubleCheck;

import java.util.concurrent.CountDownLatch;

/**
 * TODO 使用CountDownLatch来等待所有线程结束。
 * <p>
 * 但是，经过测试，貌似无影响啊？哪怕加大线程内循环的次数，结果也没什么影响。
 * <p>
 * Created by zengbin on 2018/4/17.
 */
public class Check {
    public static void main(String[] args){
        long start = System.currentTimeMillis();
        int thno = 10;
        CountDownLatch countDownLatch = new CountDownLatch(thno);

        for(int i = 0; i < thno; i++){
            new Thread(() -> {
                for(int j = 0; j < 1000000; j++){
                    Object o = DoubleCheck.getInstance();
                }
                countDownLatch.countDown();
            }).start();
        }

        try{
            countDownLatch.await();
        } catch(InterruptedException e){
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
