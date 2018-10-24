package test.jdk.executor.impl;

import java.util.concurrent.Executor;

/**
 * 最简单的，就是caller's thread 来执行已提交的任务。
 * 
 * @author Administrator
 *
 */
public class SimpleExecutor implements Executor {

    @Override
    public void execute(Runnable command) {
        command.run(); // 这里没有新启动线程，就是当前线程执行。
    }

}