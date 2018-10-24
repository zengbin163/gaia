package test.guava.str;

import com.google.common.base.Strings;
import org.junit.Test;

/**
 * Created by zengbin on 2017/10/10.
 */
public class GuavaStringsTest {
	@Test
	public void r1(){
		String s1 = "abc 123 xxx";
		String s2 = "bcd 123 xxx";
		String prefix = Strings.commonPrefix(s1, s2); // 查找共同的prefix
		String suffix = Strings.commonSuffix(s1, s2); // 查找共同的suffix

		System.out.println("prefix: " + prefix);
		System.out.println("suffix: " + suffix);

		String s = Strings.emptyToNull(""); // not "  "
		System.out.println(s);

		boolean nullOrEmpty = Strings.isNullOrEmpty(" ");
		System.out.println(nullOrEmpty);

		// 重复，就是python的 str*80
		String repeat = Strings.repeat("*", 80); // good!
		System.out.println(repeat);

		// 后填补
		String padStart = Strings.padStart(s1, 30, '-');
		System.out.println(padStart);

		// 前填补
		String padEnd = Strings.padEnd(s1, 30, '-');
		System.out.println(padEnd);
	}
}
