import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created by  ZZB on 2018/6/10.
 */
public class ZZB_Mapper extends Mapper<LongWritable, Text,Text,IntWritable>{
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException{
        //得到输入的每一行数据  
        String line = value.toString();
        //通过空格分隔  
        String[] values = line.split(" ");
        line = "";
        for(int i=0; i<values.length-1  ;++i)
        {
            line += (values[i]+" ");
        }
        context.write(new Text(line), new IntWritable((int)Float.parseFloat(values[values.length-1])));
    }
}
