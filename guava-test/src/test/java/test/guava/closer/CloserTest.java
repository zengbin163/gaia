package test.guava.closer;

import com.google.common.io.Closer;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 注意，JDK7的try-with-resource已经可以自动关闭了，所以不需要guava closer了！
 * <p>
 * Created by 张少昆 on 2017/10/15.
 */
public class CloserTest {
    @Test
    public void r1(){
        InputStream in = null;
        try{
            in = new BufferedInputStream(new FileInputStream("D:\\Workspace\\IdeaProjects\\test-parent\\guava-test\\src\\test\\java\\test\\guava\\closer\\CloserTest.java"));
            System.out.println("1");
        } catch(FileNotFoundException e){
            e.printStackTrace();
        } finally{
            try{
                in.close();
            } catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    @Test
    public void closer(){
        Closer closer = Closer.create();

//        try{
//            InputStream in = closer.register(openInputStream());
//            OutputStream out = closer.register(openOutputStream());
//            // do stuff with in and out
//        } catch(Throwable e){ // must catch Throwable
//            throw closer.rethrow(e);
//        } finally{
//            closer.close();
//        }
    }
}
