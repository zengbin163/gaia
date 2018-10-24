package test.mapdb;

import org.junit.Test;
import org.mapdb.DB;
import org.mapdb.DBMaker;

/**
 * Created by zengbin on 2018/3/2.
 */
public class MapdbTest {

    @Test
    public void r1(){
        DB heap = DBMaker.heapDB().make();//不需要序列化，会被GC影响
        DB mem = DBMaker.memoryDB().make();//byte[]，不会被GC影响
        DB direct = DBMaker.memoryDirectDB().make();//堆外内存

        DB.AtomicBooleanMaker test01 = heap.atomicBoolean("test01");
        test01.create();//如果不存在，则创建新集合；否则抛出异常
        test01.open(); //打开现有集合，或者如果已存在则抛出异常（什么鬼？？）
    }
}
