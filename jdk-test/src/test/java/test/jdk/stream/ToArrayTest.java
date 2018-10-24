package test.jdk.stream;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;
import java.util.function.IntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by zengbin on 2018/10/22.
 */
public class ToArrayTest {
    IntStream intStream = null;
    Stream stream = null;

    @Before
    public void init(){
        intStream = new Random().ints(0, 100); //unlimited!
    }

    @Test
    public void r1(){
        // System.out.println(intStream.count());//no ends!
        // intStream.forEach(System.out::println);
        stream = intStream.boxed();
    }

    @Test
    public void r2(){
        Stream<String> stream = Stream.of("hehe", "nihao", "wohao", "godie");
        // Object[] objects = stream.toArray();//en?
        IntFunction<String[]> generator = len -> { //TODO 这个方法很有意思，将stream的size作为参数传进来，然后指定数组即可
            System.out.println("len: " + len);
            return new String[len];
        };
        String[] arr = stream.toArray(generator);
        System.out.println(String.join(",", arr));
    }

}
