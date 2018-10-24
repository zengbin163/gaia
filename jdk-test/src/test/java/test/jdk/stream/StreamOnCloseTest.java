package test.jdk.stream;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * Created by zengbin on 2018/5/3.
 */
public class StreamOnCloseTest {

    @Test
    public void generate0(){
        IntStream generate = IntStream.generate(() -> {
            Random random = new Random();
            System.out.println("---");
            return random.nextInt();
        });

        generate.limit(10).forEach(System.out::println);
    }

    @Test
    public void generate1(){
        Random random = new Random();
        IntStream generate = IntStream.generate(random::nextInt);

        generate.limit(10).forEach(System.out::println);
    }

    @Test
    public void map(){ //TODO 流操作的每一步，都返回新的对象 - onClose方法除外。
        IntStream range = IntStream.range(1, 10);
        IntStream map = range.map(e -> {
            System.out.println("---"); //事实证明，这一步没用调用lambda
            return e * 2;
        });

        try{
            Thread.sleep(1000L * 3);
        } catch(InterruptedException e){
            e.printStackTrace();
        }

//        System.out.println(range.toArray());//TODO ERROR 不对
        System.out.println(Arrays.toString(map.toArray()));
    }

    @Test
    public void peek1(){
        IntStream stream = IntStream.rangeClosed(1, 10);

        IntStream stream1 = stream.onClose(() -> {
            System.out.println("流结束啦-1！");
        });
        IntStream stream2 = stream1.onClose(() -> {
            System.out.println("流结束啦-2！");
        });
        IntStream stream3 = stream2.onClose(() -> {
            System.out.println("流结束啦-3！");
        });

        //下面三行都是true
        System.out.println(stream == stream1);
        System.out.println(stream1 == stream2);
        System.out.println(stream2 == stream3);

        IntStream stream4 = stream3.map(e -> e * 2);
        IntStream stream5 = stream4.peek(e -> System.out.println("Mapped value: " + e));//TODO peek主要用于调试。但此处不会执行，因为不是action，是transform！


        //下面两行都是false
        System.out.println(stream3 == stream4);
        System.out.println(stream4 == stream5);

        stream5.sum();//TODO 这才是action，会导致之前的统统执行！注意本行所在的位置，跟结果对比，也是一种验证。

        stream3.close();
        stream5.close();
    }

    @Test
    public void peek2(){
        IntStream stream = IntStream.rangeClosed(1, 10);

        stream.onClose(() -> {
            System.out.println("流结束啦-1！");
        }).onClose(() -> {
            System.out.println("流结束啦-2！");
        }).onClose(() -> {
            System.out.println("流结束啦-3！");
        }).map(e -> e * 2)
                .peek(e -> System.out.println("Mapped value: " + e)).sum(); //TODO peek主要用于调试，一直到sum才会执行！！
//                .close();
    }

    @Test
    public void r3(){
        IntStream stream = IntStream.rangeClosed(1, 100);

//        ArrayList<Integer> list = new ArrayList<>();
        MyList list = new MyList();

        MyList collect = stream.onClose(() -> {
            System.out.println("流结束啦-1！");
        }).onClose(() -> {
            System.out.println("流结束啦-2！");
        }).onClose(() -> {
            System.out.println("流结束啦-3！");
//        }).collect(ArrayList::new, ArrayList::add, ArrayList::addAll)
//        }).collect(() -> list, ArrayList::add, ArrayList::addAll);
        }).collect(() -> list, MyList::add, MyList::addAll);


//        collect.forEach(System.out::println);//容器，计算，添加
        System.out.println(collect);
    }

    /**
     * 就是为了看看stream.collect中的行为。
     */
    static class MyList {
        private List list = new ArrayList<Integer>();

        public boolean add(int e){
            System.out.println("----add----");
            return list.add(e);
        }

        public boolean addAll(MyList list){
            System.out.println("----addAll----");
            return this.list.addAll(list.list);
        }

        @Override
        public String toString(){
            return list.toString();
        }
    }
}
