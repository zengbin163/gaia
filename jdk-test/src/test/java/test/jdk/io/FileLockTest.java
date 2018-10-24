package test.jdk.io;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 这是个很神奇的东西，不是以线程为单位，而是以JVM为单位！
 * <p>
 * TODO 就是说，不同的进程之间使用！同一个进程内，只能lock一次！注意FileChannel的打开模式。
 * TODO
 * <p>
 * 本例的用法：编译后，在命令行中同时执行两次（同时write或者同时read，混合的暂时没厘清）。
 * <p>
 * Created by zengbin on 2018/5/13.
 */
public class FileLockTest {

    public static void main(String[] args) throws Exception{
        write(args);

        // read(args);
    }

    public static void write(String[] args) throws Exception{
        if(args == null || args.length < 2){
            System.err.println("ERROR! 需要两个参数：线程名、输出内容！");
            return;
        }
        String str = args == null ? null : args[0];

        // TODO
        FileChannel channel = FileChannel.open(Paths.get("d:/test.txt"), StandardOpenOption.APPEND);
        ByteBuffer b1 = ByteBuffer.wrap(args[1].getBytes(StandardCharsets.UTF_8));

        System.out.println("Write[" + str + "]准备获取lock");
        FileLock lock = channel.lock();//TODO 排它锁！
        System.out.println("Write[" + str + "]获取了lock");
        channel.write(b1);
        Thread.sleep(1000L * 10);

        System.out.println("Write[" + str + "]准备释放lock");
        lock.release();
        channel.close();
        System.out.println("Write[" + str + "]释放了lock");

    }

    public static void read(String[] args) throws Exception{
        if(args == null || args.length < 1){
            System.err.println("ERROR! 需要参数：线程名！");
            return;
        }
        String str = args == null ? null : args[0];

        // TODO
        FileChannel channel = FileChannel.open(Paths.get("d:/test.txt"), StandardOpenOption.READ, StandardOpenOption.WRITE);
        ByteBuffer buf = ByteBuffer.allocate(1024);

        System.out.println("Read[" + str + "]准备获取lock");
        FileLock lock = channel.lock(0, 1024, true); // TODO shared lock！
        System.out.println("Read[" + str + "]获取了lock");

        // channel.write(ByteBuffer.wrap("share write".getBytes())); //FIXME ERROR? 另一个程序已锁定文件的一部分，进程无法访问

        channel.read(buf);
        buf.flip();
        while(buf.hasRemaining()){
            System.out.print(buf.getChar());//当剩余的部分不足以组成Char时，会报错 BufferUnderflowException
        }
        System.out.println();
        Thread.sleep(1000L * 10);

        System.out.println("Read[" + str + "]准备释放lock");
        lock.release();
        channel.close();
        System.out.println("Read[" + str + "]释放了lock");

    }
}
