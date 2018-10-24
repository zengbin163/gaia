package test.cglib.c01.targets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// 这是要被代理的业务处理类
// 这个类既没有任何父类，也没有实现任何接口
public class TargetClass {
    private static final Logger LOG = LoggerFactory.getLogger(TargetClass.class);

    public void handleSomething(String param) {
        LOG.info("传入了一个参数:" + param + "，做了一些业务处理");
    }
}
