package com.zhou.mapreduce.Writable;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class FlowReducer extends Reducer<Text, FlowBean, Text, FlowBean> {
    private FlowBean outV = new FlowBean();
    @Override
    protected void reduce(Text key, Iterable<FlowBean> values, Context context) throws IOException, InterruptedException{
        //Text key:手机号
        //Iterable<FlowBean> values:相同的key(手机号)的各自对应的FlowBean对象(FlowBean对象中包括上下行总流量数据)
        //一个手机号进入一次该函数
        // 1 遍历集合累加值
        long totalUp = 0;
        long totalDown = 0;

        // 2 将相同手机号的上行和下行流量相加
        for(FlowBean value : values){
            totalUp += value.getUpFlow();
            totalDown += value.getDownFlow();
        }

        // 3 封装outK, outV
        outV.setUpFlow(totalUp);
        outV.setDownFlow(totalDown);
        outV.setSumFlow();

        // 4 写出
        context.write(key,outV);
    }
}
