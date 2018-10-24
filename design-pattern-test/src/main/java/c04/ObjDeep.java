package c04;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;

/**
 * 序列化/反序列化 深拷贝
 * <p>
 * Created by 张少昆 on 2018/4/18.
 */
public class ObjDeep implements Cloneable, Serializable {
    private String name;
    private Date birthday;

    public ObjDeep(String name, Date birthday){
        this.name = name;
        this.birthday = birthday;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException{
        byte[] bytes = null;
        ObjDeep clone = null;

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try{
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(this);
            bytes = bos.toByteArray();
        } catch(IOException e){
            e.printStackTrace();
        }

        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = null;
        try{
            ois = new ObjectInputStream(bis);
            clone = (ObjDeep) ois.readObject();
        } catch(IOException e){
            e.printStackTrace();
        } catch(ClassNotFoundException e){
            e.printStackTrace();
        }


        return clone;
    }

    @Override
    public String toString(){
        return "ObjDeep{" +
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
