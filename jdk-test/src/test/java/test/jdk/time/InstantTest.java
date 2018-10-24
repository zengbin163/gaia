package test.jdk.time;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;

/**
 * 时间相关。确切的说，是UTC时间。
 * <p>
 * 需要说明的是：java.time包下的涉及日期时间的类，其对象都是不可变的！
 * 这点与java.util.Date不同。
 * <p>
 * Created by 张少昆 on 2017/10/6.
 */
public class InstantTest {
    @Test
    public void basic(){
        Instant now = Instant.now();
        System.out.println("System.currentTimeMillis: " + System.currentTimeMillis());
        System.out.println(now);

        System.out.println("now.getEpochSecond: " + now.getEpochSecond());
        System.out.println("now.getNano: " + now.getNano());

        // Epoch指的是一个特定的时间：1970-01-01 00:00:00 UTC。
        // 协调世界时（英：Coordinated Universal Time ，法：Temps Universel Coordonné），又称世界统一时间，世界标准时间，国际协调时间。英文（CUT）和法文（TUC）的缩写不同，作为妥协，简称UTC。
        // 为了方便，在不需要精确到秒的情况下，通常也将GMT 和UTC 视作等同。尽管UTC 更加科学更加精确，但是对于手表玩家和收藏者来说，GMT 仍是更加受欢迎的。
        System.out.println("now.toEpochMilli: " + now.toEpochMilli());
    }

    /**
     * 注意，只能用于两个Instant之间，不要用于两个date之间，会出问题。因为存在闰年等问题。
     * date之间请使用period。
     *
     * @throws InterruptedException
     */
    @Test
    public void duration() throws InterruptedException{
        Instant start = Instant.now();
        Thread.sleep(3000);
        Instant end = Instant.now();

        Duration duration = Duration.between(start, end);

        System.out.println("duration.getUnits(): " + duration.getUnits());

        printDuration(duration);

        duration = duration.multipliedBy(5000);// 乘法
        printDuration(duration);

        duration = duration.dividedBy(-1);// 除法
        printDuration(duration);

        duration = duration.plusDays(3); // 加上某个单位
        printDuration(duration);

        duration = duration.negated();// 取负值
        printDuration(duration);

        duration = duration.plus(duration);// 加上一个duration
        printDuration(duration);

    }

    private void printDuration(Duration duration){
        System.out.println("duration: " + duration);// PT3.001S
        // 以下toXxx，是转成Xxx表示，但都是long类型，也就是说不存在小数
        System.out.println("duration.toDays(): " + duration.toDays());// 0
        System.out.println("duration.toHours(): " + duration.toHours());// 0
        System.out.println("duration.toMinutes(): " + duration.toMinutes());// 0
        System.out.println("duration.toMillis(): " + duration.toMillis());// 3000
        System.out.println("duration.toNanos(): " + duration.toNanos());// 3000000000

        System.out.println("[]duration.getSeconds(): " + duration.getSeconds());// 3 获取的是second部分，而非second表示
        System.out.println("[]duration.getNano: " + duration.getNano());// 0 获取的是nano部门，而非nano表示

        System.out.println("duration.isZero(): " + duration.isZero());
        System.out.println("duration.isNegative: " + duration.isNegative());

        System.out.println("----------------------------------");
    }
}
