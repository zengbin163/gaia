package jdk.load;

/**
 * http://www.cnblogs.com/larryzeal/p/8135628.html
 *
 * Created by zengbin on 2017/12/28.
 */
public class A {
    private static A a = new A();
//    private static A b = new A();

    static{
        System.out.println("我是静态代码块");
    }

    {
        System.out.println("实例代码块");
    }

    public A(){
        System.out.println("构造方法");
    }

    //TODO 执行空的main方法，就是只加载字节码。可以有效测试加载过程中的顺序。
    public static void main(String[] args){
//        new A(); //放开注释，执行，看一下实例代码块和构造方法的执行顺序
    }
}
