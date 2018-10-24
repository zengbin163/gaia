package test.rpc.client;

import test.rpc.api.Message;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 嗯嗯，一次只针对某个服务。
 * <p>
 * Created by zengbin on 2018/10/20.
 */
public class MessageInvocationHandler implements InvocationHandler {
    private String host;
    private int port;
    private Class<?> interfaceCls;

    //改进版，新增 interfaceCls
    public MessageInvocationHandler(String host, int port, Class<?> interfaceCls){
        this.host = host;
        this.port = port;
        this.interfaceCls = interfaceCls;
    }

    //这种代理不好
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable{

        Message message = new Message();
        message.setInterfaceCls(interfaceCls);
        // message.setMethod(method);
        message.setMethodName(method.getName());
        message.setParameterTypes(method.getParameterTypes());
        message.setMethodArgs(args);

        TransferLayer transferLayer = new TransferLayer(host, port);

        transferLayer.send(message);
        Object recv = transferLayer.recv();
        transferLayer.close();

        return recv;
    }


}
