package test.jdk.abstractt;

/**
 * Created by 张少昆 on 2018/4/17.
 */
public class Impl extends Abstract {
    @Override
    public void hi(){
        System.out.println("hi...");
    }

    public static void main(String[] args){
        Impl impl = new Impl();
    }
}
