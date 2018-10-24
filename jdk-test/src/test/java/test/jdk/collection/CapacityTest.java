package test.jdk.collection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

/**
 * 测试下容量增长问题。 貌似只能debug模式查看了。
 * 
 * @author Administrator
 *
 */
public class CapacityTest {

    // 先看看map
    @Test
    public void r1() {
        Map<Integer, String> map = new HashMap<>(5, 1);// 低于8无效？
        for (int i = 0; i < 10; ++i) {
            map.put(i, null);
            System.out.println(map);
        }
    }

    @Test
    public void r2() {
        List<Integer> arrayList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            arrayList.add(i);
        }
    }
}
