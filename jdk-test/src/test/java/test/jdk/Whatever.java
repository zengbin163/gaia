package test.jdk;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by zengbin on 2017/12/19.
 */
public class Whatever {
    //帧头 2字节 0x7d 0x7b
    //命令码 1字节 0x02
    //命令扩展码 1字节
    //数据区 22+4*N字节
    //22 = 19字节时间 + 1字节Flag(00/01） + 1字节预留 + 1字节个数N + 4字节*N float。
    char[] packet_bytes = { //len 41 = 4+ 22+4*N +4
            0x7d, 0x7b, 0x02, 0x66, 0x32, 0x30, 0x31, 0x37,
            0x2d, 0x31, 0x32, 0x2d, 0x31, 0x39, 0x20, 0x31,
            0x34, 0x3a, 0x35, 0x34, 0x3a, 0x35, 0x32, 0x01,
            0x03, 0x41, 0x17, 0x7c, 0x95, 0x41, 0x9b, 0x78,
            0x0b, 0x41, 0xe7, 0x36, 0x56, 0x6e, 0x9e, 0x7d,
            0x7d
    };

    byte[] arr1 = {
            0x32, 0x30, 0x31, 0x37,
            0x2d, 0x31, 0x32, 0x2d, 0x31, 0x39, 0x20, 0x31,
            0x34, 0x3a, 0x35, 0x34, 0x3a, 0x35, 0x32
    };
    byte[] arr2 = {
            0x01
    };
    byte[] arr3 = {
            0x03
    };
    byte[] arr4 = {
            0x41
    };
    byte[] arr5 = {
           // 0x17, 0x7c, 0x95, 0x41 //超过byte的范围了，应该是无符号byte
    };


    @Test
    public void r1() throws IOException{
        System.out.println(packet_bytes.length);
        for(int i = 4; i < packet_bytes.length - 4; i++){
            System.out.print(packet_bytes[i]);
        }
        System.out.println("\r\n---------");

        System.out.println(String.valueOf(packet_bytes, 4, packet_bytes.length - 8));
        System.out.println("Time: " + String.valueOf(packet_bytes, 4, 19));
        if(packet_bytes[20] == 0x00){
            System.out.println("Flag: 正常");
        } else if(packet_bytes[20] == 0x01){
            System.out.println("Flag: 标定");
        }
        System.out.println("Nums: " + packet_bytes[22]); //数量
        System.out.println("Pres: " + String.valueOf(packet_bytes, 21, 1));
        System.out.println("Num1: " + String.valueOf(packet_bytes, 23, 4));


        System.out.println(new String(arr1));
        System.out.println(new String(arr2));
        System.out.println(new String(arr3));
        System.out.println(new String(arr4));

        Float v = 3.33f;
        byte[] x={0x17, 0x7c, (byte)0x95, 0x41};
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(x));
        float x1 = dataInputStream.readFloat();
        System.out.println(x1);
    }
}
