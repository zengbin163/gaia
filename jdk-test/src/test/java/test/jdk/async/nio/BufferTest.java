package test.jdk.async.nio;

import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 缓冲区本质上是一块可以写入数据，然后可以从中读取数据的内存。这块内存被包装成NIO Buffer对象，并提供了一组方法，用来方便的访问该块内存。
 * Java NIO中的Buffer用于和NIO通道进行交互。如你所知，数据是从通道读入缓冲区，从缓冲区写入到通道中的。
 * <p>
 * 这个ByteBuffer，其实跟之前的byte[] 很类似！
 * channel和inputstream 类似，但channel是双向的，既可以读，也可以写！
 * <p>
 * 使用Buffer就四步：
 * ①将数据写入Buffer
 * ②调用buffer.flip()
 * ③从Buffer中读数据
 * ④调用buffer.clear()或者buffer.compact()
 *
 * clear()方法会清空整个缓冲区。
 * compact()方法只会清除已经读过的数据,任何未读的数据都被移到缓冲区的起始处，新写入的数据将放到缓冲区未读数据的后面。
 *
 * <p>
 * Created by 张少昆 on 2017/10/9.
 */
public class BufferTest {


    //--------
    @Test
    public void map(){
        Map map = new HashMap();
        map.put((String) null, "a");
        map.put((Date) null, "a");
        System.out.println(map.size());
    }
}
