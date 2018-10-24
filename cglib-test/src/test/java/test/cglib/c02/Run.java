package test.cglib.c02;

import org.mockito.cglib.proxy.Proxy;

import test.cglib.c02.invocationhandler.ProxyInvocationHandler;
import test.cglib.c02.targets.TargetOneInterface;

/**
 * 这里，模拟了一个最简单的IoC容器。见ProxyInvocationHandler。
 * 
 * @author Administrator
 *
 */
public class Run {
    public static void main(String[] args) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        // TODO 注意，这里是cglib的代理处理器、代理对象！用法与jdk的完全一样！见jdk-test中的proxy。
        ProxyInvocationHandler proxyInvocationHandler = new ProxyInvocationHandler();
        Object proxy = Proxy.newProxyInstance(classLoader, new Class<?>[] { TargetOneInterface.class }, proxyInvocationHandler);

        // 开始调用
        TargetOneInterface target = (TargetOneInterface) proxy;
        target.doSomething();
        target.handleSomething();
    }
}
