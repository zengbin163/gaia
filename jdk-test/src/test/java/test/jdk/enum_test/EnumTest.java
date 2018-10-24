package test.jdk.enum_test;

import org.junit.Test;

/**
 * 注意，enum其实都是实现了Enum抽象类。
 * Class类有判断cls是否Enum的操作。
 * <p>
 * Created by zengbin on 2017/11/20.
 */
public class EnumTest {
    enum Week {
        MON, TUE, WED, THI, FRI, SAT, SUN
    }

    @Test
    public void r1(){
        System.out.println(Week.MON == Week.MON); //哈哈 enum的实例是final的！！
    }

    @Test
    public void r2(){
        TemplateEnum[] values = TemplateEnum.values();
        for(TemplateEnum value : values){
            System.out.println(value);
        }
    }
}
