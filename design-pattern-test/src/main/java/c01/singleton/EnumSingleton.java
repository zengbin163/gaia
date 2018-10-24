package c01.singleton;

/**
 * 枚举单例。
 * 优点：各种好；甚至可以避免通过反射和反序列化来生成。
 * 缺点：无法延迟加载。
 * <p>
 * Created by zengbin on 2018/4/17.
 */
public enum EnumSingleton {
    INSTANCE;

    public EnumSingleton getInstance(){
        return INSTANCE;
    }
}
