package jdk.spi;

/**
 * Created by zengbin on 2017/11/25.
 */
public class CommandA implements ICommand {
    @Override
    public void execute(){
        System.out.println("CommandA is executing");
    }
}
