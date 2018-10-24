package test.jvm.gc2;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

/**
 * 二者结果，先gc，再runFinalize，会立即清理内存!
 * gc(); runFinalize(); 它会立即清理内存
 * <p>
 * Created by zengbin on 2018/5/7.
 */
public class FinalizeTest {

    Object2GC obj = new Object2GC();

    @Test
    public void r1() throws InterruptedException{
        for(int i = 0; i < 10; i++){
            new Object2GC();
            System.runFinalization(); //没用。。
            System.out.println("---");
        }
        Thread.sleep(1000L * 10);
    }

    @Test
    public void r2() throws InterruptedException{
        for(int i = 0; i < 10; i++){
            new Object2GC();

            System.gc();//TODO 奇怪，怎么就立即执行了？？？jvm无所事事，只能回收了。。
            System.out.println("---");
        }
        Thread.sleep(1000L * 10);
    }

    @Test
    public void r3() throws InterruptedException{//TODO 还是模拟不出延后gc的情况！
        ArrayList<Date> list = new ArrayList<>();
        for(int i = 0; i < 100; i++){
            new Object2GC();
            for(int j = 0; j < 1000000; j++){
                list.add(new Date());
            }
            new Thread(() -> {
                while(true){
                }
            }).start();
            System.gc();//
//            System.runFinalization();//
            System.out.println("---");
        }
        Thread.sleep(1000L * 10);
    }


}
