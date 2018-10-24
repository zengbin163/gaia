package test.jdk.socket;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 使用bio实现长连接的轮询处理。
 * <p>
 * TODO 经验：不能使用try-with-resources，否则会自动关闭链接，很麻烦。
 * <p>
 * Created by 张少昆 on 2018/5/20.
 */
public class BioPollTest {

    @Test
    public void server(){
        //初始化大小，默认是你要建立的链接数
        final ConcurrentHashMap<Socket, Object> map = new ConcurrentHashMap<>(1000000);
        //线程池，处理io等
        final ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*2);
        //单线程池，负责链接
        final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        //任务：使用线程池读取map中的socket的数据
        final Runnable runnable = () -> map.keySet().forEach(e -> threadPool.submit(() -> readSocket(e)));
        //定时任务
        scheduledExecutorService.scheduleAtFixedRate(runnable, 1, 2000, TimeUnit.MILLISECONDS);

        //服务端准备
        try(ServerSocket serverSocket = new ServerSocket()){
            //服务端绑定
            serverSocket.bind(new InetSocketAddress("localhost", 8888));
            System.out.println("server ready to accept...");
            //无限循环之创建新链接 - 如果不需要，可以按照你的数量来搞定，或者放到一个线程里执行
            while(true){
                try{
                    Socket socket = serverSocket.accept();
                    map.put(socket, 1);//将链接放到map中
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    final int len = 1024;//改成你的

    /**
     * 读取一条消息。
     * 长度和格式固定，所以不考虑更多。
     *
     * @param socket 某个链接
     */
    private void readSocket(Socket socket){
        byte[] bs = new byte[len];
        try{
            InputStream inputStream = socket.getInputStream();
            int available = inputStream.available();
            int count = (available + len - 1) / len;
            for(int i = 0; i < count; i++){
                int read = inputStream.read(bs);
                dealWithMsg(bs, 0, read); //
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    //TODO 你的处理方法 - 针对一条消息！注意read可能与bs.length不等。
    private void dealWithMsg(byte[] bs, int i, int read){
        System.out.println(new String(bs, i, read, StandardCharsets.UTF_8));
    }


    @Test
    public void client(){
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        try{
            Socket socket = new Socket("localhost", 8888);

            Runnable runnable = () -> {
                try{ //TODO check
                    OutputStream outputStream = socket.getOutputStream();
                    outputStream.write(("你好啊中国" + LocalDateTime.now()).getBytes(StandardCharsets.UTF_8));
                } catch(IOException e){
                    e.printStackTrace();
                }
            };
            scheduledExecutorService.scheduleAtFixedRate(runnable, 0, 2000, TimeUnit.MILLISECONDS);

            scheduledExecutorService.awaitTermination(300, TimeUnit.SECONDS);
        } catch(UnknownHostException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        } catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    @Test
    public void client2(){
        client();
    }

    @Test
    public void client3(){
        client();
    }

}
