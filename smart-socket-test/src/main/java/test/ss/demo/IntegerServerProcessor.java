package test.ss.demo;

import org.smartboot.socket.MessageProcessor;
import org.smartboot.socket.StateMachineEnum;
import org.smartboot.socket.transport.AioSession;

import java.io.IOException;

/**
 * 服务器
 * <p>
 * Created by 张少昆 on 2018/2/9.
 */
public class IntegerServerProcessor implements MessageProcessor<Integer> {
    @Override
    public void process(AioSession<Integer> session, Integer msg){
        Integer respMsg = msg + 1;
        System.out.println("接受到客户端数据：" + msg + " ,响应数据:" + (respMsg));
        try{
            session.write(respMsg);
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void stateEvent(AioSession<Integer> session, StateMachineEnum stateMachineEnum, Throwable throwable){

    }
}
