package test.jdk.unicode;

import org.junit.Test;

/**
 * Created by zengbin on 2018/8/9.
 */
public class UnicodeTest {
    private final String s1 = "我爱你中国！i love u china!";

    @Test
    public void r1(){
        System.out.println(s1.charAt(1));
        int codePoint = s1.codePointAt(1);
        System.out.println(codePoint);
        System.out.println("Character.getName(cp):" + Character.getName(codePoint));
        System.out.println("Character.getNumericValue(cp):" + Character.getNumericValue(codePoint));
        System.out.println("Character.getType(cp):" + Character.getType(codePoint));
        System.out.println("Character.highSurrogate(cp):" + Character.highSurrogate(codePoint));
        System.out.println("Character.isDefined(cp):" + Character.isDefined(codePoint));
        char[] cs = Character.toChars(codePoint);
        for(int i = 0; i < cs.length; i++){
            System.out.println(cs[i]);
        }


        System.out.println(s1.codePointCount(0, s1.length()));
    }
}
