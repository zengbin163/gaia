package test.rpc.server;


import test.rpc.api.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by 张少昆 on 2018/10/20.
 */
public class Publisher {

    /**
     * 发布服务。
     * 考虑一下，所谓的发布服务，就是启动并监听socket，获取输入数据，并返回响应。
     *
     * @param service
     * @param host
     * @param port
     * @return
     */
    public boolean publish(Object service, String host, int port){
        ServerSocket serverSocket = null;
        try{
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(host, port));
        } catch(IOException e){
            e.printStackTrace();
        }

        while(true){
            Socket socket = null;
            ObjectInputStream in = null;
            ObjectOutputStream out = null;
            try{
                socket = serverSocket.accept();
                in = new ObjectInputStream(socket.getInputStream());
                Message msg = (Message) in.readObject();//输入的内容，约定为Message                  FIXME 卡在这里了
                System.out.println("received msg: " + msg);
                Class<?> interfaceCls = msg.getInterfaceCls();
                String methodName = msg.getMethodName();
                Class[] parameterTypes = msg.getParameterTypes();
                Object[] methodArgs = msg.getMethodArgs();
                Method method = interfaceCls.getMethod(methodName, parameterTypes);
                Object res = method.invoke(service, methodArgs);
                System.out.println("pub res: " + res);//看看调用成功了没
                out = new ObjectOutputStream((socket.getOutputStream()));
                out.writeObject(res);
                out.flush();


            } catch(IOException e){
                e.printStackTrace();
            } catch(ClassNotFoundException e){
                e.printStackTrace();
            } catch(IllegalAccessException e){
                e.printStackTrace();
            } catch(InvocationTargetException e){
                e.printStackTrace();
            } catch(NoSuchMethodException e){
                e.printStackTrace();
            } finally{
                if(socket != null){
                    try{
                        socket.close();
                    } catch(IOException e){
                        e.printStackTrace();
                    }
                }
                if(out != null){
                    try{
                        out.close();
                    } catch(IOException e){
                        e.printStackTrace();
                    }
                }
                if(in != null){
                    try{
                        in.close();
                    } catch(IOException e){
                        e.printStackTrace();
                    }
                }
            }

        }
    }
}
