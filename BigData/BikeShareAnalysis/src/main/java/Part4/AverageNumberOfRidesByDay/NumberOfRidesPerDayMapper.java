/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Part4.AverageNumberOfRidesByDay;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 *
 * @author gemi
 */
public class NumberOfRidesPerDayMapper extends Mapper<LongWritable,Text,Text,IntWritable>{
      public static final Log log = LogFactory.getLog(NumberOfRidesPerDayMapper.class);
      protected void map(LongWritable key, Text value, Mapper.Context context) throws IOException, InterruptedException, IOException {
        DateKeyWritable obj = new DateKeyWritable();
        String input = value.toString();   
        String[] eachLine = input.split(",");
        int dayOfWeek = 0;
        try {
            
            Date startDateTemp = obj.getDateObject(eachLine[1]);
            if(startDateTemp == null)
                return;

            Calendar cal = Calendar.getInstance();
            cal.setTime(startDateTemp);
            obj.setRideDate(new Text(startDateTemp.toString()));
            dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
            String day_name = obj.theDay(dayOfWeek);
            obj.setDay(new Text(day_name));
                        
        } catch (Exception e) {
            log.info("UNKNOWN date format:"+ eachLine[0]);
            return;
        } 
        
        String outKey = obj.getRideDate()+", "+obj.getDay();

        if(obj != null)       
            context.write(new Text(outKey), new IntWritable(1));
    }
      
}
