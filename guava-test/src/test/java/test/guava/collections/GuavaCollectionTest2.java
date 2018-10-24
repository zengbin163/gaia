package test.guava.collections;

import java.util.HashSet;

import org.junit.Test;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;

/**
 * 对两个Set的取交集、并集和差集
 * 
 * @author Larry
 *
 */
public class GuavaCollectionTest2 {

	// 返回视图
	@Test
	public void r1() {
		HashSet<Integer> setA = Sets.newHashSet(1, 2, 3, 4, 5);
		HashSet<Integer> setB = Sets.newHashSet(4, 5, 6, 7, 8);

		// 并集 - 注意，仅仅是视图，没有实体
		SetView<Integer> union = Sets.union(setA, setB);
		System.out.println("--------union:");
		union.forEach(System.out::println);

		// 差集 - 注意，仅仅是视图，没有实体
		SetView<Integer> difference = Sets.difference(setA, setB);
		System.out.println("--------difference:");
		difference.forEach(System.out::println);

		// 交集 - 注意，仅仅是视图，没有实体
		SetView<Integer> intersection = Sets.intersection(setA, setB);
		System.out.println("--------intersection:");
		intersection.forEach(System.out::println);
	}

	// 返回copy
	@Test
	public void r2() {
		HashSet<Integer> setA = Sets.newHashSet(1, 2, 3, 4, 5);
		HashSet<Integer> setB = Sets.newHashSet(4, 5, 6, 7, 8);

		// 交集 - 注意，仅仅是视图，没有实体
		SetView<Integer> intersection = Sets.intersection(setA, setB);
		System.out.println("--------intersection:");
		System.out.println(intersection);

		// 这才是copy -- 貌似不是copy吧
		ImmutableSet<Integer> copy = intersection.immutableCopy();
		System.out.println(copy);
	}

	// 返回copy
	@Test
	public void r3() {
		HashSet<String> setA = Sets.newHashSet("1", "2", "3", "4", "5");
		HashSet<String> setB = Sets.newHashSet("4", "5", "6", "7", "8");

		// 交集 - 注意，仅仅是视图，没有实体
		SetView<String> intersection = Sets.intersection(setA, setB);
		System.out.println("--------intersection:");
		System.out.println(intersection);
		intersection.forEach(e -> {
			System.out.println(e.hashCode());
		});

		// 这才是copy -- 貌似不是copy吧
		ImmutableSet<String> copy = intersection.immutableCopy();
		System.out.println(copy);
		copy.forEach(e -> {
			System.out.println(e.hashCode()); // 和上面视图中的地址相同啊
		});
	}
}
