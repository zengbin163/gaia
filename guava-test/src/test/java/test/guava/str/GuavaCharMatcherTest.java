package test.guava.str;

import org.junit.Test;

import com.google.common.base.CharMatcher;

public class GuavaCharMatcherTest {
	private String target = "11 ...emm, when I (小天) was born on 2003.9.9 （农历）, my Dad (@Dad) was so happy!11  ";

	@Test
	public void r1() {
		// 这里仅是构建匹配器，类似正则表达式
		System.out.println(CharMatcher.is('1'));
		System.out.println(CharMatcher.is('a'));
		System.out.println(CharMatcher.isNot('a'));
		CharMatcher.noneOf("abc").negate();

		CharMatcher matcher = CharMatcher.inRange('a', 'z').or(CharMatcher.inRange('A', 'Z'));
		System.out.println(matcher.matches('e'));
		System.out.println(matcher.matches('1'));

	}

	@Test
	public void r2() {
		// 仅保留数字
		String nos = CharMatcher.digit().retainFrom("some text 89983 and more 123");
		System.out.println(nos);// 89983123

		// 仅保留ascii字符，可以直接ascii().retainFrom()，下面只是练习用
		String string = CharMatcher.ascii().negate().removeFrom(target);
		System.out.println(string);
		string = CharMatcher.ascii().retainFrom(target);
		System.out.println(string);
	}

	@Test
	public void r3() {
		// 匹配
		CharMatcher whitespace = CharMatcher.whitespace();
		boolean matches = whitespace.matches('\t');
		System.out.println(matches);
		matches = whitespace.matches('\n');
		System.out.println(matches);
		matches = whitespace.matches(' ');
		System.out.println(matches);
		matches = whitespace.matches('\b');
		System.out.println(matches);

		// 匹配
		CharMatcher invisible = CharMatcher.invisible();
		boolean re = invisible.matches('a');
		System.out.println(re);
		re = invisible.matches(' ');
		System.out.println(re);
	}
	
	@Test
	public void r4() {
		//多个匹配
		CharMatcher matcher = CharMatcher.digit().or(CharMatcher.whitespace());
		String collapseFrom = matcher.collapseFrom(target, '〇');
		System.out.println(collapseFrom);
		
		//计数
		int countIn = matcher.countIn(target);
		System.out.println(countIn);
		
		//从两端去除匹配的内容
		String trimFrom = matcher.trimFrom(target);
		System.out.println(trimFrom);
		
		//trim之后再collapse
		String trimAndCollapseFrom = matcher.trimAndCollapseFrom(target, '-');
		System.out.println(trimAndCollapseFrom);
		
		//只trim前面的
		String trimLeadingFrom = matcher.trimLeadingFrom(target);
		System.out.println(trimLeadingFrom);
	}
}
