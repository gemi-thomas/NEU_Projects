/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Part6.AverageNumberOfRidesByHour;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
public class NumberOfRidesPerHourMapper extends Mapper<LongWritable,Text,Text,IntWritable>{
    public static final Log log = LogFactory.getLog(NumberOfRidesPerHourMapper.class);
    protected void map(LongWritable key, Text value, Mapper.Context context) throws IOException, InterruptedException, IOException {
        HourWritable obj = new HourWritable();
        String input = value.toString();   
        String[] eachLine = input.split(",");
        
        int hourOfDay = 0;

        try {
            
            Date startDateTemp = obj.getDateandHourObject(eachLine[1]);
            if(startDateTemp == null)
                return;
            
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date dateofRide = formatter.parse(formatter.format(startDateTemp));

            Calendar cal = Calendar.getInstance();
            cal.setTime(startDateTemp);

            obj.setRideDate(new Text(dateofRide.toString()));
            hourOfDay = cal.get(Calendar.HOUR_OF_DAY);
            String hour_of_day = obj.theTimeOfDay(hourOfDay);
            obj.setRideHour(new Text(hour_of_day));
                        
        } catch (Exception e) {
            log.info("UNKNOWN date format:"+ eachLine[0]);
            return;
        } 
        
        String outKey = obj.getRideDate().toString()+", "+obj.getRideHour().toString();
               
        if(obj != null)       
            context.write(new Text(outKey), new IntWritable(1));
        
    }
}
