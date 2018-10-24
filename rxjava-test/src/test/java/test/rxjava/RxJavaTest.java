package test.rxjava;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import org.junit.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;


/**
 * Created by 张少昆 on 2017/12/8.
 */
public class RxJavaTest {

    @Test
    public void r1(){
        //创建一个 Observable：
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception { //订阅时候发送的事件？
                emitter.onNext("诶？有新的观察者加入了");
                emitter.onComplete();
            }
        });
        //创建一个 Observer
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {                    // 在RxJava 2.x 中，新增的Disposable可以做到切断的操作，让Observer观察者不再接收上游事件

                System.out.println("onSubscribe");
            }

            @Override
            public void onNext(String value) {
                System.out.println("onNext: "+value);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError: "+e.getLocalizedMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        };
        //建立连接
        observable.subscribe(observer);


    }
}
