package test.ss.demo;

import org.smartboot.socket.transport.AioQuickServer;

import java.io.IOException;

/**
 * Created by zengbin on 2018/2/9.
 */
public class IntegerServer {
    public static void main(String[] args){
        AioQuickServer server = new AioQuickServer()
                                        .bind(8888)
                                        .setProtocol(new IntegerProtocol())
                                        .setProcessor(new IntegerServerProcessor());
        try{
            server.start();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}