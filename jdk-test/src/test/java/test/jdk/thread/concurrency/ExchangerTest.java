package test.jdk.thread.concurrency;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <b>两个</b>线程之间交换数据 -- 可自行扩展至多个线程。
 * <p>
 * Created by zengbin on 2017/9/23.
 */
public class ExchangerTest {
    public static void main(String[] args){

        Exchanger<String> exchanger = new Exchanger<>();

        ExecutorService executorService = Executors.newCachedThreadPool();
        final String s1 = "货物";
        final String s2 = "钱财";
        //卖家
        executorService.submit(() -> {
            try{
                Thread.sleep(1000 * 5);
                String money = exchanger.exchange(s1);
                System.out.println("卖家用[" + s1 + "]交换回[" + money + "]");
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        });
        //买家
        executorService.submit(() -> {
            try{
                String person = exchanger.exchange(s2);
                System.out.println("买家用[" + s2 + "]交换回[" + person + "]");
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        });

        executorService.shutdown();
    }


}

class Meta {}

class Common {
    public static final AtomicReference<Meta> REF = new AtomicReference();
}

class A {

    public void run(){
        Meta meta = getMeta();
        Common.REF.set(meta);
    }

    public Meta getMeta(){
        //...
        return null;
    }
}

class B {

    public void run(){
        Meta meta;
        do{
            meta = Common.REF.get();
            processMeta();
        } while(Common.REF.compareAndSet(meta, null)); //是否循环、是否置空，看你的需要
    }

    public void processMeta(){
        //...
    }
}