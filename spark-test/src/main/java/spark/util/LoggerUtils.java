package spark.util;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.impl.StaticLoggerBinder;

/**
 * Created by zengbin on 2018/4/27.
 */
public class LoggerUtils {

    /**
     * TODO 通过代码来提高日志级别，而非logback.xml！注意，必须放在开始，因为是单例！
     */
    public static void initLogLevel(Level level){
        StaticLoggerBinder loggerBinder = StaticLoggerBinder.getSingleton();
        boolean tell = loggerBinder.getLoggerFactory() instanceof LoggerContext;
        System.out.println(tell);

        LoggerContext loggerContext = (LoggerContext) loggerBinder.getLoggerFactory();
//        loggerContext.getLoggerList().forEach(System.out::println); //此时可能还没有加载xml，所以可能只有一个ROOT，正好设置
        Logger root = loggerContext.getLogger(Logger.ROOT_LOGGER_NAME);
        System.out.println(root);
        root.setLevel(level);
    }

}
