package test.jdk.executor.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

//TODO 一定要实现一个简单的ExecutorService！！！
/**
 * ExecutorService，可以结束、终结、提交、调用任务。 即，扩展了Executor。
 * 
 * TODO 未完待续；有心无力。。
 * 
 * @author Administrator
 *
 */
public class SimpleExecutorService implements ExecutorService {
    private LinkedBlockingQueue<RunnableFuture> awaitingQueue = new LinkedBlockingQueue<>();
    private LinkedBlockingQueue<Thread> executingQueue = new LinkedBlockingQueue<>();
    private boolean shutdown = false; // 允许提交任务
    private boolean terminated = false; // 允许提交任务

    @Override
    public void execute(Runnable command) {
        command.run();// 仍然是caller's thread 来执行 -- 这样的话就是顺序执行了。
    }

    @Override
    public void shutdown() {
        shutdown = true;
    }

    @Override
    public List<Runnable> shutdownNow() { // 返回等待执行的任务列表
        shutdown = true;
        return new ArrayList<>(awaitingQueue);
    }

    @Override
    public boolean isShutdown() {
        return shutdown;
    }

    @Override
    public boolean isTerminated() {
        return terminated;
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {

        return false;
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {//TODO 关键
        FutureTask<T> futureTask = new FutureTask<>(task);
        awaitingQueue.add(futureTask);
        return futureTask;
    }

    @Override
    public <T> Future<T> submit(Runnable task, T result) {//TODO 关键
        
        return null;
    }

    @Override
    public Future<?> submit(Runnable task) { //TODO 关键！ 这个Future，怎么处理？参考JDK的实现，定义一个FutureRunnable类，然后提交其对象，再将结果保存到其Future中
        FutureTask<Void> futureTask = new FutureTask<>(task, null);

        awaitingQueue.add(futureTask);
        return futureTask;
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
            throws InterruptedException, ExecutionException, TimeoutException {
        // TODO Auto-generated method stub
        return null;
    }

}
