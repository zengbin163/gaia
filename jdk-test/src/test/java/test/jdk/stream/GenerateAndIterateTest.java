package test.jdk.stream;

import org.junit.Test;

import java.util.UUID;
import java.util.stream.Stream;

/**
 * 注意重复调用和迭代计算的不同！
 * <p>
 * Created by zengbin on 2018/10/22.
 */
public class GenerateAndIterateTest {
    @Test
    public void r1(){
        // Stream<String> stream = Stream.generate(UUID.randomUUID()::toString);//FIXME 这样不对！
        Stream<String> stream = Stream.generate(() -> UUID.randomUUID().toString());//重复调用！
        stream.limit(10).forEach(System.out::println);

    }

    @Test
    public void r2(){
        Stream<Integer> stream = Stream.iterate(1, i -> i + 2);//迭代计算
        stream.limit(10).forEach(System.out::println);
    }

}
