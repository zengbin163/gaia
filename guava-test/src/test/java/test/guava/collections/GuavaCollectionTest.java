package test.guava.collections;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.io.Files;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Created by zengbin on 2017/9/7.
 */
public class GuavaCollectionTest {

	/**
	 * 便捷的构造器
	 */
	@Test
	public void r1() {
		HashMap<String, Map<Long, List<String>>> map = Maps.newHashMap();
		ArrayList<String> list = Lists.newArrayList();
		HashSet<String> set = Sets.newHashSet();
		map.put("a", null);
		list.add("b");
		set.add("c");

		ImmutableList<Character> immutableList = ImmutableList.of('a', 'b', 'c', 'd', 'e');
		immutableList.forEach(System.out::println);
	}

	@Test
	public void r() throws IOException {
		URL resource = getClass().getResource(".");
		System.out.println(resource);
		System.out.println(resource.getContent());
		System.out.println(resource.getFile());
		System.out.println(new File(resource.getFile()).getName());

	}

	/**
	 * 有个坑，@Test，其运行环境，可能不是对应的class。。。
	 */
	@Test
	public void r2() {
		// File file = new File(getClass().getResource("./test.txt").getFile());
		File file = new File("d:/test.txt");
		try {
			List<String> list = Files.readLines(file, Charset.forName("utf-8"));
			list.forEach(System.out::println);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void r3() {
		int compare = Ints.compare(3, 5);// good
		System.out.println(compare);

		List<Integer> list = Ints.asList(3, 5, 9, 7, 2);
		list.forEach(System.out::println);

		System.out.println(Ints.BYTES);// 4
		System.out.println(Ints.MAX_POWER_OF_TWO);// 1073741824

		Integer tryParse = Ints.tryParse("abc");
		System.out.println(tryParse);// 不成功时，不会抛出异常，而是返回null

		Integer tryParse2 = Ints.tryParse("abc", 16);
		System.out.println(tryParse2);

		System.out.println(tryParse2.byteValue());
	}

	@Test
	public void r4() {
		System.out.println(Longs.BYTES);
		System.out.println(Longs.MAX_POWER_OF_TWO);

		List<Long> asList = Longs.asList(1, 9, 7, 6, 13);
		asList.forEach(System.out::println);

		long constrainToRange = Longs.constrainToRange(3, 7, 9);// 返回最近的数
		System.out.println(constrainToRange);

		long max = Longs.max(Longs.toArray(asList));
		System.out.println(max);

		byte[] byteArray = Longs.toByteArray(357);
		String string = Arrays.toString(byteArray);
		System.out.println(string);

		System.out.println(Long.valueOf(357).byteValue());
	}
}
