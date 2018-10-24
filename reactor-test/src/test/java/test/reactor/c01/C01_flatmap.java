package test.reactor.c01;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * TODO 先看C01_thread
 * <p>
 * Created by zengbin on 2018/3/5.
 */
public class C01_flatmap {

    @Test
    public void r1(){
        Flux<String> flux = Flux.just("hello", "hi", "hey", "hue", "hoho", "haha");

        flux
                .log()
                .flatMap(value -> Mono.just(value.toUpperCase())
                                          .subscribeOn(Schedulers.parallel()),
                         3)// 3是个很有意思的测试，会拆分成1+2  （因为双核）
                .subscribe(e -> {
                    System.out.println("------->> " + e);
                });
    }
}
