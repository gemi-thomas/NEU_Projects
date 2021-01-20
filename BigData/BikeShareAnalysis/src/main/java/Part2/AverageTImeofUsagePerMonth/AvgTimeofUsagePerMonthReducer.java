/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Part2.AverageTImeofUsagePerMonth;

import java.io.IOException;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 *
 * @author gemi
 */
public class AvgTimeofUsagePerMonthReducer extends Reducer<Text, RiderWritable, Text, RiderWritable>{
    
    protected void reduce(Text key, Iterable<RiderWritable> values, Context context) throws IOException, InterruptedException {
        
        int count = 0;
        float total = 0;
        Text month = null;
        for(RiderWritable val : values) {
             count += val.getmCount().get();
             total += val.getmTimeDuration().get()*val.getmCount().get();
             month = val.getmMonth();
         }
        float average = total/count;
        RiderWritable obj = new RiderWritable();
        obj.setmCount(new IntWritable(count));
        obj.setmTimeDuration(new FloatWritable(average));
        obj.setmMonth(month);
        
         context.write(key,obj);
    }
}
