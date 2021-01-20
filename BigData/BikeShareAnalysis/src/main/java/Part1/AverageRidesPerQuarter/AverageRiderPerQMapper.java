/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Part1.AverageRidesPerQuarter;

import static Part1.AverageRidesPerQuarter.RiderWritable.log;
import java.io.IOException;
import java.text.SimpleDateFormat;
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
public class AverageRiderPerQMapper extends Mapper<LongWritable,Text,Text,IntWritable>{
    
    public static final Log log = LogFactory.getLog(AverageRiderPerQMapper.class);
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        
        RiderWritable obj = new RiderWritable();
        String input = value.toString();   
        String[] eachLine = input.split(",");
        try {
            int totalduration = Integer.parseInt(eachLine[0])/(1000*60);
            obj.setTotalduration(totalduration);
            obj.createRiderObject(input);
            
        } catch (Exception e) {
            log.info("UNKNOWN date format:"+ eachLine[0]);
            return;
        }
        String outputKey = obj.getSeason()+","+ obj.getUserType();

        if(obj != null)       
            context.write(new Text(outputKey), new IntWritable(1));
    }
    
    
}
