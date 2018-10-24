package test.jdk.abstractt;

/**
 * Created by 张少昆 on 2018/4/17.
 */
public abstract class Abstract {
    public Abstract(){
        System.out.println("抽象类的构造器");
        hi();
    }

    public abstract void hi();

    public void fly(){
        System.out.println("fly...");
    }
}
