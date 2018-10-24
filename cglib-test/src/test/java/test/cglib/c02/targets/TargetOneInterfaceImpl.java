package test.cglib.c02.targets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TargetOneInterfaceImpl implements TargetOneInterface {
    private static final Logger LOG = LoggerFactory.getLogger(TargetOneInterfaceImpl.class);

    @Override
    public void doSomething() {
        LOG.info("doSomething 方法被执行");
    }

    @Override
    public void handleSomething() {
        LOG.info("handleSomething 方法被执行");
    }

}
