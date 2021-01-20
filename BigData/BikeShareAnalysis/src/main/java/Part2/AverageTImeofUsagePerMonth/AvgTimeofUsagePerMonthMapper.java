/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Part2.AverageTImeofUsagePerMonth;

import Part2.AverageTImeofUsagePerMonth.RiderWritable;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 *
 * @author gemi
 */
public class AvgTimeofUsagePerMonthMapper extends Mapper<LongWritable,Text,Text,RiderWritable>{
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
        
        IntWritable count = new IntWritable(1);
        
        obj.setmCount(count);
        obj.setmTimeDuration(new FloatWritable(timeduration));
        obj.setmMonth(new Text(month_name));
        
        context.write(new Text(month_name), obj);
    }
}
