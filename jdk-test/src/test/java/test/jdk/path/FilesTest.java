package test.jdk.path;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by 张少昆 on 2017/10/7.
 */
public class FilesTest {
    Path path = Paths.get("d:/spring.factory.txt");
    Path path2 = Paths.get("d:/test111.txt");

    @Test
    public void r1() throws IOException{
        Stream<String> lines = Files.lines(Paths.get("d:/spring.factory.txt"));
        Optional<String> s = lines.findAny().filter(e -> {
            return e.length() > 10;
        });
        System.out.println(s.isPresent());
        System.out.println(s.get());
    }

    @Test
    public void r2() throws IOException{
        // Files.copy(path, path2); //
        Files.copy(path, path2, StandardCopyOption.REPLACE_EXISTING); //
        // Files.copy(path, path2, StandardCopyOption.COPY_ATTRIBUTES); // copy attributes
    }

    @Test
    public void r3() throws IOException{
        // Stream<Path> walk = Files.walk(Paths.get("d:/")); // ERROR why ? how?
        Stream<Path> walk = Files.walk(Paths.get("d:/xxx"));
        List<Path> list = walk.collect(Collectors.toList());
        System.out.println(list);
    }

    //TODO 一定要注意URL解码！！！！
    @Test
    public void r4() throws UnsupportedEncodingException{
        String f = "/D:/My%20Space/IDEA/web-test-parent/ztree-test/target/classes/test";
        File file = new File(URLDecoder.decode(f,"UTF-8")); //奇怪，为什么访问不到？？
        System.out.println(file.isFile());
        System.out.println(file.isDirectory());
        System.out.println(String.join("\r\n", file.list()));
    }

}
