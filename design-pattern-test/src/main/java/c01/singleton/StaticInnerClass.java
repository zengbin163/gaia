package c01.singleton;

/**
 * 静态内部类单例
 * <p>
 * 优点：线程安全；并发高效；懒加载；
 * <p>
 * TODO 推荐
 * <p>
 * TODO 关键：外部类不能有static属性！
 * <p>
 * Created by 张少昆 on 2018/4/17.
 */
public class StaticInnerClass {
    private StaticInnerClass(){
    }

    private static class Inner {
        static{
            System.out.println("hehe");
        }

        private static final StaticInnerClass instance = new StaticInnerClass();
    }

    // 嗯？？？内部类的类加载？？
    public static StaticInnerClass getInstance(){
        return Inner.instance;
    }

    public static void main(String[] args){
        //TODO 将main内部注释/放开，有不同效果！！！
        StaticInnerClass instance = getInstance();
    }
}
