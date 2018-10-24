package test.jdk.lambda.more;

import org.junit.Test;

/**
 * Created by zengbin on 2018/3/6.
 */
public class Main {

    @Test
    public void r01(){
        SomeClass someClass = new SomeClass();
        run(someClass::ma); //TODO 嘿嘿，用实例方法，代替指定的lambda方法！

        run(someClass::mb);
    }

    public static void run(FuncInterface func){
        func.hello();
    }
}
