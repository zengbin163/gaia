package c02.service.impl;

import c02.service.Car;

/**
 * Created by 张少昆 on 2018/4/17.
 */
public class Benz implements Car {

    @Override
    public void run(){
        System.out.println("benz is running..");
    }
}