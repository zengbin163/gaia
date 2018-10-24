package test.jdk.stream;

import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * FIXME 卧槽卧槽卧槽，Stream无法复用，动不动就已经被操作或者被关闭！！！
 * 还是用List吧！
 *
 * Created by 张少昆 on 2017/10/30.
 */

public class QSort {

	@Test
	public void r1(){
		Integer[] arr = {1, 2, 5, 3, 7, 3, 5, 9, 3, 7, 11, 99, 77, 33, 44, 77};
		Stream re = sort(Stream.of(arr));

		System.out.println(re);
	}

	public static Stream<Integer> sort(Stream<Integer> stream){
		List<Integer> list = stream.collect(Collectors.toList());
		if(list.size() < 2){
			return stream;
		}

		Stream left = sort(list.stream().filter(e -> {
			return e.compareTo(list.stream().findFirst().get()) > 0;
		}));
		Stream mid = list.stream().filter(e -> {
			return e.compareTo(list.stream().findFirst().get()) == 0;
		});
		Stream right = list.stream().filter(e -> {
			return e.compareTo(list.stream().findFirst().get()) < 0;
		});

		return Stream.concat(Stream.concat(left, mid), right);
	}
}