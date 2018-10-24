package test.rpc.api;

import test.rpc.pojo.User;

/**
 * Created by zengbin on 2018/10/20.
 */
public interface HelloService {
    /**
     * 对user打招呼，并获取user的回应
     *
     * @param user
     * @return
     */
    String hi(User user);

    int id(User user);

    String name(User user);
}
