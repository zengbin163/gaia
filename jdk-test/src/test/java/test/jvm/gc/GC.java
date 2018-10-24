package test.jvm.gc;

/**
 * TODO 注意JDK的版本！包括编译版本和运行版本。
 * <p>
 * Created by 张少昆 on 2018/3/28.
 */
public class GC {
    public static final int _1MB = 1024 * 1024;

    /**
     * VM args: -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
     * <p>
     * -Xmn  young-generation
     */
    public static void testAllocation(){
        byte[] a1, a2, a3, a4;
        a1 = new byte[2 * _1MB];
        a2 = new byte[2 * _1MB];
        a3 = new byte[2 * _1MB];
        a4 = new byte[4 * _1MB]; //出现一次minor gc。
    }

    /**
     * VM args: -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:PretenureSizeThreshold=3145728
     * <p>
     * 注意：-XX:PretenureThreshold仅仅对ParNew和Serial有效。
     */
    public static void testPretenureThreshold(){
        byte[] a;
        a = new byte[4 * _1MB];//应该会直接分配在老年代中
    }

    public static void main(String[] args){
//        testAllocation();
        testPretenureThreshold();
    }
}
