# Hadoop-MapReduce

1. Mapper
2. Reducer
3. Job

最后，导出 - 注意只需要导出自己编写的部分，指定mainClass即可。

执行：
> export HADOOP_CLASSPATH=mapreduce-test-1.0-SNAPSHOT.jar
> hadoop test.mapreduce.c01.Main "读取的路径" "输出的路径"


## 注意
可以在windows下执行！
> set HADOOP_CLASSPATH=mapreduce-test-1.0-SNAPSHOT.jar
> hadoop -Xmx1000m test.mapreduce.c01.Main file:///D:\demo.txt.gz D:\output