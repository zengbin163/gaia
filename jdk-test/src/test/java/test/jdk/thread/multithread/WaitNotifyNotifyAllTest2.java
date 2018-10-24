package test.jdk.thread.multithread;

import test.jdk.util.TimeUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * 一定要处理好逻辑。
 * 一般，wait()被唤醒之后，还要判断一下自己的条件是否满足（如货物是否满了，满了（生产者）就继续等待）。
 * 同样，nofity()或notifyAll()也应该做类似处理。
 * 这个例子不太好，还是看WaitNotifyNotifyAllTest吧。
 * <p>
 * Created by zengbin on 2017/9/7.
 */
public class WaitNotifyNotifyAllTest2 {
	public static void main(String[] args) throws InterruptedException{
		List<String> list = new ArrayList<>(5);

		new Thread(new TestThread(list), "AAA").start();
		new Thread(new TestThread(list), "BBB").start();

		Thread.sleep(3 * 1000);
		synchronized(list){
			System.out.println(TimeUtils.time() + "第一次通知，notify");
			list.notify();

			Thread.sleep(3 * 1000);
			list.add(Instant.now().toString());
			list.add(Instant.now().toString());
			System.out.println(TimeUtils.time() + "第二次通知，notify");
			list.notify();

			Thread.sleep(3 * 1000);
			System.out.println(TimeUtils.time() + "第三次通知，notify");
			list.notify();
		}


		while(true){
		}
	}

}

class TestThread implements Runnable {
	private List<String> list;

	public TestThread(List<String> list){
		this.list = list;
	}

	@Override
	public void run(){
		System.out.println(TimeUtils.time() + Thread.currentThread().getName() + " is to wait! size=" + list.size());
		synchronized(list){
			try{
				while(list.size() != 5){
					list.wait();
					System.out.println("通知到了？那就开工添加内容！"); //我擦，还真通知到了
					list.add(Instant.now().toString());
				}
			} catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		System.out.println(TimeUtils.time() + Thread.currentThread().getName() + " wakes up! size=" + list.size());
		try{
			Thread.sleep(1000);
		} catch(InterruptedException e){
			e.printStackTrace();
		}
	}
}
