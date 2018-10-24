package commons.test.compress;

import org.apache.commons.compress.archivers.zip.Zip64Mode;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;

/**
 * Created by 张少昆 on 2017/12/13.
 */
public class CompressTest {

    @Test
    public void compressOneFile() throws IOException{

        //创建压缩后的对象
        ZipArchiveEntry entry = new ZipArchiveEntry("dubbo-user-book.pdf");//压缩包中的文件名，一项一项的

        //要压缩的文件
        File f = new File("e:/dubbo-user-book.pdf");
        FileInputStream fis = new FileInputStream(f);
        BufferedInputStream bis = new BufferedInputStream(fis);

        //输出的对象 压缩的文件
        ZipArchiveOutputStream zipOutput = new ZipArchiveOutputStream(new File("e:/dubbo-user-book.pdf.zip"));
        zipOutput.setUseZip64(Zip64Mode.Always);
        zipOutput.setMethod(ZipEntry.DEFLATED);
        zipOutput.setLevel(Deflater.BEST_COMPRESSION);//JDK的！

        zipOutput.putArchiveEntry(entry);//见javadoc

        byte[] bs = new byte[1024 * 1024];
        int len = -1;
        while((len = bis.read(bs)) != -1){
            zipOutput.write(bs, 0, len);
            System.out.println(len);
        }

        zipOutput.closeArchiveEntry();
        zipOutput.close();
        fis.close();
    }
    @Test
    public void compressMultiFiles() throws IOException{

        //创建压缩后的对象
        ZipArchiveEntry entry = new ZipArchiveEntry("dubbo-user-book.pdf");//压缩包中的文件名，一项一项的

        //要压缩的文件
        File f = new File("e:/dubbo-user-book.pdf");
        FileInputStream fis = new FileInputStream(f);
        BufferedInputStream bis = new BufferedInputStream(fis);

        //创建压缩后的对象
        ZipArchiveEntry entry2 = new ZipArchiveEntry("shiro学习.txt");//压缩包中的文件名，一项一项的

        //要压缩的文件
        File f2 = new File("e:/shiro学习.txt");
        FileInputStream fis2 = new FileInputStream(f2);
        BufferedInputStream bis2 = new BufferedInputStream(fis2);

        //输出的对象 压缩的文件
        ZipArchiveOutputStream zipOutput = new ZipArchiveOutputStream(new File("e:/dubbo-user-book.pdf.zip"));
        zipOutput.setUseZip64(Zip64Mode.Always);
        zipOutput.setMethod(ZipEntry.DEFLATED);
        zipOutput.setLevel(Deflater.BEST_COMPRESSION);//JDK的！

        zipOutput.putArchiveEntry(entry);//见javadoc
        byte[] bs = new byte[1024 * 1024];
        int len = -1;
        while((len = bis.read(bs)) != -1){
            zipOutput.write(bs, 0, len);
            System.out.println(len);
        }
        zipOutput.closeArchiveEntry();

        zipOutput.putArchiveEntry(entry2);//见javadoc
        while((len = bis2.read(bs)) != -1){
            zipOutput.write(bs, 0, len);
            System.out.println(len);
        }
        zipOutput.closeArchiveEntry();

        zipOutput.close();
        fis.close();
    }
}
