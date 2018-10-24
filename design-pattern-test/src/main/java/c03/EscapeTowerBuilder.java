package c03;

import c03.pojo.EscapeTower;

/**
 * Created by zengbin on 2018/4/17.
 */
public class EscapeTowerBuilder {
    public EscapeTower create(){
        return new EscapeTower() {
            @Override
            public String name(){
                return "我的逃生塔";
            }
        };
    }
}
