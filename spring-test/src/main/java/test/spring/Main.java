package test.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import test.spring.config.MyTask;

/**
 * Created by 张少昆 on 2018/1/31.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException{
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MyTask.class);
        Thread.sleep(1000*3);
        // ctx.refresh(); //FIXME GenericApplicationContext does not support multiple refresh attempts: just call 'refresh' once
        // ctx.registerBeanDefinition();
        // ctx.removeBeanDefinition();//TODO 嗯？
        // ctx.registerShutdownHook();
        // ctx.setClassLoader();


        // while(true){
        //     //holding
        //     Thread.sleep(10000L);
        // }
        try{
            Main.class.wait();
        } catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}
