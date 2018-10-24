package spark.c01_rdd;

import ch.qos.logback.classic.Level;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;
import spark.util.LoggerUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//顺便说下Logger的原理，首先，slf4j是门面，提供了Logger和LoggerBinder接口，具体的框架则实现了这俩接口。
//当使用LoggerFactory.getLogger(xxx)的时候，LoggerFactory会先检查所有的StaticLoggerBinder(见源码)。
public class WordCount {

    public static void main(String[] args){
        //TODO 通过代码来提高日志级别，而非logback.xml！注意，必须放在开始，因为是单例！
        LoggerUtils.initLogLevel(Level.INFO);


        //-----------------

        //需要设置环境变量HADOOP_HOME或者hadoop.home.dir，这里临时设置下！
        System.setProperty("hadoop.home.dir", "d:/hadoop");

        //conf可以是null，也可以设置。主要是设置运行模式（单机、集群[yarn/mesos]）！
        SparkConf conf = new SparkConf().setMaster("local[2]").setAppName("wordCountTest");//local[2]最少2！一个driver，一个executor

        //spark context，这是关键
        JavaSparkContext sc = new JavaSparkContext(conf);
//        sc.setLogLevel("WARN");

        List<String> list = new ArrayList<>();
        list.add("1 1 2 a b");
        list.add("a b 1 2 3");

        //TODO spark创建RDD三种方式之内存对象；另外两种是：hdfs文件、其他RDD（变形、动作）
        JavaRDD<String> RddList = sc.parallelize(list);

        //先切分为单词，扁平化处理
        JavaRDD<String> flatMapRdd = RddList.flatMap(str -> Arrays.asList(str.split(" ")).iterator());

        //再转化为键值对
        JavaPairRDD<String, Integer> pairRdd = flatMapRdd.mapToPair(word -> new Tuple2<>(word, 1));

        //对每个词语进行计数
        JavaPairRDD<String, Integer> countRdd = pairRdd.reduceByKey((v1, v2) -> v1 + v2);

        System.out.println("结果：" + countRdd.collect());
        //保存为文本
//        countRdd.saveAsTextFile(outputDir);

        sc.close();
    }
}