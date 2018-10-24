package test.jdk.stream;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by zengbin on 2017/10/30.
 */
public class StreamTest {
    @Test
    public void r1(){
        Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        stream.filter(e -> {

            return e > 10;
        });

        stream.peek(null);
        stream.parallel();
        List<Integer> list = stream.collect(Collectors.toList());
        list.stream();
        Stream.concat(null, null);

    }

    @Test
    public void r2(){
        IntStream intStream = IntStream.rangeClosed(1, 10);
        System.out.println(Arrays.toString(intStream.toArray()));
    }

    @Test
    public void r3(){
        IntStream intStream = IntStream.rangeClosed(1, 10);
        intStream.map(e -> {
            System.out.println("原始数字：" + e);
            return e * e;
        }).forEach(System.out::println);
    }

    @Test
    public void r4(){
        IntStream.range(1, 10).map(e -> 11 * e).forEach(System.out::println);
    }
}
