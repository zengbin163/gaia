package test.cglib.c01.proxy;

import java.lang.reflect.Method;

import org.mockito.cglib.proxy.MethodInterceptor;
import org.mockito.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//默认的代理器，什么都不执行
public class DefaultProxy implements MethodInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultProxy.class);

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        LOG.info("被调用的方法：" + method.getName());

        // 这里不做任何处理（也可以直接使用proxy执行父级代码）
        return null;
    }

}
