package test.jdk.nanana;

import org.junit.Test;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zengbin on 2018/4/27.
 */
public class FunctionalInterfaceTest {
    @Test
    public void r1(){
        doCall(() -> {
            System.out.println("hello world");
        });

    }

    public void doCall(AnInterface anInterface){
        anInterface.call();
    }

    @FunctionalInterface
    interface AnInterface {
        void call();
    }

}
