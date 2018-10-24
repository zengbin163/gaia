package test.jdk.nanana;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by zengbin on 2018/6/28.
 */
public class DoubleAccumulator {
    static class AD{
        double a;
    }

    @Test
    public void r1(){
        AD ad = new AD();
        AtomicReference<AD> ref = new AtomicReference<>(ad);
        ref.set(new AD());
        AD ad1 = ref.get();

        // while(ref.compareAndSet(ad1,new AD()))
    }
}
