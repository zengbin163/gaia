package test.jdk.generic;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by zengbin on 2017/10/27.
 */
public class GenericTest {

	@Test
	public void r1(){
		Collection<? extends Runnable> list = Collections.emptyList();
		Runnable runnable = () -> {
		};
//		list.add(runnable); // ERROR 这里无法填入Runnable类型！
	}

	@Test
	public void r2(){
//		new ArrayList< extends Comparable>()
	}
}
