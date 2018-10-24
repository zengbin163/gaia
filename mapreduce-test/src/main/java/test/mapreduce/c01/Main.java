package test.mapreduce.c01;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Job;

import java.io.IOException;

/**
 * 入口。
 * <p>
 * Created by 张少昆 on 2018/5/23.
 */
public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException{
        Job job = new Job(); //过时了
        job.setJarByClass(Main.class);
        job.setJobName("Demo");

        //ohno，新版本已经不能用job了，这里需要用jobconf。
        //输入路径，可以有多个 - 还可以是目录！但不会递归，仅第一层。还支持压缩的！
//        FileInputFormat.addInputPath(jobconf, new Path(args[0]));
        //输出路径，只有一个。且不能已存在！
//        FileOutputFormat.setOutputPath(jobconf, new Path(args[1]));

        job.setMapperClass(DemoMapper.class);
        job.setReducerClass(DemoReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        System.out.println(job.waitForCompletion(true));
    }

    public void r() throws IOException{
        Job job = Job.getInstance();

        Configuration conf = new JobConf();

        Job demo = Job.getInstance(conf, "Demo");
    }
}
