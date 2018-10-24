package test.jdk.classloader;

public class StaticDemo {
    static {
        System.out.println("静态代码块执行！");
    }

    {
        System.out.println("实例代码块执行！");
    }

    public StaticDemo() {
        System.out.println("构造代码块执行！");
    }
}
