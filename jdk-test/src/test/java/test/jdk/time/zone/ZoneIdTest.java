package test.jdk.time.zone;

import org.junit.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Created by zengbin on 2017/10/6.
 */
public class ZoneIdTest {
    @Test
    public void all(){
        Set<String> availableZoneIds = ZoneId.getAvailableZoneIds();
        System.out.println(availableZoneIds);

        Stream<String> stream = availableZoneIds.stream().filter(e -> {
            return e.contains("China") || e.contains("GMT+8") || e.contains("Asia");
        });
        stream.forEach(System.out::println);
    }

    @Test
    public void systemZoneId(){
        System.out.println(ZoneId.systemDefault()); // Asia/Shanghai
    }

    @Test
    public void r1(){
        // ZoneId shanghai = ZoneId.of("Shanghai"); // ERROR
        ZoneId shanghai = ZoneId.of("Asia/Shanghai");
        System.out.println(shanghai);
    }


}
