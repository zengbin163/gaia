package test.rpc.server;

import test.rpc.server.service.HelloServiceImpl;

/**
 * Created by 张少昆 on 2018/10/20.
 */
public class Main {
    public static void main(String[] args){
        //1. 启动发布器
        Publisher publisher = new Publisher();
        System.out.println("server starts!");
        //2. 发布服务
        publisher.publish(new HelloServiceImpl(), "localhost", 8080);

    }
}
