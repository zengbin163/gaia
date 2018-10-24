package test.jdk.thread.multithread.join;

/**
 * TODO 为什么lock和notify要做Object类中？
 * TODO 因为wait之后，需要使用同一个obj的notify才能唤醒！而且，使用同一个锁的代码往往是在不同的类中！
 * TODO 因此，要么加一个类专门用于wait/notify，要么放到Object，后者使得任何类都可以作为锁！
 * <p>
 * Created by zengbin on 2018/3/17.
 */
public class  SynchronizedDemo {
}
