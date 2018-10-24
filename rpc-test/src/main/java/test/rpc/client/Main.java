package test.rpc.client;

import test.rpc.api.HelloService;
import test.rpc.pojo.User;

import java.lang.reflect.Proxy;

/**
 * Created by zengbin on 2018/10/20.
 */
public class Main {
    public static void main(String[] args){
        HelloService service = (HelloService) Proxy.newProxyInstance(HelloService.class.getClassLoader(), new Class<?>[]{HelloService.class}, new MessageInvocationHandler("localhost", 8080, HelloService.class));
        User user = new User(18, "Eighteen");
        String res = service.hi(user);
        System.out.println("got this: " + res);

        System.out.println("id: " + service.id(user));
        System.out.println("name: " + service.name(user));
    }
}
