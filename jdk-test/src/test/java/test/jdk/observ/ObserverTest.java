package test.jdk.observ;

import org.junit.Test;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by zengbin on 2017/12/8.
 */
public class ObserverTest {

    @Test
    public void r1(){
        //观察者需要继承Observer接口 -- 这里只是模拟
        Observer observer = new Observer() {
            @Override
            public void update(Observable observable, Object arg){
                System.out.println("收到了来自[" + observable + "]的通知，参数是：" + arg);
            }
        };
        //被观察者需要注册观察者，并在事件发生时通知观察者
        Observable observable = new Observable();
        observable.addObserver(observer);
        if(observable.hasChanged()){ //貌似不是这么用啊，需要先设置change
            observable.notifyObservers();
        }
    }
}
