package test.guava.str;

import java.util.List;

import org.junit.Test;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;

public class GuavaSplitterTest {
	private String target = "11 ...emm, when I (小天) was born on 2003.9.9 （农历）, my Dad (@Dad) was so happy!11  ";

	@Test
	public void r1() {
		Splitter splitter = Splitter.on(',');
		Iterable<String> iterable = splitter.split(target);
		iterable.forEach(System.out::println);
	}

	@Test
	public void r2() {
		CharMatcher matcher = CharMatcher.digit();
		Iterable<String> iterable = Splitter.on(matcher).split(target);
		iterable.forEach(System.out::println);

		List<String> list = Splitter.on(matcher).splitToList(target);
		list.forEach(System.out::println);
	}

	@Test
	public void r3() {
		Iterable<String> iterable= Splitter.on(",").omitEmptyStrings().trimResults().split(target);
		iterable.forEach(System.out::println);
	}
}
