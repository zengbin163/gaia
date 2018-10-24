package test.jdk.classloader;

import org.junit.Test;

/**
 * Bootstrap ClassLoader, Ext ClassLoader, App ClassLoader, Custom ClassLoader;
 * Bootstrap ClassLoader 是是用native编写的，负责加载$JRE_HOME/lib，应用程序永远获取不到该类加载器，即null。
 * Ext用于加载$JRE_HOME/lib/ext。 App ClassLoader用于加载classpath下的类库。
 * <p>
 * 还有一个URLClassLoader，用于加载classpath之外的资源！
 *
 * @author Administrator
 */
public class ClassLoaderTest {

    @Test
    public void r1(){
        System.out.println(this.getClass().getClassLoader());// app classloader
        System.out.println(this.getClass().getClassLoader().getParent());// ext classloader
        System.out.println(this.getClass().getClassLoader().getParent().getParent());// bootstrap classloader, null
    }

    @Test
    public void r11(){
        ClassLoader currentClassLoader = this.getClass().getClassLoader();
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();// app classloader
        ClassLoader extClassLoader = systemClassLoader.getParent();

        System.out.println(currentClassLoader);
        System.out.println(systemClassLoader);
        System.out.println(extClassLoader);
    }

    // 这样是动态加载！且默认初始化（不是实例化类对象，而是初始化Class）
    @Test
    public void r2(){
        try{
            Class.forName("test.jdk.classloader.StaticDemo"); // Class.forName(name, true, currentLoader);
        } catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    // 这里就没有初始化Class，也就不会执行静态代码块！
    @Test
    public void r3(){
        try{
            Class.forName("test.jdk.classloader.StaticDemo", false, this.getClass().getClassLoader()); // Class.forName(name, true, currentLoader);
        } catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    @Test
    public void r4(){
        try{
            Class<?> clz = Class.forName("test.jdk.classloader.StaticDemo", false, this.getClass().getClassLoader());
            clz.newInstance(); // 实例化对象，如果没有初始化Class，那么会顺便初始化。看说明，会bypass编译期异常检查。
        } catch(ClassNotFoundException e){
            e.printStackTrace();
        } catch(InstantiationException e){
            e.printStackTrace();
        } catch(IllegalAccessException e){
            e.printStackTrace();
        }
    }
}
