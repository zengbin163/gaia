package test.jdk.async.nio;

import org.junit.Test;

import java.io.IOException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by 张少昆 on 2017/10/10.
 */
public class SelectorTest {
    @Test
    public void r1() throws IOException{
        // 1) 创建一个Selector
        Selector selector = Selector.open();

        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(false); //与Selector一起使用时，Channel必须处于非阻塞模式下

        // 2) 为了将Channel和Selector配合使用，必须将channel注册到selector上。
        // 这里，第二个参数的意思是selector感兴趣的操作集合，二进制形式的集合！
        // SelectionKey key = channel.register(selector, SelectionKey.OP_READ);
        SelectionKey key = channel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE); // 还可以这样玩，因为是个集合，用二进制形式表示的！

        // SelectionKey对象包含了相关的信息！selector、channel、兴趣集合(intresting set)、附加对象等，非常有用！
        System.out.println("key.isValid() : " + key.isValid());
        System.out.println("key.attachment() : " + key.attachment());
        System.out.println("key.isReadable() : " + key.isReadable());
        System.out.println("key.isWritable() : " + key.isWritable());
        System.out.println("key.interestOps() : " + key.interestOps()); // 感兴趣的集合。一般不如上面两个方便
        System.out.println("key.channel() == channel : " + (key.channel() == channel)); //SelectionKey.channel()方法返回的通道需要转型成你要处理的类型，如ServerSocketChannel或SocketChannel等。
        System.out.println("key.selector() == selector : " + (key.selector() == selector));

        System.out.println("key.readyOps() : " + key.readyOps()); // channel已经准备就绪的操作的集合

        // 上面的key很有用，但不常用，都是使用selector
        // int no = selector.select();//阻塞到至少有一个通道在你注册的事件上就绪了。表示有多少通道已经就绪。--在此之前的不算？？
        // no = selector.select(1000 * 10);
        // no = selector.selectNow();//不会阻塞，不管什么通道就绪都立刻返回。或者返回0！
        // 如果select()阻塞了，可以在其他线程上调用selector.wakeUp()，立刻返回！

        // 一旦调用了select()方法，并且返回值表明有一个或更多个通道就绪了，然后可以通过调用selector的selectedKeys()方法，访问“已选择键集（selected key set）”中的就绪通道。
        Set<SelectionKey> selectionKeys = selector.selectedKeys();// 当channel中selector注册了之后，就有了一个SelectionKey！
        System.out.println(selectionKeys.contains(key)); // 因为之前的不算！

        Iterator<SelectionKey> iterator = selectionKeys.iterator();
        while(iterator.hasNext()){
            process(iterator.next());
            iterator.remove();
        }

        //用完Selector后调用其close()方法会关闭该Selector，且使注册到该Selector上的所有SelectionKey实例无效。通道本身并不会关闭。

    }

    private void process(SelectionKey key){
        if(key.isAcceptable()){

        } else if(key.isConnectable()){

        } else if(key.isReadable()){

        } else if(key.isWritable()){

        }
    }


    @Test
    public void demo(SelectableChannel channel) throws IOException{
        Selector selector = Selector.open();
        channel.configureBlocking(false);
        SelectionKey key0 = channel.register(selector, SelectionKey.OP_READ);

        while(true){
            int readyChannels = selector.select();
            if(readyChannels == 0){
                continue;
            }

            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
            while(keyIterator.hasNext()){
                SelectionKey key = keyIterator.next();
                if(key.isAcceptable()){
                    // a connection was accepted by a ServerSocketChannel.
                } else if(key.isConnectable()){
                    // a connection was established with a remote server.
                } else if(key.isReadable()){
                    // a channel is ready for reading
                } else if(key.isWritable()){
                    // a channel is ready for writing
                }
                keyIterator.remove();
            }
        }
    }

}
