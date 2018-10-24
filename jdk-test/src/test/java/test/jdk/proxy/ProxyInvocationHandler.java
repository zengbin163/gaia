package test.jdk.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

//代理的调用处理器
public class ProxyInvocationHandler implements InvocationHandler {

    // proxy 代理对象，注意是代理者，不是被代理者
    // method 被代理的方法
    // args 被执行的代理方法中传入的参数
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("-----------------------------------");
        System.out.println("代理者的对象：" + proxy.getClass().getName());

        // 被代理的接口
        Class<?> declaringClass = method.getDeclaringClass();
        System.out.println("被代理的接口类：" + declaringClass.getName());
        System.out.println("被代理的方法：" + method.getName());

        if (args == null) {
            return null;
        }

        System.out.println("被代理的调用过程参数类型：");
        Arrays.asList(args).stream().forEach((item) -> {
            System.out.println("方法类型：" + item.getClass().getName());
        });

        return null;
    }

}
