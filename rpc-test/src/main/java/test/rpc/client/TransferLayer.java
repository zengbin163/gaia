package test.rpc.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * 传输层
 * Created by zengbin on 2018/10/20.
 */
public class TransferLayer {
    private String host;
    private int port;
    private Socket socket = null;

    public TransferLayer(String host, int port){
        this.host = host;
        this.port = port;
        newSocket();
    }

    private Socket newSocket(){

        if(socket != null){
            return socket;
        }
        try{
            socket = new Socket(this.host, this.port);
        } catch(IOException e){
            e.printStackTrace();
        }
        return socket;
    }

    ObjectOutputStream out = null;

    public void send(Object data){
        if(socket == null){
            socket = newSocket();
        }
        try{
            out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(data);
            out.flush();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    ObjectInputStream in = null;

    public Object recv(){
        try{
            in = new ObjectInputStream(socket.getInputStream());

            return in.readObject();
        } catch(IOException e){
            e.printStackTrace();
        } catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }

    public void close(){
        if(in != null){
            try{
                in.close();
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
        if(socket != null){
            try{
                socket.close();
            } catch(IOException e){
                e.printStackTrace();
            }
        }
    }

}
