package c02.factory.simple;

import c02.service.Car;
import c02.service.impl.Benz;
import c02.service.impl.Byd;

/**
 * 简单工厂模式
 * <p>
 * 缺点：后续更新时，无法遵守开闭原则。
 * <p>
 * Created by 张少昆 on 2018/4/17.
 */
public class SimpleFactory {

    public static Car createCar(String type){
        if("byd".equals(type)){
            return new Byd();
        }
        if("benz".equals(type)){
            return new Benz();
        }
        return null;
    }
}
