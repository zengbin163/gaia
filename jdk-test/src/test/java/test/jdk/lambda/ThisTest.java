package test.jdk.lambda;

/**
 * 测试lambda中的this以及内部类中的this。
 * 结果表明，lambda中的this是当前类的对象，而内部类的this是内部类对象。
 *
 * Created by zengbin on 2017/9/13.
 */
public class ThisTest {
    public static void main(String[] args){
        ThisTest thisTest = new ThisTest();
        thisTest.test();
    }

    public void test(){
        new Thread(new Runnable() {
            @Override
            public void run(){
                System.out.println("匿名类的this: " + this);
            }
        }, "t1").start();

        new Thread(() -> {
            System.out.println("lambda的this: " + this);
        }, "t2").start();
    }

    @Override
    public String toString(){
        return "我是ThisTest.java的实例";
    }
}
