package com.zhou.mapreduce.partitionerandwritableComparable;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class FlowReducer extends Reducer<FlowBean, Text, Text, FlowBean> {

    @Override
    protected void reduce(FlowBean key, Iterable<Text> values, Reducer<FlowBean, Text, Text, FlowBean>.Context context) throws IOException, InterruptedException {
        // values中是多个总流量(key)相同的手机号(整合的效果), 为了要得到所有手机号的数据, 因此要逐个遍历写出
        for (Text value : values){
            context.write(value, key);
        }
    }
}
