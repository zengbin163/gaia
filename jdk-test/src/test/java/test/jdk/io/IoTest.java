package test.jdk.io;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by 张少昆 on 2018/1/31.
 */
public class IoTest {
    @Test
    public void r1(){
        int n = 1024;
        String file = "D:/My Space/IDEA/gaia-parent/jdk-test/src/test/java/test/jdk/io/IoTest.java";
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(file));){
            System.out.println(bufferedReader.markSupported());
            bufferedReader.readLine();
            bufferedReader.mark(n); //TODO 这里设置的n，就是限制最多读取的byte数量（从标记处开始）。
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    @Test
    public void r2(){
        String file = "D:/My Space/IDEA/gaia-parent/jdk-test/src/test/java/test/jdk/io/IoTest.java";
        try(RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");){
            randomAccessFile.seek(1024); //TODO 设定文件指针，可以比文件更长，但除非有写入，否则不会修改文件。
            randomAccessFile.setLength(1024); //TODO 牛逼，重新设定文件的长度，短的加长，长的砍短！
            System.out.println(randomAccessFile.getFilePointer());//获取文件指针，帅
//            randomAccessFile.readLine()
        } catch(FileNotFoundException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    @Test
    public void r22(){
        long start = System.currentTimeMillis();
        String file = "D:/Software/CentOS-6.9-x86_64-minimal.iso";
        try(RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");){
            randomAccessFile.seek(1024 * 1024 * 200); //TODO 设定文件指针，可以比文件更长，但除非有写入，否则不会修改文件。
            System.out.println(randomAccessFile.readByte());
            System.out.println(randomAccessFile.getFilePointer());//获取文件指针，帅
//            randomAccessFile.readLine()
        } catch(FileNotFoundException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    @Test
    public void r3(){
    }
}
