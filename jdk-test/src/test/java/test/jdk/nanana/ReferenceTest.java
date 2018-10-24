package test.jdk.nanana;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 * Created by 张少昆 on 2018/8/24.
 */
public class ReferenceTest {
    List list = new ArrayList();

    @Test
    public void r1(){
        int count = 0;
        Map map = new HashMap();
        for(int i = 0; i < 100; i++){
            if(i != 0 && i % 5 == 0){
                map.put(i, list);
                list = new ArrayList();
            }
            list.add(i);
        }
        map.put(100 - 1, list);

        System.out.println(map.size());
        System.out.println(map);
        System.out.println(map.keySet());
        TreeSet set = new TreeSet(map.keySet());
        System.out.println(set);
    }

    @Test
    public void r2(){
        System.out.println(3 % 10);
        System.out.println(0 % 10);
    }
}
