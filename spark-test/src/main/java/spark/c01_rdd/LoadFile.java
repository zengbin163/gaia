package spark.c01_rdd;

import ch.qos.logback.classic.Level;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import spark.util.LoggerUtils;


//顺便说下Logger的原理，首先，slf4j是门面，提供了Logger和LoggerBinder接口，具体的框架则实现了这俩接口。
//当使用LoggerFactory.getLogger(xxx)的时候，LoggerFactory会先检查所有的StaticLoggerBinder(见源码)。
public class LoadFile {

    public static void main(String[] args){
        LoggerUtils.initLogLevel(Level.INFO);


        //-----------------

        //需要设置环境变量HADOOP_HOME或者hadoop.home.dir，这里临时设置下！
//        System.setProperty("hadoop.home.dir", "d:/hadoop");

        //conf可以是null，也可以设置。主要是设置运行模式（单机、集群[yarn/mesos]）！
        SparkConf conf = new SparkConf().setMaster("local[2]").setAppName("textTest");//local[2]最少2！一个driver，一个executor

        //spark context，这是关键
        JavaSparkContext sc = new JavaSparkContext(conf);
        sc.setLogLevel("WARN");

        //会根据master自动判断当前路径类型
        JavaRDD<String> textFile = sc.textFile("D:\\解析后台管理.txt");

        System.out.println(textFile.collect());

        sc.close();
    }
}