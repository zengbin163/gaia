package commons.test.collections4;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.collections.OrderedMap;
import org.apache.commons.collections.map.LinkedMap;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.bidimap.TreeBidiMap;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeMap;

/**
 * http://blog.csdn.net/jiutianhe/article/details/19199573
 * <p>
 * Created by zengbin on 2017/12/11.
 */
public class Collections4Test {

    //有序map（存放顺序）
    @Test
    public void orderedMap(){
        OrderedMap map = new LinkedMap();
        System.out.println(map.put("FIVE", "5")); //返回之前的值或null
        System.out.println(map.put("SIX", "6"));
        System.out.println(map.put("SEVEN", "7"));
        System.out.println(map.put("EIGHT", "8"));
        System.out.println(map.firstKey());
        System.out.println(map.lastKey());
        System.out.println(map.nextKey("FIVE")); // TODO Gets the next key after the one specified.
        System.out.println(map.nextKey("SIX"));
    }

    // 也是有序的，但不是存储顺序，而是树的顺序（这里是字母顺序）
    @Test
    public void treeMap(){
        TreeMap<String, String> map = new TreeMap<>();
        System.out.println(map.put("FIVE", "5")); //返回之前的值或null
        System.out.println(map.put("SIX", "6"));
        System.out.println(map.put("SEVEN", "7"));
        System.out.println(map.put("EIGHT", "8"));

        System.out.println(map.firstKey());
        System.out.println(map.lastKey());
    }


    //双向操作的map -- 双向是指，可以通过key得到value，也可以通过value得到key。。。囧
    //要求：1对1，不能1对多！！
    @Test
    public void bidiMap(){
        /*
         * 通过key得到value
         * 通过value得到key
         * 将map里的key和value对调
         */

        BidiMap bidi = new TreeBidiMap();
        System.out.println(bidi.put("SIX", "6"));
        System.out.println(bidi);

        System.out.println(bidi.get("SIX"));
        System.out.println(bidi.getKey("6"));
        //       bidi.removeValue("6");  // removes the mapping
        BidiMap inverse = bidi.inverseBidiMap();  // returns a map with keys and values swapped
        System.out.println(inverse);
    }

    /**
     * CollectionUtils.retainAll
     * 得到两个集合中相同的元素
     */
    @Test
    public void collectionUtils1(){
        List<String> list1 = new ArrayList<>();
        list1.add("1");
        list1.add("2");
        list1.add("3");
        List<String> list2 = new ArrayList<>();
        list2.add("2");
        list2.add("3");
        list2.add("4");

        Collection c = CollectionUtils.retainAll(list1, list2); //返回新的集合
        System.out.println(c);

        List list = ListUtils.retainAll(list1, list2);//
        System.out.println(list);

        System.out.println(list1.retainAll(list2));// 操作原有集合
        System.out.println(list1);
    }
    @Test
    public void collectionUtils2(){
        List<String> list1 = new ArrayList<>();
        list1.add("1");
        list1.add("2");
        list1.add("3");
        List<String> list2 = new ArrayList<>();
        list2.add("2");
        list2.add("3");
        list2.add("4");

//        CollectionUtils.
    }

    @Test
    public void r1(){
        // MapUtils.invertMap()
        // SetUtils.difference()
        // MultiMapUtils.emptyIfNull()
        // MultiSetUtils.emptyMultiSet()
        // ListUtils.defaultIfNull()

    }
}
