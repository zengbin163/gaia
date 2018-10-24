package test.jdk.uuid;

import org.junit.Test;

import java.util.Base64;
import java.util.Random;
import java.util.UUID;

/**
 * Created by zengbin on 2017/12/27.
 */
public class UuidTest {
    @Test
    public void r1(){
        System.out.println(UUID.randomUUID().toString());
        System.out.println(new String(Base64.getEncoder().encode("84c65".getBytes())));

        System.out.println(getRandNum(5));
    }

    public String getRandNum(int charCount) {
        String charValue = "";
        for (int i = 0; i < charCount; i++) {
            char c = (char) (randomInt(0, 10) + '0');
            charValue += String.valueOf(c);
        }
        return charValue;
    }
    public int randomInt(int from, int to) {
        Random r = new Random();
        return from + r.nextInt(to - from);
    }
}
