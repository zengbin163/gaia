package test.jdk.async.nio.ag;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.Set;

/**
 * 哇哈哈，终于搞明白了asyncio是怎么回事了！！！
 * 就是先注册事件！
 * 再轮询事件，根据不同的事件执行不同的操作！
 * 只玩执行完操作之后，再注册新的事件！！！--就是告诉对方这个事件的动作我已经搞定了，可以进行下一个动作了！！！！
 * <p>
 * Created by zengbin on 2017/10/10.
 */
public class Server {
    public static void main(String[] args) throws IOException{
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(8111)); //why

        Selector selector = Selector.open();
        SelectionKey key0 = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT); // 只是给channel设置一个状态！告诉对方可以请求了

        System.out.println("server is ready to accept...");

        ByteBuffer recvBuffer = ByteBuffer.allocate(1024);
        ByteBuffer sendBuffer = ByteBuffer.allocate(1024);

        while(true){
            int readyChannels = selector.select();
            if(readyChannels == 0){
                continue;
            }

            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
            while(keyIterator.hasNext()){
                SelectionKey key = keyIterator.next();

                SocketChannel socketChannel = null;
                if(key.isAcceptable()){
                    socketChannel = serverSocketChannel.accept(); // NOTE!!!
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ); // 重新注册selector，以及感兴趣的操作集合
                } else if(key.isConnectable()){
                    System.out.println("用不到啦 - 为啥？");
                } else if(key.isReadable()){
                    socketChannel = (SocketChannel) key.channel();
                    socketChannel.configureBlocking(false);

                    // 既然是可读的，那就先读出来，再设置
                    while(socketChannel.read(recvBuffer) != -1){
                        // socketChannel.read(recvBuffer);
                    }
                    recvBuffer.flip();
                    System.out.println("server接收到的内容是：");
                    while(recvBuffer.hasRemaining()){
                        System.out.print((char) recvBuffer.get()); // 只能是ascii字符
                    }
                    System.out.println();
                    recvBuffer.clear();

                    socketChannel.register(selector, SelectionKey.OP_WRITE);
                } else if(key.isWritable()){
                    socketChannel = (SocketChannel) key.channel();
                    socketChannel.configureBlocking(false);

                    sendBuffer.put(("hi there - msg from server, " + LocalTime.now()).getBytes(StandardCharsets.UTF_8));
                    sendBuffer.flip();

                    while(socketChannel.write(sendBuffer) != -1){

                    }
                    sendBuffer.clear();
                    socketChannel.register(selector, SelectionKey.OP_READ);
                }
                keyIterator.remove();
            }
        }
    }
}
