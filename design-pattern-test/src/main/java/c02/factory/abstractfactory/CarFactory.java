package c02.factory.abstractfactory;

/**
 * 抽象工厂模式，用于构造产品族！而非用于创建单个产品。
 * <p>
 * 其实是工厂方法模式的扩展。
 * <p>
 * Created by 张少昆 on 2018/4/17.
 */
public interface CarFactory {
    Engine createEngine();

    Seat createSeat();

    Tyre createTyre();
}

class LuxuryCarFactory implements CarFactory {

    @Override
    public Engine createEngine(){
        return new LuxuryEngine();
    }

    @Override
    public Seat createSeat(){
        return new LuxurySeat();
    }

    @Override
    public Tyre createTyre(){
        return new LuxuryTyre();
    }
}

class LowCarFactory implements CarFactory {

    @Override
    public Engine createEngine(){
        return new LowEngine();
    }

    @Override
    public Seat createSeat(){
        return new LowSeat();
    }

    @Override
    public Tyre createTyre(){
        return new LowTyre();
    }
}