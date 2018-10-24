// package test.jdk.async.niosocket;
//
// import java.io.IOException;
// import java.net.InetSocketAddress;
// import java.nio.ByteBuffer;
// import java.nio.channels.SelectionKey;
// import java.nio.channels.Selector;
// import java.nio.channels.SocketChannel;
// import java.util.Iterator;
// import java.util.Set;
//
// /**
//  * Created by zengbin on 2017/10/8.
//  */
// public class NioClient {
//     SocketChannel client;
//     InetSocketAddress serverAddress=new InetSocketAddress("localhost",8888);
//     Selector selector;
//     Selector selector1;
//     ByteBuffer recBuffer=ByteBuffer.allocate(1024);
//     ByteBuffer sendBuffer=ByteBuffer.allocate(1024);
//
//     public NioClient() throws IOException{
//         client=SocketChannel.open();
//         client.configureBlocking(false);
//         client.connect(serverAddress);
//
//         selector=Selector.open();
//
//         client.register(selector, SelectionKey.OP_CONNECT);
//     }
//     public void listen() throws IOException{
//         while(true){
//             int eventCount = selector.select();// select those ready for i/o operation, blocking behavior
//             if(eventCount==0){
//                 continue;
//             }
//             Set<SelectionKey> keys = selector.selectedKeys();
//             Iterator<SelectionKey> iterator = keys.iterator();
//             while(iterator.hasNext()){
//                 process(iterator.next());
//                 iterator.remove();
//             }
//         }
//     }
//
//     private void process(SelectionKey key){
//         String newData = "New String to write to file..." + System.currentTimeMillis();
//         ByteBuffer buf = ByteBuffer.allocate(48);
//         buf.clear();
//         buf.put(newData.getBytes());
//         buf.flip();
//         while(buf.hasRemaining()) {
//             channel.write(buf);
//         }
//
//         // 非阻塞模式
//         socketChannel.configureBlocking(false);
//         socketChannel.connect(new InetSocketAddress("http://jenkov.com", 80));
//         while(! socketChannel.finishConnect() ){
//             //wait, or do something else...
//         }
//     }
//
//     public static void main(String[] args){
//
//     }
// }
