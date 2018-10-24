package test.jdk.thread.atomic;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.stream.IntStream;

/**
 * TODO AtomicIntegerArray类需要注意的是，数组value通过构造方法传递进去，然后AtomicIntegerArray会将当前数组复制一份，
 * TODO 所以当AtomicIntegerArray对内部的数组元素进行修改时，<b>不会影响到传入的数组</b>。
 * <p>
 * Created by zengbin on 2018/5/13.
 */
public class AtomicIntegerArrayTest {

    @Test
    public void r1(){
        int[] arr = IntStream.rangeClosed(1, 10).toArray();

        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(arr);
        int i = atomicIntegerArray.addAndGet(3, 10);
        System.out.println(i);
        System.out.println("原子数组：" + atomicIntegerArray.get(3));
        System.out.println("原数组：" + arr[3]);

        System.out.println("---------");
        boolean b = atomicIntegerArray.compareAndSet(5, 5, 15);
        System.out.println(b);
        System.out.println("原子数组：" + atomicIntegerArray.get(5));
        System.out.println("原数组：" + arr[5]);

        boolean b1 = atomicIntegerArray.weakCompareAndSet(1, 11, 11);
        System.out.println(b);
        System.out.println("原子数组："+atomicIntegerArray.get(1));
        System.out.println("原数组：" + arr[1]);

    }
}
