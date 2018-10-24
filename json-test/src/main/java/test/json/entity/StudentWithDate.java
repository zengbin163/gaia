package test.json.entity;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by 张少昆 on 2017/12/8.
 */
public class StudentWithDate {
    // @SerializedName("NAME")
    private String name;
    private double age;
    private Date birthday;
    private String school;
    private String[] major;
    private boolean has_girlfriend;
    private String car;
    private String house;
    private String comment;

    private transient String ignore;

    public String getIgnore(){
        return ignore;
    }

    public void setIgnore(String ignore){
        this.ignore = ignore;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public double getAge(){
        return age;
    }

    public void setAge(double age){
        this.age = age;
    }

    public Date getBirthday(){
        return birthday;
    }

    public void setBirthday(Date birthday){
        this.birthday = birthday;
    }

    public String getSchool(){
        return school;
    }

    public void setSchool(String school){
        this.school = school;
    }

    public String[] getMajor(){
        return major;
    }

    public void setMajor(String[] major){
        this.major = major;
    }

    public boolean isHas_girlfriend(){
        return has_girlfriend;
    }

    public void setHas_girlfriend(boolean has_girlfriend){
        this.has_girlfriend = has_girlfriend;
    }

    public String getCar(){
        return car;
    }

    public void setCar(String car){
        this.car = car;
    }

    public String getHouse(){
        return house;
    }

    public void setHouse(String house){
        this.house = house;
    }

    public String getComment(){
        return comment;
    }

    public void setComment(String comment){
        this.comment = comment;
    }

    @Override
    public String toString(){
        return "StudentWithDate{" +
                       "name='" + name + '\'' +
                       ", age=" + age +
                       ", birthday=" + birthday +
                       ", school='" + school + '\'' +
                       ", major=" + Arrays.toString(major) +
                       ", has_girlfriend=" + has_girlfriend +
                       ", car='" + car + '\'' +
                       ", house='" + house + '\'' +
                       ", comment='" + comment + '\'' +
                       ", ignore='" + ignore + '\'' +
                       '}';
    }
}
