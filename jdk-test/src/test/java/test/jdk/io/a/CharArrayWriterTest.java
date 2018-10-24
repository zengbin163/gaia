package test.jdk.io.a;

import org.junit.Test;

import java.io.CharArrayReader;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.util.Arrays;

/**
 * CharArrayWriter，内部是char[] buffer，还有一个count，就是写入的char的数量。
 * 默认buffer长度是32，不够再扩展：Arrays.copyOf。
 * <p>
 * 评价：毫无新意的东西，勉强可一用。
 * <p>
 * Created by 张少昆 on 2018/5/3.
 */
public class CharArrayWriterTest {
    @Test
    public void r1(){
        CharArrayWriter writer = new CharArrayWriter();//默认buffer大小是32
//        CharArrayWriter writer = new CharArrayWriter(10);
        writer.reset();//将计数清零
        System.out.println(writer.size());
        System.out.println(Arrays.toString(writer.toCharArray()));

        writer.append("你好啊你妹啊你怎么还在这里？？？？");
        System.out.println(writer.size());
        System.out.println(Arrays.toString(writer.toCharArray()));
    }

    @Test
    public void r2(){
        CharArrayWriter writer = new CharArrayWriter();//默认buffer大小是32
        for(int i = 0; i < 100; i++){
            writer.write(i);
        }

        char[] chars = "赫尔，去你妹的".toCharArray();
        try{
            writer.write(chars);
            System.out.println(writer.size());
            System.out.println(Arrays.toString(writer.toCharArray()));
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    @Test
    public void r3(){
        char[] chars = "赫尔，去你妹的".toCharArray();
        CharArrayReader reader = new CharArrayReader(chars);

        try{
            System.out.println(reader.ready()); //always true
        } catch(IOException e){
            e.printStackTrace();
        }
        System.out.println(reader.markSupported());

        try{
            int read = reader.read();
            System.out.println(read);
            System.out.println((char) read);
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
