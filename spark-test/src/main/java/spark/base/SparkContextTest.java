package spark.base;

import ch.qos.logback.classic.Level;
import org.apache.spark.HeartbeatReceiver;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import spark.util.LoggerUtils;

/**
 * 前置：spark conf！
 * <p>
 * 说明：spark context是到spark cluster的一个链接！一个JVM只能有一个！在下一个sc之前，必须先stop()！
 * <p>
 * 说明：spark context用于创建RDD、累加器、广播！
 * <p>
 * 说明：Java请使用JavaSparkContext，用法一致，但过滤一些scala的东西。
 * <p>
 * Created by 张少昆 on 2018/4/28.
 */
public class SparkContextTest {
    public static void main(String[] args){
        //log4j规定了默认的几个级别：trace<debug<info<warn<error<fatal
        LoggerUtils.initLogLevel(Level.WARN);

        SparkConf sparkConf = initSparkConfWithLocalMaster("sc-test");
        SparkContext sc = new SparkContext(sparkConf);

        //查看默认的并行数量，还是master里设置的
        System.out.println("默认的并行数量：" + sc.defaultParallelism());

        //先看看sc的日志级别（如果没有任何设置，默认是DEBUG）
        pringLogSupport(sc);

//        sc.setLogLevel("INFO"); //FIXME 奇怪的是，sc的日志级别设置不管用？？？
//        System.out.println("---------------");
//        pringLogSupport(sc);

        //会被job的所有node下载的文件
        sc.addFile("d:/线程池源码解析.txt");
        //会被所执行的task所依赖的jar
//        sc.addJar();
        System.out.println(sc.addedFiles());
        System.out.println(sc.addedJars());

//        sc.addSparkListener(new HeartbeatReceiver(sc));//Lives in the driver to receive heartbeats from executors..
        HeartbeatReceiver heartbeatReceiver = new HeartbeatReceiver(sc);//两点，一是构造对象的时候已经注册；二是spark context实际上已经注册过该对象！

        System.out.println("heartbeatReceiver: " + heartbeatReceiver);
        //输出所有的监听器 - 装载于spark listener bus之上的！
        sc.listenerBus().listeners().forEach(System.out::println);

        System.out.println("-----------");
        System.out.println(sc.jobProgressListener());//包含在上面的listenerBus.listeners中了

        //输出一系列信息
        printSparkContextInfo(sc);

        //开始RDD操作，略。见c01_rdd.LoadFile/WordCount


        //断开链接，关闭sc
        sc.stop();

    }

    /**
     * 创建一个spark conf，使用的是local master，2个线程
     *
     * @param appName
     * @return
     */
    public static SparkConf initSparkConfWithLocalMaster(String appName){
        SparkConf sparkConf = new SparkConf();//默认会加载java环境的properties，可以false来禁止

        sparkConf.setAppName(appName).setMaster("local[2]");

        return sparkConf;
    }

    /**
     * 看看spark context的日志级别支持
     *
     * @param sc
     */
    public static void pringLogSupport(SparkContext sc){
        System.out.println("sc.log().isTraceEnabled(): " + sc.log().isTraceEnabled()); //默认，sc的日志级别是DEBUG吗？
        System.out.println("sc.log().isDebugEnabled(): " + sc.log().isDebugEnabled());
        System.out.println("sc.log().isInfoEnabled(): " + sc.log().isInfoEnabled());
        System.out.println("sc.log().isWarnEnabled(): " + sc.log().isWarnEnabled());
        System.out.println("sc.log().isErrorEnabled(): " + sc.log().isErrorEnabled());
    }

    /**
     * 基本的信息
     *
     * @param sc
     */
    public static void printSparkContextInfo(SparkContext sc){
        System.out.println("master: " + sc.master());
        System.out.println("applicationAttemptId: " + sc.applicationAttemptId());
        System.out.println("applicationId: " + sc.applicationId());
        System.out.println("appName: " + sc.appName());
        System.out.println("deployMode: " + sc.deployMode());//client
        System.out.println("defaultMinPartitions: " + sc.defaultMinPartitions());//默认math.min，不能高于2？？？
        System.out.println("eventLogCodec: " + sc.eventLogCodec());
        System.out.println("env: " + sc.env());//需要展开
        System.out.println("eventLogDir: " + sc.eventLogDir());
        System.out.println("eventLogger: " + sc.eventLogger());
        System.out.println("executorAllocationManager: " + sc.executorAllocationManager());
        System.out.println("executorMemory: " + sc.executorMemory());
        System.out.println("files: " + sc.files());
        System.out.println("getAllPools: " + sc.getAllPools());
        System.out.println("getCallSite: " + sc.getCallSite());//调用点？？？？指向了当前行！
        System.out.println("getCheckpointDir: " + sc.getCheckpointDir());
        System.out.println("getExecutorIds: " + sc.getExecutorIds());
        System.out.println("getExecutorMemoryStatus: " + sc.getExecutorMemoryStatus());
//        sc.getExecutorThreadDump()
        System.out.println("getLocalProperties: " + sc.getLocalProperties());
        System.out.println("getPersistentRDDs: " + sc.getPersistentRDDs());
        System.out.println("getExecutorStorageStatus: " + sc.getExecutorStorageStatus());
        System.out.println("getRDDStorageInfo: " + sc.getRDDStorageInfo());
        System.out.println("getSchedulingMode: " + sc.getSchedulingMode());//FIFIO
        System.out.println("getSparkHome: " + sc.getSparkHome());
        System.out.println("hadoopConfiguration: " + sc.hadoopConfiguration());
        System.out.println("hadoopFile$default$5:" + sc.hadoopFile$default$5());
        System.out.println("hadoopRDD$default$5: " + sc.hadoopRDD$default$5());
        System.out.println("isEventLogEnabled: " + sc.isEventLogEnabled());
        System.out.println("isLocal: " + sc.isLocal());
        System.out.println("isStopped: " + sc.isStopped());
        System.out.println("isTraceEnabled: " + sc.isTraceEnabled());
        System.out.println("killTaskAttempt$default$2: " + sc.killTaskAttempt$default$2());
        System.out.println("killTaskAttempt$default$3: " + sc.killTaskAttempt$default$3());
        System.out.println("listenerBus: " + sc.listenerBus());
        System.out.println("listFiles: " + sc.listFiles());
        System.out.println("listJars: " + sc.listJars());
        System.out.println("localProperties: " + sc.localProperties());
        System.out.println("logName: " + sc.logName());
        System.out.println("master: " + sc.master());
        System.out.println("makeRDD$default$2: " + sc.makeRDD$default$2());
        System.out.println("newAPIHadoopRDD$default$1: " + sc.newAPIHadoopRDD$default$1());
        System.out.println("newRddId: " + sc.newRddId());
        System.out.println("newAPIHadoopFile$default$5: " + sc.newAPIHadoopFile$default$5());
        System.out.println("newShuffleId: " + sc.newShuffleId());
        System.out.println("objectFile$default$2: " + sc.objectFile$default$2());
        System.out.println("parallelize$default$2: " + sc.parallelize$default$2());
        System.out.println("persistentRdds: " + sc.persistentRdds());
        System.out.println("progressBar: " + sc.progressBar());
        System.out.println("range$default$3: " + sc.range$default$3());
        System.out.println("range$default$4: " + sc.range$default$4());
        System.out.println("schedulerBackend: " + sc.schedulerBackend());
        System.out.println("sequenceFile$default$2: " + sc.sequenceFile$default$2());
        System.out.println("sparkUser: " + sc.sparkUser());
        System.out.println("startTime: " + sc.startTime());
        System.out.println("setJobGroup$default$3: " + sc.setJobGroup$default$3());
        System.out.println("statusTracker: " + sc.statusTracker());
        System.out.println("ui: " + sc.ui());
        System.out.println("uiWebUrl: " + sc.uiWebUrl());
        System.out.println("unpersistRDD$default$2: " + sc.unpersistRDD$default$2());
        System.out.println("version: " + sc.version());
        System.out.println("wholeTextFiles$default$2: " + sc.wholeTextFiles$default$2());
    }
}
