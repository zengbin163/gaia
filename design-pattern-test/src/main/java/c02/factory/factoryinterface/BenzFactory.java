package c02.factory.factoryinterface;

import c02.service.Car;
import c02.service.impl.Benz;

/**
 * Created by 张少昆 on 2018/4/17.
 */
public class BenzFactory implements CarFactory {
    @Override
    public Car createCar(){
        return new Benz();
    }
}
