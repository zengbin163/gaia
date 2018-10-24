package reload;

import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.support.XmlWebApplicationContext;

/**
 * Created by 张少昆 on 2018/3/15.
 */
public class Demo {
    @Test
    public void r1(){

        // XmlWebApplicationContext xctx = (XmlWebApplicationContext) ContextLoader.getCurrentWebApplicationContext();
        // DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) xctx.getBeanFactory();
        // BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanN ame);
        // beanDefinition.setBeanClassName(className);
        // beanFactory.registerBeanDefinition(beanName, beanDefinition);
    }
}
