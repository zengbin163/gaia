package jdk.io;

import java.io.Console;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Created by 张少昆 on 2018/5/3.
 */
public class ConsoleDemo {

    public static void main(String[] args){
        Console console = System.console();
        System.out.println("console: " + console); //FIXME 奇怪，使用IDEA得到的是null！是因为没用cmd执行，所以环境不同？

        PrintWriter writer = console.writer();
        System.out.println("writer: " + writer);

        for(int i = 0; i < 10; i++){
//            writer.write(i);
            console.printf(i + "");
        }


//        System.runFinalization();
//        Map<String, String> env = System.getenv();
//        env.forEach((k, v) -> {
//            System.out.println(k + " : " + v);
//        });
//
//        System.out.println("------------");
//        Map<Object, Object> properties = System.getProperties();
//        properties.forEach((k, v) -> {
//            System.out.println(k + " : " + v);
//        });

    }


}
