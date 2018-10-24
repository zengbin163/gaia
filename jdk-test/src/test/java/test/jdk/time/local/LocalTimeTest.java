package test.jdk.time.local;

import org.junit.Test;

import java.time.LocalTime;
import java.time.ZoneId;

/**
 * 本地时间。
 * <p>
 * Created by zengbin on 2017/10/6.
 */
public class LocalTimeTest {
    @Test
    public void r1(){
        LocalTime now = LocalTime.now();
        System.out.println(now);
    }

    @Test
    public void r2(){
        LocalTime time = LocalTime.of(22, 10);
        System.out.println(time);

        // funny, yesterday's 23:10
        LocalTime localTime = time.minusHours(23);
        System.out.println(localTime);
    }
}
