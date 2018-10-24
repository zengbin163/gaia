package test.jdk.path;

import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Path，取代了File。
 * <p>
 * Created by zengbin on 2017/10/7.
 */
public class PathTest {
    @Test
    public void r1(){
        // Path path = Files.;
        Path path = Paths.get("d:/spring.factory.txt");
        System.out.println(path);
        System.out.println(path.endsWith("ini"));
        System.out.println(path.getFileName());
        System.out.println(path.getName(0));
        System.out.println(path.getFileSystem());
        System.out.println(path.getNameCount());
        System.out.println(path.getParent());
        System.out.println(path.getRoot());
        System.out.println(path.isAbsolute());
        System.out.println(path.normalize());

        URI uri = path.toUri();
        System.out.println(uri);
        System.out.println(uri.getPath());
        System.out.println(uri.getRawPath());
    }

    @Test
    public void r2(){
        //FIXME @Test 这里没有源码啦
        Path path = Paths.get(".").resolve("PathTest.java");
        try{
            Files.readAllLines(path).forEach(System.out::println);
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
