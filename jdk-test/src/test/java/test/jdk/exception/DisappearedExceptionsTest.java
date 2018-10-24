package test.jdk.exception;

import org.junit.Test;

/**
 * 开发人员对异常处理的try-catch-finally语句块都比较熟悉。如果在try语句块中抛出了异常，在控制权转移到调用栈上一层代码之前，finally语句块中的语句也会执行。但是finally语句块在执行的过程中，也可能会抛出异常。
 * 如果finally语句块也抛出了异常，那么这个异常会往上传递，而之前try语句块中的那个异常就丢失了。
 * <p>
 * 对这种问题的解决办法一般有两种：一种是抛出try语句块中产生的原始异常，忽略在finally语句块中产生的异常。
 * 这么做的出发点是try语句块中的异常才是问题的根源。
 * <p>
 * 另外一种是把产生的异常都记录下来。这么做的好处是不会丢失任何异常。
 * 在java7之前，这种做法需要实现自己的异常类，而在java7中，已经对Throwable类进行了修改以支持这种情况。
 * 在java7中为Throwable类增加<b>addSuppressed</b>方法。
 * 当一个异常被抛出的时候，可能有其他异常因为该异常而被抑制住，从而无法正常抛出。
 * 这时可以通过addSuppressed方法把这些被抑制的方法记录下来。
 * 被抑制的异常会出现在抛出的异常的堆栈信息中，也可以通过getSuppressed方法来获取这些异常。
 * 这样做的好处是不会丢失任何异常，方便开发人员进行调试。
 * <p>
 * 来源：https://www.cnblogs.com/langtianya/p/5139465.html
 * <p>
 * TODO 如果是io流之类的，不要用这个，请直接使用try-with-resources！
 * <p>
 * Created by zengbin on 2018/5/2.
 */
public class DisappearedExceptionsTest {
    @Test
    public void r1(){
        try{
//            showA();
            showB();
        } catch(BaseException e){
            e.printStackTrace();
        }
    }

    /**
     * bad example！
     *
     * @throws BaseException
     */
    public void showA() throws BaseException{
        try{
            Integer.parseInt("Hello");
        } catch(NumberFormatException e1){
            throw new BaseException(e1); //TODO 这个被吞了
        } finally{
            try{
                int result = 2 / 0;
            } catch(ArithmeticException e2){
                throw new BaseException(e2);
            }
        }
    }

    /**
     * good example!
     * <p>
     * 使用JDK 7 的addSuppressed，防止吞掉其他异常！
     *
     * @throws BaseException
     */
    public void showB() throws BaseException{
        //1. 关键，需要外部定义异常。
        BaseException e = null;
        try{
            Integer.parseInt("Hello");
        } catch(NumberFormatException e1){
            //2. 需要接收异常，注意，这里不要抛出！
            e = new BaseException(e1); //TODO 不需要抛出了，交给finally负责
        } finally{
            try{
                int result = 2 / 0;
            } catch(ArithmeticException e2){
                BaseException baseException = new BaseException(e2);

                if(e == null){
                    e = baseException;
                } else{
                    if(baseException != null){
                        e.addSuppressed(baseException);
                    }
                }
            }

            if(e != null){
                throw e;
            }
        }
    }

    class BaseException extends Exception {
        public BaseException(Exception ex){
            super(ex);
        }
    }
}
