package test.jdk.proxy;

import java.lang.reflect.Proxy;

import test.jdk.proxy.inter.TargetOneInterface;
import test.jdk.proxy.inter.TargetTwoInterface;

/**
 * 这是JDK proxy示例，动态代理。必须有接口才行，可以没有实现。
 * 
 * 另见cglib-test模块，动态代理（使用asm，可以动态的创建Class，以二进制形式）。
 * 
 * @author Administrator
 *
 */
public class Run {
    public static void main(String[] args) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        // 这是代理处理器
        ProxyInvocationHandler proxyInvocationHandler = new ProxyInvocationHandler();
        // 创建代理（很有意思）
        Object proxyInstance = Proxy.newProxyInstance(classLoader, new Class<?>[] { TargetOneInterface.class, TargetTwoInterface.class },
                proxyInvocationHandler);

        // 开始调用（测试第一个接口）
        TargetOneInterface targetOne = (TargetOneInterface) proxyInstance;
        targetOne.doSomething();
        targetOne.handleSomething();

        // 开始调用（测试第二个接口）
        TargetTwoInterface targetTwo = (TargetTwoInterface) proxyInstance;
        targetTwo.findSomething();
        targetTwo.findSomethingByField(111);
    }
}
