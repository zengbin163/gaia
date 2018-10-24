package test.jdk.time.local;

import org.junit.Test;

import java.time.LocalDateTime;

/**
 * 本地 日期+时间。
 * <p>
 * Created by zengbin on 2017/10/6.
 */
public class LocalDateTimeTest {
    @Test
    public void r1(){
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);
    }
}
