package spark.c01_rdd;

import ch.qos.logback.classic.Level;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.rdd.RDD;
import scala.Tuple2;
import spark.base.SparkContextTest;
import spark.util.LoggerUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zengbin on 2018/4/28.
 */
public class RddTest {

    public static void main(String[] args){
        LoggerUtils.initLogLevel(Level.ERROR);

        SparkConf sparkConf = SparkContextTest.initSparkConfWithLocalMaster("rdd-test");
        JavaSparkContext sparkContext = new JavaSparkContext(sparkConf);

        JavaRDD<String> rdd = sparkContext.textFile("d:/CopyOnWriteArrayList.txt");

//        test1(rdd);
        test2(rdd);


    }


    public static void test1(JavaRDD<String> rdd){
        rdd
                .map(e -> {
                    System.out.println(e);
                    return e;
                })
                .flatMap(e -> {
                    String[] arr = e.split("\\s+");
                    return Arrays.asList(arr).iterator();
                })
                .mapToPair(e -> new Tuple2<>(e, 1))
                .countByKey()
                .forEach((k, v) -> {
                    System.out.println(k + " : " + v);
                });
    }

    public static void test2(JavaRDD<String> rdd){
        rdd.flatMapToPair(s -> {
            String[] arr = s.split("\\s+");

            ArrayList<Tuple2<String, Integer>> ts = new ArrayList<>();
            for(String s1 : arr){
                ts.add(new Tuple2<>(s1, 1));
            }

            return ts.iterator();
        }).countByKey().forEach((k, v) -> {
            System.out.println(k + " : " + v);
        });
    }

    public static void test3(JavaRDD<String> rdd){

    }
}
