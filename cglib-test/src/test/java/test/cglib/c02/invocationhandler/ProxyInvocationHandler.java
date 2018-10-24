package test.cglib.c02.invocationhandler;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.mockito.cglib.proxy.InvocationHandler;

import test.cglib.c02.targets.TargetOneInterface;
import test.cglib.c02.targets.TargetOneInterfaceImpl;

//注意，是cglib的InvocationHandler，不是jdk中的
public class ProxyInvocationHandler implements InvocationHandler {

    /*** 模拟一个最简单的IoC容器 */
    private static Map<Class<?>, Object> container = new HashMap<>();

    static {// 初始化容器内容
        container.put(TargetOneInterface.class, new TargetOneInterfaceImpl());
    }

    // proxy是代理
    // method是jdk反射中的method
    // args是参数
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("proxy is: " + proxy.getClass().getName());
        // 被代理的接口（或类）
        Class<?> declaringClass = method.getDeclaringClass();
        // 从容器中获取该类的对象
        Object target = container.get(declaringClass);
        if (target == null) {// 容器中不存在，直接返回null
            return null;
        }
        // 容器中存在，开始调用
        return method.invoke(target, args);
    }

}
