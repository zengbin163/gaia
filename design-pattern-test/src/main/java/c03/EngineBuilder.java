package c03;

import c03.pojo.Engine;

/**
 * Created by 张少昆 on 2018/4/17.
 */
public class EngineBuilder {
    public Engine create(){
        return new Engine() {
            @Override
            public String name(){
                return "我的发动机";
            }
        };
    }
}
