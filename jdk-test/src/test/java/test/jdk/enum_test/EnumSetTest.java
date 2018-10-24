package test.jdk.enum_test;

import org.junit.Test;

import java.util.EnumSet;

/**
 * 测试下EnumSet
 * <p>
 * Created by zengbin on 2018/1/30.
 */
public class EnumSetTest {
    enum Gender {MALE, FEMALE}

    enum Week {
        MON, TUE, WED, THI, FRI, SAT, SUN
    }

    @Test
    public void r1(){
        //全部
        EnumSet<Gender> set = EnumSet.allOf(Gender.class);
        System.out.println(set);

        //什么都不要，空集合
        EnumSet<Gender> set1 = EnumSet.noneOf(Gender.class);
        //补充？？？取反操作啊
        EnumSet<Gender> set2 = EnumSet.complementOf(set1);
        System.out.println(set2);

        //自选
        EnumSet<Week> set3 = EnumSet.of(Week.MON, Week.SAT);
        System.out.println(set3);

        //范围，啧啧，很有用啊
        EnumSet<Week> range = EnumSet.range(Week.MON, Week.SUN);
        System.out.println(range);
    }
}
