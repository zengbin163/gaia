package c01.singleton;

/**
 * 懒汉式单例
 * <p>
 * 优点：懒加载；
 * 缺点：每次调用都要同步，并发效率低。
 * <p>
 * Created by 张少昆 on 2018/4/17.
 */
public class Lazy {
    // 私有化构造器
    private Lazy(){
    }

    //
    private static Lazy instance;

    public static synchronized Lazy getInstance(){
        if(instance == null){
            instance = new Lazy();
        }
        return instance;
    }
}
