package test.pojo;

import java.util.Date;

/**
 * Created by 张少昆 on 2018/4/29.
 */
public class Person {
    public String name;
    public Date birthday;

    @Override
    public String toString(){
        return "Person{" +
                "name='" + name + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
