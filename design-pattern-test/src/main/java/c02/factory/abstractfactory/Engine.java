package c02.factory.abstractfactory;

/**
 * Created by zengbin on 2018/4/17.
 */
public interface Engine {
    void run();
    void start();
}

class LuxuryEngine implements  Engine{

    @Override
    public void run(){
        System.out.println("转的快");
    }

    @Override
    public void start(){
        System.out.println("启动快");
    }
}
class LowEngine implements  Engine{

    @Override
    public void run(){
        System.out.println("转的慢");
    }

    @Override
    public void start(){
        System.out.println("启动慢");
    }
}
