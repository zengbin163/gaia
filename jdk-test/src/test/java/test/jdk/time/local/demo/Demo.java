package test.jdk.time.local.demo;

import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张少昆 on 2017/10/6.
 */
public class Demo {

    // 计算出生到现在过去了多少天
    @Test
    public void days(){
        long days = LocalDate.now().until(LocalDate.of(2000, 9, 1), ChronoUnit.DAYS);
        System.out.println("从出生到现在，总共过去这么多天: " + Math.abs(days));
    }

    // 列出21世纪所有的星期五
    @Test
    public void fridays(){
        List<LocalDate> list = new ArrayList<>(6000);// 55*100
        LocalDate start = LocalDate.of(2000, 1, 1);
        LocalDate end = LocalDate.of(2099, 12, 31);

        // 防止第一天就是周五，先执行一次nextOrSame
        LocalDate tmp = start.with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY));
        if(tmp.isEqual(start)){
            System.out.println("第一天就是周五啦！");
        }
        // 然后，循环计算后面的周五
        do{
            list.add(tmp); // 第一次是上面计算的周五
            tmp = tmp.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
        } while(tmp.isBefore(end));

        System.out.println(list.size());
        System.out.println(list.get(0));
        System.out.println(list.get(list.size() - 1));
    }
}
