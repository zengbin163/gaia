package test.jdk.time.zone;

import org.junit.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Created by zengbin on 2017/10/6.
 */
public class ZoneDateTimeTest {
    @Test
    public void r1(){
        ZonedDateTime now = ZonedDateTime.now();
        System.out.println(now);
        System.out.println(now.getOffset());

        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Shanghai"));
        System.out.println(zonedDateTime);
        System.out.println(zonedDateTime.getOffset());

        Instant instant = zonedDateTime.toInstant();// good
        System.out.println(instant);

        ZonedDateTime utc = instant.atZone(ZoneId.of("UTC"));//不包含夏令时，就是格林威治皇家天文台的时间
        System.out.println(utc);

        System.out.println(utc.getOffset());
    }

    @Test
    public void r2(){
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);

        ZonedDateTime zonedDateTime = now.atZone(ZoneId.of("Asia/Shanghai"));
        System.out.println(zonedDateTime);

        ZonedDateTime now1 = ZonedDateTime.now();
        System.out.println(now1);

        // now.toEpochSecond(ZoneOffset.UTC)
    }

}
