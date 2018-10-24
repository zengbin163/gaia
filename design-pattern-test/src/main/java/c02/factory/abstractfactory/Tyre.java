package c02.factory.abstractfactory;

/**
 * Created by 张少昆 on 2018/4/17.
 */
public interface Tyre {
    void fasten();
}

class LuxuryTyre implements Tyre {
    @Override
    public void fasten(){
        System.out.println("抓地好");
    }
}

class LowTyre implements Tyre {
    @Override
    public void fasten(){
        System.out.println("抓地一般");
    }
}
