package test.jdk.concurrency;

/**
 * 测试匿名类的this和lambda的this，二者的区别！
 *
 * Created by 张少昆 on 2017/9/13.
 */
public class ThisTest {
	public static void main(String[] args){
		ThisTest thisTest = new ThisTest();
		thisTest.test();
	}

	public void test(){
		new Thread(new Runnable() {
			@Override
			public void run(){
				System.out.println("匿名类的this: " + this);
			}
		}, "t1").start();

		new Thread(() -> {
			System.out.println("lambda的this: " + this);
		}, "t2").start();
	}

	@Override
	public String toString(){
		return "我是ThisTest.java的实例";
	}
}
