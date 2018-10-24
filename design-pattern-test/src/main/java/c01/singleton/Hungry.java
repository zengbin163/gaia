package c01.singleton;

/**
 * 饿汉式单例
 * <p>
 * 优点，无任何线程安全问题；
 * 缺点，如不需要，则浪费资源；
 * <p>
 * Created by 张少昆 on 2018/4/17.
 */
public class Hungry {
    //私有构造器
    private Hungry(){
    }

    // 静态变量
    private static Hungry instance = new Hungry();

    // 对外接口
    public static Hungry getInstance(){
        return instance;
    }
}
