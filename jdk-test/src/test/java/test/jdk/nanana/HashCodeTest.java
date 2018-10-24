package test.jdk.nanana;

import org.junit.Test;

/**
 * Created by 张少昆 on 2018/5/15.
 */
public class HashCodeTest {

    //String#hashCode 结果是int，就是int的上下限！
    @Test
    public void r1(){
        System.out.println("abc上帝发誓阀手动阀啊".hashCode());
        System.out.println("def呵呵呵呵".hashCode());

        String s1="abcdefg";
        String s2="gfedcba";

    }
}
