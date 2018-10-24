package test.jdk;

import java.io.Serializable;

import org.junit.Test;

//确定条件表达式结果类型的规则的核心是以下3点：
//1、如果表达式1和表达式2操作数具有相同的类型，那么它就是条件表达式的类型。
//2、如果一个表达式的类型是byte、short、char类型的，而另外一个是int类型的常量表达式，且它的值可以用类型byte、short、char三者之一表示的，那么条件表达式的类型就是三者之一
//3、否则，将对操作数类型进行二进制数字提升，而条件表达式的类型就是第二个和第三个操作数被提升之后的类型 
public class TriOperatorTest {

	@Test
	public void r1() {
		int a=1;
		int b=2;
		Serializable s = a*3/2 > b ? "a" : 7;
		System.out.println(s.getClass().getName());
	}
}
