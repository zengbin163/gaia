package test.jdk.thread.atomic;

import org.junit.Test;

import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 把之前的Integer或者Long换成引用对象就行。逻辑完全一致。
 *
 * Created by 张少昆 on 2018/5/13.
 */
public class AtomicReferenceTest {

    @Test
    public void r1(){
        Person p1 = new Person();
        Person p2 = new Person();
        Person p3 = new Person();

        AtomicReference<Person> ref = new AtomicReference<>();
        // AtomicReference<Person> ASR = new AtomicReference<>(p);
        ref.set(p1);
        System.out.println("p1: " + ref.get());

        Person p = ref.getAndSet(p2);
        System.out.println(p == p1);

        boolean b = ref.compareAndSet(p2, p3);
        System.out.println(b);
        System.out.println(p3 == ref.get());

        System.out.println(ref.weakCompareAndSet(p3, p1));
        System.out.println(p1 == ref.get());
    }


    static class Person {
        static int count = 0;
        public int id = count++;
        public String name = "name-" + count;
        public Date date = new Date();

        @Override
        public String toString(){
            return "Person{" +
                           "id=" + id +
                           ", name='" + name + '\'' +
                           ", date=" + date +
                           '}';
        }
    }
}
