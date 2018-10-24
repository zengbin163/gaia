package spark.base;

import ch.qos.logback.classic.Level;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import scala.Tuple2;
import spark.util.LoggerUtils;

/**
 * Created by zengbin on 2018/4/27.
 */
public class SparkConfTest {
    public static void main(String[] args){
        LoggerUtils.initLogLevel(Level.INFO);

        //1, 创建spark conf，主要是设置master和app name。master的分类需要了解一下：local是本地运行
        SparkConf conf = new SparkConf();
        conf
                .setAppName("sometest")
                .setExecutorEnv("hadoop.home.dir", "d:/") //FIXME 这个key没用
                .setMaster("local[2]");

        //2,创建spark context，这个是核心。本测试就没有更进一步，可以参考WordCount和LoadFile。
        SparkContext sc = new SparkContext(conf);

        sc.setLogLevel("INFO"); //这个不是全局的设置，而是sc的设置

        //获取所有的conf - 不全吧？？？还是某类conf？
        Tuple2<String, String>[] all = sc.conf().getAll();

        for(Tuple2<String, String> tuple2 : all){
            System.out.println(tuple2._1 + " : " + tuple2._2);
//            System.out.println(tuple2._1() + " : " + tuple2._2());//同上一行一样

//            spark.app.id : local-1524839528686
//            spark.driver.port : 56237
//            spark.executorEnv.hadoop.home.dir : d:/
//            spark.executor.id : driver
//            spark.driver.host : 192.168.56.1
//            spark.app.name : sometest
//            spark.master : local[2]
        }

        System.out.println("------------");
        System.out.println("same conf? " + (conf == sc.conf()));//TODO 根据javadoc，传给spark context的conf其实是一个clone，且不可修改！
    }


}
