package c04;

import java.util.Date;

/**
 * 注意，clone()的前提是该类实现了标记接口Cloneable。
 * <p>
 * String就没有实现，所以不支持clone操作。
 * <p>
 * Created by 张少昆 on 2018/4/18.
 */
public class Main {
    public static void main(String[] args) throws CloneNotSupportedException{
//        testObjectClone();
        testDeepClone();
    }

    public static void testObjectClone() throws CloneNotSupportedException{
        Obj obj = new Obj("doli", new Date());
        System.out.println(obj);

        Obj clone = (Obj) obj.clone();
        System.out.println(clone == obj); //false

        //true 浅拷贝
        System.out.println(obj.getName() == clone.getName());
        System.out.println(obj.getBirthday() == clone.getBirthday());

        obj.setBirthday(new Date());
        System.out.println(clone.getBirthday() == obj.getBirthday());//false
    }

    public static void testDeepClone() throws CloneNotSupportedException{
        ObjDeep obj = new ObjDeep("hehe", new Date());

        ObjDeep clone = (ObjDeep) obj.clone();
        System.out.println(clone == obj);

        System.out.println(obj.getBirthday() == clone.getBirthday());
        System.out.println(obj.getName() == clone.getName());
    }
}
