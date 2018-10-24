package test.jvm.gc;

/**
 * 测试JVM调用被回收对象的finalize()方法的次数。
 * <p>
 * finalize()的javadoc明确指出，最多执行一次！
 * <p>
 * TODO 这里仅作测试，正常情况下不要使用该方法！
 * <p>
 * Created by 张少昆 on 2018/3/23.
 */
public class FinalizeEscapeGC {
    public static FinalizeEscapeGC OO = null;

    //The finalize method is never invoked more than once by a Java virtual machine for any given object.
    @Override
    protected void finalize() throws Throwable{
        System.out.println("finalize() is executed!");
        OO = this;
    }

    public static void printStatus(){
        if(OO == null){
            System.out.println("oh no..");
        } else{
            System.out.println("yeah, alive!");
        }
    }

    public static void main(String[] args){
        OO = new FinalizeEscapeGC();

        OO = null; //强行断开
        System.gc(); //TODO 第一次gc
        try{
            Thread.sleep(1000L); //gc时finalize()优先级很低，需要等一下再执行
        } catch(InterruptedException e){
            e.printStackTrace();
        }
        printStatus();

        OO = null; //强行断开
        System.gc(); //TODO 第二次gc
        try{
            Thread.sleep(1000L); //gc时finalize()优先级很低，需要等一下再执行
        } catch(InterruptedException e){
            e.printStackTrace();
        }
        printStatus(); //现在over了，因为finalize()仅会被执行一次
    }

}
