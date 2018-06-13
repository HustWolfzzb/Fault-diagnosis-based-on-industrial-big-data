import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

/**
 * Created by  ZZB on 2018/6/10.
 */
public class ZZB_Reducer extends Reducer<Text, IntWritable, Text, IntWritable> {
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
//        Integer count=0;
//        Map<Integer,Integer> kv = new HashMap<>();
//        for (IntWritable value : values) {
//            count++;
//            if (kv.get(value.get())==null){
//                kv.put(value.get(),1);
//            }
//            else kv.put(value.get(),kv.get(value.get()+1));
//        }
//        if (kv.keySet().size()>1){
//            String[] sensor = key.toString().split(" ");
//            String comm = "delete from gear where ";
//            for (int i =0;i<sensor.length-1;++i){
//                comm+=("Sensor"+(i+1) + "="+sensor[i]+" and ");
//            }
//            comm+=("Load="+sensor[sensor.length-1]);
//            if (kv.get(0)>kv.get(1)){
//                comm+=" and category=1";
//            }
//            else {
//                comm+=" and category=0";
//            }
//            context.write(new Text(comm),new IntWritable());
//        }
//        else {
//            context.write(new Text(key+" simple"),new IntWritable(kv.keySet().size()));
//        }
        Integer count=0;
        int times = 0;
        for (IntWritable value : values) {
            count+=value.get();
            times++;
        }
        if (times==1){
            context.write(new Text(key),new IntWritable(count));
        }
        else if (count%times==0){
            context.write(new Text(key),new IntWritable(count/times));
        }
    }
}

