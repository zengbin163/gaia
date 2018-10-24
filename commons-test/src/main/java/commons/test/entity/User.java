package commons.test.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by 张少昆 on 2017/12/11.
 */
public class User {
    private String name;
    private int age;
    private String address;
    private transient String sth;

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getAge(){
        return age;
    }

    public void setAge(int age){
        this.age = age;
    }

    public String getAddress(){
        return address;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public String getSth(){
        return sth;
    }

    public void setSth(String sth){
        this.sth = sth;
    }

    @Override
    public String toString(){
        return new ToStringBuilder(this)
                       .append("name", name)
                       .append("age", age)
                       .append("address", address)
                       .append("sth", sth)
                       .build();
    }
}
