package jdk.security;

/**
 * 为安全管理器绑定policy文件的方式有两种：
 * 一、在运行程序的时候加入-Djava.security.policy=demo.policy虚拟机启动参数；
 * 二、执行System.setProperty("java.security.policy", "demo.policy");
 * <p>
 * 在上述过程中虽然完成了授权，但授权的针对性不强，
 * 在程序绑定了该policy文件后，无论是哪个用户执行都将拥有java.home系统属性的读权限，现在我们希望做更加细粒度的权限控制，这里需要用到认证服务了。
 * <p>
 * https://blog.csdn.net/xiaolangfanhua/article/details/52835920
 * <p>
 * <p>
 * Created by zengbin on 2018/6/20.
 */
public class SMDemo {
    public static void main(String[] args){
        System.out.println(System.getSecurityManager());
        System.setSecurityManager(new SecurityManager());
        System.setProperty("java.security.policy", "demo.policy");//FIXME 没有写权限！

        System.out.println(System.getProperty("java.home"));
    }
}
