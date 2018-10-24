package test.jdk.thread.concurrency;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * Created by zengbin on 2017/9/5.
 */
public class CountDownLatchTest {

	@Test
	public void r1() throws InterruptedException{
		// 就是在10个线程完成之后，再恢复主线程的执行。如果主线程结束了，那其他剩余的线程就无法执行了。--可以通过输出文件测试。
		// 关键：①每个线程结束都要调用countDown()；②主线程调用await()以便等待。
		// 另，应该在finally中执行countDown()！

		int thread_count = 10;
		CountDownLatch countDownLatch = new CountDownLatch(thread_count);
		for(int i = 0; i != 20; ++i){
			final int tmp = i;
			new Thread(() -> {
				System.out.println("当前线程[" + Thread.currentThread().getName() + "] 开始！");
				try{
					Thread.sleep((tmp * 1000));
				} catch(InterruptedException e){
					e.printStackTrace();
				}
				countDownLatch.countDown();
				System.out.println("当前次数：" + countDownLatch.getCount());
				System.out.println("当前线程[" + Thread.currentThread().getName() + "] 结束！");
			}).start();
//			Thread.sleep(1000);// 不能在这里等待，因为这里属于主线程
		}
		try{
			countDownLatch.await();
		} catch(InterruptedException e){
			e.printStackTrace();
		}

		System.out.println("主线程继续执行");
	}


}
