//import java.io.IOException;
//
//import org.apache.hadoop.conf.Configuration;
//import org.apache.hadoop.fs.Path;
//import org.apache.hadoop.io.IntWritable;
//import org.apache.hadoop.io.Text;
//import org.apache.hadoop.mapreduce.Job;
//import org.apache.hadoop.mapreduce.Mapper;
//import org.apache.hadoop.mapreduce.Reducer;
//import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
//import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
//import org.apache.hadoop.util.GenericOptionsParser;
//
//public class hadoopClear {
//    //map将输入中的value复制到输出数据的key上，并直接输出
//    public static class Map extends Mapper<Object,Text,Text,Text>{
//        //每行数据
//        private static Text line=new Text();
//        //实现map函数
//        public void map(Object key,Text value,Context context) throws IOException,InterruptedException{
//            String line = value.toString();
//            String[] values = line.split(" ");
//            line = "";
//            for(int i=0; i<values.length -1 ;++i)
//            {
//                line += values[i];
//            }
//            context.write(new Text(line), new Text(""));
//        }
//    }
//    //reduce将输入中的key复制到输出数据的key上，并直接输出
//    public static class Reduce extends Reducer<Text,Text,Text,Text>{
//        //实现reduce函数
//        public void reduce(Text key,Iterable<Text> values,Context context)
//                throws IOException,InterruptedException{
//            context.write(key, new Text(""));
//        }
//    }
//
//    public static void main(String[] args) throws Exception{
//        Configuration conf = new Configuration();
//        //这句话很关键
//        conf.set("mapred.job.tracker", "node61:9001");
//        String[] ioArgs=new String[]{"dedup_in","dedup_out"};
//        String[] otherArgs = new GenericOptionsParser(conf, ioArgs).getRemainingArgs();
//        if (otherArgs.length != 2) {
//            System.err.println("Usage: Data Deduplication <in> <out>");
//            System.exit(2);
//        }
//        Job job = new Job(conf, "Data Deduplication");
//        job.setJarByClass(hadoopClear.class);
//        //设置Map、Combine和Reduce处理类
//        job.setMapperClass(Map.class);
//        job.setCombinerClass(Reduce.class);
//        job.setReducerClass(Reduce.class);
//        //设置输出类型
//        job.setOutputKeyClass(Text.class);
//        job.setOutputValueClass(Text.class);
//        //设置输入和输出目录
//        FileInputFormat.addInputPath(job, new Path(args[0]));
//        FileOutputFormat.setOutputPath(job, new Path(args[1]));
//        System.exit(job.waitForCompletion(true) ? 0 : 1);
//    }
//}
//
//

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
/**
 * Created by  ZZB on 2018/6/10.
 */
public class hadoopClear {
    public static void main(String[] args)throws Exception{
        //创建配置对象
        Configuration conf = new Configuration();
        //创建job对象
        Job job = Job.getInstance(conf,"hadoopClear");
        //设置运行job的类
        job.setJarByClass(hadoopClear.class);
        //设置mapper 类
        job.setMapperClass(ZZB_Mapper.class);
        //设置reduce 类
        job.setReducerClass(ZZB_Reducer.class);
        //设置map输出的key value
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        //设置reduce 输出的 key value
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        //设置输入输出的路径
        FileInputFormat.setInputPaths(job, new Path(args[1]));
        FileOutputFormat.setOutputPath(job, new Path(args[2]));
        //提交job
        boolean b = job.waitForCompletion(true);
        if(!b){
            System.out.println("wordcount task fail!");
        }
    }
}