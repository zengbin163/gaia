package test.jdk.math;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by zengbin on 2017/12/19.
 */
public class ByteArrayToFloat {

    //byte[] => float
    @Test
    public void r1() throws IOException{
        byte[] x = {0x17, 0x7c, (byte) 0x95, 0x41};
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(x));
        float x1 = dataInputStream.readFloat();
        System.out.println(x1);
    }

    //byte[] => float
    @Test
    public void r2() throws IOException{
        byte[] x = {0x17, 0x7c, (byte) 0x95, 0x41};

        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        byteBuffer.put(x);
        byteBuffer.flip();

        System.out.println(byteBuffer.getFloat());
    }

    //反过来
    @Test
    public void r3() throws IOException{
        float x = 1.234436346f;
        ByteBuffer buff = ByteBuffer.allocate(4);
        buff.putFloat(x);
        buff.flip();

        byte[] array = buff.array();
        System.out.println(Arrays.toString(array)); //[63, -98, 2, 3]
    }

    //反过来
    @Test
    public void r4(){
        float x = 1.234436346f;
        int bits = Float.floatToIntBits(x);
        //FIXME 这种方式是错误的，因为结果的长度固定，都是32位
//        byte i1 = (byte) (bits & 0xff);
//        byte i2 = (byte) (bits & 0xff00);
//        byte i3 = (byte) (bits & 0xff0000);
//        byte i4 = (byte) (bits & 0xff000000);
        //应该使用位移
        byte i1= (byte) (bits);
        byte i2= (byte) (bits>>8);
        byte i3= (byte) (bits>>16);
        byte i4= (byte) (bits>>24);

        System.out.println(i1);//3
        System.out.println(i2);//2
        System.out.println(i3);//-98
        System.out.println(i4);//63
    }
}
