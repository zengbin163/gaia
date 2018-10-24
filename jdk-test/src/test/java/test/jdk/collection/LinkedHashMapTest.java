package test.jdk.collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashMap;

/**
 * TODO LinkedHashMap按照插入顺序保持key。
 * TODO 比HashMap或者HashTable有序，比TreeMap高效（因为不会有重复规划） -- 当然，用途不同。
 * <b>LinkedHashSet同理</b>
 * <p>
 * Created by zengbin on 2018/2/9.
 */
public class LinkedHashMapTest {
    private LinkedHashMap<Integer, String> linkedHashMap = null;

    @Before
    public void init(){
        linkedHashMap = new LinkedHashMap<>();

        linkedHashMap.put(1, "aaa");
        linkedHashMap.put(10, "bbb");
        linkedHashMap.put(7, "ccc");
        linkedHashMap.put(9, "ddd");
        linkedHashMap.put(3, "eee");

        linkedHashMap.put(1, "aaa");
    }

    @After
    public void after(){
        System.out.println(linkedHashMap);
    }

    @Test
    public void r1(){
    }

    @Test
    public void merge(){
        //merge，如果key有非空值，则使用第三个函数的计算结果。否则使用第二个值。
        linkedHashMap.merge(1, "777", (a, b) -> {
            System.out.println("a: " + a);
            System.out.println("b: " + b);
            return String.valueOf(a) + String.valueOf(b);
        });

        linkedHashMap.put(null, null); //OK  支持
    }
}
