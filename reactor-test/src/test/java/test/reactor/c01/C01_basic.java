package test.reactor.c01;

import org.junit.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;

import java.util.stream.Stream;

/**
 * TODO 注意，Flux中的操作符，仅仅在订阅之后，发生了数据的流动时，才会真正执行！
 * <p>
 * TODO Java 8 Stream 也是类似的逻辑。
 * <p>
 * TODO Reactive中的Flux/Mono是异步处理（非阻塞）。
 * TODO 而集合(iterable)则是同步处理（阻塞式）。
 * <p>
 * Created by 张少昆 on 2018/3/5.
 */
public class C01_basic {

    @Test
    public void stream(){// 注意，Java 8 Stream 也是一样的理念！
        Stream<String> stream = Stream.of("aaa");
        boolean allMatch = stream.allMatch(e -> e.length() == 2);
        System.out.println(allMatch);

        Stream<String> stream2 = Stream.of("aaa", "bbb", "ccc", "dd");
        boolean anyMatch = stream2.anyMatch(e -> e.length() == 3);
        System.out.println(anyMatch);
    }

    // 第一步，最简单的Flux，以及操作符（执行计划，而非订阅）。同时，请参考{@link stream()}
    @Test
    public void r01(){
        Flux<String> flux = Flux.just("hi there"); // 单个 完全可以使用Mono

        // 在任何subscribe()方法之前，都是计划执行，而实际没有执行！
        Flux<String> upper = flux
                                     .log()
                                     .map(String::toUpperCase);
    }

    // 第二步，最简单的Flux，最简单的订阅 - 直接让Flux发布所有事件，而不做任何处理。
    @Test
    public void r2(){
        Flux<String> flux = Flux.just("hi there");

        // 在任何subscribe()方法之前，都是计划执行，而实际没有执行！
        flux
                .log()
                .map(String::toUpperCase)
                .subscribe();// 注意，这里没有指定任何消费行为，所以，你懂的！
    }

    // 第三步，最简单的Flux，最简单的订阅 - 直接让Flux发布所有事件，而不做任何处理。
    @Test
    public void r3(){
        Flux<String> flux = Flux.just("hi there", "nihao", "woyehao");

        // 在任何subscribe()方法之前，都是计划执行，而实际没有执行！
        // TODO 如果想自己监视事件，可以使用Flux的doOn*()
        flux
                .log()
                .map(String::toUpperCase)
                .doOnSubscribe(e -> {
                    System.out.println("do on subscribe: " + e);
                })
                .doOnRequest(e -> {
                    System.out.println("don on request: " + e);
                })
                .doOnEach(signal -> {// next, complete
                    SignalType type = signal.getType();
                    System.out.println("do on each: type=" + type);
                })
                .doOnNext(e -> {
                    System.out.println("do on next: " + e);
                })
                .doOnComplete(() -> {
                    System.out.println("do on complete: boom..done!");
                })
                .subscribe();// 注意，这里没有指定任何消费行为，所以，你懂的！
    }

    // 让Pub最多发布2个items！（一）
    @Test
    public void r4(){
        Flux<String> flux = Flux.just("hi there", "nihao", "woyehao");

        flux
                .log()
                .map(String::toUpperCase)
                .doOnComplete(() -> {
                    System.out.println("done???");
                })
                .subscribe(new Subscriber<String>() { //TODO 参数是Subscriber，其他的都是表象

                    @Override
                    public void onSubscribe(Subscription s){
                        s.request(2);//TODO en???  请求了俩，然后，没了？？也不完成，也不继续？？也没有cancel！！！
                    }

                    @Override
                    public void onNext(String data){
                        System.out.println("------->> " + data);
                    }

                    @Override
                    public void onError(Throwable t){
                        t.printStackTrace();
                    }

                    @Override
                    public void onComplete(){
                        System.out.println("完成啦");
                    }
                });
    }

    // 让Pub一次最多发布2个items！（二）
    @Test
    public void r5(){
        Flux<String> flux = Flux.just("hi there", "nihao", "woyehao", "haha", "hehe", "meme", "da");

        flux
                .log()
                .map(String::toUpperCase)
                .doOnComplete(() -> {
                    System.out.println("done???");
                })
                .subscribe(new Subscriber<String>() { //TODO 参数是Subscriber，其他的都是表象
                    int count = 2;
                    Subscription subscription;

                    @Override
                    public void onSubscribe(Subscription s){
                        s.request(2);//TODO en???  请求了俩，然后，没了？？也不完成，也不继续？？

                        this.subscription = s;
                    }

                    @Override
                    public void onNext(String data){
                        System.out.println("------->> " + data);
                        count++;
                        if(count >= 2){
                            count = 0;
                            subscription.request(2);
                        }
                    }

                    @Override
                    public void onError(Throwable t){
                        t.printStackTrace();
                    }

                    @Override
                    public void onComplete(){
                        System.out.println("完成啦");
                    }
                });
    }

    @Test
    public void r6(){
        Flux<String> flux = Flux.just("hi there", "nihao", "woyehao", "haha", "hehe", "meme", "da");

        flux
                .log()
                .map(String::toUpperCase)
                .limitRequest(2) //TODO 卧槽，就取了2个，然后cancel()了
                .subscribe(e -> {
                    System.out.println("--------->> " + e);
                });
    }

    @Test
    public void r7(){
        Flux<String> flux = Flux.just("hi there", "nihao", "woyehao", "haha", "hehe", "meme", "da");

        flux
                .log()
                .map(String::toUpperCase)
                .take(2) //TODO 卧槽，就取了2个，然后cancel了
                .subscribe(e -> {
                    System.out.println("--------->> " + e);
                });
    }

    @Test
    public void r8(){
        Flux<String> flux = Flux.just("hi there", "nihao", "woyehao", "haha", "hehe", "meme", "da");

        flux
                .log()
                .map(String::toUpperCase)
                .takeLast(2) //TODO 卧槽，全部发送了，但就消费了最后2个。完成
                .subscribe(e -> {
                    System.out.println("--------->> " + e);
                });
    }
    @Test
    public void r9(){
        Flux<String> flux = Flux.just("hi there", "nihao", "woyehao", "haha", "hehe", "meme", "da");

        flux
                .log()
                .map(String::toUpperCase)
                // .buffer()//TODO Subscriber接收到的内容都组装进List，完成后一起发出。
                // .cache()//TODO 让Flux缓存起来，供其他Subscriber使用！
                .skip(2) //TODO 不能和buffer一起用，因为buffer后就成一个了！！！
                // .takeLast(2) //TODO 卧槽，全部发送了，但就消费了最后2个。完成
                .subscribe(e -> {
                    System.out.println("--------->> " + e);
                });
    }


    @Test
    public void r12(){
        Flux<String> more = Flux.just("aaa", "bbb", "ccc");
        Mono<Boolean> mono = more.all(e -> e.length() == 3);// 判断是否都符合，以此返回Mono<Boolean>

    }


}
