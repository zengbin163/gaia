package jdk.load;

/**
 * 加载字节码：是完全加载后才执行静态代码块 - 因为静态代码块可能用到静态变量（包括静态实例变量）。
 * 如果包含静态实例变量，那加载的中途只能先去加载需要的内容了 - 此时会导致实例代码块在静态代码块之前执行。
 * 还需要注意：实例代码块的执行，在构造方法之前！
 * <p>
 * TODO 总之，只需要考虑到"依赖满足"即可 - 是各种可能情况下都符合的依赖满足！
 * TODO http://www.cnblogs.com/larryzeal/p/8135628.html
 * <p>
 * Created by zengbin on 2017/12/28.
 */
public class ByteCodeTest {
    private static int k = 0;
    public static ByteCodeTest t1 = new ByteCodeTest("t1");
    public static ByteCodeTest t2 = new ByteCodeTest("t2");
    public static int i = 1;
    private int a = 0;
    public static int j = print("j");
    public static int n = 99;

    static{
        print("静态代码块");
    }

    {
        print("代码块");
    }

    public ByteCodeTest(String str){
        System.out.println("构造器：" + (++k) + ":" + str + " i=" + i + " n=" + n);
        ++i;
        ++n;
    }

    public static int print(String str){
        System.out.println((++k) + ":" + str + " i=" + i + " n=" + n);
        ++n;
        return ++i;
    }

    public static void main(String[] args){
        //直接运行，看看加载顺序
    }
}
