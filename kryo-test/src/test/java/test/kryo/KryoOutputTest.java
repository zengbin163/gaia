package test.kryo;

import com.esotericsoftware.kryo.io.Output;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * An OutputStream that buffers data in a byte array and optionally flushes to another OutputStream.
 * <p>
 * 请注意，只有flush了才会输出到OutputStream，否则一直存在于buffer中！
 * <p>
 * Created by 张少昆 on 2018/4/29.
 */
public class KryoOutputTest {

    @Test
    public void basic(){
        Output output = new Output();
//        printOutput(output);

        byte[] bytes = new byte[1024];
        output.setBuffer(bytes);

        printOutput(output);

        output.write(129);//write a byte！
        output.writeAscii("abcdefgHIJKLMN");//看看这个方法！
        output.writeBoolean(true);
        output.writeBoolean(false);
        output.writeByte(128);
        output.write("呵呵".getBytes());//嗯？没有写入长度！
        output.writeBytes("你好".getBytes());//嗯？没有写入长度！
        output.writeChar('你');//2byte
        output.writeChars(new char[]{'你', '好'});
        output.writeDouble(0.999999999999d);//8byte
        //Writes a 1-9 byte double with reduced precision.  true，则对小的正数更有效率(1byte)，对负数低效率(9bytes)
        output.writeDouble(0.999999999d, 0.99999999, true);
        output.writeDoubles(new double[]{0.00001d, 0.000002d, 0.0000000003d});
        output.writeFloat(1.999999f);
        output.writeInt(129);
        output.writeInt(129, true);//???
//        output.writeInts();
//        output.writeVarInt()
        output.writeLong(1234343L);
        output.writeLong(3423523L, true);
//        output.writeVarLong()
//        output.writeLongs();
        output.writeShort(65535);
//        output.writeShorts();
        output.writeString("你好啊中国");

        printOutput(output);
    }

    public void printOutput(Output output){
        System.out.println("getBuffer(): " + output.getBuffer());
        System.out.println("getOutputStream(): " + output.getOutputStream());
        System.out.println("position(): " + output.position());
        System.out.println("toBytes(): " + (output.getBuffer() == null ? null : output.toBytes()));//必须先设置，否则空指针
        System.out.println("total(): " + output.total());
        System.out.println("----------");
    }

    @Test
    public void outputStream(){
        Output output = new Output();
        try{
            output.setOutputStream(new FileOutputStream("test.txt"));
        } catch(FileNotFoundException e){
            e.printStackTrace();
        }
        byte[] bytes = new byte[1024];
        output.setBuffer(bytes);

        output.write(127);
        printOutput(output);

        output.flush();//在flush之前，永远不会写入到OutputStream！
        output.close();//默认会调用flush！
    }
}
