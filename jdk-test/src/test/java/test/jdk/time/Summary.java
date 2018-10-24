package test.jdk.time;

import org.junit.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Created by 张少昆 on 2017/10/6.
 */
public class Summary {
    @Test
    public void r1(){
        System.out.println(LocalTime.now());//UTC+8
        System.out.println(LocalDate.now());//UTC+8
        System.out.println(LocalDateTime.now());//UTC+8
        System.out.println(Instant.now());//UTC
    }
}
