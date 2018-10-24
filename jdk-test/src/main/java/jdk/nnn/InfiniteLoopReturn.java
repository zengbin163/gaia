package jdk.nnn;

import java.util.concurrent.CountDownLatch;

public class InfiniteLoopReturn {
    public static void main(String[] args) {

        CountDownLatch countDownLatch = new CountDownLatch(1);
        Runnable runnable = () -> {
            try {
                while (true)
                    return;
            } finally {
                System.out.println("呵呵");
                countDownLatch.countDown();
            }
        };

        new Thread(runnable).start();

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
