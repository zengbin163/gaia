package c02.factory.factoryinterface;

import c02.service.Car;
import c02.service.impl.Byd;

/**
 * Created by 张少昆 on 2018/4/17.
 */
public class ByzFactory implements CarFactory {
    @Override
    public Car createCar(){
        return new Byd();
    }
}
