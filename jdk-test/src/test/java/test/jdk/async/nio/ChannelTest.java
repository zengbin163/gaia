package test.jdk.async.nio;

import org.junit.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by 张少昆 on 2017/10/8.
 */
public class ChannelTest {

    /**
     * 这个ByteBuffer，其实跟之前的byte[] 很类似！
     * channel和inputstream 类似，但channel是双向的，既可以读，也可以写！
     * <p>
     * 使用Buffer就四步：
     * ①将数据写入Buffer
     * ②调用buffer.flip()
     * ③从Buffer中读数据
     * ④调用buffer.clear()或者buffer.compact()
     * clear()方法会清空整个缓冲区。
     * compact()方法只会清除已经读过的数据,任何未读的数据都被移到缓冲区的起始处，新写入的数据将放到缓冲区未读数据的后面。
     *
     * @throws IOException
     */
    @Test
    public void fileChannelTest() throws IOException{
        RandomAccessFile aFile = new RandomAccessFile("d:/data/nio-data.txt", "rw");
        FileChannel inChannel = aFile.getChannel();

        ByteBuffer buf = ByteBuffer.allocate(48);

        int bytesRead = inChannel.read(buf); // 数据是从 channel 读到 buffer!
        int count = 0;
        while(bytesRead != -1){

            System.out.println("channel向read写入了 " + bytesRead + " bytes!内容如下：");
            buf.flip(); // 复位，然后从头开始读（否则是从写完的位置开始读，然后什么都读不到）

            while(buf.hasRemaining()){
                System.out.print((char) buf.get()); // 打印出buffer中的所有字符 -- 奇怪，一个byte就是一个char？这只能是ascii编码吧，要不加个中文字符看看？果然！
            }
            System.out.println();//人为添加，为了好看，但会导致输出的内容不连续

            buf.clear();//只是设置了position和limit，标识可以重新写入了，但实际上内容还在，不信可以放开下面的注释
            /* // 如果放开，还会输出一遍！
            while(buf.hasRemaining()){
                System.out.print((char) buf.get()); // 打印出buffer中的所有字符 -- 奇怪，一个byte就是一个char？这只能是ascii编码吧，要不加个中文字符看看？果然！
            }
            buf.clear();//清了吧，要不死循环
            */


            // buf.rewind();//倒带


            bytesRead = inChannel.read(buf); // 因为buffer可能一次无法容纳channel中的内容，所以需要重复读，当然需要先clear一下

            System.out.println("---------count: " + (++count));//人为添加，为了好看，但会导致输出的内容不连续
        }
        aFile.close();
    }


    /**
     * 在Java NIO中，如果两个通道中有一个是FileChannel，那你可以直接将数据从一个channel传输到另外一个channel。
     *
     * transferFrom
     * 方法的输入参数position表示从position处开始向目标文件写入数据，count表示最多传输的字节数。如果源通道的剩余空间小于 count 个字节，则所传输的字节数要小于请求的字节数。
     * 此外要注意，在SoketChannel的实现中，SocketChannel只会传输此刻准备好的数据（可能不足count字节）。因此，SocketChannel可能不会将请求的所有数据(count个字节)全部传输到FileChannel中。
     *
     * transferTo
     * SocketChannel会一直传输数据直到目标buffer被填满。
     *
     * @throws IOException
     */
    @Test
    public void testFileChannel() throws IOException{ //
        RandomAccessFile aFile = new RandomAccessFile("d:/data/nio-data.txt", "rw");
        FileChannel fromChannel = aFile.getChannel();

        RandomAccessFile bFile = new RandomAccessFile("d:/data/nio-data-out.txt", "rw");
        FileChannel toChannel = bFile.getChannel();

        // 下面两个一样的功能，感觉就是省略了Buffer，直接追加过去！
        // long byteTransfered = toChannel.transferFrom(fromChannel, 0, fromChannel.size());
        long byteTransfered = fromChannel.transferTo(0, fromChannel.size(), toChannel);
        System.out.println(byteTransfered);
    }
}
