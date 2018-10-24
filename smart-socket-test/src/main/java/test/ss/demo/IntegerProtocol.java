package test.ss.demo;

import org.smartboot.socket.Protocol;
import org.smartboot.socket.transport.AioSession;

import java.nio.ByteBuffer;

/**
 * 编写协议，很好很强大
 * <p>
 * Created by zengbin on 2018/2/9.
 */
public class IntegerProtocol implements Protocol<Integer> {

    private static final int INT_LENGTH = 4;

    @Override
    public Integer decode(ByteBuffer data, AioSession<Integer> session, boolean eof){
        if(data.remaining() < INT_LENGTH){
            return null;
        }
        return data.getInt();
    }

    @Override
    public ByteBuffer encode(Integer s, AioSession<Integer> session){
        ByteBuffer b = ByteBuffer.allocate(INT_LENGTH);
        b.putInt(s);
        b.flip();
        return b;
    }
}
