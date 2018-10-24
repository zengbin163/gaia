package test.jdk.io.a;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.Console;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;

/**
 * Created by 张少昆 on 2018/5/3.
 */
public class DataIOStreamTest {

    @Test
    public void r0(){ //
        System.out.println(System.in);
        System.out.println(System.out);

        Map<String, String> env = System.getenv(); //看看到底有没有SecurityManager，结果表明，默认是没有的 - null
        System.out.println(env);
    }

    @Test
    public void dataInputStream(){
        DataInputStream dis = new DataInputStream(System.in);
        byte[] bytes = new byte[10];
        try{
            System.out.println(dis.readFloat());
            int read = dis.read(bytes);
            System.out.println(read);

            dis.readFully(bytes);

        } catch(IOException e){
            e.printStackTrace();
        }
    }

    @Test
    public void byteArrayInputStream(){
        byte[] arr = new byte[20];

        Random random = new Random();
        random.nextBytes(arr);
        System.out.println(Arrays.toString(arr));

        ByteArrayInputStream bais = new ByteArrayInputStream(arr);
        byte[] bytes = new byte[10];
        try{
            int read = bais.read(bytes);
            System.out.println(read);
            System.out.println(Arrays.toString(bytes));
        } catch(IOException e){
            e.printStackTrace();
        }

    }

    @Test
    public void read_vs_readFully(){ //TODO 这个才是我要测试的，已完成
        byte[] arr = new byte[20];

        Random random = new Random();
        random.nextBytes(arr);
        System.out.println(Arrays.toString(arr));

        ByteArrayInputStream bais = new ByteArrayInputStream(arr);
        DataInputStream dis = new DataInputStream(bais);

        byte[] bytes = new byte[10];

        try{
            int read = dis.read(bytes); //能读多少读多少
            System.out.println(read);
            System.out.println(Arrays.toString(bytes));

            System.out.println(dis.readByte());

            dis.readFully(bytes);//TODO 看看和上面的什么区别！首先，是接着上面的读，OK，没问题。然后呢？必须读满bytes.length！否则报错！！！
            System.out.println(Arrays.toString(bytes));

        } catch(IOException e){
            e.printStackTrace();

            //异常后使用read(byte[])，发现无问题，能读多少读多少
            int read = 0;
            try{
                read = dis.read(bytes);
                System.out.println(read);
                System.out.println(Arrays.toString(bytes));
            } catch(IOException e1){
                e1.printStackTrace();
            }
        }
    }

    @Test
    public void r2(){
        Console console = System.console();
        System.out.println("console: " + console); //FIXME 奇怪，使用IDEA得到的是null！而CMD执行则不是null！

        PrintWriter writer = console.writer();
        System.out.println("writer: " + writer);

        for(int i = 0; i < 10; i++){
//            writer.write(i);
            console.printf(i + "");
        }
    }


}
