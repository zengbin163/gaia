package test.jdk.thread.atomic;

import org.junit.Test;

import java.util.Date;
import java.util.concurrent.atomic.AtomicMarkableReference;

/**
 * 同样是解决ABA问题，（见AtomicStampReferenceTest的说明）。
 * AtomicMarkableReference跟AtomicStampedReference差不多，
 * AtomicStampedReference是使用pair的int stamp作为计数器使用，AtomicMarkableReference的pair使用的是boolean mark。
 * 还是那个水的例子，AtomicStampedReference可能关心的是动过几次，AtomicMarkableReference关心的是有没有被人动过，方法都比较简单。
 * <p>
 * 内部实现是靠内部类Pair
 * <p>
 * Created by zengbin on 2018/5/13.
 */
public class AtomicMarkableReferenceTest {

    Date date = new Date();
    AtomicMarkableReference ref = new AtomicMarkableReference(date, true);

    @Test
    public void r1(){

        System.out.println("isMarked(): " + ref.isMarked());
        System.out.println(ref.getReference() == date);

        ref.set(new Date(), false);
        System.out.println("isMarked(): " + ref.isMarked());
        System.out.println(ref.getReference() == date);

        //TODO 可能返回假的false，但，如果没有其他线程同时操作的话，且expectedValue和currentValue相符的话，重复的操作最终还是会成功。尼玛，屁话
        boolean b = ref.attemptMark(new Date(), true);
        System.out.println(b);
    }

    @Test
    public void r2(){
        //expectedRef, newRef, expectedMark, newMark
        boolean b = ref.compareAndSet(date, new Date(), true, false);
        System.out.println(b);
    }

    @Test
    public void r3(){
        boolean[] bs = new boolean[12]; //用来接收mark的，仅用[0]接收
        Object o = ref.get(bs); //TODO 看源码，一目了然

        System.out.println(bs[0]);
        System.out.println(o == date);
    }
}
