package c12;

/**
 * Created by zengbin on 2018/4/18.
 */
public interface Brand {
    void sale();
}

class Lenovo implements Brand {

    @Override
    public void sale(){
        System.out.println("sell Lenovo");
    }
}

class Dell implements Brand {

    @Override
    public void sale(){
        System.out.println("sell Dell");
    }
}