package test.jvm.ref;

import org.junit.Test;
import test.jvm.gc2.Object2GC;

import java.lang.ref.PhantomReference;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * Created by 张少昆 on 2018/5/7.
 */
public class ReferenceTest {

    //r1/r2/r3 三个测试一脉相承
    @Test
    public void r1() throws InterruptedException{
        WeakReference<Object2GC> wr = new WeakReference<>(new Object2GC());
        wr.clear();

        SoftReference<Object2GC> sr = new SoftReference<>(new Object2GC());
        sr.clear();

        PhantomReference<Object2GC> pr = new PhantomReference<>(new Object2GC(), null);
        pr.clear();

        Thread.sleep(1000L * 10);
    }

    //r1/r2/r3 三个测试一脉相承
    @Test
    public void r2() throws InterruptedException{
        WeakReference<Object2GC> wr = new WeakReference<>(new Object2GC());
        System.out.println(wr.isEnqueued());

        SoftReference<Object2GC> sr = new SoftReference<>(new Object2GC());
        System.out.println(sr.isEnqueued());

        PhantomReference<Object2GC> pr = new PhantomReference<>(new Object2GC(), null);
        System.out.println(pr.isEnqueued());

        Thread.sleep(1000L * 10);
    }

    //r1/r2/r3 三个测试一脉相承
    @Test
    public void r3() throws InterruptedException{
        WeakReference<Object2GC> wr = new WeakReference<>(new Object2GC());
        System.out.println("enqueue: " + wr.enqueue());
        System.out.println(wr.isEnqueued());

        SoftReference<Object2GC> sr = new SoftReference<>(new Object2GC());
        System.out.println("enqueue: " + sr.enqueue());
        System.out.println(sr.isEnqueued());

        PhantomReference<Object2GC> pr = new PhantomReference<>(new Object2GC(), null);
        System.out.println("enqueue: " + pr.enqueue());
        System.out.println(pr.isEnqueued());

        Thread.sleep(1000L * 10);
    }

}
