package test.jdk.thread.atomic;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 为了解决ABA问题，就是一个线程expect A，要改成B，而另一个线程expect B要改成A。
 * 这样A->B->，结果在第一个线程看来还没有变化，又改成了B。
 * <p>
 * 就像一杯水，别人喝了，又给你满上，你不知道别人喝过没有。
 * <p>
 * AtomicMarkableReference跟AtomicStampedReference差不多，
 * AtomicStampedReference是使用pair的int stamp作为计数器使用，AtomicMarkableReference的pair使用的是boolean mark。
 * 还是那个水的例子，AtomicStampedReference可能关心的是动过几次，AtomicMarkableReference关心的是有没有被人动过，方法都比较简单。
 * <p>
 * Created by 张少昆 on 2018/5/13.
 */
public class AtomicStampReferenceTest {

    final static AtomicStampedReference<Integer> ASR = new AtomicStampedReference(0, 0);

    @Test
    public void r1() throws InterruptedException{
        final int stamp = ASR.getStamp();
        final Integer reference = ASR.getReference();
        System.out.println(reference + " ============ " + stamp);
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run(){
                System.out.println(reference + "-" + stamp + "-" + ASR.compareAndSet(reference, reference + 10, stamp, stamp + 1));
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run(){
                Integer reference = ASR.getReference();
                System.out.println(reference + "-" + stamp + "-" + ASR.compareAndSet(reference, reference + 10, stamp, stamp + 1));
            }
        });
        t1.start();
        t1.join();//Waits for this thread to die.
        t2.start();
        t2.join();

        System.out.println(ASR.getReference());
        System.out.println(ASR.getStamp());
    }
}
