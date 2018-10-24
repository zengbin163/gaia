package test.jdk.thread.multithread;

import test.jdk.util.TimeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张少昆 on 2017/9/7.
 */
public class WaitNotifyNotifyAllTest {
	public static void main(String[] args){
		List<Integer> taskQueue = new ArrayList<Integer>();
		int MAX_CAPACITY = 5;
		Thread tProducer = new Thread(new Producer(taskQueue, MAX_CAPACITY), "Producer");
		Thread tConsumer1 = new Thread(new Consumer(taskQueue), "Consumer1");
		Thread tConsumer2 = new Thread(new Consumer(taskQueue), "Consumer2");
		tProducer.start();
		tConsumer1.start();
		tConsumer2.start();
	}

}

class Producer implements Runnable {
	private final List<Integer> taskQueue;
	private final int MAX_CAPACITY;

	public Producer(List<Integer> sharedQueue, int size){
		this.taskQueue = sharedQueue;
		this.MAX_CAPACITY = size;
	}

	@Override
	public void run(){
		int counter = 0;
		while(true){
			try{
				produce(counter++);
			} catch(InterruptedException ex){
				ex.printStackTrace();
			}
		}
	}

	private void produce(int i) throws InterruptedException{
		synchronized(taskQueue){ //这也行？
			while(taskQueue.size() == MAX_CAPACITY){
				System.out.println(TimeUtils.time() + "[PRODUCER]Queue is full " + Thread.currentThread().getName() + " is waiting , size: " + taskQueue.size());
				taskQueue.wait();
				System.out.println(TimeUtils.time() + "[PRODUCER] " + Thread.currentThread().getName() + " is notified, size: " + taskQueue.size());//会改变吧
			}

			Thread.sleep(1000);
			taskQueue.add(i);
			System.out.println(TimeUtils.time() + "Produced: item" + i);
//			taskQueue.notifyAll();
			taskQueue.notify();
		}
	}
}

class Consumer implements Runnable {
	private final List<Integer> taskQueue;

	public Consumer(List<Integer> sharedQueue){
		this.taskQueue = sharedQueue;
	}

	@Override
	public void run(){
		while(true){
			try{
				consume();
			} catch(InterruptedException ex){
				ex.printStackTrace();
			}
		}
	}

	private void consume() throws InterruptedException{
		synchronized(taskQueue){
			while(taskQueue.isEmpty()){
				System.out.println(TimeUtils.time() + "[CONSUMER]Queue is empty " + Thread.currentThread().getName() + " is waiting , size: " + taskQueue.size());
				taskQueue.wait();
				System.out.println(TimeUtils.time() + "[CONSUMER] " + Thread.currentThread().getName() + " is notified , size: " + taskQueue.size());//会改变吧
			}
			Thread.sleep(1000);
			int i = (Integer) taskQueue.remove(0);
			System.out.println(TimeUtils.time() + "Consumed: item" + i);
//			taskQueue.notifyAll();
			taskQueue.notify();
		}
	}
}
