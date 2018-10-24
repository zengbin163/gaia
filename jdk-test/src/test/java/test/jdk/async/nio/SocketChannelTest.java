package test.jdk.async.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * Created by 张少昆 on 2017/10/10.
 */
public class SocketChannelTest {
    @Test
    public void r1() throws IOException{
        SocketChannel client = SocketChannel.open();
        client.configureBlocking(true);
        client.connect(new InetSocketAddress("localhost", 8888));

        while(!client.finishConnect()){
        }

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put("你好啊，我是客户端，我要访问你！".getBytes(StandardCharsets.UTF_8));
        buffer.flip();
        client.write(buffer); // write to channel
        buffer.clear();
        client.read(buffer); // read from channel

        System.out.println("msg from server:");
        while(buffer.hasRemaining()){
            System.out.println(buffer.getChar());
        }
    }

    @Test
    public void r2(){
        try{
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            // serverSocketChannel.bind(new InetSocketAddress(8888)); // 和下面的什么区别？？？
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(8888)); //

            ByteBuffer buffer = ByteBuffer.allocate(1024);
            SocketChannel client = serverSocketChannel.accept();
            while(!client.finishConnect()){

            }
            client.write(buffer);
            // buffer.flip(); // 写完了，再读，需要flip吗？感觉不需要吧

            System.out.println("msg from client:");
            while(buffer.hasRemaining()){
                System.out.print(buffer.getChar());
            }

            buffer.flip();
            buffer.put("来自server的问候！".getBytes(StandardCharsets.UTF_8));
            buffer.flip();

            client.write(buffer);
        } catch(IOException e){
            e.printStackTrace();
        }


    }
}
