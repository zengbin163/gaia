package test.jdk.nanana;

import org.junit.Test;

import java.io.IOException;
import java.util.Date;

/**
 * 一切正常。
 * <p>
 * Created by zengbin on 2018/5/23.
 */
public class RefTest {
    static volatile Date date = null;

    @Test
    public void r1(){
        date = new Date(111);

        new X().start();

        try{
            Thread.sleep(10); //让线程不会先于当前线程执行
        } catch(InterruptedException e){
            e.printStackTrace();
        }
        date = new Date(99999999);

        try{
            System.in.read();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    static class X extends Thread {
        @Override
        public void run(){
            Date d = date;
            try{
                Thread.sleep(1000L * 3);
                System.out.println(d == date);
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    @Test
    public void r2(){
        date = new Date();
        Date d = date;

        date = new Date(111);

        System.out.println(d == date);
    }
}
