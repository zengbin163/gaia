package commons.test.io;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

/**
 * Created by 张少昆 on 2017/12/18.
 */
public class IoTest {
    @Test
    public void r1(){
        InputStream in = System.in;
        PrintStream out = System.out;

        try{
            IOUtils.copy(in,out); //TODO IOUtils.copy(in, out)是关键。
        } catch(IOException e){
            e.printStackTrace();
        }finally{
            IOUtils.closeQuietly(); //官方建议使用try-with-resources代替
        }
    }
}
