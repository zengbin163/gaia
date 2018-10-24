package test.jdk.thread;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * ThreadLocal，就是当前线程存储的一个变量。
 * 
 * @author Larry
 *
 */
public class ThreadLocalTest {
	private ThreadLocal<String> tl = new ThreadLocal<>();
	private List<String> list = new ArrayList<>();

	@Before
	public void setUp() {
		tl.set("init"); //嗯？
		for (int i = 0; i != 10; ++i) {
			list.add("value " + i);
		}
	}

	@Test
	public void r1() {
		for (String str : list) {
			System.out.println(tl.get());
			tl.set(str);
		}
	}
}
