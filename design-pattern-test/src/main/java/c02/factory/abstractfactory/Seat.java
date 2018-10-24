package c02.factory.abstractfactory;

/**
 * Created by 张少昆 on 2018/4/17.
 */
public interface Seat {
    void message();
}

class LuxurySeat implements Seat{

    @Override
    public void message(){
        System.out.println("能按摩..");
    }
}

class LowSeat implements Seat{

    @Override
    public void message(){
        System.out.println("就是个座..");
    }
}
