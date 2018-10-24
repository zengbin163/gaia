package test.reactor.c01;

import org.junit.Test;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

/**
 * TODO 需要练习下CompletableFuture的用法，然后再看看Mono.fromFuture
 * <p>
 * Created by zengbin on 2018/3/14.
 */
public class C02_more {
    @Test
    public void r1(){
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(() -> {
            System.out.println("tid: " + Thread.currentThread().getId() + " start");
            try{
                Thread.sleep(1000 * 10L);
            } catch(InterruptedException e){
                e.printStackTrace();
            }
            System.out.println("tid: " + Thread.currentThread().getId() + " end");
        });

        Mono.fromFuture(voidCompletableFuture).log().subscribe(System.out::println);
    }
}
