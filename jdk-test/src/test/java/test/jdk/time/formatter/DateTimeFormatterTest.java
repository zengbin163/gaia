package test.jdk.time.formatter;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.Locale;

/**
 * Created by zengbin on 2017/10/6.
 */
public class DateTimeFormatterTest {
    @Test
    public void r1(){
        // Instant now = Instant.now(); // ERROR 不能格式化instant，因为没有时区
        LocalDateTime now = LocalDateTime.now();

        String out = DateTimeFormatter.ISO_DATE_TIME.format(now);// 这里用的是标准时间格式，一般不是用于人类可读，而是用于机器可读的时间戳
        System.out.println(out);
        out = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(now);// 这里用的是标准时间格式，一般不是用于人类可读，而是用于机器可读的时间戳
        System.out.println(out);

        //带OFFSET的，需要使用带时区的
        OffsetDateTime offsetDateTime = now.atOffset(ZoneOffset.ofHours(8));
        out = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withLocale(Locale.getDefault()).format(offsetDateTime);//
        System.out.println(out);
        out = DateTimeFormatter.ISO_ZONED_DATE_TIME.format(offsetDateTime);
        System.out.println(out);

        Date date = new Date(); //嗯嗯，date就是绝对时间！
        System.out.println(date);



        TemporalAccessor parse = DateTimeFormatter.ofPattern("yyyy-MM-dd").parse("2017-10-06");
        System.out.println(parse);
    }

    // Java的时间格式
    // Java 定义了四种日期时间格式，FULL、LONG、MEDIUM、SHORT。
    // 如果需要其他时区的，可以使用formatter.withLocale(..).format(..)
    @Test
    public void r2(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);// FULL
        System.out.println(dateTimeFormatter.format(LocalDateTime.now())); //注意：不能格式化instant，因为没有时区

        dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG); // LONG
        System.out.println(dateTimeFormatter.format(LocalDateTime.now()));
        dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM); // MEDIUM  这个好，没有T。
        System.out.println(dateTimeFormatter.format(LocalDateTime.now()));
        dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT); // SHORT
        System.out.println(dateTimeFormatter.format(LocalDateTime.now()));
    }

    // Java的时间格式
    @Test
    public void r3(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);// MEDIUM  这个好，没有T。
        dateTimeFormatter = dateTimeFormatter.withLocale(Locale.CANADA);
        System.out.println(dateTimeFormatter.format(LocalDateTime.now()));
        dateTimeFormatter = dateTimeFormatter.withLocale(Locale.CHINA);
        System.out.println(dateTimeFormatter.format(LocalDateTime.now()));
    }
}
