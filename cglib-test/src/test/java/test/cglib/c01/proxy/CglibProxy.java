package test.cglib.c01.proxy;

import java.lang.reflect.Method;

import org.mockito.cglib.proxy.MethodInterceptor;
import org.mockito.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//这是一个基于cglib的代理器
public class CglibProxy implements MethodInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(CglibProxy.class);

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        // LOG.info("通过debug观察Enhancer生成的子级类,是否有似曾相识的感觉?");
        LOG.info("被调用的方法：" + method.getName());
        // LOG.info("可以在正式调用前执行一些额外的过程");

        // 这里返回真正的业务调用过程
        Object result = null;
        try {
            result = methodProxy.invokeSuper(obj, args);// TODO 为什么不用invoke？
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            LOG.info("在调用执行异常后，执行一些额外的过程");
            return null;
        }
        LOG.info("在调用执行成功后，执行一些额外的过程");
        return result;
    }

}
