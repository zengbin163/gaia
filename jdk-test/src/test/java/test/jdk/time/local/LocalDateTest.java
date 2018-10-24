package test.jdk.time.local;

import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.chrono.Era;
import java.time.chrono.IsoChronology;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * 本地日期相关。
 * <p>
 * 所谓Local，就是不考虑时区TimeZone。
 * 生日、假期、会议时间等，最好使用本地日期或时间来表示。
 * <p>
 * Created by 张少昆 on 2017/10/6.
 */
public class LocalDateTest {
    @Test
    public void basic(){
        LocalDate now = LocalDate.now();

        System.out.println(now); // 2017-10-06
        System.out.println("now.toEpochDay(): " + now.toEpochDay());
        System.out.println(now.getYear());
        Month month = now.getMonth();//
        System.out.println(month);
        System.out.println(now.getMonthValue());
        System.out.println(now.getDayOfYear());
        System.out.println(now.getDayOfMonth());

        DayOfWeek dayOfWeek = now.getDayOfWeek();
        System.out.println(dayOfWeek);
        System.out.println(dayOfWeek.name());
        System.out.println(dayOfWeek.ordinal());//enum 的坐标，一般用户用用不到
        System.out.println(dayOfWeek.getValue());// 这个才是用的

        Era era = now.getEra();
        System.out.println(era);
        IsoChronology chronology = now.getChronology();
        System.out.println(chronology);
    }

    @Test
    public void basic2(){
        LocalDate date = LocalDate.of(1999, 11, 17);
        System.out.println(date);

        date = LocalDate.of(2009, Month.FEBRUARY, 28);
        System.out.println(date);

        date = date.plusDays(1);
        System.out.println(date);

        date = date.withYear(2008);
        System.out.println(date);

        System.out.println(date.isBefore(LocalDate.now()));
        System.out.println(date.isLeapYear());

        // 看不懂月日啥意思
        Period period = date.until(LocalDate.now());
        System.out.println(period);
        System.out.println(period.getYears());
        System.out.println(period.getMonths());
        System.out.println(period.getDays());
        System.out.println(period.getUnits());

        long days = date.until(LocalDate.now(), ChronoUnit.DAYS);// GOOD 很有用
        System.out.println(days);
    }

    @Test
    public void basic3(){
        LocalDate date = LocalDate.of(2007, 11, 17);
        System.out.println(date);
        System.out.println(date.isLeapYear());

        LocalDate date2 = date.plusYears(1);// OK
        System.out.println(date2);

        LocalDate date3 = date.plus(Period.ofYears(1));// OK
        System.out.println(date3);

        LocalDate date4 = date.plus(Period.ofDays(365));// ERROR，因为2008年是闰年
        System.out.println(date4);
    }

    // 注意，如果产生了不存在的日期（如2月31），那方法会返回该月份的最后一个日期。
    @Test
    public void basic4(){
        LocalDate date1 = LocalDate.of(2016, 1, 31).plusMonths(1);
        LocalDate date2 = LocalDate.of(2016, 3, 31).minusMonths(1);
        System.out.println(date1);
        System.out.println(date2);
    }

    @Test
    public void period(){
        Period period = Period.of(2, -13, 7);//注意，是"任意值" - 非正常格式
        System.out.println(period.getUnits());

        System.out.println(period.get(ChronoUnit.DAYS));// 只是某个单位上的值，不是转成某个单位表示
        System.out.println(period.toTotalMonths());//

        // 只是某个单位上的值，不是转成某个单位表示
        System.out.println(period.getYears());
        System.out.println(period.getMonths());
        System.out.println(period.getDays());

        //--------------------------------------
        //转成正常格式
        Period normalized = period.normalized();
        System.out.println(normalized.get(ChronoUnit.DAYS));
        System.out.println(normalized.toTotalMonths());//

        // 只是某个单位上的值，不是转成某个单位表示
        System.out.println(normalized.getYears());
        System.out.println(normalized.getMonths());
        System.out.println(normalized.getDays());
    }


    //输出两个日期之间的所有日期
    @Test
    public void days(){
        List<LocalDate> list = new ArrayList<>();

        LocalDate date1 = LocalDate.of(2018, 4, 19);
        LocalDate date2 = LocalDate.of(2018, 5, 20);
        long days = date1.until(date2, ChronoUnit.DAYS);
        for(int i = 0; i < days; i++){
            LocalDate tmp = date1.plusDays(i);
            list.add(tmp);
        }
        System.out.println(list);
    }
}
