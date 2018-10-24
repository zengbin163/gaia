package test.jdk.thread.atomic;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicLongFieldUpdater;

/**
 * AtomicIntegerFieldUpdater, AtomicLongFieldUpdater和AtomicReferenceFieldUpdater
 * <p>
 * Created by 张少昆 on 2018/5/11.
 */
public class AtomicLongFieldUpdaterTest {
    @Test
    public void r1(){
        AtomicLongFieldUpdater<Person> updater = AtomicLongFieldUpdater.newUpdater(Person.class, "count");
        Person p1 = new Person();
        long re = updater.addAndGet(p1, 10);
        System.out.println(re);

        re = updater.get(p1);
        System.out.println(re);

        re = updater.decrementAndGet(p1);
        System.out.println(re);

        // updater.lazySet();
        boolean b = updater.compareAndSet(p1, 10, 11);//false
        System.out.println(b);

        boolean b1 = updater.weakCompareAndSet(p1, 10, 11);//false
        System.out.println(b1);
    }

    static class Person {
        volatile long count;

    }
}
