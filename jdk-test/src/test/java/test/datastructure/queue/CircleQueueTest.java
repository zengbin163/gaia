package test.datastructure.queue;

import org.junit.Test;

import java.util.Iterator;

/**
 * Created by 张少昆 on 2018/3/6.
 */
public class CircleQueueTest {
    @Test
    public void r1(){
        CircleQueue queue = new CircleQueue(10);
        for(int i = 0; i < 10; i++){
            queue.push(i);
        }

        System.out.println(queue);
    }

    @Test
    public void r2(){
        CircleQueue queue = new CircleQueue(10);
        for(int i = 0; i < 10; i++){
            queue.push(i);
        }
        //先弹出
        System.out.println(queue.pop());
        System.out.println(queue.pop());
        System.out.println(queue.pop());
        System.out.println(queue.pop());
        //再压入
        queue.push(10);
        queue.push(11);
        queue.push(12);
        queue.push(13);
        // queue.push(14); //异常

        System.out.println(queue);

        //再试试迭代器
        Iterator<Integer> iterator = queue.iterator();
        while(iterator.hasNext()){
            System.out.println("----- " + iterator.next());
        }
    }
}
