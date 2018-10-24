package c03;

import c03.pojo.OrbitalModule;

/**
 * Created by zengbin on 2018/4/17.
 */
public class OrbitalModuleBuilder {
    public OrbitalModule create(){
        return new OrbitalModule() {

            @Override
            public String name(){
                return "我的轨道模块";
            }
        };
    }
}
