package test.jdk.generic;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

/**
 * Created by 张少昆 on 2018/4/6.
 */
public class GenericMethodTest {
    @Test
    public void r1(){
        HashMap<String, Long> map = new HashMap<>();
        char c = 'a';
        Random random = new Random(1000);

        for(int i = 0; i < 10; i++){
            map.put("" + c + c + c, random.nextLong());
            c++;
        }

        System.out.println(map);

        map.entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue()).forEach(System.out::println);
    }

    //TODO 示意用的泛型方法
    public static <T> void print(T t){
        System.out.println(Objects.toString(t));
    }

    //TODO 结论，泛型本身就是用于限定对象类型；方法的泛型也是一样！
    //TODO 不同的是，类的泛型在创建对象时有用，而方法通常不需要创建对象，所以大多时候直接传入参数（自带类型）即可。
    @Test
    public void r2(){
        GenericMethodTest.<String>print("hehe"); //注意这种写法 GenericMethodTest.<String>print("hehe");
        //奇怪的是，可以省略！那再测试下看看
        Object object = new SomeClass();
        GenericMethodTest.<Object>print(object); //注意这种写法 GenericMethodTest.<String>print("hehe");
        GenericMethodTest.<SomeClass>print((SomeClass) object); //注意这种写法 GenericMethodTest.<String>print("hehe");
    }

    static class SomeClass {
        private String name;
        private Integer id;

        @Override
        public String toString(){
            return "SomeClass{" +
                           "name='" + name + '\'' +
                           ", id=" + id +
                           '}';
        }
    }
}
