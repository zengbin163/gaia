package test.jdk.lambda;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * DoubleStream.collect，见r2()。
 * 再看r1()的排序。
 * <p>
 * Created by zengbin on 2018/1/27.
 */
public class ComparatorTest {

    @Test
    public void r1(){
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        DoubleStream stream = random.doubles(10);

        ArrayList<BigDecimal> tmp = new ArrayList<>();
        // ArrayList<BigDecimal> collect = stream.collect(() -> tmp, (acc, t) -> { //嘿嘿，每次都使用同一个list来接收对象，防止重复创建临时对象
        //     acc.add(new BigDecimal(t));
        // }, ArrayList::addAll); //
        // System.out.println(collect);

        stream.collect(() -> tmp, (acc, t) -> { //其实这样做，还不如遍历呢
            acc.add(new BigDecimal(t));
        }, null); //
        System.out.println(tmp); //注意这里输出的对象和前面不同

        Collections.sort(tmp, BigDecimal::compareTo); //排序，升序

        System.out.println("排序后" + tmp);
    }


    public static <T> List<T> filter(Stream<T> stream, Predicate<T> predicate){
        return stream.collect(ArrayList::new, (acc, t) -> {
            System.out.println(acc + "@" + acc.hashCode() + " : " + t);
            if(predicate.test(t)){
                acc.add(t);
            }
        }, ArrayList::addAll); //TODO 第一个是每次生成一个临时list，第二个是每次往当前的临时list里添加内容，第三个，则是将当前的临时list内容添加到最终的集合中
        // TODO 换句话说，前两个类似于拉链，互相作用，而结果则保存到最后一个
        // TODO 第二个的(acc, t) ，其中acc就是第一个，t则是stream中的值！！！
    }

    @Test
    public void r2(){
        IntStream range = IntStream.range(3, 10);
        Stream<Integer> stream = range.mapToObj(e -> e);
        List<Integer> filter = filter(stream, (e) -> e % 2 == 0);
        System.out.println(filter + "@" + filter.hashCode());
    }

}
