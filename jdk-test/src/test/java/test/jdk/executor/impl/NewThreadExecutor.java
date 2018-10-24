package test.jdk.executor.impl;

import java.util.concurrent.Executor;

/**
 * 每个任务都新起一个线程。
 * 
 * @author Administrator
 *
 */
public class NewThreadExecutor implements Executor {

    @Override
    public void execute(Runnable command) {
        new Thread(command).start(); // 这里，每个任务都新起一个线程
    }

}
