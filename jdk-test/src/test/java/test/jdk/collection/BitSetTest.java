package test.jdk.collection;

import org.junit.Test;

import java.util.Arrays;
import java.util.BitSet;

/**
 * BitSet是个很有意思的东西。
 * 应用场景：统计，如出现过（或者没有出现过）哪些数字 - 还可以将其排序，按照大小列出来！
 * <p>
 * Created by zengbin on 2017/10/22 0022.
 */
public class BitSetTest {

	@Test
	public void test(){
		BitSet set = new BitSet(10); //10 bits set
		//flip() 取反
//		set.flip(0);
//		set.flip(1);
//		set.flip(5);

		//set() 设为true
		set.set(0);
		set.set(1);
		set.set(5);
		System.out.println(set); // 应该是列出值为true的那些位的坐标！

		// 8 bit >> 1 byte,  就是说截取8位，转成byte。 就是0010 0011 >>
		System.out.println(Arrays.toString(set.toByteArray()));
		// 64 bit >> 1 long
		System.out.println(Arrays.toString(set.toLongArray()));
	}

	@Test
	public void test1(){
		// 和test()中的set.toByteArray()刚好相反，这里是1 byte >> 8 bits! 需要确认90对应的低8bit 还是高8bit。
		BitSet bitSet = BitSet.valueOf(new byte[]{90, 92, 95, 97});
		System.out.println(bitSet); //{1, 3, 4, 6, 10, 11, 12, 14, 16, 17, 18, 19, 20, 22, 24, 29, 30}

		//截取下上面的输出即可知道90对应的是低位还是高位
		BitSet bs = new BitSet(8);//
		//0~7
		bs.set(1);
		bs.set(3);
		bs.set(4);
		bs.set(6);
		System.out.println(Arrays.toString(bs.toByteArray()));//90 - 事实证明是小端？

		bs.clear();
		//8~15
		bs.set(10 - 8);
		bs.set(11 - 8);
		bs.set(12 - 8);
		bs.set(14 - 8);
		System.out.println(Arrays.toString(bs.toByteArray()));//90 - 事实证明是小端？

	}

	@Test
	public void r(){
		long a = 40_0000_0000L; //40亿 bits
		System.out.println(a / 1024 + "KB");
		System.out.println(a / 1024 / 1024 + "MB");
		System.out.println(a / 1024 / 1024 / 1024 + "GB");

	}

	@Test
	public void test2(){
		BitSet bits1 = new BitSet(16);
		BitSet bits2 = new BitSet(16);

		// set some bits
		for(int i = 0; i < 16; i++){
			if((i % 2) == 0) bits1.set(i);
			if((i % 5) != 0) bits2.set(i);
		}
		System.out.println("Initial pattern in bits1: ");
		System.out.println(bits1);
		System.out.println("Initial pattern in bits2: ");
		System.out.println(bits2);

		// AND bits
		bits2.and(bits1);
		System.out.println("bits2 AND bits1: ");
		System.out.println(bits2);

		// OR bits
		bits2.or(bits1);
		System.out.println("bits2 OR bits1: ");
		System.out.println(bits2);

		// XOR bits
		bits2.xor(bits1);
		System.out.println("bits2 XOR bits1: ");
		System.out.println(bits2);
	}
}
