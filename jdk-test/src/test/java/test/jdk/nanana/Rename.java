package test.jdk.nanana;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by 张少昆 on 2018/5/20.
 */
public class Rename {
    public static void main(String[] args){
//        if(args==null || args.length==0)
//            throw new RuntimeException("请输入参数");

        Path path = Paths.get("示例路径");
        try{
            Files.list(path).forEach(e -> {
                File file = e.toFile();
                if(file.isFile()){
                    String name = file.getName().replace("[IT18掌www.it18zhang.com]", "");
                    name = name.replace("[IT十八掌www.it18zhang.com]", "");
                    System.out.println("完整路径：" + file.getAbsolutePath());

                    String newPath = file.getParent() + "\\" + name;
                    System.out.println("新完整路径：" + newPath);

                    file.renameTo(new File(newPath));
                }
            });
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}