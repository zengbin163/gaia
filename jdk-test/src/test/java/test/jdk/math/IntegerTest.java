package test.jdk.math;

import org.junit.Test;

/**
 * TODO 结论：Integer.valueOf及相关的方法得到的Integer，才有可能获取cache。前提[-128, high]
 * TODO high可以指定，默认127。
 * <p>
 * Created by zengbin on 2018/3/30.
 */
public class IntegerTest {
    @Test
    public void r1(){
        Integer i1 = new Integer(1);
        Integer i2 = new Integer(1);

        System.out.println(i1 == i2);

        System.out.println(i1.equals(i2));
    }

    @Test
    public void r2(){
        Integer i1 = Integer.valueOf(1);
        Integer i2 = Integer.valueOf(1);

        System.out.println(i1 == i2);

        System.out.println(i1.equals(i2));
    }

    @Test
    public void r22(){
        Integer i1 = 1;//自动装箱，看来用的是valueOf
        Integer i2 = 1;
        Integer i3 = Integer.valueOf(1);

        System.out.println(i1 == i2);
        System.out.println(i1 == i3);

        System.out.println(i1.equals(i2));
        System.out.println(i1.equals(i3));
    }

    @Test
    public void r3(){
        Integer i1 = Integer.valueOf(128);
        Integer i2 = Integer.valueOf(128);

        System.out.println(i1 == i2);

        System.out.println(i1.equals(i2));
    }

    @Test
    public void r4(){
        Integer i1 = new Integer(1);
        Integer i2 = Integer.valueOf(1);

        System.out.println(i1 == i2);

        System.out.println(i1.equals(i2));
    }

    //String.valueOf 是调用了参数类型的包装类的#toString方法。
    @Test
    public void r5(){
        String s1 = String.valueOf(5);
        String s2 = String.valueOf(5);
        String s3 = String.valueOf(555);
        String s4 = String.valueOf(555);

        System.out.println(s1 == s2);
        System.out.println(s3 == s4);
    }
}
