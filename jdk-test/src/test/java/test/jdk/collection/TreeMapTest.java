package test.jdk.collection;

import org.junit.After;
import org.junit.Test;

import java.util.Comparator;
import java.util.TreeMap;

/**
 * TODO TreeMap 是有序的，但这个有序是针对key来说的。所以，还可以在构建map的时候指定比较器！
 * <p>
 * Created by 张少昆 on 2018/2/9.
 */
public class TreeMapTest {
    TreeMap<Integer, String> treeMap = null;

    @After
    public void after() {
        treeMap.put(1, "aaa");
        treeMap.put(10, "bbb");
        treeMap.put(7, "ccc");
        treeMap.put(9, "ddd");
        treeMap.put(3, "eee");

        treeMap.put(1, "aaa");

        System.out.println(treeMap);
    }

    // 先看看输出顺序
    @Test
    public void r1() {
        treeMap = new TreeMap<>();
    }

    // 再尝试自定义输出顺序（确切的说是自定义插入位置）
    @Test
    public void r2() {
        // TODO 通过自定义比较器，来定义元素的插入位置
        treeMap = new TreeMap<>(Comparator.comparing(String::valueOf));
    }
}
