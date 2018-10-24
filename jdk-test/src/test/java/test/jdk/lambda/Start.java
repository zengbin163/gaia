package test.jdk.lambda;

import org.junit.Test;

public class Start {
	
	@Test
	public void start() {
		SumInterface inter = (a, b) -> a + b; //给FunctionalInterface一个实例定义，后台会转成对象
		int r = inter.sum(3, 5);
		System.out.println(r);
	}
	
}

/**
 * 一个FunctionalInterface，是指仅有一个接口方法（可以有多个默认方法什么的）。
 * 
 * @author Larry
 *
 */
@FunctionalInterface
interface SumInterface {
	int sum(int a, int b);
}