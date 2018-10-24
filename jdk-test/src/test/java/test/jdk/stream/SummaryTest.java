package test.jdk.stream;

import org.junit.Test;

import java.util.IntSummaryStatistics;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * Created by zengbin on 2018/10/22.
 */
public class SummaryTest {
    @Test
    public void r1(){
        IntStream stream = new Random().ints(0, 101);
        IntSummaryStatistics summaryStatistics = stream.limit(20).peek(System.out::println).summaryStatistics();
        System.out.println(summaryStatistics);//count,sum, min, max, average
    }
}
