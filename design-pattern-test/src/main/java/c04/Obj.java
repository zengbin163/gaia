package c04;

import java.util.Date;

/**
 * Created by zengbin on 2018/4/18.
 */
public class Obj implements Cloneable {
    private String name;
    private Date birthday;

    public Obj(String name, Date birthday){
        this.name = name;
        this.birthday = birthday;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException{
        Obj clone = (Obj) super.clone();
        //可以在这里实现属性的clone，这样就算某种意义上的深拷贝
//        clone.setBirthday();
//        clone.setName();

        return clone;
    }

    @Override
    public String toString(){
        return "Obj{" +
                       "name='" + name + '\'' +
                       ", birthday=" + birthday +
                       '}';
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public Date getBirthday(){
        return birthday;
    }

    public void setBirthday(Date birthday){
        this.birthday = birthday;
    }
}
