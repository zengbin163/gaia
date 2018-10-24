package test.mapreduce.c01;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Created by 张少昆 on 2018/5/23.
 */
public class DemoReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

    //注意值是可以迭代的（其实就是map的时候容易一个key对应多个value）
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException{
        //取最大值
        int max = Integer.MIN_VALUE;

        for(IntWritable value : values){
            max = Math.max(max, value.get());
        }

        //输出
        context.write(key, new IntWritable(max));
    }
}
