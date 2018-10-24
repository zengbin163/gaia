package test.jdk.time;

import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

/**
 * Created by zengbin on 2017/10/6.
 */
public class TemporalAdjusterTest {
    @Test
    public void r1(){
        LocalDate date = LocalDate.of(2017, 10, 6);
        System.out.println(date);

        // 直接获取下一个周二
        date = date.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        System.out.println(date);

        // 直接获取上一个周二
        date = date.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        System.out.println(date);

        // 直接获取当前（如果是）或下一个周二
        date = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));
        System.out.println(date);
    }

    @Test
    public void r2(){
        LocalDate date = LocalDate.of(2017, 10, 6);
        System.out.println(date);

        //将date中的day改为第一个周一。卧槽，牛掰啊
        LocalDate date2 = date.with(TemporalAdjusters.dayOfWeekInMonth(1, DayOfWeek.MONDAY));
        System.out.println("dayOfWeekInMonth(1, DayOfWeek.MONDAY): " + date2);
        //等同于
        date2 = date.with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));
        System.out.println("firstInMonth(DayOfWeek.MONDAY): " + date2);
        //
        date2=date.with(TemporalAdjusters.lastInMonth(DayOfWeek.MONDAY));
        System.out.println("lastInMonth(DayOfWeek.MONDAY): " + date2);

        //年度第一天
        LocalDate date3 = date.with(TemporalAdjusters.firstDayOfYear());
        System.out.println("firstDayOfYear: " + date3);
        LocalDate date4 = date.with(TemporalAdjusters.firstDayOfMonth());
        System.out.println("firstDayOfMonth: " + date4);
        LocalDate date5 = date.with(TemporalAdjusters.firstDayOfNextMonth());
        System.out.println("firstDayOfNextMonth: " + date5);
        LocalDate date6 = date.with(TemporalAdjusters.firstDayOfNextYear());
        System.out.println("firstDayOfNextYear: " + date6);
    }
}
