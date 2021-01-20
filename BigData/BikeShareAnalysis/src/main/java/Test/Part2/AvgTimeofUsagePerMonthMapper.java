/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test.Part2;

import Test.Part2.RiderWritable;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 *
 * @author gemi
 */
public class AvgTimeofUsagePerMonthMapper extends Mapper<LongWritable,Text,RiderKey,RiderWritable>{
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        RiderWritable obj = new RiderWritable();
        
        String input = value.toString();
        String[] eachLine = input.split(",");
        float timeduration;
        try {
            timeduration = Float.parseFloat(eachLine[0])/(1000*60);
        } catch (Exception e) {
            return;
        }
//        String timeofUsage = eachLine[1];
        
        Date timeofUsage = obj.getDateObject(eachLine[1]);
        
        if(timeofUsage == null)
            return;
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(timeofUsage);
        int month = cal.get(Calendar.MONTH);
        String month_name = obj.theMonth(month);
        
        if(month_name == null)
            return;
        
        IntWritable count = new IntWritable(1);
        
        obj.setmCount(count);
        obj.setmTimeDuration(new FloatWritable(timeduration));
        obj.setmMonth(new Text(month_name));
        
        RiderKey riderkey = new RiderKey(month_name, month);      
        
        context.write(riderkey, obj);
    }
}
