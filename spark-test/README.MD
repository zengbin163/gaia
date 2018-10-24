# spark-test

spark，核心spark context，执行RDD。

RDD三种生成方式：Java对象(sc.parallelize(obj)、hdfs文件(sc.textfile(..)/sc.wholetextfile(..))、其他RDD（transformation、action）。

spark context 需要依赖 spark conf 来做一些设置：例如master用来设置是单机运行(local)，还是集群运行(yarn/mesos)，用来设置应用的名字等等。
