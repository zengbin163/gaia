package spark.base;

import ch.qos.logback.classic.Level;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import spark.util.LoggerUtils;

/**
 * 比SparkContext友好，貌似屏蔽了一些sparkcontext的scala方法
 * <p>
 * Created by zengbin on 2018/4/28.
 */
public class JavaSparkContextTest {
    public static void main(String[] args){
        //log4j规定了默认的几个级别：trace<debug<info<warn<error<fatal
        LoggerUtils.initLogLevel(Level.WARN);

        SparkConf sparkConf = SparkContextTest.initSparkConfWithLocalMaster("sc-test");
        JavaSparkContext javaSparkContext = new JavaSparkContext(sparkConf);//TODO java sc!!!

        System.out.println(javaSparkContext.defaultMinPartitions());
        System.out.println(javaSparkContext.defaultParallelism());
    }
}
