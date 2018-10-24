package test.kryo;

import com.esotericsoftware.kryo.io.Input;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * 关于Input，所有的读取方法，都是从buffer（内置byte[]）中读取的，但是，会检查，可能先从inputstream中填充到buffer中！
 * <p>
 * Created by zengbin on 2018/4/29.
 */
public class KryoInputTest {

    @Test
    public void r1(){
        Input input = new Input();

//        printInput(input); //全是各种类型的零值。基本类型都有长度，而String类型则没有，所以比较特殊。

        //给定的byte[] 可以包含数据，这样就可以直接读取了！
        byte[] bytes = new byte[1024];
        input.setBuffer(bytes);
//        input.setBuffer(bytes,10,1014);

//        input.setInputStream();
//        input.setLimit();
//        input.setPosition();
//        input.setTotal();
        printInput(input);
    }

    public void printInput(Input input){
        try{
            System.out.println("available(): " + input.available());
        } catch(IOException e){
            e.printStackTrace();
        }
        System.out.println("canReadInt(): " + input.canReadInt());
        System.out.println("canReadLong(): " + input.canReadLong());
        System.out.println("eof(): " + input.eof());
        System.out.println("getBuffer(): " + input.getBuffer());
        System.out.println("getInputStream(): " + input.getInputStream());
        System.out.println("limit(): " + input.limit());
        System.out.println("position(): " + input.position());
        System.out.println("read(): " + input.read());
        System.out.println("readBoolean(): " + input.readBoolean());
        System.out.println("readByte(): " + input.readByte());
        System.out.println("readByteUnsigned(): " + input.readByteUnsigned());
        System.out.println("readChar(): " + input.readChar());
        System.out.println("readDouble(): " + input.readDouble());
        System.out.println("readFloat(): " + input.readFloat());
        System.out.println("readLong(): " + input.readLong());
        System.out.println("readShort(): " + input.readShort());
        System.out.println("readShortUnsigned(): " + input.readShortUnsigned());
//        System.out.println("readString(): " + input.readString()); //比较特殊
//        System.out.println("readStringBuilder(): " + input.readStringBuilder());
        System.out.println("total(): " + input.total());//已读取的bytes数量
        System.out.println("markSupported(): " + input.markSupported());
        System.out.println("position(): " + input.position());//


        System.out.println("-----------------");
    }
}
