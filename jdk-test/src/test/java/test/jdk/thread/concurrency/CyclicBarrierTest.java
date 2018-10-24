package test.jdk.thread.concurrency;

import org.junit.Test;
import test.jdk.util.TimeUtils;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 和CountDownLatch不同，Cyclic是所有线程必备线程都抵达之后，才唤醒所有线程。
 * CountDownLatch则是所有线程完成之后，再执行主线程。
 * 注意：是循环的！！！每N个线程，就执行一次唤醒！！！
 * <p>
 * Created by 张少昆 on 2017/9/6.
 */
public class CyclicBarrierTest {

	@Test
	public void r1(){
		int thread_num = 5;
		CyclicBarrier cyclicBarrier = new CyclicBarrier(thread_num);

		//囧囧有神，刚发现lambda的this永远是调用线程对应的对象。那也不能用wait() notify()啥的吧？？？
//		new Thread(()->{
//			try{
//				System.out.println("等待");
//				System.out.println(toString());
//				System.out.println(this.toString());
//				wait(); //这里是主线程的wait()，没有锁，所以会报错！
//
//				System.out.println("结束");
//			} catch(InterruptedException e){
//				e.printStackTrace();
//			}
//		}).start();


//		new Thread(new Runnable() {
//			@Override
//			public String toString(){
//				return "runnable{in}";
//			}
//
//			@Override
//			public void run(){
//				try{
//					System.out.println("等待");
//					System.out.println(toString()); //这个是哪个？最近的
//					System.out.println(this.toString());
////					wait(); //这里是主线程的wait()，没有锁，所以会报错！
//					this.wait();
//
//					System.out.println("结束");
//				} catch(InterruptedException e){
//					e.printStackTrace();
//				}
//			}
//		}).start();

		new Thread(new WaitThread()).start();

		for(int i = 0; i < 10; i++){
			final int tmp = i;
			new Thread(() -> {
				System.out.println(TimeUtils.time() + "线程 " + tmp + " 开始执行，要cyclicBarrier.await()了");
				System.out.println(toString());
				System.out.println(this.toString());
				try{
					cyclicBarrier.await();// 【】关键
				} catch(InterruptedException e){
					e.printStackTrace();
				} catch(BrokenBarrierException e){
					e.printStackTrace();
				}
				System.out.println(TimeUtils.time() + "线程 " + tmp + " 被唤醒了！");
				try{
					Thread.sleep(3 * 1000);
				} catch(InterruptedException e){
					e.printStackTrace();
				}
				System.out.println(TimeUtils.time() + "线程 " + tmp + " 工作了" + 3 + "秒之后完成了任务！");

			}).start();


			try{
				Thread.sleep(1000);
			} catch(InterruptedException e){
				e.printStackTrace();
			}
		}


		try{
//			wait(100);//为什么不能用，是否是全部唤醒？ 不是全部唤醒，是调用了await()的才能唤醒
//			while(true){
//			wait(100*1000);
			System.out.println("----主线程有没有被唤醒？");
			Thread.sleep(100*1000);
//			}
		} catch(InterruptedException e){
			e.printStackTrace();
		}

	}



	@Override
	public String toString(){
		return "CyclicBarrierTest{xxx}";
	}
}
