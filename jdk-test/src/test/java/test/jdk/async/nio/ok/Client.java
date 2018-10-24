package test.jdk.async.nio.ok;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Iterator;

/**
 * 哇哈哈，终于搞明白了asyncio是怎么回事了！！！
 * 就是先注册事件！
 * 再轮询事件，根据不同的事件执行不同的操作！
 * 执行完操作之后，再注册新的事件！！！--就是告诉对方这个事件的动作我已经搞定了，可以进行下一个动作了！！！！
 * <p>
 * Created by 张少昆 on 2017/12/5.
 */
public class Client {
    @Test
    public void r1() throws IOException{
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("localhost", 8877));
        socketChannel.configureBlocking(false);

        ByteBuffer src = ByteBuffer.allocate(1024);
        ByteBuffer dest = ByteBuffer.allocate(1024);
        src.put("hehehehhehehehe".getBytes());

        Selector selector = Selector.open();
        socketChannel.register(selector, /*SelectionKey.OP_CONNECT | */SelectionKey.OP_READ | SelectionKey.OP_WRITE);
        while(selector.select() >= 0){
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while(iterator.hasNext()){
                SelectionKey next = iterator.next();
               /* if(next.isConnectable()){
                    socketChannel.write(src);
                    src.flip();
                    socketChannel.register(selector, SelectionKey.OP_READ);
                } else*/
                if(next.isReadable()){
                    System.out.println("next.isReadable()");

                    dest.clear();
                    socketChannel.read(dest);
                    dest.flip();
                    System.out.println("got: " + new String(dest.array(), StandardCharsets.UTF_8));
                    socketChannel.register(selector, SelectionKey.OP_WRITE);
                } else if(next.isWritable()){
                    System.out.println("next.isWritable()");

                    src.put(("client@" + LocalDateTime.now()).getBytes(StandardCharsets.UTF_8));
                    src.flip();
                    socketChannel.write(src);
                    src.clear();
                    socketChannel.register(selector, SelectionKey.OP_READ);
                }
                iterator.remove();
            }
        }
        socketChannel.write(src);

    }
}
