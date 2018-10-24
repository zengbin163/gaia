package test.jdk.io;

import org.junit.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * FileChannel是abstract！
 * <p>
 * Created by zengbin on 2018/5/13.
 */
public class FileChannelTest {
    @Test
    public void r1(){
        try(FileChannel channel = FileChannel.open(Paths.get("d:/test111.txt"));){
            // channel = FileChannel.open(Paths.get("d:/test111.txt"));
            // fc.lock();//针对最大区域加锁！shared表示是否。--不是用于多线程，而是用于多进程！以JVM为单位！
            // fc.lock(0, size, shared); //可以针对部分区域加锁！

            System.out.println("channel.size(): " + channel.size());
            System.out.println("channel.position(): " + channel.position());
            System.out.println("channel.isOpen(): " + channel.isOpen());

            // channel.map();//TODO
            // channel.transferFrom();//TODO
            // channel.transferTo();//TODO
            // channel.truncate(size);
            // channel.position(newPosition);
            channel.force(true);

            ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);
            int len = channel.read(buffer);
            System.out.println("读取了 " + len + " 个byte！");

            buffer.flip();
            // while(buffer.hasRemaining()){
            //     System.out.print(buffer.getChar()); //FIXME 这样，只能一次读2 byte！不适合UTF8编码
            // }
            //一次全转了，就不需要循环了
            System.out.println(new String(buffer.array(), "utf-8"));
            System.out.println();

            System.out.println(buffer.isDirect());
            // buffer.clear();
            // buffer.rewind();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    @Test
    public void map(){
        try(FileChannel channel = FileChannel.open(Paths.get("d:/test111.txt"));){
            // channel = FileChannel.open(Paths.get("d:/test111.txt"));
            // fc.lock();//针对最大区域加锁！shared表示是否。--不是用于多线程，而是用于多进程！以JVM为单位！
            // fc.lock(0, size, shared); //可以针对部分区域加锁！


            //将文件的一部分映射到内存中！一般适用于相对较大的文件！该对象的其他方法就是ByteBuffer的方法！
            //映射模式：READ_ONLY/READ_WRITE/PRIVATE。只读、可修改（原文件）、只修改映射！
            MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_ONLY, 0, 1024);//TODO Maps a region of this channel's file directly into memory.
            map.force();
            System.out.println("map.isLoaded(): " + map.isLoaded()); //看样子需要先手动加载
            MappedByteBuffer load = map.load(); //手动加载
            System.out.println(map == load); //true
            System.out.println("map.isLoaded(): " + map.isLoaded()); //看样子需要先手动加载


            // channel.transferFrom();//TODO
            // channel.transferTo();//TODO
            // channel.truncate(size);
            // channel.position(newPosition);

            // buffer.clear();
            // buffer.rewind();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    @Test
    public void transferFromTo(){//TODO 注意，这俩方法非常高效，不经过用户空间！直接由os搞定。
        try(FileChannel channel = FileChannel.open(Paths.get("d:/test111.txt"), StandardOpenOption.READ);
            FileChannel from = FileChannel.open(Paths.get("d:/from.txt"), StandardOpenOption.READ, StandardOpenOption.WRITE);
            FileChannel to = FileChannel.open(Paths.get("d:/to.txt"), StandardOpenOption.APPEND);){
            // channel = FileChannel.open(Paths.get("d:/test111.txt"));
            // fc.lock();//针对最大区域加锁！shared表示是否。--不是用于多线程，而是用于多进程！以JVM为单位！
            // fc.lock(0, size, shared); //可以针对部分区域加锁！

            //先将channel的内容写到from中，再将from的内容写到to中
            long len1 = from.transferFrom(channel, 0, 10);
            long len2 = from.transferTo(0, 10, to);

            System.out.println(len1);
            System.out.println(len2);

            // channel.truncate(size);
            // channel.position(newPosition);

            // buffer.clear();
            // buffer.rewind();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
