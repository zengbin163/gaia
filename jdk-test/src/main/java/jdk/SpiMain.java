package jdk;

import jdk.spi.ICommand;

import java.util.ServiceLoader;

/**
 * 所谓SPI呢，就是服务提供接口。
 * 具体的做法是：定义接口；提供接口的实现；
 * 但在执行的时候不写死实现，而是通过META-INF/service/下的"接口全路径"为名字的文件来指定接口的实现。
 * 某种意义上，类似反射。
 * 可以指定一个，也可以指定多个。有意思。
 * <p>
 * Created by zengbin on 2017/11/25.
 */
public class SpiMain {
    public static void main(String[] args){
        //SPI加载
        ServiceLoader<ICommand> loader = ServiceLoader.load(ICommand.class);
        //接口实现的执行 -- 这就是为什么mysql driver能被识别的原因吗？
        loader.forEach(ICommand::execute);
    }
}
