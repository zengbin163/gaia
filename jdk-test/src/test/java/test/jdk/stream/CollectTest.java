package test.jdk.stream;

import org.junit.Test;

import java.util.ArrayList;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by zengbin on 2018/10/22.
 */
public class CollectTest {

    @Test
    public void r1(){
        Stream<String> stream = Stream.of("hehe", "nihao", "wohao", "godie");

        // System.out.println(stream.collect(Collectors.toList()));
        ArrayList new_list = stream.collect(() -> {//1. supplier; 2. accumulator; 3. two container reduction! two????
            System.out.println("new list");
            return new ArrayList();
        }, ((arrayList, s) -> {
            arrayList.add(s);
        }), ((arrayList, arrayList2) -> {
            System.out.println(arrayList == arrayList2); //TODO 没有调用？？为啥？
            arrayList.addAll(arrayList2);
        }));

        System.out.println(new_list);
    }

    @Test
    public void r2(){
        Stream<String> stream = Stream.of("hehe", "nihao", "wohao", "godie");

        System.out.println(stream.collect(Collectors.joining()));
    }

    @Test
    public void r3(){
        Stream<String> stream = Stream.of("hehe", "nihao", "wohao", "godie");
        System.out.println(stream.collect(Collectors.toCollection(TreeSet::new)));
    }
}
