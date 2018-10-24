package test.cglib.c01;

import java.lang.reflect.Method;

import org.mockito.cglib.proxy.Callback;
import org.mockito.cglib.proxy.CallbackFilter;
import org.mockito.cglib.proxy.Enhancer;

import test.cglib.c01.proxy.CglibProxy;
import test.cglib.c01.proxy.DefaultProxy;
import test.cglib.c01.targets.TargetClass;

public class Run {
    public static void main(String[] args) {
        // cglib的核心思想，是依靠java字节码操作框架asm，在运行时，为被代理的Class生成扩展代码（子类代码）。
        // 然后，通过运行该扩展代码，实现代理
        // 其中，Enhancer是用来生成代码的主要类，如下：
        Enhancer enhancer = new Enhancer();

        // 设置要代理的类
        enhancer.setSuperclass(TargetClass.class);
        // 设置两个代理器（也可以传入null）
        enhancer.setCallbacks(new Callback[] { new DefaultProxy(), new CglibProxy() });
        // 设置回调过滤器 - 见类MethodFilter - 主要工作是根据代理调用的方法，决定使用哪个代理器
        enhancer.setCallbackFilter(new MethodFilter());

        // 运行时动态创建代理类
        TargetClass proxy = (TargetClass) enhancer.create(); //TODO 还有一个createClass()方法！

        // 以下方法将被DefaultProxy代理 - 原理见MethodFilter
        String str = proxy.toString(); //因为DefaultProxy直接返回null，所以null
        System.out.println(str);
        // 以下方法将被CglibProxy代理 - 原理见MethodFilter
        proxy.handleSomething("随便做点好事吧");
    }

    static class MethodFilter implements CallbackFilter {

        @Override
        public int accept(Method method) {
            /*
             * 在main方法中，我们一共设置了两个代理器，DefaultProxy和CglibProxy。
             * 当被执行的方法是Object类中的，类似clone、toString、hashCode这样的方法，则使用DefaultProxy进行代理。
             * 其他的方法，就是我们真正需要被代理的业务方法了，使用CglibProxy这个代理器进行代理。
             * 
             * 这里返回的结果，就是我们在main方法中使用enhancer.setCallbacks设定的代理器顺序。
             * 返回0，表示使用DefaultProxy代理器；返回1，表示使用CglibProxy代理器。
             * 
             * 代码就不解释了，很容易看懂。
             * 大家还可以参见org.mockito.cglib.proxy.Proxy.getProxyClass的源代码，后文中我们还会提到这个Proxy类
             */
            if (method.getDeclaringClass().getName().equals("java.lang.Object")) {
                return 0;
            }
            return 1;
        }
    }

}
