package test.jdk.thread.concurrency;

import org.junit.Test;
import test.jdk.util.TimeUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 设定semaphore对应的数值N，每个线程都先acquire一下，用完了就release。
 * 然后，当线程数大于N时，只有N个线程继续执行（并非此时只有N个线程，可以很多，但N个之外的都被挂起而已）。其他的线程，只有在前N个线程中有释放了semaphore时才会继续执行。
 * 就是用来保证同时最多有N个执行，其他的都得等着。
 * 可以通过true来设定后续的执行顺序为FIFO。
 * <p>
 * 奇怪的是，这个和FixedThreadPool有什么区别？？？
 * 区别就是，semaphore的线程只是被挂起，但总线程的数量不变。
 * 而FixedThreadPool则是只能有N个线程启动。
 * <p>
 * Created by zengbin on 2017/9/8.
 */
public class SemaphoreTest {
	@Test
	public void semaphore() throws InterruptedException{
//		Semaphore semaphore = new Semaphore(5);
//		Semaphore semaphore = new Semaphore(5, true); //5个，FIFO

		// 线程池
		ExecutorService exec = Executors.newCachedThreadPool();

		// 只能5个线程同时访问
		final Semaphore semp = new Semaphore(5);

		// 模拟20个客户端访问
		for(int index = 0; index < 20; index++){
			final int NO = index;
			Runnable run = new Runnable() {
				public void run(){
					System.out.println(TimeUtils.time() + "[" + NO + "] started");
					try{
						// 获取许可
						semp.acquire();
						System.out.println(TimeUtils.time() + "[" + NO + "] acquired semaphore, and ready to sleep some seconds.");
						Thread.sleep((long) (Math.random() * 10 * 1000) + 5000);

						// 访问完后，释放
						semp.release();
						System.out.println(TimeUtils.time() + "[" + NO + "] released semaphore, and availablePermits: " + semp.availablePermits());
					} catch(InterruptedException e){
						e.printStackTrace();
					}

				}

			};
			exec.execute(run);
		}

		// 退出线程池
		exec.shutdown();

		Thread.sleep(30 * 1000);
	}

	/**
	 * @throws InterruptedException
	 */
	@Test
	public void fixedThreadPool() throws InterruptedException{
		ExecutorService executorService = Executors.newFixedThreadPool(5);

		for(int i = 0; i < 20; i++){
			final int NO = i;
			executorService.execute(() -> {
				System.out.println(TimeUtils.time() + "[" + NO + "] started");
				try{
					Thread.sleep((long) (Math.random() * 10 * 1000) + 5000);
					System.out.println(TimeUtils.time() + "[" + NO + "] ---finished");
				} catch(InterruptedException e){
					e.printStackTrace();
				}
			});
		}

		executorService.shutdown();

		Thread.sleep(50 * 1000);
	}
}
