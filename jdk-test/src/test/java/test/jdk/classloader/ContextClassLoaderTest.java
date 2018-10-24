package test.jdk.classloader;

import org.junit.Test;

/**
 * 线程上下文类加载器。 用于解决SPI之类的问题，因为SPI的定义是JDK的核心类，而其实现则是由第三方库提供。 因此，SPI的加载是由Bootstrap
 * ClassLoader负责的，而Bootstrap ClassLoader又无法加载非JDK的核心类，又不能委托！ 那么，怎么解决该问题？
 * 这就是线程上下文类加载器的作用了！
 * 
 * 
 * @author Administrator
 *
 */
public class ContextClassLoaderTest {

    @Test
    public void r2() {// 线程上下文类加载器，如无指定，就是AppClassLoader
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        System.out.println(contextClassLoader);
    }

}
