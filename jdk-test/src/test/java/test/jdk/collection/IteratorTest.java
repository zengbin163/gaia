package test.jdk.collection;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by 张少昆 on 2018/4/21.
 */
public class IteratorTest {

    //TODO 尼玛，集合返回的迭代器Iterator，居然不是单例的！
    @Test
    public void r1(){
        ArrayList<Integer> list = new ArrayList<>();
        Iterator<Integer> i1 = list.iterator();
        Iterator<Integer> i2 = list.iterator();

        System.out.println(i1 == i2);
    }
}
