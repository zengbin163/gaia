package test.jdk.executor;

import java.util.concurrent.ExecutorService;

import org.junit.Test;

import test.jdk.executor.service.SimpleExecutorService;

/**
 * ExecutorService，可以结束、终结、提交、调用任务。
 * 即，扩展了Executor。
 * 
 * @author Administrator
 *
 */
public class ExecutorServiceTest {

    @Test
    public void r1() {
        ExecutorService es=new SimpleExecutorService();
    }
}
