package test.mapreduce.c01;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * 四个泛型：k_in,v_in,k_out,v_out
 * <p>
 * 注意，Mapper还有几个方法，其中run()需要关注一下。setup方法与context有关。
 * <p>
 * Created by zengbin on 2018/5/23.
 */
public class DemoMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    //必须覆盖，否则没有变化
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException{
        //转换
        long lineNo = key.get(); //行号，自动生成的。
        String line = value.toString(); //Text -> String
        //处理过程，略

        //输出 - 注意，context有很多信息
        context.write(new Text(key.get() + ""), new IntWritable(1)); //仅示意输出，内容没有意义！
    }
}
