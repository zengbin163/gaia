package test.jvm.gc2;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zengbin on 2018/5/7.
 */
public class Object2GC {
    static AtomicInteger count = new AtomicInteger(0);

    @Override
    protected void finalize() throws Throwable{
        System.out.println(this + " 被finalize了！" + count.getAndIncrement());
    }
}
