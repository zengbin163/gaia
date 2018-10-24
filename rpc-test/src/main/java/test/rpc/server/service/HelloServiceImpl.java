package test.rpc.server.service;

import test.rpc.api.HelloService;
import test.rpc.pojo.User;

/**
 * Created by zengbin on 2018/10/20.
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String hi(User user){
        System.out.println("test.rpc.server.service.HelloServiceImpl.hi(User user) 被调用了！");
        return user.toString();
    }

    @Override
    public int id(User user){
        if(user == null){
            return 0;
        }
        return user.getId();
    }

    @Override
    public String name(User user){
        if(user == null){
            return null;
        }
        return user.getName();
    }
}
