package test.rpc.api;

import lombok.Data;

import java.io.Serializable;

/**
 * Message or Request
 * <p>
 * Created by 张少昆 on 2018/10/20.
 */
@Data
public class Message implements Serializable {


    private static final long serialVersionUID = -9153719258476812604L;
    /*** 接口名 */
    private Class<?> interfaceCls; //TODO 暂时用不到。约定只有一个接口。
    // /*** 方法 */
    // private Method method; //FIXME 默认序列化不行，因为Method没有实现序列化接口
    private String methodName;
    private Class[] parameterTypes;
    /*** 参数 */
    private Object[] methodArgs;
}
