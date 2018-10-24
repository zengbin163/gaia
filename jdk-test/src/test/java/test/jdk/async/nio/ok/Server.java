package test.jdk.async.nio.ok;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
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
 * 根据 test.jdk.async.nio.demo.ServerSocketChannelTest 可知：
 * ServerSocketChannel也可以注册selector及事件，但只能处理ACCEPT事件（客户端请求时发起的吧？貌似不是）。
 * 一旦有ACCEPT事件，就可以准备accept()以创建链接了。
 * 如果创建了链接，就返回SocketChannel对象，然后就是SocketChannel之间的操作啦！！！
 *
 * Created by 张少昆 on 2017/12/5.
 */
public class Server {
    @Test
    public void r1() throws IOException{
        //创建服务器端channel对象
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress local = new InetSocketAddress("localhost", 8877);
        //绑定到本机8877端口
        serverSocketChannel.bind(local);
        //是否阻塞式
        serverSocketChannel.configureBlocking(true);
        //如果阻塞式，accept就会一直等待创建链接
        SocketChannel socketChannel = serverSocketChannel.accept();
        //拿到了channel，就可以为所欲为了。不过我们先创建一个selector再说
        Selector selector = Selector.open();
        //再设为非阻塞式
        socketChannel.configureBlocking(false);
        //再将channel注册到selector上，不过这会应该只关心CONNECT
        //selectionKey代表了channel与selector的注册信息
        SelectionKey selectionKey = socketChannel.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_READ | SelectionKey.OP_WRITE);//TODO 不能注册ACCEPT，只有server才有
        // serverSocketChannel.register(selector,)
        //接下来，就得判断了
        while(true){
            int select = selector.select(); //blocking!!!
            System.out.println("select: " + select);
            if(select >= 0){
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while(iterator.hasNext()){
                    ByteBuffer dest = ByteBuffer.allocate(1024);
                    ByteBuffer src = ByteBuffer.allocate(1024);

                    SelectionKey next = iterator.next();
                    SocketChannel channel = (SocketChannel) next.channel();
                    // if(next.isAcceptable()){
                    //     System.out.println("next.isAcceptable()");
                    //     channel.configureBlocking(false);
                    //     channel.register(selector, SelectionKey.OP_CONNECT);
                    // }
                    if(next.isConnectable()){
                        System.out.println("next.isConnectable()");
                        channel.configureBlocking(false);
                        channel.register(selector, SelectionKey.OP_READ);
                    } else if(next.isReadable()){
                        System.out.println("next.isReadable()");
                        channel.configureBlocking(false);
                        channel.read(dest);
                        System.out.println("got: " + new String(dest.array(), StandardCharsets.UTF_8));
                        channel.register(selector, SelectionKey.OP_WRITE);
                    } else if(next.isWritable()){
                        System.out.println("next.isWritable()");
                        channel.configureBlocking(false);
                        src.put(("server@" + LocalDateTime.now()).getBytes(StandardCharsets.UTF_8));
                        src.flip();
                        channel.write(src);
                        src.flip();
                        channel.register(selector, SelectionKey.OP_READ);
                    }
                    iterator.remove();
                }
            }
        }
    }
}
