package com.zhou.mapreduce.outputformat;

import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

import java.io.IOException;

public class LogRecordWriter extends RecordWriter<Text, NullWritable> {
    private FSDataOutputStream biliOut;
    private FSDataOutputStream otherOut;
    public LogRecordWriter(TaskAttemptContext job){
        //创建两条流
        try{
            FileSystem fs = FileSystem.get(job.getConfiguration());
            //一条专门输出一行数据(网站)中包含"bilibili"的流
            biliOut = fs.create(new Path("D:\\projects\\IDEworkspace\\MapReduceDemo\\outputoutputformat\\bilibili.log"));
            //一条输出其他的数据
            otherOut = fs.create(new Path("D:\\projects\\IDEworkspace\\MapReduceDemo\\outputoutputformat\\other.log"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(Text key, NullWritable value) throws IOException, InterruptedException {
        String log = key.toString();
        //若一行数据中包含"bilibili"字符串
        if(log.contains("bilibili")){
            biliOut.writeBytes(log+"\n");
        }else {
            otherOut.writeBytes(log+"\n");
        }
    }

    @Override
    public void close(TaskAttemptContext taskAttemptContext) throws IOException, InterruptedException {
        IOUtils.closeStream(biliOut);
        IOUtils.closeStream(otherOut);
    }
}
