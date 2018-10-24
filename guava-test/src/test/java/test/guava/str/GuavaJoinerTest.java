package test.guava.str;


import org.junit.Test;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;

/**
 * 这个可以考虑使用String.join，但只能拼接String[] Collection<String>。
 * ps: Joiner目前还是属于collections。
 * 
 * @author Larry
 *
 */
public class GuavaJoinerTest {

	@Test
	public void r1() {
		String[] subdirs = { "usr", "local", "lib" };
		String join = Joiner.on("-").join(subdirs);
		System.out.println(join);
	}

	@Test
	public void r2() {
		ImmutableList<Character> list = Lists.charactersOf("sldjfadsg");
		System.out.println(list);
		// String.join('-', list.toArray());//error 这是String的join的缺陷
		// Arrays.toString(arr)//功能有限
		String str = Joiner.on(',').join(list);
		System.out.println(str);

		// char[] charArray = "sldjfadsg".toCharArray(); //有限
		// byte[] bytes = "sldjfadsg".getBytes(); //有限
	}

	@Test
	public void r3() {
		int[] numbers = { 1, 2, 3, 4, 5 };
		String str = Joiner.on(";").join(Ints.asList(numbers));
		System.out.println(str);

		str = Ints.join("--", numbers);
		System.out.println(str);

		// String.join("+", numbers)
	}
	
}
