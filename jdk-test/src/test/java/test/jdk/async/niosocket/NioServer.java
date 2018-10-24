package test.jdk.async.niosocket;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by 张少昆 on 2017/10/7.
 */
public class NioServer {
    private int port;
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private ByteBuffer recBuffer = ByteBuffer.allocate(1024);
    private ByteBuffer sendBuffer = ByteBuffer.allocate(1024);
    int clientId = 10010;
    Map<SelectionKey, String> sessionMsg = new HashMap<>();
    Map<SelectionKey, Integer> clientIdMsg = new HashMap<>();

    public NioServer(int port) throws IOException{
        this.port = port;

        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        serverSocketChannel.configureBlocking(false);//non-blocking

        selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("Server socket is ready on port[" + port + "]!");
    }

    private void listen() throws IOException{
        while(true){
            int eventCount = selector.select();
            if(eventCount == 0){
                continue;
            }
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while(iterator.hasNext()){
                process(iterator.next());
                iterator.remove();
            }
        }
    }

    private void process(SelectionKey key){
        SocketChannel client = null;
        try{
            if(key.isValid() && key.isAcceptable()){
                client = serverSocketChannel.accept();
                ++clientId; //此处代表新的客户端！
                client.configureBlocking(false);
                client.register(selector, SelectionKey.OP_READ);
            } else if(key.isValid() && key.isReadable()){
                recBuffer.clear();
                client = (SocketChannel) key.channel();

                int len = client.read(recBuffer);
                if(len > 0){
                    String msg = new String(recBuffer.array(), 0, len);
                    sessionMsg.put(key, msg);
                    System.out.println("got this: " + msg); // thread, time
                    clientIdMsg.put(key, clientId);
                    client.register(selector, SelectionKey.OP_WRITE);
                }
            } else if(key.isValid() && key.isWritable()){
                if(!sessionMsg.containsKey(key)){
                    return;
                }
                client = (SocketChannel) key.channel();
                sendBuffer.clear();
                sendBuffer.put((sessionMsg.get(key) + "-------").getBytes());
                sendBuffer.flip();
                client.write(sendBuffer);
                System.out.println("sent this..");
                client.register(selector, SelectionKey.OP_READ);
            }
        } catch(IOException e){
            key.cancel();
            try{
                client.socket().close();
                client.close();
                System.out.println("client[" + clientIdMsg.get(key) + "] 已经下线！");
                clientIdMsg.remove(key);
            } catch(IOException e1){
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }
}
