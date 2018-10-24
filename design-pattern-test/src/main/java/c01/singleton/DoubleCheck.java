package c01.singleton;

/**
 * 双重检查锁单例。
 * <p>
 * 优点：懒加载；且只有第一次是同步；
 * 缺点：由于编译器优化以及JVM底层模型，导致偶尔出现问题，不建议使用。
 * <p>
 * Created by zengbin on 2018/4/17.
 */
public class DoubleCheck {
    private DoubleCheck(){
    }

    private static DoubleCheck instance;

    public static DoubleCheck getInstance(){
        if(instance == null){
            DoubleCheck tmp;
            synchronized(DoubleCheck.class){
                tmp = instance;
                if(tmp == null){
                    synchronized(DoubleCheck.class){
                        if(tmp == null){
                            tmp = new DoubleCheck();
                        }
                    }
                    instance = tmp;
                }
            }
        }

        return instance;
    }
}
