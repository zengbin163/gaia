package test.jdk.lambda;

import java.util.ArrayList;
import java.util.List;

public class ForEach {
	// 内部集合
	private List list = new ArrayList();

	// 函数式接口，关键
	@FunctionalInterface
	private interface Func {
		void job(Object obj);
	}

	// 使用函数式接口的方法，关键的关键
	void forEach(Func func) {
		for (Object obj : list) {
			func.job(obj);
		}
	}

	public static void main(String[] args) {
		ForEach obj = new ForEach();
		for (int i = 0; i != 10; ++i) {
			obj.list.add(i);
		}

		// obj.forEach((e) -> {
		// System.out.println(e);
		// });
		obj.forEach(System.out::println);
	}

}
