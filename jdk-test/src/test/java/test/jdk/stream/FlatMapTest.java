package test.jdk.stream;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by 张少昆 on 2018/10/22.
 */
public class FlatMapTest {
    @Test
    public void r1(){
        Stream<List<Integer>> stream = Stream.of(Arrays.asList(1), Arrays.asList(2, 3), Arrays.asList(4, 5, 6));

        System.out.println(stream.flatMap(list -> list.stream()).map(e -> e * e).collect(Collectors.toList()));
    }
}
