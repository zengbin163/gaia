package test.jdk.nanana.pojo;

import org.junit.Test;

/**
 * 测试某个类的静态变量被继承后，是否所有继承类都是这个静态变量！
 * 结果：是！
 * <p>
 * Created by zengbin on 2018/3/25.
 */
public class S2 extends P {
    @Test
    public void r1(){
        P.KEY = "P";
        S1.KEY = "S1";
        S2.KEY = "S2";
        System.out.println(P.KEY);
        System.out.println(S1.KEY);
        System.out.println(S2.KEY);

    }
}
