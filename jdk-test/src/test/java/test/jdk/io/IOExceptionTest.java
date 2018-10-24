package test.jdk.io;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by 张少昆 on 2018/5/2.
 */
public class IOExceptionTest {

    @Test
    public void r1(){
        try(FileInputStream fis = new FileInputStream(new File("pom.xml"))){
            fis.close(); //提前关闭流，再读写，就会导致异常
            System.out.println(fis.read());
        } catch(FileNotFoundException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
