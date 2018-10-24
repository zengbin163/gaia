package test.jdk.math;

import org.junit.Test;

/**
 * Created by zengbin on 2017/10/9.
 */
public class BasicTest {

	@Test
	public void test(){
		int a = 0b111; // 二进制
		int b = 0111; // 八进制
		int c = 0x111; // 十六进制
		int d = 01_1_1; // 间隔符

		System.out.println(a);
		System.out.println(b);
		System.out.println(c);
		System.out.println(d);
	}

	@Test
	public void eq(){
		Integer a = 127;
		Integer b = 127;

		System.out.println(a == b);//true (<128)

		a = 128;
		b = 128;
		System.out.println(a == b);//false

		a = b = 2000;
		System.out.println(a == b);//true

	}

	@Test
	public void testUnsigned(){
		byte b = -127;
		int no = Byte.toUnsignedInt(b); // jdk8 转成无符号数
		System.out.println(no); // 129!!!

		b = -1;
		System.out.println(Byte.toUnsignedInt(b)); // 255!
	}

	@Test
	public void testInfiniteAndNaN(){ //测试无限和无效
		double d = 3.0d;
		double r = d / 0;
		System.out.println(r); // Infinity

		System.out.println(Double.isInfinite(r));
		System.out.println(Double.isNaN(r));

		// 奇怪，什么时候出现Double.isNaN ？ - 见源码
		double v = 0.0d / 0.0;
		System.out.println(v); //
		System.out.println(Double.isNaN(v));
	}

	@Test
	public void testFloorMod(){ //测试求余
		int a = 12 % 5;
		System.out.println(a); //2

		int b = -12 % 5;
		System.out.println(b); //-2

		int c = Math.floorMod(-12, 5);
		System.out.println(c); //3!!!  always positive
	}

	@Test
	public void testToString(){ //测试数字转成其他进制形式的字符串
		int a = 9999;
		String s1 = Integer.toBinaryString(a);
		System.out.println(s1);

		String s2 = Integer.toOctalString(a);
		System.out.println(s2);

		String s3 = Integer.toHexString(a);
		System.out.println(s3);

		String s4 = Integer.toString(a, 7);
		System.out.println(s4);
	}

}
